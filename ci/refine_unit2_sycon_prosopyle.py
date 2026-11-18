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
APERTURE_PX = (595, 751)
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

    # Correct topology: the RADIAL CANAL HAS A BLIND OUTER TIP.
    # Prosopyles pierce its LATERAL WALL from the intervening incurrent
    # canal (between adjacent radial chambers), not the blind distal tip.
    # Carve a modest pore across the basal-lateral wall at (595,751).
    # This is a schematic candidate, not a claim of histological proof.
    draw.ellipse((591,746,599,755),fill=paper)
    draw.arc((589,744,601,758),110,265,fill=(88,88,88),width=1)
    draw.arc((589,744,601,758),295,78,fill=(102,102,102),width=1)

    # Repoint the PRINTED WebP leader within the source pixels.
    # Its endpoint aligns with the pore opening in the lateral wall.
    # No runtime labels or Canvas text masks.
    draw.line([(647,745),(630,749),(610,758),APERTURE_PX],fill=ink,width=2)
    draw.polygon([APERTURE_PX,(600,754),(594,757)],fill=ink)

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
