#!/usr/bin/env python3
"""Unit II true historical Sycon canal reference without simulated anatomy.

Augusta Foote Arnold, The Sea-Beach at Ebb-Tide (1903), p.103, plate.
Source: Project Gutenberg eBook 43946, figure i155, in the public domain.
The original bitmap stays at 1:1 scale and is not painted over.
"""
from pathlib import Path
from urllib.request import urlopen,Request
from io import BytesIO
from PIL import Image, ImageDraw, ImageFont
import hashlib

URL="https://www.gutenberg.org/files/43946/43946-h/images/i155.jpg"
OUTPUT=Path("native-app/app/src/main/assets/unit2/sycon_historical.png")
def font(size,bold=False):
    return ImageFont.truetype("/usr/share/fonts/truetype/dejavu/DejaVuSans"+
                             ("-Bold" if bold else "")+".ttf",size)

def main():
    with urlopen(Request(URL,headers={"User-Agent":
        "GASC-Zoology-Teaching-Atlas/1.0 historical-source-review"}),timeout=40) as response:
        data=response.read(5_000_000)
    print("SYCON_1903_SOURCE_SHA256="+hashlib.sha256(data).hexdigest())
    with Image.open(BytesIO(data)) as img:
        assert img.format in ("JPEG","PNG")
        base=img.convert("RGB")
    print("SYCON_1903_SOURCE_DIMENSIONS="+str(base.size))
    assert base.width>=300 and base.height>=400, "Unexpected historic source too small"
    w=max(1250,base.width+130);h=base.height+320
    canvas=Image.new("RGB",(w,h),(254,253,249))
    canvas.paste(base,((w-base.width)//2,116))
    draw=ImageDraw.Draw(canvas)
    draw.text((52,19),"SYCON — HISTORICAL CANAL-SECTION STUDY",
              font=font(30,True),fill=(36,34,32))
    draw.text((52,63),"Arnold (1903): original scanned microscopic line diagram",
              font=font(20),fill=(74,70,64))
    y=base.height+149
    draw.line((42,y,w-42,y),width=2,fill=(80,76,72))
    draw.text((50,y+15),"IC: incurrent canal   R: radial canal   pp: prosopyle   ap: apopyle",
              font=font(17),fill=(42,39,37))
    draw.text((50,y+53),"Water: ostia → IC → prosopyle → choanocyte-lined R → apopyle → spongocoel → osculum",
              font=font(15),fill=(42,39,37))
    draw.text((50,y+91),"Source line drawing preserved at 1:1 pixels. Independent biology review pending.",
              font=font(15),fill=(91,80,73))
    OUTPUT.parent.mkdir(parents=True,exist_ok=True)
    canvas.save(OUTPUT,format="PNG",optimize=True)
    print("SYCON_HISTORIC_CANDIDATE_SHA256="+hashlib.sha256(OUTPUT.read_bytes()).hexdigest())

if __name__=="__main__":
    main()
