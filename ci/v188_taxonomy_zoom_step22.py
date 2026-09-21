#!/usr/bin/env python3
from __future__ import annotations

import hashlib
import sys
from pathlib import Path

INPUT_SHA256 = "392924cc3e854bbd2c91b3ec974959de23efa147a1ee9463494c85396a6b573f"
INPUT_SIZE = 2393951
OUTPUT_SHA256 = "e59475a6a45d8b52568fded3cc2ff916c0d717454dd3fe894193399d2c8fd7e6"
OUTPUT_SIZE = 2400517
PAYLOADS = (
    Path("academic_payload/index.html"),
    Path("app/src/main/assets/www/index.html"),
)

OLD_DIAGRAMS = """window.diagramsForChapter = function(id,lang){
  const html = (window.ORG_SYSTEM_DIAGRAMS && window.ORG_SYSTEM_DIAGRAMS[id]) || '';
  if(!html) return '';
  const heading = lang==='ta' ? '<h3 lang="ta">அமைப்புகளும் வரைபடங்களும்</h3>' : '<h3 lang="en">Systems and diagrams</h3>';
  return '<section class="sys-atlas diagram-lang-'+(lang==='ta'?'ta':'en')+'">'+heading+html+'</section>';
};"""

NEW_DIAGRAMS = r"""function v188TaxonomyIntroDiagrams(lang){
  const ta=lang==='ta';
  const ranks=ta
    ? ['உலகம்','தொகுதி','வகுப்பு','வரிசை','குடும்பம்','பேரினம்','சிற்றினம்']
    : ['Kingdom','Phylum','Class','Order','Family','Genus','Species'];
  const bars=[
    [35,40,530],[60,130,480],[85,220,430],[110,310,380],[135,400,330],[160,490,280],[185,580,230]
  ];
  const rankSvg='<svg class="v188-taxonomy-clean-plate" data-v188-language-locked="true" data-v188-zoom-max="2" data-v188-taxonomy-plate="ranks" viewBox="0 0 600 700" role="img" aria-label="'+(ta?'வகைப்பாட்டு படிநிலைகள்':'Taxonomic hierarchy')+'" preserveAspectRatio="xMidYMid meet">'+
    '<rect width="600" height="700" fill="#fffdf8"/>'+
    bars.map(function(b,i){
      const cx=b[0]+b[2]/2;
      return '<g data-taxonomy-rank="'+i+'"><rect x="'+b[0]+'" y="'+b[1]+'" width="'+b[2]+'" height="62" rx="8" fill="#f7f1e6" stroke="#333333" stroke-width="2"/>'+
        '<text data-taxonomy-label="'+i+'" x="'+cx+'" y="'+(b[1]+40)+'" text-anchor="middle" style="font-size:24px!important;font-family:system-ui,sans-serif;font-weight:700;fill:#1a1713">'+ranks[i]+'</text></g>';
    }).join('')+
    '</svg>';
  const binomialSvg=ta
    ? '<svg class="v188-taxonomy-clean-plate" data-v188-language-locked="true" data-v188-zoom-max="2" data-v188-taxonomy-plate="binomial" viewBox="0 0 600 360" role="img" aria-label="இருபெயர் பெயரிடல்" preserveAspectRatio="xMidYMid meet"><rect width="600" height="360" fill="#fffdf8"/><rect x="35" y="55" width="235" height="235" rx="12" fill="#f7f1e6" stroke="#333333" stroke-width="2"/><rect x="330" y="55" width="235" height="235" rx="12" fill="#f7f1e6" stroke="#333333" stroke-width="2"/><text x="152.5" y="110" text-anchor="middle" style="font-size:26px!important;font-family:system-ui,sans-serif;font-weight:700;fill:#1a1713">பேரினம்</text><text x="152.5" y="185" text-anchor="middle" style="font-size:34px!important;font-family:Georgia,serif;font-style:italic;font-weight:700;fill:#1a1713">Paramecium</text><text x="447.5" y="100" text-anchor="middle" style="font-size:23px!important;font-family:system-ui,sans-serif;font-weight:700;fill:#1a1713"><tspan x="447.5" dy="0">இனச்சிறப்புப்</tspan><tspan x="447.5" dy="31">பெயர்</tspan></text><text x="447.5" y="185" text-anchor="middle" style="font-size:34px!important;font-family:Georgia,serif;font-style:italic;font-weight:700;fill:#1a1713">caudatum</text><text x="300" y="190" text-anchor="middle" style="font-size:36px!important;font-family:system-ui,sans-serif;font-weight:700;fill:#1a1713">+</text></svg>'
    : '<svg class="v188-taxonomy-clean-plate" data-v188-language-locked="true" data-v188-zoom-max="2" data-v188-taxonomy-plate="binomial" viewBox="0 0 600 360" role="img" aria-label="Binomial nomenclature" preserveAspectRatio="xMidYMid meet"><rect width="600" height="360" fill="#fffdf8"/><rect x="35" y="55" width="235" height="235" rx="12" fill="#f7f1e6" stroke="#333333" stroke-width="2"/><rect x="330" y="55" width="235" height="235" rx="12" fill="#f7f1e6" stroke="#333333" stroke-width="2"/><text x="152.5" y="110" text-anchor="middle" style="font-size:26px!important;font-family:system-ui,sans-serif;font-weight:700;fill:#1a1713">Genus</text><text x="152.5" y="185" text-anchor="middle" style="font-size:34px!important;font-family:Georgia,serif;font-style:italic;font-weight:700;fill:#1a1713">Paramecium</text><text x="447.5" y="110" text-anchor="middle" style="font-size:26px!important;font-family:system-ui,sans-serif;font-weight:700;fill:#1a1713">Specific epithet</text><text x="447.5" y="185" text-anchor="middle" style="font-size:34px!important;font-family:Georgia,serif;font-style:italic;font-weight:700;fill:#1a1713">caudatum</text><text x="300" y="190" text-anchor="middle" style="font-size:36px!important;font-family:system-ui,sans-serif;font-weight:700;fill:#1a1713">+</text></svg>';
  const rankCaption=ta?'வகைப்பாட்டு படிநிலைகள்':'Taxonomic hierarchy';
  const binomialCaption=ta?'இருபெயர் பெயரிடல்':'Binomial nomenclature';
  const rankNote=ta?'ஒவ்வொரு படிநிலையும் அதற்கு மேலுள்ள படிநிலைக்குள் அடங்கும்; சிற்றினம் வேலை அலகாகப் பயன்படுத்தப்படுகிறது.':'Each rank is nested within the rank above it; species is the working unit.';
  const binomialNote=ta?'அறிவியல் பெயர்: <em>Paramecium caudatum</em>. பேரினம் பெரிய எழுத்தில் தொடங்கும்; இனச்சிறப்புப் பெயர் சிறிய எழுத்தில் தொடங்கும்; இரண்டும் சாய்வெழுத்தில் எழுதப்படும்.':'Scientific name: <em>Paramecium caudatum</em>. The genus starts with a capital letter, the specific epithet with a lower-case letter, and both are italicised.';
  return '<section class="sys-atlas diagram-lang-'+(ta?'ta':'en')+'"><h3 '+(ta?'lang="ta"':'lang="en"')+'>'+(ta?'அமைப்புகளும் வரைபடங்களும்':'Systems and diagrams')+'</h3>'+
    '<figure class="sys-fig v188-taxonomy-clean-figure"><figcaption>'+rankCaption+'</figcaption>'+rankSvg+'<p class="'+(ta?'ta-explain':'en-main')+'"'+(ta?' lang="ta"':'')+'>'+rankNote+'</p></figure>'+
    '<figure class="sys-fig v188-taxonomy-clean-figure"><figcaption>'+binomialCaption+'</figcaption>'+binomialSvg+'<p class="'+(ta?'ta-explain':'en-main')+'"'+(ta?' lang="ta"':'')+'>'+binomialNote+'</p></figure>'+
    '</section>';
}

window.diagramsForChapter = function(id,lang){
  if(id==='u1-intro') return v188TaxonomyIntroDiagrams(lang);
  const html = (window.ORG_SYSTEM_DIAGRAMS && window.ORG_SYSTEM_DIAGRAMS[id]) || '';
  if(!html) return '';
  const heading = lang==='ta' ? '<h3 lang="ta">அமைப்புகளும் வரைபடங்களும்</h3>' : '<h3 lang="en">Systems and diagrams</h3>';
  return '<section class="sys-atlas diagram-lang-'+(lang==='ta'?'ta':'en')+'">'+heading+html+'</section>';
};"""

OLD_LOCALIZE = """function localizeTamilVisualLabels(scope){
      if(!scope)return;
      scope.querySelectorAll('.theory-pane[lang="ta"] .textbook-visuals svg text').forEach(function(el){
        const original=el.textContent||'';
        const localized=tamilTextbook(original);
        if(localized!==original)el.textContent=localized;
      });
    }

"""

NEW_LOCALIZE = """function localizeTamilVisualLabels(scope){
      if(!scope)return;
      scope.querySelectorAll('.theory-pane[lang="ta"] .textbook-visuals svg text').forEach(function(el){
        if(el.closest('[data-v188-language-locked="true"]'))return;
        const original=el.textContent||'';
        const localized=tamilTextbook(original);
        if(localized!==original)el.textContent=localized;
      });
    }

"""

OLD_OPEN = """function openTheoryVisualNode(source,titleText){
      const overlay=root.querySelector('#invSyllabusVisualOverlay');const viewport=root.querySelector('#invSyllabusVisualViewport');const title=root.querySelector('#invSyllabusVisualTitle');if(!source||!overlay||!viewport)return;
      if(!overlay.hidden)return;
      const figureId=visualReturnFigureId(source);
      activeVisualReturnState={figureId:figureId,scrollX:window.scrollX,scrollY:window.scrollY,triggerElement:visualReturnTrigger(source),chapterId:state.chapter};
      visualHistoryEntryActive=false;
      visualHistoryUnwindPending=false;
      const clone=source.cloneNode(true);clone.removeAttribute('style');clone.classList.add('theory-zoom-media');if(source.closest&&source.closest('.v183-audited-plate'))clone.classList.add('v183-audited-svg');
      if(mobileLiteMode&&clone.querySelectorAll){clone.querySelectorAll('.syllabus-hitbox-layer').forEach(function(n){n.remove();});}
      viewport.replaceChildren(clone);if(title)title.textContent=titleText||localText('Figure','வரைபடம்');overlay.hidden=false;overlay.setAttribute('aria-hidden','false');document.documentElement.style.overflow='hidden';document.body.style.overflow='hidden';syllabusVisualState.scale=1;syllabusVisualState.x=0;syllabusVisualState.y=0;requestAnimationFrame(function(){fitSyllabusVisualMedia();applySyllabusVisualTransform();});
      try{
        const base=(window.history.state&&typeof window.history.state==='object')?window.history.state:{};
        window.history.pushState(Object.assign({},base,{marker:historyMarker,v188VisualOverlay:true,figureId:figureId}),'');
        visualHistoryEntryActive=true;
      }catch(error){visualHistoryEntryActive=false;}
    }"""

NEW_OPEN = """function openTheoryVisualNode(source,titleText){
      const overlay=root.querySelector('#invSyllabusVisualOverlay');const viewport=root.querySelector('#invSyllabusVisualViewport');const title=root.querySelector('#invSyllabusVisualTitle');if(!source||!overlay||!viewport)return;
      if(!overlay.hidden)return;
      const figureId=visualReturnFigureId(source);
      activeVisualReturnState={figureId:figureId,scrollX:window.scrollX,scrollY:window.scrollY,triggerElement:visualReturnTrigger(source),chapterId:state.chapter};
      visualHistoryEntryActive=false;
      visualHistoryUnwindPending=false;
      const requestedMax=parseFloat(source.getAttribute&&source.getAttribute('data-v188-zoom-max')||'3');
      syllabusVisualState.maxScale=isFinite(requestedMax)?Math.max(1,Math.min(3,requestedMax)):3;
      const clone=source.cloneNode(true);clone.removeAttribute('style');clone.classList.add('theory-zoom-media');if(source.closest&&source.closest('.v183-audited-plate'))clone.classList.add('v183-audited-svg');
      if(clone.matches&&clone.matches('.sys-svg')){
        clone.style.filter='none';
        clone.style.background='#fffdf8';
        clone.querySelectorAll('.sbox,.sfill').forEach(function(node){node.style.fill='#f3ead8';node.style.stroke='#2b2b2b';});
      }
      if(mobileLiteMode&&clone.querySelectorAll){clone.querySelectorAll('.syllabus-hitbox-layer').forEach(function(n){n.remove();});}
      viewport.replaceChildren(clone);if(title)title.textContent=titleText||localText('Figure','வரைபடம்');overlay.hidden=false;overlay.setAttribute('aria-hidden','false');document.documentElement.style.overflow='hidden';document.body.style.overflow='hidden';syllabusVisualState.scale=1;syllabusVisualState.x=0;syllabusVisualState.y=0;requestAnimationFrame(function(){fitSyllabusVisualMedia();applySyllabusVisualTransform();});
      try{
        const base=(window.history.state&&typeof window.history.state==='object')?window.history.state:{};
        window.history.pushState(Object.assign({},base,{marker:historyMarker,v188VisualOverlay:true,figureId:figureId}),'');
        visualHistoryEntryActive=true;
      }catch(error){visualHistoryEntryActive=false;}
    }"""

OLD_PINCH = "syllabusVisualState.scale=Math.max(1,Math.min(4,syllabusVisualState.lastPinchScale*(dist/syllabusVisualState.lastPinchDistance)));"
NEW_PINCH = "syllabusVisualState.scale=Math.max(syllabusVisualState.minScale,Math.min(syllabusVisualState.maxScale,syllabusVisualState.lastPinchScale*(dist/syllabusVisualState.lastPinchDistance)));"

def sha256(data: bytes) -> str:
    return hashlib.sha256(data).hexdigest()

def require(condition: bool, message: str) -> None:
    if not condition:
        raise SystemExit(message)

def transform(text: str) -> str:
    raw=text.encode('utf-8')
    require(len(raw)==INPUT_SIZE, f"STEP-22 INPUT SIZE MISMATCH — ABORT expected={INPUT_SIZE} actual={len(raw)}")
    require(sha256(raw)==INPUT_SHA256, f"STEP-22 INPUT HASH MISMATCH — ABORT expected={INPUT_SHA256} actual={sha256(raw)}")

    original=text
    replacements=(
        (OLD_DIAGRAMS,NEW_DIAGRAMS,'taxonomy diagrams'),
        (OLD_LOCALIZE,NEW_LOCALIZE,'Tamil visual localisation lock'),
        (OLD_OPEN,NEW_OPEN,'zoom clone hardening'),
        (OLD_PINCH,NEW_PINCH,'pinch zoom cap'),
    )
    for before,after,label in replacements:
        count=text.count(before)
        require(count==1, f"STEP-22 PRECONDITION MISMATCH — {label}: expected=1 actual={count}")
        text=text.replace(before,after,1)

    require(text.count('data-v188-taxonomy-plate="ranks"')==1, 'STEP-22 RANK PLATE CARDINALITY FAILURE')
    require(text.count('data-v188-taxonomy-plate="binomial"')==2, 'STEP-22 BINOMIAL LANGUAGE PLATE CARDINALITY FAILURE')
    require(text.count('data-v188-language-locked="true"')==4, 'STEP-22 LANGUAGE LOCK CARDINALITY FAILURE')
    require(text.count('data-v188-zoom-max="2"')==3, 'STEP-22 TAXONOMY ZOOM CAP CARDINALITY FAILURE')
    require("Math.min(4,syllabusVisualState.lastPinchScale" not in text, 'STEP-22 OLD PINCH LIMIT REMAINS')
    require("clone.matches&&clone.matches('.sys-svg')" in text, 'STEP-22 SHARED SYS-SVG ZOOM HARDENING MISSING')
    require("if(el.closest('[data-v188-language-locked=\"true\"]'))return;" in text, 'STEP-22 LANGUAGE LOCK BYPASS MISSING')
    require('Paramecium</text>' in text and 'caudatum</text>' in text, 'STEP-22 LATIN BINOMIAL MISSING')
    for term in ('உலகம்','தொகுதி','வகுப்பு','வரிசை','குடும்பம்','பேரினம்','சிற்றினம்','இனச்சிறப்புப்'):
        require(term in text, f'STEP-22 TAMIL TAXONOMY LABEL MISSING: {term}')

    restored=text
    for before,after,_label in reversed(replacements):
        count=restored.count(after)
        require(count==1, f"STEP-22 REVERSAL CARDINALITY MISMATCH expected=1 actual={count}")
        restored=restored.replace(after,before,1)
    require(restored==original, 'STEP-22 UNAUTHORISED SOURCE DRIFT')

    out=text.encode('utf-8')
    require(len(out)==OUTPUT_SIZE, f"STEP-22 OUTPUT SIZE MISMATCH expected={OUTPUT_SIZE} actual={len(out)}")
    require(sha256(out)==OUTPUT_SHA256, f"STEP-22 OUTPUT HASH MISMATCH expected={OUTPUT_SHA256} actual={sha256(out)}")
    return text

def main() -> None:
    root=Path(sys.argv[1]).resolve() if len(sys.argv)>1 else Path.cwd()
    a=root/PAYLOADS[0]
    b=root/PAYLOADS[1]
    require(a.is_file() and b.is_file(), 'STEP-22 PAYLOAD COPIES MISSING')
    require(a.read_bytes()==b.read_bytes(), 'STEP-22 INPUT PAYLOAD COPIES DIVERGED')
    require(sha256(a.read_bytes())==INPUT_SHA256, 'STEP-22 INPUT HASH MISMATCH — ABORT')

    output=transform(a.read_text(encoding='utf-8'))
    for p in (a,b):
        p.write_text(output,encoding='utf-8',newline='\n')
    require(a.read_bytes()==b.read_bytes(), 'STEP-22 OUTPUT PAYLOAD COPIES DIVERGED')

    out=a.read_bytes()
    print(f'STEP22_INPUT_SHA256={INPUT_SHA256}')
    print('STEP22_AUTHORISED_MUTATION_SITES=4')
    print('STEP22_TAXONOMY_LAYOUTS=EN,TA')
    print('STEP22_TAXONOMY_PLATES=2_PER_LANGUAGE')
    print('STEP22_SYS_SVG_OVERLAY_FILTER=DISABLED')
    print('STEP22_TAXONOMY_MAX_ZOOM=200%')
    print(f'STEP22_OUTPUT_SHA256={sha256(out)}')
    print(f'STEP22_OUTPUT_SIZE={len(out)}')

if __name__=='__main__':
    main()
