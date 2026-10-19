#!/usr/bin/env python3
"""Evidence-only reconciliation of committed REVIEW images against missing native plate paths.
No images are copied/promoted; biological, editorial, rights and device approval stay HOLD.
"""
import argparse
import hashlib
import json
from pathlib import Path
import runpy
import struct

# Associations are tentative review candidates, NOT verified anatomy equivalences.
# The uploaded source filenames are intentionally preserved.
CANDIDATES = {
 ("earthworm","external-setae"):("01_earthworm_external_REVIEW_ONLY.png","setae and segment boundaries"),
 ("earthworm","digestive"):("02_earthworm_digestive_REVIEW_ONLY.png","crop/anus/typhlosole"),
 ("earthworm","circulatory"):("03_earthworm_vascular_REVIEW_ONLY.png","aortic arches and heart leader endpoints"),
 ("earthworm","nervous"):("04_earthworm_nervous_REVIEW_ONLY.png","ganglia and nerve cord"),
 ("earthworm","excretory"):("05_earthworm_excretory_REVIEW_ONLY.png","three nephridial categories"),
 ("earthworm","reproductive"):("06_earthworm_reproductive_REVIEW_ONLY.png","spermathecae, ducts and segments"),
 ("penaeus","external"):("07_penaeus_external_REVIEW_ONLY.png","appendages and body segments"),
 ("penaeus","appendages"):("08_penaeus_mouthparts_REVIEW_ONLY.png","PARTIAL: mouthparts are not the complete appendage series"),
 ("penaeus","internal"):("10_penaeus_internal_REVIEW_ONLY.png","gills, alimentary and other organs"),
 ("penaeus","larval-development"):("11_penaeus_lifecycle_REVIEW_ONLY.png","nauplius, protozoea, mysis and postlarva"),
 ("pila","external"):("12_pila_external_REVIEW_ONLY.png","shell, operculum, orientation"),
 ("pila","mantle-cavity"):("13_pila_internal_REVIEW_ONLY.png","PARTIAL: internal views do not prove mantle cavity coverage"),
 ("pila","ctenidium"):("08_respiratory_system_REVIEW.png","PARTIAL: respiratory diagram does not prove ctenidial anatomy"),
 ("asterias","external"):("15_asterias_external_REVIEW_ONLY.png","oral/aboral orientation, podia and madreporite"),
 ("asterias","water-vascular-system"):("16_asterias_water_vascular_REVIEW_ONLY.png","ring/radial/stone canals and ampullae"),
 ("asterias","internal"):("17_asterias_internal_REVIEW_ONLY.png","digestive and gonadal organ boundaries"),
}
ROOT="native-app/app/src/main/assets"

def digest(path):
    return hashlib.sha256(path.read_bytes()).hexdigest()

def dimensions(path):
    b=path.read_bytes()[:24]
    return list(struct.unpack(">II",b[16:24])) if b[:8]==b"\x89PNG\r\n\x1a\n" and b[12:16]==b"IHDR" else None

def debug_path(repo,org,name):
    if name.endswith("_REVIEW_ONLY.png"):
        return repo/("native-app/app/src/debug/assets/review_only/u59/"+org+"/"+name)
    return None   # Pila 12-panel archive is staged only by Gradle debug tasks

def main():
    parser=argparse.ArgumentParser()
    parser.add_argument("--repo-root",type=Path,default=Path("."))
    parser.add_argument("--out",type=Path,default=Path("qa-evidence/u63-committed-images"))
    args=parser.parse_args()
    root=args.repo_root
    original=runpy.run_path(str(root/".github/scripts/audit-five-unit-assets.py"))
    required=[r for r in original["audit"](root) if r["required"]]
    assert len(required)==43
    valid={(r["organism"],r["plate"]) for r in required}
    assert set(CANDIDATES)<=valid
    rows=[]
    for plate in required:
        key=(plate["organism"],plate["plate"])
        info=CANDIDATES.get(key)
        filename=info[0] if info else None
        candidate=root/filename if filename else None
        committed=bool(candidate and candidate.is_file())
        debug=debug_path(root,plate["organism"],filename) if filename else None
        debug_copy=bool(debug and debug.is_file())
        hashvalue=digest(candidate) if committed else None
        rows.append({
          "unit":plate["unit"],"organism":plate["organism"],"required_plate":plate["plate"],
          "required_asset_path":ROOT+"/"+plate["required_stem"],
          "required_asset_state":plate["state"],
          "candidate_source_path":filename if committed else None,
          "candidate_source_sha256":hashvalue,
          "candidate_dimensions_px":dimensions(candidate) if committed else None,
          "candidate_already_in_debug":debug_copy,
          "debug_bytes_match_committed_original":(hashvalue==digest(debug)) if committed and debug_copy else None,
          "candidate_coverage_caution":info[1] if info else "No specifically mapped root review candidate; search other committed source files before drawing",
          "morphological_accuracy":"NOT_APPROVED",
          "leader_line_endpoints":"NOT_APPROVED",
          "english_tamil_labels":"NOT_APPROVED",
          "image_rights":"NOT_APPROVED",
          "production_promotion":False
        })
    gaps=[r for r in rows if r["required_asset_state"]!="PRESENT_UNVERIFIED"]
    mapped=[r for r in gaps if r["candidate_source_path"]]
    unmapped=[r for r in gaps if not r["candidate_source_path"]]
    altered=[r for r in rows if r["debug_bytes_match_committed_original"] is False]
    result={
        "unit_count":5,"mandatory_plate_count":len(rows),
        "canonical_present_but_not_approved":len(rows)-len(gaps),
        "canonical_missing":len(gaps),
        "canonical_missing_with_named_committed_candidate":len(mapped),
        "canonical_missing_without_named_review_candidate":len(unmapped),
        "byte_mismatched_debug_candidates":len(altered),
        "all_scientific_and_release_approvals":"HOLD",
        "notice":"Named candidate does NOT establish complete anatomical equivalence; debug-only and root review files must not be copied to main assets without independent sign-off.",
        "plates":rows
    }
    args.out.mkdir(parents=True,exist_ok=True)
    (args.out/"committed-image-map.json").write_text(json.dumps(result,indent=2,ensure_ascii=False)+"\n",encoding="utf-8")
    lines=["# Existing committed image intake (not production approval)","",
        f"Mandatory production slots: {len(rows)-len(gaps)}/{len(rows)} (all unapproved).",
        f"Unfilled canonical slots: {len(gaps)}. With a specific named committed review candidate: {len(mapped)}. Without one: {len(unmapped)}.",
        "",
        "| Unit | Required plate | Committed candidate | Caution |",
        "|---|---|---|---|"]
    for rec in gaps:
        lines.append(f"| {rec['unit']} | {rec['organism']}/{rec['required_plate']} | {rec['candidate_source_path'] or 'not mapped'} | {rec['candidate_coverage_caution']} |")
    lines+=["","Every candidate needs pixel-level anatomical, source-licence, Tamil and Android-device verification. No promotion made.",""]
    (args.out/"README.md").write_text("\n".join(lines),encoding="utf-8")
    print(f"COMMITTED_IMAGE_RECONCILIATION={len(mapped)} mapped review candidates for {len(gaps)} unfilled canonical slots")
    print(f"UNMAPPED_GAPS={len(unmapped)} DEBUG_HASH_MISMATCHES={len(altered)} RELEASE=HOLD")
    if altered:
        raise SystemExit("DEBUG_REVIEW_IMAGE_BYTES_DIFFER_FROM_ROOT_UPLOADS")
if __name__=="__main__":
    main()
