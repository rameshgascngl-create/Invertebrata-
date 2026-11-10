#!/usr/bin/env python3
"""Generate a 512×512 Play/PhonePe listing icon from the shipped Android adaptive icon vector.

This is a raster export of the *same* existing launcher foreground, not alternative artwork.
Requires cairosvg installed in CI. Exact vector paths and source colours are preserved.
"""
from pathlib import Path
import re
import struct
import xml.etree.ElementTree as ET

import cairosvg

ROOT = Path(__file__).resolve().parents[1]
SRC = ROOT / "native-app/app/src/main/res/drawable/ic_launcher_foreground.xml"
COLORS = ROOT / "native-app/app/src/main/res/values/colors.xml"
DST = ROOT / "release/store-assets/INVERTEBRATA_icon_512.png"
ANDROID = "{http://schemas.android.com/apk/res/android}"


def svg_colour(value):
    if value is None:
        return "none", "1"
    if value.startswith("#") and len(value) == 9:
        return "#" + value[3:], str(round(int(value[1:3], 16) / 255, 4))
    return value, "1"


def make_svg():
    root = ET.parse(SRC).getroot()
    colors = ET.parse(COLORS).getroot()
    background = next(x.text.strip() for x in colors.findall("color")
                      if x.get("name") == "ic_launcher_background")
    parts = ['<svg xmlns="http://www.w3.org/2000/svg" width="512" height="512" viewBox="0 0 108 108">',
             f'<rect width="108" height="108" fill="{background}"/>']
    for path in root.findall("path"):
        a = path.attrib
        fill, fill_opacity = svg_colour(a.get(ANDROID + "fillColor"))
        stroke, stroke_opacity = svg_colour(a.get(ANDROID + "strokeColor"))
        op = float(stroke_opacity) * float(a.get(ANDROID + "strokeAlpha", "1"))
        path_data = a[ANDROID + "pathData"].replace("&", "&amp;").replace('"', "&quot;")
        parts.append(
            f'<path d="{path_data}" fill="{fill}" fill-opacity="{fill_opacity}" '
            f'stroke="{stroke}" stroke-opacity="{op:.4f}" '
            f'stroke-width="{a.get(ANDROID + "strokeWidth", "0")}" '
            f'stroke-linecap="{a.get(ANDROID + "strokeLineCap", "butt")}"/>')
    return "\n".join(parts) + "\n</svg>"


DST.parent.mkdir(parents=True, exist_ok=True)
cairosvg.svg2png(bytestring=make_svg().encode(), write_to=str(DST),
                output_width=512, output_height=512)
data = DST.read_bytes()
assert data[:8] == bytes((137,80,78,71,13,10,26,10))
assert struct.unpack(">II", data[16:24]) == (512,512)
assert len(data) <= 1_024 * 1_024, "PhonePe icon size must be <= 1 MB"
print(f"ICON_READY path={DST} dimensions=512x512 bytes={len(data)}")
