#!/usr/bin/env python3
"""Academic gate. No unapproved or missing raster can be treated as present."""
from pathlib import Path
import json, hashlib, sys
root=Path(__file__).resolve().parents[1]
base=root/"native-app/app/src/main/assets/unit2"
required={
 "sycon_historical.png":"1903 historical section: expert anatomy/source review pending",
 "obelia_lifecycle.png":"high-resolution authentic 1933 historical life cycle master",
 "obelia_settlement.png":"original-source settlement plate; recovered PDF composite is candidate only",
}
existing=sorted(p.name for p in base.iterdir() if p.is_file()) if base.exists() else []
items=[]
for filename,description in required.items():
    f=base/filename
    items.append({"file":filename,"exists":f.is_file(),"source_requirement":description,
       "sha256":hashlib.sha256(f.read_bytes()).hexdigest() if f.is_file() else None,
       "academic_approved":False})
report={"scope":"UNIT_II_SOURCE_IMAGE_COMPLETENESS","outcome":"HOLD",
        "bundled_rasters":existing,"required_image_masters":items,
        "bilingual_editorial_review":"PENDING",
        "anatomical_review":"PENDING","source_rights_review":"PENDING",
        "no_programmatic_substitutes":True}
out=root/"qa-evidence/unit2-image-only"
out.mkdir(parents=True,exist_ok=True)
(out/"image-master-report.json").write_text(json.dumps(report,indent=2,ensure_ascii=False)+"\n")
print("UNIT_II_IMAGE_MASTERS=HOLD")
for it in items:print("  "+it["file"]+" = "+("FILE_CANDIDATE_PRESENT_NOT_APPROVED" if it["exists"] else "MISSING"))
sys.exit(1)
