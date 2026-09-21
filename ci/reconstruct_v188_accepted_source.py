#!/usr/bin/env python3
"""
INVERTEBRATA v1.8.8 accepted-source reconstruction driver.

Purpose:
- verify the frozen canonical input and the accepted 21-step transform manifest;
- apply accepted transformations exactly once;
- adapt legacy raw-HTML targeted remediations to JSON-serialized
  window.ORG_SYSTEM_DIAGRAMS[...] assignments without changing their accepted markup;
- fail closed on unexpected inputs;
- emit a deterministic reconstructed source candidate.

This driver does NOT run Gradle, dispatch GitHub Actions, sign, or generate an APK.
"""
from __future__ import annotations

import argparse
import csv
import hashlib
import json
import os
import re
import shutil
import subprocess
import sys
import tempfile
import zipfile
from pathlib import Path
from typing import Dict, List, Tuple

CANONICAL_ZIP = "INVERTEBRATA_v1.8.7_RECONCILED_ANDROID_SOURCE.zip"
CANONICAL_ZIP_SHA256 = "281e5f80a14f80df6ad87427a8e29859050f35430ec3158ad52b84ae607df1a3"
MATRIX_REL = Path("provenance/V188_SOURCE_GATE_MATRIX_02.csv")
MANIFEST_REL = Path("ci/v188_accepted_transform_manifest.json")
PAYLOAD_RELS = (
    Path("academic_payload/index.html"),
    Path("app/src/main/assets/www/index.html"),
)
EXPECTED_MATRIX_ROWS = 76

ASSIGN_RE = re.compile(
    r'window\.ORG_SYSTEM_DIAGRAMS\['
    r'(?P<lesson>"(?:\\.|[^"\\])*")'
    r'\]\s*=\s*\('
    r'window\.ORG_SYSTEM_DIAGRAMS\[(?P=lesson)\]'
    r'\s*\|\|\s*\'\'\s*\)\s*\+\s*'
    r'(?P<payload>"(?:\\.|[^"\\])*")\s*;',
    re.S,
)

SURROGATE_BEGIN = "<!-- V188_SERIALIZED_ASSIGNMENT_{:04d}_BEGIN -->"
SURROGATE_END = "<!-- V188_SERIALIZED_ASSIGNMENT_{:04d}_END -->"


def die(msg: str) -> "NoReturn":
    raise SystemExit(msg)


def sha256_bytes(data: bytes) -> str:
    return hashlib.sha256(data).hexdigest()


def sha256_file(path: Path) -> str:
    return sha256_bytes(path.read_bytes())


def git_blob_sha(path: Path) -> str:
    data = path.read_bytes()
    header = f"blob {len(data)}\0".encode("ascii")
    return hashlib.sha1(header + data).hexdigest()


def require(cond: bool, msg: str) -> None:
    if not cond:
        die(msg)


def repo_root_from_script() -> Path:
    return Path(__file__).resolve().parent.parent


def load_manifest(repo_root: Path) -> dict:
    path = repo_root / MANIFEST_REL
    require(path.is_file(), f"manifest missing: {path}")
    obj = json.loads(path.read_text(encoding="utf-8"))
    steps = obj.get("steps")
    require(isinstance(steps, list), "manifest steps must be a list")
    require(len(steps) == 21, f"manifest must contain exactly 21 steps, found {len(steps)}")
    nums = [s.get("step") for s in steps]
    require(nums == list(range(1, 22)), f"manifest step order invalid: {nums}")
    require(len({s.get("path") for s in steps}) == 21, "manifest contains duplicate executable paths")
    excluded = set(obj.get("superseded_commits", []))
    for s in steps:
        require(s.get("accepted") is True, f"step {s.get('step')}: accepted flag is not true")
        require(s.get("superseded") is False, f"step {s.get('step')}: superseded flag is not false")
        require(s.get("governing_commit") not in excluded,
                f"step {s.get('step')}: superseded commit entered executable set")
    return obj


def verify_manifest_files(repo_root: Path, manifest: dict) -> None:
    for step in manifest["steps"]:
        p = repo_root / step["path"]
        require(p.is_file(), f"step {step['step']}: executable missing: {step['path']}")
        observed = git_blob_sha(p)
        expected = step["git_blob"]
        require(observed == expected,
                f"step {step['step']}: Git blob mismatch for {step['path']}: "
                f"expected {expected}, observed {observed}")


def verify_matrix(repo_root: Path) -> None:
    path = repo_root / MATRIX_REL
    require(path.is_file(), f"Matrix 02 missing: {path}")
    with path.open("r", encoding="utf-8", newline="") as f:
        rows = list(csv.DictReader(f))
    require(len(rows) == EXPECTED_MATRIX_ROWS,
            f"Matrix 02 row count must be {EXPECTED_MATRIX_ROWS}, found {len(rows)}")
    ids = [r.get("Requirement ID", "") for r in rows]
    require(all(ids), "Matrix 02 contains empty Requirement ID")
    require(len(set(ids)) == EXPECTED_MATRIX_ROWS, "Matrix 02 contains duplicate Requirement IDs")


def verify_canonical_zip(repo_root: Path) -> Path:
    p = repo_root / CANONICAL_ZIP
    require(p.is_file(), f"canonical ZIP missing: {p}")
    observed = sha256_file(p)
    require(observed == CANONICAL_ZIP_SHA256,
            f"canonical ZIP SHA-256 mismatch: expected {CANONICAL_ZIP_SHA256}, observed {observed}")
    return p


def safe_extract_zip(zip_path: Path, dest: Path) -> Path:
    dest.mkdir(parents=True, exist_ok=True)
    with zipfile.ZipFile(zip_path) as zf:
        names = [n for n in zf.namelist() if n and not n.endswith("/")]
        require(names, "canonical ZIP is empty")
        for name in names:
            q = Path(name)
            require(not q.is_absolute() and ".." not in q.parts, f"unsafe ZIP member: {name}")
        tops = {Path(n).parts[0] for n in names}
        require(len(tops) == 1, f"canonical ZIP must contain one top-level source directory, found {tops}")
        zf.extractall(dest)
    root = dest / next(iter(tops))
    require(root.is_dir(), f"extracted source root missing: {root}")
    return root


def run_process(argv: List[str], cwd: Path | None = None) -> str:
    env = dict(os.environ)
    env["PYTHONHASHSEED"] = "0"
    cp = subprocess.run(
        argv,
        cwd=str(cwd) if cwd else None,
        env=env,
        text=True,
        stdout=subprocess.PIPE,
        stderr=subprocess.STDOUT,
        check=False,
    )
    if cp.returncode != 0:
        die(f"command failed ({cp.returncode}): {' '.join(argv)}\n{cp.stdout}")
    return cp.stdout


def payload_paths(root: Path) -> Tuple[Path, Path]:
    a, b = (root / PAYLOAD_RELS[0], root / PAYLOAD_RELS[1])
    require(a.is_file() and b.is_file(), "academic payload copies are missing")
    require(a.read_bytes() == b.read_bytes(), "academic payload copies diverged before transform")
    return a, b


def parse_assignments(text: str) -> list:
    out = []
    for i, m in enumerate(ASSIGN_RE.finditer(text)):
        try:
            lesson = json.loads(m.group("lesson"))
            payload = json.loads(m.group("payload"))
        except json.JSONDecodeError as e:
            die(f"ORG_SYSTEM_DIAGRAMS assignment {i}: JSON decode failed: {e}")
        out.append({
            "index": i,
            "match": m,
            "lesson": lesson,
            "payload": payload,
        })
    require(out, "no serialized ORG_SYSTEM_DIAGRAMS additive assignments found")
    return out


def make_surrogate(text: str) -> Tuple[str, list]:
    assignments = parse_assignments(text)
    parts = []
    for item in assignments:
        i = item["index"]
        parts.append(SURROGATE_BEGIN.format(i))
        parts.append(item["payload"])
        parts.append(SURROGATE_END.format(i))
    return "\n".join(parts) + "\n", assignments


def parse_surrogate(text: str, count: int) -> Dict[int, str]:
    result: Dict[int, str] = {}
    for i in range(count):
        begin = re.escape(SURROGATE_BEGIN.format(i))
        end = re.escape(SURROGATE_END.format(i))
        m = re.search(begin + r"\n?(.*?)\n?" + end, text, re.S)
        require(m is not None, f"serialized surrogate marker {i} missing after legacy transform")
        result[i] = m.group(1)
    return result


def reserialize_changed_assignments(original: str, assignments: list, modified: Dict[int, str]) -> str:
    chunks: List[str] = []
    cursor = 0
    for item in assignments:
        m = item["match"]
        chunks.append(original[cursor:m.start()])
        new_payload = modified[item["index"]]
        if new_payload == item["payload"]:
            chunks.append(m.group(0))
        else:
            literal = json.dumps(new_payload, ensure_ascii=False)
            whole = m.group(0)
            ps, pe = m.span("payload")
            rel_s, rel_e = ps - m.start(), pe - m.start()
            chunks.append(whole[:rel_s] + literal + whole[rel_e:])
        cursor = m.end()
    chunks.append(original[cursor:])
    return "".join(chunks)


def run_serialized_legacy_adapter(repo_root: Path, source_root: Path, step: dict) -> str:
    a, b = payload_paths(source_root)
    original = a.read_text(encoding="utf-8")
    surrogate, assignments = make_surrogate(original)

    with tempfile.TemporaryDirectory(prefix=f"v188-step-{step['step']:02d}-") as td:
        troot = Path(td)
        sa = troot / PAYLOAD_RELS[0]
        sb = troot / PAYLOAD_RELS[1]
        sa.parent.mkdir(parents=True, exist_ok=True)
        sb.parent.mkdir(parents=True, exist_ok=True)
        sa.write_text(surrogate, encoding="utf-8", newline="\n")
        sb.write_text(surrogate, encoding="utf-8", newline="\n")

        output = run_process([sys.executable, str(repo_root / step["path"]), str(troot)])
        require(sa.read_bytes() == sb.read_bytes(),
                f"step {step['step']}: legacy adapter surrogate payload copies diverged")
        changed_surrogate = sa.read_text(encoding="utf-8")

    modified = parse_surrogate(changed_surrogate, len(assignments))
    changed_count = sum(modified[i] != assignments[i]["payload"] for i in range(len(assignments)))
    require(changed_count > 0, f"step {step['step']}: legacy adapter produced no serialized assignment change")

    rebuilt = reserialize_changed_assignments(original, assignments, modified)
    a.write_text(rebuilt, encoding="utf-8", newline="\n")
    b.write_text(rebuilt, encoding="utf-8", newline="\n")
    require(a.read_bytes() == b.read_bytes(),
            f"step {step['step']}: payload copies diverged after serializer-aware adapter")
    return output


def run_native(repo_root: Path, source_root: Path, step: dict, run_dir: Path) -> str:
    script = repo_root / step["path"]
    if step["mode"] == "native_r2":
        out_zip = run_dir / "R2_RECONCILED_INTERMEDIATE.zip"
        return run_process([sys.executable, str(script), str(source_root), str(out_zip)])
    return run_process([sys.executable, str(script), str(source_root)])


def execute_step(repo_root: Path, source_root: Path, step: dict, run_dir: Path) -> str:
    mode = step["mode"]
    if mode in {"native", "native_r2"}:
        return run_native(repo_root, source_root, step, run_dir)
    if mode == "serialized_surrogate":
        return run_serialized_legacy_adapter(repo_root, source_root, step)
    die(f"step {step['step']}: unknown execution mode {mode}")


def preflight(repo_root: Path) -> dict:
    manifest = load_manifest(repo_root)
    verify_manifest_files(repo_root, manifest)
    verify_matrix(repo_root)
    canonical = verify_canonical_zip(repo_root)
    return {
        "manifest": manifest,
        "canonical": canonical,
    }


def reconstruct(repo_root: Path, work_dir: Path, output: Path) -> None:
    state = preflight(repo_root)
    manifest = state["manifest"]
    canonical = state["canonical"]

    if work_dir.exists():
        shutil.rmtree(work_dir)
    work_dir.mkdir(parents=True)
    source_root = safe_extract_zip(canonical, work_dir / "extracted")

    executed = []
    for step in manifest["steps"]:
        before_a, before_b = payload_paths(source_root)
        before_hash = sha256_file(before_a)
        log = execute_step(repo_root, source_root, step, work_dir)
        after_a, after_b = payload_paths(source_root)
        after_hash = sha256_file(after_a)
        require(before_a.read_bytes() == before_b.read_bytes(), "pre-step payload copies diverged")
        require(after_a.read_bytes() == after_b.read_bytes(), "post-step payload copies diverged")
        executed.append({
            "step": step["step"],
            "path": step["path"],
            "before": before_hash,
            "after": after_hash,
            "stdout": log.strip(),
        })

    require(len(executed) == 21, f"expected 21 executed transforms, found {len(executed)}")
    final_a, final_b = payload_paths(source_root)
    require(final_a.read_bytes() == final_b.read_bytes(), "final academic payload copies diverged")

    output.parent.mkdir(parents=True, exist_ok=True)
    shutil.copyfile(final_a, output)

    print(f"ACCEPTED_TRANSFORMS_EXECUTED={len(executed)}")
    print("SUPERSEDED_TRANSFORMS_EXECUTED=0")
    print(f"AUTHORITATIVE_SOURCE_CANDIDATE={output}")
    print(f"AUTHORITATIVE_SOURCE_CANDIDATE_SIZE={output.stat().st_size}")
    print(f"AUTHORITATIVE_SOURCE_CANDIDATE_SHA256={sha256_file(output)}")


def main() -> None:
    ap = argparse.ArgumentParser()
    ap.add_argument("command", choices=("preflight", "reconstruct"))
    ap.add_argument("--repo-root", type=Path, default=repo_root_from_script())
    ap.add_argument("--work-dir", type=Path)
    ap.add_argument("--output", type=Path)
    args = ap.parse_args()

    repo_root = args.repo_root.resolve()
    if args.command == "preflight":
        preflight(repo_root)
        print("PHASE_B1_PREFLIGHT=PASS")
        print("ACCEPTED_TRANSFORMS_AVAILABLE=21")
        print("SUPERSEDED_EXECUTABLE_TRANSFORMS=0")
        print(f"MATRIX_ROWS={EXPECTED_MATRIX_ROWS}")
        return

    require(args.work_dir is not None, "--work-dir is required for reconstruct")
    require(args.output is not None, "--output is required for reconstruct")
    reconstruct(repo_root, args.work_dir.resolve(), args.output.resolve())


if __name__ == "__main__":
    main()
