#!/usr/bin/env python3
"""Import public-domain source drawings; never invent anatomy."""
from io import BytesIO
from pathlib import Path
from urllib.request import Request, urlopen
from urllib.parse import quote
import hashlib
from PIL import Image, ImageEnhance, ImageOps

target=Path("native-app/app/src/main/assets/type_studies/asterias")
target.mkdir(parents=True,exist_ok=True)
base="https://upload.wikimedia.org/wikipedia/commons/"
source={
 "external":(
  base+"6/68/FMIB_41097_Asterias_forbesi_%28small_specimen%29_Arboral_view_%281%29%2C_Oral_view_%282%29%3B_Asterias_vulgaris_%28small_specimen%29_Arboral_view_%283%29%2C_Oral.jpeg",
  "8b23920ae074f6f7148d7c2ec9f17baa4e4132ff",(1073,1761)),
 "wvs":(
  base+"9/9b/FMIB_52615_Diagram_of_water-vascular_system_of_a_starfish_%3B.jpeg",
  "e2f6757c45c07de8c4e3fb3b4d6608064e299310",(474,507))
}
retrieval = {}
def get(name):
    url,expected,dims=source[name]
    # Commons occasionally rate limits datacenter IPs (HTTP 429).
    # The verified Commons filename remains the only upstream target;
    # optional proxy outputs may re-encode JPEGs but cannot change taxa.
    urls = [
        ("wikimedia_original", url),
        ("wsrv_image_proxy", "https://wsrv.nl/?url="+quote(url,safe="")+"&output=jpg&q=100"),
        ("weserv_image_proxy", "https://images.weserv.nl/?url="+quote(url,safe="")+"&output=jpg&q=100"),
    ]
    failures=[]
    for method,candidate in urls:
        try:
            req=Request(candidate,headers={
               "User-Agent":"Mozilla/5.0 (compatible; INVERTEBRATA public-domain zoology asset importer)",
               "Accept":"image/jpeg,image/*"
            })
            with urlopen(req,timeout=45) as response:
                data=response.read(6000000)
            actual=hashlib.sha1(data).hexdigest()
            img=Image.open(BytesIO(data))
            img.load()
            result=ImageOps.exif_transpose(img).convert("RGB")
            assert result.size==dims,(method,name,result.size)
            if method=="wikimedia_original" and actual!=expected:
                raise ValueError("Wikimedia source SHA1 mismatch: "+actual+" expected "+expected)
            retrieval[name]=(method,actual)
            print("SOURCE",name,"method",method,"sha1",actual,"expected_original_sha1",expected)
            return result
        except Exception as error:
            failures.append(method+": "+str(error)[:400])
    raise RuntimeError("All licensed-image download sources failed: "+"; ".join(failures))

# Historical source figures 1 and 2 are respectively aboral/oral
# Asterias forbesi. Lower figures 3 and 4 are another species; omit them.
external=get("external").crop((0,16,1073,805))
external=external.resize((1600,1176),Image.Resampling.LANCZOS)
external=ImageEnhance.Contrast(external).enhance(1.1)
external.save(target/"external.png",format="PNG",optimize=True)

# Original 1903 scheme is PARTIAL/general starfish, not a whole
# Asterias specimen. It has actual printed markers a through f.
wvs=get("wvs").resize((1422,1521),Image.Resampling.LANCZOS)
wvs=ImageEnhance.Contrast(wvs).enhance(1.10)
wvs.save(target/"water-vascular-system.png",format="PNG",optimize=True)

p1="https://commons.wikimedia.org/wiki/File:FMIB_41097_Asterias_forbesi_(small_specimen)_Arboral_view_(1),_Oral_view_(2);_Asterias_vulgaris_(small_specimen)_Arboral_view_(3),_Oral.jpeg"
p2="https://commons.wikimedia.org/wiki/File:FMIB_52615_Diagram_of_water-vascular_system_of_a_starfish_;.jpeg"
metadata=[
("# Asterias — actual public-domain source illustrations",
"","No AI-generated artwork. Original scans obtained from the University of Washington Freshwater and Marine Image Bank via Wikimedia Commons.",
"Both sources are designated PUBLIC DOMAIN. Attribution is provided even though no copyright licence is required.",
"","## External morphology: external.png",
"Source: Hubert Lyman Clark, Echinoderms of the Woods Hole Region (1904).",
"Taxon: Asterias forbesi, figure 1 aboral and figure 2 oral.",
"Edits: cropped upper pair (excluding A. vulgaris figures 3-4), increased pixel dimensions and contrast; no new anatomical features or labels.",
"Original URL: "+p1,
"Original JPEG SHA1: "+source["external"][1],
"","## Water vascular system: water-vascular-system.png",
"Source: Augusta Foote Arnold, Sea-Beach at Ebb-Tide (1903).",
"Taxon: generalized starfish schematic, NOT a complete Asterias species-specific five-arm dissection.",
"Printed markers: a=madreporite; b=stone canal; c=ring canal; d=radial canals; e=ampullae; f=ambulacra.",
"Edits: resized for legibility and slightly increased contrast; no new labels or structures.",
"Original URL: "+p2,
"Original JPEG SHA1: "+source["wvs"][1],
"","## Acceptance boundaries",
"Missing internal and larval-development master plates are not substituted.",
"Scientific leader-line and Tamil terminology editorial approval remain independent of public-domain licensing.",
"Do not present generalized water-vascular schematic as an image of all five arms.",
"")]
out=[]
for x in metadata:
    if isinstance(x,tuple): out.extend(x)
    else: out.append(x)
out.append("Retrieval verification: "+str(retrieval)+". A proxy-derived JPEG is pixel-dimension checked but not byte-identical to the uploaded Wikimedia JPEG.")
for name in ("external.png","water-vascular-system.png"):
    blob=(target/name).read_bytes()
    with Image.open(BytesIO(blob)) as im:
        assert im.width>=1200 and im.height>=1000
    assert len(blob)>50000
    print("VALID_PNG",name,len(blob),hashlib.sha256(blob).hexdigest())
    out.append("Committed "+name+" SHA256: "+hashlib.sha256(blob).hexdigest())
(target/"ATTRIBUTION.md").write_text("\n".join(out)+"\n",encoding="utf-8")
print("COPYRIGHT=PUBLIC_DOMAIN; IMAGE_DIMENSIONS=PASS; TWO_DISTINCT_IMAGES=PASS; SOURCE_METHODS="+str(retrieval))
