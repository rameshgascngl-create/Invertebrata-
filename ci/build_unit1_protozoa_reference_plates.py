#!/usr/bin/env python3
"""Four independent organism reference illustrations, NOT pencil drawings.
The accepted source is authenticated where Commons reports a SHA-1.
Fails closed: no stock substitutes or synthetic biological contours.
"""
import hashlib, io, time, urllib.request
from pathlib import Path
from urllib.parse import quote
from PIL import Image,ImageOps,ImageDraw,ImageFont
import cairosvg

OUT=Path("native-app/app/src/main/assets/unit1/references")
FONT="/usr/share/fonts/truetype/dejavu/DejaVuSans.ttf"
SOURCES={
 "entamoeba": ("Entamoeba_histolytica_life_cycle-en.svg","1e558e816f284f433face024c8a3ce7e524b69e9",
               "ENTAMOEBA HISTOLYTICA · LIFE CYCLE",
               "Mariana Ruiz Villarreal (LadyofHats) · public domain · Wikimedia Commons"),
 "trypanosoma": ("Trypanosoma_brucei.svg","a3d3773f53d6aebfe466ad32567713ddc91f3c6c",
               "TRYPANOSOMA BRUCEI · MORPHOLOGY REFERENCE",
               "Johannahoog · CC BY-SA 3.0 · Wikimedia Commons"),
 "leishmania": ("Leishmania_Intracellular_Amastigote_(NIH_BioArt_872_-_798227).png",
                "28b844a79417f71ae8de261c06c0244245fb75c7",
               "LEISHMANIA spp. · INTRACELLULAR AMASTIGOTE",
               "NIAID / Ryan Kissinger · US public domain · Wikimedia Commons"),
 "plasmodium": ("Malaria_Plasmodium_infection_cycle.png",None,
               "PLASMODIUM · HUMAN AND MOSQUITO LIFE CYCLE",
               "Sina Adl (2025) · CC BY 4.0 · Wikimedia Commons")
}
def commons_original(name):
    normalized=name.replace(" ","_")
    digest=hashlib.md5(normalized.encode("utf-8")).hexdigest()
    return f"https://upload.wikimedia.org/wikipedia/commons/{digest[0]}/{digest[:2]}/{quote(normalized,safe='()_-')}"

def acquire(filename,sha1):
    error=None
    for attempt in range(4):
        try:
            req=urllib.request.Request(commons_original(filename),
                    headers={"User-Agent":"GASCNagercoilZoology/1.0 (educational artwork source audit)"})
            with urllib.request.urlopen(req,timeout=45) as r:
                data=r.read(6_000_000)
            if not data or len(data)>5_500_000:raise ValueError("Source size invalid")
            actual=hashlib.sha1(data).hexdigest()
            if sha1 and actual!=sha1:
                raise ValueError(f"Original SHA1 changed for {filename}: {actual}")
            return data,actual
        except Exception as exc:
            error=exc
            if attempt<3:time.sleep(3*(attempt+1))
    raise RuntimeError(f"No authenticated illustration for {filename}") from error

def image_from_source(filename,data):
    if filename.endswith(".svg"):
        data=cairosvg.svg2png(bytestring=data,output_width=2100)
    with Image.open(io.BytesIO(data)) as im:
        im.load()
        source=im.convert("RGBA")
        # Commons SVG/PNG references can be transparent with WHITE labels.
        # Converting RGBA directly to RGB would turn transparent regions black.
        # Composite uniformly on a deliberate dark matte; never repaint anatomy.
        matte=Image.new("RGBA",source.size,(35,39,46,255))
        matte.alpha_composite(source)
        return matte.convert("RGB")

def make(id,filename,sha,title,credit):
    source,sha1=acquire(filename,sha)
    im=image_from_source(filename,source)
    # Fit the WHOLE reference artwork into a measured content rectangle.
    # Image.thumbnail() only downsizes. For small source images it left the
    # biological figure tiny inside a 2100x1900 file even in full-screen mode.
    # High-quality enlargement changes pixel sampling, NOT anatomy or labels.
    original_size=im.size
    max_size=(1880,1640)
    scale=min(max_size[0]/im.width, max_size[1]/im.height, 2.5)
    target=(max(1,round(im.width*scale)),max(1,round(im.height*scale)))
    assert target[0]<=max_size[0] and target[1]<=max_size[1]
    im=im.resize(target,Image.Resampling.LANCZOS)
    canvas=Image.new("RGB",(2100,1900),(255,253,249))
    x=(2100-im.width)//2
    y=125+(1640-im.height)//2
    assert 0<=x and 125<=y and x+im.width<=2100 and y+im.height<=1765
    canvas.paste(im,(x,y))
    draw=ImageDraw.Draw(canvas)
    head=ImageFont.truetype(FONT,40);foot=ImageFont.truetype(FONT,28)
    draw.text((46,28),title,font=head,fill=(33,37,41))
    draw.line((48,1800,2052,1800),fill=(180,177,170),width=2)
    draw.text((46,1815),credit,font=foot,fill=(54,53,50))
    draw.text((46,1856),"SOURCE REFERENCE · NOT PENCIL MASTER · ANATOMY REVIEW PENDING",font=foot,fill=(125,43,35))
    OUT.mkdir(parents=True,exist_ok=True)
    dest=OUT/(id+".png")
    canvas.save(dest,format="PNG",optimize=True)
    with Image.open(dest) as check:
        assert check.format=="PNG" and check.size==(2100,1900)
    print(f"REFERENCE_LAYOUT={id} original={original_size[0]}x{original_size[1]} scale={scale:.3f} content={im.width}x{im.height} xy={x},{y} frame=2100x1900")
    print(f"REFERENCE={id} FILE={dest} SHA256={hashlib.sha256(dest.read_bytes()).hexdigest()} SOURCE_SHA1={sha1} SOURCE={commons_original(filename)}")

for id,details in SOURCES.items():
    make(id,*details)
print("FOUR_INDEPENDENT_REFERENCE_SOURCES=PASS; SCANNED_PENCIL_AND_SCIENCE_APPROVAL=OPEN")
