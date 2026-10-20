#!/usr/bin/env python3
"""Replace rejected Unit I teaching bitmaps with distinct, pinned published figures.

All shapes are from source art; Pillow merely places a decoded published image
on a light neutral page. No Compose Canvas, AI-labelled organ leaders, or
procedurally drawn specimen geometry. Pinning stops silent upstream changes.
CC BY-SA attribution lives in SOURCE_PROVENANCE.txt and native captions.
"""
import hashlib, io, json, time, urllib.request, subprocess
from pathlib import Path
from PIL import Image, ImageDraw, ImageFont, ImageOps
import cairosvg

ROOT = Path("native-app/app/src/main/assets")
OUT = ROOT / "unit1"
REFERENCES = OUT / "references"
HEADER_FONT = "/usr/share/fonts/truetype/dejavu/DejaVuSans.ttf"
RECORDS = [
  ("paramecium_external", "Paramecium diagram.png", "9690f7018985923cb02f94530e6a61313ffeb784", "Deuterostome, CC BY-SA 4.0", "PARAMECIUM | EXTERNAL ANATOMY", "unit1/paramecium_external.png"),
  ("paramecium_oral", "Paramecium Anatomy.svg", "813c6f4266cb68b2f632a4622a565785eeee410e", "Ciarachristina, CC BY-SA 3.0", "PARAMECIUM | ORAL & FEEDING ANATOMY", "unit1/paramecium_oral.png"),
  ("paramecium_internal", "Paramecium sp+Nr.svg", "b693f7955103ae3be6801a63ce2f426c8ddb4ce7", "Karlis Kalviskis, CC BY-SA 4.0", "PARAMECIUM | CELL ORGANISATION", "unit1/paramecium_internal.png"),
  ("paramecium_vacuole", "Contractile Vacuole In Paramecium.gif", "531bf47b511ad7cf4437c32ab0ee370982f95073", "Katyddd, CC BY-SA 4.0", "PARAMECIUM | WATER-BALANCE MODEL", "unit1/paramecium_vacuole.png"),
  ("trypanosoma", "Trypanosoma African life cycle CDC.jpg", "6d679576bd38e7c7f9e61e3d605af9ced5ea0e31", "CDC DPDx, US public domain", "TRYPANOSOMA BRUCEI | HOST-VECTOR CYCLE", "unit1/references/trypanosoma.png"),
  ("leishmania", "Leishmania LifeCycle.gif", "dc86a4f4f56c8a7b6df3db83ec8aaad3a5999184", "CDC DPDx, US public domain", "LEISHMANIA | AMASTIGOTE & PROMASTIGOTE", "unit1/references/leishmania.png"),
]
def commons_url(filename):
    from urllib.parse import quote
    canonical = filename.replace(" ", "_")
    code = hashlib.md5(canonical.encode("utf-8")).hexdigest()
    return "https://upload.wikimedia.org/wikipedia/commons/" + code[0] + "/" + code[:2] + "/" + quote(canonical)

def download(filename, sha1):
    url = commons_url(filename)
    last = None
    for attempt in range(4):
        try:
            request = urllib.request.Request(url, headers={"User-Agent":"GASC-Nagercoil-Zoology-Atlas/1.0 (educational source verification; Wikimedia Commons)"})
            with urllib.request.urlopen(request, timeout=65) as response:
                payload = response.read(30_000_000)
            actual = hashlib.sha1(payload).hexdigest()
            if actual != sha1:
                raise ValueError("Unexpected publisher bytes for " + filename + ": " + actual)
            return url,payload
        except Exception as ex:
            last = ex
            time.sleep((attempt+1)*4)
    raise RuntimeError("Pinned source unavailable: "+filename) from last

def decoded(filename, raw):
    if filename.lower().endswith(".svg"):
        try:
            image_bytes = cairosvg.svg2png(bytestring=raw, output_width=1700)
        except Exception as cairo_error:
            # Published Inkscape SVG contains zero-length arrow markers which
            # CairoSVG cannot render; librsvg preserves original source paths.
            proc = subprocess.run(["rsvg-convert","-w","1700"], input=raw,
                stdout=subprocess.PIPE, stderr=subprocess.PIPE)
            if proc.returncode != 0:
                raise RuntimeError("CairoSVG and librsvg could not render source") from cairo_error
            image_bytes = proc.stdout
        return Image.open(io.BytesIO(image_bytes)).convert("RGBA")
    with Image.open(io.BytesIO(raw)) as original:
        if filename.endswith("Vacuole_In_Paramecium.gif"):
            original.seek(min(3,original.n_frames-1))
        return original.convert("RGBA")

def build(img, title):
    # Editorial framing ONLY. Draw no biology or inferred anatomy.
    page = Image.new("RGB",(1700,1700),(252,251,248))
    rgba = img.copy()
    rgba.thumbnail((1570,1440),Image.Resampling.LANCZOS)
    matte = Image.new("RGBA",rgba.size,"white")
    matte.alpha_composite(rgba)
    page.paste(matte.convert("RGB"),((1700-rgba.width)//2,(1640-rgba.height)//2+40))
    brush = ImageDraw.Draw(page)
    font = ImageFont.truetype(HEADER_FONT,37)
    brush.text((52,45), title, fill=(26,36,43), font=font)
    return page

manifest=[]
old_shas={
 "unit1/paramecium_external.png":"115115b0ba0a3758dad65087c2b3080a785c9fa5",
 "unit1/paramecium_oral.png":"079028b0f2b6e8ecfa5546dab29fc9724157a753",
 "unit1/paramecium_vacuole.png":"c90156f3e4188af59d75a22783d1d6a2fc39ee49",
 "unit1/paramecium_internal.png":"c685b1db80bdf2028ba849e127ac867f4ccd9e34",
}
for ident,name,sha1,rights,title,where in RECORDS:
    url,raw=download(name,sha1)
    png=build(decoded(name,raw),title)
    path=ROOT/where
    path.parent.mkdir(parents=True,exist_ok=True)
    png.save(path,format="PNG",optimize=True)
    with Image.open(path) as check:
        assert check.format=="PNG" and check.size==(1700,1700),where
    # A SHA-256 change is mandatory: prior old photographic crops may not survive.
    new_raw=path.read_bytes()
    digest=hashlib.sha256(new_raw).hexdigest()
    manifest.append({"id":ident,"path":where,"source_url":url,"source_sha1":sha1,
                     "output_sha256":digest,"license_or_rights":rights})
    print("REPLACED",where,"sha256",digest,"source",name)
# Also replace the duplicated type study slots, used by other native screens.
for current, target in [
 ("paramecium_external","external-cilia"),
 ("paramecium_oral","oral-apparatus"),
 ("paramecium_vacuole","contractile-vacuoles"),
]:
    source=OUT/("paramecium_"+current.split("_")[-1]+".png")
    if current=="paramecium_external": source=OUT/"paramecium_external.png"
    if current=="paramecium_oral": source=OUT/"paramecium_oral.png"
    if current=="paramecium_vacuole": source=OUT/"paramecium_vacuole.png"
    dest=ROOT/"type_studies"/"paramecium"/(target+".png")
    dest.write_bytes(source.read_bytes())
for idx,a in enumerate(manifest):
    if idx<4:
        assert a["output_sha256"] not in [hashlib.sha256(bytes.fromhex(s)).hexdigest() for s in old_shas.values()]
(REFERENCES/"U60_REPLACEMENTS_MANIFEST.json").write_text(json.dumps(manifest,indent=2,ensure_ascii=False)+"\n",encoding="utf-8")
(REFERENCES/"SOURCE_PROVENANCE.txt").write_text(
    "U60 UNIT I REPLACEMENT: distinct source-backed anatomical/lifecycle PNGs.\n"
    "Generated composite from chat NOT imported: its labels and anatomy were not independently approved.\n"
    "Original 2014 Rijan photo crops are retired from the six active teaching slots.\n"
    "All attribution and upstream pages are preserved in U60_REPLACEMENTS_MANIFEST.json.\n"
    "Published diagrams are educational references, not proof of species-specific specimen geometry.\n"
    "No source-photo hotspots have been enabled. Tamil editorial and handset QA still required.\n",
    encoding="utf-8")
print("U60 SIX DISTINCT SOURCE PNGS=GENERATED; REVIEW STILL PENDING")
