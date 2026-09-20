#!/usr/bin/env python3
from __future__ import annotations
import argparse, asyncio, csv, json
from collections import Counter, defaultdict
from pathlib import Path
from playwright.async_api import async_playwright

BLOCKERS={
 "peripatus":{"chapter":"u4-peripatus","mode":"NEUTRAL_ORIGINAL_CONTAINER","heading":None,"old":"Tubicolous"},
 "penaeus-nervous":{"chapter":"u4-penaeus","mode":"NEUTRAL_ORIGINAL_CONTAINER","heading":None,"old":"Appendage roster worth memorising"},
 "penaeus-reproductive":{"chapter":"u4-penaeus","mode":"NEUTRAL_ORIGINAL_CONTAINER","heading":None,"old":"Appendage roster worth memorising"},
 "PENAEUS-08-N1":{"chapter":"u4-penaeus","mode":"NEUTRAL_ORIGINAL_CONTAINER","heading":None,"old":"Names"},
 "PENAEUS-09-R1":{"chapter":"u4-penaeus","mode":"NEUTRAL_ORIGINAL_CONTAINER","heading":None,"old":"Appendage roster worth memorising"},
 "PILA-NER-R1":{"chapter":"u5-pila","mode":"NEUTRAL_ORIGINAL_CONTAINER","heading":None,"old":"Reproduction"},
 "asterias-oral":{"chapter":"u5-asterias","mode":"CONTEXTUAL_VISIBLE_MATCH","heading":"External","old":"Reproduction and repair"},
 "asterias-aboral":{"chapter":"u5-asterias","mode":"CONTEXTUAL_VISIBLE_MATCH","heading":"External","old":"Reproduction and repair"},
 "cephalopod":{"chapter":"u5-cephalopods","mode":"NEUTRAL_ORIGINAL_CONTAINER","heading":None,"old":"Circulatory efficiency"}
}
EXISTING_NEUTRALS={"physalia","earthworm-external-r2","earthworm-digestive-r2","earthworm-reproductive-r2","nereis-circulatory","pest-ipm","gastropod-torsion"}
FROZEN_REVIEWS={"paramecium","PARAMECIUM-CV-CILIA-N1","sycon-spicules","obelia-zooids","master-sycon-canal"}
REGRESSION_ANCHORS={"PENAEUS-01-R1","master-sycon-canal","PARAMECIUM-CV-CILIA-N1","earthworm-reproductive-r2","nereis-circulatory","gastropod-torsion"}

async def frames(page,n=3):
    await page.evaluate("""n=>new Promise(resolve=>{
      let k=n;function step(){k-=1;if(k<=0)resolve();else requestAnimationFrame(step)}
      requestAnimationFrame(step);
    })""",n)

async def prepare(browser,html):
    ctx=await browser.new_context(viewport={"width":360,"height":800},device_scale_factor=1,is_mobile=True,has_touch=True)
    page=await ctx.new_page()
    errors=[]
    page.on("pageerror",lambda e:errors.append("PAGEERROR "+str(e)))
    page.on("console",lambda m:errors.append("CONSOLE "+m.type+" "+m.text) if m.type=="error" else None)
    await page.set_content(html,wait_until="load")
    await frames(page,4)
    return ctx,page,errors

async def open_chapter(page,lesson):
    await page.evaluate("""lesson=>{
      const b=document.querySelector('button[data-open-chapter="'+lesson+'"]');
      if(!b)throw new Error('missing chapter button '+lesson);
      b.click();
    }""",lesson)
    await page.wait_for_function("""lesson=>{
      const s=history.state?.view?.chapter;
      const v=window.__invLastStudyView?.view?.chapter;
      return s===lesson||v===lesson;
    }""",arg=lesson,timeout=5000)
    await frames(page,5)

async def source_inventory(page):
    return await page.evaluate("""() => {
      const t=document.createElement('template'),out=[];
      for(const [lesson,html] of Object.entries(window.ORG_SYSTEM_DIAGRAMS||{})){
        t.innerHTML=html||'';
        for(const f of t.content.querySelectorAll('figure[data-v188-plate]')){
          out.push({
            lesson,id:f.dataset.v188Plate,
            caption:(f.querySelector('figcaption')?.textContent||'').replace(/\s+/g,' ').trim(),
            figure_html:f.outerHTML
          });
        }
      }
      return out;
    }""")

async def figure_row(page,plate,lesson):
    loc=page.locator(f'figure[data-v188-plate="{plate}"]')
    await loc.scroll_into_view_if_needed(timeout=5000)
    await frames(page,3)
    return await loc.evaluate(r"""(fig,lesson)=>{
      const pane=fig.closest('.theory-pane');
      const cap=(fig.querySelector('figcaption')?.innerText||'').replace(/\s+/g,' ').trim();
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
      function clean(x){return (x?.innerText||'').replace(/\s+/g,' ').trim();}
      function heading(x){return clean(x?.querySelector('h4,h3,h5'))||null;}
      const parent=fig.parentElement;
      let mode='UNKNOWN',dest=null;
      if(parent?.classList.contains('v188-neutral-figure-anchor'))mode='NEUTRAL_LESSON_ANCHOR';
      else if(fig.closest('.textbook-visuals'))mode='NEUTRAL_ORIGINAL_CONTAINER';
      else{
        mode='CONTEXTUAL_VISIBLE_MATCH';
        let n=fig.previousElementSibling;
        while(n&&!n.classList?.contains('textbook-subsection'))n=n.previousElementSibling;
        dest=n;
      }
      const sections=Array.from(pane.querySelectorAll('.textbook-detail .textbook-subsection')).filter(sec=>!sec.closest('details:not([open])'));
      let best=null,bestScore=0;
      sections.forEach(sec=>{const sc=score(cap.toLowerCase(),clean(sec).toLowerCase());if(sc>bestScore){bestScore=sc;best=sec}});
      const r=fig.getBoundingClientRect();
      return {
        figure_id:fig.dataset.v188Plate,chapter:lesson,caption:cap,
        placement_mode:mode,destination_heading:heading(dest),
        destination_score:dest?score(cap.toLowerCase(),clean(dest).toLowerCase()):null,
        best_visible_heading:heading(best),best_visible_score:bestScore,threshold:3,
        default_visible:!!(r.width&&r.height)&&!fig.closest('details:not([open])')&&getComputedStyle(fig).display!=='none'&&getComputedStyle(fig).visibility!=='hidden',
        inside_closed_details:!!fig.closest('details:not([open])'),
        horizontal_overflow_px:Math.max(0,Math.ceil(r.right-document.documentElement.clientWidth),Math.ceil(-r.left)),
        document_overflow_px:Math.max(0,document.documentElement.scrollWidth-document.documentElement.clientWidth),
        rendered_count:document.querySelectorAll('figure[data-v188-plate="'+CSS.escape(fig.dataset.v188Plate)+'"]').length,
        remap_marker:fig.dataset.v188ContextualRemap||null
      };
    }""",lesson)

async def collect(browser,html):
    ctx,page,errors=await prepare(browser,html)
    inv=await source_inventory(page)
    bylesson=defaultdict(list)
    for item in inv:bylesson[item["lesson"]].append(item)
    rows=[]
    for lesson,items in bylesson.items():
        await open_chapter(page,lesson)
        for item in items:rows.append(await figure_row(page,item["id"],lesson))
    await ctx.close()
    return inv,rows,errors

async def overlay_state(page,plate):
    return await page.evaluate("""plate=>{
      const fig=document.querySelector('figure[data-v188-plate="'+CSS.escape(plate)+'"]');
      const src=fig?.querySelector('svg,img');
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

async def snapshot(page,plate):
    return await page.evaluate("""plate=>({
      scrollX,scrollY,
      chapter:history.state?.view?.chapter||window.__invLastStudyView?.view?.chapter||null,
      focusPlate:document.activeElement?.getAttribute?.('data-v188-plate')||document.activeElement?.closest?.('[data-v188-plate]')?.getAttribute('data-v188-plate')||null,
      focusHidden:!!document.activeElement?.closest?.('[hidden]'),
      count:document.querySelectorAll('figure[data-v188-plate="'+CSS.escape(plate)+'"]').length
    })""",plate)

async def interact(page,plate,lesson,activation,close_method):
    await open_chapter(page,lesson)
    loc=page.locator(f'figure[data-v188-plate="{plate}"]')
    await loc.scroll_into_view_if_needed(timeout=5000);await frames(page,2)
    pre=await snapshot(page,plate)
    if activation=="click":await loc.click(timeout=5000,no_wait_after=True)
    elif activation=="touch":await loc.tap(timeout=5000,no_wait_after=True)
    else:
        await loc.focus()
        await page.keyboard.press("Enter" if activation=="enter" else "Space")
    await page.wait_for_function("()=>{const o=document.querySelector('#invSyllabusVisualOverlay');return o&&!o.hidden}",timeout=5000)
    await frames(page,2)
    opened=await overlay_state(page,plate)
    if close_method=="explicit":await page.locator("#invSyllabusVisualClose").click(timeout=5000,no_wait_after=True)
    else:await page.evaluate("history.back()")
    await page.wait_for_function("()=>document.querySelector('#invSyllabusVisualOverlay')?.hidden===true",timeout=5000)
    await frames(page,5)
    post=await snapshot(page,plate)
    ok=(opened["visible"] and opened["count"]==1 and opened["identity"] and
        post["scrollX"]==pre["scrollX"] and post["scrollY"]==pre["scrollY"] and
        post["chapter"]==pre["chapter"]==lesson and post["focusPlate"]==plate and
        not post["focusHidden"] and post["count"]==1)
    return {
      "figure_id":plate,"chapter":lesson,"activation":activation,"close_method":close_method,
      "pre_scroll_x":pre["scrollX"],"pre_scroll_y":pre["scrollY"],
      "post_scroll_x":post["scrollX"],"post_scroll_y":post["scrollY"],
      "delta_x":post["scrollX"]-pre["scrollX"],"delta_y":post["scrollY"]-pre["scrollY"],
      "overlay_count":opened["count"],"overlay_identity":opened["identity"],
      "chapter_preserved":post["chapter"]==pre["chapter"]==lesson,
      "focus_restored":post["focusPlate"]==plate and not post["focusHidden"],
      "runtime_count":post["count"],"result":"PASS" if ok else "FAIL"
    }

async def interaction_audit(browser,html,inv,out):
    p2l={x["id"]:x["lesson"] for x in inv}
    ctx,page,errors=await prepare(browser,html)
    rows=[]
    for plate in list(BLOCKERS)+sorted(REGRESSION_ANCHORS):
        lesson=p2l[plate]
        for activation in ("click","touch","enter","space"):
            rows.append(await interact(page,plate,lesson,activation,"explicit"))
        rows.append(await interact(page,plate,lesson,"click","browser_back"))
    for item in inv:
        rows.append(await interact(page,item["id"],item["lesson"],"click","explicit"))
    await ctx.close()
    fields=list(rows[0])
    with (out/"TARGETED_CONTEXTUAL_INTERACTION_AUDIT.csv").open("w",encoding="utf-8",newline="") as f:
        w=csv.DictWriter(f,fieldnames=fields);w.writeheader();w.writerows(rows)
    s={
      "records":len(rows),"failures":sum(r["result"]!="PASS" for r in rows),
      "scroll_failures":sum(r["delta_x"]!=0 or r["delta_y"]!=0 for r in rows),
      "overlay_identity_failures":sum(not r["overlay_identity"] for r in rows),
      "multiple_overlay_failures":sum(r["overlay_count"]!=1 for r in rows),
      "chapter_return_failures":sum(not r["chapter_preserved"] for r in rows),
      "focus_failures":sum(not r["focus_restored"] for r in rows),
      "runtime_duplicate_failures":sum(r["runtime_count"]!=1 for r in rows),
      "runtime_errors":errors
    }
    s["pass"]=s["failures"]==0 and not errors
    (out/"TARGETED_CONTEXTUAL_INTERACTION_AUDIT.json").write_text(json.dumps(s,indent=2),encoding="utf-8")
    return s

async def screenshots(browser,html,inv,out):
    p2l={x["id"]:x["lesson"] for x in inv}
    ctx,page,errors=await prepare(browser,html)
    shots=out/"screenshots";shots.mkdir(exist_ok=True)
    for plate in BLOCKERS:
        await open_chapter(page,p2l[plate])
        loc=page.locator(f'figure[data-v188-plate="{plate}"]')
        await loc.scroll_into_view_if_needed();await frames(page,3)
        box=await loc.bounding_box()
        if box:
            sh=await page.evaluate("document.documentElement.scrollHeight")
            y=max(0,box["y"]-260);bottom=min(sh,box["y"]+box["height"]+260)
            await page.screenshot(path=str(shots/f"{plate}.png"),clip={"x":0,"y":y,"width":360,"height":max(240,bottom-y)})
    await ctx.close()
    return errors

async def main_async(args):
    out=Path(args.out);out.mkdir(parents=True,exist_ok=True)
    baseline=Path(args.authoritative).read_text(encoding="utf-8")
    candidate=Path(args.candidate).read_text(encoding="utf-8")
    mapping=json.loads(Path(args.mapping).read_text(encoding="utf-8"))
    async with async_playwright() as p:
        browser=await p.chromium.launch(headless=True,executable_path=args.chromium,args=["--no-sandbox","--disable-dev-shm-usage"])
        base_inv,base_rows,base_errors=await collect(browser,baseline)
        cand_inv,cand_rows,cand_errors=await collect(browser,candidate)
        interaction_summary=await interaction_audit(browser,candidate,cand_inv,out)
        screen_errors=await screenshots(browser,candidate,cand_inv,out)
        await browser.close()

    base_by={r["figure_id"]:r for r in base_rows};cand_by={r["figure_id"]:r for r in cand_rows}
    base_inv_by={x["id"]:x for x in base_inv};cand_inv_by={x["id"]:x for x in cand_inv}
    blocker_ids=set(BLOCKERS)
    inventory={
      "authoritative_occurrences":len(base_inv),"candidate_occurrences":len(cand_inv),
      "authoritative_unique":len(base_inv_by),"candidate_unique":len(cand_inv_by),
      "missing":sorted(set(base_inv_by)-set(cand_inv_by)),
      "extra":sorted(set(cand_inv_by)-set(base_inv_by)),
      "duplicates":{k:v for k,v in Counter(x["id"] for x in cand_inv).items() if v>1},
      "figure_markup_changed":[k for k in base_inv_by if k in cand_inv_by and base_inv_by[k]["figure_html"]!=cand_inv_by[k]["figure_html"]],
      "caption_changed":[k for k in base_inv_by if k in cand_inv_by and base_inv_by[k]["caption"]!=cand_inv_by[k]["caption"]]
    }
    compare_fields=["chapter","placement_mode","destination_heading","destination_score","best_visible_heading","best_visible_score"]
    nonblocker_drift=[]
    for fid in sorted((set(base_by)&set(cand_by))-blocker_ids):
        diffs={k:[base_by[fid].get(k),cand_by[fid].get(k)] for k in compare_fields if base_by[fid].get(k)!=cand_by[fid].get(k)}
        if diffs:nonblocker_drift.append({"figure_id":fid,"diffs":diffs})
    existing_neutral_checks={fid:{
      "baseline":base_by[fid]["placement_mode"],"candidate":cand_by[fid]["placement_mode"],
      "unchanged":base_by[fid]["placement_mode"]==cand_by[fid]["placement_mode"]=="NEUTRAL_ORIGINAL_CONTAINER"
    } for fid in sorted(EXISTING_NEUTRALS)}
    review_checks={fid:{
      "baseline_mode":base_by[fid]["placement_mode"],"candidate_mode":cand_by[fid]["placement_mode"],
      "baseline_destination":base_by[fid]["destination_heading"],"candidate_destination":cand_by[fid]["destination_heading"],
      "unchanged":base_by[fid]["placement_mode"]==cand_by[fid]["placement_mode"] and base_by[fid]["destination_heading"]==cand_by[fid]["destination_heading"]
    } for fid in sorted(FROZEN_REVIEWS)}
    blocker_results={}
    for fid,expected in BLOCKERS.items():
        r=cand_by[fid]
        ok=(r["chapter"]==expected["chapter"] and r["placement_mode"]==expected["mode"] and
            r["destination_heading"]==expected["heading"] and r["default_visible"] and
            not r["inside_closed_details"] and r["horizontal_overflow_px"]==0 and
            r["document_overflow_px"]==0 and r["rendered_count"]==1)
        blocker_results[fid]={
          "old_destination":expected["old"],"candidate_chapter":r["chapter"],
          "candidate_mode":r["placement_mode"],"candidate_destination":r["destination_heading"],
          "destination_score":r["destination_score"],"best_visible_heading":r["best_visible_heading"],
          "best_visible_score":r["best_visible_score"],"default_visible":r["default_visible"],
          "inside_closed_details":r["inside_closed_details"],"horizontal_overflow_px":r["horizontal_overflow_px"],
          "runtime_count":r["rendered_count"],"pass":ok
        }
    dist=Counter(r["placement_mode"] for r in cand_rows)
    structural={
      "contextual_visible_matches":dist.get("CONTEXTUAL_VISIBLE_MATCH",0),
      "neutral_original_container_fallbacks":dist.get("NEUTRAL_ORIGINAL_CONTAINER",0),
      "neutral_lesson_anchor_fallbacks":dist.get("NEUTRAL_LESSON_ANCHOR",0),
      "closed_details":sum(r["inside_closed_details"] for r in cand_rows),
      "hidden":sum(not r["default_visible"] for r in cand_rows),
      "overflow_failures":sum(max(r["horizontal_overflow_px"],r["document_overflow_px"])>0 for r in cand_rows),
      "runtime_duplicates":sum(r["rendered_count"]!=1 for r in cand_rows),
      "runtime_errors":base_errors+cand_errors+screen_errors
    }
    ownership_changes=[
      {"figure_id":fid,"from":base_inv_by[fid]["lesson"],"to":cand_inv_by[fid]["lesson"]}
      for fid in base_inv_by if base_inv_by[fid]["lesson"]!=cand_inv_by[fid]["lesson"]
    ]
    candidate_pass=all([
      inventory["authoritative_occurrences"]==82,inventory["candidate_occurrences"]==82,
      inventory["authoritative_unique"]==82,inventory["candidate_unique"]==82,
      not inventory["missing"],not inventory["extra"],not inventory["duplicates"],
      not inventory["figure_markup_changed"],not inventory["caption_changed"],not nonblocker_drift,
      all(x["unchanged"] for x in existing_neutral_checks.values()),
      all(x["unchanged"] for x in review_checks.values()),
      all(x["pass"] for x in blocker_results.values()),
      ownership_changes==[{"figure_id":"peripatus","from":"u4-modes-life","to":"u4-peripatus"}],
      structural["contextual_visible_matches"]==68,
      structural["neutral_original_container_fallbacks"]==14,
      structural["neutral_lesson_anchor_fallbacks"]==0,
      structural["closed_details"]==0,structural["hidden"]==0,structural["overflow_failures"]==0,
      structural["runtime_duplicates"]==0,not structural["runtime_errors"],interaction_summary["pass"]
    ])
    evidence={
      "pass":candidate_pass,"inventory":inventory,"ownership_changes":ownership_changes,
      "nonblocker_placement_drift":nonblocker_drift,"existing_neutral_checks":existing_neutral_checks,
      "frozen_review_checks":review_checks,"blocker_results":blocker_results,
      "placement_distribution":structural,"interaction_summary":interaction_summary
    }
    (out/"CONTEXTUAL_NINE_BLOCKER_REMEDIATION_AUDIT.json").write_text(json.dumps(evidence,ensure_ascii=False,indent=2),encoding="utf-8")
    fields=["figure_id","chapter","caption","placement_mode","destination_heading","destination_score","best_visible_heading","best_visible_score","threshold","default_visible","inside_closed_details","horizontal_overflow_px","document_overflow_px","rendered_count","remap_marker"]
    with (out/"CONTEXTUAL_NINE_BLOCKER_FINAL_MATRIX.csv").open("w",encoding="utf-8",newline="") as f:
        w=csv.DictWriter(f,fieldnames=fields,extrasaction="ignore");w.writeheader();w.writerows(cand_rows)
    fields2=["figure_id","old_destination","candidate_chapter","candidate_mode","candidate_destination","destination_score","best_visible_heading","best_visible_score","default_visible","inside_closed_details","horizontal_overflow_px","runtime_count","pass"]
    with (out/"CONTEXTUAL_NINE_BLOCKER_BLOCKER_MATRIX.csv").open("w",encoding="utf-8",newline="") as f:
        w=csv.DictWriter(f,fieldnames=fields2);w.writeheader()
        for fid,data in blocker_results.items():w.writerow({"figure_id":fid,**data})

    mapping_by={x["figure_id"]:x for x in mapping["remaps"]}
    lines=["# INVERTEBRATA — Nine BLOCKER-C Contextual Placement Remediation Candidate","",
           "## Scope","",
           "- Exact blockers remapped: 9","- Non-blocker placement changes: 0",
           "- Existing neutral fallback changes: 0","- Frozen REVIEW placement changes: 0",
           "- Academic/caption/label/SVG edits: 0","",
           "## Figure-by-figure remediation",""]
    for fid in BLOCKERS:
        m=mapping_by[fid];b=blocker_results[fid]
        lines += [
          "### "+fid,"",
          "- Current wrong destination: "+m["current_destination"],
          "- Candidate lesson: "+m["target_lesson"],
          "- Candidate placement: "+b["candidate_mode"]+(" -> "+str(b["candidate_destination"]) if b["candidate_destination"] else ""),
          "- Reference anchor: "+m["reference_anchor"],
          "- Academic justification: "+m["reason"],
          "- 360x800: default-visible="+str(b["default_visible"])+"; closed-details="+str(b["inside_closed_details"])+"; overflow="+str(b["horizontal_overflow_px"])+" px; instances="+str(b["runtime_count"]),
          "- Result: "+("PASS" if b["pass"] else "FAIL"),""
        ]
    lines += [
      "## Mechanical invariance","",
      "- Candidate inventory: "+str(inventory["candidate_occurrences"])+"/82; unique "+str(inventory["candidate_unique"])+"/82",
      "- Missing: "+str(len(inventory["missing"]))+"; extra: "+str(len(inventory["extra"]))+"; duplicates: "+str(len(inventory["duplicates"])),
      "- Figure markup changes: "+str(len(inventory["figure_markup_changed"])),
      "- Caption changes: "+str(len(inventory["caption_changed"])),
      "- Non-blocker placement drift: "+str(len(nonblocker_drift)),
      "- Contextual visible matches: "+str(structural["contextual_visible_matches"]),
      "- Neutral original-container fallbacks: "+str(structural["neutral_original_container_fallbacks"]),
      "- Neutral lesson-anchor fallbacks: "+str(structural["neutral_lesson_anchor_fallbacks"]),
      "- Hidden figures: "+str(structural["hidden"]),
      "- Overflow failures: "+str(structural["overflow_failures"]),
      "- Runtime duplicates: "+str(structural["runtime_duplicates"]),
      "- Runtime errors: "+str(len(structural["runtime_errors"])),"",
      "## Candidate decision","",
      "PASS — ELIGIBLE FOR SEPARATE PROMOTION/PIPELINE-INTEGRATION REVIEW" if candidate_pass else "FAIL — PROMOTION BLOCKED","",
      "This candidate is not authoritative source. main, Step-2, accepted-transform manifest, overlay-history implementation and release workflow are not modified by this audit branch."
    ]
    (out/"CONTEXTUAL_NINE_BLOCKER_REMEDIATION_REPORT.md").write_text("\n".join(lines)+"\n",encoding="utf-8")
    raise SystemExit(0 if candidate_pass else 2)

def main():
    ap=argparse.ArgumentParser()
    ap.add_argument("--authoritative",required=True);ap.add_argument("--candidate",required=True)
    ap.add_argument("--mapping",required=True);ap.add_argument("--out",required=True);ap.add_argument("--chromium",required=True)
    args=ap.parse_args()
    raise SystemExit(asyncio.run(main_async(args)))
if __name__=="__main__":main()
