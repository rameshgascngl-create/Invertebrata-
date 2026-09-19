#!/usr/bin/env python3
from __future__ import annotations
import argparse, asyncio, csv, hashlib, json
from collections import Counter, defaultdict
from pathlib import Path
from playwright.async_api import async_playwright

EXPECTED_HTML_SHA="62bc38edec4c0256d14952c79c106ac91698a035d441937399f756e28c0d1b95"
EXPECTED_HTML_SIZE=2387111
EXPECTED_INVENTORY=82
THRESHOLD=3
ANCHORS={
 "earthworm-reproductive-r2":{"mode":"neutral_original","score":0,"forbidden":"Closed vessels and work"},
 "nereis-circulatory":{"mode":"neutral_original","score":0,"forbidden":"Parapodium as a multifunctional limb"},
 "gastropod-torsion":{"mode":"neutral_original","score":0,"forbidden":"Reproduction"},
 "master-sycon-canal":{"mode":"contextual","score":10,"candidate":"Position and habit"},
 "PENAEUS-01-R1":{"mode":"contextual","score":7,"candidate":"Appendages"},
 "PARAMECIUM-CV-CILIA-N1":{"mode":"contextual","score":10,"candidate":"Pellicle and cilia"},
 "earthworm-external-r2":{},
}

def sha256_file(path:Path)->str:
    h=hashlib.sha256()
    with path.open("rb") as f:
        for chunk in iter(lambda:f.read(1024*1024),b""): h.update(chunk)
    return h.hexdigest()

async def open_chapter(page,lesson:str):
    await page.evaluate("""lesson=>{
      const b=document.querySelector('button[data-open-chapter="'+lesson+'"]');
      if(!b) throw new Error('missing chapter '+lesson);
      b.click();
    }""",lesson)
    await page.wait_for_timeout(80)

async def source_inventory(page):
    return await page.evaluate("""() => {
      const out=[], t=document.createElement('template');
      for(const [lesson,html] of Object.entries(window.ORG_SYSTEM_DIAGRAMS||{})){
        t.innerHTML=html||'';
        for(const f of t.content.querySelectorAll('figure[data-v188-plate]')){
          out.push({lesson,id:f.getAttribute('data-v188-plate'),
                    caption:(f.querySelector('figcaption')?.textContent||'').trim()});
        }
      }
      return out;
    }""")

async def placement(page,plate):
    return await page.locator(f'figure[data-v188-plate="{plate}"]').evaluate("""fig=>{
      const pane=fig.closest('.theory-pane');
      const cap=(fig.querySelector('figcaption')?.innerText||'').trim();
      const stop=new Set(['figure','system','systems','diagram','diagrams','of','the','and','a','an','in','with','showing','structure','structures','external','internal','representative','type','study','overview']);
      const families={
       digestive:['digestive','alimentary','gut','pharynx','intestine','stomach','feeding','nutrition','hepatopancreas'],
       respiratory:['respiratory','respiration','gill','gills','ctenidium','trachea','pulmonary','breathing'],
       circulatory:['circulatory','circulation','blood','vascular','heart','haemocoel','hemocoel'],
       excretory:['excretory','excretion','nephridia','nephridium','malpighian','osmoregulation'],
       nervous:['nervous','nerve','ganglion','ganglia','brain','sensory'],
       reproductive:['reproductive','reproduction','gonad','gonads','ovary','testis','testes','uterus','male','female'],
       lifecycle:['life','cycle','larva','larvae','cercaria','miracidium','metacercaria','nauplius','medusa'],
       morphology:['morphology','body','wall','shell','appendage','appendages','surface','colony','hydranth','gonangium'],
       locomotion:['locomotion','locomotory','cilia','flagellum','pseudopodia','parapodia','tube','foot','feet'],
       canal:['canal','canals','sycon','spongocoel','osculum','ostia','water','vascular']
      };
      function words(t){return String(t||'').toLowerCase().replace(/[^a-z0-9\u0B80-\u0BFF]+/g,' ').split(/\s+/).filter(x=>x.length>2&&!stop.has(x));}
      function score(a,b){const cw=words(a),tw=new Set(words(b));let n=0;cw.forEach(w=>{if(tw.has(w))n+=3});Object.values(families).forEach(f=>{if(f.some(w=>a.indexOf(w)>=0)&&f.some(w=>b.indexOf(w)>=0))n+=7});return n;}
      const sections=Array.from(pane?.querySelectorAll('.textbook-detail .textbook-subsection')||[]).filter(sec=>!sec.closest('details:not([open])'));
      let best=null,bestScore=0;
      for(const sec of sections){const sc=score(cap.toLowerCase(),sec.innerText.toLowerCase());if(sc>bestScore){bestScore=sc;best=sec;}}
      const parent=fig.parentElement;
      let mode='unknown',actual=null;
      if(parent?.classList.contains('v188-neutral-figure-anchor')) mode='neutral_lesson_anchor';
      else if(fig.closest('.textbook-visuals')) mode='neutral_original';
      else {
        mode='contextual';
        let n=fig.previousElementSibling;
        while(n&&!n.classList?.contains('textbook-subsection'))n=n.previousElementSibling;
        actual=n;
      }
      const r=fig.getBoundingClientRect(),vw=document.documentElement.clientWidth;
      const overflow=Math.max(0,Math.ceil(r.right-vw),Math.ceil(-r.left));
      let clipping=false,a=fig.parentElement;
      while(a&&a!==document.body){
        const cs=getComputedStyle(a),ar=a.getBoundingClientRect();
        if(/(hidden|clip)/.test(cs.overflowX+' '+cs.overflowY) &&
           (r.left<ar.left-1||r.right>ar.right+1||r.top<ar.top-1||r.bottom>ar.bottom+1)){clipping=true;break;}
        a=a.parentElement;
      }
      const fc=fig.querySelector('figcaption'),fr=fc?.getBoundingClientRect();
      return {
       caption:cap,source_container:'textbook-visuals',
       final_container:parent?(parent.dataset.v188NeutralFallback==='1'?'v188-neutral-figure-anchor':(parent.className||parent.tagName)):null,
       placement_mode:mode,
       expected_best_heading:best?.querySelector('h4')?.innerText?.trim()||null,
       expected_best_score:bestScore,
       candidate_subsection:actual?.querySelector('h4')?.innerText?.trim()||null,
       candidate_score:actual?score(cap.toLowerCase(),actual.innerText.toLowerCase()):0,
       threshold:3,threshold_met:mode==='contextual'?(!!actual&&score(cap.toLowerCase(),actual.innerText.toLowerCase())>=3):false,
       default_visible:!!(r.width&&r.height)&&!fig.closest('details:not([open])')&&getComputedStyle(fig).display!=='none'&&getComputedStyle(fig).visibility!=='hidden',
       inside_closed_details:!!fig.closest('details:not([open])'),
       rendered_instance_count:document.querySelectorAll('figure[data-v188-plate="'+CSS.escape(fig.dataset.v188Plate)+'"]').length,
       horizontal_overflow_px:overflow,
       document_horizontal_overflow_px:Math.max(0,document.documentElement.scrollWidth-document.documentElement.clientWidth),
       clipping,
       caption_visible:!!fc&&getComputedStyle(fc).display!=='none'&&(fr?.width||0)>0&&(fr?.height||0)>0,
       role:fig.getAttribute('role'),tabindex:fig.getAttribute('tabindex')
      };
    }""")

async def overlay_state(page,plate):
    return await page.evaluate("""plate=>{
      const f=document.querySelector('figure[data-v188-plate="'+CSS.escape(plate)+'"]');
      const m=f?.querySelector('svg,img'),o=document.querySelector('#invSyllabusVisualOverlay');
      const z=o?.querySelectorAll('.theory-zoom-media')||[], q=z[0]||null;
      let same=false;
      if(m&&q&&m.tagName===q.tagName){
        same=m.tagName.toLowerCase()==='svg'?(m.innerHTML===q.innerHTML&&m.getAttribute('viewBox')===q.getAttribute('viewBox')):m.getAttribute('src')===q.getAttribute('src');
      }
      return {visible:!!o&&!o.hidden,count:z.length,identity:same};
    }""",plate)

async def close_overlay(page,mode):
    before=await page.evaluate("scrollY")
    if mode in ("enter","space"): await page.keyboard.press("Escape")
    elif mode=="touch": await page.locator("#invSyllabusVisualClose").tap(timeout=5000,no_wait_after=True)
    else: await page.locator("#invSyllabusVisualClose").click(timeout=5000,no_wait_after=True)
    await page.wait_for_timeout(80)
    return await page.evaluate("""before=>({
      hidden:document.querySelector('#invSyllabusVisualOverlay').hidden,
      scroll_before:before,scroll_after:scrollY,scroll_delta:scrollY-before,
      active_tag:document.activeElement?.tagName||null,
      active_id:document.activeElement?.id||null,
      active_plate:document.activeElement?.getAttribute?.('data-v188-plate')||null,
      active_hidden:!!document.activeElement?.closest?.('[hidden]')
    })""",before)

async def activation(page,plate,mode):
    f=page.locator(f'figure[data-v188-plate="{plate}"]')
    await f.scroll_into_view_if_needed(timeout=5000)
    before=await page.evaluate("scrollY")
    if mode=="click": await f.click(timeout=5000,no_wait_after=True)
    elif mode=="touch": await f.tap(timeout=5000,no_wait_after=True)
    else:
        await f.focus()
        await page.keyboard.press("Enter" if mode=="enter" else "Space")
    await page.wait_for_timeout(50)
    ov=await overlay_state(page,plate)
    open_scroll=await page.evaluate("scrollY")
    cl=await close_overlay(page,mode)
    return {
      "opened":ov["visible"],"overlay_count":ov["count"],"overlay_identity":ov["identity"],
      "space_scroll_delta":open_scroll-before if mode=="space" else None,
      "close_hidden":cl["hidden"],"close_scroll_delta":cl["scroll_delta"],
      "focus_return_valid":not cl["active_hidden"],
      "active_after_close":{"tag":cl["active_tag"],"id":cl["active_id"],"plate":cl["active_plate"]}
    }

async def semantic_fixtures(page):
    return await page.evaluate("""async()=>{
      const mount=document.querySelector('#invTheoryMount');
      const sleep=ms=>new Promise(r=>setTimeout(r,ms));
      async function one(name,body,expected){
        const pane=document.createElement('div');pane.className='theory-pane';pane.dataset.auditFixture=name;
        pane.innerHTML=body;mount.appendChild(pane);await sleep(40);
        const fig=pane.querySelector('figure.sys-fig');
        const neutral=!!fig.closest('.textbook-visuals');
        let prev=fig.previousElementSibling;while(prev&&!prev.classList?.contains('textbook-subsection'))prev=prev.previousElementSibling;
        const result=prev?'contextual':'neutral';
        const heading=prev?.querySelector('h4')?.textContent?.trim()||null;
        const count=pane.querySelectorAll('figure.sys-fig').length;
        pane.remove();await sleep(10);
        return {name,result,heading,count,pass:result===expected};
      }
      const fig='<div class="textbook-visuals"><figure class="sys-fig"><figcaption>Digestive system</figcaption><svg viewBox="0 0 10 10"></svg></figure></div>';
      const sec='<div class="textbook-detail"><section class="textbook-subsection"><h4>Digestive system</h4><p>Digestive gut intestine.</p></section></div>';
      const low='<div class="textbook-detail"><section class="textbook-subsection"><h4>Shell form</h4><p>Shell aperture colour.</p></section></div>';
      const hidden='<div class="textbook-detail"><details><summary>More</summary><section class="textbook-subsection"><h4>Digestive system</h4><p>Digestive gut intestine.</p></section></details></div>';
      const open='<div class="textbook-detail"><details open><summary>More</summary><section class="textbook-subsection"><h4>Digestive system</h4><p>Digestive gut intestine.</p></section></details></div>';
      const nestedOuterClosed='<div class="textbook-detail"><details><summary>O</summary><details open><summary>I</summary><section class="textbook-subsection"><h4>Digestive system</h4><p>Digestive gut.</p></section></details></details></div>';
      const nestedInnerClosed='<div class="textbook-detail"><details open><summary>O</summary><details><summary>I</summary><section class="textbook-subsection"><h4>Digestive system</h4><p>Digestive gut.</p></section></details></details></div>';
      const nestedOpen='<div class="textbook-detail"><details open><summary>O</summary><details open><summary>I</summary><section class="textbook-subsection"><h4>Digestive system</h4><p>Digestive gut.</p></section></details></details></div>';
      const cases=[];
      cases.push(await one('outside-details',fig+sec,'contextual'));
      cases.push(await one('inside-open-details',fig+open,'contextual'));
      cases.push(await one('inside-closed-details',fig+hidden,'neutral'));
      cases.push(await one('outer-closed-inner-open',fig+nestedOuterClosed,'neutral'));
      cases.push(await one('outer-open-inner-closed',fig+nestedInnerClosed,'neutral'));
      cases.push(await one('all-ancestors-open',fig+nestedOpen,'contextual'));
      cases.push(await one('hidden-high-visible-low',fig+low+hidden,'neutral'));
      return {cases,pass:cases.every(x=>x.pass&&x.count===1)};
    }""")

async def audit_run(html,output:Path,label:str,shard:int,shards:int,chrome:str):
    output.mkdir(parents=True,exist_ok=True)
    async with async_playwright() as p:
        browser=await p.chromium.launch(headless=True,executable_path=chrome,args=["--no-sandbox","--disable-dev-shm-usage"])
        context=await browser.new_context(viewport={"width":360,"height":800},device_scale_factor=1,is_mobile=True,has_touch=True)
        page=await context.new_page()
        runtime_errors=[]
        page.on("pageerror",lambda e:runtime_errors.append("PAGEERROR "+str(e)))
        page.on("console",lambda m:runtime_errors.append("CONSOLE "+m.type+" "+m.text) if m.type=="error" else None)
        await page.set_content(html,wait_until="load")
        await page.wait_for_timeout(100)
        inventory=await source_inventory(page)
        all_ids=sorted(x["id"] for x in inventory)
        selected={pid for i,pid in enumerate(all_ids) if i%shards==shard}
        bylesson=defaultdict(list)
        for x in inventory:
            if x["id"] in selected: bylesson[x["lesson"]].append(x["id"])
        lesson_buttons=await page.locator("button[data-open-chapter]").evaluate_all("(els)=>els.map(e=>e.dataset.openChapter)")
        rows=[]
        for lesson in lesson_buttons:
            if lesson not in bylesson: continue
            await open_chapter(page,lesson)
            for plate in bylesson[lesson]:
                rec={"run":label,"shard":shard,"figure_id":plate,"chapter":lesson}
                try:
                    rec.update(await placement(page,plate))
                    for mode in ("click","touch","enter","space"):
                        t=await activation(page,plate,mode)
                        for k,v in t.items(): rec[f"{mode}_{k}"]=v
                    checks=[
                      rec["default_visible"],not rec["inside_closed_details"],rec["rendered_instance_count"]==1,
                      rec["horizontal_overflow_px"]==0,rec["document_horizontal_overflow_px"]==0,
                      not rec["clipping"],rec["caption_visible"],rec["role"]=="button",rec["tabindex"]=="0"
                    ]
                    for mode in ("click","touch","enter","space"):
                        checks += [rec[f"{mode}_opened"],rec[f"{mode}_overlay_count"]==1,
                                   rec[f"{mode}_overlay_identity"],rec[f"{mode}_close_hidden"],
                                   rec[f"{mode}_focus_return_valid"]]
                    checks += [rec["space_space_scroll_delta"]==0]
                    if rec["placement_mode"]=="contextual": checks += [rec["candidate_score"]>=THRESHOLD]
                    rec["result"]="PASS" if all(checks) else "FAIL"
                except Exception as e:
                    rec["result"]="FAIL";rec["exception"]=repr(e)
                rows.append(rec)
                if rec["result"]=="FAIL":
                    try: await page.screenshot(path=str(output/f"{label}__FAIL__{plate}.png"),full_page=True)
                    except Exception: pass
            other=next((x for x in lesson_buttons if x!=lesson),None)
            if other:
                await open_chapter(page,other);await open_chapter(page,lesson)
                for plate in bylesson[lesson]:
                    n=await page.locator(f'figure[data-v188-plate="{plate}"]').count()
                    rec=next(r for r in rows if r["figure_id"]==plate and r["chapter"]==lesson)
                    rec["post_revisit_rendered_count"]=n
                    rec["chapter_revisit"]=n==1
                    rec["duplicate_after_revisit"]=n!=1
                    if n!=1: rec["result"]="FAIL"
        fixtures=await semantic_fixtures(page) if shard==0 else None
        sycon=[]
        if "master-sycon-canal" in selected:
            await open_chapter(page,"u2-sycon")
            for cycle in (1,2,3):
                if cycle==3:
                    await open_chapter(page,"u1-paramecium");await open_chapter(page,"u2-sycon")
                fig=page.locator('figure[data-v188-plate="master-sycon-canal"]')
                await fig.scroll_into_view_if_needed();await fig.focus()
                before=await page.evaluate("scrollY")
                await page.keyboard.press("Enter");await page.wait_for_timeout(50)
                ov=await overlay_state(page,"master-sycon-canal")
                await page.keyboard.press("Escape");await page.wait_for_timeout(80)
                after=await page.evaluate("""()=>({
                  scrollY,active_tag:document.activeElement?.tagName||null,
                  active_plate:document.activeElement?.getAttribute?.('data-v188-plate')||null,
                  active_hidden:!!document.activeElement?.closest?.('[hidden]')
                })""")
                sycon.append({"cycle":cycle,"open":ov["visible"],"overlay_count":ov["count"],
                              "overlay_identity":ov["identity"],"scroll_before":before,"scroll_after":after["scrollY"],
                              "scroll_delta":after["scrollY"]-before,
                              "focus_after_close":{"tag":after["active_tag"],"plate":after["active_plate"],"hidden":after["active_hidden"]},
                              "pass":ov["visible"] and ov["count"]==1 and ov["identity"] and after["scrollY"]==before and not after["active_hidden"]})
        rowmap={r["figure_id"]:r for r in rows}
        anchor_checks={}
        for plate,exp in ANCHORS.items():
            if plate not in selected: continue
            r=rowmap.get(plate);ok=bool(r and r["result"]=="PASS")
            if r:
                if "mode" in exp: ok &= r["placement_mode"]==exp["mode"]
                if "score" in exp: ok &= r["expected_best_score"]==exp["score"]
                if "candidate" in exp: ok &= r["candidate_subsection"]==exp["candidate"]
                if "forbidden" in exp: ok &= r.get("candidate_subsection")!=exp["forbidden"]
            anchor_checks[plate]=bool(ok)
        summary={
          "run":label,"shard":shard,"shards":shards,
          "full_source_inventory_count":len(all_ids),"full_source_unique_ids":len(set(all_ids)),
          "full_source_duplicate_ids":{k:v for k,v in Counter(all_ids).items() if v>1},
          "selected_count":len(selected),"rows_checked":len(rows),
          "contextual_visible_matches":sum(r.get("placement_mode")=="contextual" for r in rows),
          "neutral_original_container_fallbacks":sum(r.get("placement_mode")=="neutral_original" for r in rows),
          "neutral_lesson_anchor_fallbacks":sum(r.get("placement_mode")=="neutral_lesson_anchor" for r in rows),
          "below_threshold_contextual_placements":sum(r.get("placement_mode")=="contextual" and r.get("candidate_score",0)<3 for r in rows),
          "closed_details_contextual_placements":sum(bool(r.get("inside_closed_details")) for r in rows),
          "index_arbitrary_fallback_placements":sum(r.get("placement_mode")=="contextual" and r.get("candidate_score",0)<3 for r in rows),
          "hidden_required_figures":sum(not bool(r.get("default_visible")) for r in rows),
          "overflow_failures":sum(max(r.get("horizontal_overflow_px",0),r.get("document_horizontal_overflow_px",0))>0 for r in rows),
          "clipping_failures":sum(bool(r.get("clipping")) for r in rows),
          "maximum_horizontal_overflow_px":max([max(r.get("horizontal_overflow_px",0),r.get("document_horizontal_overflow_px",0)) for r in rows] or [0]),
          "runtime_duplicates":sum(r.get("rendered_instance_count")!=1 or r.get("post_revisit_rendered_count")!=1 for r in rows),
          "failed_figures":[r["figure_id"] for r in rows if r["result"]!="PASS"],
          "anchor_checks":anchor_checks,"semantic_fixtures":fixtures,"sycon_cycles":sycon,
          "runtime_errors":runtime_errors
        }
        summary["pass"]=(
          len(all_ids)==EXPECTED_INVENTORY and len(set(all_ids))==EXPECTED_INVENTORY and
          not summary["full_source_duplicate_ids"] and len(rows)==len(selected) and
          summary["below_threshold_contextual_placements"]==0 and
          summary["closed_details_contextual_placements"]==0 and
          summary["index_arbitrary_fallback_placements"]==0 and
          summary["hidden_required_figures"]==0 and summary["overflow_failures"]==0 and
          summary["clipping_failures"]==0 and summary["runtime_duplicates"]==0 and
          not summary["failed_figures"] and all(anchor_checks.values()) and
          (fixtures is None or fixtures["pass"]) and all(x["pass"] for x in sycon)
        )
        (output/f"RUN_{label}_RAW.json").write_text(json.dumps({"summary":summary,"rows":rows},ensure_ascii=False,indent=2),encoding="utf-8")
        with (output/f"RUN_{label}_MATRIX.csv").open("w",encoding="utf-8",newline="") as fh:
            fields=sorted({k for r in rows for k in r})
            w=csv.DictWriter(fh,fieldnames=fields);w.writeheader();w.writerows(rows)
        await browser.close()
        return summary,rows

def normalized(rows):
    out=[]
    for r in rows:
        x=dict(r);x.pop("run",None)
        out.append(x)
    return sorted(out,key=lambda r:r["figure_id"])

async def main_async(args):
    html_path=Path(args.html);out=Path(args.out);out.mkdir(parents=True,exist_ok=True)
    sha=sha256_file(html_path);size=html_path.stat().st_size
    if sha!=EXPECTED_HTML_SHA or size!=EXPECTED_HTML_SIZE:
        raise SystemExit(f"AUTHORITATIVE HTML IDENTITY FAIL sha={sha} size={size}")
    html=html_path.read_text(encoding="utf-8")
    a,ar=await audit_run(html,out/"run-a","A",args.shard_index,args.shard_count,args.chromium)
    b,br=await audit_run(html,out/"run-b","B",args.shard_index,args.shard_count,args.chromium)
    same=normalized(ar)==normalized(br)
    comp={"shard":args.shard_index,"run_a_pass":a["pass"],"run_b_pass":b["pass"],
          "row_results_identical":same,"overall_pass":a["pass"] and b["pass"] and same}
    (out/"SHARD_REPEATABILITY.json").write_text(json.dumps(comp,indent=2),encoding="utf-8")
    return 0 if comp["overall_pass"] else 2

def main():
    ap=argparse.ArgumentParser()
    ap.add_argument("--html",required=True);ap.add_argument("--out",required=True)
    ap.add_argument("--shard-index",type=int,required=True);ap.add_argument("--shard-count",type=int,default=8)
    ap.add_argument("--chromium",required=True)
    args=ap.parse_args()
    raise SystemExit(asyncio.run(main_async(args)))
if __name__=="__main__":main()
