#!/usr/bin/env python3
"""Check that a debug QA APK is installable and carries a valid APK signature."""
from pathlib import Path
import argparse
import hashlib
import os
import subprocess
import sys
import zipfile

def find_apksigner():
    sdk = os.environ.get("ANDROID_HOME") or os.environ.get("ANDROID_SDK_ROOT")
    if not sdk:
        raise RuntimeError("Android SDK location is not configured")
    candidates = list((Path(sdk) / "build-tools").glob("*/apksigner"))
    if not candidates:
        raise RuntimeError("Android SDK apksigner not installed")
    return sorted(candidates, key=lambda p: tuple(int(n) if n.isdigit() else n for n in p.parent.name.split(".")))[-1]

def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("apk", type=Path)
    args = parser.parse_args()
    try:
        if not args.apk.is_file():
            raise RuntimeError("QA APK does not exist")
        with zipfile.ZipFile(args.apk) as archive:
            bad = archive.testzip()
            if bad:
                raise RuntimeError("Corrupt APK entry: " + bad)
            names = archive.namelist()
            if "AndroidManifest.xml" not in names or "classes.dex" not in names:
                raise RuntimeError("Missing manifest or DEX")
            if not any(n.startswith("assets/") for n in names):
                raise RuntimeError("Missing textbook assets")
        result = subprocess.run(
            [str(find_apksigner()), "verify", "--verbose", str(args.apk)],
            text=True, capture_output=True, check=False,
        )
        if result.returncode:
            raise RuntimeError("APK signature verification failed: " + (result.stderr or result.stdout))
    except (OSError, RuntimeError, zipfile.BadZipFile) as exc:
        print("FAIL:", exc, file=sys.stderr)
        return 1
    print("PASS: APK archive, manifest, DEX, assets, and cryptographic APK signature")
    print("Bytes:", args.apk.stat().st_size)
    with args.apk.open("rb") as handle:
        print("SHA-256:", hashlib.file_digest(handle, "sha256").hexdigest())
    print("NOTE: Debug QA signing only. Not a production release or device acceptance.")
    return 0

if __name__ == "__main__":
    raise SystemExit(main())
