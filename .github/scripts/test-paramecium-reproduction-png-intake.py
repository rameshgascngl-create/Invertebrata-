#!/usr/bin/env python3
"""Regression tests for fail-closed Paramecium reproduction raster intake."""
import importlib.util
import json
from pathlib import Path
import struct
import tempfile
import unittest
import zlib

SCRIPT = Path(__file__).with_name("verify-paramecium-reproduction-png-intake.py")
spec = importlib.util.spec_from_file_location("paramecium_intake", SCRIPT)
module = importlib.util.module_from_spec(spec)
spec.loader.exec_module(module)

def chunk(name, payload):
    return struct.pack(">I", len(payload)) + name + payload + struct.pack(">I", zlib.crc32(name + payload) & 0xffffffff)

def png(width=1800, height=600):
    # Minimal valid structural PNG for testing the container-level checks.
    header = struct.pack(">IIBBBBB", width, height, 8, 2, 0, 0, 0)
    raw = b"\x00" + b"\x00" * (width * 3)
    compressed = zlib.compress(raw * height)
    return module.SIGNATURE + chunk(b"IHDR", header) + chunk(b"IDAT", compressed) + chunk(b"IEND", b"")

class IntakeTests(unittest.TestCase):
    def setUp(self):
        self.tmp = tempfile.TemporaryDirectory()
        self.addCleanup(self.tmp.cleanup)
        self.root = Path(self.tmp.name)
        self.folder = self.root / "qa-intake/paramecium"
        self.folder.mkdir(parents=True)
        self.manifest = self.folder / "reproduction-png-intake.json"
        self.records = {}
        for plate_id in module.IDS:
            path = self.folder / (plate_id + ".png")
            path.write_bytes(png())
            self.records[plate_id] = {
                "path": "qa-intake/paramecium/" + plate_id + ".png",
                "sha256": module.hashlib.sha256(path.read_bytes()).hexdigest(),
                "creator": "Test creator", "source_url_or_user_provenance": "Test fixture",
                "licence_or_permission_evidence": "Test fixture only",
                "attribution": "Test fixture", "species": "Paramecium caudatum",
                "observed_or_schematic": "schematic"
            }
        self.save()

    def save(self):
        self.manifest.write_text(json.dumps(self.records), encoding="utf-8")

    def test_all_sixteen_structural_fixtures_pass(self):
        self.assertEqual(len(module.verify(self.root, self.manifest)), 16)
        self.assertTrue(all(x["technical_intake"] == "PASS" for x in module.verify(self.root, self.manifest)))

    def test_missing_png_fails_closed(self):
        (self.folder / "F01.png").unlink()
        self.assertIn("PNG_MISSING", module.verify(self.root, self.manifest)[0]["blockers"])

    def test_crc_corruption_fails_closed(self):
        p = self.folder / "F01.png"
        data = bytearray(p.read_bytes())
        data[29] ^= 1
        p.write_bytes(data)
        self.assertTrue(any(x.startswith("PNG_INVALID:") for x in module.verify(self.root, self.manifest)[0]["blockers"]))

    def test_changed_bytes_fail_hash(self):
        p = self.folder / "F01.png"
        p.write_bytes(png(1900, 600))
        self.assertIn("HASH_MISMATCH", module.verify(self.root, self.manifest)[0]["blockers"])

    def test_missing_rights_metadata_fails(self):
        self.records["F01"]["licence_or_permission_evidence"] = ""
        self.save()
        self.assertIn("MISSING_LICENCE_OR_PERMISSION_EVIDENCE", module.verify(self.root, self.manifest)[0]["blockers"])

    def test_wrong_species_fails(self):
        self.records["F01"]["species"] = "Paramecium aurelia"
        self.save()
        self.assertIn("SPECIES_NOT_CONFIRMED", module.verify(self.root, self.manifest)[0]["blockers"])

    def test_small_raster_fails(self):
        (self.folder / "F01.png").write_bytes(png(900, 600))
        self.assertTrue(any(x.startswith("PNG_INVALID:") for x in module.verify(self.root, self.manifest)[0]["blockers"]))

    def test_path_traversal_fails(self):
        self.records["F01"]["path"] = "qa-intake/paramecium/../../outside.png"
        self.save()
        self.assertIn("INVALID_PATH", module.verify(self.root, self.manifest)[0]["blockers"])

    def test_missing_stage_record_fails(self):
        del self.records["C10"]
        self.save()
        with self.assertRaises(ValueError):
            module.verify(self.root, self.manifest)

if __name__ == "__main__":
    unittest.main()
