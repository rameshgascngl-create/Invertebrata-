#!/usr/bin/env python3
# v1.8.8 AUDIT MODE — Phase 1 only. No SVG production and no APK build.
# Corrected PDF page references below are authoritative and supersede historical
# approximate/+1 references in earlier Batch-2 scripts.
from pathlib import Path
import csv,sys

PAGE={
'Amoeba':'p4–5','Paramecium':'p5','Entamoeba histolytica':'p7','Trypanosoma':'p7','Leishmania':'p7','Plasmodium':'p10',
'Porifera':'p11–14','Sycon':'p12–14','Obelia':'p16','Aurelia':'p15','Physalia':'p15','Coral':'p17–18',
'Fasciola hepatica':'p21','Taenia solium':'p20–23','Ascaris lumbricoides':'p25','Nematode parasites':'p26–27',
'Nereis':'p29','Earthworm':'p30','Penaeus':'p34','Peripatus':'p35','Crustacean larvae':'p36','Arthropod pest/IPM':'p38–39',
'Mollusca':'p40','Pila globosa':'p41','Gastropoda':'p42','Cephalopoda':'p43','Asterias':'p44–45','Echinoderm larvae':'p46'}
# Explicit independent requirements that must not be hidden by organism-level completion.
REQUIRED=[
('Paramecium','u1-paramecium','Contractile vacuole / ciliary organisation','p5'),
('Penaeus','u4-penaeus','Specialised appendage series / identity and attachment positions','p34')]

def main(root):
 root=Path(root).resolve(); mf=root/'provenance/V188_SVG_AUDIT_MANIFEST.csv'
 rows=list(csv.DictReader(mf.open(encoding='utf-8'))); fields=list(rows[0].keys())
 for f in ['Visual Type','Reference Source/Page','Redrawn/Reconstructed']:
  if f not in fields: fields.append(f)
 # Freeze corrected reference numbering for every known taxon.
 for r in rows:
  if r.get('Organism') in PAGE:
   r['PDF Reference']=PAGE[r['Organism']]
   r['Reference Source/Page']='Invertebrata draft.pdf '+PAGE[r['Organism']]
 existing={(r.get('Organism'),r.get('Lesson ID'),r.get('Figure/System')) for r in rows}
 for org,lesson,fig,pdf in REQUIRED:
  if (org,lesson,fig) not in existing:
   r={f:'' for f in fields}; r.update({'Organism':org,'Lesson ID':lesson,'Figure/System':fig,'PDF Reference':pdf,'SVG Identifier/File':'NOT YET CREATED','Embedded Location':lesson,'Embedded':'NO','Biological Audit':'PENDING','Leader-Line Audit':'PENDING','English Labels':'PENDING','Tamil Labels':'TERMINOLOGY REVIEW PENDING','360px Audit':'PENDING','Enlarged-View Source Audit':'PENDING','Status':'NOT YET CREATED','Notes':'Independent figure requirement identified during audit reconciliation; must not be satisfied by a generic overview plate.','Visual Type':'Pencil SVG required','Reference Source/Page':'Invertebrata draft.pdf '+pdf,'Redrawn/Reconstructed':'NO'}); rows.append(r)
 # Historical Penaeus generalised appendage overview cannot satisfy specialised series requirement.
 for r in rows:
  if r.get('Organism')=='Penaeus' and 'appendage' in r.get('Figure/System','').lower():
   if r.get('Figure/System')!='Specialised appendage series / identity and attachment positions':
    r['Status']='PENDING'; r['Biological Audit']='PENDING'; r['Leader-Line Audit']='PENDING'; r['Notes']='Generalised/overview appendage material only. It does not satisfy the independent specialised appendage identity, serial order and attachment-position requirement.'
 with mf.open('w',encoding='utf-8',newline='') as f:
  w=csv.DictWriter(f,fieldnames=fields); w.writeheader(); w.writerows(rows)
 print('AUDIT_MODE=YES'); print('CORRECTED_PAGE_MAP_FROZEN=YES'); print('MANIFEST_ROWS='+str(len(rows))); print('BUILD_ELIGIBLE=NO')
if __name__=='__main__': main(sys.argv[1])
