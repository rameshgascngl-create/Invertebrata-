#!/usr/bin/env python3
"""Inspect a built release APK without claiming store signing or device acceptance."""
import argparse
import hashlib
from pathlib import Path
import sys
import zipfile

def main():
    p = argparse.ArgumentParser()
    p.add_argument("apk", type=Path)
    args = p.parse_args()
    if not args.apk.is_file():
        print("FAIL: release APK not found", file=sys.stderr)
        return 1
    with zipfile.ZipFile(args.apk) as z:
        bad = z.testzip()
        if bad:
            print("FAIL: corrupt archive entry:", bad, file=sys.stderr)
            return 1
        names = z.namelist()
        for name in ("AndroidManifest.xml", "classes.dex"):
            if name not in names:
                print("FAIL: required APK entry missing:", name, file=sys.stderr)
                return 1
        if not any(name.startswith("assets/") for name in names):
            print("FAIL: no packaged educational assets", file=sys.stderr)
            return 1
        if any(name.startswith("assets/review_only/") for name in names):
            print("FAIL: editorial-only review assets leaked into release APK", file=sys.stderr)
            return 1
    print("PASS: release APK ZIP integrity, manifest, DEX, assets, and review-only exclusion")
    print("APK bytes:", args.apk.stat().st_size)
    print("SHA-256:", hashlib.sha256(args.apk.read_bytes()).hexdigest())
    print("NOT VERIFIED: signing certificate, Play App Signing, on-device behaviour, AAB acceptance")
    return 0

if __name__ == "__main__":
    raise SystemExit(main())
