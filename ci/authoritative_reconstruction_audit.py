#!/usr/bin/env python3
from __future__ import annotations
import argparse, csv, hashlib, importlib.util, json, shutil, sys
from pathlib import Path

EXPECTED_MAIN="284a2f1ee75c9b048306c185442ac5bc196d934d"
EXPECTED_CANONICAL="281e5f80a14f80df6ad87427a8e29859050f35430ec3158ad52b84ae607df1a3"
EXPECTED_STEP2_BLOB="1161c46184c3d69530c1cad74db774734b4947af"
EXPECTED_STEP2_SHA256="350054a4c93c0849117c6eefc9da4f4bcbf5841d92970fbe1ab19151681380d3"
EXPECTED_MANIFEST_BLOB="149c2f860f86bb810a98ce6bf1a3bc7a53c7a351"
EXPECTED_FINAL_SHA256="9d4a0e5b6bd23e8fd6cd7e148100bb6017374d2cd9dea45098007eff0c899f65"
EXPECTED_FINAL_SIZE=2389584
EXPECTED_STEPS=19

def sha256_bytes(data:bytes)->str:
    return hashlib.sha256(data).hexdigest()

def sha256_file(path:Path)->str:
    h=hashlib.sha256()
    with path.open("rb") as f:
        for chunk in iter(lambda:f.read(1024*1024),b""):
            h.update(chunk)
    return h.hexdigest()

def git_blob_sha(path:Path)->str:
    data=path.read_bytes()
    return hashlib.sha1(f"blob {len(data)}\0".encode("ascii")+data).hexdigest()

def load_driver(repo_root:Path):
    p=repo_root/"ci/reconstruct_v188_accepted_source.py"
    spec=importlib.util.spec_from_file_location("v188_reconstruct_driver",p)
    if spec is None or spec.loader is None:
        raise RuntimeError("cannot load reconstruction driver")
    mod=importlib.util.module_from_spec(spec)
    spec.loader.exec_module(mod)
    return mod

def tree_manifest(root:Path):
    rows=[]
    for p in sorted(x for x in root.rglob("*") if x.is_file()):
        rel=p.relative_to(root).as_posix()
        rows.append({"path":rel,"size":p.stat().st_size,"sha256":sha256_file(p)})
    text="".join(f'{r["sha256"]}  {r["size"]}  {r["path"]}\n' for r in rows)
    return rows,text,sha256_bytes(text.encode("utf-8"))

def run_one(driver,repo_root:Path,canonical:Path,out:Path,label:str):
    work=out/f"run_{label.lower()}_work"
    if work.exists(): shutil.rmtree(work)
    work.mkdir(parents=True)
    source_root=driver.safe_extract_zip(canonical,work/"extracted")
    matrix=[]
    for step in driver.load_manifest(repo_root)["steps"]:
        before_a,before_b=driver.payload_paths(source_root)
        assert before_a.read_bytes()==before_b.read_bytes()
        row={
            "run":label,
            "step":step["step"],
            "transformation":step["path"],
            "governing_commit":step["governing_commit"],
            "transformation_git_blob":step["git_blob"],
            "mode":step["mode"],
            "input_payload_sha256":sha256_file(before_a),
            "input_payload_size":before_a.stat().st_size,
            "output_payload_sha256":"",
            "output_payload_size":"",
            "status":"FAIL",
            "finding":"",
        }
        try:
            log=driver.execute_step(repo_root,source_root,step,work)
            after_a,after_b=driver.payload_paths(source_root)
            assert after_a.read_bytes()==after_b.read_bytes(),"payload copies diverged"
            row["output_payload_sha256"]=sha256_file(after_a)
            row["output_payload_size"]=after_a.stat().st_size
            row["status"]="PASS"
            row["finding"]=log.strip().replace("\n"," | ")[:1500]
        except BaseException as e:
            row["finding"]=f"{type(e).__name__}: {e}"
            matrix.append(row)
            return {"pass":False,"matrix":matrix,"error":row["finding"]}
        matrix.append(row)

    if len(matrix)!=EXPECTED_STEPS or any(r["status"]!="PASS" for r in matrix):
        return {"pass":False,"matrix":matrix,"error":"19-step completion gate failed"}

    final_a,final_b=driver.payload_paths(source_root)
    assert final_a.read_bytes()==final_b.read_bytes()
    final=out/f"AUTHORITATIVE_RECONSTRUCTED_{label}.html"
    shutil.copyfile(final_a,final)

    tree_rows,tree_text,tree_hash=tree_manifest(source_root)
    (out/f"RECONSTRUCTION_RUN_{label}_TREE_SHA256_MANIFEST.txt").write_text(tree_text,encoding="utf-8")
    return {
        "pass":True,
        "matrix":matrix,
        "source_root":str(source_root),
        "final_path":str(final),
        "final_sha256":sha256_file(final),
        "final_size":final.stat().st_size,
        "tree_rows":tree_rows,
        "tree_manifest_sha256":tree_hash,
    }

def main():
    ap=argparse.ArgumentParser()
    ap.add_argument("--repo-root",required=True)
    ap.add_argument("--out",required=True)
    args=ap.parse_args()
    repo_root=Path(args.repo_root).resolve()
    out=Path(args.out).resolve()
    out.mkdir(parents=True,exist_ok=True)

    driver=load_driver(repo_root)
    state=driver.preflight(repo_root)
    manifest=state["manifest"]
    canonical=state["canonical"]

    # Exact authoritative identities before any transform execution.
    step2=repo_root/"ci/reconcile_v188.py"
    manifest_path=repo_root/"ci/v188_accepted_transform_manifest.json"
    assert git_blob_sha(step2)==EXPECTED_STEP2_BLOB
    assert sha256_file(step2)==EXPECTED_STEP2_SHA256
    assert git_blob_sha(manifest_path)==EXPECTED_MANIFEST_BLOB
    assert len(manifest["steps"])==EXPECTED_STEPS
    excluded=set(manifest.get("superseded_commits",[]))
    assert sum(1 for s in manifest["steps"] if s["governing_commit"] in excluded)==0
    assert sha256_file(canonical)==EXPECTED_CANONICAL
    s2=[s for s in manifest["steps"] if s["step"]==2][0]
    assert s2["git_blob"]==EXPECTED_STEP2_BLOB

    run_a=run_one(driver,repo_root,canonical,out,"A")
    run_b=run_one(driver,repo_root,canonical,out,"B")

    matrix=(run_a.get("matrix") or [])+(run_b.get("matrix") or [])
    fields=["run","step","transformation","governing_commit","transformation_git_blob","mode","input_payload_sha256","input_payload_size","output_payload_sha256","output_payload_size","status","finding"]
    with (out/"RECONSTRUCTION_19_STEP_MATRIX.csv").open("w",encoding="utf-8",newline="") as f:
        w=csv.DictWriter(f,fieldnames=fields);w.writeheader();w.writerows(matrix)

    if not run_a.get("pass") or not run_b.get("pass"):
        audit={"pass":False,"run_a":{k:v for k,v in run_a.items() if k not in ("tree_rows","matrix")},"run_b":{k:v for k,v in run_b.items() if k not in ("tree_rows","matrix")}}
        (out/"RECONSTRUCTION_19_STEP_AUDIT.json").write_text(json.dumps(audit,indent=2),encoding="utf-8")
        raise SystemExit(2)

    amap={r["path"]:(r["size"],r["sha256"]) for r in run_a["tree_rows"]}
    bmap={r["path"]:(r["size"],r["sha256"]) for r in run_b["tree_rows"]}
    only_a=sorted(set(amap)-set(bmap));only_b=sorted(set(bmap)-set(amap))
    differing=sorted(p for p in set(amap)&set(bmap) if amap[p]!=bmap[p])
    tree_det={
        "differing_files":len(differing),
        "only_in_a":len(only_a),
        "only_in_b":len(only_b),
        "differing_paths":differing,
        "only_a_paths":only_a,
        "only_b_paths":only_b,
        "run_a_tree_manifest_sha256":run_a["tree_manifest_sha256"],
        "run_b_tree_manifest_sha256":run_b["tree_manifest_sha256"],
        "pass":not differing and not only_a and not only_b and run_a["tree_manifest_sha256"]==run_b["tree_manifest_sha256"],
    }
    (out/"RECONSTRUCTION_TREE_DETERMINISM.json").write_text(json.dumps(tree_det,indent=2),encoding="utf-8")

    exact=(
        run_a["final_sha256"]==EXPECTED_FINAL_SHA256 and
        run_b["final_sha256"]==EXPECTED_FINAL_SHA256 and
        run_a["final_size"]==EXPECTED_FINAL_SIZE and
        run_b["final_size"]==EXPECTED_FINAL_SIZE and
        tree_det["pass"]
    )
    final_identity="\n".join([
        f"CANONICAL_ZIP_SHA256={sha256_file(canonical)}",
        f"STEP2_GIT_BLOB={git_blob_sha(step2)}",
        f"STEP2_SHA256={sha256_file(step2)}",
        f"MANIFEST_GIT_BLOB={git_blob_sha(manifest_path)}",
        f"TRANSFORM_COUNT={len(manifest['steps'])}",
        "SUPERSEDED_EXECUTABLE_TRANSFORMS=0",
        f"RUN_A_HTML_SHA256={run_a['final_sha256']}",
        f"RUN_A_HTML_SIZE={run_a['final_size']}",
        f"RUN_B_HTML_SHA256={run_b['final_sha256']}",
        f"RUN_B_HTML_SIZE={run_b['final_size']}",
        f"AUDITED_CANDIDATE_SHA256={EXPECTED_FINAL_SHA256}",
        f"AUDITED_CANDIDATE_SIZE={EXPECTED_FINAL_SIZE}",
        f"RUN_A_TREE_MANIFEST_SHA256={run_a['tree_manifest_sha256']}",
        f"RUN_B_TREE_MANIFEST_SHA256={run_b['tree_manifest_sha256']}",
        f"TREE_DIFFERING_FILES={tree_det['differing_files']}",
        f"TREE_ONLY_IN_A={tree_det['only_in_a']}",
        f"TREE_ONLY_IN_B={tree_det['only_in_b']}",
        f"EXACT_AUDITED_CANDIDATE_MATCH={'YES' if exact else 'NO'}",
        ""
    ])
    (out/"RECONSTRUCTION_FINAL_IDENTITY.txt").write_text(final_identity,encoding="utf-8")

    audit={
        "pass":exact,
        "expected_final_sha256":EXPECTED_FINAL_SHA256,
        "expected_final_size":EXPECTED_FINAL_SIZE,
        "run_a":{k:v for k,v in run_a.items() if k not in ("tree_rows","matrix","source_root")},
        "run_b":{k:v for k,v in run_b.items() if k not in ("tree_rows","matrix","source_root")},
        "tree_determinism":tree_det,
        "step2_git_blob":git_blob_sha(step2),
        "step2_sha256":sha256_file(step2),
        "manifest_git_blob":git_blob_sha(manifest_path),
        "transform_count":len(manifest["steps"]),
        "superseded_executable_transforms":0,
    }
    (out/"RECONSTRUCTION_19_STEP_AUDIT.json").write_text(json.dumps(audit,indent=2),encoding="utf-8")
    if not exact:
        raise SystemExit(2)

if __name__=="__main__":
    main()
