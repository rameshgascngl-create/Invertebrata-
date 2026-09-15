#!/usr/bin/env python3
from pathlib import Path
import csv,sys

# Apply after reconcile_v188_svg_batch2.py. This deliberately prevents Batch 2
# source creation from being misreported as full illustration-gate completion.
# It records unresolved Tamil anatomical-label localisation and the mapped taxa
# not yet created. Source/static audit != physical-device QA.

def main(root):
    root=Path(root).resolve()
    mf=root/'provenance/V188_SVG_AUDIT_MANIFEST.csv'
    rows=list(csv.DictReader(mf.open(encoding='utf-8')))
    fields=['Organism','Lesson ID','Figure/System','PDF Reference','SVG Identifier/File','Embedded Location','Embedded','Biological Audit','Leader-Line Audit','English Labels','Tamil Labels','360px Audit','Enlarged-View Source Audit','Status','Notes']
    # Batch-2 figures contain Tamil academic captions, but the anatomical callouts
    # are still English. Do not call Tamil label treatment complete.
    for r in rows:
        if r['SVG Identifier/File'].startswith('batch2#'):
            r['Tamil Labels']='TERMINOLOGY REVIEW PENDING'
            if r['Status']=='PASS': r['Status']='PENDING'
            r['Notes']='Production SVG created and embedded. Biological/leader-line source audit recorded separately; English labels present. Tamil anatomical-label localisation still requires terminology review. Physical-device QA not performed.'
    # Congested Batch-1 Earthworm overview is not accepted as the primary teaching plate.
    for r in rows:
        if r['SVG Identifier/File']=='batch1#EARTHWORM':
            r['360px Audit']='FAIL-congestion'; r['Enlarged-View Source Audit']='PENDING'; r['Status']='FAIL'
            r['Notes']='Composite retained only as an overview. Six system-specific Batch-2 plates supersede it for primary teaching because combined digestive/vascular/nervous/excretory relationships are too congested for acceptance at phone reading width.'
        if r['SVG Identifier/File']=='batch1#PENAEUS':
            r['Status']='PENDING'; r['Biological Audit']='PENDING'; r['Leader-Line Audit']='PENDING'; r['360px Audit']='PENDING'; r['Enlarged-View Source Audit']='PENDING'
            r['Notes']='Overview retained, but appendage identity/serial order is judged from the dedicated Batch-2 appendage plate and remains subject to final reference resolution/device QA.'
        if r['SVG Identifier/File']=='batch1#PILA':
            r['Status']='PENDING'; r['360px Audit']='PENDING'; r['Enlarged-View Source Audit']='PENDING'
            r['Notes']='Overview supplemented by system-specific plates; not independently accepted as a completed mobile teaching plate.'
    missing=[
      ('Amoeba','u1-protozoa','External morphology / pseudopodia','p5'),
      ('Paramecium','u1-paramecium','External morphology + oral apparatus','p6'),
      ('Paramecium','u1-paramecium','Contractile vacuole / ciliary organisation','p6'),
      ('Entamoeba histolytica','u1-proto-parasites','Trophozoite / cyst morphology','p8'),
      ('Trypanosoma','u1-proto-parasites','Flagellate morphology','p8'),
      ('Leishmania','u1-proto-parasites','Promastigote / amastigote forms','p8'),
      ('Plasmodium','u1-host-parasite','Life-cycle organisation','p7, p11'),
      ('Porifera','u2-porifera','Asconoid / syconoid / leuconoid comparison','p14'),
      ('Aurelia','u2-cnidaria','Medusa morphology','p16'),
      ('Physalia','u2-cnidaria','Colonial polymorphism','p16'),
      ('Coral','u2-coral-economics','Coral polyp / reef organisation','p18'),
      ('Taenia solium','u3-platy','Scolex / strobila morphology','p21'),
      ('Taenia solium','u3-platy-adapt','Life-cycle organisation','p23–24'),
      ('Nematode parasites','u3-nematode-adapt','Representative parasite/life-cycle comparison','p27–28'),
      ('Peripatus','u4-modes-life','External morphology / affinities features','p36'),
      ('Crustacean larvae','u4-penaeus','Larval-form comparison','p37'),
      ('Arthropod pest/IPM','u4-arthropoda','Agricultural pest / IPM process visual','p38–40'),
      ('Mollusca','u5-mollusca','Generalised molluscan body plan','p41'),
      ('Gastropoda','u5-pila','Torsion process','p43'),
      ('Cephalopoda','u5-cephalopods','Representative morphology / economic relevance','p44'),
      ('Echinoderm larvae','u5-echinodermata','Larval-form comparison','p47')]
    existing={(r['Organism'],r['Lesson ID'],r['Figure/System']) for r in rows}
    for org,lesson,fig,pdf in missing:
        if (org,lesson,fig) not in existing:
            rows.append(dict(zip(fields,[org,lesson,fig,pdf,'NOT YET CREATED',lesson,'NO','PENDING','PENDING','PENDING','TERMINOLOGY REVIEW PENDING','PENDING','PENDING','NOT YET CREATED','Mapped requirement retained for a later batch; no production SVG exists yet.'])))
    with mf.open('w',encoding='utf-8',newline='') as f:
        w=csv.DictWriter(f,fieldnames=fields); w.writeheader(); w.writerows(rows)
    from collections import defaultdict
    d=defaultdict(lambda:[0,0,0,0,0])
    for r in rows:
        a=d[r['Organism']]; a[0]+=1
        a[1]+=r['SVG Identifier/File']!='NOT YET CREATED'
        a[2]+=r['Embedded']=='YES'
        a[3]+=r['Status']=='PASS'
        a[4]+=r['Status']!='PASS'
    cm=root/'provenance/V188_SVG_COMPLETENESS_MATRIX.csv'
    with cm.open('w',encoding='utf-8',newline='') as f:
        w=csv.writer(f); w.writerow(['Organism','Required figures','Created','Embedded','Passed','Pending/Failed'])
        for org,v in sorted(d.items()): w.writerow([org]+v)
    print('V188_MANIFEST_ROWS='+str(len(rows)))
    print('V188_CREATED='+str(sum(r['SVG Identifier/File']!='NOT YET CREATED' for r in rows)))
    print('V188_EMBEDDED='+str(sum(r['Embedded']=='YES' for r in rows)))
    print('V188_PASS='+str(sum(r['Status']=='PASS' for r in rows)))
    print('V188_FAIL='+str(sum(r['Status']=='FAIL' for r in rows)))
    print('V188_PENDING_OR_NOT_CREATED='+str(sum(r['Status'] not in ('PASS','FAIL') for r in rows)))
    print('V188_BUILD_ELIGIBLE=NO')

if __name__=='__main__': main(sys.argv[1])
