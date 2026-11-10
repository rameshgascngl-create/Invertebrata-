#!/usr/bin/env python3
"""Build seven PNG REVIEW candidates using documented Commons source figures.

No generative anatomy. Existing illustrations are not relabelled at guessed
coordinates. A cycle is assembled from real Obelia source drawings and explicit
stage-order text. All outputs are QA-only until visual/biological editorial review.
"""
from __future__ import annotations
import hashlib, io, json, math, textwrap, time
from pathlib import Path
from urllib.parse import quote
import requests
from PIL import Image, ImageDraw, ImageFont, ImageOps
import cairosvg

HERE=Path(__file__).resolve().parent
CFG=json.loads((HERE/"remaining_seven_sources.json").read_text(encoding="utf-8"))
OUT=HERE/"remaining-seven-review"
OUT.mkdir(parents=True,exist_ok=True)
RAW=OUT/"original_source_receipts"
RAW.mkdir(exist_ok=True)
UA="INVERTEBRATA-Zoology-SourceIntake/1.1 (https://github.com/rameshgascngl-create/Invertebrata-)"
NAVY="#103753";INK="#172e40";SUB="#3f586c";PALE="#f5f8fb";BORDER="#b5cbdc"

def font(size=30,bold=False):
    suffix="-Bold" if bold else ""
    path=f"/usr/share/fonts/truetype/dejavu/DejaVuSans{suffix}.ttf"
    try:return ImageFont.truetype(path,size)
    except OSError:return ImageFont.load_default()

def fetch_approved_source(item):
    filename=item["filename"].replace(" ","_")
    dig=hashlib.md5(filename.encode("utf-8")).hexdigest()
    url=f"https://upload.wikimedia.org/wikipedia/commons/{dig[0]}/{dig[:2]}/{quote(filename,safe='()')}"
    last=None
    for candidate in (url,item["source_page"].replace("/wiki/File:","/wiki/Special:Redirect/file/")):
        for attempt in range(3):
            try:
                resp=requests.get(candidate,timeout=55,
                    headers={"User-Agent":UA,"Accept":"image/svg+xml,image/png,image/jpeg,image/*;q=0.8"})
                resp.raise_for_status()
                if not 300 < len(resp.content) < 18*1024*1024:
                    raise ValueError("Implausible source size: "+str(len(resp.content)))
                content=resp.content
                if filename.lower().endswith(".svg"):
                    assert b"<svg" in content[:5000], "Downloaded non-SVG content"
                    png=cairosvg.svg2png(bytestring=content,output_width=2000)
                    image=Image.open(io.BytesIO(png)).convert("RGBA")
                else:
                    with Image.open(io.BytesIO(content)) as img: img.verify()
                    with Image.open(io.BytesIO(content)) as img:
                        image=ImageOps.exif_transpose(img).convert("RGBA")
                rawhash=hashlib.sha256(content).hexdigest()
                return image,{
                    "key":item["key"],"source_filename":item["filename"],
                    "source_url":item["source_page"],"download_url":resp.url,
                    "creator":item["creator"],"license":item["license"],
                    "source_sha256":rawhash,"original_px":list(image.size),
                    "source_bytes":len(content),
                    "change":"Direct raster conversion from source SVG/JPEG/PNG; source figures never redrawn"
                }
            except Exception as exc:
                last=exc
                time.sleep(attempt+1)
    raise RuntimeError(f"COULD_NOT_FETCH_VERIFIED_SOURCE {filename}: {last}")

def card(title,notes, width=2500,height=1840):
    canvas=Image.new("RGB",(width,height),PALE)
    d=ImageDraw.Draw(canvas)
    d.rectangle((0,0,width,132),fill=NAVY)
    d.text((72,34),title,font=font(53,True),fill="white")
    d.text((70,height-133),notes,font=font(23),fill=SUB)
    d.text((70,height-85),"Source-based QA reference · Original figure credits and licences in PROVENANCE.json",font=font(19),fill=SUB)
    return canvas,d

def put(img,source,rect,inner="contain"):
    x1,y1,x2,y2=rect
    w,h=x2-x1,y2-y1
    ImageDraw.Draw(img).rounded_rectangle(rect,radius=16,fill="white",outline=BORDER,width=3)
    if inner=="contain":
        fit=ImageOps.contain(source,(w-42,h-42),Image.Resampling.LANCZOS)
    else:
        fit=ImageOps.fit(source,(w-42,h-42),Image.Resampling.LANCZOS)
    # transparent foreground composited on white without altering its internal features
    back=Image.new("RGBA",fit.size,(255,255,255,255))
    back.alpha_composite(fit)
    img.paste(back.convert("RGB"),(x1+(w-fit.width)//2,y1+(h-fit.height)//2))

def titleline(img,x,y,headline,subline=None):
    d=ImageDraw.Draw(img)
    d.text((x,y),headline,font=font(34,True),fill=INK)
    if subline:d.text((x,y+48),subline,font=font(25),fill=SUB)

def bodylines(img,xy,lines,size=29,leading=45, color=INK):
    d=ImageDraw.Draw(img);x,y=xy
    for i,line in enumerate(lines):d.text((x,y+i*leading),line,font=font(size),fill=color)

def connect(d,a,b):
    x1,y1=a;x2,y2=b
    d.line((x1,y1,x2,y2),fill="#1d618b",width=8)
    ang=math.atan2(y2-y1,x2-x1)
    for delta in [-0.57,0.57]:
        px=x2-28*math.cos(ang+delta);py=y2-28*math.sin(ang+delta)
        d.line((x2,y2,px,py),fill="#1d618b",width=8)

images={};provenance={}
for item in CFG["sources"]:
    image,record=fetch_approved_source(item)
    images[item["key"]]=image
    provenance[item["key"]]=record
    print("SOURCE_OK",item["key"],image.size,flush=True)
outdata={}
def save(key,im,src_keys,qualifications):
    name=next(t["file"] for t in CFG["targets"] if t["id"]==key)
    path=OUT/name
    im.save(path,format="PNG",optimize=True)
    b=path.read_bytes()
    outdata[key]=dict(file=name,format="PNG",size=list(im.size),
      sha256=hashlib.sha256(b).hexdigest(),bytes=len(b),
      source_keys=src_keys,QA_limitations=qualifications,
      academic_approval="NOT_GRANTED",rights_status="Original source licences recorded; adaptation obligations must be met")
    print("CANDIDATE_OK",key,name,im.size,flush=True)

# 1: anatomically annotated full-cell source; no arbitrary crop of leader lines.
im,d=card("01  PARAMECIUM — ORAL STRUCTURES",
          "Important: this verified whole-cell image is NOT a magnified cytopharynx dissection.")
put(im,images["paramecium"],(80,190,2420,1510))
bodylines(im,(90,1540),["Oral groove, buccal overture, cytostome and cytopharynx are distinct.",
                        "Original labels retained. See the bilingual lesson for structure-by-structure details."],size=25,leading=44)
save("paramecium/oral-apparatus",im,["paramecium"],"General caudatum diagram, not enlarged oral apparatus; do not assert magnified features")

# 2: syconoid diagram is a general body plan, not a photographed Sycon species.
im,d=card("02  SYCON — EXTERNAL BODY FORM",
          "Generalised syconoid-type sponge diagram · not a species-specific dissection.")
put(im,images["sycon"],(80,190,2420,1470))
bodylines(im,(90,1510),["Observe the osculum, dermal surface and syconoid aquiferous architecture.",
                        "Illustration is a body-plan reference; direct genus-level identification remains provisional."],size=24)
save("sycon/external",im,["sycon"],"A syconoid body-plan illustration, not a confirmed Sycon specimen")

# 4: actual Obelia medusa side + oral historical scientific plate.
im,d=card("04  OBELIA — MEDUSA ANATOMY",
          "Hargitt (1905), Obelia diaphana / O. geniculata · historic, public-domain plate.")
titleline(im,130,176,"Lateral / side aspect")
titleline(im,1360,176,"Oral aspect")
put(im,images["obelia_side"],(75,285,1235,1450))
put(im,images["obelia_oral"],(1280,285,2435,1450))
bodylines(im,(108,1510),["A medusa has an umbrella, manubrium, radial canals, gonads and marginal tentacles.",
                        "The archival source is not a modern photomicrograph; use zoom and the chapter key."],size=25)
save("obelia/medusa",im,["obelia_side","obelia_oral"],"Original linework is small; do not infer structures not clearly present")

# 5: original stage-order flow, anchored in actual Obelia colony and medusa source plates.
im,d=card("05  OBELIA — COMPLETE LIFE-HISTORY SEQUENCE",
          "Source figures: Hargitt/University of Washington (1905); arrows and stages are original schematic.",
          width=2800,height=1940)
# Two-row eight-step loop, arrows inside margins and unambiguous n/diploid states.
stages=[
 (1,"Hydroid colony","Sessile / asexual",120,220),
 (2,"Gonangium","Medusa buds form",770,220),
 (3,"Free medusae","Sexual stage",1420,220),
 (4,"Gametes","Egg + sperm",2070,220),
 (8,"New colony","Hydroid budding",120,1030),
 (7,"Young polyp","After attachment",770,1030),
 (6,"Planula larva","Ciliated / motile",1420,1030),
 (5,"Zygote","After fertilisation",2070,1030)
]
for n,ttl,sub,x,y in stages:
    d.rounded_rectangle((x,y,x+540,y+570),radius=24,fill="white",outline=BORDER,width=4)
    d.text((x+25,y+17),f"{n}. {ttl}",font=font(31,True),fill=INK)
    d.text((x+26,y+65),sub,font=font(23),fill=SUB)
    if n in (1,2,8):
        src=images["obelia_colony"];put(im,src,(x+30,y+116,x+510,y+520))
    if n==3:
        src=images["obelia_side"];put(im,src,(x+30,y+130,x+510,y+505))
    if n==4:
        d.ellipse((x+145,y+220,x+240,y+315),fill="#b2d4ec",outline="#245478",width=4)
        d.ellipse((x+305,y+223,x+363,y+281),fill="#e3b69a",outline="#9a5845",width=4)
        d.text((x+145,y+365),"male + female",font=font(22),fill=SUB)
    if n==5:
        d.ellipse((x+202,y+239,x+330,y+365),fill="#d1caee",outline="#6a5598",width=5)
    if n==6:
        d.ellipse((x+160,y+227,x+376,y+352),fill="#b5d4ef",outline="#276191",width=5)
        for deg in range(0,360,24):
            cx,cy=x+268,y+289; theta=math.radians(deg)
            px=cx+112*math.cos(theta);py=cy+66*math.sin(theta)
            d.line((px,py,px+18*math.cos(theta),py+18*math.sin(theta)),fill="#276191",width=3)
    if n==7:
        d.line((x+170,y+427,x+382,y+427),fill="#7a8c9b",width=7)
        d.rounded_rectangle((x+260,y+279,x+297,y+424),radius=12,fill="#bcd5e7",outline="#416b8b",width=4)
        for a in range(-90,91,45):
            ang=math.radians(a-90)
            d.line((x+278,y+279,x+278+82*math.cos(ang),y+279+60*math.sin(ang)),fill="#376a98",width=4)
for i in range(3):connect(d,(660+650*i,505),(760+650*i,505))
connect(d,(2340,800),(2340,1015))
for i in range(3):connect(d,(2070-650*i,1330),(1860-650*i,1330))
connect(d,(380,1015),(380,805))
bodylines(im,(86,1660),["Diploid hydroid buds diploid medusae; medusae release haploid gametes.",
                        "Fertilisation → zygote → ciliated planula → settled polyp → new colony."],size=20,leading=30)
save("obelia/life-cycle",im,["obelia_colony","obelia_side"],
     "Stage shapes are explicitly schematic; references show actual Obelia colony and medusa")

# 6: public-domain historical species-specific plate, not a modern organ-key overlay.
im,d=card("06  ASCARIS — FEMALE INTERNAL STRUCTURES",
          "Paul Pfurtscheller · original Ascaris lumbricoides anatomy plate · public domain.")
put(im,images["ascaris"],(80,190,2420,1510))
bodylines(im,(90,1540),["Identify alimentary canal separately from the elongated paired female reproductive tubes.",
                        "Historical plate is not the same as a female transverse-section microscope image."],size=24)
save("ascaris/female-internal",im,["ascaris"],"Female organs should be confirmed in original historical drawing before final acceptance")

# 7: two sourced diagrams cover nephridium and metameric distribution; species distinction explicit.
im,d=card("07  EARTHWORM — EXCRETORY SYSTEM",
          "Two sourced drawings: Oligochaeta segment (PD) + metanephridium (KDS4444 CC BY-SA 4.0).")
titleline(im,100,180,"Segment / nephridiopore")
titleline(im,1350,180,"Detailed metanephridium")
put(im,images["earthworm_segments"],(80,285,1240,1250))
put(im,images["earthworm_nephridium"],(1270,285,2420,1250))
bodylines(im,(115,1300),["Pheretima: integumentary nephridia → external pores (exonephric).",
                        "Septal nephridia → intestine (enteronephric).",
                        "Pharyngeal nephridia → anterior alimentary canal (enteronephric)."],size=29,leading=75)
save("earthworm/excretory",im,["earthworm_segments","earthworm_nephridium"],
     "Two species/general diagrams + text; route classes are Pheretima comparison, not directly shown by source figures")

# 8: species-authenticated CC photograph and public-domain historical Ampullaria comparison.
im,d=card("08  PILA GLOBOSA — EXTERNAL MORPHOLOGY",
          "Specimen: Siyaulhaque (2025), CC BY-SA 4.0 · comparative apple-snail plate: NIE 1905 PD.")
titleline(im,110,180,"Pila globosa specimen")
titleline(im,1335,180,"Apple snail comparison")
put(im,images["pila_photo"],(70,275,1230,1380))
# The source plate holds nine unrelated taxa. Restrict the comparison to its
# bottom-right figure 9 (the apple snail with extended head/tentacles).
# The archival snail is Ampullaria, NOT a Pila globosa species identifier.
apple_nine=images["apple_snail_historical"].crop((760,1550,1487,2372))
put(im,apple_nine,(1280,275,2430,1380))
bodylines(im,(90,1430),["In living Pila, eyes arise on small prominences OUTSIDE the tentacle BASES.",
                        "Historical engraving is Ampullaria, NOT identified as Pila globosa.",
                        "If the specimen hides its soft parts, do not infer eye position from the shell."],size=24,leading=49)
save("pila/external",im,["pila_photo","apple_snail_historical"],
     "Photo may not show extended soft parts; historical Ampullaria is comparative, not species proof")

receipt={"schema_version":1,"status":"NEW_VISUAL_QA_NOT_ACADEMIC_ACCEPTANCE",
  "figures":outdata,"source_files":provenance,"not_modified":
  ["41 existing green-ticked master images","11 held mandatory assets","strict academic approval gate"]}
(OUT/"PROVENANCE.json").write_text(json.dumps(receipt,ensure_ascii=False,indent=2)+"\n",encoding="utf-8")
thumbs=[]
for target in CFG["targets"]:
    f=OUT/target["file"]
    with Image.open(f) as p: th=ImageOps.contain(p.convert("RGB"),(700,480))
    thumbs.append((target["id"],th.copy()))
sheet=Image.new("RGB",(2220,1770),"#eff3f7")
d=ImageDraw.Draw(sheet)
d.text((50,30),"Seven new source-based figures · visual QA contact sheet",font=font(39,True),fill=INK)
for i,(name,p) in enumerate(thumbs):
    col=i%3;row=i//3;x=40+col*740;y=125+row*540
    d.rounded_rectangle((x,y,x+700,y+515),radius=15,fill="white",outline="#b5cbdc",width=2)
    sheet.paste(p,(x+(700-p.width)//2,y+18))
    d.text((x+20,y+468),name,font=font(23,True),fill=INK)
sheet.save(OUT/"REVIEW_CONTACT_SHEET.jpg",quality=91)
print("REMAINING_SEVEN_PNGS_GENERATED=7",flush=True)
