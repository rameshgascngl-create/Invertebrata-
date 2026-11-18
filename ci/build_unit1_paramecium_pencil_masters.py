#!/usr/bin/env python3
"""Offline PNG masters derived from an actual photographed pencil drawing.

Four separate regional teaching views use ONE original hand-drawn source.
These are provisional, NOT four independently produced anatomy plates.
No SVG, Canvas or generated organism anatomy is used, and leader positions
are NOT invented. No silent source substitution is permitted.
"""
import hashlib,io,time,urllib.request
from pathlib import Path
from PIL import Image,ImageDraw,ImageFont,ImageOps
URL="https://upload.wikimedia.org/wikipedia/commons/3/33/Paramecium_caudatum-hand_labeled.jpg"
SHA1="0d9d108ccc8310ed82de617d0cf71ca64ce0aad7"
DEST=Path("native-app/app/src/main/assets/unit1")
SIZE=(1700,1900)
FONT="/usr/share/fonts/truetype/dejavu/DejaVuSans.ttf"

def load():
    error=None
    for i in range(4):
        try:
            req=urllib.request.Request(URL,headers={"User-Agent":"Mozilla/5.0 (GASC Zoology teaching art attribution check)"})
            with urllib.request.urlopen(req,timeout=35) as response:
                raw=response.read(4_000_000)
            assert hashlib.sha1(raw).hexdigest()==SHA1, "Pencil reference source bytes changed"
            with Image.open(io.BytesIO(raw)) as im:
                assert im.format=="JPEG" and im.size==(1536,2048), (im.format,im.size)
                return ImageOps.autocontrast(ImageOps.grayscale(im).convert("RGB"),cutoff=1)
        except Exception as ex:
            error=ex
            if i<3:time.sleep((i+1)*4)
    raise RuntimeError("Source drawing unavailable: no fallback to synthetic biology") from error

def build(im,mode,crop,title):
    # Preserve actual sketched organ strokes and their original annotation
    # text. Cropping is editorial framing only, NOT an organ reconstruction.
    piece=im.crop(crop)
    piece.thumbnail((1550,1550),Image.Resampling.LANCZOS)
    canvas=Image.new("RGB",SIZE,(254,252,247))
    d=ImageDraw.Draw(canvas)
    large=ImageFont.truetype(FONT,37)
    small=ImageFont.truetype(FONT,24)
    d.text((55,33),"PARAMECIUM CAUDATUM  |  "+title,font=large,fill=(35,33,31))
    d.text((58,95),"Hand-drawn pencil source · 2014 · anatomical review pending",font=small,fill=(73,69,64))
    canvas.paste(piece,((SIZE[0]-piece.width)//2,145))
    d.line((60,1792,1640,1792),fill=(115,112,105),width=2)
    d.text((60,1810),"Rijan Bhandari; CC BY-SA 3.0 · cropped source photo",font=small,fill=(59,56,53))
    DEST.mkdir(parents=True,exist_ok=True)
    out=DEST/("paramecium_"+mode+".png")
    canvas.save(out,"PNG",optimize=True)
    with Image.open(out) as check:assert check.format=="PNG" and check.size==SIZE
    print("SOURCE_BACKED_PENCIL_PNG="+str(out)+" SHA256="+hashlib.sha256(out.read_bytes()).hexdigest())

source=load()
for mode,region,name in [
    ("external",(0,0,1536,2048),"EXTERNAL & CILIA"),
    ("oral",(240,350,1510,1570),"ORAL REGION"),
    ("vacuole",(100,20,1470,1910),"CONTRACTILE VACUOLES"),
    ("internal",(135,320,1430,1790),"INTERNAL STRUCTURES"),
]:
    build(source,mode,region,name)
print("PENCIL_SOURCE_PROVENANCE=PHOTO_VERIFIED; ACADEMIC_REVIEW=OPEN; DIRECT_HOTSPOTS=DISABLED")
