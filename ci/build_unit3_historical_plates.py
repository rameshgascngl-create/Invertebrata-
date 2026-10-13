#!/usr/bin/env python3
"""Reproduce verifiable public-domain zoological scans as Unit III lesson rasters.

Sources: Encyclopaedia Britannica 9th ed. (1888), Trematoda Fig.1; Paul
Pfurtscheller, Ascaris wall chart (pre-1927). Original source geometry is
preserved 1:1; only titles/credits OUTSIDE the scanned image are added.
Scientific/translation/rights review remains an independent acceptance gate.
No programmatic organism silhouettes, SVG anatomy, or hotspot substitution.
"""
import hashlib
from io import BytesIO
from pathlib import Path
from urllib.request import urlopen, Request
from PIL import Image, ImageDraw, ImageFont

ROOT=Path("native-app/app/src/main/assets/unit3")
SOURCES=[
  {"id":"fasciola_type","url":"https://upload.wikimedia.org/wikipedia/commons/d/df/EB_9th_Volume23_Trematota_Fig_1.png",
   "sha1":"b315b17ad886044e69b82f37685f7f5020faeb67","dims":(1286,1708),
   "title":"FASCIOLA HEPATICA — HISTORICAL ANATOMY",
   "credit":"Encyclopaedia Britannica, Ninth Edition (1888), Trematoda Fig. 1 · Public Domain"},
  {"id":"ascaris_type","url":"https://upload.wikimedia.org/wikipedia/commons/8/86/Pfurtscheller_Table_38_a.png",
   "sha1":None,"dims":(2176,2203),
   "title":"ASCARIS LUMBRICOIDES — HISTORICAL ANATOMY",
   "credit":"Paul Pfurtscheller, Zoologische Wandtafeln (pre-1927) · Public Domain"},
]
def font(size,bold=False):
    path="/usr/share/fonts/truetype/dejavu/DejaVuSans" + ("-Bold" if bold else "") + ".ttf"
    return ImageFont.truetype(path,size)

def main():
    ROOT.mkdir(parents=True,exist_ok=True)
    for record in SOURCES:
        request=Request(record["url"],headers={"User-Agent":
            "GASC Zoology Native Atlas 1.0 (student historical public-domain source verification)"})
        with urlopen(request,timeout=55) as response:
            raw=response.read(12_000_000)
        observed=hashlib.sha1(raw).hexdigest()
        print("SOURCE_SHA1 "+record["id"]+"="+observed)
        if record["sha1"] is not None:
            assert observed==record["sha1"],"Pinned source changed: "+record["id"]
        else:
            print("SOURCE_SHA1_PINNING_REQUIRED: "+record["id"]+
                  " — DO NOT CLAIM RELEASE SOURCE APPROVAL")
        with Image.open(BytesIO(raw)) as source:
            assert source.format=="PNG" and source.size==record["dims"],(
                f"Unexpected source image geometry: {record['id']}, {source.size}")
            # Keep the historical scanned image at original pixel dimensions.
            picture=source.convert("RGB")
        w,h=picture.size
        margin=58
        new_width=max(1600,w+margin*2)
        new_height=h+280
        page=Image.new("RGB",(new_width,new_height),(253,252,248))
        x=(new_width-w)//2
        page.paste(picture,(x,115))
        d=ImageDraw.Draw(page)
        d.text((50,20),record["title"],font=font(35,True),fill=(37,35,33))
        d.line((50,h+145,new_width-50,h+145),fill=(85,75,65),width=2)
        d.text((50,h+169),record["credit"],font=font(19),fill=(65,59,53))
        d.text((50,h+207),"Original scanned figure: unscaled 1:1. Academic annotation approval pending.",
              font=font(18),fill=(82,76,69))
        target=ROOT/(record["id"]+".png")
        page.save(target,"PNG",optimize=True)
        print("UNIT3_RASTER "+str(target)+" "+str(page.size)+" SHA256="+
              hashlib.sha256(target.read_bytes()).hexdigest())
    print("HISTORICAL_SOURCE_PIXELS_PRESERVED; ACADEMIC_QA_PENDING")

if __name__=="__main__":
    main()
