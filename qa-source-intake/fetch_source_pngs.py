#!/usr/bin/env python3
"""Download explicitly attributed, existing anatomical source illustrations.

Produces *separate PNG candidate files*, independent of required Android
master slots. Never installs a candidate into app/src/main/assets or changes
academic approval records. Use only on GitHub CI or another network-capable host.
"""
import hashlib
import io
import json
import time
from pathlib import Path
from urllib.parse import urlparse
import requests
from PIL import Image, ImageOps

ROOT = Path(__file__).resolve().parent
CONFIG = ROOT / "source_candidates.json"
OUT = ROOT / "downloaded-png-review"
USER_AGENT = "INVERTEBRATA-Zoology-SourceIntake/1.0 (https://github.com/rameshgascngl-create/Invertebrata-)"
def main():
    data = json.loads(CONFIG.read_text(encoding="utf-8"))
    OUT.mkdir(parents=True, exist_ok=True)
    report = {"schema_version":1,"source_manifest":str(CONFIG.relative_to(ROOT.parent)), "candidates":[], "failures":[]}
    for item in data["sources"]:
        url = item["download_url"]
        host = urlparse(url).hostname
        assert host in {"upload.wikimedia.org","thumb.wikimedia.org"}, host
        try:
            content = None
            for attempt in range(3):
                resp = requests.get(url, headers={"User-Agent":USER_AGENT,
                    "Accept":"image/png,image/jpeg,image/webp,image/*;q=0.8"}, timeout=90)
                if resp.status_code == 200:
                    content = resp.content
                    break
                if resp.status_code not in (429,502,503,504):
                    resp.raise_for_status()
                time.sleep(2*(attempt+1))
            if content is None:
                raise RuntimeError("Download retries exhausted: HTTP "+str(resp.status_code))
            if len(content) > 20*1024*1024:
                raise ValueError("Source is larger than allowed intake limit")
            with Image.open(io.BytesIO(content)) as src:
                src.verify()
            with Image.open(io.BytesIO(content)) as src:
                raster=ImageOps.exif_transpose(src)
                raster.thumbnail((2600,2600),Image.Resampling.LANCZOS)
                if raster.mode in ("RGBA","LA") or (raster.mode=="P" and "transparency" in raster.info):
                    rgba=raster.convert("RGBA")
                    background=Image.new("RGBA",rgba.size,(255,255,255,255))
                    background.alpha_composite(rgba)
                    raster=background.convert("RGB")
                else:
                    raster=raster.convert("RGB")
                if min(raster.size)<450:
                    raise ValueError("Source is too small for core image review: "+str(raster.size))
                target=OUT/item["file"]
                raster.save(target,format="PNG",optimize=True)
            output=target.read_bytes()
            record={key:item[key] for key in ("slot","file","source_file","source_page","author","license","scope","reason")}
            record.update({"input_bytes":len(content),"source_sha256":hashlib.sha256(content).hexdigest(),
                           "output_bytes":len(output),"png_sha256":hashlib.sha256(output).hexdigest(),
                           "size":list(raster.size),"conversion":"Original pixels decoded; resized down if >2600; white compositing for transparent PNG; no redrawn anatomy"})
            report["candidates"].append(record)
            print("INTAKE_OK",item["slot"],target.name,record["size"],flush=True)
        except Exception as exc:
            report["failures"].append({"slot":item["slot"],"error":type(exc).__name__+": "+str(exc)})
            print("INTAKE_FAILED",item["slot"],str(exc),flush=True)
    (OUT/"PROVENANCE.json").write_text(json.dumps(report,ensure_ascii=False,indent=2)+"\n",encoding="utf-8")
    readme=["# Source-sourced PNG review candidates","",
            "No file here is a promoted INVERTEBRATA master. Review against the exact lesson",
            "objective, licence terms and phone-size label readability before using it.",
            "The 41 provisionally accepted images and 11 held assets are untouched.","",
            f"Downloaded: {len(report['candidates'])}; failed: {len(report['failures'])}",""]
    for x in report["candidates"]:
        readme.extend([f"- {x['file']} · {x['slot']} · {x['scope']}",
                       f"  Author: {x['author']} · {x['license']}",
                       f"  Source: {x['source_page']}",
                       f"  Qualification: {x['reason']}"])
    (OUT/"README.md").write_text("\n".join(readme)+"\n",encoding="utf-8")
    if len(report["candidates"])<3:
        raise SystemExit("Fewer than three source files downloaded; no partial success claim.")
if __name__=="__main__": main()
