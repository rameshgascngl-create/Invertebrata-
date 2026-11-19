#!/usr/bin/env python3
"""Read-only inspection of an unsigned Android App Bundle candidate.

AAB archive checks do not establish Play signing or device compatibility.
"""
from pathlib import Path
import argparse
import hashlib
import sys
import zipfile

def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("bundle", type=Path)
    args = parser.parse_args()
    if not args.bundle.is_file():
        print("FAIL: AAB file missing", file=sys.stderr)
        return 1
    try:
        with zipfile.ZipFile(args.bundle) as archive:
            bad = archive.testzip()
            if bad:
                print("FAIL: corrupt bundle entry", bad, file=sys.stderr)
                return 1
            names = archive.namelist()
            for required in ("BundleConfig.pb", "base/manifest/AndroidManifest.xml"):
                if required not in names:
                    print("FAIL: missing AAB entry", required, file=sys.stderr)
                    return 1
            if not any(name.startswith("base/dex/") and name.endswith(".dex") for name in names):
                print("FAIL: compiled DEX missing", file=sys.stderr)
                return 1
            if not any(name.startswith("base/assets/") for name in names):
                print("FAIL: educational assets missing", file=sys.stderr)
                return 1
            if any(name.startswith("base/assets/review_only/") for name in names):
                print("FAIL: review-only assets included in release bundle", file=sys.stderr)
                return 1
    except (zipfile.BadZipFile, OSError) as exc:
        print("FAIL: cannot inspect AAB:", exc, file=sys.stderr)
        return 1
    print("PASS: AAB archive integrity, manifest, DEX, assets and review-only exclusion")
    print("Bundle bytes:", args.bundle.stat().st_size)
    print("SHA-256:", hashlib.sha256(args.bundle.read_bytes()).hexdigest())
    print("NOT VERIFIED: Play signing, production key ownership, Play Console validation, device acceptance")
    return 0

if __name__ == "__main__":
    raise SystemExit(main())
