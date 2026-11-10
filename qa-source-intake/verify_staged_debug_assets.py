#!/usr/bin/env python3
"""Verify eleven owner-approved images in standard and debug Android assets.

Keep the original 41 provisionally accepted master image bytes unchanged.
"""
import hashlib
from pathlib import Path
from struct import unpack
ROOT = Path(__file__).resolve().parents[1]
REL = Path("native-app/app/src/main/assets/qa_source_plates")
DEBUG_REL = Path("native-app/app/src/debug/assets/qa_source_plates")
EXPECTED = {
  "03_sycon_development.png": "340fc98a6621931b6c58ebebef4327dd5b187955",
  "09_asterias_dissection.png": "c6305805ce89e85252ea1e57771c996b2e2c195e",
  "10_asterias_larval_reference.png": "6ab2147feb21e71b5445824d3c8657d6ced132a9",
  "11_leishmania_two_forms.png": "e2e0c22c03f3e1cf30608d6c78cfa897080265d7",
  "01_paramecium_oral_sourced.png": "3e331971fbe31438b70b4cf3455cdaa0eb216621",
  "02_sycon_external_sourced.png": "447ea036e973ef11ec0723ab97e4460717b6ec95",
  "04_obelia_medusa_sourced.png": "45c7322d9ba4c3c3312591544be3f59206eb3da2",
  "05_obelia_lifecycle_sourced.png": "3255ad4291b461375f2d4d8d5e3be6f5c18f5cd0",
  "06_ascaris_female_sourced.png": "ed2be05fd0cf3d4c3e7b95e7404b3f7cef14712c",
  "07_earthworm_excretory_sourced.png": "251d21d084d2895cda5657ef9aaa5bb4ee7fa294",
  "08_pila_external_sourced.png": "7b99da180ef24ab2faafa4736f4f54a348bc87a4"
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
    # Legacy QA overlay copies are retained byte-identically; never silently
    # substitute them for the newly installed main textbook source images.
    for filename in EXPECTED:
        assert (ROOT/DEBUG_REL/filename).read_bytes() == (ROOT/REL/filename).read_bytes(), filename
    print("STANDARD_AND_DEBUG_SOURCE_PNG=11/11; OWNER_APPROVED_IMAGE_INCLUSION=YES")
    print("FORMAL_ACADEMIC_RIGHTS_SIGNOFF=SEPARATE")
if __name__=="__main__": main()
