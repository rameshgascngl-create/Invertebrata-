#!/usr/bin/env python3
"""Focused privacy and accessibility source contract for the existing native app.

Does not change lessons, images, or previously accepted tests. Static source
checks are not a substitute for TalkBack or emulator acceptance.
"""
from pathlib import Path
import sys
import xml.etree.ElementTree as ET

ROOT = Path(__file__).resolve().parents[1]
UI = ROOT / "native-app/app/src/main/java/com/gasczoology/invertebratelab/ui/InvertebrataApp.kt"
MANIFEST = ROOT / "native-app/app/src/main/AndroidManifest.xml"
READER = ROOT / "native-app/app/src/main/java/com/gasczoology/invertebratelab/ui/BookChapterReader.kt"

def main() -> int:
    ui = UI.read_text(encoding="utf-8")
    reader = READER.read_text(encoding="utf-8")
    manifest = ET.parse(MANIFEST).getroot()
    ns = "{http://schemas.android.com/apk/res/android}"
    errors = []
    requirements = {
        "Bilingual privacy title": ('"Privacy policy"', '"தனியுரிமைக் கொள்கை"'),
        "Privacy open control": ('testTag("privacy-policy-open")', 'showPrivacy = true'),
        "Privacy close control": ('testTag("privacy-close")', 'showPrivacy = false'),
        "Privacy dialog": ('testTag("privacy-dialog")', 'onDismissRequest = { showPrivacy = false }'),
        "Local-data disclosure": ('"The app stores language selection, reading progress, bookmarks', 'locally on this device'),
        "Offline disclosure": ('does not require an account or request Internet access',),
        "Reader image accessibility": ('contentDescription = caption', 'Open full-size image'),
    }
    for label, tokens in requirements.items():
        source = reader if label.startswith("Reader") else ui
        if not all(token in source for token in tokens):
            errors.append(label + ": missing expected source contract")
    if manifest.find("application").get(ns + "allowBackup") != "false":
        errors.append("Automatic backup is not disabled")
    permissions = [node.get(ns + "name") for node in manifest.findall("uses-permission")]
    if "android.permission.INTERNET" in permissions:
        errors.append("Internet permission conflicts with the offline notice")
    for error in errors:
        print("FAIL:", error, file=sys.stderr)
    if errors:
        return 1
    print("PASS: existing bilingual privacy entry, dialog, local-data disclosure, and image semantics")
    print("PASS: manifest backup and Internet permission agree with in-app notice")
    print("PENDING: real-device TalkBack, 200% text scaling and policy legal review")
    return 0

if __name__ == "__main__":
    raise SystemExit(main())
