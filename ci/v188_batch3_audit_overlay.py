#!/usr/bin/env python3
# Batch 3 audit overlay. Must run after Batch 2 audit/provenance scripts.
# It updates the existing required rows; it does not invent new requirement counts.
from pathlib import Path
import csv,sys

MAP={
('Amoeba','External morphology / pseudopodia'):('batch3#amoeba','Pencil SVG','Invertebrata draft.pdf p4–5'),
('Paramecium','External morphology + oral apparatus'):('batch3#paramecium','Pencil SVG','Invertebrata draft.pdf p5'),
('Entamoeba histolytica','Trophozoite / cyst morphology'):('batch3#entamoeba','Pencil SVG','Invertebrata draft.pdf p7'),
('Trypanosoma','Flagellate morphology'):('batch3#trypanosoma','Pencil SVG','Invertebrata draft.pdf p7'),
('Leishmania','Promastigote / amastigote forms'):('batch3#leishmania','Pencil SVG','Invertebrata draft.pdf p7'),
('Plasmodium','Life-cycle organisation'):('batch3#plasmodium','Pencil SVG','Invertebrata draft.pdf p10'),
('Porifera','Asconoid / syconoid / leuconoid comparison'):('batch3#porifera_canals','Pencil SVG','Invertebrata draft.pdf p13'),
('Aurelia','Medusa morphology'):('batch3#aurelia','Pencil SVG','Invertebrata draft.pdf p15'),
('Physalia','Colonial polymorphism'):('batch3#physalia','Pencil SVG','Invertebrata draft.pdf p15'),
('Coral','Coral polyp / reef organisation'):('batch3#coral','Pencil SVG','Invertebrata draft.pdf p17'),
('Taenia solium','Scolex / strobila morphology'):('batch3#taenia','Pencil SVG','Invertebrata draft.pdf p20'),
('Taenia solium','Life-cycle organisation'):('batch3#taenia_cycle','Pencil SVG','Invertebrata draft.pdf p22–23'),
('Nematode parasites','Representative parasite/life-cycle comparison'):('batch3#nematodes','Pencil SVG','Invertebrata draft.pdf p26–27'),
('Peripatus','External morphology / affinities features'):('batch3#peripatus','Pencil SVG','Invertebrata draft.pdf p35'),
('Crustacean larvae','Larval-form comparison'):('batch3#crustacean_larvae','Pencil SVG','Invertebrata draft.pdf p36'),
('Arthropod pest/IPM','Agricultural pest / IPM process visual'):('batch3#pest_ipm','Pencil SVG','Invertebrata draft.pdf p38–39'),
('Mollusca','Generalised molluscan body plan'):('batch3#mollusc_plan','Pencil SVG','Invertebrata draft.pdf p40'),
('Gastropoda','Torsion process'):('batch3#torsion','Pencil SVG','Invertebrata draft.pdf p42'),
('Cephalopoda','Representative morphology / economic relevance'):('batch3#cephalopod','Pencil SVG','Invertebrata draft.pdf p43'),
('Echinoderm larvae','Larval-form comparison'):('batch3#echino_larvae','Pencil SVG','Invertebrata draft.pdf p46')}

def main(root):
 root=Path(root).resolve(); mf=root/'provenance/V188_SVG_AUDIT_MANIFEST.csv'
 rows=list(csv.DictReader(mf.open(encoding='utf-8'))); fields=list(rows[0].keys())
 for f in ['Visual Type','Reference Source/Page','Redrawn/Reconstructed']:
  if f not in fields: fields.append(f)
 changed=0
 for r in rows:
  k=(r.get('Organism',''),r.get('Figure/System',''))
  if k in MAP:
   ident,vt,ref=MAP[k]; r['SVG Identifier/File']=ident; r['Embedded']='YES'; r['Embedded Location']=r.get('Lesson ID',''); r['Visual Type']=vt; r['Reference Source/Page']=ref; r['Redrawn/Reconstructed']='YES'
   r['Biological Audit']='PENDING'; r['Leader-Line Audit']='PENDING'; r['English Labels']='PASS'; r['Tamil Labels']='TERMINOLOGY REVIEW PENDING'; r['360px Audit']='PENDING'; r['Enlarged-View Source Audit']='PENDING'; r['Status']='PENDING'; r['Notes']='Production pencil SVG created and contextually embedded in Batch 3. Requires per-figure biological/reference, leader-line, Tamil terminology and source-render audits before PASS.'; changed+=1
 # Paramecium second requirement remains explicit: one external plate does not satisfy ciliary/contractile-vacuole organisation.
 with mf.open('w',encoding='utf-8',newline='') as f:
  w=csv.DictWriter(f,fieldnames=fields); w.writeheader(); w.writerows(rows)
 from collections import defaultdict
 d=defaultdict(lambda:[0,0,0,0,0])
 for r in rows:
  if str(r.get('SVG Identifier/File','')).startswith('master-'): continue
  a=d[r['Organism']]; a[0]+=1; created=r['SVG Identifier/File']!='NOT YET CREATED'; a[1]+=created; a[2]+=r['Embedded']=='YES'; a[3]+=r['Status']=='PASS'; a[4]+=r['Status']!='PASS'
 cm=root/'provenance/V188_SVG_COMPLETENESS_MATRIX.csv'
 with cm.open('w',encoding='utf-8',newline='') as f:
  w=csv.writer(f); w.writerow(['Organism','Required figures','Created','Embedded','Passed','Pending/Failed']);
  for org,v in sorted(d.items()): w.writerow([org]+v)
 print('V188_BATCH3_REQUIRED_ROWS_UPDATED='+str(changed))
 print('V188_REQUIRED_ROWS='+str(sum(v[0] for v in d.values())))
 print('V188_CREATED='+str(sum(v[1] for v in d.values())))
 print('V188_EMBEDDED='+str(sum(v[2] for v in d.values())))
 print('V188_PASS='+str(sum(v[3] for v in d.values())))
 print('V188_PENDING_OR_FAILED='+str(sum(v[4] for v in d.values())))
 print('V188_BUILD_ELIGIBLE=NO')
if __name__=='__main__': main(sys.argv[1])
