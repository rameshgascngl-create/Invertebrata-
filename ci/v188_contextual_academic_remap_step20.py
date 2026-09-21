#!/usr/bin/env python3
from __future__ import annotations
import hashlib,json,re,sys
from collections import Counter
from pathlib import Path

IN_SHA='9d4a0e5b6bd23e8fd6cd7e148100bb6017374d2cd9dea45098007eff0c899f65'
IN_SIZE=2389584
ATLAS_SHA='005c2e130b35454fd56b7b5b3ce6295a51adc33dea7b258c312f6f312b4597d2'
PAYLOADS=(Path('academic_payload/index.html'),Path('app/src/main/assets/www/index.html'))
SCRIPT_ID='v188-contextual-academic-remap-step20'
RULES=(
 ('peripatus','u4-modes-life','u4-peripatus','Tubicolous','Annelid-like','N',None),
 ('penaeus-nervous','u4-penaeus','u4-penaeus','Appendage roster worth memorising',None,'N',None),
 ('penaeus-reproductive','u4-penaeus','u4-penaeus','Appendage roster worth memorising',None,'N',None),
 ('PENAEUS-08-N1','u4-penaeus','u4-penaeus','Names',None,'N',None),
 ('PENAEUS-09-R1','u4-penaeus','u4-penaeus','Appendage roster worth memorising',None,'N',None),
 ('PILA-NER-R1','u5-pila','u5-pila','Reproduction',None,'N',None),
 ('asterias-oral','u5-asterias','u5-asterias','Reproduction and repair',None,'C','External'),
 ('asterias-aboral','u5-asterias','u5-asterias','Reproduction and repair',None,'C','External'),
 ('cephalopod','u5-cephalopods','u5-cephalopods','Circulatory efficiency',None,'N',None),
)
ASSIGN=re.compile(r'window\.ORG_SYSTEM_DIAGRAMS\[(?P<lesson>"(?:\\.|[^"\\])*")\]\s*=\s*\(window\.ORG_SYSTEM_DIAGRAMS\[(?P=lesson)\]\s*\|\|\s*(?:\'\'|"")\s*\)\s*\+\s*(?P<payload>"(?:\\.|[^"\\])*")\s*;',re.S)
PLATE=re.compile(r'data-v188-plate="([^"]+)"')

def req(x,m):
 if not x: raise SystemExit(m)
def sha(b): return hashlib.sha256(b).hexdigest()
def atlas(s):
 k='<script id="v188-contextual-atlas">';req(s.count(k)==1,'contextual-atlas cardinality mismatch')
 a=s.index(k);b=s.index('</script>',a)+9;return s[a:b]
def assignments(s):
 out=[]
 for m in ASSIGN.finditer(s): out.append((m,json.loads(m.group('lesson')),json.loads(m.group('payload'))))
 req(out,'no ORG_SYSTEM_DIAGRAMS assignments');return out
def owners(s):
 c=Counter();o={}
 for _,lesson,p in assignments(s):
  for fid in PLATE.findall(p): c[fid]+=1;o.setdefault(fid,lesson)
 return c,o

def remap_script():
 rr=[dict(id=i,lesson=to,expected=(post or old),mode=mode,heading=head) for i,frm,to,old,post,mode,head in RULES]
 data=json.dumps(rr,separators=(',',':'),ensure_ascii=False)
 return f'''<script id="{SCRIPT_ID}">
(function(){{'use strict';
var rules={data},byId=new Map(rules.map(r=>[r.id,r])),allowed=new Set(rules.map(r=>r.id));
var applied=new Set(),events=[],fatal=null,mount=document.getElementById('invTheoryMount');if(!mount)return;
function fail(m){{fatal=String(m);window.__v188Step20AcademicRemap={{rules,applied,events,fatal}};throw new Error('STEP-20 '+fatal)}}
function txt(x){{return String(x||'').replace(/\\s+/g,' ').trim()}}
function head(s){{var h=s&&s.querySelector('h4,h3,h5');return txt(h&&h.innerText)}}
function visible(n){{return !!n&&!n.closest('details:not([open])')}}
function prevSec(f){{var n=f.previousElementSibling;while(n&&!(n.classList&&n.classList.contains('textbook-subsection')))n=n.previousElementSibling;return n||null}}
function chapter(){{return history.state?.view?.chapter||window.__invLastStudyView?.view?.chapter||null}}
function targetPane(p){{return Array.from(p.querySelectorAll('figure[data-v188-plate]')).filter(f=>allowed.has(f.dataset.v188Plate))}}
function exactHeading(p,h){{var a=Array.from(p.querySelectorAll('.textbook-detail .textbook-subsection')).filter(visible).filter(s=>head(s)===h);if(a.length!==1)fail('DESTINATION HEADING CARDINALITY MISMATCH '+h+' count='+a.length);return a[0]}}
function apply(p){{
 if(p.dataset.v188Step20Applied==='1')return;var t=targetPane(p);if(!t.length)return;
 var visuals=p.querySelector('.textbook-visuals');if(!visuals||visuals.dataset.v188Done!=='1')return;
 var ch=chapter(),prep=[];
 t.forEach(f=>{{var id=f.dataset.v188Plate,r=byId.get(id);if(!r)fail('UNAUTHORIZED TARGET '+id);if(ch!==r.lesson)fail('LESSON OWNERSHIP MISMATCH '+id+' expected='+r.lesson+' actual='+ch);if(!f.classList.contains('contextual-theory-figure'))fail('GENERIC STEP-2 NOT COMPLETE '+id);if(!visible(f))fail('TARGET NOT DEFAULT VISIBLE '+id);var s=prevSec(f),actual=s?head(s):(f.closest('.textbook-visuals')?'NEUTRAL_ORIGINAL_CONTAINER':null);if(actual!==r.expected)fail('PRECONDITION DESTINATION MISMATCH '+id+' expected='+r.expected+' actual='+actual);prep.push({{f,r}})}});
 var fixed=new Map();prep.forEach(x=>{{if(x.r.mode==='C'){{if(x.r.heading!=='External')fail('UNAUTHORIZED CONTEXTUAL HEADING '+x.r.id);if(!fixed.has('External'))fixed.set('External',exactHeading(p,'External'))}}else if(x.r.mode!=='N')fail('UNSUPPORTED MODE '+x.r.id)}});
 prep.filter(x=>x.r.mode==='N').forEach(x=>{{if(!visible(visuals))fail('NEUTRAL ORIGINAL CONTAINER HIDDEN '+x.r.id);visuals.hidden=false;if(x.f.parentElement!==visuals)visuals.appendChild(x.f);applied.add(x.r.id);events.push({{id:x.r.id,lesson:x.r.lesson,result:'NEUTRAL_ORIGINAL_CONTAINER'}})}});
 fixed.forEach((anchor,h)=>{{prep.filter(x=>x.r.mode==='C').sort((a,b)=>a.r.id==='asterias-oral'?-1:(b.r.id==='asterias-oral'?1:0)).forEach(x=>{{anchor.insertAdjacentElement('afterend',x.f);anchor=x.f;applied.add(x.r.id);events.push({{id:x.r.id,lesson:x.r.lesson,result:'CONTEXTUAL_VISIBLE_MATCH',heading:h}})}})}});
 p.dataset.v188Step20Applied='1';window.__v188Step20AcademicRemap={{rules,applied,events,fatal}};
}}
function run(){{Array.from(mount.querySelectorAll('.theory-pane')).forEach(apply)}}var q=false;function queue(){{if(q)return;q=true;requestAnimationFrame(()=>requestAnimationFrame(()=>{{q=false;run()}}))}}new MutationObserver(queue).observe(mount,{{childList:true,subtree:true}});window.__v188Step20AcademicRemap={{rules,applied,events,fatal}};queue();
}})();
</script>'''

def transform(s):
 raw=s.encode();req(len(raw)==IN_SIZE,f'STEP-20 INPUT SIZE MISMATCH — ABORT expected={IN_SIZE} actual={len(raw)}');req(sha(raw)==IN_SHA,f'STEP-20 INPUT HASH MISMATCH — ABORT expected={IN_SHA} actual={sha(raw)}')
 a0=atlas(s);req(sha(a0.encode())==ATLAS_SHA,'contextual-atlas input identity mismatch');req(f'id="{SCRIPT_ID}"' not in s,'Step-20 already present')
 c,o=owners(s);ids=[r[0] for r in RULES];req(len(ids)==len(set(ids))==9,'target set must be exactly nine unique IDs')
 for fid,frm,to,old,post,mode,head in RULES: req(c[fid]==1,f'target occurrence mismatch {fid}: {c[fid]}') or req(o.get(fid)==frm,f'pre-owner mismatch {fid}: {o.get(fid)}')
 req([r[0] for r in RULES if r[1]!=r[2]]==['peripatus'],'LESSON_OWNERSHIP_MIGRATIONS must be exactly peripatus')
 req({r[0] for r in RULES if r[5]=='C'}=={'asterias-oral','asterias-aboral'},'ASTERIAS_CONTEXTUAL_OVERRIDES must be exact two')
 req(sum(r[5]=='N' for r in RULES)==7,'NEUTRAL_OUTCOMES must equal seven')
 hits=[x for x in assignments(s) if 'data-v188-plate="peripatus"' in x[2]];req(len(hits)==1 and hits[0][1]=='u4-modes-life','peripatus source assignment precondition mismatch')
 m=hits[0][0];whole=m.group(0);new=whole.replace(m.group('lesson'),json.dumps('u4-peripatus'));req(new.count(json.dumps('u4-peripatus'))==2,'peripatus owner rewrite cardinality mismatch');changed=s[:m.start()]+new+s[m.end():]
 script=remap_script();req(changed.count('</body>')==1,'body terminator cardinality mismatch');out=changed.replace('</body>',script+'\n</body>',1);req(atlas(out)==a0,'Step-2 contextual-atlas changed')
 c2,o2=owners(out);req(c2==c,'figure inventory changed');req([f for f in o if o[f]!=o2.get(f)]==['peripatus'],'unexpected lesson ownership migration');req(o2['peripatus']=='u4-peripatus','peripatus post-owner mismatch')
 # Exact normalization proof: remove only Step-20 script and reverse only the Peripatus assignment key.
 norm=out.replace(script+'\n','',1);hh=[x for x in assignments(norm) if 'data-v188-plate="peripatus"' in x[2]];req(len(hh)==1 and hh[0][1]=='u4-peripatus','normalized peripatus assignment missing');mm=hh[0][0];ww=mm.group(0);rest=ww.replace(json.dumps('u4-peripatus'),json.dumps('u4-modes-life'));norm=norm[:mm.start()]+rest+norm[mm.end():];req(norm==s,'unauthorized Step-20 source drift')
 return out

def main():
 root=Path(sys.argv[1]).resolve() if len(sys.argv)>1 else Path.cwd();a,b=(root/PAYLOADS[0],root/PAYLOADS[1]);req(a.is_file() and b.is_file(),'payload copies missing');req(a.read_bytes()==b.read_bytes(),'payload copies diverged');req(sha(a.read_bytes())==IN_SHA,'STEP-20 INPUT HASH MISMATCH — ABORT')
 out=transform(a.read_text(encoding='utf-8'));a.write_text(out,encoding='utf-8',newline='\n');b.write_text(out,encoding='utf-8',newline='\n');req(a.read_bytes()==b.read_bytes(),'Step-20 output copies diverged');print('STEP20_INPUT_SHA256='+IN_SHA);print('STEP20_TARGET_IDS=9');print('STEP20_LESSON_OWNERSHIP_MIGRATIONS=1');print('STEP20_MIGRATED_ID=peripatus');print('STEP20_ASTERIAS_CONTEXTUAL_OVERRIDES=2');print('STEP20_NEUTRAL_OUTCOMES=7');print('STEP20_CONTEXTUAL_ATLAS_UNCHANGED=YES');print('STEP20_OUTPUT_SHA256='+sha(a.read_bytes()));print('STEP20_OUTPUT_SIZE='+str(a.stat().st_size))
if __name__=='__main__':main()
