import json, sys, tempfile, unittest
from pathlib import Path
sys.path.insert(0, str(Path(__file__).parent))
import import_review_sheet as imp

SHA = "a" * 64
REQ = {"unit1/x": {"state": "PRESENT_UNVERIFIED", "sha256": SHA}, "unit1/y": {"state": "PRESENT_UNVERIFIED", "sha256": SHA}}

def row(stem="unit1/x", status="ACCEPTED", **kw):
    r = {"asset_stem": stem, "status": status, "sha256_now": SHA, "biology_reviewer": "A", "tamil_reviewer": "B",
         "source_reference": "s", "rights_evidence": "e", "rights_status": "author_owned",
         "pixel_leaders_verified": True, "morphology_verified": True, "tamil_terms_verified": True, "display_reviewed": True}
    r.update(kw); return r

def ok_verify(path):
    return [{"status": "ACCEPTED"} for _ in json.loads(Path(path).read_text())["reviews"]]

class T(unittest.TestCase):
    def setUp(self):
        self.d = tempfile.TemporaryDirectory(); self.out = Path(self.d.name) / "r.json"
        self.out.write_text("ORIGINAL")
    def tearDown(self): self.d.cleanup()
    def go(self, rows, **kw):
        return imp.run(rows, REQ, self.out, kw.pop("check", False), ok_verify, **kw)
    def test_check_never_writes(self):
        self.assertEqual(self.go([row()], check=True), 0); self.assertEqual(self.out.read_text(), "ORIGINAL")
    def test_hold_only_check_and_write(self):
        self.assertEqual(self.go([row(status="HOLD")], check=True), 0); self.assertEqual(self.out.read_text(), "ORIGINAL")
        self.assertEqual(self.go([row(status="HOLD")]), 1); self.assertEqual(self.out.read_text(), "ORIGINAL")
        self.assertEqual(self.go([row(status="HOLD")], allow_empty=True), 0)
        self.assertEqual(json.loads(self.out.read_text())["reviews"], [])
    def test_duplicates_rejected_everywhere(self):
        for chk in (True, False):
            self.assertEqual(self.go([row(), row()], check=chk), 1); self.assertEqual(self.out.read_text(), "ORIGINAL")
    def test_missing_stem_rejected(self):
        for chk in (True, False):
            self.assertEqual(self.go([row(), row(stem="")], check=chk), 1); self.assertEqual(self.out.read_text(), "ORIGINAL")
    def test_invalid_rows_block_write(self):
        for bad in (row(sha256_now="b" * 64), row(tamil_reviewer="a"), row(rights_status="maybe"), row(display_reviewed=False)):
            self.assertEqual(self.go([bad]), 1); self.assertEqual(self.out.read_text(), "ORIGINAL")
    def test_valid_write_is_atomic_replace(self):
        self.assertEqual(self.go([row(), row(stem="unit1/y")]), 0)
        self.assertEqual(len(json.loads(self.out.read_text())["reviews"]), 2)
        self.assertEqual([p.name for p in self.out.parent.iterdir()], ["r.json"])
    def test_gate_rejection_blocks_write(self):
        r = imp.run([row()], REQ, self.out, False, lambda p: [{"status": "HOLD"}])
        self.assertEqual(r, 1); self.assertEqual(self.out.read_text(), "ORIGINAL")
if __name__ == "__main__": unittest.main()
