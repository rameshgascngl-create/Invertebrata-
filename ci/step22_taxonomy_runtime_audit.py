#!/usr/bin/env python3
from __future__ import annotations
import argparse, asyncio, json
from pathlib import Path
from playwright.async_api import async_playwright

EXPECTED_EN=["Kingdom","Phylum","Class","Order","Family","Genus","Species"]
EXPECTED_TA=["உலகம்","தொகுதி","வகுப்பு","வரிசை","குடும்பம்","பேரினம்","சிற்றினம்"]

async def frames(page,n=4):
    await page.evaluate("""n=>new Promise(r=>{let k=n;function f(){if(--k<=0)r();else requestAnimationFrame(f)}requestAnimationFrame(f)})""",n)

async def set_lang(page,lang):
    ok=await page.evaluate("""l=>{const b=document.querySelector('[data-set-lang="'+l+'"]');if(!b)return false;b.click();return true}""",lang)
    if not ok: raise RuntimeError(f"language control missing: {lang}")
    await frames(page,5)

async def open_ch(page,lesson):
    ok=await page.evaluate("""l=>{const b=document.querySelector('button[data-open-chapter="'+l+'"]');if(!b)return false;b.click();return true}""",lesson)
    if not ok: raise RuntimeError(f"chapter control missing: {lesson}")
    await page.wait_for_function("l=>(history.state?.view?.chapter||window.__invLastStudyView?.view?.chapter)===l",arg=lesson,timeout=5000)
    await frames(page,5)

async def taxonomy_snapshot(page,lang):
    return await page.evaluate("""lang=>{
      const pane=document.querySelector('.theory-pane[lang="'+lang+'"]');
      if(!pane)return {error:'pane missing'};
      const figs=Array.from(pane.querySelectorAll('.v188-taxonomy-clean-figure'));
      const ranks=pane.querySelector('[data-v188-taxonomy-plate="ranks"]');
      const bin=pane.querySelector('[data-v188-taxonomy-plate="binomial"]');
      if(!ranks||!bin)return {error:'taxonomy plates missing',figureCount:figs.length};
      const labels=Array.from(ranks.querySelectorAll('[data-taxonomy-label]')).map(x=>x.textContent.trim());
      const geometry=Array.from(ranks.querySelectorAll('g[data-taxonomy-rank]')).map(g=>{
        const r=g.querySelector('rect').getBBox(),t=g.querySelector('text').getBBox();
        return {rank:g.dataset.taxonomyRank,rect:{x:r.x,y:r.y,w:r.width,h:r.height},text:{x:t.x,y:t.y,w:t.width,h:t.height},
          inside:t.x>=r.x-1&&t.y>=r.y-1&&t.x+t.width<=r.x+r.width+1&&t.y+t.height<=r.y+r.height+1};
      });
      const rankRects=Array.from(ranks.querySelectorAll('g[data-taxonomy-rank] rect')).map(r=>({
        fill:getComputedStyle(r).fill,stroke:getComputedStyle(r).stroke
      }));
      const binTexts=Array.from(bin.querySelectorAll('text')).map(t=>({text:t.textContent.trim(),box:(()=>{const b=t.getBBox();return{x:b.x,y:b.y,w:b.width,h:b.height}})()}));
      const latin=binTexts.filter(x=>x.text==='Paramecium'||x.text==='caudatum');
      const vr=ranks.getBoundingClientRect(),br=bin.getBoundingClientRect();
      return {
        figureCount:figs.length,
        labels,geometry,rankRects,binTexts,latin,
        rankFilter:getComputedStyle(ranks).filter,
        rankBackground:getComputedStyle(ranks).backgroundColor,
        rankZoomMax:ranks.getAttribute('data-v188-zoom-max'),
        languageLocked:ranks.getAttribute('data-v188-language-locked'),
        binomialText:bin.textContent.replace(/\s+/g,' ').trim(),
        viewport:{w:innerWidth,h:innerHeight},
        rankClient:{left:vr.left,right:vr.right,width:vr.width},
        binClient:{left:br.left,right:br.right,width:br.width},
        documentOverflow:Math.max(0,document.documentElement.scrollWidth-document.documentElement.clientWidth)
      };
    }""",lang)

async def open_rank_and_zoom(page,lang,out):
    rank=page.locator(f'.theory-pane[lang="{lang}"] [data-v188-taxonomy-plate="ranks"]').first
    await rank.scroll_into_view_if_needed()
    fig=rank.locator('xpath=ancestor::figure[1]')
    await fig.click(no_wait_after=True)
    await page.wait_for_function("""()=>{const o=document.querySelector('#invSyllabusVisualOverlay');return o&&!o.hidden}""",timeout=5000)
    await frames(page,4)
    initial=await page.evaluate("""()=>{
      const m=document.querySelector('#invSyllabusVisualViewport [data-v188-taxonomy-plate="ranks"]');
      const r=m?.querySelector('g[data-taxonomy-rank] rect');
      return {present:!!m,filter:m?getComputedStyle(m).filter:null,fill:r?getComputedStyle(r).fill:null,
        stroke:r?getComputedStyle(r).stroke:null,readout:document.querySelector('#invSyllabusZoomReadout')?.textContent||''};
    }""")
    for _ in range(8):
        await page.locator('#invSyllabusZoomIn').click(no_wait_after=True)
    await frames(page,3)
    zoomed=await page.evaluate("""()=>{
      const m=document.querySelector('#invSyllabusVisualViewport [data-v188-taxonomy-plate="ranks"]');
      const r=m?.querySelector('g[data-taxonomy-rank] rect');
      return {filter:m?getComputedStyle(m).filter:null,fill:r?getComputedStyle(r).fill:null,
        readout:document.querySelector('#invSyllabusZoomReadout')?.textContent||'',transform:m?.style.transform||''};
    }""")
    await page.screenshot(path=str(out/f'taxonomy_{lang}_zoom_200.png'),full_page=False)
    result=await page.evaluate("""()=>window.__invHandleAndroidBack()""")
    await page.wait_for_function("""()=>document.querySelector('#invSyllabusVisualOverlay')?.hidden===true""",timeout=5000)
    return {"initial":initial,"zoomed":zoomed,"android_back_result":result}

async def audit_shared_sys_svg(page):
    await set_lang(page,'en')
    candidates=['u1-proto-parasites','u4-arthropoda','u5-foot']
    selected=None
    for lesson in candidates:
        await open_ch(page,lesson)
        found=await page.evaluate("""()=>Array.from(document.querySelectorAll('.theory-pane[lang="en"] svg.sys-svg')).some(s=>s.querySelector('.sbox,.sfill'))""")
        if found:
            selected=lesson
            break
    if not selected:return {"pass":False,"error":"no rendered sys-svg with sbox/sfill"}
    await page.evaluate("""()=>{const s=Array.from(document.querySelectorAll('.theory-pane[lang="en"] svg.sys-svg')).find(x=>x.querySelector('.sbox,.sfill'));s.closest('.sys-fig').click()}""")
    await page.wait_for_function("""()=>{const o=document.querySelector('#invSyllabusVisualOverlay');return o&&!o.hidden}""",timeout=5000)
    await frames(page,3)
    snap=await page.evaluate("""()=>{
      const s=document.querySelector('#invSyllabusVisualViewport svg.sys-svg');
      const b=s?.querySelector('.sbox,.sfill');
      return {lesson:history.state?.view?.chapter||window.__invLastStudyView?.view?.chapter||null,
        inlineFilter:s?.style.filter||'',computedFilter:s?getComputedStyle(s).filter:null,
        inlineFill:b?.style.fill||'',computedFill:b?getComputedStyle(b).fill:null,
        inlineStroke:b?.style.stroke||'',computedStroke:b?getComputedStyle(b).stroke:null};
    }""")
    await page.evaluate("""()=>window.__invHandleAndroidBack()""")
    await page.wait_for_function("""()=>document.querySelector('#invSyllabusVisualOverlay')?.hidden===true""",timeout=5000)
    snap["pass"]=snap["inlineFilter"]=="none" and snap["computedFill"]=="rgb(243, 234, 216)" and snap["computedStroke"]=="rgb(43, 43, 43)"
    return snap

async def main_async(args):
    out=Path(args.out);out.mkdir(parents=True,exist_ok=True)
    html=Path(args.html).read_text(encoding='utf-8')
    source_checks={
      "pinch_uses_dynamic_max":"Math.min(syllabusVisualState.maxScale,syllabusVisualState.lastPinchScale" in html,
      "old_hardcoded_pinch_absent":"Math.min(4,syllabusVisualState.lastPinchScale" not in html,
      "language_lock_bypass":"if(el.closest('[data-v188-language-locked=\"true\"]'))return;" in html,
      "sys_svg_overlay_hardening":"clone.matches&&clone.matches('.sys-svg')" in html,
      "taxonomy_zoom_cap":html.count('data-v188-zoom-max="2"')==3,
    }
    async with async_playwright() as p:
        browser=await p.chromium.launch(headless=True,executable_path=args.chromium,args=['--no-sandbox','--disable-dev-shm-usage'])
        ctx=await browser.new_context(viewport={'width':360,'height':800},device_scale_factor=1,is_mobile=True,has_touch=True)
        page=await ctx.new_page();errors=[]
        page.on('pageerror',lambda e:errors.append('PAGEERROR '+str(e)))
        page.on('console',lambda m:errors.append('CONSOLE '+m.type+' '+m.text) if m.type=='error' else None)
        await page.set_content(html,wait_until='load');await frames(page,5)

        snapshots={};zooms={}
        for lang,expected in [('en',EXPECTED_EN),('ta',EXPECTED_TA)]:
            await set_lang(page,lang);await open_ch(page,'u1-intro')
            snap=await taxonomy_snapshot(page,lang);snap['expectedLabels']=expected
            snap['labelsPass']=snap.get('labels')==expected
            snap['geometryPass']=all(x['inside'] for x in snap.get('geometry',[])) and len(snap.get('geometry',[]))==7
            snap['fillPass']=all(x['fill']=='rgb(247, 241, 230)' and x['stroke']=='rgb(51, 51, 51)' for x in snap.get('rankRects',[]))
            snap['latinPass']=set(x['text'] for x in snap.get('latin',[]))=={'Paramecium','caudatum'}
            if lang=='ta':
                bt=snap.get('binomialText','')
                snap['tamilBinomialPass']='பேரினம்' in bt and 'இனச்சிறப்புப்' in bt and 'பெயர்' in bt and 'Paramecium' in bt and 'caudatum' in bt
            else:snap['tamilBinomialPass']=True
            snap['layoutPass']=snap.get('figureCount')==2 and snap.get('documentOverflow')==0 and snap.get('rankClient',{}).get('left',-1)>=0 and snap.get('rankClient',{}).get('right',9999)<=360.5
            snapshots[lang]=snap
            await page.screenshot(path=str(out/f'taxonomy_{lang}_page.png'),full_page=True)
            zooms[lang]=await open_rank_and_zoom(page,lang,out)

        shared=await audit_shared_sys_svg(page)
        await ctx.close();await browser.close()

    zoom_pass=all(
      z['initial']['present'] and z['initial']['filter']=='none' and z['initial']['fill']=='rgb(247, 241, 230)'
      and z['zoomed']['filter']=='none' and z['zoomed']['fill']=='rgb(247, 241, 230)'
      and z['zoomed']['readout']=='200%' and 'scale(2)' in z['zoomed']['transform']
      and z['android_back_result']=='overlay'
      for z in zooms.values()
    )
    lang_pass=all(
      x.get('labelsPass') and x.get('geometryPass') and x.get('fillPass') and x.get('latinPass')
      and x.get('tamilBinomialPass') and x.get('layoutPass') and x.get('rankFilter')=='none'
      and x.get('rankZoomMax')=='2' and x.get('languageLocked')=='true'
      for x in snapshots.values()
    )
    result={
      "source_checks":source_checks,"snapshots":snapshots,"zoom":zooms,"shared_sys_svg":shared,
      "runtime_errors":errors,"language_layout_pass":lang_pass,"taxonomy_zoom_pass":zoom_pass,
      "pass":all(source_checks.values()) and lang_pass and zoom_pass and shared.get('pass') and not errors
    }
    (out/'STEP22_TAXONOMY_RUNTIME_AUDIT.json').write_text(json.dumps(result,ensure_ascii=False,indent=2),encoding='utf-8')
    print("STEP22_TAXONOMY_LAYOUT="+("PASS" if lang_pass else "FAIL"))
    print("STEP22_TAXONOMY_ZOOM_200="+("PASS" if zoom_pass else "FAIL"))
    print("STEP22_SHARED_SYS_SVG_OVERLAY="+("PASS" if shared.get('pass') else "FAIL"))
    print("STEP22_RUNTIME_ERRORS="+str(len(errors)))
    print("STEP22_TAXONOMY_RUNTIME_AUDIT="+("PASS" if result['pass'] else "FAIL"))
    raise SystemExit(0 if result['pass'] else 2)

def main():
    ap=argparse.ArgumentParser()
    ap.add_argument('--html',required=True);ap.add_argument('--out',required=True);ap.add_argument('--chromium',required=True)
    args=ap.parse_args();raise SystemExit(asyncio.run(main_async(args)))

if __name__=='__main__':main()
