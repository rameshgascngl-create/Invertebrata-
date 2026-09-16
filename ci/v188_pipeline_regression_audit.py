#!/usr/bin/env python3
"""Static regression audit for a fully reconstructed v1.8.8 source tree.

Consumes Matrix 02 as the frozen 76-row contract. Does not modify source or build.
"""
from pathlib import Path
from collections import Counter
import csv,json,re,sys

HERE=Path(__file__).resolve().parent
REPO=HERE.parent
MATRIX=REPO/'provenance/V188_SOURCE_GATE_MATRIX_02.csv'
ASSIGN_RE=re.compile(r'^(?P<prefix>window\.ORG_SYSTEM_DIAGRAMS\[(?P<key>"(?:\\.|[^"\\])*")\][^\n]*?\+)(?P<json>"(?:\\.|[^"\\])*")(?P<suffix>\s*;\s*)$',re.M)
FIG_RE=re.compile(r'<figure\b[^>]*data-v188-plate="(?P<pid>[^"]+)"[^>]*>[\s\S]*?</figure>')
ID_RE=re.compile(r'\bid="([^"]+)"')
REF_RE=re.compile(r'(?:url\(#|(?:xlink:)?href="#)([^)"\s]+)')


def require(c,m):
    if not c: raise SystemExit(m)

def assignments(text):
    out=[]
    for m in ASSIGN_RE.finditer(text):
        out.append((json.loads(m.group('key')),json.loads(m.group('json'))))
    require(out,'no ORG_SYSTEM_DIAGRAMS assignments decoded')
    return out

def figure_index(asgn):
    idx={}
    for lesson,markup in asgn:
        for m in FIG_RE.finditer(markup):
            pid=m.group('pid')
            require(pid not in idx,f'duplicate data-v188-plate before regression mapping: {pid}')
            idx[pid]=(lesson,m.group(0))
    return idx

def expected_lesson(rid):
    if rid.startswith('PAR-'): return 'u1-paramecium'
    if rid=='AMO-01': return 'u1-protozoa'
    if rid.startswith(('ENTA-','TRYP-','LEISH-')): return 'u1-proto-parasites'
    if rid.startswith('PLASM-'): return 'u1-host-parasite'
    if rid.startswith('SYC-'): return 'u2-sycon'
    if rid.startswith('POR-'): return 'u2-porifera'
    if rid.startswith('OBE-'): return 'u2-obelia'
    if rid.startswith(('AUR-','PHY-')): return 'u2-cnidaria'
    if rid.startswith('COR-'): return 'u2-coral-economics'
    if rid.startswith('FAS-'): return 'u3-fasciola'
    if rid=='TAE-01': return 'u3-platy'
    if rid=='TAE-02': return 'u3-platy-adapt'
    if rid.startswith('ASC-'): return 'u3-ascaris'
    if rid.startswith('NEM-'): return 'u3-nematode-adapt'
    if rid.startswith('EWT-'): return 'u4-annelida'
    if rid.startswith('NER-'): return 'u4-nereis'
    if rid.startswith('PEN-'): return 'u4-penaeus'
    if rid.startswith('PERI-'): return 'u4-modes-life'
    if rid.startswith('CRUST-'): return 'u4-penaeus'
    if rid.startswith('IPM-'): return 'u4-arthropoda'
    if rid.startswith('PILA-'): return 'u5-pila'
    if rid.startswith('MOL-'): return 'u5-mollusca'
    if rid.startswith('TOR-'): return 'u5-pila'
    if rid.startswith('CEPH-'): return 'u5-cephalopods'
    if rid.startswith('AST-'): return 'u5-asterias'
    if rid.startswith('ECHL-'): return 'u5-echinodermata'
    raise SystemExit(f'no expected lesson mapping for {rid}')

SPECIAL={
 'OBE-02':['obelia-zooids'],
 'OBE-03':['obelia-zooids'],
 'PEN-03':['PENAEUS-03-OVERVIEW','PENAEUS-03-CEPHALIC','PENAEUS-03-THORACIC','PENAEUS-03-ABDOMINAL'],
}

def expected_pids(row):
    rid=row['Requirement ID']
    if rid in SPECIAL: return SPECIAL[rid]
    a=row['Active Candidate'].split(' + ',1)[0].strip()
    if a.startswith('batch2#') or a.startswith('batch3#'): return [a.split('#',1)[1]]
    if a.endswith(' child series'): raise SystemExit(f'unmapped child series: {rid}')
    return [a]

def visible_ok(rid,fig):
    if '<figcaption>' not in fig or '<svg ' not in fig: return False
    # Batch-3 closure requires actual visible callouts, not aria/caption alone.
    batch3={'PAR-01','AMO-01','ENTA-01','TRYP-01','LEISH-01','PLASM-01','POR-01','AUR-01','PHY-01','COR-01','TAE-01','TAE-02','NEM-01','PERI-01','CRUST-01','IPM-01','MOL-01','TOR-01','CEPH-01','ECHL-01'}
    if rid in batch3: return 'v188-pass2-callouts' in fig and 'v188-pass2-label-legend' in fig
    return '<text ' in fig or 'v188-figure-legend' in fig

def main(root):
    root=Path(root).resolve(); html=root/'academic_payload/index.html'
    require(html.is_file(),'reconstructed payload missing')
    text=html.read_text(encoding='utf-8'); asgn=assignments(text); idx=figure_index(asgn)
    with MATRIX.open(encoding='utf-8',newline='') as f: rows=list(csv.DictReader(f))
    require(len(rows)==76,f'Matrix 02 rows={len(rows)}; expected 76')
    results=[]
    for row in rows:
        rid=row['Requirement ID']; exp_lesson=expected_lesson(rid); pids=expected_pids(row)
        missing=[p for p in pids if p not in idx]
        lessons=sorted({idx[p][0] for p in pids if p in idx})
        lesson_ok=(not missing and lessons==[exp_lesson])
        vis_ok=(not missing and all(visible_ok(rid,idx[p][1]) for p in pids))
        result='PASS' if not missing and lesson_ok and vis_ok else 'FAIL'
        results.append({
          'Requirement ID':rid,'Organism':row['Organism'],'Expected Candidate(s)':';'.join(pids),
          'Expected Lesson':exp_lesson,'Observed Lesson(s)':';'.join(lessons),
          'Presence':'PASS' if not missing else 'FAIL: '+','.join(missing),
          'Lesson Embedding':'PASS' if lesson_ok else 'FAIL',
          'Visible Instructional Treatment':'PASS' if vis_ok else 'FAIL','Regression':result})
    # Superseded/rejected definitions that must not remain active.
    forbidden_pids=['earthworm-external','earthworm-digestive','earthworm-vascular','earthworm-excretory','earthworm-reproductive',
      'penaeus-external','penaeus-digestive','penaeus-reproductive','obelia-colony','obelia-medusa','obelia-life-cycle',
      'nereis-external','nereis-parapodium','NEREIS-06-N1','pila-external','pila-pallial','pila-digestive','pila-nervous','pila-reproductive',
      'asterias-digestive','asterias-reproductive','master-fasciola-reproductive']
    forbidden_titles=['Earthworm — major internal systems (schematic L.S.)','Sycon — syconoid canal system and water current',
      'Fasciola hepatica — hermaphrodite reproductive system</strong><span class="ta-explain" lang="ta">ஃபாசியோலா ஹெபாட்டிகா — இருபாலின இனப்பெருக்க அமைப்பு',
      'Ascaris lumbricoides — male and female reproductive systems']
    surviving=[p for p in forbidden_pids if p in idx]
    require(not surviving,'superseded data-v188-plate IDs survived: '+','.join(surviving))
    for title in forbidden_titles: require(title not in text,'superseded legacy figure survived: '+title[:80])
    # Combined decoded source-definition integrity.
    markups=[m for _,m in asgn]; ids=[i for m in markups for i in ID_RE.findall(m)]
    dup=sum(v-1 for v in Counter(ids).values() if v>1); idset=set(ids)
    refs=[x for m in markups for x in REF_RE.findall(m)]; broken=sorted({x for x in refs if x not in idset})
    require(dup==0,f'duplicate source-definition IDs={dup}')
    require(not broken,'broken internal refs='+repr(broken))
    failed=[r for r in results if r['Regression']!='PASS']
    out=REPO/'provenance/V188_PIPELINE_RECONSTRUCTION_MATRIX_01.csv'
    with out.open('w',encoding='utf-8',newline='') as f:
        w=csv.DictWriter(f,fieldnames=list(results[0])); w.writeheader(); w.writerows(results)
    print('V188_PIPELINE_REGRESSION_ROWS='+str(len(results)))
    print('V188_PIPELINE_REGRESSION_FAILS='+str(len(failed)))
    print('V188_GENERATED_SOURCE_DUPLICATE_IDS='+str(dup))
    print('V188_GENERATED_SOURCE_BROKEN_INTERNAL_REFS='+str(len(broken)))
    print('V188_76_ROW_RECONSTRUCTION_REGRESSION='+('PASS' if not failed else 'FAIL'))
    if failed:
        for x in failed: print('FAIL_ROW='+x['Requirement ID']+': '+x['Presence']+'; lesson='+x['Lesson Embedding']+'; visible='+x['Visible Instructional Treatment'])
        raise SystemExit(2)

if __name__=='__main__':
    if len(sys.argv)!=2: raise SystemExit('usage: v188_pipeline_regression_audit.py <reconstructed-source-root>')
    main(sys.argv[1])
