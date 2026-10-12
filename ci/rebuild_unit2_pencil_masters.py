#!/usr/bin/env python3
"""Deterministically reconstruct Unit II raster teaching plates from pinned
source-pencil-style WebPs. DIGITALLY DRAWN: not a verified scanned pencil original.
The actual pixels (rather than Compose labels) contain every corrected structure.
"""
from pathlib import Path
from PIL import Image, ImageDraw, ImageFont, ImageFilter
import hashlib, random, math, json, sys
SEED=20771
rng=random.Random(SEED)
BASE={
"sycon_plate.webp":"aa7528461dcd02bc444cf58f49757a0c1de3cf2ba89b057876abc85281f34bf1",
"obelia_colony.webp":"a4072d35ae5ef51901ffff6dd4d8e8b85caabc172adcea51d179bdf7c5397a93",
"obelia_lifecycle.webp":"2ee1ee163a363d2574b3af294cffde4adabf98b0246a031878e046a034ebadf9"}
S="/usr/share/fonts/truetype/dejavu/DejaVuSerif.ttf"
B="/usr/share/fonts/truetype/dejavu/DejaVuSerif-Bold.ttf"
T="/usr/share/fonts/truetype/noto/NotoSansTamil-Regular.ttf"
INK=(48,47,44); PA=(250,249,245); GREY=(114,112,108); BLUE=(45,109,143)
def font(n,b=False,t=False):return ImageFont.truetype(T if t else B if b else S,n)
def text(d,p,s,n=23,b=False,c=INK,t=False,anchor=None):
 d.text(p,s,font=font(n,b,t),fill=c,anchor=anchor)
def canvas(w,h):return Image.new("RGB",(w,h),PA)
def frame(d,r):d.rounded_rectangle(r,14,outline=GREY,width=2)
def sketch(d,points,w=3,color=INK):
 for k in range(2):
  xy=[(round(x+rng.uniform(-.7,.7)),round(y+rng.uniform(-.7,.7))) for x,y in points]
  d.line(xy,fill=color if k==0 else GREY,width=max(1,w-k),joint="curve")
def arrow(d,p,q):
 sketch(d,[p,q],4,BLUE)
 a=math.atan2(q[1]-p[1],q[0]-p[0]); h=12
 d.polygon([q,(q[0]-h*math.cos(a-.48),q[1]-h*math.sin(a-.48)),(q[0]-h*math.cos(a+.48),q[1]-h*math.sin(a+.48))],fill=BLUE)
def pastefit(img,src,rect):
 box=(rect[2]-rect[0],rect[3]-rect[1]); src=src.copy();src.thumbnail(box,Image.Resampling.LANCZOS)
 img.paste(src,(rect[0]+(box[0]-src.width)//2,rect[1]+(box[1]-src.height)//2))
def label(d,p,s):
 x,y=p;d.ellipse((x-17,y-17,x+17,y+17),fill=PA,outline=BLUE,width=3);text(d,(x,y-1),s,19,True,BLUE,anchor="mm")
def sycon(inp):
 im=canvas(1400,1800);d=ImageDraw.Draw(im)
 text(d,(700,25),"SYCON (SCYPHA)",51,True,anchor="mt")
 text(d,(700,92),"Pencil-style external form and syconoid water-current section",22,c=GREY,anchor="mt")
 frame(d,(37,155,620,1080));frame(d,(643,155,1363,1080))
 text(d,(65,176),"A. External view",31,True);text(d,(668,176),"B. Longitudinal canal model",30,True)
 original=Image.open(inp/"sycon_plate.webp").convert("RGB")
 pastefit(im,original.crop((100,55,360,996)),(98,233,575,1030));d=ImageDraw.Draw(im)
 # Spongocoel: central white cavity; only flattened pinacocytes, NO choanocytes.
 d.rounded_rectangle((1138,277,1286,940),29,fill=(244,243,239),outline=INK,width=4)
 d.rounded_rectangle((1161,298,1267,918),18,fill=PA)
 for y in range(320,910,33):sketch(d,[(1142,y),(1153,y+7)],2,GREY)
 d.rectangle((1142,262,1285,310),fill=PA)
 # Four parallel radial canals: blind outside tips; open via apopyles to spongocoel.
 for y in (359,507,655,803):
  d.rounded_rectangle((883,y-44,1148,y+44),37,fill=(232,231,226),outline=INK,width=3)
  d.rounded_rectangle((906,y-23,1180,y+23),18,fill=PA)
  d.rectangle((1137,y-18,1183,y+18),fill=PA)
  # Choanocytes with collar/flagella projecting into each radial canal, not central cavity.
  for x in range(930,1130,24):
   for side in (-1,1):
    ycell=y+side*35
    d.ellipse((x-7,ycell-7,x+7,ycell+7),fill=(193,190,187),outline=INK,width=1)
    sketch(d,[(x,ycell-side*8),(x+2,y-side*9)],1,GREY)
 # Ostium opens to incurrent space, then LATERAL prosopyle perforates radial canal.
 d.rounded_rectangle((759,570,868,607),12,fill=(244,243,239),outline=INK,width=3)
 d.rectangle((749,575,777,600),fill=PA)
 d.rectangle((971,612,994,643),fill=PA)
 for p,q in [((765,587),(844,587)),((846,587),(972,615)),((987,634),(1020,655)),((1036,655),(1130,655)),((1176,655),(1214,655)),((1214,572),(1214,425)),((1214,362),(1214,281))]:
  arrow(d,p,q)
 marks=[(782,551),(856,540),(972,631),(1037,696),(1142,620),(1230,721),(1212,254)]
 for i,p in enumerate(marks):label(d,p,str(i+1))
 frame(d,(668,954,1337,1051))
 text(d,(682,970),"1 Ostia   2 Incurrent canal   3 Prosopyle",20)
 text(d,(682,1005),"4 Radial canal   5 Apopyle   6 Spongocoel   7 Osculum",18)
 frame(d,(38,1100,1362,1550))
 text(d,(68,1119),"C. Flow across incurrent/radial canals (schematic enlargement)",27,True)
 cells=[("OSTIUM","Exterior",143),("INCURRENT","Pinacocytes",346),("PROSOPYLE","Entry opening",550),
 ("RADIAL CANAL","Choanocytes",764),("APOPYLE","Exit opening",987),("SPONGOCOEL","Pinacocytes",1188)]
 for name,sub,x in cells:
  d.ellipse((x-43,1250,x+43,1336),fill=(244,243,239),outline=INK,width=3)
  text(d,(x,1361),name,18,True,anchor="mm")
  text(d,(x,1395),sub,16,c=GREY,anchor="mm")
 for i in range(len(cells)-1):arrow(d,(cells[i][2]+48,1293),(cells[i+1][2]-48,1293))
 for k in range(9):
  a=2*math.pi*k/9;x=764+31*math.cos(a);y=1293+31*math.sin(a)
  d.ellipse((x-5,y-5,x+5,y+5),outline=INK,width=1)
  sketch(d,[(x,y),(764+9*math.cos(a),1293+9*math.sin(a))],1,GREY)
 text(d,(70,1484),"Choanocytes line radial canals; the spongocoel has flattened pinacocytes.",22)
 frame(d,(38,1572,1362,1765))
 text(d,(73,1600),"PATH OF WATER",27,True);text(d,(410,1596),"நீர் ஓட்டப் பாதை",24,t=True)
 text(d,(73,1650),"Ostia → incurrent canal → prosopyle → radial canal → apopyle →",24)
 text(d,(73,1695),"spongocoel → osculum. Illustrative, not to microscopic scale.",22)
 return im.filter(ImageFilter.UnsharpMask(radius=1,percent=65,threshold=2)),marks

def colony(inp):
 im=canvas(1400,1830);d=ImageDraw.Draw(im)
 text(d,(700,24),"OBELIA — COLONY",50,True,anchor="mt")
 text(d,(700,97),"Feeding hydranth and reproductive gonangium are different zooids",22,c=GREY,anchor="mt")
 frame(d,(38,153,1362,1421))
 src=Image.open(inp/"obelia_colony.webp").convert("RGB")
 pastefit(im,src.crop((20,8,997,1110)),(54,165,1336,1385));d=ImageDraw.Draw(im)
 text(d,(70,1390),"A. Original graphite-style colony with printed anatomical leader lines",19,c=GREY)
 frame(d,(38,1450,1362,1797))
 text(d,(70,1466),"B. Gonangium enlarged",27,True)
 text(d,(461,1467),"இனப்பெருக்க பாலிப்",22,t=True)
 cx=480;top=1550;bot=1750
 d.polygon([(cx-92,bot),(cx-94,top+28),(cx-50,top-11),(cx+50,top-11),
 (cx+94,top+28),(cx+92,bot)],outline=INK)
 sketch(d,[(cx,bot),(cx,top+10)],5)
 for j in range(4):
  y=top+47+j*37
  for side in (-1,1):
   x=cx+side*42;d.ellipse((x-18,y-13,x+18,y+13),outline=INK,width=2,fill=(235,234,230))
   d.ellipse((x-4,y-4,x+4,y+4),outline=GREY,width=1)
   sketch(d,[(cx,y+5),(x-side*18,y+5)],2,GREY)
 sketch(d,[(cx-92,top+43),(290,top+43)],2)
 text(d,(74,top+21),"Gonotheca",24,True);text(d,(74,top+58),"protective sheath",17,c=GREY)
 sketch(d,[(cx,top+93),(686,top+93)],2);text(d,(706,top+72),"Blastostyle",23,True)
 text(d,(706,top+106),"reproductive axis",17,c=GREY)
 sketch(d,[(cx+60,top+142),(686,top+142)],2)
 text(d,(706,top+128),"Medusa buds",23,True);text(d,(706,top+165),"within gonotheca",17,c=GREY)
 return im.filter(ImageFilter.UnsharpMask(radius=1,percent=65,threshold=2))

def life(inp):
 im=canvas(1400,1990);d=ImageDraw.Draw(im)
 text(d,(700,25),"OBELIA — LIFE HISTORY",50,True,anchor="mt")
 text(d,(700,95),"Colony → medusae → gametes → zygote → planula → new colony",22,c=GREY,anchor="mt")
 text(d,(700,136),"Fertilisation precedes the planula stage",21,c=(149,90,53),anchor="mt")
 titles=[
 ("MATURE COLONY","Hydranths + gonangia"),
 ("MEDUSA BUDS","Gonangium / blastostyle"),
 ("FREE MEDUSA","Sexual form"),
 ("GAMETES","Ovum + sperm"),
 ("FERTILISATION","Zygote / embryo"),
 ("CILIATED PLANULA","Swimming larva"),
 ("SETTLEMENT","Attached young polyp"),
 ("NEW COLONY","Budding produces zooids")]
 for i,(title,sub) in enumerate(titles):
  x=44+(i%2)*686;y=193+(i//2)*424
  frame(d,(x,y,x+641,y+400))
  d.ellipse((x+16,y+14,x+64,y+62),outline=INK,width=2)
  text(d,(x+40,y+38),str(i+1),25,True,anchor="mm")
  text(d,(x+78,y+17),title,24,True)
  text(d,(x+78,y+52),sub,19,c=GREY)
  if i%2==0:arrow(d,(x+642,y+219),(x+682,y+219))
 source=Image.open(inp/"obelia_colony.webp").convert("RGB")
 old=Image.open(inp/"obelia_lifecycle.webp").convert("RGB")
 pastefit(im,source.crop((188,101,580,841)),(110,265,615,563))
 pastefit(im,source.crop((558,342,713,617)),(797,264,1185,554))
 pastefit(im,old.crop((16,21,195,215)),(115,692,612,936))
 d=ImageDraw.Draw(im)
 egg=(994,801);d.ellipse((egg[0]-49,egg[1]-49,egg[0]+49,egg[1]+49),outline=INK,width=4)
 d.ellipse((egg[0]-16,egg[1]-16,egg[0]+16,egg[1]+16),outline=GREY,width=2)
 for j in range(5):
  x=1079+(j%3)*26;y=737+(j//3)*50
  d.ellipse((x-10,y-7,x+10,y+7),fill=(184,182,178),outline=INK)
  sketch(d,[(x+11,y),(x+38,y+10),(x+54,y-3)],2)
 text(d,(811,883),"egg",18);text(d,(1123,883),"sperm",18)
 x=303;y=1214
 d.ellipse((x-58,y-58,x+58,y+58),outline=INK,width=3)
 for xx,yy in [(x-15,y-9),(x+23,y-16),(x-14,y+28),(x+29,y+30)]:
  d.ellipse((xx-17,yy-17,xx+17,yy+17),outline=GREY,width=2)
 text(d,(407,1208),"2–4 cell embryo",19,c=GREY)
 pastefit(im,old.crop((348,23,452,217)),(818,1093,1265,1338))
 pastefit(im,old.crop((512,17,721,226)),(120,1522,590,1787))
 pastefit(im,source.crop((190,94,713,1100)),(828,1514,1249,1804))
 d=ImageDraw.Draw(im)
 text(d,(700,1890),"The settled polyp buds to regenerate a colony.",22,anchor="mt")
 text(d,(700,1940),"Sequence is descriptive and not to chronological scale.",18,c=GREY,anchor="mt")
 return im.filter(ImageFilter.UnsharpMask(radius=1,percent=55,threshold=2))
def main():
 if len(sys.argv)!=3:raise SystemExit("Usage: rebuild_unit2_pencil_masters.py INPUT_DIR OUTPUT_DIR")
 inp=Path(sys.argv[1]);out=Path(sys.argv[2]);out.mkdir(parents=True,exist_ok=True)
 for name,h in BASE.items():
  observed=hashlib.sha256((inp/name).read_bytes()).hexdigest()
  if observed!=h:raise RuntimeError(f"Unknown source file {name}: {observed}")
 images={"sycon_plate":sycon(inp)[0],"obelia_colony":colony(inp),"obelia_lifecycle":life(inp)}
 evidence={}
 for name,img in images.items():
  path=out/(name+".webp")
  img.save(path,format="WEBP",quality=94,method=6)
  evidence[name]={"sha256":hashlib.sha256(path.read_bytes()).hexdigest(),
   "dimensions":[img.width,img.height],"file":str(path),
   "classification":"digitally reconstructed pencil-style raster, NOT genuine scanned graphite"}
  print("CORRECTED_RASTER",name,evidence[name])
 (out/"source_evidence.json").write_text(json.dumps(evidence,indent=2)+"\n",encoding="utf-8")
if __name__=="__main__":main()
