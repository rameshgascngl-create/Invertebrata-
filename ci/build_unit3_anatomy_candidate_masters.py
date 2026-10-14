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
   "CDC_DPDx_Ascariasis_LifeCycle_lg.jpg",
   "CDC DPDx: Ascariasis lifecycle diagram (official public-health reference)",
   "Embryonated eggs · ingestion · intestinal hatch · lung migration · re-swallow",
   "Complete cycle depicted; not a graphite-pencil scan. Hotspots still require source-pixel review.")
)

# Byte-integrity seal from the actual first-pass GitHub artifact. This does
# NOT certify anatomy; it prevents silent artwork substitution across builds.
ACCEPTED_SOURCE_BYTES = {
    ("fasciola", "external"): ("91b86a437a5ca68763adfbd5b2995fecca6c87936d05204da11ef8753eb55141", "08adafaf9756a01ec20f2a9614e05b80a29223b1c2a5d29564bb9923b8977e25"),
    ("fasciola", "digestive-reproductive"): ("6e13cd0856bd8de9613a368c027978a5c234c9b7c65e2f3a11d2f57ed8783101", "37e09f88775bb14a55129db191a59b25c5c80070ca2b6bb3079518a31327c791"),
    ("fasciola", "life-cycle"): ("c12b932fae3d751c7435f0b3377fa82ee0e8dc6a5d027516ab2ac92f840463af", "9a7ab9bb76fb9e1e8b1527bc9363c6349ecafce739451a82307bc3b43108520a"),
    ("ascaris", "male-female-external"): ("fc48dbe207af955e2d8f8f9d8e4fa03a165189769ba3b2d9725743203983d362", "d8df8c8eb4b79e9186017650d818060b756a302968eb4d73c4329e88d73908c9"),
    ("ascaris", "female-internal"): ("25b545745a64e0a1fc838c4c50001137b57ec7246e2cfda3ef73d272cc2c6640", "0f476f9823025c4cbd23808c49515652161709d39969b7c39c20e1309171341e"),
    # First-run source/output SHA will be printed, inspected, then pinned.
    # Never accept the obsolete Pfurtscheller crop as a complete Ascaris life cycle.
    ("ascaris", "life-cycle"): (None, None),
}

def font(sz, bold=False):
    filename = "/usr/share/fonts/truetype/dejavu/DejaVuSans" + ("-Bold" if bold else "") + ".ttf"
    return ImageFont.truetype(filename, sz)

def fetch_commons(filename):
    # Reuse source pixels already SHA-pinned by the existing 1888/1927
    # historical plate restoration step. No duplicate Wikimedia download,
    # extra scaling or raster-to-vector conversion of organism anatomy.
    local = {
        "EB_9th_Volume23_Trematota_Fig_1.png":
            ("unit3/fasciola_type.png", (157,115,1443,1823)),
        "Pfurtscheller_Table_38_a.png":
            ("unit3/ascaris_type.png", (58,115,2234,2318))
    }
    if filename in local:
        rel,box=local[filename]
        parent=Path("native-app/app/src/main/assets")/rel
        with Image.open(parent) as im:
            cropped=im.crop(box)
            assert cropped.width>=900 and cropped.height>=1200
            buffer=io.BytesIO()
            cropped.save(buffer, format="PNG")
            raw=buffer.getvalue()
        return raw, "https://commons.wikimedia.org/wiki/File:"+urllib.parse.quote(filename)
    # Direct Wikimedia static asset URL: SHA fanout is MD5 of the Wikimedia
    # canonical filename. Avoid Wikimedia's rate-limited MediaWiki API.
    from hashlib import md5
    digest=md5(filename.encode("utf-8")).hexdigest()
    url=("https://upload.wikimedia.org/wikipedia/commons/"+digest[0]+"/"+
         digest[:2]+"/"+urllib.parse.quote(filename,safe="()_,"))
    # CDC DPDx itself illustrates eggs, soil embryonation, ingestion, gut
    # hatching, lung migration and re-swallowing; the historical egg/adult
    # fragment cannot be passed off as a complete life-cycle figure.
    if filename=="CDC_DPDx_Ascariasis_LifeCycle_lg.jpg":
        url="https://www.cdc.gov/dpdx/ascariasis/modules/Ascariasis_LifeCycle_lg.jpg"
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
    raw_expected, output_expected = ACCEPTED_SOURCE_BYTES[(org,plate)]
    if raw_expected is not None:
        assert hashlib.sha256(raw).hexdigest() == raw_expected, (
            "Historical source bytes changed unexpectedly: "+org+"/"+plate
        )
    elif (org,plate)==("ascaris","life-cycle"):
        print("CDC_CANDIDATE_SOURCE_SHA256="+hashlib.sha256(raw).hexdigest())
    else:
        raise AssertionError("Unpinned source forbidden: "+org+"/"+plate)
    with Image.open(io.BytesIO(raw)) as source:
        assert source.format in ("PNG","JPEG") and source.width>=400 and source.height>=400,(filename,source.size)
        orig_size=source.size
        # Grayscale only where historic drawings were originally monochrome.
        # Preserve archive pixel geometry and visible anatomical structures:
        # do not redraw spicules, gonads, ducts, larvae or life-cycle arrows.
        picture=source.convert("RGB")
        if org=="fasciola" or plate=="female-internal":
            picture=ImageOps.grayscale(picture).convert("RGB")
    # Preserve all seven CDC diagram labels and original route/arrow directions.
    # No partial crop: include fertilized/unfertilized eggs, soil embryonation,
    # ingestion, intestinal hatch, lungs and reswallowing in one complete plate.
    if org=="ascaris" and plate=="life-cycle":
        assert filename=="CDC_DPDx_Ascariasis_LifeCycle_lg.jpg"
        assert orig_size[0] >= 900 and orig_size[1] >= 800
        print("AS_CYCLE_SOURCE_IMAGE_COMPLETE=CDC_DPDx")
    # Downscaling to mobile reading area is permitted; no enlargement.
    picture.thumbnail((920,950),resample=Image.Resampling.LANCZOS)
    out=Image.new("RGB",SIZE,(255,254,250))
    d=ImageDraw.Draw(out)
    title=(org.upper()+"  —  "+plate.replace("-"," ").upper())
    d.text((20,18),title,font=font(28,True),fill=(33,31,29))
    header=("CDC DPDx COMPLETE LIFECYCLE / NOT A PENCIL SCAN"
            if org=="ascaris" and plate=="life-cycle"
            else "ARCHIVED ANATOMICAL ILLUSTRATION / SOURCE-BACKED RASTER")
    d.text((21,63),header,font=font(13),fill=(92,85,75))
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
    if output_expected is not None:
        assert hashlib.sha256(output.read_bytes()).hexdigest()==output_expected, (
            "Raster master bytes changed: "+org+"/"+plate
        )
    else:
        print("CDC_CANDIDATE_OUTPUT_SHA256="+hashlib.sha256(output.read_bytes()).hexdigest())
    result={"organism":org,"plate_id":plate,"file":str(output),
            "source_filename":filename,"source_url":url,
            "source_sha256":hashlib.sha256(raw).hexdigest(),
            "source_dimensions":list(orig_size),
            "output_dimensions":list(SIZE),
            "output_sha256":hashlib.sha256(output.read_bytes()).hexdigest(),
            "source_illustration_only":True,
            "pencil_origin_verified":False,
            "life_cycle_completeness":("CDC_DPDx_COMPLETE_REFERENCE" if
                org=="ascaris" and plate=="life-cycle" else "SOURCE_REVIEW_PENDING"),
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
