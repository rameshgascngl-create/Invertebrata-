#!/usr/bin/env python3
# Formal audit overlay 01. Run AFTER all historical batch/audit scripts and
# AFTER v188_audit_phase1_reconcile.py. It cannot create SVGs or build APKs.
from pathlib import Path
import csv,sys

# Match by organism + figure-system substrings because historical generated
# manifest identifiers vary across batches. Corrections are deliberately fail-closed.
RULES=[
 ('Sycon','canal',{'Biological Audit':'PASS','Tamil Labels':'TERMINOLOGY REVIEW PENDING','360px Audit':'FAIL','Status':'PENDING','Notes':'Biological topology PASS only. 360px source specimen shows the in-SVG flow sentence below readable instructional size; leader endpoints and Tamil terminology remain open.'}),
 ('Fasciola hepatica','Reproductive',{'Biological Audit':'FAIL','Leader-Line Audit':'PENDING','Tamil Labels':'TERMINOLOGY REVIEW PENDING','Status':'FAIL','Notes':'Reproductive master is incomplete for the p21 lesson/reference: male-duct and vitelline/ootype/common-genital connections are not independently resolved. Targeted correction required.'}),
 ('Earthworm','major internal',{'360px Audit':'FAIL','Status':'FAIL','Notes':'Established multi-system congestion failure. Composite remains overview only; never downgrade this FAIL to PENDING.'}),
 ('Penaeus','Representative biramous',{'Biological Audit':'PASS','Leader-Line Audit':'PASS','English Labels':'PASS','Tamil Labels':'TERMINOLOGY REVIEW PENDING','Status':'PENDING','Notes':'Generalised biramous topology only; cannot satisfy specialised appendage identity/serial-order/attachment requirement.'}),
 ('Asterias','Water-vascular',{'Biological Audit':'PASS','Tamil Labels':'TERMINOLOGY REVIEW PENDING','Status':'PENDING','Notes':'Core WVS topology supported by corrected p44. Leader/360px/Tamil gates must independently pass; p45 is unavailable and is not visible evidence.'}),
]

def main(root):
 root=Path(root).resolve(); mf=root/'provenance/V188_SVG_AUDIT_MANIFEST.csv'
 rows=list(csv.DictReader(mf.open(encoding='utf-8'))); fields=list(rows[0])
 # Corrected page map must already have been frozen by Phase 1. Refuse historical regression.
 bad=[]
 for r in rows:
  if r.get('Organism')=='Fasciola hepatica' and r.get('PDF Reference')!='p21': bad.append(('Fasciola',r.get('PDF Reference')))
  if r.get('Organism')=='Ascaris lumbricoides' and r.get('PDF Reference')!='p25': bad.append(('Ascaris',r.get('PDF Reference')))
  if r.get('Organism')=='Earthworm' and r.get('PDF Reference')!='p30': bad.append(('Earthworm',r.get('PDF Reference')))
  if r.get('Organism')=='Penaeus' and r.get('PDF Reference')!='p34': bad.append(('Penaeus',r.get('PDF Reference')))
  if r.get('Organism')=='Pila globosa' and r.get('PDF Reference')!='p41': bad.append(('Pila',r.get('PDF Reference')))
 if bad: raise SystemExit('CORRECTED PAGE MAP REGRESSION: '+repr(bad[:8]))
 for org,needle,updates in RULES:
  for r in rows:
   if r.get('Organism')==org and needle.lower() in r.get('Figure/System','').lower(): r.update(updates)
 # Independent requirements are hard visible blockers.
 required=[('Paramecium','u1-paramecium','Contractile vacuole / ciliary organisation','p5'),('Penaeus','u4-penaeus','Specialised appendage series / identity and attachment positions','p34')]
 keys={(r.get('Organism'),r.get('Lesson ID'),r.get('Figure/System')) for r in rows}
 for org,lesson,fig,pdf in required:
  if (org,lesson,fig) not in keys: raise SystemExit('MISSING INDEPENDENT MANIFEST ROW: '+org+' / '+fig)
  for r in rows:
   if (r.get('Organism'),r.get('Lesson ID'),r.get('Figure/System'))==(org,lesson,fig):
    r['PDF Reference']=pdf; r['SVG Identifier/File']='NOT YET CREATED'; r['Embedded']='NO'; r['Biological Audit']='PENDING'; r['Leader-Line Audit']='PENDING'; r['English Labels']='PENDING'; r['Tamil Labels']='TERMINOLOGY REVIEW PENDING'; r['360px Audit']='PENDING'; r['Enlarged-View Source Audit']='PENDING'; r['Status']='NOT YET CREATED'
 with mf.open('w',encoding='utf-8',newline='') as f:
  w=csv.DictWriter(f,fieldnames=fields); w.writeheader(); w.writerows(rows)
 # Recompute matrix from actual rows. Master style variants are not extra requirements.
 from collections import defaultdict
 d=defaultdict(lambda:[0,0,0,0,0,0])
 for r in rows:
  if str(r.get('SVG Identifier/File','')).startswith('master-'): continue
  a=d[r['Organism']]; a[0]+=1; a[1]+=r.get('SVG Identifier/File')!='NOT YET CREATED'; a[2]+=r.get('Embedded')=='YES'; a[3]+=r.get('Status')=='PASS'; a[4]+=r.get('Status')=='FAIL'; a[5]+=r.get('Status') not in ('PASS','FAIL')
 cm=root/'provenance/V188_SVG_COMPLETENESS_MATRIX.csv'
 with cm.open('w',encoding='utf-8',newline='') as f:
  w=csv.writer(f); w.writerow(['Organism','Required figures','Created','Embedded','Passed','Failed','Pending/Not-created']);
  for org,v in sorted(d.items()): w.writerow([org]+v)
 print('V188_REQUIRED_ROWS='+str(sum(v[0] for v in d.values())))
 print('V188_PASS='+str(sum(v[3] for v in d.values())))
 print('V188_FAIL='+str(sum(v[4] for v in d.values())))
 print('V188_PENDING_OR_NOT_CREATED='+str(sum(v[5] for v in d.values())))
 print('V188_BUILD_ELIGIBLE=NO')
if __name__=='__main__': main(sys.argv[1])
