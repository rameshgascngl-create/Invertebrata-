#!/usr/bin/env python3
"""Regression checks: exact artwork IDs and hashes, release fail-closed."""
import json
from pathlib import Path
import shutil
import tempfile
import unittest

from unit2_artwork_acceptance import check

ROOT = Path(__file__).resolve().parents[1]
ASSETS = ROOT / "native-app/app/src/main/assets/unit2"


class UnitIIArtworkGateTest(unittest.TestCase):
    def test_engineering_integrity_can_pass_without_false_academic_acceptance(self):
        report, passed = check(ROOT, "engineering")
        self.assertTrue(passed, report["technical_errors"])
        self.assertEqual(report["engineering_integrity"], "PASS")
        self.assertEqual(report["academic_release"], "HOLD")
        self.assertEqual({p["id"] for p in report["plates"]},
                         {"sycon_plate", "obelia_colony", "obelia_lifecycle"})

    def test_release_must_fail_without_human_evidence(self):
        report, passed = check(ROOT, "release")
        self.assertFalse(passed)
        self.assertEqual(report["academic_release"], "HOLD")
        self.assertTrue(any("genuine hand-scan provenance" in x for x in report["approval_blockers"]))
        self.assertTrue(any("physical Android" in x for x in report["approval_blockers"]))

    def test_changed_pixels_are_not_silently_approved(self):
        with tempfile.TemporaryDirectory() as td:
            temp = Path(td)
            for name in ("sycon_plate.webp", "obelia_colony.webp", "obelia_lifecycle.webp"):
                shutil.copy2(ASSETS / name, temp / name)
            altered = temp / "obelia_colony.webp"
            altered.write_bytes(altered.read_bytes() + b"\x00")
            report, passed = check(ROOT, "engineering", assets_override=temp)
            self.assertFalse(passed)
            self.assertTrue(any("SHA-256 mismatch" in x for x in report["technical_errors"]))

    def test_an_unreviewed_png_cannot_shadow_pinned_webp(self):
        with tempfile.TemporaryDirectory() as td:
            temp = Path(td)
            for name in ("sycon_plate.webp", "obelia_colony.webp", "obelia_lifecycle.webp"):
                shutil.copy2(ASSETS / name, temp / name)
            shutil.copy2(ASSETS / "obelia_colony.webp", temp / "obelia_colony.png")
            report, passed = check(ROOT, "engineering", assets_override=temp)
            self.assertFalse(passed)
            self.assertTrue(any("unreviewed PNG" in x for x in report["technical_errors"]))


if __name__ == "__main__":
    unittest.main()
