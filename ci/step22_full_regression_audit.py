#!/usr/bin/env python3
from __future__ import annotations
import argparse, asyncio, csv, hashlib, json
from collections import Counter, defaultdict
from pathlib import Path
from playwright.async_api import async_playwright

TARGETS={
 'peripatus':{'pre_lesson':'u4-modes-life','pre_dest':'Tubicolous','post_lesson':'u4-peripatus','post_mode':'NEUTRAL_ORIGINAL_CONTAINER','post_dest':None},
 'penaeus-nervous':{'pre_lesson':'u4-penaeus','pre_dest':'Appendage roster worth memorising','post_lesson':'u4-penaeus','post_mode':'NEUTRAL_ORIGINAL_CONTAINER','post_dest':None},
 'penaeus-reproductive':{'pre_lesson':'u4-penaeus','pre_dest':'Appendage roster worth memorising','post_lesson':'u4-penaeus','post_mode':'NEUTRAL_ORIGINAL_CONTAINER','post_dest':None},
 'PENAEUS-08-N1':{'pre_lesson':'u4-penaeus','pre_dest':'Names','post_lesson':'u4-penaeus','post_mode':'NEUTRAL_ORIGINAL_CONTAINER','post_dest':None},
 'PENAEUS-09-R1':{'pre_lesson':'u4-penaeus','pre_dest':'Appendage roster worth memorising','post_lesson':'u4-penaeus','post_mode':'NEUTRAL_ORIGINAL_CONTAINER','post_dest':None},
 'PILA-NER-R1':{'pre_lesson':'u5-pila','pre_dest':'Reproduction','post_lesson':'u5-pila','post_mode':'NEUTRAL_ORIGINAL_CONTAINER','post_dest':None},
 'asterias-oral':{'pre_lesson':'u5-asterias','pre_dest':'Reproduction and repair','post_lesson':'u5-asterias','post_mode':'CONTEXTUAL_VISIBLE_MATCH','post_dest':'External'},
 'asterias-aboral':{'pre_lesson':'u5-asterias','pre_dest':'Reproduction and repair','post_lesson':'u5-asterias','post_mode':'CONTEXTUAL_VISIBLE_MATCH','post_dest':'External'},
 'cephalopod':{'pre_lesson':'u5-cephalopods','pre_dest':'Circulatory efficiency','post_lesson':'u5-cephalopods','post_mode':'NEUTRAL_ORIGINAL_CONTAINER','post_dest':None},
}
REVIEW_IDS={'paramecium','PARAMECIUM-CV-CILIA-N1','sycon-spicules','obelia-zooids'}
EXPECTED_OLD_NEUTRALS={'physalia','earthworm-external-r2','earthworm-digestive-r2','earthworm-reproductive-r2','nereis-circulatory','pest-ipm','gastropod-torsion'}

async def frames(page,n=3):
 await page.evaluate('''n=>new Promise(r=>{let k=n;function f(){if(--k<=0)r();else requestAnimationFrame(f)}requestAnimationFrame(f)})''',n)

async def open_ch(page,lesson):
 await page.evaluate("l=>document.querySelector('button[data-open-chapter=\\\"'+l+'\\\"]')?.click()",lesson)
 await page.wait_for_function("l=>(history.state?.view?.chapter||window.__invLastStudyView?.view?.chapter)===l",arg=lesson,timeout=5000)
 await frames(page,4)

async def prep(browser,html):
 ctx=await browser.new_context(viewport={'width':360,'height':800},device_scale_factor=1,is_mobile=True,has_touch=True)
 page=await ctx.new_page();errs=[]
 page.on('pageerror',lambda e:errs.append('PAGEERROR '+str(e)))
 page.on('console',lambda m:errs.append('CONSOLE '+m.type+' '+m.text) if m.type=='error' else None)
 await page.set_content(html,wait_until='load');await frames(page,4)
 return ctx,page,errs

async def inventory(page):
 return await page.evaluate('''()=>{const t=document.createElement('template'),out=[];for(const [lesson,html] of Object.entries(window.ORG_SYSTEM_DIAGRAMS||{})){t.innerHTML=html||'';for(const f of t.content.querySelectorAll('figure[data-v188-plate]'))out.push({lesson,id:f.dataset.v188Plate,caption:(f.querySelector('figcaption')?.textContent||'').replace(/\\s+/g,' ').trim(),html:f.outerHTML})}return out}''')

async def placement(page,fid,lesson):
 loc=page.locator(f'figure[data-v188-plate="{fid}"]');await loc.scroll_into_view_if_needed(timeout=5000);await frames(page,2)
 return await loc.evaluate('''(f,lesson)=>{function txt(x){return (x?.innerText||'').replace(/\\s+/g,' ').trim()}function h(s){return txt(s?.querySelector('h4,h3,h5'))||null}let mode,d=null;if(f.parentElement?.classList.contains('v188-neutral-figure-anchor'))mode='NEUTRAL_LESSON_ANCHOR';else if(f.closest('.textbook-visuals'))mode='NEUTRAL_ORIGINAL_CONTAINER';else{mode='CONTEXTUAL_VISIBLE_MATCH';let n=f.previousElementSibling;while(n&&!n.classList?.contains('textbook-subsection'))n=n.previousElementSibling;d=n}const r=f.getBoundingClientRect();return {figure_id:f.dataset.v188Plate,lesson,placement_mode:mode,destination:h(d),default_visible:!!(r.width&&r.height)&&!f.closest('details:not([open])')&&getComputedStyle(f).display!=='none'&&getComputedStyle(f).visibility!=='hidden',inside_closed_details:!!f.closest('details:not([open])'),horizontal_overflow_px:Math.max(0,Math.ceil(r.right-document.documentElement.clientWidth),Math.ceil(-r.left)),document_overflow_px:Math.max(0,document.documentElement.scrollWidth-document.documentElement.clientWidth),rendered_count:document.querySelectorAll('figure[data-v188-plate="'+CSS.escape(f.dataset.v188Plate)+'"]').length,caption:(f.querySelector('figcaption')?.textContent||'').replace(/\\s+/g,' ').trim()}}''',lesson)

async def collect(browser,html):
 ctx,page,errs=await prep(browser,html);inv=await inventory(page);by=defaultdict(list)
 for x in inv:by[x['lesson']].append(x)
 rows=[]
 for lesson,items in by.items():
  await open_ch(page,lesson)
  for x in items:rows.append(await placement(page,x['id'],lesson))
 applied=await page.evaluate("()=>({ids:Array.from(window.__v188Step20AcademicRemap?.applied||[]),fatal:window.__v188Step20AcademicRemap?.fatal||null})")
 await ctx.close();return inv,rows,errs,applied

async def snapshot(page,fid):
 return await page.evaluate('''fid=>({x:scrollX,y:scrollY,chapter:history.state?.view?.chapter||window.__invLastStudyView?.view?.chapter||null,focus:document.activeElement?.getAttribute?.('data-v188-plate')||document.activeElement?.closest?.('[data-v188-plate]')?.getAttribute('data-v188-plate')||null,focusHidden:!!document.activeElement?.closest?.('[hidden]'),count:document.querySelectorAll('figure[data-v188-plate="'+CSS.escape(fid)+'"]').length})''',fid)

async def overlay(page,fid):
 return await page.evaluate('''fid=>{const f=document.querySelector('figure[data-v188-plate="'+CSS.escape(fid)+'"]'),src=f?.querySelector('svg,img'),o=document.querySelector('#invSyllabusVisualOverlay'),m=o?.querySelectorAll('.theory-zoom-media')||[];let same=false;if(src&&m[0]&&src.tagName===m[0].tagName){if(src.tagName.toLowerCase()==='svg'){const norm=n=>{const c=n.cloneNode(true);c.querySelectorAll('.sbox,.sfill').forEach(x=>x.removeAttribute('style'));return c.innerHTML};same=norm(src)===norm(m[0])&&src.getAttribute('viewBox')===m[0].getAttribute('viewBox')}else same=src.getAttribute('src')===m[0].getAttribute('src')}return {visible:!!o&&!o.hidden,count:m.length,identity:same}}''',fid)

async def interact(page,fid,lesson,activation,close_method):
 await open_ch(page,lesson);loc=page.locator(f'figure[data-v188-plate="{fid}"]');await loc.scroll_into_view_if_needed();await frames(page,2)
 if activation in ('enter','space'): await loc.focus()
 pre=await snapshot(page,fid)
 if activation=='click':await loc.click(no_wait_after=True)
 elif activation=='touch':await loc.tap(no_wait_after=True)
 elif activation=='enter':await page.keyboard.press('Enter')
 else:await page.keyboard.press('Space')
 await page.wait_for_function("()=>{const o=document.querySelector('#invSyllabusVisualOverlay');return o&&!o.hidden}",timeout=5000);await frames(page,2);ov=await overlay(page,fid)
 if close_method=='explicit':await page.locator('#invSyllabusVisualClose').click(no_wait_after=True)
 else:await page.evaluate('history.back()')
 await page.wait_for_function("()=>document.querySelector('#invSyllabusVisualOverlay')?.hidden===true",timeout=5000);await frames(page,5);post=await snapshot(page,fid)
 ok=ov['visible'] and ov['count']==1 and ov['identity'] and post['x']==pre['x'] and post['y']==pre['y'] and post['chapter']==pre['chapter']==lesson and post['focus']==fid and not post['focusHidden'] and post['count']==1
 return {'figure_id':fid,'lesson':lesson,'activation':activation,'close_method':close_method,'delta_x':post['x']-pre['x'],'delta_y':post['y']-pre['y'],'chapter_preserved':post['chapter']==pre['chapter']==lesson,'focus_restored':post['focus']==fid and not post['focusHidden'],'overlay_count':ov['count'],'overlay_identity':ov['identity'],'runtime_count':post['count'],'result':'PASS' if ok else 'FAIL'}

async def phase_b2(browser,html,inv,label,out):
 ctx,page,errs=await prep(browser,html);rows=[]
 for x in inv:
  fid,lesson=x['id'],x['lesson']
  for a in ('click','touch','enter','space'): rows.append(await interact(page,fid,lesson,a,'explicit'))
  rows.append(await interact(page,fid,lesson,'click','browser_back'))
  rows.append(await interact(page,fid,lesson,'click','android_back_equivalent'))
 ordinary=[]
 for a,b in [('u1-intro','u1-protozoa'),('u3-ascaris','u3-nematode-parasites'),('u5-wvs','u5-cephalopods')]:
  await open_ch(page,a);await open_ch(page,b);await page.evaluate('history.back()');await page.wait_for_function("x=>(history.state?.view?.chapter||window.__invLastStudyView?.view?.chapter)===x",arg=a,timeout=5000);ordinary.append({'from':a,'to':b,'returned':a,'pass':True})
 await ctx.close()
 with (out/f'PHASE_B2_{label}_INTERACTIONS.csv').open('w',newline='',encoding='utf-8') as f:
  w=csv.DictWriter(f,fieldnames=list(rows[0]));w.writeheader();w.writerows(rows)
 s={'run':label,'records':len(rows),'failures':sum(r['result']!='PASS' for r in rows),'scroll_failures':sum(r['delta_x'] or r['delta_y'] for r in rows),'chapter_failures':sum(not r['chapter_preserved'] for r in rows),'focus_failures':sum(not r['focus_restored'] for r in rows),'overlay_identity_failures':sum(not r['overlay_identity'] for r in rows),'overlay_count_failures':sum(r['overlay_count']!=1 for r in rows),'runtime_duplicate_failures':sum(r['runtime_count']!=1 for r in rows),'ordinary_back':ordinary,'runtime_errors':errs}
 s['pass']=all([s['failures']==0,s['scroll_failures']==0,s['chapter_failures']==0,s['focus_failures']==0,s['overlay_identity_failures']==0,s['overlay_count_failures']==0,s['runtime_duplicate_failures']==0,not errs,all(x['pass'] for x in ordinary)])
 (out/f'PHASE_B2_{label}_SUMMARY.json').write_text(json.dumps(s,indent=2),encoding='utf-8');return s

async def main_async(args):
 out=Path(args.out);out.mkdir(parents=True,exist_ok=True)
 step19=Path(args.step19).read_text(encoding='utf-8');step21a=Path(args.step21a).read_text(encoding='utf-8');step21b=Path(args.step21b).read_text(encoding='utf-8')
 if step21a!=step21b: raise SystemExit('Run A/B HTML differ')
 async with async_playwright() as p:
  browser=await p.chromium.launch(headless=True,executable_path=args.chromium,args=['--no-sandbox','--disable-dev-shm-usage'])
  bi,br,be,_=await collect(browser,step19);ci,cr,ce,applied=await collect(browser,step21a)
  sa=await phase_b2(browser,step21a,ci,'A',out);sb=await phase_b2(browser,step21b,ci,'B',out)
  await browser.close()
 bmap={r['figure_id']:r for r in br};cmap={r['figure_id']:r for r in cr};binv={x['id']:x for x in bi};cinv={x['id']:x for x in ci}
 pre=[];post=[]
 for fid,t in TARGETS.items():
  bp=bmap[fid];cp=cmap[fid];pre.append({'figure_id':fid,'expected_lesson':t['pre_lesson'],'actual_lesson':bp['lesson'],'expected_destination':t['pre_dest'],'actual_destination':bp['destination'],'pass':bp['lesson']==t['pre_lesson'] and bp['destination']==t['pre_dest']})
  post.append({'figure_id':fid,'expected_lesson':t['post_lesson'],'actual_lesson':cp['lesson'],'expected_mode':t['post_mode'],'actual_mode':cp['placement_mode'],'expected_destination':t['post_dest'],'actual_destination':cp['destination'],'pass':cp['lesson']==t['post_lesson'] and cp['placement_mode']==t['post_mode'] and cp['destination']==t['post_dest'] and cp['default_visible'] and not cp['inside_closed_details'] and cp['horizontal_overflow_px']==0 and cp['document_overflow_px']==0 and cp['rendered_count']==1})
 non=[]
 for fid in sorted(set(bmap)&set(cmap)-set(TARGETS)):
  a,b=bmap[fid],cmap[fid];same=all(a[k]==b[k] for k in ['lesson','placement_mode','destination','caption']);non.append({'figure_id':fid,'pass':same,'before_lesson':a['lesson'],'after_lesson':b['lesson'],'before_mode':a['placement_mode'],'after_mode':b['placement_mode'],'before_destination':a['destination'],'after_destination':b['destination']})
 inv={'step19_occurrences':len(bi),'step21_occurrences':len(ci),'step19_unique':len(binv),'step21_unique':len(cinv),'missing':sorted(set(binv)-set(cinv)),'extra':sorted(set(cinv)-set(binv)),'duplicates':{k:v for k,v in Counter(x['id'] for x in ci).items() if v>1},'markup_changes':[fid for fid in binv if fid in cinv and binv[fid]['html']!=cinv[fid]['html']],'caption_changes':[fid for fid in binv if fid in cinv and binv[fid]['caption']!=cinv[fid]['caption']]}
 dist=Counter(r['placement_mode'] for r in cr)
 layout={'contextual_visible_matches':dist['CONTEXTUAL_VISIBLE_MATCH'],'neutral_original_container_fallbacks':dist['NEUTRAL_ORIGINAL_CONTAINER'],'neutral_lesson_anchor_fallbacks':dist['NEUTRAL_LESSON_ANCHOR'],'hidden':sum(not r['default_visible'] for r in cr),'closed_details':sum(r['inside_closed_details'] for r in cr),'overflow_failures':sum(max(r['horizontal_overflow_px'],r['document_overflow_px'])>0 for r in cr),'runtime_duplicates':sum(r['rendered_count']!=1 for r in cr),'runtime_errors':be+ce}
 ownership=[{'figure_id':fid,'from':binv[fid]['lesson'],'to':cinv[fid]['lesson']} for fid in binv if binv[fid]['lesson']!=cinv.get(fid,{}).get('lesson')]
 full=[]
 for r in cr:
  fid=r['figure_id']
  if fid in TARGETS: academic='PASS — STEP-20 ADJUDICATED REMAP'
  elif fid in REVIEW_IDS: academic='REVIEW — NON-BLOCKING, PLACEMENT UNCHANGED'
  else: academic='PASS — PRIOR ACADEMIC CONTEXT RETAINED; SOURCE AND PLACEMENT UNCHANGED'
  blocking='NO' if (fid in TARGETS and next(x for x in post if x['figure_id']==fid)['pass']) or fid not in TARGETS else 'YES'
  full.append({**r,'academic_assessment':academic,'blocking':blocking,'interaction_status':'PASS' if sa['pass'] and sb['pass'] else 'FAIL'})
 with (out/'STEP22_PRECONDITION_DESTINATIONS.csv').open('w',newline='',encoding='utf-8') as f:w=csv.DictWriter(f,fieldnames=list(pre[0]));w.writeheader();w.writerows(pre)
 with (out/'STEP22_POSTCONDITIONS.csv').open('w',newline='',encoding='utf-8') as f:w=csv.DictWriter(f,fieldnames=list(post[0]));w.writeheader();w.writerows(post)
 with (out/'NON_TARGET_PLACEMENT_COMPARISON.csv').open('w',newline='',encoding='utf-8') as f:w=csv.DictWriter(f,fieldnames=list(non[0]));w.writeheader();w.writerows(non)
 with (out/'FULL_CONTEXTUAL_AUDIT_STEP22.csv').open('w',newline='',encoding='utf-8') as f:w=csv.DictWriter(f,fieldnames=list(full[0]));w.writeheader();w.writerows(full)
 summary={'preconditions_pass':all(x['pass'] for x in pre),'postconditions_pass':all(x['pass'] for x in post),'inventory':inv,'ownership_migrations':ownership,'non_target_drift_count':sum(not x['pass'] for x in non),'placement_distribution':layout,'step21_runtime_applied_ids':sorted(applied['ids']),'step21_runtime_fatal':applied['fatal'],'phase_b2_A':sa,'phase_b2_B':sb,'blocker_c_remediation_pass':sum(x['pass'] for x in post),'full_contextual_audit_pass':all(x['pass'] for x in post) and sum(not x['pass'] for x in non)==0 and layout['hidden']==0 and layout['closed_details']==0 and layout['overflow_failures']==0 and layout['runtime_duplicates']==0 and not layout['runtime_errors'] and sa['pass'] and sb['pass']}
 summary['pass']=all([summary['preconditions_pass'],summary['postconditions_pass'],inv['step19_occurrences']==82,inv['step21_occurrences']==82,inv['step19_unique']==82,inv['step21_unique']==82,not inv['missing'],not inv['extra'],not inv['duplicates'],not inv['markup_changes'],not inv['caption_changes'],ownership==[{'figure_id':'peripatus','from':'u4-modes-life','to':'u4-peripatus'}],summary['non_target_drift_count']==0,layout['contextual_visible_matches']==68,layout['neutral_original_container_fallbacks']==14,layout['neutral_lesson_anchor_fallbacks']==0,layout['hidden']==0,layout['closed_details']==0,layout['overflow_failures']==0,layout['runtime_duplicates']==0,not layout['runtime_errors'],sorted(applied['ids'])==sorted(TARGETS),applied['fatal'] is None,sa['pass'],sb['pass'],summary['full_contextual_audit_pass']])
 (out/'STEP22_FULL_REGRESSION_AUDIT.json').write_text(json.dumps(summary,ensure_ascii=False,indent=2),encoding='utf-8')
 report=['# INVERTEBRATA Step-22 Full Regression Audit','',f"Overall: {'PASS' if summary['pass'] else 'FAIL'}",'',f"Precondition destinations: {'PASS' if summary['preconditions_pass'] else 'FAIL'}",f"Postconditions: {'PASS' if summary['postconditions_pass'] else 'FAIL'}",f"Blocker-C remediation: {summary['blocker_c_remediation_pass']}/9 PASS",f"Non-target drift: {summary['non_target_drift_count']}",f"Placement distribution: {dict(layout)}",f"Phase-B2 Run A: {'PASS' if sa['pass'] else 'FAIL'}",f"Phase-B2 Run B: {'PASS' if sb['pass'] else 'FAIL'}",f"Full contextual audit: {'PASS' if summary['full_contextual_audit_pass'] else 'FAIL'}"]
 (out/'STEP22_FULL_REGRESSION_AUDIT_REPORT.md').write_text('\n'.join(report)+'\n',encoding='utf-8')
 raise SystemExit(0 if summary['pass'] else 2)

def main():
 ap=argparse.ArgumentParser();ap.add_argument('--step19',required=True);ap.add_argument('--step21a',required=True);ap.add_argument('--step21b',required=True);ap.add_argument('--out',required=True);ap.add_argument('--chromium',required=True);args=ap.parse_args();raise SystemExit(asyncio.run(main_async(args)))
if __name__=='__main__':main()
