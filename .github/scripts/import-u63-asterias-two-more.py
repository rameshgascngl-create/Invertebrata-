#!/usr/bin/env python3
"""Retrieve and bundle TWO missing Asterias teaching subjects from correctly licensed originals."""
from pathlib import Path
from io import BytesIO
from urllib.request import Request,urlopen
from urllib.parse import quote
from PIL import Image, ImageOps,ImageEnhance
import hashlib

root=Path("native-app/app/src/main/assets/type_studies/asterias")
root.mkdir(parents=True,exist_ok=True)
base="https://upload.wikimedia.org/wikipedia/commons/"
origins={
 "internal":(
    base+"1/15/Asterias_rubens%2C_dissection_modified.jpg",
    "a30d35ab2c40ed081fb1542b0d1ee9b317edfc87",(1234,1080)),
 "bipinnaria":(
    base+"3/35/Asterias_sp.%2C_bipinnaria_larva_from_below.jpg",
    "9273d3d9995441f9651d75d6a1113f50c2a52cb5",(732,1167)),
 "brachiolaria":(
    base+"8/83/Asterias_sp.%2C_older_brachiolaria_larva_from_below.jpg",
    "e803e00100ba0ccf2bb2d5ed22e095f36acee701",(708,1152)),
}

def retrieve(key):
    link,sha1,dims=origins[key]
    candidates=[
      ("original",link),
      ("image_proxy", "https://wsrv.nl/?url="+quote(link,safe="")+"&output=jpg&q=100"),
    ]
    issues=[]
    for method,url in candidates:
        try:
            request=Request(url,headers={"User-Agent":"Mozilla/5.0 (INVERTEBRATA zoology source illustration retrieval)", "Accept":"image/*"})
            with urlopen(request,timeout=45) as f:
                data=f.read(8000000)
            digest=hashlib.sha1(data).hexdigest()
            if method=="original" and digest!=sha1:
                raise ValueError("Unexpected source digest "+digest+" expected "+sha1)
            image=Image.open(BytesIO(data));image.load()
            assert image.size==dims,(key,image.size)
            print(key,method,"source-sha1",digest)
            return ImageOps.exif_transpose(image).convert("RGB"),method,digest
        except Exception as error:
            issues.append(method+":"+str(error)[:150])
    raise RuntimeError(key+" failed: "+str(issues))

anatomy,anmethod,ansha=retrieve("internal")
# Exact Hans Hillewaert published line drawing with labels intentionally removed.
# Keep relative anatomical placements unchanged and draw NO speculative labels.
anatomy.resize((1728,1512),Image.Resampling.LANCZOS).save(root/"internal.png",optimize=True)

b1,m1,h1=retrieve("bipinnaria")
b2,m2,h2=retrieve("brachiolaria")
# Place genuinely DIFFERENT larval stages next to each other, preserving aspect
# ratio and original pixels. The English/Tamil stage names appear in the app's
# external text caption, not baked onto biologically sensitive drawing pixels.
sheet=Image.new("RGB",(1600,1420),(255,255,255))
for original,x in ((b1,30),(b2,830)):
    ratio=min(740/original.width,1320/original.height)
    scaled=original.resize((round(original.width*ratio),round(original.height*ratio)),Image.Resampling.LANCZOS)
    sheet.paste(scaled,(x+(740-scaled.width)//2,55+(1320-scaled.height)//2))
sheet.save(root/"larval-development.png",optimize=True)
for filename in ("internal.png","larval-development.png"):
    blob=(root/filename).read_bytes()
    with Image.open(BytesIO(blob)) as im:
       assert im.width>=1500 and im.height>=1400
    assert len(blob)>50000
    print("COMMITTED CANDIDATE",filename,len(blob),"SHA256",hashlib.sha256(blob).hexdigest())

credit=root/"ATTRIBUTION.md"
assert credit.exists(),"Earlier credited public-domain source PNGs are required"
existing=credit.read_text(encoding="utf-8")
addition="""
## Internal dissection: internal.png
- Species: **Asterias rubens** (not A. forbesi). Anatomical dissection schematic *without labels*, so organ-specific leader lines are not represented as verified.
- Illustrator: **Hans Hillewaert**, 5 December 2007.
- Original source: https://commons.wikimedia.org/wiki/File:Asterias_rubens,_dissection_modified.jpg
- Rights: **CC BY-SA 4.0**, https://creativecommons.org/licenses/by-sa/4.0/
- Source JPEG SHA1: a30d35ab2c40ed081fb1542b0d1ee9b317edfc87
- Transformations: technical file conversion to PNG and resizing for Android; no organ structures or labels redrawn. Reusers must observe CC BY-SA 4.0 conditions. This is an image licence, not a representation that the whole app is Creative Commons licensed.

## Larval development: larval-development.png
- LEFT: **Asterias sp. bipinnaria larva**, original cropped plate by Ernst Haeckel (1904), Commons extraction by Haplochromis.
- RIGHT: **Asterias sp. older brachiolaria larva**, original cropped plate by Ernst Haeckel (1904), Commons extraction by Haplochromis.
- LEFT source: https://commons.wikimedia.org/wiki/File:Asterias_sp.,_bipinnaria_larva_from_below.jpg
- RIGHT source: https://commons.wikimedia.org/wiki/File:Asterias_sp.,_older_brachiolaria_larva_from_below.jpg
- Rights: **Public domain (PD-old)**, both images. Attribution retained.
- Original LEFT SHA1: 9273d3d9995441f9651d75d6a1113f50c2a52cb5
- Original RIGHT SHA1: e803e00100ba0ccf2bb2d5ed22e095f36acee701
- Edits: side-by-side composite layout and resizing; no invented larval organs, labels or chronological claims beyond the sourced stage names.

## Editorial limits
- Different plates may represent A. forbesi, A. rubens, or genus-level Asterias spp.; **do not falsely claim all show the same species**.
- Anatomy and Tamil labels require independent faculty review; physical-phone signoff is separate from emulator CI.
- Keep reusable visual assets' source and license visible in the Android UI and README.
"""
existing=existing.replace("Missing internal and larval-development master plates are not substituted.", "Internal and larval-development subjects were subsequently supplied using source-licensed drawings; see additional credits below.")
existing+="\n"+addition.strip()+"\n"
existing+="\nDownload authentication (method, actual retrieved SHA1): "+str({"internal":(anmethod,ansha),"bipinnaria":(m1,h1),"brachiolaria":(m2,h2)})+"\n"
credit.write_text(existing,encoding="utf-8")
print("INTERNAL=CC-BY-SA-4.0 LARVAE=PUBLIC_DOMAIN TWO_ADDITIONAL_MASTERS=READY")
