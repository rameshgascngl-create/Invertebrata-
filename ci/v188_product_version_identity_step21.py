#!/usr/bin/env python3
from __future__ import annotations

import hashlib
import sys
from pathlib import Path

INPUT_SHA256 = "bdb39d4fa1f438c5de83fc9a84ab33905016854c1f641227dbc23063c14c2c46"
INPUT_SIZE = 2393951
PAYLOADS = (
    Path("academic_payload/index.html"),
    Path("app/src/main/assets/www/index.html"),
)

REPLACEMENTS = (
    (
        "window.INVERTEBRATA_ILLUSTRATION_RELEASE={version:'1.8.3',",
        "window.INVERTEBRATA_ILLUSTRATION_RELEASE={version:'1.8.8',",
        "illustration release identity",
    ),
    (
        "aboutTitle:['INVERTEBRATA Theory Learning Resource v1.8.3','முதுகுநாணற்ற விலங்கியல் கோட்பாட்டுக் கற்றல் வளம் v1.8.3']",
        "aboutTitle:['INVERTEBRATA Theory Learning Resource v1.8.8','முதுகுநாணற்ற விலங்கியல் கோட்பாட்டுக் கற்றல் வளம் v1.8.8']",
        "bilingual About title",
    ),
    (
        "<p><strong>v1.8.3</strong> · Student textbook edition · audited vector anatomy · bilingual theory · offline-first</p>",
        "<p><strong>v1.8.8</strong> · Student textbook edition · audited vector anatomy · bilingual theory · offline-first</p>",
        "About edition line",
    ),
    (
        "version:'1.8.3',\n        study: JSON.parse(window.localStorage.getItem(STUDY_STATE_KEY)||'{}'),",
        "version:'1.8.8',\n        study: JSON.parse(window.localStorage.getItem(STUDY_STATE_KEY)||'{}'),",
        "exported study bundle version",
    ),
)


def sha256(data: bytes) -> str:
    return hashlib.sha256(data).hexdigest()


def require(condition: bool, message: str) -> None:
    if not condition:
        raise SystemExit(message)


def transform(text: str) -> str:
    raw = text.encode("utf-8")
    require(len(raw) == INPUT_SIZE,
            f"STEP-21 INPUT SIZE MISMATCH — ABORT expected={INPUT_SIZE} actual={len(raw)}")
    require(sha256(raw) == INPUT_SHA256,
            f"STEP-21 INPUT HASH MISMATCH — ABORT expected={INPUT_SHA256} actual={sha256(raw)}")

    original = text
    for before, after, label in REPLACEMENTS:
        count = text.count(before)
        require(count == 1, f"STEP-21 PRECONDITION MISMATCH — {label}: expected=1 actual={count}")
        text = text.replace(before, after, 1)

    # Release identity must no longer be stale in live product fields.
    require("INVERTEBRATA Theory Learning Resource v1.8.3" not in text,
            "STEP-21 STALE ABOUT TITLE REMAINS")
    require("<strong>v1.8.3</strong> · Student textbook edition" not in text,
            "STEP-21 STALE ABOUT EDITION REMAINS")
    require("version:'1.8.3',\n        study:" not in text,
            "STEP-21 STALE STUDY-BUNDLE VERSION REMAINS")
    require("INVERTEBRATA_ILLUSTRATION_RELEASE={version:'1.8.3'" not in text,
            "STEP-21 STALE ILLUSTRATION-RELEASE VERSION REMAINS")

    # Historical layer comments are provenance and must remain unchanged.
    require(text.count("v1.8.3") == 7,
            f"STEP-21 HISTORICAL COMMENT COUNT DRIFT expected=7 actual={text.count('v1.8.3')}")
    for line in (ln for ln in text.splitlines() if "v1.8.3" in ln):
        stripped = line.lstrip()
        require(stripped.startswith("/*") or stripped.startswith("<!--"),
                f"STEP-21 NON-HISTORICAL v1.8.3 REMAINS: {line[:120]}")

    # Exact reversibility proves only the four authorized substitutions changed.
    restored = text
    for before, after, _label in reversed(REPLACEMENTS):
        count = restored.count(after)
        require(count == 1, f"STEP-21 REVERSAL CARDINALITY MISMATCH: expected=1 actual={count}")
        restored = restored.replace(after, before, 1)
    require(restored == original, "STEP-21 UNAUTHORIZED SOURCE DRIFT")

    return text


def main() -> None:
    root = Path(sys.argv[1]).resolve() if len(sys.argv) > 1 else Path.cwd()
    a = root / PAYLOADS[0]
    b = root / PAYLOADS[1]

    require(a.is_file() and b.is_file(), "STEP-21 PAYLOAD COPIES MISSING")
    require(a.read_bytes() == b.read_bytes(), "STEP-21 INPUT PAYLOAD COPIES DIVERGED")
    require(sha256(a.read_bytes()) == INPUT_SHA256, "STEP-21 INPUT HASH MISMATCH — ABORT")

    output = transform(a.read_text(encoding="utf-8"))
    for path in (a, b):
        path.write_text(output, encoding="utf-8", newline="\n")

    require(a.read_bytes() == b.read_bytes(), "STEP-21 OUTPUT PAYLOAD COPIES DIVERGED")

    out = a.read_bytes()
    print(f"STEP21_INPUT_SHA256={INPUT_SHA256}")
    print("STEP21_AUTHORIZED_MUTATIONS=4")
    print("STEP21_LIVE_PRODUCT_VERSION=1.8.8")
    print("STEP21_HISTORICAL_VERSION_COMMENTS_PRESERVED=7")
    print(f"STEP21_OUTPUT_SHA256={sha256(out)}")
    print(f"STEP21_OUTPUT_SIZE={len(out)}")


if __name__ == "__main__":
    main()
