#!/usr/bin/env python3
from __future__ import annotations
import argparse, asyncio, csv, hashlib, json, os
from collections import Counter, defaultdict
from pathlib import Path
from playwright.async_api import async_playwright

TARGETS=[
 "earthworm-reproductive-r2",
 "PARAMECIUM-CV-CILIA-N1",
 "PENAEUS-01-R1",
 "master-sycon-canal",
 "fasciola-excretory",
]
THRESHOLD=3
EXPECTED_INVENTORY=82

def sha256_file(path:Path)->str:
    h=hashlib.sha256()
    with path.open("rb") as f:
        for chunk in iter(lambda:f.read(1024*1024),b""): h.update(chunk)
    return h.hexdigest()

async def wait_frames(page,n=4):
    await page.evaluate("""n=>new Promise(resolve=>{
      let left=n; function step(){left-=1;if(left<=0)resolve();else requestAnimationFrame(step);}
      requestAnimationFrame(step);
    })""",n)

async def open_chapter(page,lesson):
    await page.evaluate("""lesson=>{
      const b=document.querySelector('button[data-open-chapter="'+lesson+'"]');
      if(!b)throw new Error('missing chapter button '+lesson);
      b.click();
    }""",lesson)
    await page.wait_for_function("""lesson=>{
      const s=history.state&&history.state.view&&history.state.view.chapter;
      const v=window.__invLastStudyView&&window.__invLastStudyView.view&&window.__invLastStudyView.view.chapter;
      return s===lesson||v===lesson;
    }""",lesson,timeout=5000)
    await wait_frames(page,2)

async def inventory(page):
    return await page.evaluate("""() => {
      const out=[],t=document.createElement('template');
      for(const [lesson,html] of Object.entries(window.ORG_SYSTEM_DIAGRAMS||{})){
        t.innerHTML=html||'';
        for(const f of t.content.querySelectorAll('figure[data-v188-plate]')){
          out.push({lesson,id:f.dataset.v188Plate,caption:(f.querySelector('figcaption')?.textContent||'').trim()});
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
      if(parent?.classList.contains('v188-neutral-figure-anchor'))mode='neutral_lesson_anchor';
      else if(fig.closest('.textbook-visuals'))mode='neutral_original';
      else{
        mode='contextual';let n=fig.previousElementSibling;
        while(n&&!n.classList?.contains('textbook-subsection'))n=n.previousElementSibling;
        actual=n;
      }
      const r=fig.getBoundingClientRect(),vw=document.documentElement.clientWidth;
      return {
        placement_mode:mode,
        expected_best_heading:best?.querySelector('h4')?.innerText?.trim()||null,
        expected_best_score:bestScore,
        candidate_subsection:actual?.querySelector('h4')?.innerText?.trim()||null,
        candidate_score:actual?score(cap.toLowerCase(),actual.innerText.toLowerCase()):0,
        threshold:3,
        default_visible:!!(r.width&&r.height)&&!fig.closest('details:not([open])')&&getComputedStyle(fig).display!=='none'&&getComputedStyle(fig).visibility!=='hidden',
        inside_closed_details:!!fig.closest('details:not([open])'),
        rendered_instance_count:document.querySelectorAll('figure[data-v188-plate="'+CSS.escape(fig.dataset.v188Plate)+'"]').length,
        horizontal_overflow_px:Math.max(0,Math.ceil(r.right-vw),Math.ceil(-r.left)),
        document_horizontal_overflow_px:Math.max(0,document.documentElement.scrollWidth-document.documentElement.clientWidth)
      };
    }""")

async def state_snapshot(page,plate):
    return await page.evaluate("""plate=>{
      function desc(el){
        if(!el)return null;
        return {
          tag:el.tagName||null,id:el.id||null,
          plate:el.getAttribute?.('data-v188-plate')||el.closest?.('[data-v188-plate]')?.getAttribute('data-v188-plate')||null,
          hidden:!!el.closest?.('[hidden]'),
          className:typeof el.className==='string'?el.className:null
        };
      }
      return {
        scroll_x:scrollX,scroll_y:scrollY,
        history_length:history.length,
        history_state_overlay:!!(history.state&&history.state.v188VisualOverlay),
        chapter:(history.state&&history.state.view&&history.state.view.chapter)||
                (window.__invLastStudyView&&window.__invLastStudyView.view&&window.__invLastStudyView.view.chapter)||null,
        focus:desc(document.activeElement),
        plate_count:document.querySelectorAll('figure[data-v188-plate="'+CSS.escape(plate)+'"]').length,
        overlay_hidden:document.querySelector('#invSyllabusVisualOverlay')?.hidden,
      };
    }""",plate)

async def overlay_identity(page,plate):
    return await page.evaluate("""plate=>{
      const f=document.querySelector('figure[data-v188-plate="'+CSS.escape(plate)+'"]');
      const src=f?.querySelector('svg,img');
      const overlay=document.querySelector('#invSyllabusVisualOverlay');
      const media=overlay?.querySelectorAll('.theory-zoom-media')||[];
      const clone=media[0]||null;
      let same=false;
      if(src&&clone&&src.tagName===clone.tagName){
        same=src.tagName.toLowerCase()==='svg'
          ? src.innerHTML===clone.innerHTML&&src.getAttribute('viewBox')===clone.getAttribute('viewBox')
          : src.getAttribute('src')===clone.getAttribute('src');
      }
      return {visible:!!overlay&&!overlay.hidden,count:media.length,identity:same};
    }""",plate)

async def activate(page,plate,method):
    loc=page.locator(f'figure[data-v188-plate="{plate}"]')
    await loc.scroll_into_view_if_needed(timeout=5000)
    await wait_frames(page,2)
    if method=="click":
        await loc.click(timeout=5000,no_wait_after=True)
    elif method=="touch":
        await loc.tap(timeout=5000,no_wait_after=True)
    elif method=="enter":
        await loc.focus();await page.keyboard.press("Enter")
    elif method=="space":
        await loc.focus();await page.keyboard.press("Space")
    else:
        raise ValueError(method)
    await page.wait_for_function("()=>{const o=document.querySelector('#invSyllabusVisualOverlay');return o&&!o.hidden}",timeout=5000)
    await wait_frames(page,2)

async def close_overlay(page,method):
    if method=="explicit":
        await page.locator("#invSyllabusVisualClose").click(timeout=5000,no_wait_after=True)
    elif method=="browser_back":
        await page.evaluate("history.back()")
    elif method=="android_back":
        result=await page.evaluate("window.__invHandleAndroidBack&&window.__invHandleAndroidBack()")
        if result!="overlay": raise RuntimeError("Android Back did not report overlay")
    else: raise ValueError(method)
    await page.wait_for_function("()=>document.querySelector('#invSyllabusVisualOverlay')?.hidden===true",timeout=5000)
    await wait_frames(page,5)

async def activation_record(page,plate,lesson,activation_method,close_method,iteration,phase):
    await open_chapter(page,lesson)
    loc=page.locator(f'figure[data-v188-plate="{plate}"]')
    await loc.scroll_into_view_if_needed(timeout=5000);await wait_frames(page,2)
    pre=await state_snapshot(page,plate)
    await activate(page,plate,activation_method)
    opened=await overlay_identity(page,plate)
    mid=await state_snapshot(page,plate)
    await close_overlay(page,close_method)
    post=await state_snapshot(page,plate)
    dx=post["scroll_x"]-pre["scroll_x"];dy=post["scroll_y"]-pre["scroll_y"]
    result=all([
      opened["visible"],opened["count"]==1,opened["identity"],
      dx==0,dy==0,post["chapter"]==pre["chapter"]==lesson,
      post["plate_count"]==1,not post["focus"]["hidden"],
      post["focus"]["plate"]==plate,
      post["history_state_overlay"] is False,
      post["overlay_hidden"] is True,
    ])
    return {
      "phase":phase,"figure_id":plate,"chapter":lesson,
      "activation_method":activation_method,"close_method":close_method,"iteration":iteration,
      "pre_scroll_x":pre["scroll_x"],"pre_scroll_y":pre["scroll_y"],
      "post_scroll_x":post["scroll_x"],"post_scroll_y":post["scroll_y"],
      "delta_x":dx,"delta_y":dy,
      "pre_history_length":pre["history_length"],"overlay_history_length":mid["history_length"],"post_close_history_length":post["history_length"],
      "pre_focus":pre["focus"],"post_focus":post["focus"],
      "overlay_count":opened["count"],"overlay_identity":opened["identity"],
      "overlay_marker_while_open":mid["history_state_overlay"],
      "overlay_marker_after_close":post["history_state_overlay"],
      "chapter_before":pre["chapter"],"chapter_after":post["chapter"],
      "chapter_preserved":post["chapter"]==pre["chapter"]==lesson,
      "runtime_plate_count":post["plate_count"],
      "result":"PASS" if result else "FAIL"
    }

async def ordinary_navigation_back(page,lesson_a,lesson_b):
    await open_chapter(page,lesson_a)
    await open_chapter(page,lesson_b)
    before=await page.evaluate("""()=>({chapter:history.state?.view?.chapter||window.__invLastStudyView?.view?.chapter||null,overlay:!!history.state?.v188VisualOverlay})""")
    await page.evaluate("history.back()")
    try:
        await page.wait_for_function("lesson=>history.state?.view?.chapter===lesson",lesson_a,timeout=4000)
        await wait_frames(page,3)
    except Exception:
        pass
    after=await page.evaluate("""()=>({chapter:history.state?.view?.chapter||window.__invLastStudyView?.view?.chapter||null,overlay:!!history.state?.v188VisualOverlay})""")
    return {"from":before,"to":after,"expected":lesson_a,"pass":before["chapter"]==lesson_b and after["chapter"]==lesson_a and not after["overlay"]}

async def prepare_page(browser,html):
    context=await browser.new_context(viewport={"width":360,"height":800},device_scale_factor=1,is_mobile=True,has_touch=True)
    page=await context.new_page()
    errors=[]
    page.on("pageerror",lambda e:errors.append("PAGEERROR "+str(e)))
    page.on("console",lambda m:errors.append("CONSOLE "+m.type+" "+m.text) if m.type=="error" else None)
    await page.set_content(html,wait_until="load")
    await wait_frames(page,3)
    return context,page,errors

async def targeted(browser,html,out):
    context,page,errors=await prepare_page(browser,html)
    inv=await inventory(page)
    p2l={x["id"]:x["lesson"] for x in inv}
    rows=[]
    # Deliberately history-rich ordering and repeated explicit closes.
    order=TARGETS+[TARGETS[0],TARGETS[2],TARGETS[1]]
    for plate in order:
        lesson=p2l[plate]
        for method in ("click","touch","enter","space"):
            for i in range(1,4):
                rows.append(await activation_record(page,plate,lesson,method,"explicit",i,"targeted-repeat"))
        for i,close in enumerate(("browser_back","explicit","browser_back","explicit","android_back"),1):
            rows.append(await activation_record(page,plate,lesson,"click",close,i,"targeted-back-alternation"))
    nav=await ordinary_navigation_back(page,p2l[TARGETS[1]],p2l[TARGETS[3]])
    passed=all(r["result"]=="PASS" for r in rows) and nav["pass"]
    data={"pass":passed,"rows":rows,"ordinary_navigation_back":nav,"runtime_errors":errors}
    (out/"OVERLAY_HISTORY_TARGETED_AUDIT.json").write_text(json.dumps(data,ensure_ascii=False,indent=2),encoding="utf-8")
    fields=["phase","figure_id","chapter","activation_method","close_method","iteration","pre_scroll_x","pre_scroll_y","post_scroll_x","post_scroll_y","delta_x","delta_y","pre_history_length","overlay_history_length","post_close_history_length","overlay_count","overlay_identity","overlay_marker_while_open","overlay_marker_after_close","chapter_before","chapter_after","chapter_preserved","runtime_plate_count","result"]
    with (out/"OVERLAY_HISTORY_TARGETED_AUDIT.csv").open("w",encoding="utf-8",newline="") as f:
        w=csv.DictWriter(f,fieldnames=fields,extrasaction="ignore");w.writeheader();w.writerows(rows)
    await context.close()
    return data

async def full_run(browser,html,label,out):
    context,page,errors=await prepare_page(browser,html)
    inv=await inventory(page)
    ids=[x["id"] for x in inv];counts=Counter(ids)
    bylesson=defaultdict(list)
    for x in inv: bylesson[x["lesson"]].append(x["id"])
    rows=[];matrix=[]
    lesson_order=[]
    for lesson in await page.locator("button[data-open-chapter]").evaluate_all("(els)=>els.map(e=>e.dataset.openChapter)"):
        if lesson in bylesson: lesson_order.append(lesson)
    close_map={"click":"explicit","touch":"browser_back","enter":"explicit","space":"browser_back"}
    for lesson in lesson_order:
        await open_chapter(page,lesson)
        for plate in bylesson[lesson]:
            place=await placement(page,plate)
            figrows=[]
            for method in ("click","touch","enter","space"):
                rec=await activation_record(page,plate,lesson,method,close_map[method],1,"full-"+label)
                rows.append(rec);figrows.append(rec)
            ok=all(r["result"]=="PASS" for r in figrows)
            ok &= place["default_visible"] and not place["inside_closed_details"]
            ok &= place["rendered_instance_count"]==1 and place["horizontal_overflow_px"]==0 and place["document_horizontal_overflow_px"]==0
            if place["placement_mode"]=="contextual": ok &= place["candidate_score"]>=THRESHOLD
            matrix.append({"run":label,"figure_id":plate,"chapter":lesson,**place,
                           "explicit_close_scroll_failures":sum(r["close_method"]=="explicit" and (r["delta_x"]!=0 or r["delta_y"]!=0) for r in figrows),
                           "back_close_scroll_failures":sum(r["close_method"]=="browser_back" and (r["delta_x"]!=0 or r["delta_y"]!=0) for r in figrows),
                           "wrong_chapter_returns":sum(not r["chapter_preserved"] for r in figrows),
                           "focus_failures":sum(r["post_focus"]["hidden"] or r["post_focus"]["plate"]!=plate for r in figrows),
                           "overlay_identity_failures":sum(not r["overlay_identity"] for r in figrows),
                           "overlay_count_failures":sum(r["overlay_count"]!=1 for r in figrows),
                           "result":"PASS" if ok else "FAIL"})
    # Revisit every chapter and ensure runtime figure duplication has not accumulated.
    revisit={}
    for lesson in lesson_order:
        await open_chapter(page,lesson)
        revisit[lesson]={}
        for plate in bylesson[lesson]:
            revisit[lesson][plate]=await page.locator(f'figure[data-v188-plate="{plate}"]').count()
    nav=await ordinary_navigation_back(page,lesson_order[0],lesson_order[1])
    summary={
      "run":label,
      "inventory_occurrences":len(ids),"unique_plate_ids":len(counts),
      "duplicate_plate_ids":{k:v for k,v in counts.items() if v>1},
      "figures_checked":len(matrix),"activations_checked":len(rows),
      "explicit_close_scroll_failures":sum(m["explicit_close_scroll_failures"] for m in matrix),
      "back_close_scroll_failures":sum(m["back_close_scroll_failures"] for m in matrix),
      "wrong_chapter_returns":sum(m["wrong_chapter_returns"] for m in matrix),
      "focus_failures":sum(m["focus_failures"] for m in matrix),
      "overlay_identity_failures":sum(m["overlay_identity_failures"] for m in matrix),
      "multiple_overlay_failures":sum(m["overlay_count_failures"] for m in matrix),
      "runtime_duplicates":sum(v!=1 for d in revisit.values() for v in d.values()),
      "horizontal_overflow_failures":sum(max(m["horizontal_overflow_px"],m["document_horizontal_overflow_px"])>0 for m in matrix),
      "closed_details_placements":sum(bool(m["inside_closed_details"]) for m in matrix),
      "below_threshold_contextual_placements":sum(m["placement_mode"]=="contextual" and m["candidate_score"]<3 for m in matrix),
      "ordinary_lesson_back":nav,
      "failed_figures":[m["figure_id"] for m in matrix if m["result"]!="PASS"],
      "runtime_errors":errors,
    }
    summary["pass"]=all([
      summary["inventory_occurrences"]==EXPECTED_INVENTORY,summary["unique_plate_ids"]==EXPECTED_INVENTORY,
      not summary["duplicate_plate_ids"],summary["figures_checked"]==EXPECTED_INVENTORY,
      summary["explicit_close_scroll_failures"]==0,summary["back_close_scroll_failures"]==0,
      summary["wrong_chapter_returns"]==0,summary["focus_failures"]==0,
      summary["overlay_identity_failures"]==0,summary["multiple_overlay_failures"]==0,
      summary["runtime_duplicates"]==0,summary["horizontal_overflow_failures"]==0,
      summary["closed_details_placements"]==0,summary["below_threshold_contextual_placements"]==0,
      nav["pass"],not summary["failed_figures"]
    ])
    (out/f"OVERLAY_HISTORY_RUN_{label}.json").write_text(json.dumps({"summary":summary,"activations":rows,"matrix":matrix,"revisit":revisit},ensure_ascii=False,indent=2),encoding="utf-8")
    await context.close()
    return summary,rows,matrix

def normalize_rows(rows):
    return sorted([{k:v for k,v in r.items() if k!="phase"} for r in rows],key=lambda x:(x["figure_id"],x["activation_method"],x["close_method"],x["iteration"]))

async def run_all(args):
    html_path=Path(args.html);out=Path(args.out);out.mkdir(parents=True,exist_ok=True)
    html=html_path.read_text(encoding="utf-8")
    async with async_playwright() as p:
        browser=await p.chromium.launch(headless=True,executable_path=args.chromium,args=["--no-sandbox","--disable-dev-shm-usage"])
        targeted_data=await targeted(browser,html,out)
        if not targeted_data["pass"]:
            await browser.close()
            return 2
        a,ar,am=await full_run(browser,html,"A",out)
        b,br,bm=await full_run(browser,html,"B",out)
        repeat=normalize_rows(ar)==normalize_rows(br)
        matrix_repeat=sorted([{k:v for k,v in x.items() if k!="run"} for x in am],key=lambda x:x["figure_id"])==sorted([{k:v for k,v in x.items() if k!="run"} for x in bm],key=lambda x:x["figure_id"])
        await browser.close()

    focus={
      "run_a_focus_failures":a["focus_failures"],
      "run_b_focus_failures":b["focus_failures"],
      "hidden_content_focus_failures":a["focus_failures"]+b["focus_failures"],
    }
    (out/"OVERLAY_HISTORY_FOCUS_AUDIT.json").write_text(json.dumps(focus,indent=2),encoding="utf-8")
    fields=sorted({k for r in am for k in r})
    with (out/"OVERLAY_HISTORY_82_FIGURE_MATRIX.csv").open("w",encoding="utf-8",newline="") as f:
        w=csv.DictWriter(f,fieldnames=fields);w.writeheader();w.writerows(am)

    candidate_pass=targeted_data["pass"] and a["pass"] and b["pass"] and repeat and matrix_repeat
    identity=Path(args.identity).read_text(encoding="utf-8") if args.identity else ""
    integrity=Path(args.integrity).read_text(encoding="utf-8") if args.integrity else ""
    report=f"""# INVERTEBRATA — Overlay History / Scroll-Return Remediation

## A. ROOT CAUSE CONFIRMATION
Explicit Close previously hid the visual overlay before calling history.back(). When popstate arrived, the central router no longer saw an open overlay and could enter ordinary lesson-history restoration. The remediation keeps the overlay active until the overlay-owned history transition is consumed.

## B. IMPLEMENTATION
Changed production transformation: ci/reconcile_v188.py only.
The generated application now captures visual return state, pushes an explicit v188VisualOverlay transient history marker, defers explicit Close finalization until popstate, and consumes overlay unwind before ordinary lesson history routing.

## C. HISTORY ROUTING
- overlay history marker: v188VisualOverlay
- overlay-unwind detection: visualHistoryEntryActive + visualHistoryUnwindPending/open overlay
- normal-navigation bypass: early return from central popstate router
- explicit Close: requests history.back without hiding first
- browser Back: same central overlay finalizer
- ordinary lesson Back regression: {'PASS' if a['ordinary_lesson_back']['pass'] and b['ordinary_lesson_back']['pass'] else 'FAIL'}

## D. SCROLL / FOCUS RETURN
Exact pre-overlay scrollX/scrollY are restored after overlay DOM/CSS teardown using requestAnimationFrame scheduling. Focus returns to the original trigger or is reacquired by stable data-v188-plate.

## E. TARGETED REPRODUCTION
Targeted history-rich audit: {'PASS' if targeted_data['pass'] else 'FAIL'}
Five representative targets: {', '.join(TARGETS)}

## F. FULL 82-FIGURE RUN A
{json.dumps(a,sort_keys=True)}

## G. FULL 82-FIGURE RUN B
{json.dumps(b,sort_keys=True)}

## H. SOURCE INTEGRITY
{integrity}

## I. CANDIDATE IDENTITY
{identity}

## J. DECISION
{'OVERLAY HISTORY/SCROLL REMEDIATION — PASS · ELIGIBLE FOR PROMOTION REVIEW' if candidate_pass else 'OVERLAY HISTORY/SCROLL REMEDIATION — FAIL · PROMOTION BLOCKED'}
"""
    (out/"OVERLAY_HISTORY_REMEDIATION_REPORT.md").write_text(report,encoding="utf-8")
    comparison={"targeted_pass":targeted_data["pass"],"run_a_pass":a["pass"],"run_b_pass":b["pass"],"activation_results_identical":repeat,"matrix_results_identical":matrix_repeat,"candidate_pass":candidate_pass}
    (out/"OVERLAY_HISTORY_REPEATABILITY.json").write_text(json.dumps(comparison,indent=2),encoding="utf-8")
    return 0 if candidate_pass else 2

def main():
    ap=argparse.ArgumentParser()
    ap.add_argument("--html",required=True);ap.add_argument("--out",required=True);ap.add_argument("--chromium",required=True)
    ap.add_argument("--identity");ap.add_argument("--integrity")
    args=ap.parse_args()
    raise SystemExit(asyncio.run(run_all(args)))
if __name__=="__main__":main()
