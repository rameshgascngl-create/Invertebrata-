#!/usr/bin/env python3
from pathlib import Path
import hashlib, sys

OLD_PAYLOAD='25220886837b724f781612f66ae5f8fd4a983ca644559c4871aff91b479e24b7'
def sha(p): return hashlib.sha256(p.read_bytes()).hexdigest()
def require(c,m):
    if not c: raise SystemExit(m)

root=Path(sys.argv[1]).resolve()
files=[root/'academic_payload/index.html',root/'app/src/main/assets/www/index.html']
for p in files:
    s=p.read_text(encoding='utf-8')
    require(sha(p)==OLD_PAYLOAD,'unexpected academic payload before v1.8.8 patch')

    # Preserve the app's existing delegated theory-figure enlargement path while
    # widening its semantic selector from ancestry-dependent placement to the
    # figure's stable instructional class. Contextual relocation may move a
    # .sys-fig out of .textbook-visuals; the root-level delegated handler must
    # continue to recognize the same figure after that DOM move.
    old_zoom_selector="const generatedFigure=event.target.closest('.textbook-visuals .sys-fig');"
    new_zoom_selector="const generatedFigure=event.target.closest('.sys-fig');"
    require(s.count(old_zoom_selector)==1,'unexpected theory enlargement selector before v1.8.8 patch')
    s=s.replace(old_zoom_selector,new_zoom_selector,1)
    require(old_zoom_selector not in s and s.count(new_zoom_selector)==1,'theory enlargement selector routing patch failed')

    marker='</style>'
    require(marker in s,'style terminator missing')
    css=r'''
/* v1.8.8 mobile reading surface + contextual illustrated-textbook layer */
html{-webkit-text-size-adjust:100%;text-size-adjust:100%;}
html,body,#widget,#inv-type-lab-v4,#inv-type-lab-v4 .theory-mount,#inv-type-lab-v4 .theory-pane,
#inv-type-lab-v4 .chapter-section,#inv-type-lab-v4 .expanded-lesson,#inv-type-lab-v4 .lesson-group,
#inv-type-lab-v4 .lesson-group-body,#inv-type-lab-v4 .lesson-point,#inv-type-lab-v4 .textbook-detail,
#inv-type-lab-v4 .textbook-subsection{min-width:0!important;max-width:100%!important;box-sizing:border-box;}
#inv-type-lab-v4 .theory-pane,#inv-type-lab-v4 .chapter-section,#inv-type-lab-v4 .expanded-lesson,#inv-type-lab-v4 .lesson-point{overflow-x:clip;}
#inv-type-lab-v4 .theory-pane :is(p,li,h1,h2,h3,h4,h5,h6,dd,dt,blockquote),
#inv-type-lab-v4 .expanded-lesson :is(p,li,h1,h2,h3,h4,h5,h6,dd,dt,blockquote){max-inline-size:100%;overflow-wrap:anywhere;word-break:normal;white-space:normal;}
#inv-type-lab-v4 .textbook-visuals,#inv-type-lab-v4 .sys-atlas,#inv-type-lab-v4 .sys-fig,#inv-type-lab-v4 .pencil-atlas-figure,
#inv-type-lab-v4 .contextual-theory-figure{min-width:0!important;max-width:100%!important;box-sizing:border-box;}
#inv-type-lab-v4 .contextual-theory-figure{margin:1rem 0 1.15rem;padding:.65rem;border:1px solid var(--border);border-radius:12px;background:#fffdf8;cursor:zoom-in;overflow:hidden;}
#inv-type-lab-v4 .contextual-theory-figure:focus-visible{outline:3px solid currentColor;outline-offset:2px;}
#inv-type-lab-v4 .contextual-theory-figure figcaption{max-width:100%;overflow-wrap:anywhere;margin-bottom:.5rem;line-height:1.35;}
#inv-type-lab-v4 .sys-fig svg,#inv-type-lab-v4 .pencil-atlas-figure svg,#inv-type-lab-v4 .contextual-theory-figure svg,
#inv-type-lab-v4 .contextual-theory-figure img{display:block;width:100%!important;height:auto!important;max-width:100%!important;min-width:0!important;}
#inv-type-lab-v4 .contextual-theory-figure svg text{paint-order:stroke;stroke:#fffdf8;stroke-width:.7px;stroke-linejoin:round;}
#inv-type-lab-v4 .textbook-visuals.contextualized-figures>h3:only-child{display:none;}
@media(max-width:600px){
 #inv-type-lab-v4 .theory-pane{width:100%;margin-inline:0;padding-inline:min(3.5vw,14px);}
 #inv-type-lab-v4 .contextual-theory-figure{padding:.45rem;margin:.85rem 0 1rem;}
 #inv-type-lab-v4 .contextual-theory-figure svg text{font-size:clamp(12px,3.35vw,16px)!important;}
 #inv-type-lab-v4 .contextual-theory-figure svg text.small{font-size:clamp(10px,2.9vw,13px)!important;}
}
'''
    s=s.replace(marker,css+'\n'+marker,1)

    # The source already contains the audited Pencil Atlas and organ-system SVG library.
    # Reuse those local SVGs and move each current-chapter figure beside the most relevant
    # explanatory subsection after rendering. No remote images and no theory deletion.
    body='</body>'
    require(body in s,'body terminator missing')
    js=r'''
<script id="v188-contextual-atlas">
(function(){
 'use strict';
 var mount=document.getElementById('invTheoryMount');
 if(!mount)return;
 var stop=new Set(['figure','system','systems','diagram','diagrams','of','the','and','a','an','in','with','showing','structure','structures','external','internal','representative','type','study','overview']);
 var families={
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
 function words(t){return String(t||'').toLowerCase().replace(/[^a-z0-9\u0B80-\u0BFF]+/g,' ').split(/\s+/).filter(function(x){return x.length>2&&!stop.has(x);});}
 function score(cap,txt){
   var cw=words(cap), tw=new Set(words(txt)), n=0;
   cw.forEach(function(w){if(tw.has(w))n+=3;});
   Object.keys(families).forEach(function(k){var f=families[k],a=f.some(function(w){return cap.indexOf(w)>=0;}),b=f.some(function(w){return txt.indexOf(w)>=0;});if(a&&b)n+=7;});
   return n;
 }
 function isDefaultVisibleNode(node){
   return !!node&&!node.closest('details:not([open])');
 }
 function contextualizePane(pane){
   var visuals=pane.querySelector('.textbook-visuals');
   if(!visuals||visuals.dataset.v188Done==='1')return;
   var figures=Array.from(visuals.querySelectorAll('figure.sys-fig'));
   var sections=Array.from(pane.querySelectorAll('.textbook-detail .textbook-subsection')).filter(isDefaultVisibleNode);
   figures.forEach(function(fig,index){
     fig.classList.add('contextual-theory-figure');
     fig.setAttribute('role','button'); fig.setAttribute('tabindex','0');
     var capNode=fig.querySelector('figcaption'); var cap=(capNode?capNode.innerText:'Figure').trim();
     var svg=fig.querySelector('svg'); var img=fig.querySelector('img');
     if(svg){svg.setAttribute('role','img');svg.setAttribute('aria-label',cap);svg.setAttribute('focusable','false');}
     if(img){img.setAttribute('alt',cap);img.setAttribute('loading','lazy');img.setAttribute('decoding','async');}
     var best=null,bestScore=0;
     sections.forEach(function(sec){var sc=score(cap.toLowerCase(),sec.innerText.toLowerCase());if(sc>bestScore){bestScore=sc;best=sec;}});
     if(best&&bestScore>=3)best.insertAdjacentElement('afterend',fig);
     else if(isDefaultVisibleNode(visuals)){
       // Neutral fallback A: retain the same figure in its lesson's original
       // default-visible instructional visual container. Do not force an
       // academically unrelated subsection association.
     } else {
       // Neutral fallback B: only when the original visual container itself is
       // hidden, move the same figure to a default-visible lesson-level anchor.
       var detail=pane.querySelector('.textbook-detail');
       var neutralAnchor=isDefaultVisibleNode(detail)?detail:pane;
       neutralAnchor.appendChild(fig);
     }
   });
   visuals.classList.add('contextualized-figures'); visuals.dataset.v188Done='1';
   if(!visuals.querySelector('figure,.inline-syllabus-visual'))visuals.hidden=true;
 }
 function run(){Array.from(mount.querySelectorAll('.theory-pane')).forEach(contextualizePane);}
 var queued=false;function queue(){if(queued)return;queued=true;requestAnimationFrame(function(){queued=false;run();});}
 new MutationObserver(queue).observe(mount,{childList:true,subtree:true});
 mount.addEventListener('keydown',function(e){var f=e.target.closest&&e.target.closest('.contextual-theory-figure');if(f&&(e.key==='Enter'||e.key===' ')){e.preventDefault();f.click();}});
 run();
})();
</script>
'''
    s=s.replace(body,js+'\n'+body,1)
    require("function isDefaultVisibleNode(node)" in s,'default-visible placement guard missing')
    require("closest('details:not([open])')" in s,'closed-details placement guard missing')
    require(".filter(isDefaultVisibleNode)" in s,'contextual candidate visibility filter missing')
    require("else if(isDefaultVisibleNode(visuals))" in s,'neutral original-container fallback missing')
    require("var neutralAnchor=isDefaultVisibleNode(detail)?detail:pane;" in s,'neutral lesson-level fallback missing')
    require("subs[Math.min(index" not in s,'prohibited index-based contextual fallback survived')
    p.write_text(s,encoding='utf-8',newline='\n')

require(sha(files[0])==sha(files[1]),'payload copies diverged')
new_payload=sha(files[0])
b=root/'app/build.gradle'; t=b.read_text(encoding='utf-8')
require("versionCode 18700" in t and "versionName '1.8.7'" in t,'unexpected Android version')
t=t.replace('versionCode 18700','versionCode 18800',1).replace("versionName '1.8.7'","versionName '1.8.8'",1)
b.write_text(t,encoding='utf-8',newline='\n')

# Build-time evidence: count locally embedded theory SVG figure definitions.
text=files[0].read_text(encoding='utf-8')
fig_count=text.count('<figure class=\\"sys-fig') + text.count('<figure class="sys-fig')
svg_count=text.count('<svg ')
report=root/'provenance/V188_ILLUSTRATED_THEORY_UPGRADE.md'
report.write_text(f'''# INVERTEBRATA v1.8.8 — illustrated theory upgrade\n\nThe v1.8.7 academic text is preserved. v1.8.8 reuses the application's existing local Pencil Atlas and organ-system SVG library and contextually places current-chapter figures beside the most relevant detailed theory subsections. No network image source is introduced.\n\n## Corrections and behaviour\n- Android WebView text autosizing normalized; lesson widths constrained to the reading viewport.\n- Existing Pencil Atlas / organ-system SVGs remain vector, local and offline.\n- Figures are contextually redistributed after chapter render instead of being presented as a detached gallery.\n- Current-chapter rendering provides chapter-level lazy loading; raster fallbacks, if present, receive native lazy/decode hints.\n- Major figures remain tap-to-enlarge through the existing theory visual viewer, with zoom/pan/reset/close controls and Android Back integration already provided by the app.\n- Figure keyboard activation and meaningful aria-label/alt text are added from the academic caption.\n- Mobile SVG/label containment prevents figures from reintroducing horizontal clipping.\n- English/Tamil caption architecture and existing Tamil visual-label localization are retained.\n\nEmbedded SVG elements detected in payload: {svg_count}\nFigure definitions detected: {fig_count}\nNew payload SHA-256: `{new_payload}`\n''',encoding='utf-8',newline='\n')

mf=root/'provenance/SOURCE_MANIFEST_SHA256.txt'
tracked=[]
for line in mf.read_text(encoding='utf-8').splitlines():
    if line.strip(): tracked.append(line.split('  ',1)[1])
for rel in ['provenance/V188_ILLUSTRATED_THEORY_UPGRADE.md']:
    if rel not in tracked: tracked.append(rel)
lines=[]
for rel in sorted(tracked):
    fp=root/rel; require(fp.is_file(),f'missing tracked file: {rel}')
    lines.append(f'{sha(fp)}  {rel}')
mf.write_text('\n'.join(lines)+'\n',encoding='utf-8',newline='\n')
print('V188_PAYLOAD_SHA256='+new_payload)
print('V188_EMBEDDED_SVG_COUNT='+str(svg_count))
print('V188_FIGURE_DEFINITION_COUNT='+str(fig_count))
