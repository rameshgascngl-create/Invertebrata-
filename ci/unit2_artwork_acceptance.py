#!/usr/bin/env python3
"""Independent Unit II artwork/provenance gate.

Engineering mode validates exact candidate raster bytes and records unresolved
scientific review, without preventing emulator/device QA. Release mode is fail
closed: neither a green Kotlin build nor raster asset presence is approval.
"""
import argparse
import hashlib
import json
from pathlib import Path
import sys

from PIL import Image

FIELDS = (
    "anatomy_approved",
    "tamil_approved",
    "hotspot_approved",
    "provenance_approved",
    "rights_approved",
)
EXPECTED_IDS = {"sycon_plate", "obelia_colony", "obelia_lifecycle"}


def check(root, mode, assets_override=None):
    root = Path(root)
    manifest = json.loads((root / "qa/unit2/artwork_review_manifest.json").read_text(encoding="utf-8"))
    assets = Path(assets_override) if assets_override is not None else (
        root / "native-app/app/src/main/assets/unit2"
    )
    errors, holds, plates = [], [], []
    required_size = manifest["minimum_legible_resolution"]
    source = (
        root / "native-app/app/src/main/java/com/gasczoology/invertebratelab/ui/UnitIIImagePlate.kt"
    )
    chapter = (
        root / "native-app/app/src/main/java/com/gasczoology/invertebratelab/ui/UnitIITeachingChapter.kt"
    )
    if not source.is_file() or not chapter.is_file():
        errors.append("Missing native Unit II renderer/lesson source")
    else:
        ui = source.read_text(encoding="utf-8")
        lesson = chapter.read_text(encoding="utf-8")
        for marker in (
            "UnitIILessonPencilPlate", "UnitIIImagePlate",
            "u2-raster-bitmap-visible", "u2-sycon-lesson-raster",
        ):
            if marker not in ui:
                errors.append("Renderer contract missing: " + marker)
        for marker in ("u2-pencil-lesson-section", "u2-interactive-atlas"):
            if marker not in lesson:
                errors.append("Lesson/interactive separation missing: " + marker)
        if "SyconCanalCanvas" in lesson or "ObeliaColonyCanvas" in lesson:
            errors.append("Legacy schematic fallback reintroduced into Unit II")

    registered = {p.get("id") for p in manifest.get("plates", [])}
    if registered != EXPECTED_IDS or len(manifest.get("plates", [])) != len(EXPECTED_IDS):
        errors.append("Manifest must cover each of the three distinct unit II plates exactly once")

    for plate in manifest.get("plates", []):
        plate_id = plate.get("id", "missing-id")
        filename = plate.get("filename", "")
        image_path = assets / filename
        stat = {"id": plate_id, "file": filename, "status": "UNKNOWN"}
        if filename not in {plate_id + ".png", plate_id + ".webp"}:
            errors.append(f"{plate_id}: invalid raster filename")
        # Runtime prefers PNG. A newly supplied PNG is a different artwork and
        # cannot bypass the image review merely because old WebP stays present.
        preferred_png = assets / (plate_id + ".png")
        if preferred_png.is_file() and image_path != preferred_png:
            errors.append(f"{plate_id}: unreviewed PNG would shadow the pinned WebP")
        if not image_path.is_file():
            errors.append(f"{plate_id}: required image missing ({filename})")
            plates.append(stat)
            continue
        raw = image_path.read_bytes()
        observed_sha = hashlib.sha256(raw).hexdigest()
        if observed_sha != plate.get("sha256"):
            errors.append(f"{plate_id}: SHA-256 mismatch: {observed_sha}")
        try:
            with Image.open(image_path) as im:
                im.verify()
            with Image.open(image_path) as im:
                dimensions = (im.width, im.height)
                raster_type = im.format
            if dimensions != (plate.get("width"), plate.get("height")):
                errors.append(f"{plate_id}: dimensions differ from approved candidate manifest: {dimensions}")
            if raster_type not in {"PNG", "WEBP"}:
                errors.append(f"{plate_id}: not a decoded PNG or WebP raster")
            if dimensions[0] < required_size["width"] or dimensions[1] < required_size["height"]:
                holds.append(f"{plate_id}: insufficient legibility resolution {dimensions}")
            stat.update(size=dimensions, sha256=observed_sha, format=raster_type)
        except (OSError, ValueError) as exc:
            errors.append(f"{plate_id}: image decode failed: {exc}")

        checks = plate.get("review", {})
        if set(checks) != set(FIELDS) or any(not isinstance(checks.get(k), bool) for k in FIELDS):
            errors.append(f"{plate_id}: incomplete reviewer gate fields")
        for key in FIELDS:
            if checks.get(key) is not True:
                holds.append(f"{plate_id}: {key} not accepted")
        if plate.get("source_type") != "verified_hand_scanned":
            holds.append(f"{plate_id}: genuine hand-scan provenance NOT VERIFIED")
        provenance = plate.get("source_documentation")
        if not provenance or not (root / provenance).is_file():
            holds.append(f"{plate_id}: source/authorship evidence document missing")
        records = plate.get("evidence", [])
        if not records or not all(isinstance(p, str) and (root / p).is_file() for p in records):
            holds.append(f"{plate_id}: independent review evidence files missing")
        stat["status"] = "TECHNICAL_OK" if not any(
            e.startswith(plate_id + ":") for e in errors
        ) else "TECHNICAL_FAIL"
        plates.append(stat)

    physical = manifest.get("physical_device", {})
    if physical.get("status") != "PASS" or not physical.get("evidence"):
        holds.append("physical Android device QA evidence not accepted")
    if manifest.get("no_approval_inferred_from_ci") is not True:
        errors.append("CI cannot infer scientific approval")

    engineering_ok = not errors
    release_ok = engineering_ok and not holds
    report = {
        "scope": manifest.get("scope"),
        "mode": mode,
        "engineering_integrity": "PASS" if engineering_ok else "FAIL",
        "academic_release": "PASS" if release_ok else "HOLD",
        "plates": plates,
        "technical_errors": errors,
        "approval_blockers": holds,
    }
    return report, engineering_ok if mode == "engineering" else release_ok


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("--mode", choices=["engineering", "release"], default="engineering")
    parser.add_argument("--root", default=".")
    parser.add_argument("--assets-dir")
    parser.add_argument("--report", default="qa/unit2/artwork_gate_result.json")
    args = parser.parse_args()
    report, passed = check(args.root, args.mode, args.assets_dir)
    output = Path(args.report)
    output.parent.mkdir(parents=True, exist_ok=True)
    output.write_text(json.dumps(report, indent=2) + "\n", encoding="utf-8")
    print(json.dumps({
        "engineering_integrity": report["engineering_integrity"],
        "academic_release": report["academic_release"],
        "approval_blocker_count": len(report["approval_blockers"]),
        "technical_error_count": len(report["technical_errors"]),
        "report": str(output),
    }, indent=2))
    return 0 if passed else 1


if __name__ == "__main__":
    sys.exit(main())
