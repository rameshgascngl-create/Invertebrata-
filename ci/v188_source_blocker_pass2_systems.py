#!/usr/bin/env python3
from pathlib import Path
import re, sys

# Pass 2: only source blockers SG-002..SG-009 and scope closures SG-013..SG-016.
# Existing biological geometry is retained where possible; no unrelated figure is modified.

def getfig(s,pid):
    pat=rf'(<figure\b[^>]*data-v188-plate="{re.escape(pid)}"[^>]*>)([\s\S]*?)(</figure>)'
    m=re.search(pat,s)
    if not m: raise SystemExit(f'figure not found: {pid}')
    return pat,m

def inject(s,pid,svg_fragment='',legend_en=None,legend_ta=None,marker='data-source-pass2="1"'):
    pat,m=getfig(s,pid); opening,body,closing=m.groups()
    if marker in opening: raise SystemExit(f'Pass2 already applied: {pid}')
    if svg_fragment:
        if '</svg>' not in body: raise SystemExit(f'no svg close: {pid}')
        body=body.replace('</svg>',svg_fragment+'</svg>',1)
    if legend_en is not None:
        ta=legend_ta if legend_ta is not None else legend_en
        body += f'<div class="v188-figure-legend v188-pass2-system-legend" data-legend-en="{legend_en}" data-legend-ta="{ta}"></div>'
    opening=opening[:-1]+' '+marker+'>'
    return s[:m.start()]+opening+body+closing+s[m.end():]

def replace_literal_in_figure(s,pid,old,new):
    pat,m=getfig(s,pid); opening,body,closing=m.groups()
    if old not in body: raise SystemExit(f'literal not found in {pid}: {old}')
    body=body.replace(old,new,1)
    return s[:m.start()]+opening+body+closing+s[m.end():]

NEREIS_DIG=r'''<g class="v188-pass2-nereis-digestive" font-family="sans-serif"><path d="M82 210L55 315M125 210L125 330M610 210L650 315" stroke="#303030" stroke-width="1"/><g fill="#fffefa" stroke="#303030"><circle cx="55" cy="315" r="11"/><circle cx="125" cy="330" r="11"/><circle cx="650" cy="315" r="11"/></g><g fill="#222" stroke="none" font-size="13" text-anchor="middle"><text x="55" y="320">1</text><text x="125" y="335">2</text><text x="650" y="320">3</text></g><path d="M112 190V230M605 190V230" stroke="#303030" stroke-width="1" stroke-dasharray="4 3"/></g>'''
NEREIS_NER=r'''<g class="v188-pass2-nereis-nervous"><ellipse cx="130" cy="205" rx="17" ry="11" fill="#fffefa" stroke="#303030" stroke-width="2"/><path d="M130 205L55 305" stroke="#303030" stroke-width="1"/><circle cx="55" cy="305" r="11" fill="#fffefa" stroke="#303030"/><text x="55" y="310" fill="#222" stroke="none" font-family="sans-serif" font-size="13" text-anchor="middle">1</text></g>'''
NEREIS_EXC='''<figure class="sys-fig v188-pencil-plate v188-nereis-remediation" data-v188-plate="NEREIS-06-R2" data-source-pass2="1"><figcaption><strong>Nereis — intersegmental metanephridium</strong><span class="ta-explain" lang="ta">நெரீஸ் — மெட்டாநெஃப்ரிடியல் கழிவுநீக்க அமைப்பு</span></figcaption><svg class="sys-svg" viewBox="0 0 760 500" role="img" aria-label="Nereis metanephridium opening from the coelom of one segment and discharging through the body wall of the next" xmlns="http://www.w3.org/2000/svg"><rect width="760" height="500" fill="#fffefa"/><g fill="none" stroke="#303030" stroke-linecap="round" stroke-linejoin="round"><rect x="90" y="80" width="580" height="330" stroke-width="2"/><path d="M380 80V410" stroke-width="1.2" stroke-dasharray="7 5"/><path d="M305 165q-42-32-70 0q35 36 70 0Z" stroke-width="2"/><path d="M305 165C335 185 345 235 365 255C395 285 445 255 480 285C510 310 500 345 540 360" stroke-width="2.4"/><circle cx="540" cy="360" r="7"/><path d="M235 165L55 120M385 245L620 210M540 360L665 360" stroke-width="1"/><g fill="#fffefa"><circle cx="55" cy="120" r="11"/><circle cx="620" cy="210" r="11"/><circle cx="665" cy="360" r="11"/></g></g><g fill="#222" font-family="sans-serif" font-size="13"><text x="51" y="125">1</text><text x="616" y="215">2</text><text x="661" y="365">3</text><text x="315" y="55">Segment A</text><text x="465" y="55">Segment B</text><text x="348" y="435">Septum / segment boundary</text></g></svg><div class="v188-figure-legend v188-pass2-system-legend" data-legend-en="1 Ciliated nephrostome opens from the coelom of one segment; 2 metanephridial tubule crosses the septal boundary; 3 nephridiopore opens through the body wall of the next segment" data-legend-ta="1 மெட்டாநெஃப்ரிடியல் புனல் / nephrostome ஒரு கண்டத்தின் coelom-இல் திறக்கிறது; 2 குழாய் அடுத்த கண்டத்தை நோக்கி septum-ஐ கடக்கிறது; 3 nephridiopore அடுத்த கண்டத்தின் உடற்சுவரில் திறக்கிறது"></div></figure>'''
PEN_RESP=r'''<g class="v188-pass2-penaeus-resp"><path d="M445 335H600" stroke="#303030" stroke-width="4"/><path d="M520 300V335" stroke="#303030" stroke-width="2"/><path d="M470 140Q520 125 565 145M470 195Q520 180 570 200M470 250Q520 235 565 255" stroke="#303030" stroke-width="1.8"/><path d="M565 145l-12-7m12 7l-12 7M570 200l-12-7m12 7l-12 7M565 255l-12-7m12 7l-12 7" stroke="#303030" stroke-width="1.3"/><path d="M520 335L675 345M570 200L680 205" stroke="#303030" stroke-width="1"/><g fill="#fffefa" stroke="#303030"><circle cx="675" cy="345" r="11"/><circle cx="680" cy="205" r="11"/></g><g fill="#222" stroke="none" font-family="sans-serif" font-size="13" text-anchor="middle"><text x="675" y="350">1</text><text x="680" y="210">2</text></g></g>'''
PEN_REP=r'''<g class="v188-pass2-penaeus-reproduction" fill="none" stroke="#303030" stroke-linecap="round" stroke-linejoin="round"><path d="M105 365q25-35 50 0q25-35 50 0q-50 50-100 0Z" stroke-width="2.2"/><path d="M535 365q30-18 60 0q-30 28-60 0Z" stroke-width="2.2"/><path d="M155 365L85 425M565 365L675 425" stroke-width="1"/><circle cx="85" cy="425" r="11" fill="#fffefa"/><circle cx="675" cy="425" r="11" fill="#fffefa"/><g fill="#222" stroke="none" font-family="sans-serif" font-size="13" text-anchor="middle"><text x="85" y="430">1</text><text x="675" y="430">2</text></g></g>'''
FAS_EXT=r'''<g class="v188-pass2-fasciola-external"><path d="M380 38Q365 58 350 72M380 38Q395 58 410 72" fill="none" stroke="#303030" stroke-width="1.5"/><path d="M380 45L610 45M380 105L610 105M380 365L610 365" stroke="#303030" stroke-width="1"/><g fill="#fffefa" stroke="#303030"><circle cx="610" cy="45" r="11"/><circle cx="610" cy="105" r="11"/><circle cx="610" cy="365" r="11"/></g><g fill="#222" stroke="none" font-family="sans-serif" font-size="13" text-anchor="middle"><text x="610" y="50">1</text><text x="610" y="110">2</text><text x="610" y="370">3</text></g></g>'''
FAS_EXC=r'''<g class="v188-pass2-fasciola-excretory"><ellipse cx="380" cy="330" rx="16" ry="28" fill="#fffefa" stroke="#303030" stroke-width="2"/><path d="M380 302V260M380 358V365M380 330L610 330" stroke="#303030" stroke-width="1.5"/><circle cx="610" cy="330" r="11" fill="#fffefa" stroke="#303030"/><text x="610" y="335" fill="#222" stroke="none" font-family="sans-serif" font-size="13" text-anchor="middle">1</text></g>'''
FAS_CYCLE=r'''<g class="v188-pass2-fasciola-cycle" font-family="sans-serif"><ellipse cx="385" cy="55" rx="24" ry="14" fill="#fffefa" stroke="#303030"/><ellipse cx="455" cy="55" rx="24" ry="14" fill="#fffefa" stroke="#303030"/><path d="M410 55H428M428 55l-8-5m8 5l-8 5" stroke="#303030" stroke-width="1.4"/><text x="385" y="60" text-anchor="middle" font-size="11" fill="#222" stroke="none">S</text><text x="455" y="60" text-anchor="middle" font-size="11" fill="#222" stroke="none">R</text><path d="M385 75L420 95M455 75L420 95" stroke="#303030" stroke-width="1"/></g>'''
SYCON_LS=r'''<g class="v188-pass2-sycon-ls"><path d="M250 185L80 305M300 185L80 350M320 185L650 330" stroke="#303030" stroke-width="1"/><g fill="#fffefa" stroke="#303030"><circle cx="80" cy="305" r="11"/><circle cx="80" cy="350" r="11"/><circle cx="650" cy="330" r="11"/></g><g fill="#222" stroke="none" font-family="sans-serif" font-size="13" text-anchor="middle"><text x="80" y="310">1</text><text x="80" y="355">2</text><text x="650" y="335">3</text></g></g>'''
ASC_EXT=r'''<g class="v188-pass2-ascaris-external"><path d="M170 115Q158 205 175 300M420 105Q405 205 420 295" fill="none" stroke="#777" stroke-width="1.2"/><path d="M170 210L70 270" stroke="#303030" stroke-width="1"/><circle cx="70" cy="270" r="11" fill="#fffefa" stroke="#303030"/><text x="70" y="275" fill="#222" stroke="none" font-family="sans-serif" font-size="13" text-anchor="middle">1</text></g>'''
ASC_TS=r'''<g class="v188-pass2-ascaris-ts"><circle cx="245" cy="210" r="8" fill="#fffefa" stroke="#303030" stroke-width="2"/><circle cx="515" cy="210" r="8" fill="#fffefa" stroke="#303030" stroke-width="2"/><path d="M270 265L70 300M245 210L70 250" stroke="#303030" stroke-width="1"/><g fill="#fffefa" stroke="#303030"><circle cx="70" cy="300" r="11"/><circle cx="70" cy="250" r="11"/></g><g fill="#222" stroke="none" font-family="sans-serif" font-size="13" text-anchor="middle"><text x="70" y="305">1</text><text x="70" y="255">2</text></g></g>'''
ASTER_AB=r'''<g class="v188-pass2-asterias-aboral"><path d="M330 145q-12-18-24 0m24 0q12-18 24 0" fill="none" stroke="#303030" stroke-width="1.5"/><path d="M350 120q0 18-10 28q10 10 20 0q-10-10-10-28" fill="none" stroke="#303030" stroke-width="1.3"/><path d="M330 145L90 320M350 120L90 365" stroke="#303030" stroke-width="1"/><g fill="#fffefa" stroke="#303030"><circle cx="90" cy="320" r="11"/><circle cx="90" cy="365" r="11"/></g><g fill="#222" stroke="none" font-family="sans-serif" font-size="13" text-anchor="middle"><text x="90" y="325">1</text><text x="90" y="370">2</text></g></g>'''
CSS='''<style id="v188-pass2-system-css">.v188-pass2-system-legend{font-size:.86rem;line-height:1.42;margin:.42rem .2rem .1rem;color:var(--text)}.v188-pass2-system-legend:empty:before{content:attr(data-legend-en)}html[lang="ta"] .v188-pass2-system-legend:empty:before{content:attr(data-legend-ta)}@media(max-width:420px){.v188-pass2-system-legend{font-size:.81rem}}</style>'''

ASSIGN_RE=re.compile(r'^(?P<prefix>.*\+)(?P<json>"(?:\\.|[^"\\])*")(?P<suffix>\s*;\s*)$',re.M)

def replace_whole_markup(markup,oldpid,newfigure):
    pat,m=getfig(markup,oldpid)
    return markup[:m.start()]+newfigure+markup[m.end():],1

def patch_markup(markup,counts):
    ops=[
      ('nereis-digestive',lambda x: inject(x,'nereis-digestive',NEREIS_DIG,'1 Mouth; 2 Buccal cavity; 3 Rectum before anus. Existing eversible pharynx/jaws, oesophagus and intestine are retained.','1 வாய்; 2 Buccal cavity; 3 anus-க்கு முன் rectum. ஏற்கனவே உள்ள eversible pharynx/jaws, oesophagus, intestine மாற்றப்படவில்லை.')),
      ('nereis-nervous',lambda x: inject(x,'nereis-nervous',NEREIS_NER,'1 Subpharyngeal ganglion at the junction of circumpharyngeal connectives with the ventral ganglionated cord.','1 Circumpharyngeal connectives மற்றும் ventral ganglionated cord இணையும் subpharyngeal ganglion.')),
      ('penaeus-respiratory',lambda x: inject(x,'penaeus-respiratory',PEN_RESP,'1 Gill axis/branchial unit related to thoracic appendage base; 2 directed water current passes over phyllobranchiate lamellae in the branchial chamber.','1 Thoracic appendage அடிப்பகுதியுடன் தொடர்புடைய gill/branchial unit; 2 branchial chamber-இல் phyllobranchiate lamellae மீது நீரோட்டம் செல்கிறது.')),
      ('PENAEUS-09-R1',lambda x: inject(x,'PENAEUS-09-R1',PEN_REP,'1 Petasma — male external copulatory structure; 2 Thelycum — female ventral thoracic spermatophore-receiving structure. Finer duct microtopology is not asserted.','1 பெட்டாஸ்மா — ஆண் வெளிப்புற copulatory structure; 2 தெலிக்கம் — பெண் ventral thoracic spermatophore-receiving structure. நுண்ணிய duct அமைப்பு புதிதாகக் கூறப்படவில்லை.')),
      ('fasciola-external',lambda x: inject(x,'fasciola-external',FAS_EXT,'1 Anterior cone; 2 genital-pore region between/near the anterior sucker complex; 3 posterior end. Oral and ventral suckers remain as previously labelled.','1 Anterior cone; 2 genital-pore பகுதி; 3 posterior end. Oral sucker மற்றும் ventral sucker முன்பிருந்தபடி பாதுகாக்கப்பட்டுள்ளன.')),
      ('fasciola-excretory',lambda x: inject(x,'fasciola-excretory',FAS_EXC,'1 Excretory bladder receiving the main collecting system before the posterior excretory pore.','1 posterior excretory pore-க்கு முன் main collecting system சேரும் excretory bladder.')),
      ('sycon-ls',lambda x: inject(x,'sycon-ls',SYCON_LS,'1 Pinacoderm (outer layer); 2 mesohyl between layers; 3 choanocyte-lined radial canal / choanoderm. Canal topology remains governed by the protected master flow plate.','1 Pinacoderm (வெளிப்புற அடுக்கு); 2 mesohyl; 3 choanocyte-lined radial canal / choanoderm. கால்வாய் topology மாற்றப்படவில்லை.')),
      ('ascaris-external',lambda x: inject(x,'ascaris-external',ASC_EXT,'1 Lateral line marking the longitudinal excretory-canal region. Three lips and female-straight/male-curved posterior dimorphism remain the external-scope identifiers.','1 நீள excretory-canal பகுதியைக் குறிக்கும் lateral line. Three lips மற்றும் female-straight / male-curved posterior வேறுபாடு பாதுகாக்கப்பட்டுள்ளது.')),
      ('ascaris-ts',lambda x: inject(x,'ascaris-ts',ASC_TS,'1 Pseudocoel; 2 paired lateral excretory-canal region. Cuticle/hypodermis/longitudinal muscle, intestine, uteri and dorsal/ventral nerve cords remain as previously shown.','1 Pseudocoel; 2 paired lateral excretory-canal பகுதி. ஏற்கனவே உள்ள body-wall, intestine, uteri மற்றும் nerve-cord அமைப்புகள் மாற்றப்படவில்லை.')),
      ('asterias-aboral',lambda x: inject(x,'asterias-aboral',ASTER_AB,'1 Pedicellaria; 2 papula (dermal branchia/skin gill). Spines, madreporite and anus remain in the existing aboral plate; no independent body-wall master is created.','1 Pedicellaria; 2 papula (dermal branchia / skin gill). Spines, madreporite, anus ஏற்கனவே உள்ள aboral plate-இல் தொடர்கின்றன; தனி body-wall plate உருவாக்கப்படவில்லை.')),
    ]
    out=markup
    for pid,fn in ops:
        if f'data-v188-plate="{pid}"' in out:
            out=fn(out); counts[pid]+=1
    if 'data-v188-plate="fasciola-life-cycle"' in out:
        out=inject(out,'fasciola-life-cycle',FAS_CYCLE,'Egg → miracidium → sporocyst (S) → redia (R) in snail → cercaria → metacercaria on vegetation → definitive-host ingestion → adult in bile duct → eggs.','Egg → miracidium → snail-இல் sporocyst (S) → redia (R) → cercaria → தாவரத்தில் metacercaria → definitive host உட்கொள்ளல் → bile duct adult → eggs.')
        out=replace_literal_in_figure(out,'fasciola-life-cycle','<text x="380" y="25">Snail stages</text>','<text x="380" y="25">Sporocyst → redia in snail</text>')
        counts['fasciola-life-cycle']+=1
    if 'data-v188-plate="NEREIS-06-N1"' in out:
        out,_=replace_whole_markup(out,'NEREIS-06-N1',NEREIS_EXC); counts['NEREIS-06-N1']+=1
    return out

def patch_assignments(source):
    import json
    expected=['nereis-digestive','nereis-nervous','NEREIS-06-N1','penaeus-respiratory','PENAEUS-09-R1','fasciola-external','fasciola-excretory','fasciola-life-cycle','sycon-ls','ascaris-external','ascaris-ts','asterias-aboral']
    counts={k:0 for k in expected}
    def repl(m):
        try: markup=json.loads(m.group('json'))
        except Exception: return m.group(0)
        new=patch_markup(markup,counts)
        if new==markup: return m.group(0)
        return m.group('prefix')+json.dumps(new,ensure_ascii=False)+m.group('suffix')
    out=ASSIGN_RE.sub(repl,source)
    bad={k:v for k,v in counts.items() if v!=1}
    if bad: raise SystemExit('Pass2 system figure occurrence mismatch: '+repr(bad))
    return out

def main(root):
    root=Path(root).resolve(); files=[root/'academic_payload/index.html',root/'app/src/main/assets/www/index.html']
    for p in files:
        s=p.read_text(encoding='utf-8')
        if 'id="v188-pass2-system-css"' in s: raise SystemExit('Pass2 systems already applied')
        s=patch_assignments(s)
        if '</head>' in s: s=s.replace('</head>',CSS+'\n</head>',1)
        else: s=s.replace('</style>','</style>\n'+CSS,1)
        p.write_text(s,encoding='utf-8',newline='\n')
    if files[0].read_bytes()!=files[1].read_bytes(): raise SystemExit('payload copies diverged')
    print('PASS2_SYSTEM_BLOCKERS_APPLIED=12')
    print('BUILD_ELIGIBLE=NO')

if __name__=='__main__': main(sys.argv[1] if len(sys.argv)>1 else '.')
