#!/usr/bin/env python3
"""Fail closed if release artifacts do not match their recorded build provenance."""
from pathlib import Path
import argparse
import hashlib
import sys

ROOT = Path(__file__).resolve().parents[1]
APK = ROOT / "native-app/app/build/outputs/apk/release/app-release-unsigned.apk"
AAB = ROOT / "native-app/app/build/outputs/bundle/release/app-release.aab"
EVIDENCE = ROOT / "native-app/app/build/release-evidence"

def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("--expected-commit", required=True)
    args = parser.parse_args()
    sha = args.expected_commit.strip().lower()
    if len(sha) != 40 or any(c not in "0123456789abcdef" for c in sha):
        print("FAIL: expected commit must be a full 40-character SHA", file=sys.stderr)
        return 1
    try:
        actual = (EVIDENCE / "commit-sha.txt").read_text().strip().lower()
        if actual != sha:
            raise ValueError("build commit does not match triggering commit")
        recorded = (EVIDENCE / "SHA256SUMS").read_text().splitlines()
        expected = {}
        for line in recorded:
            parts = line.split(maxsplit=1)
            if len(parts) != 2 or len(parts[0]) != 64:
                raise ValueError("invalid SHA256SUMS entry")
            expected[parts[1].lstrip("*")] = parts[0].lower()
        for artifact in (APK, AAB):
            key = str(artifact.relative_to(ROOT))
            if key not in expected:
                raise ValueError("missing checksum for " + key)
            actual_hash = hashlib.file_digest(artifact.open("rb"), "sha256").hexdigest()
            if actual_hash != expected[key]:
                raise ValueError("checksum mismatch for " + key)
    except (OSError, ValueError) as exc:
        print("FAIL:", exc, file=sys.stderr)
        return 1
    print("PASS: APK and AAB SHA-256 checksums match provenance at commit", sha)
    return 0

if __name__ == "__main__":
    raise SystemExit(main())
