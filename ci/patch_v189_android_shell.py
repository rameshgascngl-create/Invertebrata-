#!/usr/bin/env python3
"""
Apply INVERTEBRATA v1.8.9 Android-shell build optimization changes to an
already reconstructed, accepted v1.8.8 source tree.

Academic payload is deliberately not modified.
"""
from __future__ import annotations

import argparse
import hashlib
import re
from pathlib import Path

PROGUARD = r"""# INVERTEBRATA v1.8.9 — targeted R8/ProGuard rules

# Preserve annotation metadata required by annotation-driven runtime entry points.
-keepattributes RuntimeVisibleAnnotations,RuntimeInvisibleAnnotations,AnnotationDefault

# WebView JavaScript -> Java/Kotlin bridge methods.
-keepclassmembers,allowoptimization class * {
    @android.webkit.JavascriptInterface <methods>;
}

# Explicit runtime/reflection entry points marked with androidx.annotation.Keep.
-keep @androidx.annotation.Keep class * {
    *;
}

-keepclassmembers,allowoptimization class * {
    @androidx.annotation.Keep <fields>;
    @androidx.annotation.Keep <methods>;
    @androidx.annotation.Keep <init>(...);
}

# Intentionally no global shrink/optimization/obfuscation disablers and
# no blanket package-wide keep rules.
"""

SYNC_BLOCK = r"""
// -----------------------------------------------------------------------------
// Web asset synchronization
// -----------------------------------------------------------------------------
// frontend/dist is the canonical compiled web distribution consumed by Android.
// Sync (rather than Copy) removes stale files from assets/www before packaging.
def webDistDir = file("$rootDir/frontend/dist")
def androidWebAssetsDir = file("$projectDir/src/main/assets/www")

def copyWebAssets = tasks.register('copyWebAssets', Sync) {
    group = 'build setup'
    description = 'Synchronizes compiled frontend assets into Android assets/www.'

    from(webDistDir)
    into(androidWebAssetsDir)
    includeEmptyDirs = false

    doFirst {
        if (!webDistDir.exists()) {
            throw new GradleException(
                "Frontend distribution directory does not exist: ${webDistDir}. " +
                "Build/stage the frontend into frontend/dist before Android compilation."
            )
        }
    }
}

"""

PREBUILD_BLOCK = r"""

// Lazy lifecycle binding: web assets must be synchronized before Android pre-build.
tasks.named('preBuild').configure {
    dependsOn(copyWebAssets)
}
"""


def sha256(path: Path) -> str:
    return hashlib.sha256(path.read_bytes()).hexdigest()


def require(condition: bool, message: str) -> None:
    if not condition:
        raise SystemExit(message)


def main() -> None:
    ap = argparse.ArgumentParser()
    ap.add_argument("source_root", type=Path)
    args = ap.parse_args()
    root = args.source_root.resolve()

    app_gradle = root / "app/build.gradle"
    root_gradle = root / "build.gradle"
    proguard = root / "app/proguard-rules.pro"
    manifest = root / "app/src/main/AndroidManifest.xml"
    academic = root / "academic_payload/index.html"
    android_asset = root / "app/src/main/assets/www/index.html"

    for p in (app_gradle, root_gradle, proguard, manifest, academic, android_asset):
        require(p.is_file(), f"required source file missing: {p}")

    academic_before = sha256(academic)
    asset_before = sha256(android_asset)
    require(academic_before == asset_before,
            "academic_payload/index.html and Android asset diverge before v1.8.9 shell patch")

    root_text = root_gradle.read_text(encoding="utf-8")
    require("version '8.13.0'" in root_text,
            "AGP baseline is not exactly 8.13.0; refusing an implicit plugin upgrade")

    text = app_gradle.read_text(encoding="utf-8")
    require("applicationId 'com.gasczoology.invertebratelab'" in text,
            "unexpected applicationId")
    require("versionCode 18800" in text and "versionName '1.8.8'" in text,
            "expected v1.8.8 Gradle identity not found")

    text = text.replace("versionCode 18800", "versionCode 18900", 1)
    text = text.replace("versionName '1.8.8'", "versionName '1.8.9'", 1)

    require("copyWebAssets" not in text, "copyWebAssets already exists; refusing duplicate task")
    marker = "android {"
    require(marker in text, "android block not found")
    text = text.replace(marker, SYNC_BLOCK + marker, 1)

    debug_re = re.compile(r"(debug\s*\{\s*\n)(?P<body>.*?)(\n\s*\})", re.S)
    m = debug_re.search(text)
    require(m is not None, "debug buildType not found")
    debug_body = m.group("body")
    if "minifyEnabled" not in debug_body:
        indent = "            "
        new_body = debug_body.rstrip() + f"\n{indent}minifyEnabled false"
        text = text[:m.start("body")] + new_body + text[m.end("body"):]

    require(re.search(r"release\s*\{.*?minifyEnabled\s+true", text, re.S) is not None,
            "release minifyEnabled true missing")
    require(re.search(r"release\s*\{.*?shrinkResources\s+true", text, re.S) is not None,
            "release shrinkResources true missing")
    require("getDefaultProguardFile('proguard-android-optimize.txt')" in text,
            "optimized default ProGuard configuration missing")
    require("'proguard-rules.pro'" in text, "app proguard-rules.pro binding missing")

    text = text.rstrip() + PREBUILD_BLOCK + "\n"
    app_gradle.write_text(text, encoding="utf-8", newline="\n")

    proguard.write_text(PROGUARD, encoding="utf-8", newline="\n")

    manifest_text = manifest.read_text(encoding="utf-8")
    require("android.permission.INTERNET" not in manifest_text,
            "unexpected INTERNET permission in source manifest")
    require('android:usesCleartextTraffic="false"' in manifest_text,
            "usesCleartextTraffic=false missing")
    require("android:versionName" not in manifest_text and "android:versionCode" not in manifest_text,
            "version fields should remain single-sourced in Gradle under AGP")

    require(sha256(academic) == academic_before, "academic payload changed during shell patch")
    require(sha256(android_asset) == asset_before, "Android web asset changed during shell patch")

    print("V189_ANDROID_SHELL_PATCH=PASS")
    print("APPLICATION_ID=com.gasczoology.invertebratelab")
    print("VERSION_NAME=1.8.9")
    print("VERSION_CODE=18900")
    print("AGP_VERSION=8.13.0")
    print(f"ACADEMIC_PAYLOAD_SHA256={academic_before}")
    print(f"ANDROID_ASSET_SHA256={asset_before}")
    print("ACADEMIC_PAYLOAD_UNCHANGED=PASS")


if __name__ == "__main__":
    main()
