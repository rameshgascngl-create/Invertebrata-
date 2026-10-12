#!/usr/bin/env python3
"""Create the corrected Sycon WebP pixels used by native Unit II candidate builds.

An entry prosopyle must join incurrent space to a choanocyte-lined radial
canal, not the radial canal to the spongocoel. This is a candidate anatomy
reconstruction; a zoologist must still approve precise pore morphology.
No runtime overlay or screenshot crop is introduced.
"""
import hashlib
from pathlib import Path
from PIL import Image, ImageDraw

BASE_SHA256 = "1b7b8b0ceee848603c24581f60d87866abb03773570f83c43f54665bbb00c05a"
TARGET = Path("native-app/app/src/main/assets/unit2/sycon_plate.webp")
APERTURE_PX = (569, 724)
APOPYLE_PX = (648, 617)
SIZE_PX = (1000, 1250)

def main():
    before = TARGET.read_bytes()
    observed = hashlib.sha256(before).hexdigest()
    assert observed == BASE_SHA256, (
        f"Unexpected Sycon base master: {observed}; refusing to modify unknown bytes"
    )
    im = Image.open(TARGET).convert("RGB")
    assert im.size == SIZE_PX, f"Unexpected master dimensions: {im.size}"
    draw = ImageDraw.Draw(im)
    paper = (255, 255, 255)
    ink = (37, 37, 37)

    # Distal wall of the illustrated radial chamber, away from the
    # spongocoel. Cut a narrow source-pixel aperture at ~569,724.
    # This reconstruction is schematic, not a claim of a tissue micrograph.
    draw.polygon(
        [(553,719),(560,717),(567,720),(575,721),
         (581,723),(579,727),(569,726),(559,724),(553,724)],
        fill=paper
    )
    draw.line([(553,718),(560,716),(568,719),(577,721)],fill=(84,84,84),width=2)
    draw.line([(553,725),(559,725),(570,728),(580,728)],fill=(80,80,80),width=2)
    draw.line([(560,717),(568,720),(574,720)],fill=(138,138,138),width=1)
    draw.line([(558,725),(568,727),(574,727)],fill=(148,148,148),width=1)
    draw.line([(558,721),(567,723),(579,724)],fill=(205,205,205),width=1)

    # Repoint the *printed source-image leader* to the newly exposed
    # entry aperture. Retain authentic surrounding graphite textures and
    # avoid composable text masking or Android overlays.
    draw.line([(646,745),(620,751),(586,752),APERTURE_PX],fill=ink,width=2)
    draw.polygon([APERTURE_PX,(564,725),(568,731)],fill=ink)

    assert abs(APERTURE_PX[0]-APOPYLE_PX[0]) > 50
    assert abs(APERTURE_PX[1]-APOPYLE_PX[1]) > 75
    im.save(TARGET,format="WEBP",quality=92,method=6)
    result = hashlib.sha256(TARGET.read_bytes()).hexdigest()
    assert result != BASE_SHA256
    reloaded = Image.open(TARGET)
    assert reloaded.size == SIZE_PX
    print(f"SYCON_APERTURE_CANDIDATE SHA256={result} hotspot_px={APERTURE_PX}")
    print("ACADEMIC_REVIEW=OPEN; physical-device review=OPEN")

if __name__ == "__main__":
    main()
