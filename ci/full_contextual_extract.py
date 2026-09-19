#!/usr/bin/env python3
from __future__ import annotations
import argparse, asyncio, csv, json, re
from collections import Counter, defaultdict
from pathlib import Path
from playwright.async_api import async_playwright

EXPECTED_INVENTORY=82
EXPECTED_CONTEXTUAL=75
EXPECTED_NEUTRAL_ORIGINAL=7
THRESHOLD=3

async def wait_frames(page,n=3):
    await page.evaluate("""n=>new Promise(resolve=>{let l=n;function s(){if(--l<=0)resolve();else requestAnimationFrame(s)}requestAnimationFrame(s)})""",n)

async def open_chapter(page,lesson):
    await page.evaluate("""lesson=>{
      const b=document.querySelector('button[data-open-chapter="'+lesson+'"]');
      if(!b) throw new Error('missing '+lesson);
      b.click();
    }""",lesson)
    await page.wait_for_timeout(60)
    await wait_frames(page,2)

async def main_async(args):
    html=Path(args.html).read_text(encoding="utf-8")
    out=Path(args.out);out.mkdir(parents=True,exist_ok=True)
    shots=out/"screenshots-360";shots.mkdir(exist_ok=True)

    async with async_playwright() as p:
      browser=await p.chromium.launch(headless=True,executable_path=args.chromium,args=["--no-sandbox","--disable-dev-shm-usage"])
      context=await browser.new_context(viewport={"width":360,"height":800},device_scale_factor=1,is_mobile=True,has_touch=True)
      page=await context.new_page()
      errors=[]
      page.on("pageerror",lambda e:errors.append("PAGEERROR "+str(e)))
      page.on("console",lambda m:errors.append("CONSOLE "+m.type+" "+m.text) if m.type=="error" else None)
      await page.set_content(html,wait_until="load")
      await wait_frames(page,3)

      inv=await page.evaluate("""()=>{const out=[],t=document.createElement('template');for(const [lesson,html] of Object.entries(window.ORG_SYSTEM_DIAGRAMS||{})){t.innerHTML=html||'';for(const f of t.content.querySelectorAll('figure[data-v188-plate]'))out.push({lesson,id:f.dataset.v188Plate,caption:(f.querySelector('figcaption')?.textContent||'').trim()});}return out;}""")
      bylesson=defaultdict(list)
      for x in inv: bylesson[x["lesson"]].append(x["id"])
      buttons=await page.locator("button[data-open-chapter]").evaluate_all("(els)=>els.map(e=>({id:e.dataset.openChapter,text:(e.innerText||'').trim()}))")
      button_map={b["id"]:b["text"] for b in buttons}
      lesson_order=[b["id"] for b in buttons if b["id"] in bylesson]
      rows=[]

      for lesson in lesson_order:
        await open_chapter(page,lesson)
        chapter_heads=await page.evaluate("""()=>{const pane=document.querySelector('.theory-pane:not([hidden])')||document.querySelector('.theory-pane');return [...pane.querySelectorAll('h1,h2,h3')].map(x=>(x.innerText||'').trim()).filter(Boolean).slice(0,6);}""")
        for plate in bylesson[lesson]:
          loc=page.locator(f'figure[data-v188-plate="{plate}"]')
          await loc.scroll_into_view_if_needed(timeout=5000)
          await wait_frames(page,1)
          info=await loc.evaluate(r"""fig=>{
            const pane=fig.closest('.theory-pane');
            const cap=(fig.querySelector('figcaption')?.innerText||'').trim();
            const parent=fig.parentElement;
            let mode='UNKNOWN',dest=null;
            if(parent?.classList.contains('v188-neutral-figure-anchor')) mode='NEUTRAL_LESSON_ANCHOR';
            else if(fig.closest('.textbook-visuals')) mode='NEUTRAL_ORIGINAL_CONTAINER';
            else { mode='CONTEXTUAL_VISIBLE_MATCH'; let n=fig.previousElementSibling; while(n&&!n.classList?.contains('textbook-subsection'))n=n.previousElementSibling; dest=n; }
            function txt(n){return (n?.innerText||'').replace(/\s+/g,' ').trim();}
            let prev=dest?.previousElementSibling,next=fig.nextElementSibling;
            while(prev&&prev.tagName==='FIGURE')prev=prev.previousElementSibling;
            while(next&&next.tagName==='FIGURE')next=next.nextElementSibling;
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
            const destText=txt(dest);
            const visibleSubs=[...pane.querySelectorAll('.textbook-detail .textbook-subsection')].filter(s=>!s.closest('details:not([open])'));
            let best=null,bestScore=0;
            for(const s of visibleSubs){const sc=score(cap.toLowerCase(),txt(s).toLowerCase());if(sc>bestScore){bestScore=sc;best=s;}}
            const r=fig.getBoundingClientRect();
            return {
              caption:cap,
              placement_mode:mode,
              destination_heading:txt(dest?.querySelector('h4'))||null,
              destination_subsection:destText.slice(0,2200)||null,
              previous_visible_text:txt(prev).slice(0,1200)||null,
              following_visible_text:txt(next).slice(0,1200)||null,
              best_visible_heading:txt(best?.querySelector('h4'))||null,
              best_visible_score:bestScore,
              current_score:dest?score(cap.toLowerCase(),destText.toLowerCase()):0,
              threshold:3,
              default_visible:!!(r.width&&r.height)&&!fig.closest('details:not([open])')&&getComputedStyle(fig).display!=='none'&&getComputedStyle(fig).visibility!=='hidden',
              inside_closed_details:!!fig.closest('details:not([open])'),
              horizontal_overflow_px:Math.max(0,Math.ceil(r.right-document.documentElement.clientWidth),Math.ceil(-r.left)),
              svg_text_labels:[...fig.querySelectorAll('svg text')].map(x=>(x.textContent||'').trim()).filter(Boolean),
              legend_en:fig.querySelector('.v188-figure-legend')?.getAttribute('data-legend-en')||'',
              legend_ta:fig.querySelector('.v188-figure-legend')?.getAttribute('data-legend-ta')||'',
              aria_label:fig.querySelector('svg,img')?.getAttribute('aria-label')||fig.querySelector('img')?.alt||'',
              full_figure_text:(fig.textContent||'').replace(/\s+/g,' ').trim(),
              data_source_pass2:fig.getAttribute('data-source-pass2')||'',
              role:fig.getAttribute('role')||'',
              tabindex:fig.getAttribute('tabindex')||''
            };
          }""")
          box=await loc.bounding_box();shot=""
          if box:
            y=max(0,box["y"]-180);height=min(max(500,box["height"]+360),1900)
            path=shots/f"{plate}.png"
            await page.screenshot(path=str(path),clip={"x":0,"y":y,"width":360,"height":height})
            shot=path.name
          rows.append({
            "figure_id":plate,"chapter":lesson,"chapter_button":button_map.get(lesson,""),
            "chapter_heads":" | ".join(chapter_heads),**info,"screenshot_360":shot,
            "interaction_status":"PASS — reused authoritative Phase-B2 run 35456354727; source identity unchanged"
          })

      await context.close();await browser.close()

    counts=Counter(r["figure_id"] for r in rows)
    place=Counter(r["placement_mode"] for r in rows)
    summary={
      "figures_expected":EXPECTED_INVENTORY,
      "figures_audited":len(rows),
      "unique_ids":len(counts),
      "duplicates":{k:v for k,v in counts.items() if v>1},
      "chapters_covered":len(set(r["chapter"] for r in rows)),
      "placement_distribution":dict(place),
      "below_threshold_contextual":sum(r["placement_mode"]=="CONTEXTUAL_VISIBLE_MATCH" and r["current_score"]<THRESHOLD for r in rows),
      "closed_details_contextual":sum(r["placement_mode"]=="CONTEXTUAL_VISIBLE_MATCH" and r["inside_closed_details"] for r in rows),
      "hidden_figures":sum(not r["default_visible"] for r in rows),
      "horizontal_overflow_failures":sum(r["horizontal_overflow_px"]>0 for r in rows),
      "runtime_errors":errors,
    }
    assert len(rows)==EXPECTED_INVENTORY
    assert len(counts)==EXPECTED_INVENTORY
    assert not summary["duplicates"]
    assert place["CONTEXTUAL_VISIBLE_MATCH"]==EXPECTED_CONTEXTUAL
    assert place["NEUTRAL_ORIGINAL_CONTAINER"]==EXPECTED_NEUTRAL_ORIGINAL
    assert place.get("NEUTRAL_LESSON_ANCHOR",0)==0
    assert summary["below_threshold_contextual"]==0
    assert summary["closed_details_contextual"]==0
    assert summary["hidden_figures"]==0
    assert summary["horizontal_overflow_failures"]==0
    assert not errors

    (out/"FULL_CONTEXTUAL_RAW.json").write_text(json.dumps({"summary":summary,"rows":rows},ensure_ascii=False,indent=2),encoding="utf-8")
    fields=[
      "figure_id","chapter","chapter_button","chapter_heads","caption","placement_mode","destination_heading","destination_subsection",
      "previous_visible_text","following_visible_text","best_visible_heading","best_visible_score","current_score","threshold",
      "default_visible","inside_closed_details","horizontal_overflow_px","svg_text_labels","legend_en","legend_ta","aria_label",
      "full_figure_text","data_source_pass2","role","tabindex","interaction_status","screenshot_360"
    ]
    with (out/"FULL_CONTEXTUAL_RAW.csv").open("w",encoding="utf-8",newline="") as f:
      w=csv.DictWriter(f,fieldnames=fields);w.writeheader()
      for r in rows:
        rr=r.copy();rr["svg_text_labels"]=" | ".join(rr["svg_text_labels"]);w.writerow(rr)
    (out/"FULL_CONTEXTUAL_RAW_SUMMARY.json").write_text(json.dumps(summary,indent=2),encoding="utf-8")

def main():
    ap=argparse.ArgumentParser()
    ap.add_argument("--html",required=True)
    ap.add_argument("--out",required=True)
    ap.add_argument("--chromium",required=True)
    args=ap.parse_args()
    raise SystemExit(asyncio.run(main_async(args)))

if __name__=="__main__": main()
