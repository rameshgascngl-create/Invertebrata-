#!/usr/bin/env python3
from pathlib import Path
import csv,sys
from collections import defaultdict

# Apply after Batch-2 audit and pencil-atlas master integration.
# Extends the existing per-figure manifest; does not build the Android app.
MASTER={
 ('Sycon','u2-sycon','Syconoid canal system + water flow'):'master-sycon-canal',
 ('Fasciola hepatica','u3-fasciola','Reproductive system'):'master-fasciola-reproductive',
 ('Penaeus','u4-penaeus','Representative biramous appendage plan'):'master-penaeus-appendage',
 ('Asterias','u5-asterias','Water-vascular system + ampulla/tube-foot'):'master-asterias-wvs'
}

def main(root):
 root=Path(root).resolve(); mf=root/'provenance/V188_SVG_AUDIT_MANIFEST.csv'
 rows=list(csv.DictReader(mf.open(encoding='utf-8')))
 base=list(rows[0].keys()) if rows else []
 for f in ['Visual Type','Reference Source/Page','Redrawn/Reconstructed']:
  if f not in base: base.append(f)
 for r in rows:
  created=r.get('SVG Identifier/File')!='NOT YET CREATED'
  r['Visual Type']='Pencil SVG' if created else 'Other — production figure not yet created'
  r['Reference Source/Page']='Invertebrata draft PDF '+r.get('PDF Reference','')
  r['Redrawn/Reconstructed']='YES' if created else 'NO'
  # Existing Batch-1/2 plates are vector reconstructions. Master specimens below define
  # the final visual language; do not falsely mark all legacy plates as style-refined.
  if r.get('SVG Identifier/File','').startswith(('batch1#','batch2#')):
   r['Notes']=r.get('Notes','')+' Visual provenance recorded; master pencil-atlas treatment is not yet propagated to this plate.'
 # Add four master specimens as independent audit rows. They supplement rather than erase prior evidence.
 specs=[
  ['Sycon','u2-sycon','Syconoid canal system + water flow','p13–14','master-sycon-canal','u2-sycon'],
  ['Fasciola hepatica','u3-fasciola','Reproductive system','p22','master-fasciola-reproductive','u3-fasciola'],
  ['Penaeus','u4-penaeus','Representative biramous appendage plan','p35','master-penaeus-appendage','u4-penaeus'],
  ['Asterias','u5-asterias','Water-vascular system + ampulla/tube-foot','p45–46','master-asterias-wvs','u5-asterias']]
 for org,lesson,fig,pdf,sid,dest in specs:
  if not any(x.get('SVG Identifier/File')==sid for x in rows):
   x={k:'' for k in base}; x.update({'Organism':org,'Lesson ID':lesson,'Figure/System':fig,'PDF Reference':pdf,'SVG Identifier/File':sid,'Embedded Location':'ORG_SYSTEM_DIAGRAMS['+lesson+']','Embedded':'YES','Biological Audit':'PASS','Leader-Line Audit':'PASS','English Labels':'PASS','Tamil Labels':'TERMINOLOGY REVIEW PENDING','360px Audit':'PASS-source','Enlarged-View Source Audit':'PASS-source','Status':'PENDING','Notes':'Master pencil-atlas style specimen. Source/static audit only; physical-device QA not performed. Tamil anatomical legend remains terminology-review pending.','Visual Type':'Pencil SVG + numbered legend','Reference Source/Page':'Invertebrata draft PDF '+pdf,'Redrawn/Reconstructed':'YES'}); rows.append(x)
 with mf.open('w',encoding='utf-8',newline='') as f:
  w=csv.DictWriter(f,fieldnames=base); w.writeheader(); w.writerows(rows)
 d=defaultdict(lambda:[0,0,0,0,0])
 for r in rows:
  a=d[r['Organism']]; a[0]+=1; a[1]+=r['SVG Identifier/File']!='NOT YET CREATED'; a[2]+=r.get('Embedded')=='YES'; a[3]+=r.get('Status')=='PASS'; a[4]+=r.get('Status')!='PASS'
 cm=root/'provenance/V188_SVG_COMPLETENESS_MATRIX.csv'
 with cm.open('w',encoding='utf-8',newline='') as f:
  w=csv.writer(f); w.writerow(['Organism','Required figures','Created','Embedded','Passed','Pending/Failed']);
  for org,v in sorted(d.items()): w.writerow([org]+v)
 print('V188_PENCIL_MASTER_ROWS=4'); print('V188_VISUAL_PROVENANCE_FIELDS=3'); print('V188_BUILD_TRIGGERED=NO'); print('V188_BUILD_ELIGIBLE=NO')

if __name__=='__main__': main(sys.argv[1])
