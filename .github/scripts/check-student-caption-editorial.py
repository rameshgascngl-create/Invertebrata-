#!/usr/bin/env python3
"""Static regression gate for student-facing native figure captions.

This does not certify scientific accuracy or replace Android build/device tests.
"""
from pathlib import Path

UI = Path("native-app/app/src/main/java/com/gasczoology/invertebratelab/ui")
checks = {
    "BookChapterReader.kt": (
        "Figure unavailable.",
        "படம் கிடைக்கவில்லை.",
        'testTag("r2-plate-missing-$tag")',
    ),
    "ParameciumNutritionTextbook.kt": (
        'testTag("r19-plate-legend")',
        'testTag("r19-plate-orientation")',
        'testTag("r19-plate-scientific-note")',
        "Figure key:",
        "படக் குறியீடுகள்:",
        "measured pH",
    ),
    "ParameciumEgestionPlate.kt": (
        'testTag("r19-egestion-legend")',
        'testTag("r19-egestion-orientation")',
        'testTag("r19-egestion-scientific-note")',
        "cytoproct (egestion opening)",
        "சைட்டோப்ராக்ட்",
        "not direct measurements",
    ),
    "UnitIVEarthwormExternalAtlas.kt": (
        "40 representative body segments",
        "40 உடற்கண்டங்கள்",
        'testTag("u43-earthworm-schematic-notice")',
    ),
}
obsolete = {
    "BookChapterReader.kt": ("This figure is not available in this build.",),
    "ParameciumEgestionPlate.kt": ("Play and hold a real intermediate egestion frame",),
    "UnitIVEarthwormExternalAtlas.kt": ("NOT a pencil scan", "உண்மை பென்சில் ஸ்கேன் அல்ல"),
}
errors = []
for filename, expected in checks.items():
    file = UI / filename
    if not file.is_file():
        errors.append(f"{file}: missing")
        continue
    source = file.read_text(encoding="utf-8")
    for phrase in expected:
        if phrase not in source:
            errors.append(f"{file}: missing required text {phrase!r}")
    for phrase in obsolete.get(filename, ()):
        if phrase in source:
            errors.append(f"{file}: obsolete student-facing text {phrase!r}")

if errors:
    raise SystemExit("\n".join(errors))
print("STUDENT_CAPTION_EDITORIAL_CONTRACT=PASS (4 source files)")
