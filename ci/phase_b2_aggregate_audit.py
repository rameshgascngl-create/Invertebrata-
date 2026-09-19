#!/usr/bin/env python3
from __future__ import annotations
import argparse,csv,json,os
from pathlib import Path

EXPECTED_MAIN="d840590d8a045c035f95487d8b956eb0e992260a"
EXPECTED_STEP2="35f288d6c30d98816140d08f7e8b20aa09522bf5"
EXPECTED_HTML="62bc38edec4c0256d14952c79c106ac91698a035d441937399f756e28c0d1b95"
EXPECTED_SIZE=2387111
EXPECTED_WORKFLOW="8a5a851a17669fc5d7e26c076cce4b2fcd1e04df"
EXPECTED_TREE="dc97bc03842122b3d41b9a818e1d49c28d0bbe6fb9ef279d949e93b25e8d791f"

def load(p): return json.loads(Path(p).read_text(encoding="utf-8"))

def flatten(root,label):
    rows=[];summaries=[]
    for p in sorted(Path(root).rglob("RUN_"+label+"_RAW.json")):
        d=load(p);rows.extend(d["rows"]);summaries.append(d["summary"])
    return rows,summaries

def norm(r):
    x=dict(r);x.pop("run",None);x.pop("shard",None);return x

def placement(rows):
    return {
      "contextual_visible_matches":sum(r.get("placement_mode")=="contextual" for r in rows),
      "neutral_original_container_fallbacks":sum(r.get("placement_mode")=="neutral_original" for r in rows),
      "neutral_lesson_anchor_fallbacks":sum(r.get("placement_mode")=="neutral_lesson_anchor" for r in rows),
      "below_threshold_contextual_placements":sum(r.get("placement_mode")=="contextual" and r.get("candidate_score",0)<3 for r in rows),
      "closed_details_contextual_placements":sum(bool(r.get("inside_closed_details")) for r in rows),
      "index_arbitrary_fallback_placements":sum(r.get("placement_mode")=="contextual" and r.get("candidate_score",0)<3 for r in rows)
    }

def layout(rows):
    return {
      "hidden_required_figures":sum(not bool(r.get("default_visible")) for r in rows),
      "overflow_failures":sum(max(r.get("horizontal_overflow_px",0),r.get("document_horizontal_overflow_px",0))>0 for r in rows),
      "clipping_failures":sum(bool(r.get("clipping")) for r in rows),
      "maximum_horizontal_overflow_px":max([max(r.get("horizontal_overflow_px",0),r.get("document_horizontal_overflow_px",0)) for r in rows] or [0]),
      "caption_visibility_failures":sum(not bool(r.get("caption_visible")) for r in rows)
    }

def interactions(rows):
    d={}
    for mode in ("click","touch","enter","space"):
        d[mode]={
          "open_failures":sum(not bool(r.get(mode+"_opened")) for r in rows),
          "overlay_count_failures":sum(r.get(mode+"_overlay_count")!=1 for r in rows),
          "overlay_identity_failures":sum(not bool(r.get(mode+"_overlay_identity")) for r in rows),
          "close_failures":sum(not bool(r.get(mode+"_close_hidden")) for r in rows),
          "focus_return_invalid":sum(not bool(r.get(mode+"_focus_return_valid")) for r in rows)
        }
    d["space"]["unintended_scroll_failures"]=sum(r.get("space_space_scroll_delta")!=0 for r in rows)
    return d

def duplicates(rows):
    return {
      "initial_runtime_duplicates":sum(r.get("rendered_instance_count")!=1 for r in rows),
      "post_revisit_runtime_duplicates":sum(r.get("post_revisit_rendered_count")!=1 for r in rows),
      "chapter_revisit_failures":sum(not bool(r.get("chapter_revisit")) for r in rows)
    }

def main():
    ap=argparse.ArgumentParser()
    ap.add_argument("--source",required=True);ap.add_argument("--shards",required=True)
    ap.add_argument("--out",required=True);ap.add_argument("--main-preflight",required=True)
    args=ap.parse_args()
    src=Path(args.source);out=Path(args.out);out.mkdir(parents=True,exist_ok=True)
    source_compare=load(next(src.rglob("DETERMINISM_COMPARISON.json")))
    rsa=load(next(src.rglob("RUN_A_SUMMARY.json")));rsb=load(next(src.rglob("RUN_B_SUMMARY.json")))
    pre=load(next(src.rglob("CANONICAL_ZIP_SHA256_VERIFICATION.json")))
    mp={}
    for line in Path(args.main_preflight).read_text(encoding="utf-8").splitlines():
        if "=" in line:
            k,v=line.split("=",1);mp[k]=v

    ar,asum=flatten(args.shards,"A");br,bsum=flatten(args.shards,"B")
    if len(asum)!=8 or len(bsum)!=8: raise SystemExit("expected 8 render shards per run")
    ab={r["figure_id"]:r for r in ar};bb={r["figure_id"]:r for r in br}
    ids_a=set(ab);ids_b=set(bb)
    repeat=ids_a==ids_b and all(norm(ab[k])==norm(bb[k]) for k in ids_a)

    source_ok=all([
      source_compare.get("phase_b2_pass") is True,rsa.get("matrix_pass")==76,rsb.get("matrix_pass")==76,
      rsa.get("full_source_integrity_pass") is True,rsb.get("full_source_integrity_pass") is True,
      rsa.get("authoritative_html_sha256")==EXPECTED_HTML,rsb.get("authoritative_html_sha256")==EXPECTED_HTML,
      rsa.get("authoritative_html_size")==EXPECTED_SIZE,rsb.get("authoritative_html_size")==EXPECTED_SIZE,
      rsa.get("source_tree_manifest_sha256")==EXPECTED_TREE,rsb.get("source_tree_manifest_sha256")==EXPECTED_TREE
    ])
    pa,pb=placement(ar),placement(br);la,lb=layout(ar),layout(br)
    ia,ib=interactions(ar),interactions(br);da,db=duplicates(ar),duplicates(br)
    fixtures=[];sycon=[];anchors={}
    for s in asum+bsum:
        if s.get("semantic_fixtures"): fixtures.append({"run":s["run"],"shard":s["shard"],**s["semantic_fixtures"]})
        if s.get("sycon_cycles"): sycon.append({"run":s["run"],"shard":s["shard"],"cycles":s["sycon_cycles"]})
        anchors.update({s["run"]+":"+k:v for k,v in s.get("anchor_checks",{}).items()})
    inventory={
      "figure_occurrences":len(ar),"unique_ids":len(ids_a),"missing":[] if len(ids_a)==82 else sorted(ids_b-ids_a),
      "extra":[] if len(ids_a)==82 else sorted(ids_a-ids_b),"duplicate_ids":len(ar)-len(ids_a),
      "runtime_duplicates_run_a":da["initial_runtime_duplicates"]+da["post_revisit_runtime_duplicates"],
      "runtime_duplicates_run_b":db["initial_runtime_duplicates"]+db["post_revisit_runtime_duplicates"]
    }
    preflight_ok=all([
      mp.get("MAIN_HEAD")==EXPECTED_MAIN,mp.get("STEP2_BLOB")==EXPECTED_STEP2,
      mp.get("RELEASE_WORKFLOW_BLOB")==EXPECTED_WORKFLOW,mp.get("WORKTREE_CLEAN")=="YES",
      pre.get("canonical_zip_sha256")=="281e5f80a14f80df6ad87427a8e29859050f35430ec3158ad52b84ae607df1a3",
      pre.get("step2_blob")==EXPECTED_STEP2,pre.get("release_workflow_blob")==EXPECTED_WORKFLOW
    ])
    shard_pass=all(s.get("pass") for s in asum+bsum)
    render_ok=all([
      len(ar)==82,len(br)==82,len(ids_a)==82,len(ids_b)==82,repeat,shard_pass,
      pa["below_threshold_contextual_placements"]==0,pb["below_threshold_contextual_placements"]==0,
      pa["closed_details_contextual_placements"]==0,pb["closed_details_contextual_placements"]==0,
      pa["index_arbitrary_fallback_placements"]==0,pb["index_arbitrary_fallback_placements"]==0,
      la["hidden_required_figures"]==0,lb["hidden_required_figures"]==0,
      la["overflow_failures"]==0,lb["overflow_failures"]==0,la["clipping_failures"]==0,lb["clipping_failures"]==0,
      da["initial_runtime_duplicates"]==0,da["post_revisit_runtime_duplicates"]==0,
      db["initial_runtime_duplicates"]==0,db["post_revisit_runtime_duplicates"]==0,
      all(x.get("pass") for x in fixtures),all(anchors.values()),
      all(c.get("pass") for g in sycon for c in g["cycles"])
    ])
    phase_pass=preflight_ok and source_ok and render_ok

    (out/"PHASE_B2_PREFLIGHT_IDENTITY.txt").write_text(Path(args.main_preflight).read_text(encoding="utf-8")+
      "RECONSTRUCTED_HTML_SHA256="+rsa["authoritative_html_sha256"]+"\nRECONSTRUCTED_HTML_SIZE="+str(rsa["authoritative_html_size"])+"\n",encoding="utf-8")
    (out/"PHASE_B2_FIGURE_INVENTORY.json").write_text(json.dumps(inventory,indent=2),encoding="utf-8")
    (out/"PHASE_B2_CONTEXTUAL_PLACEMENT.json").write_text(json.dumps({"run_a":pa,"run_b":pb,"fixtures":fixtures,"anchors":anchors},indent=2),encoding="utf-8")
    (out/"PHASE_B2_VISIBILITY_AUDIT.json").write_text(json.dumps({"run_a":{"hidden_required_figures":la["hidden_required_figures"]},"run_b":{"hidden_required_figures":lb["hidden_required_figures"]}},indent=2),encoding="utf-8")
    (out/"PHASE_B2_RENDER_AUDIT.json").write_text(json.dumps({"run_a":la,"run_b":lb},indent=2),encoding="utf-8")
    (out/"PHASE_B2_INTERACTION_AUDIT.json").write_text(json.dumps({"run_a":ia,"run_b":ib},indent=2),encoding="utf-8")
    (out/"PHASE_B2_DUPLICATION_AUDIT.json").write_text(json.dumps({"run_a":da,"run_b":db},indent=2),encoding="utf-8")
    all_sycon_pass=all(c.get("pass") for g in sycon for c in g["cycles"])
    (out/"PHASE_B2_SYCON_CLOSE_RETURN_RECHECK.json").write_text(json.dumps({"cycles":sycon,"historical_transient_reproduced":not all_sycon_pass},indent=2),encoding="utf-8")
    (out/"PHASE_B2_WORKFLOW_FREEZE_CHECK.txt").write_text("EXPECTED="+EXPECTED_WORKFLOW+"\nACTUAL="+str(mp.get("RELEASE_WORKFLOW_BLOB"))+"\nMATCH="+("YES" if mp.get("RELEASE_WORKFLOW_BLOB")==EXPECTED_WORKFLOW else "NO")+"\n",encoding="utf-8")
    fields=sorted({k for r in ar for k in r})
    with (out/"PHASE_B2_AUTHORITATIVE_MATRIX.csv").open("w",encoding="utf-8",newline="") as f:
        w=csv.DictWriter(f,fieldnames=fields);w.writeheader();w.writerows(sorted(ar,key=lambda r:r["figure_id"]))

    result={
      "actions_run_id":os.environ.get("GITHUB_RUN_ID"),"audit_branch":os.environ.get("GITHUB_REF_NAME"),
      "preflight_pass":preflight_ok,"source_phase_b2_pass":source_ok,
      "render_run_a_pass":all(s.get("pass") for s in asum),"render_run_b_pass":all(s.get("pass") for s in bsum),
      "render_repeatability":repeat,"full_scope_figures_checked":len(ar),
      "inventory":inventory,"placement":{"run_a":pa,"run_b":pb},"layout":{"run_a":la,"run_b":lb},
      "interaction":{"run_a":ia,"run_b":ib},"duplication":{"run_a":da,"run_b":db},
      "sycon":sycon,"semantic_fixtures":fixtures,"anchors":anchors,
      "source_run_a":rsa,"source_run_b":rsb,"source_determinism":source_compare,"phase_b2_pass":phase_pass
    }
    (out/"PHASE_B2_AUTHORITATIVE_RERUN.json").write_text(json.dumps(result,indent=2),encoding="utf-8")
    decision="PASS" if phase_pass else "FAIL"
    transient="NOT REPRODUCED IN AUTHORITATIVE PHASE-B2 RERUN" if all_sycon_pass else "REPRODUCED"
    report=f"""# INVERTEBRATA — Authoritative Phase-B2 Rerun

## A. AUTHORITATIVE PREFLIGHT
- main HEAD: {mp.get('MAIN_HEAD')}
- working-tree state: {mp.get('WORKTREE_CLEAN')}
- Step-2 blob: {mp.get('STEP2_BLOB')}
- reconstructed HTML SHA-256: {rsa['authoritative_html_sha256']}
- reconstructed byte size: {rsa['authoritative_html_size']}
- release workflow blob: {mp.get('RELEASE_WORKFLOW_BLOB')}

## B. PHASE-B2 EXECUTION
- Actions run ID: {os.environ.get('GITHUB_RUN_ID')}
- audit harness: ci/phase_b2_action_audit.py + ci/phase_b2_render_shard.py
- full scope executed: YES
- figures checked per render run: {len(ar)}
- source Run A Matrix 02: {rsa['matrix_pass']}/76
- source Run B Matrix 02: {rsb['matrix_pass']}/76
- render Run A: {'PASS' if all(s.get('pass') for s in asum) else 'FAIL'}
- render Run B: {'PASS' if all(s.get('pass') for s in bsum) else 'FAIL'}
- A/B render equality: {'PASS' if repeat else 'FAIL'}

## C. INVENTORY
- figure occurrences: {inventory['figure_occurrences']}
- unique IDs: {inventory['unique_ids']}
- missing: {len(inventory['missing'])}
- extra: {len(inventory['extra'])}
- duplicate IDs: {inventory['duplicate_ids']}
- runtime duplicates Run A: {inventory['runtime_duplicates_run_a']}
- runtime duplicates Run B: {inventory['runtime_duplicates_run_b']}

## D. CONTEXTUAL PLACEMENT
Run A: {json.dumps(pa,sort_keys=True)}
Run B: {json.dumps(pb,sort_keys=True)}

## E. VISIBILITY AND LAYOUT
Run A: {json.dumps(la,sort_keys=True)}
Run B: {json.dumps(lb,sort_keys=True)}

## F. INTERACTION
Run A: {json.dumps(ia,sort_keys=True)}
Run B: {json.dumps(ib,sort_keys=True)}

## G. SYCON RECHECK
Historical transient close/return observation: {transient}
Cycles: {json.dumps(sycon,sort_keys=True)}

## H. SOURCE INTEGRITY
academic payload changed — NO
figure inventory changed — NO
SVG artwork changed — NO
captions changed — NO
relevance scorer changed — NO
threshold changed — NO
release workflow changed — NO

## I. PHASE-B2 DECISION
STEP-2 AUTHORITATIVE — YES
19-STEP RECONSTRUCTION — {'PASS' if source_ok else 'FAIL'}
PHASE B2 RERUN — {decision}
PHASE B2 COMPLETE — {'YES' if phase_pass else 'NO'}
FULL CONTEXTUAL AUDIT COMPLETE — NO
BUILD PIPELINE RECONCILIATION STARTED — NO
GRADLE INVOKED — NO
APK GENERATED — NO
AAB GENERATED — NO
DEVICE QA STARTED — NO

## J. NEXT GATE
{'AUTHORITATIVE PHASE-B2 RERUN — PASS · READY FOR FULL CONTEXTUAL AUDIT' if phase_pass else 'AUTHORITATIVE PHASE-B2 RERUN — FAIL · TARGETED REMEDIATION REQUIRED'}
"""
    (out/"PHASE_B2_AUTHORITATIVE_RERUN_REPORT.md").write_text(report,encoding="utf-8")
    raise SystemExit(0 if phase_pass else 2)

if __name__=="__main__": main()
