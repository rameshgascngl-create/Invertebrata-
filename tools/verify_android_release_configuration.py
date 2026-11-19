#!/usr/bin/env python3
"""Read-only Android release configuration checks; no figure or lesson changes."""
from pathlib import Path
import re
import sys
import xml.etree.ElementTree as ET

root = Path(__file__).resolve().parents[1]
manifest = root / "native-app/app/src/main/AndroidManifest.xml"
gradle = root / "native-app/app/build.gradle.kts"
errors = []
try:
    tree = ET.parse(manifest)
    ns = "{http://schemas.android.com/apk/res/android}"
    application = tree.getroot().find("application")
    if application is None:
        errors.append("No application node")
    else:
        if application.get(ns + "allowBackup") != "false":
            errors.append("Backup must be explicitly disabled or separately audited")
        activities = application.findall("activity")
        launcher = [a for a in activities if any(
            i.find("action") is not None and i.find("action").get(ns + "name") == "android.intent.action.MAIN"
            for i in a.findall("intent-filter")
        )]
        if len(launcher) != 1 or launcher[0].get(ns + "exported") != "true":
            errors.append("Expected exactly one explicitly exported launcher activity")
        for a in activities:
            if a not in launcher and a.get(ns + "exported") == "true":
                errors.append("Unexpected exported activity: " + str(a.get(ns + "name")))
    for perm in tree.getroot().findall("uses-permission"):
        name = perm.get(ns + "name", "")
        if name in ("android.permission.INTERNET", "android.permission.READ_EXTERNAL_STORAGE",
                    "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.MANAGE_EXTERNAL_STORAGE"):
            errors.append("Unexpected permission: " + name)
except ET.ParseError as exc:
    errors.append("Manifest XML invalid: " + str(exc))

s = gradle.read_text(encoding="utf-8")
for token in ('minSdk = 24', 'targetSdk = 36', 'isMinifyEnabled = true', 'isShrinkResources = true'):
    if token not in s:
        errors.append("Release configuration missing: " + token)
if not re.search(r'release\s*\{[\s\S]*?if \(functionalRelease\) signingConfig = signingConfigs\.getByName\("debug"\)', s):
    errors.append("Cannot establish debug signing is limited to functionalRelease")
if errors:
    for error in errors:
        print("FAIL:", error, file=sys.stderr)
    raise SystemExit(1)
print("PASS: manifest parses, launcher exposure constrained, backup disabled, restricted permissions absent")
print("PASS: release minification and resource shrinking configured")
print("NOTE: Store signing, in-app privacy notice, accessibility, device QA and Play policy still require independent checks")
