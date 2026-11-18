#!/usr/bin/env python3
"""Restores the source WebP pixels. Never render corrected labels as a runtime overlay.

The anatomy of the illustrated openings and leader endpoints remains provisional
until independent zoological review. Use only on the isolated Unit II candidate.
"""
import hashlib
from pathlib import Path
from PIL import Image,ImageDraw,ImageFont

BASE={
"sycon_plate.webp":"8a3fae39bbc0c594ce603b3b1270111f45bfe775c53525a56deefc0624bd9c55",
"obelia_colony.webp":"b6f746f2ecd24f714056e8ecf7882aa8f9ac196b86657f5ead2c1fe3cf4eddaa",
"obelia_lifecycle.webp":"2ee1ee163a363d2574b3af294cffde4adabf98b0246a031878e046a034ebadf9",
}
CORRECTED={
"sycon_plate.webp":"1b7b8b0ceee848603c24581f60d87866abb03773570f83c43f54665bbb00c05a",
"obelia_colony.webp":"a4072d35ae5ef51901ffff6dd4d8e8b85caabc172adcea51d179bdf7c5397a93",
}
DIRECTORY=Path("native-app/app/src/main/assets/unit2")
FONTS=[
"/usr/share/fonts/truetype/liberation2/LiberationSerif-Regular.ttf",
"/usr/share/fonts/truetype/dejavu/DejaVuSerif.ttf",
]
def font(size):
    for f in FONTS:
        if Path(f).exists(): return ImageFont.truetype(f,size)
    raise RuntimeError("No approved Serif font available")

def repair(name):
    path=DIRECTORY/name
    source=path.read_bytes()
    current=hashlib.sha256(source).hexdigest()
    if current!=BASE[name]:
        if current!=CORRECTED[name]:
            raise RuntimeError("Unknown source image hash: "+name+" "+current)
        print("VERIFIED-CORRECTED-MASTER",name,current)
        return
    im=Image.open(path).convert("RGB")
    if im.size!=(1000,1250): raise RuntimeError("Unexpected image dimensions")
    d=ImageDraw.Draw(im)
    ink=(42,42,42)
    if name=="sycon_plate.webp":
        # The obsolete terms are printed in the source raster.
        d.rectangle((834,548,999,624),fill="white")
        d.rectangle((833,735,999,807),fill="white")
        d.line([(667,739),(832,742)],fill="white",width=9)
        d.line([(662,741),(688,739)],fill="white",width=7)
        d.text((841,557),"Apopyle",font=font(23),fill=ink)
        d.text((841,585),"(exit opening)",font=font(18),fill=ink)
        d.line([(833,574),(736,574),(648,617)],fill=ink,width=2)
        d.text((839,738),"Prosopyle",font=font(23),fill=ink)
        d.text((839,765),"(entry opening)",font=font(18),fill=ink)
        d.line([(830,755),(770,755),(584,742)],fill=ink,width=2)
        d.ellipse((580,738,588,746),outline=ink,width=1)
    elif name=="obelia_colony.webp":
        # Gonotheca is the protective sheath, not the reproductive polyp.
        d.rectangle((815,405,999,476),fill="white")
        d.text((827,413),"Gonotheca",font=font(23),fill=ink)
        d.text((827,439),"(protective sheath)",font=font(17),fill=ink)
        d.line([(820,425),(754,425),(735,422)],fill=ink,width=2)
        d.text((831,583),"Gonangium",font=font(23),fill=ink)
        d.text((831,609),"(reproductive)",font=font(17),fill=ink)
        d.text((831,630),"polyp",font=font(17),fill=ink)
        d.line([(826,603),(763,603),(704,576)],fill=ink,width=2)
    im.save(path,format="WEBP",quality=92,method=6)
    print("RESTORED-MASTER",name,hashlib.sha256(path.read_bytes()).hexdigest())

if __name__=="__main__":
    for path in ("sycon_plate.webp","obelia_colony.webp"):
        repair(path)
    life=DIRECTORY/"obelia_lifecycle.webp"
    assert hashlib.sha256(life.read_bytes()).hexdigest()==BASE[life.name], "Life cycle was changed"
    print("PRESERVED-LIFE-CYCLE",BASE[life.name])
