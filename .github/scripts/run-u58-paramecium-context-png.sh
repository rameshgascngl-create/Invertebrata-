#!/usr/bin/env bash
set -euo pipefail
E="qa-evidence/u58-reader"
mkdir -p "$E" "$E/captured"
result=NOT_STARTED
trap 'code=$?; printf "commit=%s\nresult=%s\nexit=%s\n" "$GITHUB_SHA" "$result" "$code" > "$E/status.txt"; adb pull /sdcard/Download/u57-qa "$E/captured/u57" > "$E/u57-pull.log" 2>&1 || true; adb pull /sdcard/Download/u58-qa "$E/captured/u58" > "$E/u58-pull.log" 2>&1 || true; adb logcat -d -v threadtime > "$E/logcat.txt" 2>&1 || true' EXIT
adb shell mkdir -p /sdcard/Download/u57-qa /sdcard/Download/u58-qa
adb shell settings put system accelerometer_rotation 0
adb shell settings put system user_rotation 0
adb shell cmd connectivity airplane-mode enable
adb shell svc wifi disable
adb shell svc data disable
for scale in 1.0 2.0; do
    adb shell settings put system font_scale "$scale"
    actual="$(adb shell settings get system font_scale | tr -d '\r')"
    [[ "$actual" == "$scale" ]] || { result="FONT_SCALE_INVALID_$actual";exit 1; }
    for suite in NativeU57ParameciumProcessPngTest NativeU58ParameciumReaderPngTest; do
        result="RUNNING_$scale-$suite"
        root="$E/scale-$scale/$suite"
        mkdir -p "$root"
        adb shell dumpsys connectivity > "$root/network.txt"
        rm -rf native-app/app/build/outputs/androidTest-results/connected
        if ! gradle -p native-app :app:connectedDebugAndroidTest --stacktrace \
           "-Pandroid.testInstrumentationRunnerArguments.class=com.gasczoology.invertebratelab.$suite" \
           > "$root/gradle.txt" 2>&1; then
            cp -a native-app/app/build/outputs/androidTest-results/connected "$root/xml" 2>/dev/null || true
            result="ANDROID_UI_FAIL_$scale-$suite"
            exit 1
        fi
        cp -a native-app/app/build/outputs/androidTest-results/connected "$root/xml"
        python3 - "$root/xml" "$suite" <<'PY'
from pathlib import Path
import xml.etree.ElementTree as ET
import sys
files=list(Path(sys.argv[1]).rglob("TEST-*.xml"))
cases=[t for p in files for t in ET.parse(p).getroot().iter("testcase")]
assert len(cases)==1,("Unexpected test count",len(cases))
assert cases[0].get("classname")=="com.gasczoology.invertebratelab."+sys.argv[2]
assert all(cases[0].find(x) is None for x in ("failure","error","skipped"))
print("UNIT1_OFFLINE_TEST=PASS",sys.argv[2])
PY
    done
done
adb pull /sdcard/Download/u57-qa "$E/captured/u57" > "$E/u57-pull-final.log"
adb pull /sdcard/Download/u58-qa "$E/captured/u58" > "$E/u58-pull-final.log"
for scale in 100 200; do
    for view in vacuole-source-png vacuole-png-enlarged conjugation-text-no-vector; do
        [[ -s "$E/captured/u57/paramecium-$scale-$view.png" ]] || { result="MISSING_U57_$scale-$view";exit 1;}
    done
    for view in external-png-fullscreen water-balance-png-fullscreen other-lesson-no-duplicate-image; do
        [[ -s "$E/captured/u58/reader-$scale-$view.png" ]] || { result="MISSING_U58_$scale-$view";exit 1;}
    done
done
# JUnit TEST-*.xml is not a UI hierarchy; capture real accessibility trees.
# The test preserves hierarchy XML beside the PNG in /sdcard/Download/u58-qa.
# Both are already pulled together above, before validation.
python3 - "$E" <<'PY'
from pathlib import Path
import xml.etree.ElementTree as ET
import json, re, sys
root=Path(sys.argv[1])
screenshot=root/"captured/u58"
hierarchy=screenshot
scenes=("external-png-fullscreen","water-balance-png-fullscreen",
        "nuclear-overview-figure","fission-figure","conjugation-figure",
        "other-lesson-no-duplicate-image")
report=[]
for scale in ("100","200"):
    for scene in scenes:
        stem=f"reader-{scale}-{scene}"
        png=screenshot/(stem+".png")
        xml=hierarchy/(stem+".hierarchy.xml")
        assert png.is_file() and png.stat().st_size>12000,("SCREENSHOT_MISSING",str(png))
        assert xml.is_file() and xml.stat().st_size>300,("UI_HIERARCHY_MISSING",str(xml))
        tree=ET.parse(xml).getroot()
        nodes=tree.findall(".//node")
        assert tree.tag=="hierarchy" and tree.attrib["scene"]==scene
        assert tree.attrib["font-scale"]==scale and len(nodes)>=2,(stem,len(nodes))
        assert any(n.attrib.get("bounds") for n in nodes),(stem,"NO_SCREEN_BOUNDS")
        texts=" ".join((n.attrib.get("text","")+" "+n.attrib.get("content-desc","")) for n in nodes)
        report.append({"scene":scene,"font_scale":scale,
          "screenshot":str(png.relative_to(root)),
          "ui_hierarchy_xml":str(xml.relative_to(root)),
          "accessibility_nodes":len(nodes),
          "tamil_accessibility_text_observed":bool(re.search("[\\u0B80-\\u0BFF]",texts)),
          "anatomy_and_full_scroll_certified":False})
(root/"ui-hierarchy-summary.json").write_text(json.dumps({
 "source":"Android UiAutomation.rootInActiveWindow; not JUnit XML",
 "scope":"6 selected foreground Paramecium views x normal and 200% Tamil font",
 "count":len(report),"scenes":report,
 "academic_status":"HOLD: not all units, scroll positions, or plate leader endpoints covered"
},indent=2,ensure_ascii=False)+"\n",encoding="utf-8")
assert len(report)==12
print("REAL_ANDROID_UI_HIERARCHY_XML_AND_SCREENSHOT_PAIRS=12; SCIENTIFIC_QA=HOLD")
PY
result=PASS
