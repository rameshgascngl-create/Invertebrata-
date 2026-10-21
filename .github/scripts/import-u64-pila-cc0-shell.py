#!/usr/bin/env python3
"""Install only exact SHA1-verified CC0 Naturalis Pila globosa shell evidence.

This photograph is supplementary. It must never be renamed to external.png
or used to mark an anatomy requirement complete.
"""
from pathlib import Path
from urllib.request import Request, urlopen
from urllib.error import HTTPError
from PIL import Image, ImageOps
from io import BytesIO
from time import sleep
import hashlib

SOURCE_URL = ("https://upload.wikimedia.org/wikipedia/commons/6/61/"
              "Naturalis_Biodiversity_Center_-_ZMA.MOLL.318132_-_Pila_globosa_"
              "Swainson_-_Ampullariidae_-_Mollusc_shell.jpeg")
SOURCE_SHA1 = "625a4add0ab66aebf514b3cce93f66522afeae14"
SOURCE_DIMENSIONS = (1920, 1278)
DEST = Path("native-app/app/src/main/assets/type_studies/pila/shell-specimen-reference.png")
CREDIT = DEST.with_name("SHELL_MUSEUM_ATTRIBUTION.md")
AGENT = ("INVERTEBRATA-Zoology-Education/1.2 "
         "(https://github.com/rameshgascngl-create/Invertebrata-; "
         "CC0 museum specimen educational reuse)")

if DEST.is_file():
    if not CREDIT.is_file() or "ZMA.MOLL.318132" not in CREDIT.read_text("utf-8"):
        raise RuntimeError("Museum raster exists but credit record is absent")
    with Image.open(DEST) as image:
        image.verify()
    print("U64_CC0_SHELL_REFERENCE_ALREADY_IMPORTED; ANATOMY_PLATES_STILL_MISSING")
    raise SystemExit(0)

if not CREDIT.is_file():
    raise RuntimeError("Missing precommitted rights and anatomical limits")
data = None
for attempt in (1, 2):
    try:
        request = Request(SOURCE_URL, headers={"User-Agent": AGENT, "Accept": "image/jpeg", "Accept-Encoding": "identity"})
        with urlopen(request, timeout=45) as response:
            data = response.read(5_000_001)
        break
    except HTTPError as err:
        if err.code == 429 and attempt == 1:
            retry = err.headers.get("Retry-After", "")
            delay = min(90, int(retry)) if retry.isdecimal() else 60
            print("PILA_RATE_LIMIT_SAFETY_WAIT_SECONDS", delay, flush=True)
            sleep(delay)
            continue
        raise

if not data or len(data) > 5_000_000:
    raise RuntimeError("Missing or oversized source JPEG")
digest = hashlib.sha1(data).hexdigest()
assert digest == SOURCE_SHA1, (digest, SOURCE_SHA1)
with Image.open(BytesIO(data)) as image:
    image.load()
    assert image.size == SOURCE_DIMENSIONS, image.size
    original = ImageOps.exif_transpose(image).convert("RGB")
DEST.parent.mkdir(parents=True, exist_ok=True)
original.save(DEST, format="PNG", optimize=True)
raw = DEST.read_bytes()
assert raw[:8] == b"\x89PNG\r\n\x1a\n"
assert len(raw) > 100_000
assert not (DEST.parent / "external.png").exists(), "Never replace required anatomy slot"
print("U64_AUTHENTIC_SHELL_IMAGE", original.size, "SOURCE_SHA1", digest)
print("U64_CC0_SPECIMEN_PNG_SHA256", hashlib.sha256(raw).hexdigest())
print("PILA_FOUR_MANDATORY_PLATES_STILL_MISSING")
