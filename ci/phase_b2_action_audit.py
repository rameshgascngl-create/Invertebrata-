#!/usr/bin/env python3
"""Temporary Phase-B2 GitHub Actions audit harness.

Audit infrastructure only. It imports the persisted Phase-B1 reconstruction
driver and executes the accepted 19-step chain without changing academic
content, release workflows, Gradle configuration, versioning, or Android build
state.
"""
from __future__ import annotations

import argparse
import csv
import hashlib
import html.parser
import importlib.util
import json
import os
import re
import shutil
import subprocess
import sys
from collections import Counter, defaultdict
from pathlib import Path

BASELINE = "04f556ca3df937583c2d2f54619225cfeba97b23"
EXPECTED_RELEASE_WORKFLOW_BLOB = "8a5a851a17669fc5d7e26c076cce4b2fcd1e04df"
EXPECTED_ZIP_BLOB = "4bbb361886a8b1f20ba7ed47677ba54325dc45bc"
EXPECTED_ZIP_SHA256 = "281e5f80a14f80df6ad87427a8e29859050f35430ec3158ad52b84ae607df1a3"
EXPECTED_DRIVER_BLOB = "96ee2ade682dbbe747f4a88aa27b3b9333d00488"
EXPECTED_MANIFEST_BLOB = "485336a8f8f018441fe6ef75fb36ee2296317665"
EXPECTED_MATRIX_BLOB = "958c8bacd91600213b64bb591027eafaf118a7f8"
EXPECTED_ROWS = 76

AUDIT_ALLOWED_DIFFS = {
    ".github/workflows/phase-b2-reconstruction-audit.yml",
    "ci/phase_b2_action_audit.py",
}

BATCH3_PLATE_EXCEPTIONS = {
    "arthropod-pest-ipm": "pest-ipm",
    "molluscan-body-plan": "mollusc-plan",
}
BATCH2_PLATE_EXCEPTIONS = {
    "fasciola_lifecycle": "fasciola-life-cycle",
    "obelia-zooids-left": "obelia-zooids",
    "obelia-zooids-right": "obelia-zooids",
}
PEN03_IDS = [
    "PENAEUS-03-OVERVIEW",
    "PENAEUS-03-CEPHALIC",
    "PENAEUS-03-THORACIC",
    "PENAEUS-03-ABDOMINAL",
]

LESSON_BY_REQ = {
    "PAR-01": "u1-paramecium", "PAR-02": "u1-paramecium",
    "AMO-01": "u1-protozoa",
    "ENTA-01": "u1-proto-parasites", "TRYP-01": "u1-proto-parasites", "LEISH-01": "u1-proto-parasites",
    "PLASM-01": "u1-host-parasite",
    "SYC-01": "u2-sycon", "SYC-02": "u2-sycon", "SYC-03": "u2-sycon", "SYC-04": "u2-sycon",
    "POR-01": "u2-porifera",
    "OBE-01": "u2-obelia", "OBE-02": "u2-obelia", "OBE-03": "u2-obelia", "OBE-04": "u2-obelia", "OBE-05": "u2-obelia",
    "AUR-01": "u2-cnidaria", "PHY-01": "u2-cnidaria", "COR-01": "u2-coral-economics",
    "FAS-01": "u3-fasciola", "FAS-02": "u3-fasciola", "FAS-03": "u3-fasciola", "FAS-04": "u3-fasciola", "FAS-05": "u3-fasciola",
    "TAE-01": "u3-platy", "TAE-02": "u3-platy-adapt",
    "ASC-01": "u3-ascaris", "ASC-02": "u3-ascaris", "ASC-03": "u3-ascaris", "ASC-04": "u3-ascaris", "ASC-05": "u3-ascaris", "ASC-06": "u3-ascaris",
    "NEM-01": "u3-nematode-adapt",
    "EWT-01": "u4-annelida", "EWT-02": "u4-annelida", "EWT-03": "u4-annelida", "EWT-04": "u4-annelida", "EWT-05": "u4-annelida", "EWT-06": "u4-annelida",
    "NER-01": "u4-nereis", "NER-02": "u4-nereis", "NER-03": "u4-nereis", "NER-04": "u4-nereis", "NER-05": "u4-nereis", "NER-06": "u4-nereis", "NER-07": "u4-nereis",
    "PEN-01": "u4-penaeus", "PEN-02": "u4-penaeus", "PEN-03": "u4-penaeus", "PEN-04": "u4-penaeus", "PEN-05": "u4-penaeus", "PEN-06": "u4-penaeus", "PEN-07": "u4-penaeus", "PEN-08": "u4-penaeus", "PEN-09": "u4-penaeus",
    "PERI-01": "u4-modes-life", "CRUST-01": "u4-penaeus", "IPM-01": "u4-arthropoda",
    "PILA-01": "u5-pila", "PILA-02": "u5-pila", "PILA-03": "u5-pila", "PILA-04": "u5-pila", "PILA-05": "u5-pila", "PILA-06": "u5-pila", "PILA-07": "u5-pila",
    "MOL-01": "u5-mollusca", "TOR-01": "u5-pila", "CEPH-01": "u5-cephalopods",
    "AST-01": "u5-asterias", "AST-02": "u5-asterias", "AST-03": "u5-asterias", "AST-04": "u5-asterias", "AST-05": "u5-asterias", "AST-06": "u5-asterias",
    "ECHL-01": "u5-echinodermata",
}

KNOWN_REJECTED_FIGURE_TITLES = [
    "Earthworm — major internal systems (schematic L.S.)",
    "Fasciola hepatica — hermaphrodite reproductive system",
    "Ascaris lumbricoides — male and female reproductive systems",
]

OBSOLETE_MAPPING_PATTERNS = [
    r"Obelia.{0,100}\bp\.?\s*17\b",
    r"Nereis.{0,100}\bp\.?\s*30\b",
    r"Pila.{0,100}\bp\.?\s*42\b",
    r"Asterias.{0,100}\bp\.?\s*45(?:\s*[–-]\s*46)?\b",
]

RESOURCE_RE = re.compile(
    r"<(?:script|img|iframe|link|source)\b[^>]*(?:src|href)\s*=\s*['\"](https?://[^'\"]+)",
    re.I,
)
ID_RE = re.compile(r"\bid\s*=\s*['\"]([^'\"]+)['\"]", re.I)
PLATE_RE = re.compile(r"\bdata-v188-plate\s*=\s*['\"]([^'\"]+)['\"]", re.I)
REF_RE = re.compile(r"url\(\s*#([^\s)]+)\s*\)|(?:href|xlink:href)\s*=\s*['\"]#([^'\"]+)['\"]", re.I)


def sha256_file(path: Path) -> str:
    h = hashlib.sha256()
    with path.open("rb") as f:
        for chunk in iter(lambda: f.read(1024 * 1024), b""):
            h.update(chunk)
    return h.hexdigest()


def git(*args: str, cwd: Path) -> str:
    cp = subprocess.run(["git", *args], cwd=cwd, text=True, stdout=subprocess.PIPE, stderr=subprocess.STDOUT)
    if cp.returncode != 0:
        raise RuntimeError(f"git {' '.join(args)} failed ({cp.returncode}):\n{cp.stdout}")
    return cp.stdout.strip()


def import_driver(repo: Path):
    p = repo / "ci/reconstruct_v188_accepted_source.py"
    spec = importlib.util.spec_from_file_location("v188_driver", p)
    if spec is None or spec.loader is None:
        raise RuntimeError("cannot load persisted reconstruction driver")
    mod = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(mod)
    return mod


def hash_tree(root: Path) -> dict[str, str]:
    result = {}
    for p in sorted(x for x in root.rglob("*") if x.is_file()):
        rel = p.relative_to(root).as_posix()
        result[rel] = sha256_file(p)
    return result


def write_hash_manifest(hashes: dict[str, str], path: Path) -> None:
    path.write_text("".join(f"{h}  {p}\n" for p, h in sorted(hashes.items())), encoding="utf-8")


def expected_plate_ids(row: dict[str, str]) -> list[str]:
    rid = row["Requirement ID"]
    cand = row["Active Candidate"].strip()
    if rid == "PEN-03":
        return PEN03_IDS[:]
    if cand.startswith("batch3#"):
        raw = cand.split("#", 1)[1].split(" +", 1)[0].strip()
        return [BATCH3_PLATE_EXCEPTIONS.get(raw, raw)]
    if cand.startswith("batch2#"):
        raw = cand.split("#", 1)[1].split(" +", 1)[0].strip()
        raw = BATCH2_PLATE_EXCEPTIONS.get(raw, raw.replace("_", "-"))
        return [raw]
    raw = cand.split(" +", 1)[0].strip()
    if raw.endswith(" child series"):
        raise RuntimeError(f"unhandled child-series candidate: {rid}: {cand}")
    return [raw]


def figure_sections(assignments: list[dict]) -> tuple[Counter, dict[str, set[str]], dict[str, str]]:
    counts: Counter = Counter()
    lessons: dict[str, set[str]] = defaultdict(set)
    snippets: dict[str, str] = {}
    for item in assignments:
        lesson = item["lesson"]
        payload = item["payload"]
        for m in re.finditer(r"<figure\b[^>]*data-v188-plate=['\"]([^'\"]+)['\"][^>]*>[\s\S]*?</figure>", payload, re.I):
            pid = m.group(1)
            counts[pid] += 1
            lessons[pid].add(lesson)
            snippets[pid] = m.group(0)
    return counts, lessons, snippets


def run_matrix_regression(repo: Path, final_html: str, driver, out_csv: Path) -> tuple[int, list[str]]:
    matrix = repo / "provenance/V188_SOURCE_GATE_MATRIX_02.csv"
    with matrix.open("r", encoding="utf-8", newline="") as f:
        rows = list(csv.DictReader(f))
    if len(rows) != EXPECTED_ROWS:
        raise RuntimeError(f"Matrix 02 row count {len(rows)} != {EXPECTED_ROWS}")

    assignments = driver.parse_assignments(final_html)
    counts, lessons, snippets = figure_sections(assignments)

    fields = [
        "Requirement ID", "Organism", "Figure/System", "Active Candidate",
        "Governing Transformation", "Expected Materialization", "Expected Lesson",
        "Presence", "Duplication Check", "Provenance Check", "Instructional Marker Check",
        "Result", "Exact Finding",
    ]
    failed = []
    with out_csv.open("w", encoding="utf-8", newline="") as f:
        w = csv.DictWriter(f, fieldnames=fields)
        w.writeheader()
        for row in rows:
            rid = row["Requirement ID"]
            ids = expected_plate_ids(row)
            expected_lesson = LESSON_BY_REQ.get(rid)
            if not expected_lesson:
                raise RuntimeError(f"no expected lesson mapping for {rid}")
            observed_counts = {pid: counts.get(pid, 0) for pid in ids}
            presence_ok = all(v == 1 for v in observed_counts.values())
            dup_ok = all(v <= 1 for v in observed_counts.values())
            prov_detail = {pid: sorted(lessons.get(pid, set())) for pid in ids}
            prov_ok = all(lessons.get(pid, set()) == {expected_lesson} for pid in ids)

            marker_ok = True
            marker_note = "not separately required"
            cand = row["Active Candidate"]
            if cand.startswith("batch3#"):
                marker_ok = all(
                    'data-source-pass2-labels="1"' in snippets.get(pid, "")
                    and "v188-pass2-callouts" in snippets.get(pid, "")
                    and "v188-pass2-legend" in snippets.get(pid, "")
                    for pid in ids
                )
                marker_note = "Batch-3 visible Pass-2 callouts/legend present" if marker_ok else "Batch-3 visible Pass-2 marker/callout/legend missing"
            elif "Pass2" in cand and rid != "SYC-04":
                marker_ok = all('data-source-pass2="1"' in snippets.get(pid, "") for pid in ids)
                marker_note = "Pass-2 system marker present" if marker_ok else "Pass-2 system marker missing"
            elif rid == "SYC-04":
                s = snippets.get(ids[0], "")
                old_sentence = "Water: ostia → incurrent canal → prosopyle → radial canal → apopyle → spongocoel → osculum"
                marker_ok = old_sentence not in s
                marker_note = "old narrow-width in-SVG prose absent" if marker_ok else "old narrow-width in-SVG prose restored"

            ok = presence_ok and dup_ok and prov_ok and marker_ok
            if not ok:
                failed.append(rid)
            governing = row.get("Evidence/Exact Finding", "")
            w.writerow({
                "Requirement ID": rid,
                "Organism": row["Organism"],
                "Figure/System": row["Figure/System"],
                "Active Candidate": cand,
                "Governing Transformation": governing,
                "Expected Materialization": ";".join(ids),
                "Expected Lesson": expected_lesson,
                "Presence": json.dumps(observed_counts, sort_keys=True),
                "Duplication Check": "PASS" if dup_ok else "FAIL",
                "Provenance Check": ("PASS " + json.dumps(prov_detail, sort_keys=True)) if prov_ok else ("FAIL " + json.dumps(prov_detail, sort_keys=True)),
                "Instructional Marker Check": ("PASS " if marker_ok else "FAIL ") + marker_note,
                "Result": "PASS" if ok else "FAIL",
                "Exact Finding": "accepted materialization present exactly once in expected lesson" if ok else "one or more reconstruction regression checks failed",
            })
    return len(rows) - len(failed), failed


def integrity_audit(repo: Path, source_root: Path, final_html: str, driver, out_json: Path) -> dict:
    assignments = driver.parse_assignments(final_html)
    decoded_payload = "\n".join(item["payload"] for item in assignments)

    # Top-level id= attributes are unescaped; serialized payload id= quotes are JSON-escaped.
    top_ids = ID_RE.findall(final_html)
    payload_ids = ID_RE.findall(decoded_payload)
    ids = top_ids + payload_ids
    dup_ids = sorted(k for k, v in Counter(ids).items() if v > 1)

    refs = []
    for text in (final_html, decoded_payload):
        for m in REF_RE.finditer(text):
            refs.append(m.group(1) or m.group(2))
    idset = set(ids)
    broken_refs = sorted(set(r for r in refs if r not in idset))

    plates = PLATE_RE.findall(decoded_payload)
    duplicate_plates = sorted(k for k, v in Counter(plates).items() if v > 1)

    remotes = []
    for text in (final_html, decoded_payload):
        for url in RESOURCE_RE.findall(text):
            if "appassets.androidplatform.net" not in url:
                remotes.append(url)
    remotes = sorted(set(remotes))

    rejected_restored = [title for title in KNOWN_REJECTED_FIGURE_TITLES if title in decoded_payload]
    obsolete_mapping_hits = []
    for pat in OBSOLETE_MAPPING_PATTERNS:
        if re.search(pat, final_html, re.I | re.S):
            obsolete_mapping_hits.append(pat)

    changed = set(git("diff", "--name-only", f"{BASELINE}..HEAD", cwd=repo).splitlines())
    unexpected_repo_drift = sorted(changed - AUDIT_ALLOWED_DIFFS)

    release_blob = git("hash-object", ".github/workflows/invertebrata-v188-debug.yml", cwd=repo)
    zip_blob = git("hash-object", "INVERTEBRATA_v1.8.7_RECONCILED_ANDROID_SOURCE.zip", cwd=repo)

    # Core accepted state regression guards.
    required_text = {
        "earthworm_limited_scope": "earthworm-reproductive-r2",
        "penaeus_petasma": "Petasma",
        "penaeus_thelycum": "Thelycum",
        "obelia_corrected_colony": "OBELIA-01-R1",
        "obelia_corrected_medusa": "OBELIA-02-R1",
        "obelia_corrected_lifecycle": "OBELIA-03-R1",
        "nereis_excretory_r2": "NEREIS-06-R2",
        "pila_excretory": "PILA-EXC-N1",
        "asterias_wvs": "master-asterias-wvs",
        "fasciola_reproductive": "FASCIOLA-REP-R1",
        "ascaris_female": "ASCARIS-FEMALE-REP-R1",
        "ascaris_male": "ASCARIS-MALE-REP-R1",
        "paramecium_cv_cilia": "PARAMECIUM-CV-CILIA-N1",
    }
    missing_guards = [k for k, token in required_text.items() if token not in decoded_payload]

    report = {
        "accepted_transformations_expected": 19,
        "superseded_transformations_executed": 0,
        "release_workflow_blob": release_blob,
        "release_workflow_blob_expected": EXPECTED_RELEASE_WORKFLOW_BLOB,
        "release_workflow_drift": 0 if release_blob == EXPECTED_RELEASE_WORKFLOW_BLOB else 1,
        "canonical_zip_blob": zip_blob,
        "canonical_zip_blob_expected": EXPECTED_ZIP_BLOB,
        "canonical_zip_blob_match": zip_blob == EXPECTED_ZIP_BLOB,
        "duplicate_dom_svg_ids_count": len(dup_ids),
        "duplicate_dom_svg_ids": dup_ids,
        "broken_internal_svg_refs_count": len(broken_refs),
        "broken_internal_svg_refs": broken_refs,
        "duplicate_production_plate_ids_count": len(duplicate_plates),
        "duplicate_production_plate_ids": duplicate_plates,
        "remote_resource_dependencies_count": len(remotes),
        "remote_resource_dependencies": remotes,
        "rejected_or_superseded_plate_restoration_count": len(rejected_restored),
        "rejected_or_superseded_plate_restoration": rejected_restored,
        "obsolete_page_mapping_restoration_count": len(obsolete_mapping_hits),
        "obsolete_page_mapping_patterns": obsolete_mapping_hits,
        "unexpected_repository_drift_count": len(unexpected_repo_drift),
        "unexpected_repository_drift": unexpected_repo_drift,
        "accepted_state_guard_failures_count": len(missing_guards),
        "accepted_state_guard_failures": missing_guards,
        "org_system_diagram_assignments_decoded": len(assignments),
        "tree_files": len(hash_tree(source_root)),
    }
    report["pass"] = all([
        report["release_workflow_drift"] == 0,
        report["canonical_zip_blob_match"],
        report["duplicate_dom_svg_ids_count"] == 0,
        report["broken_internal_svg_refs_count"] == 0,
        report["duplicate_production_plate_ids_count"] == 0,
        report["remote_resource_dependencies_count"] == 0,
        report["rejected_or_superseded_plate_restoration_count"] == 0,
        report["obsolete_page_mapping_restoration_count"] == 0,
        report["unexpected_repository_drift_count"] == 0,
        report["accepted_state_guard_failures_count"] == 0,
    ])
    out_json.write_text(json.dumps(report, ensure_ascii=False, indent=2, sort_keys=True) + "\n", encoding="utf-8")
    return report


def run_once(repo: Path, label: str, out: Path) -> int:
    out.mkdir(parents=True, exist_ok=True)
    driver = import_driver(repo)

    state = driver.preflight(repo)
    manifest = state["manifest"]
    canonical = state["canonical"]

    zip_blob = git("hash-object", str(canonical.relative_to(repo)), cwd=repo)
    if zip_blob != EXPECTED_ZIP_BLOB:
        raise RuntimeError(f"canonical ZIP Git blob mismatch: {zip_blob}")
    actual_zip_sha = sha256_file(canonical)
    if actual_zip_sha != EXPECTED_ZIP_SHA256:
        raise RuntimeError(f"canonical ZIP SHA-256 mismatch: {actual_zip_sha}")

    work = out / "work"
    if work.exists():
        shutil.rmtree(work)
    work.mkdir()
    source_root = driver.safe_extract_zip(canonical, work / "extracted")

    log_path = out / f"RUN_{label}_19_STEP_EXECUTION_LOG.jsonl"
    step_records = []
    with log_path.open("w", encoding="utf-8") as logf:
        for step in manifest["steps"]:
            before = hash_tree(source_root)
            record = {
                "step": step["step"],
                "script": step["path"],
                "governing_commit": step["governing_commit"],
                "git_blob": step["git_blob"],
                "mode": step["mode"],
                "exit_code": None,
                "warnings_or_output": "",
                "changed_files": [],
            }
            try:
                output = driver.execute_step(repo, source_root, step, work)
                record["exit_code"] = 0
                record["warnings_or_output"] = output
            except BaseException as e:
                record["exit_code"] = 1
                record["warnings_or_output"] = repr(e)
                after = hash_tree(source_root)
                record["changed_files"] = sorted(set(before) | set(after))
                logf.write(json.dumps(record, ensure_ascii=False) + "\n")
                logf.flush()
                raise
            after = hash_tree(source_root)
            changed = sorted(p for p in set(before) | set(after) if before.get(p) != after.get(p))
            record["changed_files"] = changed
            step_records.append(record)
            logf.write(json.dumps(record, ensure_ascii=False) + "\n")
            logf.flush()

    if len(step_records) != 19 or [r["step"] for r in step_records] != list(range(1, 20)):
        raise RuntimeError("19-step execution ledger incomplete or out of order")

    a, b = driver.payload_paths(source_root)
    if a.read_bytes() != b.read_bytes():
        raise RuntimeError("final payload copies diverged")
    final_html = a.read_text(encoding="utf-8")

    authoritative = out / "AUTHORITATIVE_RECONSTRUCTED_SOURCE.html"
    shutil.copyfile(a, authoritative)

    hashes = hash_tree(source_root)
    hash_manifest = out / f"RUN_{label}_SHA256_MANIFEST.txt"
    write_hash_manifest(hashes, hash_manifest)

    matrix_report = out / f"RUN_{label}_MATRIX02_RECONSTRUCTION.csv"
    matrix_pass, failed_rows = run_matrix_regression(repo, final_html, driver, matrix_report)

    integrity_path = out / f"RUN_{label}_FULL_SOURCE_INTEGRITY.json"
    integrity = integrity_audit(repo, source_root, final_html, driver, integrity_path)
    integrity["accepted_transformations_executed"] = 19
    integrity["accepted_transformations_exactly_once"] = True
    integrity["skipped_transformations"] = 0
    integrity["duplicate_transformations"] = 0
    integrity["matrix_pass_count"] = matrix_pass
    integrity["matrix_fail_count"] = len(failed_rows)
    integrity["matrix_failed_row_ids"] = failed_rows
    integrity_path.write_text(json.dumps(integrity, ensure_ascii=False, indent=2, sort_keys=True) + "\n", encoding="utf-8")

    summary = {
        "run": label,
        "canonical_zip_sha256": actual_zip_sha,
        "canonical_zip_git_blob": zip_blob,
        "accepted_transforms_executed": 19,
        "superseded_transforms_executed": 0,
        "source_tree_files": len(hashes),
        "source_tree_manifest_sha256": sha256_file(hash_manifest),
        "authoritative_html_sha256": sha256_file(authoritative),
        "authoritative_html_size": authoritative.stat().st_size,
        "matrix_pass": matrix_pass,
        "matrix_failed": failed_rows,
        "full_source_integrity_pass": integrity["pass"],
    }
    (out / f"RUN_{label}_SUMMARY.json").write_text(
        json.dumps(summary, indent=2, sort_keys=True) + "\n", encoding="utf-8"
    )

    print(json.dumps(summary, sort_keys=True))
    if failed_rows or not integrity["pass"]:
        return 2
    return 0


def read_manifest(path: Path) -> dict[str, str]:
    out = {}
    for line in path.read_text(encoding="utf-8").splitlines():
        if not line.strip():
            continue
        h, rel = line.split("  ", 1)
        out[rel] = h
    return out


def compare_runs(a_dir: Path, b_dir: Path, out_dir: Path) -> int:
    out_dir.mkdir(parents=True, exist_ok=True)
    a = read_manifest(a_dir / "RUN_A_SHA256_MANIFEST.txt")
    b = read_manifest(b_dir / "RUN_B_SHA256_MANIFEST.txt")
    paths = sorted(set(a) | set(b))
    differing = [p for p in paths if p in a and p in b and a[p] != b[p]]
    only_a = [p for p in paths if p in a and p not in b]
    only_b = [p for p in paths if p in b and p not in a]
    identical = [p for p in paths if p in a and p in b and a[p] == b[p]]

    ma = a_dir / "RUN_A_MATRIX02_RECONSTRUCTION.csv"
    mb = b_dir / "RUN_B_MATRIX02_RECONSTRUCTION.csv"
    matrix_equal = ma.read_bytes() == mb.read_bytes()
    ia = json.loads((a_dir / "RUN_A_FULL_SOURCE_INTEGRITY.json").read_text(encoding="utf-8"))
    ib = json.loads((b_dir / "RUN_B_FULL_SOURCE_INTEGRITY.json").read_text(encoding="utf-8"))
    matrix_a_pass = ia.get("matrix_pass_count") == 76 and ia.get("matrix_fail_count") == 0
    matrix_b_pass = ib.get("matrix_pass_count") == 76 and ib.get("matrix_fail_count") == 0

    report = {
        "files_in_run_a": len(a),
        "files_in_run_b": len(b),
        "files_compared": len(paths),
        "identical": len(identical),
        "differing": len(differing),
        "only_in_a": len(only_a),
        "only_in_b": len(only_b),
        "differing_paths": differing,
        "only_in_a_paths": only_a,
        "only_in_b_paths": only_b,
        "manifest_equality": not differing and not only_a and not only_b,
        "matrix_reports_byte_identical": matrix_equal,
        "matrix_run_a_76_of_76": matrix_a_pass,
        "matrix_run_b_76_of_76": matrix_b_pass,
        "integrity_run_a_pass": bool(ia.get("pass")),
        "integrity_run_b_pass": bool(ib.get("pass")),
    }
    report["phase_b2_pass"] = all([
        report["manifest_equality"],
        report["matrix_reports_byte_identical"],
        report["matrix_run_a_76_of_76"],
        report["matrix_run_b_76_of_76"],
        report["integrity_run_a_pass"],
        report["integrity_run_b_pass"],
    ])
    (out_dir / "DETERMINISM_COMPARISON.json").write_text(
        json.dumps(report, indent=2, sort_keys=True) + "\n", encoding="utf-8"
    )
    (out_dir / "DETERMINISM_COMPARISON.txt").write_text(
        "\n".join([
            f"FILES_IN_RUN_A={len(a)}",
            f"FILES_IN_RUN_B={len(b)}",
            f"FILES_COMPARED={len(paths)}",
            f"IDENTICAL={len(identical)}",
            f"DIFFERING={len(differing)}",
            f"ONLY_IN_A={len(only_a)}",
            f"ONLY_IN_B={len(only_b)}",
            f"MANIFEST_EQUALITY={'PASS' if report['manifest_equality'] else 'FAIL'}",
            f"MATRIX_A_76_OF_76={'PASS' if matrix_a_pass else 'FAIL'}",
            f"MATRIX_B_76_OF_76={'PASS' if matrix_b_pass else 'FAIL'}",
            f"FULL_SOURCE_INTEGRITY_A={'PASS' if ia.get('pass') else 'FAIL'}",
            f"FULL_SOURCE_INTEGRITY_B={'PASS' if ib.get('pass') else 'FAIL'}",
            f"PHASE_B2={'PASS' if report['phase_b2_pass'] else 'FAIL'}",
            "",
        ]),
        encoding="utf-8",
    )
    print(json.dumps(report, sort_keys=True))
    return 0 if report["phase_b2_pass"] else 3


def preflight(repo: Path, out: Path) -> int:
    out.mkdir(parents=True, exist_ok=True)
    driver = import_driver(repo)
    state = driver.preflight(repo)

    head = git("rev-parse", "HEAD", cwd=repo)
    if not git("merge-base", "--is-ancestor", BASELINE, head, cwd=repo) == "":
        # git merge-base --is-ancestor has no stdout; successful command is enough.
        pass
    driver_blob = git("hash-object", "ci/reconstruct_v188_accepted_source.py", cwd=repo)
    manifest_blob = git("hash-object", "ci/v188_accepted_transform_manifest.json", cwd=repo)
    matrix_blob = git("hash-object", "provenance/V188_SOURCE_GATE_MATRIX_02.csv", cwd=repo)
    release_blob = git("hash-object", ".github/workflows/invertebrata-v188-debug.yml", cwd=repo)
    zip_blob = git("hash-object", "INVERTEBRATA_v1.8.7_RECONCILED_ANDROID_SOURCE.zip", cwd=repo)
    zip_sha = sha256_file(repo / "INVERTEBRATA_v1.8.7_RECONCILED_ANDROID_SOURCE.zip")
    branch = os.environ.get("GITHUB_REF_NAME", "")
    changed = set(git("diff", "--name-only", f"{BASELINE}..HEAD", cwd=repo).splitlines())
    unexpected = sorted(changed - AUDIT_ALLOWED_DIFFS)

    manifest = state["manifest"]
    record = {
        "head": head,
        "baseline": BASELINE,
        "branch": branch,
        "driver_blob": driver_blob,
        "driver_blob_expected": EXPECTED_DRIVER_BLOB,
        "manifest_blob": manifest_blob,
        "manifest_blob_expected": EXPECTED_MANIFEST_BLOB,
        "matrix_blob": matrix_blob,
        "matrix_blob_expected": EXPECTED_MATRIX_BLOB,
        "release_workflow_blob": release_blob,
        "release_workflow_blob_expected": EXPECTED_RELEASE_WORKFLOW_BLOB,
        "canonical_zip_blob": zip_blob,
        "canonical_zip_blob_expected": EXPECTED_ZIP_BLOB,
        "canonical_zip_sha256": zip_sha,
        "canonical_zip_sha256_expected": EXPECTED_ZIP_SHA256,
        "accepted_steps": len(manifest["steps"]),
        "step_order": [s["step"] for s in manifest["steps"]],
        "superseded_in_executable_set": sum(1 for s in manifest["steps"] if s["governing_commit"] in manifest.get("superseded_commits", [])),
        "matrix_rows": EXPECTED_ROWS,
        "unexpected_repo_drift": unexpected,
    }
    checks = [
        branch == "audit/phase-b2-reconstruction-20260918",
        driver_blob == EXPECTED_DRIVER_BLOB,
        manifest_blob == EXPECTED_MANIFEST_BLOB,
        matrix_blob == EXPECTED_MATRIX_BLOB,
        release_blob == EXPECTED_RELEASE_WORKFLOW_BLOB,
        zip_blob == EXPECTED_ZIP_BLOB,
        zip_sha == EXPECTED_ZIP_SHA256,
        len(manifest["steps"]) == 19,
        [s["step"] for s in manifest["steps"]] == list(range(1, 20)),
        record["superseded_in_executable_set"] == 0,
        not unexpected,
    ]
    record["pass"] = all(checks)
    (out / "CANONICAL_ZIP_SHA256_VERIFICATION.json").write_text(
        json.dumps(record, indent=2, sort_keys=True) + "\n", encoding="utf-8"
    )
    print(json.dumps(record, sort_keys=True))
    return 0 if record["pass"] else 4



def diagnose_step4_url(repo: Path, out: Path) -> int:
    """Reconstruct only through accepted Step 3, then enumerate URL-like strings
    in the exact payload that Step 4 receives. No source mutation is persisted.
    """
    out.mkdir(parents=True, exist_ok=True)
    driver = import_driver(repo)
    state = driver.preflight(repo)
    manifest = state["manifest"]
    canonical = state["canonical"]

    actual_zip_sha = sha256_file(canonical)
    zip_blob = git("hash-object", str(canonical.relative_to(repo)), cwd=repo)
    if actual_zip_sha != EXPECTED_ZIP_SHA256:
        raise RuntimeError(f"canonical ZIP SHA-256 mismatch: {actual_zip_sha}")
    if zip_blob != EXPECTED_ZIP_BLOB:
        raise RuntimeError(f"canonical ZIP Git blob mismatch: {zip_blob}")

    work = out / "work"
    if work.exists():
        shutil.rmtree(work)
    work.mkdir()
    source_root = driver.safe_extract_zip(canonical, work / "extracted")

    step_log = []
    for step in manifest["steps"][:3]:
        output = driver.execute_step(repo, source_root, step, work)
        step_log.append({
            "step": step["step"],
            "script": step["path"],
            "governing_commit": step["governing_commit"],
            "git_blob": step["git_blob"],
            "output": output,
        })

    a, b = driver.payload_paths(source_root)
    if a.read_bytes() != b.read_bytes():
        raise RuntimeError("payload copies diverged after Step 3")
    payload = a.read_text(encoding="utf-8")

    token_re = re.compile(r'https?://[^\\s"\'<>]+')
    hits = []
    for m in token_re.finditer(payload):
        start = max(0, m.start() - 180)
        end = min(len(payload), m.end() + 180)
        context = payload[start:end].replace("\n", "\\n")
        hits.append({
            "scheme": "https" if m.group(0).startswith("https://") else "http",
            "value": m.group(0),
            "offset": m.start(),
            "context": context,
        })

    # Also record generic occurrences even if punctuation made the URL regex stop early.
    raw_https_offsets = [m.start() for m in re.finditer("https://", payload)]
    raw_http_offsets = [m.start() for m in re.finditer("http://", payload)]

    # Classify HTML resource-loading constructs separately from mere strings.
    remote_loads = []
    load_pat = re.compile(
        r'<(?:script|img|iframe|link|source)\b[^>]*(?:src|href)\s*=\s*["\'](https?://[^"\']+)["\']',
        re.I,
    )
    for m in load_pat.finditer(payload):
        remote_loads.append({"url": m.group(1), "context": m.group(0)})

    js_network = []
    js_pat = re.compile(
        r'\b(?:fetch|XMLHttpRequest|WebSocket)\s*\([^\n]{0,300}?https?://[^\s"\'<>]+',
        re.I,
    )
    for m in js_pat.finditer(payload):
        js_network.append(m.group(0))

    report = {
        "canonical_zip_sha256": actual_zip_sha,
        "canonical_zip_git_blob": zip_blob,
        "steps_executed": [x["step"] for x in step_log],
        "step_log": step_log,
        "payload_sha256_after_step3": sha256_file(a),
        "payload_size_after_step3": a.stat().st_size,
        "raw_https_occurrences": len(raw_https_offsets),
        "raw_http_occurrences": len(raw_http_offsets),
        "url_like_hits": hits,
        "remote_resource_loads": remote_loads,
        "javascript_network_calls": js_network,
    }
    (out / "STEP3_URL_DIAGNOSTIC.json").write_text(
        json.dumps(report, ensure_ascii=False, indent=2, sort_keys=True) + "\n",
        encoding="utf-8",
    )
    with (out / "STEP3_URL_DIAGNOSTIC.txt").open("w", encoding="utf-8") as fh:
        fh.write(f"PAYLOAD_SHA256_AFTER_STEP3={report['payload_sha256_after_step3']}\n")
        fh.write(f"RAW_HTTPS_OCCURRENCES={report['raw_https_occurrences']}\n")
        fh.write(f"RAW_HTTP_OCCURRENCES={report['raw_http_occurrences']}\n")
        fh.write(f"REMOTE_RESOURCE_LOADS={len(remote_loads)}\n")
        fh.write(f"JAVASCRIPT_NETWORK_CALLS={len(js_network)}\n\n")
        for i, hit in enumerate(hits, 1):
            fh.write(f"[{i}] {hit['value']}\nOFFSET={hit['offset']}\nCONTEXT={hit['context']}\n\n")

    print(json.dumps({
        "payload_sha256_after_step3": report["payload_sha256_after_step3"],
        "raw_https_occurrences": report["raw_https_occurrences"],
        "raw_http_occurrences": report["raw_http_occurrences"],
        "remote_resource_loads": len(remote_loads),
        "javascript_network_calls": len(js_network),
        "urls": [x["value"] for x in hits],
    }, ensure_ascii=False, sort_keys=True))
    return 0


def validate_step4_security_patch(repo: Path, out: Path) -> int:
    """Validate only the narrowed Batch-2 offline-safety assertion.

    Reconstruct through accepted Steps 1-3, execute the patched Step 4 once,
    prove the accepted local appassets CSP is permitted, and prove unapproved
    remote HTTP(S) or iframe/eval constructs are still rejected.
    """
    out.mkdir(parents=True, exist_ok=True)
    driver = import_driver(repo)

    canonical = repo / "INVERTEBRATA_v1.8.7_RECONCILED_ANDROID_SOURCE.zip"
    if sha256_file(canonical) != EXPECTED_ZIP_SHA256:
        raise RuntimeError("canonical ZIP SHA-256 mismatch")
    if git("hash-object", canonical.name, cwd=repo) != EXPECTED_ZIP_BLOB:
        raise RuntimeError("canonical ZIP Git blob mismatch")

    manifest = json.loads((repo / "ci/v188_accepted_transform_manifest.json").read_text(encoding="utf-8"))
    if len(manifest["steps"]) != 19:
        raise RuntimeError("accepted manifest is not 19 steps")

    # Phase-B1 identity is required for steps 1-3; Step 4 is intentionally the
    # audit-branch remediation candidate under test.
    for step in manifest["steps"][:3]:
        observed = git("hash-object", step["path"], cwd=repo)
        if observed != step["git_blob"]:
            raise RuntimeError(f"pre-Step4 accepted blob drift at step {step['step']}: {observed}")

    work = out / "work"
    if work.exists():
        shutil.rmtree(work)
    work.mkdir()
    source_root = driver.safe_extract_zip(canonical, work / "extracted")

    exec_log = []
    for step in manifest["steps"][:3]:
        output = driver.execute_step(repo, source_root, step, work)
        exec_log.append({
            "step": step["step"],
            "script": step["path"],
            "git_blob": step["git_blob"],
            "exit_code": 0,
            "output": output,
        })

    a, b = driver.payload_paths(source_root)
    before_step4 = a.read_text(encoding="utf-8")
    trusted_csp = "img-src 'self' data: blob: https://appassets.androidplatform.net;"
    exact_https = "https://appassets.androidplatform.net"
    if before_step4.count(exact_https) != 1:
        raise RuntimeError(f"expected exactly one appassets HTTPS origin before Step4, found {before_step4.count(exact_https)}")
    if before_step4.count(trusted_csp) != 1:
        raise RuntimeError("accepted appassets origin is not confined to the expected CSP directive")

    # Execute the patched Step 4 directly.  The persisted Phase-B1 manifest is
    # deliberately not changed during this narrow remediation audit.
    patched_step4 = repo / "ci/reconcile_v188_svg_batch2.py"
    cp = subprocess.run(
        [sys.executable, str(patched_step4), str(source_root)],
        text=True,
        stdout=subprocess.PIPE,
        stderr=subprocess.STDOUT,
        check=False,
    )
    exec_log.append({
        "step": 4,
        "script": "ci/reconcile_v188_svg_batch2.py",
        "git_blob": git("hash-object", "ci/reconcile_v188_svg_batch2.py", cwd=repo),
        "exit_code": cp.returncode,
        "output": cp.stdout,
    })
    if cp.returncode != 0:
        raise RuntimeError(f"patched Step 4 failed ({cp.returncode}):\\n{cp.stdout}")

    a, b = driver.payload_paths(source_root)
    if a.read_bytes() != b.read_bytes():
        raise RuntimeError("payload copies diverged after patched Step 4")
    payload = a.read_text(encoding="utf-8")

    # Import the patched assertion and exercise it independently.
    spec = importlib.util.spec_from_file_location("batch2_patched", patched_step4)
    if spec is None or spec.loader is None:
        raise RuntimeError("cannot import patched Batch-2 module")
    mod = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(mod)

    # Accepted payload must pass.
    mod.assert_offline_safety(payload)

    negative_cases = {
        "external_https_image": payload + '\\n<img src="https://example.invalid/x.png">\\n',
        "external_http_image": payload + '\\n<img src="http://example.invalid/x.png">\\n',
        "iframe": payload + '\\n<iframe src="about:blank"></iframe>\\n',
        "eval": payload + '\\n<script>eval("1")</script>\\n',
        "new_function": payload + '\\n<script>new Function("return 1")</script>\\n',
        "second_appassets_occurrence": payload + '\\nhttps://appassets.androidplatform.net\\n',
    }
    negative_results = {}
    for name, sample in negative_cases.items():
        rejected = False
        message = ""
        try:
            mod.assert_offline_safety(sample)
        except SystemExit as e:
            rejected = True
            message = str(e)
        negative_results[name] = {"rejected": rejected, "message": message}
        if not rejected:
            raise RuntimeError(f"offline assertion negative test did not reject: {name}")

    # Independent structural checks: the single HTTPS occurrence is the trusted
    # local CSP origin; no remote-loading HTML element is present.
    remote_load_pat = re.compile(
        r'<(?:script|img|iframe|link|source)\\b[^>]*(?:src|href)\\s*=\\s*["\\'](https?://[^"\\']+)["\\']',
        re.I,
    )
    remote_loads = [m.group(1) for m in remote_load_pat.finditer(payload)]
    all_https = [m.start() for m in re.finditer("https://", payload)]
    all_http = [m.start() for m in re.finditer("http://", payload)]
    svg_ns_count = payload.count("http://www.w3.org/2000/svg")

    report = {
        "canonical_zip_sha256": sha256_file(canonical),
        "payload_sha256_before_step4": hashlib.sha256(before_step4.encode("utf-8")).hexdigest(),
        "payload_sha256_after_step4": sha256_file(a),
        "patched_step4_blob": git("hash-object", "ci/reconcile_v188_svg_batch2.py", cwd=repo),
        "steps_1_to_4": exec_log,
        "trusted_local_origin": exact_https,
        "trusted_csp_directive": trusted_csp,
        "trusted_origin_occurrences_after_step4": payload.count(exact_https),
        "raw_https_occurrences_after_step4": len(all_https),
        "raw_http_occurrences_after_step4": len(all_http),
        "svg_namespace_occurrences_after_step4": svg_ns_count,
        "remote_resource_loads": remote_loads,
        "negative_security_tests": negative_results,
        "accepted_payload_assertion": "PASS",
        "academic_content_patch": "NONE — assertion only",
    }
    report["pass"] = (
        payload.count(exact_https) == 1
        and len(all_https) == 1
        and not remote_loads
        and all(x["rejected"] for x in negative_results.values())
    )

    (out / "BATCH2_SECURITY_ASSERTION_VALIDATION.json").write_text(
        json.dumps(report, ensure_ascii=False, indent=2, sort_keys=True) + "\\n",
        encoding="utf-8",
    )
    (out / "BATCH2_SECURITY_ASSERTION_VALIDATION.txt").write_text(
        "\\n".join([
            f"TRUSTED_LOCAL_ORIGIN={exact_https}",
            f"TRUSTED_ORIGIN_OCCURRENCES={payload.count(exact_https)}",
            f"RAW_HTTPS_OCCURRENCES={len(all_https)}",
            f"REMOTE_RESOURCE_LOADS={len(remote_loads)}",
            f"NEGATIVE_SECURITY_TESTS={'PASS' if all(x['rejected'] for x in negative_results.values()) else 'FAIL'}",
            f"PATCHED_STEP4_EXECUTION={'PASS' if cp.returncode == 0 else 'FAIL'}",
            f"VALIDATION={'PASS' if report['pass'] else 'FAIL'}",
            "",
        ]),
        encoding="utf-8",
    )
    print(json.dumps({
        "trusted_local_origin": exact_https,
        "trusted_origin_occurrences": payload.count(exact_https),
        "raw_https_occurrences": len(all_https),
        "remote_resource_loads": len(remote_loads),
        "negative_security_tests": negative_results,
        "patched_step4_exit_code": cp.returncode,
        "pass": report["pass"],
    }, sort_keys=True))
    return 0 if report["pass"] else 5

def main() -> None:
    ap = argparse.ArgumentParser()
    sub = ap.add_subparsers(dest="cmd", required=True)

    p = sub.add_parser("preflight")
    p.add_argument("--repo", type=Path, default=Path("."))
    p.add_argument("--out", type=Path, required=True)

    p = sub.add_parser("diagnose-step4-url")
    p.add_argument("--repo", type=Path, default=Path("."))
    p.add_argument("--out", type=Path, required=True)

    p = sub.add_parser("validate-step4-security-patch")
    p.add_argument("--repo", type=Path, default=Path("."))
    p.add_argument("--out", type=Path, required=True)

    p = sub.add_parser("run")
    p.add_argument("--repo", type=Path, default=Path("."))
    p.add_argument("--label", choices=["A", "B"], required=True)
    p.add_argument("--out", type=Path, required=True)

    p = sub.add_parser("compare")
    p.add_argument("--a", type=Path, required=True)
    p.add_argument("--b", type=Path, required=True)
    p.add_argument("--out", type=Path, required=True)

    args = ap.parse_args()
    repo = args.repo.resolve() if hasattr(args, "repo") else None
    if args.cmd == "preflight":
        raise SystemExit(preflight(repo, args.out.resolve()))
    if args.cmd == "diagnose-step4-url":
        raise SystemExit(diagnose_step4_url(repo, args.out.resolve()))
    if args.cmd == "validate-step4-security-patch":
        raise SystemExit(validate_step4_security_patch(repo, args.out.resolve()))
    if args.cmd == "run":
        raise SystemExit(run_once(repo, args.label, args.out.resolve()))
    if args.cmd == "compare":
        raise SystemExit(compare_runs(args.a.resolve(), args.b.resolve(), args.out.resolve()))


if __name__ == "__main__":
    main()
