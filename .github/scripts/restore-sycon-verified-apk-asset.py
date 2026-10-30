#!/usr/bin/env python3
"""Restore exact Sycon reference already embedded in a previously green Unit III APK.

The known-good APK is downloaded by actions/download-artifact from run 38347398335.
No historical-site or external HTTP access occurs in this script.
This restores the *same bytes* used in that passing emulator run.
Academic sign-off of the source illustration remains a separate gate.
"""
from pathlib import Path
from zipfile import ZipFile
import hashlib
import sys

EXPECTED_SHA256 = "32d255bc803f2cbff040d1ddaeccb4dddef93a4b36d56246554f4967bb9e726d"
APK_ASSET_PATH = "assets/unit2/sycon_historical.png"
OUTPUT = Path("native-app/app/src/main/assets/unit2/sycon_historical.png")

def main(apk_path: str) -> None:
    apk = Path(apk_path)
    assert apk.is_file(), f"Immutable prior APK unavailable: {apk}"
    with ZipFile(apk) as archive:
        assert APK_ASSET_PATH in archive.namelist(), "Sycon asset missing from approved engineering APK"
        data = archive.read(APK_ASSET_PATH)
    actual = hashlib.sha256(data).hexdigest()
    assert actual == EXPECTED_SHA256, f"Sycon asset unexpectedly changed: {actual}"
    assert data[:8] == b"\x89PNG\r\n\x1a\n", "Recovered artifact is not a PNG"
    OUTPUT.parent.mkdir(parents=True, exist_ok=True)
    OUTPUT.write_bytes(data)
    print(f"SYCON_RESTORED_FROM_VERIFIED_APK={apk} SHA256={actual} bytes={len(data)}")
    print("SYCON_HISTORICAL_SOURCE_REDOWNLOAD=DISABLED; ACADEMIC_QA_PENDING")

if __name__ == "__main__":
    assert len(sys.argv) == 2, "Usage: restore-sycon-verified-apk-asset.py APK"
    main(sys.argv[1])
