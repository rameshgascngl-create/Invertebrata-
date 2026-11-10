#!/usr/bin/env python3
"""Fail if a sourced DEBUG-only anatomical PNG changes without review.

Keep original held raster masters, formal release review register and forty-one
provisionally accepted source hashes unchanged.
"""
import hashlib
from pathlib import Path
from struct import unpack
ROOT = Path(__file__).resolve().parents[1]
REL = Path("native-app/app/src/debug/assets/qa_source_plates")
EXPECTED = {
  "03_sycon_development.png": "340fc98a6621931b6c58ebebef4327dd5b187955",
  "09_asterias_dissection.png": "c6305805ce89e85252ea1e57771c996b2e2c195e",
  "10_asterias_larval_reference.png": "6ab2147feb21e71b5445824d3c8657d6ced132a9"
}
def main():
    actual = {p.name for p in (ROOT/REL).glob("*.png")}
    assert actual == set(EXPECTED), (actual,set(EXPECTED))
    for filename, sha in EXPECTED.items():
        p=ROOT/REL/filename
        data=p.read_bytes()
        assert len(data)>50000 and data[:8]==b"\x89PNG\r\n\x1a\n", filename
        w,h=unpack(">II",data[16:24])
        assert w>=800 and h>=800,(filename,w,h)
        actual_sha=hashlib.sha1(b"blob "+str(len(data)).encode()+b"\0"+data).hexdigest()
        assert actual_sha==sha,("PNG_BYTES_CHANGED_REVIEW_REQUIRED",filename,actual_sha)
        print(f"QA_SOURCE_PNG={filename} {w}x{h} blob={sha}",flush=True)
    assert all(not (ROOT/"native-app/app/src/main/assets/qa_source_plates"/n).exists()
               for n in EXPECTED)
    print("DEBUG_ONLY_SOURCE_PNG=3; FORMAL_ACADEMIC_GATE=UNCHANGED")
if __name__=="__main__": main()
