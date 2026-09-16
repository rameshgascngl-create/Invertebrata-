#!/usr/bin/env python3
"""INVERTEBRATA v1.8.8 accepted source-chain reconciler.

SOURCE RECONSTRUCTION ONLY. This script never invokes Gradle, signs an artifact,
or dispatches a workflow. It reconstructs the source state frozen by Matrix 02.
"""
from __future__ import annotations
from pathlib import Path
from collections import Counter
import argparse, csv, hashlib, importlib.util, json, re, subprocess, sys, tempfile

HERE=Path(__file__).resolve().parent
CANONICAL_PAYLOAD='25220886837b724f781612f66ae5f8fd4a983ca644559c4871aff91b479e24b7'
R2_ZIP_SHA256='3f3ffd5d0b03da74b9f7b85cfc755883594aea3141149b1131ee11963d9a184a'
MATRIX_REL='provenance/V188_SOURCE_GATE_MATRIX_02.csv'
ASSIGN_RE=re.compile(r'^(?P<prefix>window\.ORG_SYSTEM_DIAGRAMS\[[^\n]*?\]\s*=\s*[^\n]*?\+)(?P<json>"(?:\\.|[^"\\])*")(?P<suffix>\s*;\s*)$',re.M)
FIG_RE_TMPL=r'<figure\b[^>]*data-v188-plate="{pid}"[^>]*>[\s\S]*?</figure>'


def sha256(p:Path)->str:
    return hashlib.sha256(p.read_bytes()).hexdigest()

def require(c,msg):
    if not c: raise SystemExit(msg)

def run(script:str, root:Path, *args:str):
    cmd=[sys.executable,str(HERE/script),str(root),*map(str,args)]
    cp=subprocess.run(cmd,text=True,capture_output=True)
    if cp.stdout: print(cp.stdout,end='')
    if cp.stderr: print(cp.stderr,end='',file=sys.stderr)
    if cp.returncode: raise SystemExit(f'{script} failed with exit {cp.returncode}')

def load_module(filename:str, name:str):
    spec=importlib.util.spec_from_file_location(name,HERE/filename)
    require(spec and spec.loader,f'cannot load {filename}')
    mod=importlib.util.module_from_spec(spec); spec.loader.exec_module(mod)
    return mod

def targets(root:Path):
    return [root/'academic_payload/index.html',root/'app/src/main/assets/www/index.html']

def decode_assignments(source:str):
    items=[]
    for m in ASSIGN_RE.finditer(source):
        try: markup=json.loads(m.group('json'))
        except Exception as e: raise SystemExit(f'cannot decode ORG_SYSTEM_DIAGRAMS assignment: {e}')
        items.append([m,markup])
    require(items,'no serialized ORG_SYSTEM_DIAGRAMS assignments found')
    return items

def encode_assignments(source:str,items):
    pieces=[]; last=0
    for m,markup in items:
        pieces.append(source[last:m.start()])
        pieces.append(m.group('prefix')+json.dumps(markup,ensure_ascii=False)+m.group('suffix'))
        last=m.end()
    pieces.append(source[last:])
    return ''.join(pieces)

def replace_pid(items,old,new):
    pat=re.compile(FIG_RE_TMPL.format(pid=re.escape(old)))
    total=0
    for it in items:
        it[1],n=pat.subn(new,it[1],count=1); total+=n
    require(total==1,f'{old}: expected exactly one active candidate, found {total}')

def append_after_pid(items,anchor,new,marker):
    require(sum(markup.count(marker) for _,markup in items)==0,f'{marker}: new treatment already exists')
    pat=re.compile(FIG_RE_TMPL.format(pid=re.escape(anchor)))
    total=0
    for it in items:
        def repl(m): return m.group(0)+new
        it[1],n=pat.subn(repl,it[1],count=1); total+=n
    require(total==1,f'{anchor}: expected exactly one insertion anchor, found {total}')

def replace_legacy_caption(items,title,replacement=''):
    esc=re.escape(title)
    # Batch-1 figures intentionally have no data-v188-plate attribute.
    pat=re.compile(r'<figure class="sys-fig v188-pencil-plate">(?=[\s\S]*?<strong>'+esc+r'</strong>)[\s\S]*?</figure>')
    total=0
    for it in items:
        it[1],n=pat.subn(replacement,it[1],count=1); total+=n
    require(total==1,f'legacy figure {title!r}: expected exactly one candidate, found {total}')

def replace_literal_once(items,old,new,what):
    hits=sum(markup.count(old) for _,markup in items)
    require(hits==1,f'{what}: expected one literal, found {hits}')
    for it in items:
        if old in it[1]: it[1]=it[1].replace(old,new,1); return

def serializer_bridge_source(source:str)->str:
    """Apply accepted pre-Pass2 targeted content to serialized figure definitions.

    This deliberately does not call legacy direct-match main() functions because the
    current production candidates are JSON-serialized inside ORG_SYSTEM_DIAGRAMS.
    """
    items=decode_assignments(source)
    ew=load_module('reconcile_v188_earthworm_remediation.py','v188_ew')
    pen=load_module('v188_penaeus_targeted_rectification.py','v188_pen')
    obe=load_module('v188_obelia_targeted_remediation.py','v188_obe')
    ner=load_module('v188_nereis_targeted_remediation.py','v188_ner')
    pila=load_module('v188_pila_targeted_remediation.py','v188_pila')
    ast=load_module('v188_asterias_targeted_remediation.py','v188_ast')
    fa=load_module('v188_fasciola_ascaris_targeted_remediation.py','v188_fa')
    par=load_module('v188_paramecium_cv_cilia_targeted_creation.py','v188_par')

    # Earthworm: five audited replacements; nervous is intentionally protected.
    for old,new in ew.REV.items(): replace_pid(items,old,new)
    replace_literal_once(items,'மண்புழு — இரத்த நாள அமைப்பு','மண்புழு — இரத்த ஓட்ட மண்டலம்','Earthworm vascular Tamil caption')
    replace_legacy_caption(items,'Earthworm — major internal systems (schematic L.S.)','')

    # Penaeus: replace only audited existing candidates and append only genuinely new
    # PENAEUS-03 and PENAEUS-08 treatments. Protected 02/04/06/07 remain from Batch2/master.
    replace_pid(items,'penaeus-external',pen.EXT)
    replace_pid(items,'penaeus-digestive',pen.DIG)
    replace_pid(items,'penaeus-reproductive',pen.REP)
    append_after_pid(items,'PENAEUS-01-R1',pen.APP+pen.EXC,'PENAEUS-08-N1')

    # Obelia p16.
    replace_pid(items,'obelia-colony',obe.COL)
    replace_pid(items,'obelia-medusa',obe.MED)
    replace_pid(items,'obelia-life-cycle',obe.LIFE)

    # Nereis p29; Pass2 will later turn NEREIS-06-N1 into NEREIS-06-R2.
    replace_pid(items,'nereis-external',ner.EXT)
    replace_pid(items,'nereis-parapodium',ner.PAR)
    append_after_pid(items,'NEREIS-02-R1',ner.EXC+ner.REP,'NEREIS-06-N1')

    # Pila p41; circulatory plate is protected and therefore not replaced.
    replace_pid(items,'pila-external',pila.EXT)
    replace_pid(items,'pila-pallial',pila.PAL)
    replace_pid(items,'pila-digestive',pila.DIG)
    replace_pid(items,'pila-nervous',pila.NER)
    replace_pid(items,'pila-reproductive',pila.REP)
    append_after_pid(items,'PILA-DIG-R1',pila.EXC,'PILA-EXC-N1')

    # Asterias: only candidate-backed digestive/reproductive FAILs were remediated.
    replace_pid(items,'asterias-digestive',ast.DIG)
    replace_pid(items,'asterias-reproductive',ast.REP)

    # Fasciola/Ascaris. Replace the master Fasciola reproductive plate; replace the
    # old Batch1 combined Ascaris plate with separate accepted male/female figures.
    replace_pid(items,'master-fasciola-reproductive',fa.FAS)
    replace_legacy_caption(items,'Fasciola hepatica — hermaphrodite reproductive system','')
    replace_legacy_caption(items,'Ascaris lumbricoides — male and female reproductive systems',fa.FEM+fa.MAL)

    # Paramecium independent contractile-vacuole/ciliary requirement remains beside
    # the Batch3 external/oral plate; it does not replace it.
    append_after_pid(items,'paramecium',par.NEW,'PARAMECIUM-CV-CILIA-N1')

    # The old Batch1 Sycon canal is superseded by the protected master plate.
    replace_legacy_caption(items,'Sycon — syconoid canal system and water current','')

    out=encode_assignments(source,items)
    require('Earthworm — major internal systems (schematic L.S.)' not in out,'superseded Earthworm composite survived bridge')
    require('data-v188-plate=\\"master-sycon-canal\\"' in out or 'data-v188-plate="master-sycon-canal"' in out,'master Sycon canal missing after bridge')
    return out

def apply_serializer_bridge(root:Path):
    fs=targets(root)
    for p in fs:
        s=p.read_text(encoding='utf-8')
        require('data-v188-pipeline-bridge="1"' not in s,'serializer bridge already applied')
        s=serializer_bridge_source(s)
        anchor='<script id="v188-contextual-atlas">'
        require(anchor in s,'contextual atlas anchor missing after bridge')
        s=s.replace(anchor,'<meta data-v188-pipeline-bridge="1">\n'+anchor,1)
        p.write_text(s,encoding='utf-8',newline='\n')
    require(fs[0].read_bytes()==fs[1].read_bytes(),'payload copies diverged after serializer bridge')
    print('V188_SERIALIZER_AWARE_TARGETED_BRIDGE=PASS')

def source_definition_integrity(text:str):
    items=decode_assignments(text)
    markups=[x[1] for x in items]
    ids=[i for m in markups for i in re.findall(r'\bid="([^"]+)"',m)]
    dup=sum(v-1 for v in Counter(ids).values() if v>1)
    idset=set(ids)
    refs=[r for m in markups for r in re.findall(r'(?:url\(#|(?:xlink:)?href="#)([^)"\s]+)',m)]
    broken=sorted({r for r in refs if r not in idset})
    pids=[p for m in markups for p in re.findall(r'data-v188-plate="([^"]+)"',m)]
    dup_pids=sum(v-1 for v in Counter(pids).values() if v>1)
    return dup,broken,dup_pids,len(pids)

def validate_static(root:Path):
    a,b=targets(root); require(a.is_file() and b.is_file(),'payload file missing')
    require(a.read_bytes()==b.read_bytes(),'payload copies diverged at final validation')
    text=a.read_text(encoding='utf-8')
    require("versionCode 18800" in (root/'app/build.gradle').read_text(encoding='utf-8'),'versionCode 18800 missing')
    require("versionName '1.8.8'" in (root/'app/build.gradle').read_text(encoding='utf-8'),'versionName 1.8.8 missing')
    require('77a9f0610564c7e2513fc3ea552c90f7656f0adb' not in text,'superseded Pass2 Batch3 implementation marker unexpectedly embedded')
    require('3523f3493f92bf3a7b176a9bff86f524499eefd2' not in text,'superseded Pass2 systems implementation marker unexpectedly embedded')
    dup,broken,dup_pids,pid_count=source_definition_integrity(text)
    require(dup==0,f'final source-definition duplicate IDs: {dup}')
    require(not broken,f'final source-definition broken refs: {broken}')
    require(dup_pids==0,f'final duplicate data-v188-plate IDs: {dup_pids}')
    # Offline-active dependency checks. Normal source/reference hyperlinks are not runtime dependencies.
    bad=[]
    for pat,label in [
        (r'<script[^>]+src=["\']https?://','remote script'),
        (r'<img[^>]+src=["\']https?://','remote image'),
        (r'<link[^>]+href=["\']https?://[^>]+(?:stylesheet|font)','remote stylesheet/font'),
        (r'@import\s+(?:url\()?\s*["\']?https?://','remote CSS import'),
    ]:
        if re.search(pat,text,re.I): bad.append(label)
    require(not bad,'offline dependency failure: '+', '.join(bad))
    # Matrix contract is frozen at exactly 76 data rows.
    matrix=root/MATRIX_REL; require(matrix.is_file(),'Matrix 02 missing from source tree')
    with matrix.open(encoding='utf-8',newline='') as f: rows=list(csv.DictReader(f))
    require(len(rows)==76,f'Matrix 02 row count is {len(rows)}, expected 76')
    print('V188_FINAL_SOURCE_DEFINITION_DUPLICATE_IDS=0')
    print('V188_FINAL_SOURCE_DEFINITION_BROKEN_REFS=0')
    print('V188_FINAL_DUPLICATE_FIGURE_IDS=0')
    print('V188_MATRIX02_ROWS=76')
    print('V188_OFFLINE_ACTIVE_DEPENDENCIES=PASS')
    print('V188_AUTHORITATIVE_SOURCE_SHA256='+sha256(a))
    print('V188_AUTHORITATIVE_SOURCE_BYTES='+str(a.stat().st_size))
    return sha256(a)

def reconstruct(root:Path,r2_zip:Path):
    root=root.resolve(); require(root.is_dir(),'source root does not exist')
    a=root/'academic_payload/index.html'; require(a.is_file(),'academic payload missing')
    require(sha256(a)==CANONICAL_PAYLOAD,'unexpected canonical academic payload before accepted pipeline')
    run('reconcile_r2.py',root,r2_zip)
    require(sha256(r2_zip)==R2_ZIP_SHA256,'R2 deterministic ZIP hash mismatch')
    run('reconcile_v188.py',root)
    run('reconcile_v188_svg_batch1.py',root)
    run('reconcile_v188_svg_batch2.py',root)
    run('reconcile_v188_pencil_atlas_master.py',root)
    run('v188_pencil_atlas_completion_batch3.py',root)
    apply_serializer_bridge(root)
    run('v188_source_blocker_pass2_batch3_labels.py',root)
    run('v188_source_blocker_pass2_systems.py',root)
    run('v188_source_blocker_pass2_integrity.py',root)
    run('v188_source_blocker_pass2_terminology.py',root)
    return validate_static(root)

def main():
    ap=argparse.ArgumentParser()
    ap.add_argument('source_root')
    ap.add_argument('--r2-zip',default=None)
    ns=ap.parse_args(); root=Path(ns.source_root)
    with tempfile.TemporaryDirectory(prefix='v188-reconcile-') as td:
        r2=Path(ns.r2_zip) if ns.r2_zip else Path(td)/'r2.zip'
        final_hash=reconstruct(root,r2)
    print('V188_ACCEPTED_TRANSFORMATIONS_APPLIED=19')
    print('V188_SUPERSEDED_TRANSFORMATIONS_EXECUTED=0')
    print('V188_PIPELINE_RECONSTRUCTION=PASS')
    print('V188_FINAL_PAYLOAD_SHA256='+final_hash)
    print('GRADLE_BUILD=NOT_PERFORMED')

if __name__=='__main__': main()
