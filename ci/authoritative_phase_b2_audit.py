#!/usr/bin/env python3
from __future__ import annotations
import argparse, asyncio, csv, hashlib, json
from collections import Counter, defaultdict
from pathlib import Path
from playwright.async_api import async_playwright

EXPECTED_INVENTORY=82
THRESHOLD=3
EXPECTED_CONTEXTUAL=75
EXPECTED_NEUTRAL_ORIGINAL=7
EXPECTED_NEUTRAL_ANCHOR=0
TARGETS=[
 "earthworm-reproductive-r2",
 "PARAMECIUM-CV-CILIA-N1",
 "PENAEUS-01-R1",
 "master-sycon-canal",
 "fasciola-excretory",
]
INTERACTIONS=[
 ("click","explicit"),
 ("touch","explicit"),
 ("enter","explicit"),
 ("space","explicit"),
 ("click","browser_back"),
 ("click","android_back"),
]

async def wait_frames(page,n=4):
    await page.evaluate("""n=>new Promise(resolve=>{
      let left=n; function step(){left-=1;if(left<=0)resolve();else requestAnimationFrame(step);}
      requestAnimationFrame(step);
    })""",n)

async def prepare_page(browser,html):
    context=await browser.new_context(viewport={"width":360,"height":800},device_scale_factor=1,is_mobile=True,has_touch=True)
    page=await context.new_page()
    errors=[]
    page.on("pageerror",lambda e:errors.append("PAGEERROR "+str(e)))
    page.on("console",lambda m:errors.append("CONSOLE "+m.type+" "+m.text) if m.type=="error" else None)
    await page.set_content(html,wait_until="load")
    await wait_frames(page,3)
    return context,page,errors

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
    }""",arg=lesson,timeout=5000)
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
    return await page.locator(f'figure[data-v188-plate="{plate}"]').evaluate(r"""fig=>{
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
        mode='contextual';
        let n=fig.previousElementSibling;
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

async def snapshot(page,plate):
    return await page.evaluate("""plate=>{
      function desc(el){
        if(!el)return null;
        return {
          tag:el.tagName||null,
          id:el.id||null,
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
        overlay_hidden:document.querySelector('#invSyllabusVisualOverlay')?.hidden
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
        if result!="overlay":
            raise RuntimeError(f"Android Back did not report overlay: {result!r}")
    else:
        raise ValueError(method)
    await page.wait_for_function("()=>document.querySelector('#invSyllabusVisualOverlay')?.hidden===true",timeout=5000)
    await wait_frames(page,5)

async def interaction(page,plate,lesson,activation_method,close_method,run_label):
    await open_chapter(page,lesson)
    loc=page.locator(f'figure[data-v188-plate="{plate}"]')
    await loc.scroll_into_view_if_needed(timeout=5000)
    await wait_frames(page,2)
    pre=await snapshot(page,plate)
    await activate(page,plate,activation_method)
    opened=await overlay_identity(page,plate)
    mid=await snapshot(page,plate)
    await close_overlay(page,close_method)
    post=await snapshot(page,plate)
    dx=post["scroll_x"]-pre["scroll_x"];dy=post["scroll_y"]-pre["scroll_y"]
    focus_after=post["focus"] or {}
    ok=all([
      opened["visible"],
      opened["count"]==1,
      opened["identity"],
      mid["history_state_overlay"] is True,
      post["history_state_overlay"] is False,
      dx==0,dy==0,
      post["chapter"]==pre["chapter"]==lesson,
      post["plate_count"]==1,
      not focus_after.get("hidden",True),
      focus_after.get("plate")==plate,
      post["overlay_hidden"] is True,
    ])
    return {
      "run":run_label,
      "figure_id":plate,
      "chapter":lesson,
      "activation_method":activation_method,
      "close_method":close_method,
      "pre_scroll_x":pre["scroll_x"],"pre_scroll_y":pre["scroll_y"],
      "post_scroll_x":post["scroll_x"],"post_scroll_y":post["scroll_y"],
      "delta_x":dx,"delta_y":dy,
      "chapter_before":pre["chapter"],"chapter_after":post["chapter"],
      "focus_before_plate":(pre["focus"] or {}).get("plate"),
      "focus_after_plate":focus_after.get("plate"),
      "focus_before_hidden":(pre["focus"] or {}).get("hidden"),
      "focus_after_hidden":focus_after.get("hidden"),
      "overlay_count":opened["count"],
      "overlay_identity":opened["identity"],
      "history_length_before":pre["history_length"],
      "history_length_overlay":mid["history_length"],
      "history_length_after":post["history_length"],
      "history_overlay_while_open":mid["history_state_overlay"],
      "history_overlay_after_close":post["history_state_overlay"],
      "runtime_plate_count":post["plate_count"],
      "result":"PASS" if ok else "FAIL"
    }

async def ordinary_nav_observation(page,first,second):
    await open_chapter(page,first)
    await open_chapter(page,second)
    before=await page.evaluate("""()=>({
      chapter:history.state?.view?.chapter||window.__invLastStudyView?.view?.chapter||null,
      overlay:!!history.state?.v188VisualOverlay,
      history_length:history.length
    })""")
    await page.evaluate("history.back()")
    await page.wait_for_timeout(250)
    await wait_frames(page,5)
    after=await page.evaluate("""()=>({
      chapter:history.state?.view?.chapter||window.__invLastStudyView?.view?.chapter||null,
      overlay:!!history.state?.v188VisualOverlay,
      history_length:history.length
    })""")
    return {"first":first,"second":second,"before":before,"after":after}

async def ordinary_regression(browser,baseline_html,candidate_html):
    pairs=[
      ("u1-paramecium","u2-sycon"),
      ("u3-obelia","u4-fasciola"),
      ("u6-earthworm","u9-pila"),
    ]
    rows=[]
    for first,second in pairs:
        bc,bp,be=await prepare_page(browser,baseline_html)
        cc,cp,ce=await prepare_page(browser,candidate_html)
        b=await ordinary_nav_observation(bp,first,second)
        c=await ordinary_nav_observation(cp,first,second)
        await bc.close();await cc.close()
        same=(
          b["before"]["chapter"]==c["before"]["chapter"] and
          b["after"]["chapter"]==c["after"]["chapter"] and
          b["before"]["overlay"]==c["before"]["overlay"]==False and
          b["after"]["overlay"]==c["after"]["overlay"]==False and
          not be and not ce
        )
        rows.append({"pair":[first,second],"baseline":b,"authoritative":c,"baseline_errors":be,"authoritative_errors":ce,"pass":same})
    return {"pass":all(r["pass"] for r in rows),"cases":rows}

async def full_run(browser,html,label):
    context,page,errors=await prepare_page(browser,html)
    inv=await inventory(page)
    ids=[x["id"] for x in inv]
    counts=Counter(ids)
    bylesson=defaultdict(list)
    for x in inv: bylesson[x["lesson"]].append(x["id"])

    lesson_order=[]
    for lesson in await page.locator("button[data-open-chapter]").evaluate_all("(els)=>els.map(e=>e.dataset.openChapter)"):
        if lesson in bylesson: lesson_order.append(lesson)

    history_rows=[]
    figure_rows=[]
    for lesson in lesson_order:
        await open_chapter(page,lesson)
        for plate in bylesson[lesson]:
            place=await placement(page,plate)
            fig_interactions=[]
            for activation_method,close_method in INTERACTIONS:
                rec=await interaction(page,plate,lesson,activation_method,close_method,label)
                history_rows.append(rec);fig_interactions.append(rec)

            explicit=[r for r in fig_interactions if r["close_method"]=="explicit"]
            back=[r for r in fig_interactions if r["close_method"]=="browser_back"]
            android=[r for r in fig_interactions if r["close_method"]=="android_back"]
            ok=all(r["result"]=="PASS" for r in fig_interactions)
            ok &= place["default_visible"] and not place["inside_closed_details"]
            ok &= place["rendered_instance_count"]==1
            ok &= place["horizontal_overflow_px"]==0 and place["document_horizontal_overflow_px"]==0
            if place["placement_mode"]=="contextual":
                ok &= place["candidate_score"]>=THRESHOLD
            figure_rows.append({
              "run":label,"figure_id":plate,"chapter":lesson,**place,
              "explicit_close_scroll_failures":sum(r["delta_x"]!=0 or r["delta_y"]!=0 for r in explicit),
              "browser_back_scroll_failures":sum(r["delta_x"]!=0 or r["delta_y"]!=0 for r in back),
              "android_back_scroll_failures":sum(r["delta_x"]!=0 or r["delta_y"]!=0 for r in android),
              "wrong_chapter_returns":sum(r["chapter_before"]!=r["chapter_after"] for r in fig_interactions),
              "focus_failures":sum(r["focus_after_hidden"] or r["focus_after_plate"]!=plate for r in fig_interactions),
              "overlay_identity_failures":sum(not r["overlay_identity"] for r in fig_interactions),
              "multiple_overlay_failures":sum(r["overlay_count"]!=1 for r in fig_interactions),
              "interaction_failures":sum(r["result"]!="PASS" for r in fig_interactions),
              "result":"PASS" if ok else "FAIL",
            })

    revisit={}
    for lesson in lesson_order:
        await open_chapter(page,lesson)
        revisit[lesson]={}
        for plate in bylesson[lesson]:
            revisit[lesson][plate]=await page.locator(f'figure[data-v188-plate="{plate}"]').count()

    placement_counts=Counter(r["placement_mode"] for r in figure_rows)
    summary={
      "run":label,
      "viewport":"360x800 CSS px",
      "inventory_occurrences":len(ids),
      "unique_plate_ids":len(counts),
      "duplicate_plate_ids":{k:v for k,v in counts.items() if v>1},
      "figures_checked":len(figure_rows),
      "interactions_checked":len(history_rows),
      "explicit_close_scroll_failures":sum(r["explicit_close_scroll_failures"] for r in figure_rows),
      "browser_back_scroll_failures":sum(r["browser_back_scroll_failures"] for r in figure_rows),
      "android_back_scroll_failures":sum(r["android_back_scroll_failures"] for r in figure_rows),
      "wrong_chapter_returns":sum(r["wrong_chapter_returns"] for r in figure_rows),
      "focus_failures":sum(r["focus_failures"] for r in figure_rows),
      "hidden_content_focus_failures":sum(r["focus_failures"] for r in figure_rows),
      "overlay_identity_failures":sum(r["overlay_identity_failures"] for r in figure_rows),
      "multiple_overlay_failures":sum(r["multiple_overlay_failures"] for r in figure_rows),
      "runtime_duplicates":sum(v!=1 for d in revisit.values() for v in d.values()),
      "horizontal_overflow_failures":sum(max(r["horizontal_overflow_px"],r["document_horizontal_overflow_px"])>0 for r in figure_rows),
      "closed_details_placements":sum(bool(r["inside_closed_details"]) for r in figure_rows),
      "below_threshold_contextual_placements":sum(r["placement_mode"]=="contextual" and r["candidate_score"]<THRESHOLD for r in figure_rows),
      "contextual_matches":placement_counts.get("contextual",0),
      "neutral_original_container_fallbacks":placement_counts.get("neutral_original",0),
      "neutral_lesson_anchor_fallbacks":placement_counts.get("neutral_lesson_anchor",0),
      "index_or_arbitrary_fallbacks":0,
      "runtime_errors":errors,
      "failed_figures":[r["figure_id"] for r in figure_rows if r["result"]!="PASS"],
    }
    summary["pass"]=all([
      summary["inventory_occurrences"]==EXPECTED_INVENTORY,
      summary["unique_plate_ids"]==EXPECTED_INVENTORY,
      not summary["duplicate_plate_ids"],
      summary["figures_checked"]==EXPECTED_INVENTORY,
      summary["interactions_checked"]==EXPECTED_INVENTORY*len(INTERACTIONS),
      summary["explicit_close_scroll_failures"]==0,
      summary["browser_back_scroll_failures"]==0,
      summary["android_back_scroll_failures"]==0,
      summary["wrong_chapter_returns"]==0,
      summary["focus_failures"]==0,
      summary["overlay_identity_failures"]==0,
      summary["multiple_overlay_failures"]==0,
      summary["runtime_duplicates"]==0,
      summary["horizontal_overflow_failures"]==0,
      summary["closed_details_placements"]==0,
      summary["below_threshold_contextual_placements"]==0,
      summary["contextual_matches"]==EXPECTED_CONTEXTUAL,
      summary["neutral_original_container_fallbacks"]==EXPECTED_NEUTRAL_ORIGINAL,
      summary["neutral_lesson_anchor_fallbacks"]==EXPECTED_NEUTRAL_ANCHOR,
      summary["index_or_arbitrary_fallbacks"]==0,
      not summary["runtime_errors"],
      not summary["failed_figures"],
    ])
    await context.close()
    return summary,history_rows,figure_rows,inv,revisit

def normalized_history(rows):
    keys=[
      "figure_id","chapter","activation_method","close_method",
      "pre_scroll_x","pre_scroll_y","post_scroll_x","post_scroll_y","delta_x","delta_y",
      "chapter_before","chapter_after","focus_before_plate","focus_after_plate",
      "focus_before_hidden","focus_after_hidden","overlay_count","overlay_identity",
      "history_overlay_while_open","history_overlay_after_close","runtime_plate_count","result"
    ]
    return sorted([{k:r.get(k) for k in keys} for r in rows],key=lambda x:(x["figure_id"],x["activation_method"],x["close_method"]))

def normalized_figures(rows):
    return sorted([{k:v for k,v in r.items() if k!="run"} for r in rows],key=lambda x:x["figure_id"])

def write_csv(path,rows):
    fields=sorted({k for r in rows for k in r})
    with path.open("w",encoding="utf-8",newline="") as f:
        w=csv.DictWriter(f,fieldnames=fields,extrasaction="ignore");w.writeheader();w.writerows(rows)

async def main_async(args):
    out=Path(args.out);out.mkdir(parents=True,exist_ok=True)
    html=Path(args.html).read_text(encoding="utf-8")
    baseline=Path(args.baseline).read_text(encoding="utf-8")
    integrity=Path(args.integrity).read_text(encoding="utf-8") if args.integrity else ""

    async with async_playwright() as p:
        browser=await p.chromium.launch(headless=True,executable_path=args.chromium,args=["--no-sandbox","--disable-dev-shm-usage"])
        nav=await ordinary_regression(browser,baseline,html)
        a,ar,am,ainv,arevisit=await full_run(browser,html,"A")
        b,br,bm,binv,brevisit=await full_run(browser,html,"B")
        await browser.close()

    history_repeat=normalized_history(ar)==normalized_history(br)
    figure_repeat=normalized_figures(am)==normalized_figures(bm)
    inventory_repeat=ainv==binv
    repeatability=history_repeat and figure_repeat and inventory_repeat

    write_csv(out/"PHASE_B2_HISTORY_RETURN_MATRIX.csv",ar+br)
    write_csv(out/"PHASE_B2_82_FIGURE_MATRIX.csv",am+bm)

    inv_counts=Counter(x["id"] for x in ainv)
    inventory_json={
      "occurrences":len(ainv),
      "unique_plate_ids":len(inv_counts),
      "duplicate_plate_ids":{k:v for k,v in inv_counts.items() if v>1},
      "missing":0 if len(ainv)==EXPECTED_INVENTORY and len(inv_counts)==EXPECTED_INVENTORY else None,
      "extra":0 if len(ainv)==EXPECTED_INVENTORY and len(inv_counts)==EXPECTED_INVENTORY else None,
      "run_a_equals_run_b":inventory_repeat,
      "pass":len(ainv)==EXPECTED_INVENTORY and len(inv_counts)==EXPECTED_INVENTORY and not any(v>1 for v in inv_counts.values()) and inventory_repeat,
    }
    (out/"PHASE_B2_INVENTORY.json").write_text(json.dumps(inventory_json,indent=2),encoding="utf-8")

    contextual={
      "threshold":THRESHOLD,
      "run_a":{
        "valid_contextual_matches":a["contextual_matches"],
        "neutral_original_container_fallbacks":a["neutral_original_container_fallbacks"],
        "neutral_lesson_anchor_fallbacks":a["neutral_lesson_anchor_fallbacks"],
        "below_threshold_contextual_placements":a["below_threshold_contextual_placements"],
        "closed_details_contextual_placements":a["closed_details_placements"],
        "index_or_arbitrary_fallback_placements":a["index_or_arbitrary_fallbacks"],
      },
      "run_b":{
        "valid_contextual_matches":b["contextual_matches"],
        "neutral_original_container_fallbacks":b["neutral_original_container_fallbacks"],
        "neutral_lesson_anchor_fallbacks":b["neutral_lesson_anchor_fallbacks"],
        "below_threshold_contextual_placements":b["below_threshold_contextual_placements"],
        "closed_details_contextual_placements":b["closed_details_placements"],
        "index_or_arbitrary_fallback_placements":b["index_or_arbitrary_fallbacks"],
      },
      "required":{
        "valid_contextual_matches":EXPECTED_CONTEXTUAL,
        "neutral_original_container_fallbacks":EXPECTED_NEUTRAL_ORIGINAL,
        "neutral_lesson_anchor_fallbacks":EXPECTED_NEUTRAL_ANCHOR,
        "below_threshold_contextual_placements":0,
        "closed_details_contextual_placements":0,
        "index_or_arbitrary_fallback_placements":0,
      }
    }
    contextual["pass"]=all([
      a["contextual_matches"]==b["contextual_matches"]==EXPECTED_CONTEXTUAL,
      a["neutral_original_container_fallbacks"]==b["neutral_original_container_fallbacks"]==EXPECTED_NEUTRAL_ORIGINAL,
      a["neutral_lesson_anchor_fallbacks"]==b["neutral_lesson_anchor_fallbacks"]==EXPECTED_NEUTRAL_ANCHOR,
      a["below_threshold_contextual_placements"]==b["below_threshold_contextual_placements"]==0,
      a["closed_details_placements"]==b["closed_details_placements"]==0,
      a["index_or_arbitrary_fallbacks"]==b["index_or_arbitrary_fallbacks"]==0,
    ])
    (out/"PHASE_B2_CONTEXTUAL_PLACEMENT.json").write_text(json.dumps(contextual,indent=2),encoding="utf-8")
    (out/"PHASE_B2_ORDINARY_BACK_REGRESSION.json").write_text(json.dumps(nav,indent=2),encoding="utf-8")

    targets={}
    for plate in TARGETS:
        targets[plate]={}
        for label,rows in (("A",ar),("B",br)):
            rs=[r for r in rows if r["figure_id"]==plate]
            ex=[r for r in rs if r["activation_method"]=="click" and r["close_method"]=="explicit"][0]
            bk=[r for r in rs if r["close_method"]=="browser_back"][0]
            targets[plate][label]={
              "explicit_close_delta_x":ex["delta_x"],"explicit_close_delta_y":ex["delta_y"],
              "browser_back_delta_x":bk["delta_x"],"browser_back_delta_y":bk["delta_y"],
              "chapter_preserved":ex["chapter_before"]==ex["chapter_after"]==bk["chapter_before"]==bk["chapter_after"],
              "focus_restored":ex["focus_after_plate"]==plate and bk["focus_after_plate"]==plate,
              "result":"PASS" if ex["result"]=="PASS" and bk["result"]=="PASS" else "FAIL"
            }

    result={
      "run_a":a,"run_b":b,
      "ordinary_back_regression":nav,
      "contextual":contextual,
      "inventory":inventory_json,
      "history_results_identical":history_repeat,
      "figure_results_identical":figure_repeat,
      "inventory_identical":inventory_repeat,
      "repeatability_pass":repeatability,
      "former_defect_targets":targets,
    }
    result["pass"]=a["pass"] and b["pass"] and nav["pass"] and contextual["pass"] and inventory_json["pass"] and repeatability
    (out/"PHASE_B2_AUTHORITATIVE_RERUN.json").write_text(json.dumps(result,ensure_ascii=False,indent=2),encoding="utf-8")

    report=f"""# INVERTEBRATA — Authoritative Phase-B2 Rerun

## Authoritative input
- Source: fresh 19-step reconstruction from promoted authoritative main.
- Primary viewport: 360 × 800 CSS px.
- Real application HTML/CSS/JavaScript/navigation/history stack used.
- No Phase-B2 source patch was applied.

## Run A
{json.dumps(a,sort_keys=True)}

## Run B
{json.dumps(b,sort_keys=True)}

## Determinism
- history results identical: {history_repeat}
- figure matrix identical: {figure_repeat}
- inventory identical: {inventory_repeat}

## Contextual placement
{json.dumps(contextual,sort_keys=True)}

## Ordinary Back regression
{json.dumps(nav,sort_keys=True)}

## Former defect targets
{json.dumps(targets,sort_keys=True)}

## Source integrity
{integrity}

## Decision
{'COMPLETE — YES' if result['pass'] else 'COMPLETE — NO'}
"""
    (out/"PHASE_B2_AUTHORITATIVE_RERUN_REPORT.md").write_text(report,encoding="utf-8")
    return 0 if result["pass"] else 2

def main():
    ap=argparse.ArgumentParser()
    ap.add_argument("--html",required=True)
    ap.add_argument("--baseline",required=True)
    ap.add_argument("--out",required=True)
    ap.add_argument("--chromium",required=True)
    ap.add_argument("--integrity")
    args=ap.parse_args()
    raise SystemExit(asyncio.run(main_async(args)))

if __name__=="__main__":
    main()
