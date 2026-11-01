import io, json, sys, tempfile, unittest
from pathlib import Path
sys.path.insert(0, str(Path(__file__).parent))
import fetch_commons_plates as f
from PIL import Image

def payload(lic, url="", artist="<a>Deuterostome</a>"):
    return {"query": {"pages": {"1": {"title": "File:X.png", "imageinfo": [{
        "url": "u", "thumburl": "t", "width": 10, "height": 10, "descriptionurl": "p",
        "extmetadata": {"LicenseShortName": {"value": lic}, "LicenseUrl": {"value": url},
                        "Artist": {"value": artist}}}]}}}}

def png():
    b = io.BytesIO(); Image.new("RGBA", (8, 8), (0, 0, 0, 0)).save(b, "PNG"); return b.getvalue()

class T(unittest.TestCase):
    def test_classify(self):
        for s, v in [("Public domain", "ok"), ("CC0", "ok"), ("CC BY 4.0", "ok"), ("CC BY-SA 4.0", "share-alike"),
                     ("CC BY-NC 4.0", "refuse"), ("CC BY-ND 2.0", "refuse"), ("Fair use", "refuse"), ("", "refuse")]:
            self.assertEqual(f.classify(s), v, s)
    def test_stage(self):
        with tempfile.TemporaryDirectory() as d:
            f.OUT = Path(d)
            info = f.parse_info(payload("Public domain"))
            p = f.stage(info, "unit1/x", False, fetch=lambda u: png())
            rec = json.loads((Path(d) / "unit1__x.provenance.json").read_text())
            self.assertEqual(rec["author"], "Deuterostome"); self.assertEqual(len(rec["sha256"]), 64)
            self.assertEqual(Image.open(p).getpixel((0, 0)), (255, 255, 255))  # flattened on white
    def test_sa_held_and_nc_refused(self):
        with tempfile.TemporaryDirectory() as d:
            f.OUT = Path(d)
            with self.assertRaises(SystemExit): f.stage(f.parse_info(payload("CC BY-SA 4.0")), "s", False, fetch=lambda u: png())
            f.stage(f.parse_info(payload("CC BY-SA 4.0")), "s", True, fetch=lambda u: png())
            with self.assertRaises(SystemExit): f.stage(f.parse_info(payload("CC BY-NC 4.0")), "s", True, fetch=lambda u: png())
            with self.assertRaises(SystemExit): f.stage(f.parse_info(payload("CC0", artist="")), "s", True, fetch=lambda u: png())
if __name__ == "__main__": unittest.main()
