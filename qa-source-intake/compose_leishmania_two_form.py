#!/usr/bin/env python3
"""Create a sourced, two-form Leishmania morphology plate for DEBUG QA only.

No anatomical content is generated: original Servier Medical Art files are
decoded, resized without cropping, positioned side-by-side, and captioned.
CC BY 2.0 attribution and each original image SHA-256 are retained.
"""
import hashlib
import io
import json
import time
from pathlib import Path
from PIL import Image, ImageDraw, ImageFont, ImageOps
import requests

HERE=Path(__file__).resolve().parent
OUT=HERE/"downloaded-png-review"
OUT.mkdir(parents=True,exist_ok=True)
FILES=[
 ("amastigote","https://upload.wikimedia.org/wikipedia/commons/8/82/Leishmania_amastigote.png",
  "https://commons.wikimedia.org/wiki/File:Leishmania_amastigote.png",
  "Amastigote","Mammalian host macrophage","No prominent free flagellum"),
 ("promastigote","https://upload.wikimedia.org/wikipedia/commons/2/2e/Leishmania_promastigote.png",
  "https://commons.wikimedia.org/wiki/File:Leishmania_promastigote.png",
  "Promastigote","Sand fly","Elongated form; anterior free flagellum")
]
def fetch(url):
    last=None
    for attempt in range(3):
        try:
            r=requests.get(url,timeout=90,headers={
                "User-Agent":"INVERTEBRATA-Zoology-SourceIntake/1.0 (https://github.com/rameshgascngl-create/Invertebrata-)",
                "Accept":"image/png"})
            r.raise_for_status()
            if len(r.content)>12_000_000: raise ValueError("unexpectedly large source")
            return r.content
        except (requests.RequestException,ValueError) as ex:
            last=ex;time.sleep(attempt+1)
    raise RuntimeError(f"source download failed: {last}")

def font(size,bold=False):
    path="/usr/share/fonts/truetype/dejavu/DejaVuSans"+("-Bold" if bold else "")+".ttf"
    try: return ImageFont.truetype(path,size)
    except OSError:return ImageFont.load_default()

canvas=Image.new("RGB",(2180,1610),"#f5f8fc")
draw=ImageDraw.Draw(canvas)
draw.rectangle((0,0,2179,125),fill="#103b5b")
draw.text((86,38),"LEISHMANIA — TWO MORPHOLOGICAL FORMS",font=font(45,True),fill="#ffffff")
draw.text((90,155),"Comparative anatomical illustrations · source-derived, not to scale",font=font(25),fill="#294258")
provenance=[]
for idx,(key,url,page,label,host,feature) in enumerate(FILES):
    data=fetch(url)
    with Image.open(io.BytesIO(data)) as im:
        im.verify()
    with Image.open(io.BytesIO(data)) as im:
        original=ImageOps.exif_transpose(im).convert("RGBA")
    ox=70+idx*1040
    draw.rounded_rectangle((ox,215,ox+995,1430),radius=24,fill="white",outline="#b7cde0",width=3)
    draw.text((ox+35,247),label.upper(),font=font(36,True),fill="#113a56")
    draw.text((ox+35,300),host,font=font(24),fill="#365a73")
    fitted=ImageOps.contain(original,(880,920),Image.Resampling.LANCZOS)
    x=ox+int((995-fitted.width)/2)
    y=350+int((925-fitted.height)/2)
    canvas.paste(fitted,(x,y),fitted)
    draw.text((ox+36,1325),feature,font=font(21),fill="#25445d")
    provenance.append(dict(form=key,source_url=url,source_page=page,
        copyright_author="Servier Medical Art",license="CC BY 2.0 Generic",
        original_size=list(original.size),original_sha256=hashlib.sha256(data).hexdigest(),
        process="Scaled uniformly, placed on white background, surrounding captions added; original anatomical outline not redrawn"))
draw.line((90,1464,2090,1464),fill="#9eb3c4",width=2)
draw.text((90,1490),"Illustrations © Servier Medical Art · CC BY 2.0 · Wikimedia Commons originals",font=font(23),fill="#24425b")
draw.text((90,1531),"Combined and captioned for INVERTEBRATA debug QA · Expert review pending",font=font(19),fill="#496477")
path=OUT/"11_leishmania_two_forms.png"
canvas.save(path,format="PNG",optimize=True)
receipt={"schema_version":1,"subject":"Leishmania amastigote and promastigote",
    "output_filename":path.name,"output_sha256":hashlib.sha256(path.read_bytes()).hexdigest(),
    "output_size":list(canvas.size),"source_drawings":provenance,
    "licence_url":"https://creativecommons.org/licenses/by/2.0/",
    "academic_approval":"PENDING independent scientific/Tamil review",
    "implementation":"Debug-only candidate; no released asset overwritten"}
(OUT/"LEISHMANIA_PROVENANCE.json").write_text(json.dumps(receipt,indent=2,ensure_ascii=False)+"\n",encoding="utf-8")
print(f"LEISHMANIA_TWO_FORMS_PNG={path} {canvas.size} sha256={receipt['output_sha256']}",flush=True)
