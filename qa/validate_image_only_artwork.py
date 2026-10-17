#!/usr/bin/env python3
"""Fail the student-release artwork gate when programmatic figure sketches remain.

Report intentionally includes *all* native UI Kotlin code across Units I–V.
This is an academic acceptance gate, separate from Gradle's engineering PASS.
"""
from __future__ import annotations
import json
import re
import sys
from pathlib import Path

root = Path(__file__).resolve().parents[1]
kotlin = root / "native-app/app/src/main/java"
drawable = root / "native-app/app/src/main/res/drawable"
assets = root / "native-app/app/src/main/assets"
out = root / "qa-evidence/image-only"
out.mkdir(parents=True, exist_ok=True)

canvas_call = re.compile(r"\bCanvas\s*\(")
vector_path = re.compile(r"<vector\b")
violations = []
for f in sorted(kotlin.rglob("*.kt")):
    lines = f.read_text(encoding="utf-8").splitlines()
    for n, line in enumerate(lines, 1):
        if canvas_call.search(line) and not line.lstrip().startswith(("*", "//")):
            violations.append({
                "kind": "COMPOSE_CANVAS_SKETCH", "path": f.relative_to(root).as_posix(),
                "line": n, "excerpt": line.strip()[:180]})
for f in sorted(drawable.glob("*.xml")) if drawable.exists() else []:
    content=f.read_text(encoding="utf-8")
    if vector_path.search(content):
        violations.append({"kind":"GENERATED_VECTOR_ARTWORK",
                           "path":f.relative_to(root).as_posix(),
                           "line":1,"excerpt":"VectorDrawable is not an approved pencil-scan master"})
raster_ext={".webp", ".png", ".jpeg", ".jpg"}
rasters = [p.relative_to(root).as_posix() for parent in (assets,drawable) if parent.exists()
           for p in parent.rglob("*") if p.is_file() and p.suffix.lower() in raster_ext]
report = {
  "policy":"IMAGE_ONLY_ANATOMICAL_PLATES_ALL_UNITS",
  "acceptance":"HOLD" if violations else "SOURCE_ARTWORK_TECHNICAL_CHECK_PASS_NOT_ACADEMIC_APPROVAL",
  "canvas_and_vector_violations":violations,
  "raster_candidate_files":sorted(rasters),
  "raster_files_not_automatically_academically_approved":True,
}
(out/"audit.json").write_text(json.dumps(report,indent=2,ensure_ascii=False)+"\n",encoding="utf-8")
print(f"IMAGE_ONLY_POLICY={report['acceptance']} violations={len(violations)} raster_candidates={len(rasters)}")
for x in violations:
    print(f"  {x['kind']}: {x['path']}:{x['line']} {x['excerpt']}")
if violations:
    print("RELEASE_BLOCKED: Replace procedural drawings with source-reviewed raster masters,")
    print("then verify Tamil/English labels, captured Android screens and physical device.")
    sys.exit(1)
