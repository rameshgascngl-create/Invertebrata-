#!/usr/bin/env python3
from pathlib import Path
import csv,json,sys

# Visual-production pass only. Apply after v1.8.8 + SVG Batch 1 + SVG Batch 2.
# It deliberately does not invoke Gradle or alter the manual-dispatch workflow.

STYLE='''<defs><pattern id="graphiteHatch" width="10" height="10" patternUnits="userSpaceOnUse" patternTransform="rotate(18)"><path d="M0 0V10" stroke="#777" stroke-width="0.55" opacity=".32"/></pattern><pattern id="graphiteStipple" width="14" height="14" patternUnits="userSpaceOnUse"><circle cx="3" cy="4" r=".8" fill="#666" opacity=".32"/><circle cx="10" cy="9" r=".65" fill="#666" opacity=".26"/></pattern><marker id="pArrow" markerWidth="8" markerHeight="8" refX="7" refY="3" orient="auto"><path d="M0 0L8 3L0 6Z" fill="#3b3b3b"/></marker></defs>'''

def plate(pid,title,ta,aria,drawing,legend):
    return f'''<figure class="sys-fig v188-pencil-plate v188-master-pencil" data-v188-plate="{pid}" data-visual-type="Pencil SVG + numbered legend"><figcaption><strong>{title}</strong><span class="ta-explain" lang="ta">{ta}</span></figcaption><svg class="sys-svg" viewBox="0 0 760 470" role="img" aria-label="{aria}" xmlns="http://www.w3.org/2000/svg"><rect width="760" height="470" fill="#fffefa"/>{STYLE}<g fill="none" stroke="#303030" stroke-linecap="round" stroke-linejoin="round">{drawing}</g></svg><div class="v188-figure-legend" data-legend-en="{legend[0]}" data-legend-ta="{legend[1]}"></div></figure>'''

SYCON=plate('master-sycon-canal','Sycon — syconoid canal system','சைகான் — சைகனாய்டு கால்வாய் அமைப்பு','Pencil-style longitudinal section of Sycon showing incurrent canals, radial canals, spongocoel and direction of water flow.',r'''
<path d="M260 382 C220 315 220 155 270 72 Q380 26 490 72 C540 155 540 315 500 382Z" fill="url(#graphiteStipple)" stroke-width="3.2"/>
<path d="M335 360 C310 275 318 145 352 80 M425 360 C450 275 442 145 408 80" stroke-width="2.1"/><path d="M352 80 Q380 55 408 80" stroke-width="2.1"/>
<g stroke-width="1.65"><path d="M278 118 Q318 126 340 150 M482 118 Q442 126 420 150 M266 170 Q315 176 338 197 M494 170 Q445 176 422 197 M262 225 Q312 228 337 244 M498 225 Q448 228 423 244 M270 282 Q315 276 339 289 M490 282 Q445 276 421 289 M288 335 Q320 318 342 327 M472 335 Q440 318 418 327"/></g>
<g stroke-width="1.15"><path d="M245 120l-12 10m22-2l-13 11 M515 120l12 10m-22-2l13 11 M250 280l-12 10m22-2l-13 11 M510 280l12 10m-22-2l13 11"/></g>
<g stroke-width="1.8" marker-end="url(#pArrow)"><path d="M185 170H260"/><path d="M290 170Q318 178 337 197"/><path d="M338 197H365"/><path d="M380 310V86"/></g>
<g stroke-width="1"><path d="M380 60L540 40"/><path d="M380 205L548 205"/><path d="M315 176L115 145"/><path d="M337 244L115 255"/></g>
<g fill="#fffefa" stroke="#303030" stroke-width="1.2"><circle cx="540" cy="40" r="11"/><circle cx="548" cy="205" r="11"/><circle cx="115" cy="145" r="11"/><circle cx="115" cy="255" r="11"/></g><g fill="#222" stroke="none" font-family="sans-serif" font-size="13" text-anchor="middle"><text x="540" y="45">1</text><text x="548" y="210">2</text><text x="115" y="150">3</text><text x="115" y="260">4</text></g>
<text x="380" y="430" fill="#333" stroke="none" font-family="sans-serif" font-size="13" text-anchor="middle">Water: ostia → incurrent canal → prosopyle → radial canal → apopyle → spongocoel → osculum</text>
''',('1 Osculum; 2 Spongocoel; 3 Incurrent canal; 4 Radial canal','1 ஆஸ்குலம்; 2 ஸ்பாஞ்சோசீல்; 3 உள்வரும் கால்வாய்; 4 ஆரக் கால்வாய்'))

FASCIOLA=plate('master-fasciola-reproductive','Fasciola hepatica — reproductive system','ஃபாசியோலா ஹெபாட்டிகா — இனப்பெருக்க அமைப்பு','Pencil-style reproductive anatomy of Fasciola hepatica showing connected male and female reproductive structures.',r'''
<path d="M270 398 Q205 295 230 130 Q250 55 380 35 Q510 55 530 130 Q555 295 490 398 Q380 425 270 398Z" fill="url(#graphiteStipple)" stroke-width="3.2"/>
<g stroke-width="2"><path d="M315 320 q-50-35-8-72 q-36-28 2-55 M445 320 q50-35 8-72 q36-28-2-55"/><path d="M315 320Q350 275 366 228 M445 320Q410 275 394 228 M366 228Q380 213 394 228 M380 213V145"/><ellipse cx="380" cy="124" rx="26" ry="39"/><path d="M380 85V62"/>
<path d="M432 183 q43-26 31-66 q-38-10-57 25 q18 12 26 41Z"/><path d="M432 183Q410 202 394 218"/><circle cx="394" cy="218" r="11"/><path d="M394 218 q-64-22-56-65 q8-34 52-20 q45 16 14 43 q-44 30-73-4"/>
<path d="M270 150 q-25 18-4 40 q-28 19-3 42 q-26 20-2 44 M490 150 q25 18 4 40 q28 19 3 42 q26 20 2 44"/><path d="M276 240Q335 235 383 220 M484 240Q438 235 405 220"/></g>
<g stroke-width="1"><path d="M380 62L570 50"/><path d="M432 183L590 150"/><path d="M394 218L590 215"/><path d="M445 300L590 315"/><path d="M335 155L135 125"/></g>
<g fill="#fffefa" stroke="#303030"><circle cx="570" cy="50" r="11"/><circle cx="590" cy="150" r="11"/><circle cx="590" cy="215" r="11"/><circle cx="590" cy="315" r="11"/><circle cx="135" cy="125" r="11"/></g><g fill="#222" stroke="none" font-family="sans-serif" font-size="13" text-anchor="middle"><text x="570" y="55">1</text><text x="590" y="155">2</text><text x="590" y="220">3</text><text x="590" y="320">4</text><text x="135" y="130">5</text></g>
''',('1 Genital opening/cirrus region; 2 Ovary; 3 Ootype–Mehlis’ gland region; 4 Branched testes; 5 Coiled uterus','1 பிறப்புறுப்புத் திறப்பு/சிரஸ் பகுதி; 2 சூலகம்; 3 ஊடைப் பகுதி/Mehlis சுரப்பிப் பகுதி; 4 கிளைத்த விந்தகங்கள்; 5 சுருண்ட கருப்பை'))

PENAEUS=plate('master-penaeus-appendage','Penaeus — representative biramous appendage','பெனேயஸ் — இருகிளை இணைப்புறுப்பின் அடிப்படை அமைப்பு','Pencil-style representative biramous appendage of Penaeus showing protopodite, endopodite and exopodite without assigning unsupported specialised identity.',r'''
<path d="M365 355 Q330 300 350 230 L380 150 Q410 230 395 355Z" fill="url(#graphiteHatch)" stroke-width="3.2"/>
<path d="M370 190 Q285 145 205 90" stroke-width="2.3"/><path d="M390 190 Q475 145 555 90" stroke-width="2.3"/>
<path d="M205 90 l-22-18 m30 26l-28 5 m42 4l-22 18 M555 90 l22-18 m-30 26l28 5 m-42 4l22 18" stroke-width="1.15"/>
<path d="M380 150L380 95" stroke-width="1.7"/><path d="M350 230Q325 220 310 195 M395 230Q420 220 438 195" stroke-width="1.2"/>
<g stroke-width="1"><path d="M380 315L560 330"/><path d="M260 125L120 100"/><path d="M500 125L640 100"/></g><g fill="#fffefa" stroke="#303030"><circle cx="560" cy="330" r="11"/><circle cx="120" cy="100" r="11"/><circle cx="640" cy="100" r="11"/></g><g fill="#222" stroke="none" font-family="sans-serif" font-size="13" text-anchor="middle"><text x="560" y="335">1</text><text x="120" y="105">2</text><text x="640" y="105">3</text></g>
<text x="380" y="420" fill="#333" stroke="none" font-family="sans-serif" font-size="13" text-anchor="middle">Generalised biramous plan — specialised appendages require separate identity-specific audit</text>
''',('1 Protopodite; 2 Endopodite; 3 Exopodite','1 புரோட்டோபோடைட்; 2 எண்டோபோடைட்; 3 எக்சோபோடைட்'))

ASTERIAS=plate('master-asterias-wvs','Asterias — water-vascular system','ஆஸ்டீரியாஸ் — நீர்க்குழல் அமைப்பு','Pencil-style water-vascular system of Asterias showing madreporite, stone canal, ring canal, radial canals, lateral canals, ampullae and tube feet.',r'''
<path d="M300 218 L315 72 L360 178 L485 112 L392 215 L520 310 L365 255 L300 405 L280 255 L120 310 L248 215 L155 112 L275 178Z" fill="url(#graphiteStipple)" stroke-width="3.2"/>
<circle cx="300" cy="220" r="39" stroke-width="2.2"/><circle cx="365" cy="145" r="14" fill="url(#graphiteHatch)" stroke-width="2"/><path d="M365 159Q345 185 326 205" stroke-width="2.2"/>
<g stroke-width="2"><path d="M300 181V85 M334 198L455 130 M338 232L485 300 M300 259V385 M266 232L145 300 M266 198L175 130"/></g>
<g stroke-width="1.1"><path d="M325 130l28-12 M330 150l30-10 M385 170l12 28 M410 155l15 28 M410 260l-5 30 M440 278l-8 30"/></g>
<!-- enlarged ampulla/tube-foot detail --> <ellipse cx="620" cy="195" rx="38" ry="27" fill="url(#graphiteHatch)" stroke-width="2.2"/><path d="M620 222V330" stroke-width="5"/><ellipse cx="620" cy="340" rx="25" ry="9" stroke-width="2"/><path d="M540 195H578" stroke-width="1.8" marker-end="url(#pArrow)"/>
<g stroke-width="1"><path d="M365 145L520 65"/><path d="M300 220L520 220"/><path d="M420 150L520 135"/><path d="M620 195L700 165"/><path d="M620 340L700 350"/></g><g fill="#fffefa" stroke="#303030"><circle cx="520" cy="65" r="11"/><circle cx="520" cy="220" r="11"/><circle cx="520" cy="135" r="11"/><circle cx="700" cy="165" r="11"/><circle cx="700" cy="350" r="11"/></g><g fill="#222" stroke="none" font-family="sans-serif" font-size="13" text-anchor="middle"><text x="520" y="70">1</text><text x="520" y="225">2</text><text x="520" y="140">3</text><text x="700" y="170">4</text><text x="700" y="355">5</text></g>
''',('1 Madreporite + stone canal; 2 Ring canal; 3 Radial/lateral canals; 4 Ampulla; 5 Tube foot','1 மாட்ரிபோரைட் + கற்குழாய்; 2 வளையக் கால்வாய்; 3 ஆர/பக்கக் கால்வாய்கள்; 4 ஆம்புல்லா; 5 குழாய்க்கால்'))

MASTER={'u2-sycon':SYCON,'u3-fasciola':FASCIOLA,'u4-penaeus':PENAEUS,'u5-asterias':ASTERIAS}

def main(root):
    root=Path(root).resolve(); targets=[root/'academic_payload/index.html',root/'app/src/main/assets/www/index.html']
    inj='<script id="v188-pencil-master-style">\n'+'\n'.join("window.ORG_SYSTEM_DIAGRAMS[%s]=(window.ORG_SYSTEM_DIAGRAMS[%s]||'')+%s;"%(json.dumps(k),json.dumps(k),json.dumps(v,ensure_ascii=False)) for k,v in MASTER.items())+'\n</script>\n'
    css='''<style id="v188-pencil-master-css">.v188-master-pencil{background:#fffefa!important}.v188-master-pencil svg{background:#fffefa}.v188-figure-legend{font-size:.88rem;line-height:1.45;margin:.45rem .2rem .1rem;color:var(--text)}.v188-figure-legend:empty:before{content:attr(data-legend-en)}html[lang="ta"] .v188-figure-legend:empty:before{content:attr(data-legend-ta)}@media(max-width:420px){.v188-master-pencil{padding:.38rem!important}.v188-master-pencil svg{width:100%!important;height:auto!important}.v188-figure-legend{font-size:.82rem}}</style>\n'''
    for p in targets:
        s=p.read_text(encoding='utf-8')
        if 'id="v188-pencil-master-style"' in s: raise SystemExit('master style already applied')
        anchor='<script id="v188-contextual-atlas">'
        if anchor not in s: raise SystemExit('v1.8.8 contextual atlas missing')
        s=s.replace(anchor,css+inj+anchor,1); p.write_text(s,encoding='utf-8',newline='\n')
    if targets[0].read_bytes()!=targets[1].read_bytes(): raise SystemExit('payload copies diverged')
    print('V188_MASTER_PENCIL_PLATES=4'); print('V188_MASTER_BUILD_TRIGGERED=NO')

if __name__=='__main__': main(sys.argv[1])
