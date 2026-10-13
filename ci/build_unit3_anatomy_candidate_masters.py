#!/usr/bin/env python3
"""Create six actual archived-illustration raster masters; NO procedural anatomy.

The former six-fake-shape builder is intentionally replaced, not run as a
background substitute. Every organism stroke derives from an attributed
original scanned illustration or the CDC's independently documented
epidemiological raster. Titles/credit panels are outside the art.

These are still editorial candidates; this tool DOES NOT sign off anatomy,
English/Tamil leader endpoints, hand-pencil provenance, or device quality.
"""
import hashlib
import io
import json
import urllib.parse
import urllib.request
from pathlib import Path
from PIL import Image, ImageDraw, ImageFont, ImageOps

OUT = Path("native-app/app/src/main/assets/type_studies")
META = Path("qa-evidence/unit3/source-backed-plate-manifest.json")
SIZE = (960, 1280)
SOURCES = (
  ("fasciola", "external",
   "EB_9th_Volume23_Trematota_Fig_1.png", "1888 Encyclopaedia Britannica, Trematoda, Fig. 1",
   "Adult Fasciola; mouth, suckers, intestinal branches",
   "Historical figure includes several systems and inset panels."),
  ("fasciola", "digestive-reproductive",
   "Diagnostic_methods,_chemical,_bacteriological_and_microscopical_-_a_text-book_for_students_and_practitioners_(1909)_(14781538944).jpg",
   "R. W. Webster (1909), Fig. 51: sexual glands",
   "Branched ovary and tandem testes; vitelline glands",
   "Confirm that original page depicts Fasciola hepatica."),
  ("fasciola", "life-cycle",
   "Animal_parasites_and_human_disease_(1918)_(18198420775).jpg",
   "A. C. Chandler (1918), Fig. 63",
   "Egg > miracidium > sporocyst > rediae > cercaria",
   "Also includes aquatic vegetation, metacercaria and mammal."),
  ("ascaris", "male-female-external",
   "Pfurtscheller_Table_38_a.png",
   "Paul Pfurtscheller, Zoologische Wandtafeln, pre-1927",
   "Adult female and male; head and curved male tail",
   "Historical multi-panel chart; review original sex labels."),
  ("ascaris", "female-internal",
   "Text-book_of_comparative_anatomy_(1898)_(14776166501).jpg",
   "Text-book of comparative anatomy (1898), Figs. 168-169",
   "Vulva, vagina, uterus, oviducts and tubular ovaries",
   "Confirm female fig. 169 separately from male fig. 168."),
  ("ascaris", "life-cycle",
   "Ascaris_lumbricoides_life_cycle.png",
   "CDC DPDx (2019), public-domain life cycle",
   "Soil-embryonated eggs, intestine, lung and reswallowing",
   "CDC source is precise raster, NOT historical pencil art: ART HOLD.")
)

def font(sz, bold=False):
    filename = "/usr/share/fonts/truetype/dejavu/DejaVuSans" + ("-Bold" if bold else "") + ".ttf"
    return ImageFont.truetype(filename, sz)

def fetch_commons(filename):
    # Direct Wikimedia static asset URL: SHA fanout is MD5 of the Wikimedia
    # canonical filename. Avoid Wikimedia's rate-limited MediaWiki API.
    from hashlib import md5
    digest=md5(filename.encode("utf-8")).hexdigest()
    url=("https://upload.wikimedia.org/wikipedia/commons/"+digest[0]+"/"+
         digest[:2]+"/"+urllib.parse.quote(filename,safe="()_,"))
    hdr={"User-Agent":"Mozilla/5.0 (compatible; GASCZoologySourceAudit/1.0)"}
    import time
    last_error=None
    for attempt in range(3):
        try:
            with urllib.request.urlopen(urllib.request.Request(url,headers=hdr),timeout=60) as f:
                raw=f.read(15_000_001)
            assert 15_000 < len(raw) < 15_000_000, f"Source corrupt or oversized: {filename}"
            return raw,url
        except Exception as ex:
            last_error=ex
            if attempt<2:time.sleep(3*(attempt+1))
    raise RuntimeError("Wikimedia source inaccessible: "+filename) from last_error

def draw_master(record):
    org,plate,filename,credit,focus,note=record
    raw,url=fetch_commons(filename)
    with Image.open(io.BytesIO(raw)) as source:
        assert source.format in ("PNG","JPEG") and source.width>=400 and source.height>=400,(filename,source.size)
        orig_size=source.size
        # Grayscale only where historic drawings were originally monochrome.
        # Preserve archive pixel geometry and visible anatomical structures:
        # do not redraw spicules, gonads, ducts, larvae or life-cycle arrows.
        picture=source.convert("RGB")
        if org=="fasciola" or plate=="female-internal":
            picture=ImageOps.grayscale(picture).convert("RGB")
    # Downscaling to mobile reading area is permitted; no enlargement.
    picture.thumbnail((920,950),resample=Image.Resampling.LANCZOS)
    out=Image.new("RGB",SIZE,(255,254,250))
    d=ImageDraw.Draw(out)
    title=(org.upper()+"  —  "+plate.replace("-"," ").upper())
    d.text((20,18),title,font=font(28,True),fill=(33,31,29))
    d.text((21,63),"ARCHIVED ANATOMICAL ILLUSTRATION / SOURCE-BACKED RASTER",
            font=font(13),fill=(92,85,75))
    px=(SIZE[0]-picture.width)//2
    py=106+(950-picture.height)//2
    out.paste(picture,(px,py))
    d.line((22,1070,938,1070),fill=(124,119,109),width=2)
    d.text((22,1084),focus,font=font(17,True),fill=(41,37,33))
    d.text((22,1121),credit,font=font(14),fill=(67,63,57))
    d.text((22,1153),"AUTHORITATIVE PROVENANCE; ZOOLOGICAL REVIEW PENDING",
             font=font(13),fill=(103,69,43))
    d.text((22,1185),note[:106],font=font(13),fill=(79,72,65))
    output=OUT/org/(plate+".png")
    output.parent.mkdir(parents=True,exist_ok=True)
    out.save(output,"PNG",optimize=True)
    result={"organism":org,"plate_id":plate,"file":str(output),
            "source_filename":filename,"source_url":url,
            "source_sha256":hashlib.sha256(raw).hexdigest(),
            "source_dimensions":list(orig_size),
            "output_dimensions":list(SIZE),
            "output_sha256":hashlib.sha256(output.read_bytes()).hexdigest(),
            "source_illustration_only":True,
            "pencil_origin_verified":False,
            "anatomy_academic_review":"PENDING",
            "hotspot_academic_review":"PENDING",
            "image_interpretation_note":note}
    print("SOURCE_BACKED_RASTER="+str(output)+" "+result["output_sha256"])
    return result

def main():
    results=[draw_master(rec) for rec in SOURCES]
    assert len(results)==6
    META.parent.mkdir(parents=True,exist_ok=True)
    META.write_text(json.dumps({"branch_candidate_only":True,
            "no_procedural_anatomy":True,
            "scientific_acceptance":"HOLD",
            "source_records":results},indent=2),encoding="utf-8")
    for rec in results:
        with Image.open(rec["file"]) as f:
            assert f.size==SIZE and f.format=="PNG"
    print("SIX_SOURCE_BACKED_RASTERS=PASS; PENCIL_ORIGIN_AND_ACADEMIC_ACCEPTANCE=HOLD")

if __name__=="__main__": main()
