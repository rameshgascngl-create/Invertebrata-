#!/usr/bin/env python3
from __future__ import annotations
import argparse, hashlib, importlib.util, json, shutil
from pathlib import Path

EXPECTED_MAIN="284a2f1ee75c9b048306c185442ac5bc196d934d"
EXPECTED_CANONICAL="281e5f80a14f80df6ad87427a8e29859050f35430ec3158ad52b84ae607df1a3"
EXPECTED_STEP2_BLOB="1161c46184c3d69530c1cad74db774734b4947af"
EXPECTED_STEP2_SHA256="350054a4c93c0849117c6eefc9da4f4bcbf5841d92970fbe1ab19151681380d3"
EXPECTED_MANIFEST_BLOB="149c2f860f86bb810a98ce6bf1a3bc7a53c7a351"
EXPECTED_HTML_SHA256="9d4a0e5b6bd23e8fd6cd7e148100bb6017374d2cd9dea45098007eff0c899f65"
EXPECTED_HTML_SIZE=2389584
EXPECTED_TREE_MANIFEST="1009eb7f5179ac0968781d7310a4eae12fc83a792cade67c5e684926c87df6d8"
EXPECTED_CONTEXTUAL_ATLAS="005c2e130b35454fd56b7b5b3ce6295a51adc33dea7b258c312f6f312b4597d2"
EXPECTED_RELEASE_BLOB="8a5a851a17669fc5d7e26c076cce4b2fcd1e04df"

def sha256_file(p:Path)->str:
    h=hashlib.sha256()
    with p.open("rb") as f:
        for c in iter(lambda:f.read(1024*1024),b""): h.update(c)
    return h.hexdigest()

def git_blob(p:Path)->str:
    data=p.read_bytes()
    return hashlib.sha1(f"blob {len(data)}\0".encode()+data).hexdigest()

def load_driver(repo:Path):
    p=repo/"ci/reconstruct_v188_accepted_source.py"
    spec=importlib.util.spec_from_file_location("drv",p)
    mod=importlib.util.module_from_spec(spec);spec.loader.exec_module(mod)
    return mod

def tree_manifest(root:Path):
    rows=[]
    for p in sorted(x for x in root.rglob("*") if x.is_file()):
        rel=p.relative_to(root).as_posix()
        rows.append((sha256_file(p),p.stat().st_size,rel))
    text="".join(f"{h}  {s}  {r}\n" for h,s,r in rows)
    return text,hashlib.sha256(text.encode()).hexdigest()

def main():
    ap=argparse.ArgumentParser()
    ap.add_argument("--repo-root",required=True)
    ap.add_argument("--out",required=True)
    args=ap.parse_args()
    repo=Path(args.repo_root).resolve()
    out=Path(args.out).resolve();out.mkdir(parents=True,exist_ok=True)

    assert git_blob(repo/"ci/reconcile_v188.py")==EXPECTED_STEP2_BLOB
    assert sha256_file(repo/"ci/reconcile_v188.py")==EXPECTED_STEP2_SHA256
    assert git_blob(repo/"ci/v188_accepted_transform_manifest.json")==EXPECTED_MANIFEST_BLOB
    assert git_blob(repo/".github/workflows/invertebrata-v188-debug.yml")==EXPECTED_RELEASE_BLOB

    drv=load_driver(repo)
    state=drv.preflight(repo)
    manifest=state["manifest"]; canonical=state["canonical"]
    assert len(manifest["steps"])==19
    assert sha256_file(canonical)==EXPECTED_CANONICAL
    excluded=set(manifest.get("superseded_commits",[]))
    assert sum(1 for s in manifest["steps"] if s["governing_commit"] in excluded)==0

    work=out/"work"
    if work.exists(): shutil.rmtree(work)
    source_root=drv.safe_extract_zip(canonical,work/"extracted")
    matrix=[]
    for step in manifest["steps"]:
        before,_=drv.payload_paths(source_root)
        inp=sha256_file(before)
        log=drv.execute_step(repo,source_root,step,work)
        after,_=drv.payload_paths(source_root)
        matrix.append({
          "step":step["step"],"path":step["path"],"git_blob":step["git_blob"],
          "input_sha256":inp,"output_sha256":sha256_file(after),
          "output_size":after.stat().st_size,"status":"PASS","log":log.strip()[:1200]
        })

    final,_=drv.payload_paths(source_root)
    authoritative=out/"AUTHORITATIVE_FULL_CONTEXTUAL_AUDIT.html"
    shutil.copyfile(final,authoritative)
    assert sha256_file(authoritative)==EXPECTED_HTML_SHA256
    assert authoritative.stat().st_size==EXPECTED_HTML_SIZE

    tm,tmhash=tree_manifest(source_root)
    (out/"SOURCE_TREE_SHA256_MANIFEST.txt").write_text(tm,encoding="utf-8")
    assert tmhash==EXPECTED_TREE_MANIFEST

    html=authoritative.read_text(encoding="utf-8")
    marker='<script id="v188-contextual-atlas">'
    a=html.index(marker);b=html.index("</script>",a)+len("</script>")
    contextual_hash=hashlib.sha256(html[a:b].encode()).hexdigest()
    assert contextual_hash==EXPECTED_CONTEXTUAL_ATLAS

    record={
      "main_head":EXPECTED_MAIN,
      "canonical_zip_sha256":sha256_file(canonical),
      "step2_git_blob":git_blob(repo/"ci/reconcile_v188.py"),
      "step2_sha256":sha256_file(repo/"ci/reconcile_v188.py"),
      "manifest_git_blob":git_blob(repo/"ci/v188_accepted_transform_manifest.json"),
      "html_sha256":sha256_file(authoritative),
      "html_size":authoritative.stat().st_size,
      "source_tree_manifest_sha256":tmhash,
      "contextual_atlas_sha256":contextual_hash,
      "release_workflow_blob":git_blob(repo/".github/workflows/invertebrata-v188-debug.yml"),
      "transform_count":len(manifest["steps"]),
      "superseded_executable_transforms":0,
      "status":"PASS"
    }
    (out/"FULL_CONTEXTUAL_PREFLIGHT.json").write_text(json.dumps(record,indent=2),encoding="utf-8")
    (out/"RECONSTRUCTION_19_STEPS.json").write_text(json.dumps(matrix,indent=2),encoding="utf-8")

if __name__=="__main__": main()
