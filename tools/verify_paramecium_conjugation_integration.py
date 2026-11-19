#!/usr/bin/env python3
"""Focused acceptance gate for the NEW provisional conjugation figure only.

This does not rerun the previously accepted 43-chapter tests or other plates.
Use --apk to check exact bytes in a newly built APK.
"""
from __future__ import annotations
import argparse
import hashlib
from pathlib import Path
import sys
import zipfile

ROOT = Path(__file__).resolve().parents[1]
ASSET = "paramecium/conjugation-provisional.png"
EXPECTED = "5db2d73e912cae0cd96ead08cd2500d380592bee94904a4857897ad08bcc4181"
LIBRARY = ROOT / "native-app/app/src/main/java/com/gasczoology/invertebratelab/data/BookFigureLibrary.kt"
CHAPTER = ROOT / "native-app/app/src/main/java/com/gasczoology/invertebratelab/data/ParameciumBookChapter.kt"
READER = ROOT / "native-app/app/src/main/java/com/gasczoology/invertebratelab/ui/BookChapterReader.kt"


def check_bytes(data: bytes, label: str) -> None:
    actual = hashlib.sha256(data).hexdigest()
    assert actual == EXPECTED, f"{label}: incorrect SHA-256 {actual}"
    assert len(data) == 2521564, f"{label}: incorrect size"
    assert data[:8] == bytes.fromhex("89504e470d0a1a0a"), f"{label}: invalid PNG"
    assert (int.from_bytes(data[16:20], "big"), int.from_bytes(data[20:24], "big")) == (1672, 941), f"{label}: unexpected dimensions"


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("--apk", type=Path)
    args = parser.parse_args()
    try:
        check_bytes((ROOT / "native-app/app/src/main/assets" / ASSET).read_bytes(), "source PNG")
        library = LIBRARY.read_text(encoding="utf-8")
        chapter = CHAPTER.read_text(encoding="utf-8")
        reader = READER.read_text(encoding="utf-8")
        assert library.count('BookLibraryFigure("paramecium-conjugation-provisional"') == 1
        assert '"paramecium/conjugation-provisional"' in library
        assert chapter.count('bfig("lib/paramecium-conjugation-provisional"') == 1
        assert chapter.index('bfig("lib/paramecium-conjugation-provisional"') < chapter.index('btable("Stages of conjugation"')
        assert chapter.count('bfig("lib/paramecium-reproduction-approved"') == 1, "Original approved plate was removed or duplicated"
        assert "BookFigureLibrary.stemFor(figure.slot)" in reader
        assert "rememberCanonicalPlateBitmap(" in reader
        assert "expanded = true" in reader
        if args.apk:
            with zipfile.ZipFile(args.apk) as archive:
                name = "assets/" + ASSET
                assert archive.namelist().count(name) == 1, f"APK must contain exactly one {name}"
                check_bytes(archive.read(name), "APK PNG")
    except (AssertionError, OSError, zipfile.BadZipFile) as exc:
        print(f"FAIL: {exc}", file=sys.stderr)
        return 1
    print("PASS: exact provisional PNG, separate figure slot, bilingual chapter placement and existing zoom entry")
    if args.apk:
        print("PASS: exact PNG packaged once in APK")
    print("NOT VERIFIED: emulator rendering, visual clarity, scientific/Tamil/rights sign-off")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
