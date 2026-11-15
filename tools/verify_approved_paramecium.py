#!/usr/bin/env python3
"""Verify the accepted Paramecium illustration without modifying the source ZIP.

Checks the committed integration package, its exact PNG, the native Gradle
staging hook, and the bilingual book chapter placement. Rendering and CI
acceptance are separate checks.
"""
from __future__ import annotations

import hashlib
import argparse
import pathlib
import sys
import zipfile

ROOT = pathlib.Path(__file__).resolve().parents[1]
SHA256 = "8f54a66d89843688d64108cc6ebacae35db497e2bbb8bcbef4c959eefbb44d51"
ZIP = ROOT / "Paramecium_Approved_Integration_Kit.zip"
ENTRY = "paramecium_integration/assets/paramecium-reproduction-approved.png"
CHAPTER = ROOT / "native-app/app/src/main/java/com/gasczoology/invertebratelab/data/ParameciumBookChapter.kt"
GRADLE = ROOT / "native-app/app/build.gradle.kts"
SLOT = "lib/paramecium-reproduction-approved"


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument('--apk', type=pathlib.Path)
    args = parser.parse_args()
    if not ZIP.is_file():
        print(f"FAIL: committed integration archive missing: {ZIP}")
        return 1
    try:
        with zipfile.ZipFile(ZIP) as archive:
            matches = [name for name in archive.namelist() if name == ENTRY]
            if len(matches) != 1:
                print(f"FAIL: expected exactly one approved PNG archive entry; found {len(matches)}")
                return 1
            data = archive.read(ENTRY)
    except (zipfile.BadZipFile, OSError, RuntimeError) as exc:
        print(f"FAIL: cannot read approved image archive: {exc}")
        return 1

    actual = hashlib.sha256(data).hexdigest()
    if actual != SHA256:
        print(f"FAIL: image checksum mismatch: {actual}")
        return 1
    if data[:8] != bytes.fromhex("89504e470d0a1a0a"):
        print("FAIL: image does not have PNG signature")
        return 1
    size = (int.from_bytes(data[16:20], "big"), int.from_bytes(data[20:24], "big"))
    if size != (1536, 1024):
        print(f"FAIL: image dimensions {size}, expected (1536, 1024)")
        return 1

    gradle = GRADLE.read_text(encoding="utf-8")
    if not all(token in gradle for token in (
        "stageApprovedParamecium", "generated/approvedParameciumAssets",
        "paramecium/reproduction-approved.png", SHA256,
        "dependsOn(stageApprovedParamecium)",
    )):
        print("FAIL: Android Gradle staging configuration incomplete")
        return 1

    chapter = CHAPTER.read_text(encoding="utf-8")
    figure = f'bfig("{SLOT}"'
    table = 'btable("Stages of conjugation"'
    if chapter.count(figure) != 1 or table not in chapter:
        print("FAIL: approved figure missing, duplicated, or conjugation table absent")
        return 1
    if chapter.index(figure) > chapter.index(table):
        print("FAIL: approved figure must precede conjugation table")
        return 1

    library_path = ROOT / "native-app/app/src/main/java/com/gasczoology/invertebratelab/data/BookFigureLibrary.kt"
    reader_path = ROOT / "native-app/app/src/main/java/com/gasczoology/invertebratelab/ui/BookChapterReader.kt"
    library = library_path.read_text(encoding="utf-8")
    reader = reader_path.read_text(encoding="utf-8")
    registered = 'BookLibraryFigure("paramecium-reproduction-approved", "paramecium/reproduction-approved"'
    if library.count(registered) != 1:
        print("FAIL: approved figure must be registered exactly once with its actual asset stem")
        return 1
    if 'BookFigureLibrary.stemFor(figure.slot)' not in reader:
        print("FAIL: book reader no longer resolves registered library figures")
        return 1
    if 'rememberCanonicalPlateBitmap(' not in reader:
        print("FAIL: book reader no longer loads canonical plate bitmap")
        return 1
    print("PASS: approved figure registered in book figure library and used by reader")

    if args.apk:
        if not args.apk.is_file():
            print(f'FAIL: APK missing: {args.apk}')
            return 1
        try:
            with zipfile.ZipFile(args.apk) as apk:
                name = 'assets/paramecium/reproduction-approved.png'
                if apk.namelist().count(name) != 1:
                    print(f'FAIL: approved PNG not packaged exactly once at {name}')
                    return 1
                packaged = apk.read(name)
        except (zipfile.BadZipFile, OSError, RuntimeError) as exc:
            print(f'FAIL: APK inspection error: {exc}')
            return 1
        if hashlib.sha256(packaged).hexdigest() != SHA256:
            print('FAIL: packaged PNG differs from approved image')
            return 1
        print('PASS: approved PNG is packaged unchanged in APK')
    print(f"PASS: committed ZIP and approved PNG (SHA-256 {SHA256})")
    print("PASS: PNG dimensions 1536x1024 and valid PNG signature")
    print("PASS: Gradle asset staging hook and bilingual chapter figure placement")
    print("PASS: source-level figure registry and reader resolver checks")
    if args.apk:
        print("PASS: APK-level approved-image integrity check")
    else:
        print("NOT VERIFIED: APK packaging (run with --apk after assembly)")
    print("NOT VERIFIED HERE: Kotlin compilation, emulator rendering, visible textbook page")
    return 0


if __name__ == "__main__":
    sys.exit(main())
