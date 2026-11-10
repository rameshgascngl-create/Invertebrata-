#!/usr/bin/env python3
"""Validate QA-only legibility green ticks, never independent academic approval."""
import hashlib
import json
import runpy
from collections import Counter
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
REGISTER = ROOT / "qa-intake/provisional-figure-inclusion-20261110.json"
ALLOWED = {"GREEN_TICK_QA", "HOLD_CORRECTION"}

def main():
    registry = json.loads(REGISTER.read_text(encoding="utf-8"))
    assert registry["schema_version"] == 1
    assert registry["academic_release_gate"].startswith("UNCHANGED:")
    assert registry["meaning_of_green_tick"].startswith("Provisional suitability")
    figures = registry["figures"]
    required = {r["required_stem"]: r for r in
                runpy.run_path(str(ROOT / ".github/scripts/audit-five-unit-assets.py"))["audit"](ROOT)
                if r["required"]}
    assert len(figures) == len(required) == 52
    assert [row["index"] for row in figures] == list(range(1, 53))
    seen = set()
    tally = Counter()
    for fig in figures:
        stem = fig["asset_stem"]
        assert stem in required and stem not in seen, stem
        seen.add(stem)
        state = fig["provisional_qa_decision"]
        assert state in ALLOWED, stem
        assert fig["independent_scientific_tamil_rights_approval"] is False, stem
        assert fig["rationale"].strip(), stem
        path = fig["source_path"]
        expected = "native-app/app/src/main/assets/" + required[stem]["file"]
        assert path == expected, (stem, path, expected)
        assert required[stem]["state"] == "PRESENT_UNVERIFIED", stem
        data = (ROOT / path).read_bytes()
        blob = hashlib.sha1(b"blob " + str(len(data)).encode("ascii") +
                            bytes([0]) + data).hexdigest()
        assert fig["git_blob_sha1"] == blob, ("QA_REVIEW_EXPIRED_RECHECK_IMAGE", path)
        tally[state] += 1
    assert seen == set(required)
    assert registry["summary"] == {
        "total": 52, "green_tick_qa": tally["GREEN_TICK_QA"],
        "hold_correction": tally["HOLD_CORRECTION"]
    }
    assert tally["GREEN_TICK_QA"] == 41 and tally["HOLD_CORRECTION"] == 11
    print("QA_FIGURES_GREEN_TICK=41/52; QA_FIGURES_HOLD=11/52")
    print("QA_EXACT_SOURCE_BYTES=PASS; INDEPENDENT_ACADEMIC_RIGHTS_GATE=UNCHANGED")

if __name__ == "__main__":
    main()
