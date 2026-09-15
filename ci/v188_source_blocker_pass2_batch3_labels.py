#!/usr/bin/env python3
from pathlib import Path
import re, sys

# Pass 2: visible instructional callouts for the 20 already-created Batch-3 plates.
# Geometry/topology is deliberately untouched. Apply after v188_pencil_atlas_completion_batch3.py.

def callout_group(items):
    parts=['<g class="v188-pass2-callouts" font-family="sans-serif">']
    for n,cx,cy,tx,ty in items:
        parts.append(f'<path d="M{cx} {cy}L{tx} {ty}" fill="none" stroke="#303030" stroke-width="1"/>')
        parts.append(f'<circle cx="{cx}" cy="{cy}" r="11" fill="#fffefa" stroke="#303030" stroke-width="1.2"/>')
        parts.append(f'<text x="{cx}" y="{cy+5}" text-anchor="middle" font-size="13" fill="#222" stroke="none">{n}</text>')
    parts.append('</g>')
    return ''.join(parts)

C={
'amoeba':[(1,70,90,625,180),(2,70,150,360,215),(3,70,210,275,250),(4,70,270,455,165)],
'paramecium':[(1,70,95,300,180),(2,70,155,315,260),(3,690,110,410,210),(4,690,170,445,215)],
'entamoeba':[(1,60,90,170,150),(2,60,150,195,190),(3,690,120,500,205),(4,690,185,540,170)],
'trypanosoma':[(1,70,90,650,105),(2,70,150,300,190),(3,690,150,350,215),(4,690,215,155,238)],
'leishmania':[(1,70,90,240,210),(2,70,150,275,40),(3,70,210,245,135),(4,690,125,520,210)],
'plasmodium':[(1,80,75,140,130),(2,300,45,370,100),(3,675,85,590,150),(4,650,375,500,330),(5,120,375,235,330)],
'porifera-canals':[(1,85,390,160,225),(2,335,390,380,225),(3,585,390,600,220)],
'aurelia':[(1,80,80,380,90),(2,80,150,325,165),(3,680,110,380,245),(4,680,180,505,380)],
'physalia':[(1,80,85,350,120),(2,80,155,285,205),(3,680,170,470,260)],
'coral':[(1,85,75,380,90),(2,85,140,380,185),(3,675,150,380,235),(4,675,225,380,385)],
'taenia':[(1,90,70,380,80),(2,90,135,360,65),(3,670,100,380,145),(4,670,180,380,280)],
'taenia-cycle':[(1,80,65,150,110),(2,300,45,380,90),(3,675,70,575,120),(4,675,375,550,330),(5,120,375,270,330)],
'nematode-parasites':[(1,85,70,140,220),(2,260,70,290,220),(3,440,70,430,220),(4,620,70,575,220)],
'peripatus':[(1,85,80,105,155),(2,85,145,120,205),(3,670,180,330,340),(4,670,245,530,335)],
'crustacean-larvae':[(1,90,80,150,210),(2,350,70,380,190),(3,620,70,570,190)],
'pest-ipm':[(1,380,30,380,70),(2,690,120,580,170),(3,660,400,510,360),(4,100,400,250,360),(5,70,120,180,170)],
'mollusc-plan':[(1,90,75,350,120),(2,90,145,390,175),(3,680,120,180,260),(4,680,190,360,330),(5,680,260,500,285)],
'gastropod-torsion':[(1,90,80,230,170),(2,380,55,410,95),(3,670,80,560,170)],
'cephalopod':[(1,90,80,360,100),(2,90,145,300,245),(3,680,120,220,280),(4,680,200,390,350)],
'echinoderm-larvae':[(1,90,70,190,210),(2,380,55,395,210),(3,660,70,600,210)],
}

L={
'amoeba':('1 Pseudopodium; 2 Nucleus; 3 Food vacuole; 4 Contractile vacuole','1 போலிக்கால்; 2 உட்கரு; 3 உணவு நுண்குமிழ்; 4 சுருங்கும் நுண்குமிழ்'),
'paramecium':('1 Oral groove; 2 Cytostome / cytopharynx; 3 Macronucleus; 4 Micronucleus','1 வாய்ப்பள்ளம்; 2 Cytostome / Cytopharynx; 3 Macronucleus; 4 Micronucleus'),
'entamoeba':('1 Trophozoite; 2 Nucleus; 3 Mature cyst; 4 Four nuclei of mature cyst','1 Trophozoite; 2 உட்கரு; 3 முதிர்ந்த cyst; 4 முதிர்ந்த cyst-இன் நான்கு உட்கருக்கள்'),
'trypanosoma':('1 Free flagellum; 2 Undulating membrane; 3 Nucleus; 4 Kinetoplast','1 Flagellum; 2 Undulating membrane; 3 உட்கரு; 4 Kinetoplast'),
'leishmania':('1 Promastigote; 2 Free flagellum; 3 Kinetoplast region; 4 Intracellular amastigotes','1 Promastigote; 2 Flagellum; 3 Kinetoplast பகுதி; 4 செலுக்குள் Amastigote வடிவங்கள்'),
'plasmodium':('1 Infected mosquito / sporozoite entry; 2 Liver schizogony → merozoites; 3 Erythrocytic cycle; 4 Gametocytes; 5 Mosquito sexual/sporogonic phase → sporozoites','1 தொற்றிய கொசு / sporozoite நுழைவு; 2 கல்லீரல் நிலை → merozoites; 3 சிவப்பணு சுழற்சி; 4 Gametocytes; 5 கொசுவில் பாலியல் / sporogonic நிலை → sporozoites'),
'porifera-canals':('1 Asconoid; 2 Syconoid; 3 Leuconoid — increasing canal-system complexity','1 Asconoid; 2 Syconoid; 3 Leuconoid — கால்வாய் அமைப்பின் சிக்கல்தன்மை அதிகரிப்பு'),
'aurelia':('1 Umbrella / bell; 2 Gonadal region; 3 Manubrium / oral region; 4 Oral arms / marginal tentacular region','1 Umbrella / bell; 2 Gonad பகுதி; 3 Manubrium / வாய்ப்பகுதி; 4 Oral arms / விளிம்பு tentacle பகுதி'),
'physalia':('1 Pneumatophore (float); 2 Polymorphic zooid-bearing region; 3 Long defensive/tentacular zooid region','1 Pneumatophore (மிதவை); 2 பலவடிவ zooid பகுதி; 3 நீண்ட பாதுகாப்பு / tentacle zooid பகுதி'),
'coral':('1 Tentacles; 2 Mouth; 3 Gastrovascular cavity; 4 Calcareous corallite / basal skeleton','1 Tentacles; 2 வாய்; 3 Gastrovascular cavity; 4 சுண்ணாம்பு corallite / அடிப்படை எலும்பமைப்பு'),
'taenia':('1 Scolex; 2 Suckers / armed rostellum region; 3 Neck; 4 Progressive proglottids (immature → mature → gravid)','1 Scolex; 2 Suckers / armed rostellum பகுதி; 3 கழுத்து; 4 Proglottids — immature → mature → gravid'),
'taenia-cycle':('1 Human definitive host / adult; 2 Eggs or gravid segments; 3 Pig intermediate host; 4 Cysticercus in pork; 5 Ingestion → adult tapeworm','1 மனித definitive host / adult; 2 Eggs / gravid segments; 3 பன்றி intermediate host; 4 Cysticercus; 5 உட்கொள்ளல் → adult tapeworm'),
'nematode-parasites':('1 Ascaris lumbricoides; 2 Wuchereria bancrofti; 3 Enterobius vermicularis; 4 Ancylostoma duodenale','1 Ascaris lumbricoides; 2 Wuchereria bancrofti; 3 Enterobius vermicularis; 4 Ancylostoma duodenale'),
'peripatus':('1 Antennae; 2 Oral papilla / slime-gland opening region; 3 Unjointed lobopod legs; 4 Terminal claws','1 Antennae; 2 Oral papilla / slime-gland திறப்பு பகுதி; 3 மூட்டில்லா lobopod கால்கள்; 4 இறுதி claws'),
'crustacean-larvae':('1 Nauplius; 2 Protozoea-type middle larva; 3 Mysis-type late larva — representative developmental progression','1 Nauplius; 2 Protozoea-type நடுநிலை larva; 3 Mysis-type பிந்தைய larva — பிரதிநிதி வளர்ச்சி வரிசை'),
'pest-ipm':('1 Monitoring; 2 Identification + economic threshold decision; 3 Cultural/mechanical/biological controls; 4 Targeted chemical intervention only when needed; 5 Evaluation + feedback','1 கண்காணிப்பு; 2 அடையாளம் + பொருளாதார threshold முடிவு; 3 கலாசார / mechanical / biological கட்டுப்பாடுகள்; 4 தேவையானபோது மட்டும் targeted chemical control; 5 மதிப்பீடு + feedback'),
'mollusc-plan':('1 Shell + mantle; 2 Visceral mass; 3 Head region; 4 Muscular foot; 5 Mantle cavity / posterior opening region','1 ஓடு + mantle; 2 visceral mass; 3 தலைப்பகுதி; 4 தசைமிகு foot; 5 mantle cavity / பின்திறப்பு பகுதி'),
'gastropod-torsion':('1 Pre-torsion larva; 2 180° torsion during larval development; 3 Post-torsion condition with mantle complex displaced anteriorly','1 Torsion-க்கு முன் larva; 2 larval development-இல் 180° torsion; 3 torsion பிந்தைய நிலையில் mantle complex முன்புறம்'),
'cephalopod':('1 Mantle; 2 Head with eye; 3 Funnel; 4 Arms / tentacles','1 Mantle; 2 கண் உடைய தலை; 3 Funnel; 4 Arms / tentacles'),
'echinoderm-larvae':('1 Asteroid larva — Bipinnaria; 2 Echinoid larva — Echinopluteus; 3 Holothuroid larva — Auricularia','1 Asteroid larva — Bipinnaria; 2 Echinoid larva — Echinopluteus; 3 Holothuroid larva — Auricularia'),
}

CSS='''<style id="v188-pass2-batch3-callout-css">.v188-pass2-legend{font-size:.86rem;line-height:1.42;margin:.42rem .2rem .1rem;color:var(--text)}.v188-pass2-legend:empty:before{content:attr(data-legend-en)}html[lang="ta"] .v188-pass2-legend:empty:before{content:attr(data-legend-ta)}@media(max-width:420px){.v188-pass2-legend{font-size:.81rem}}</style>'''

ASSIGN_RE=re.compile(r'^(?P<prefix>.*\+)(?P<json>"(?:\\.|[^"\\])*")(?P<suffix>\s*;\s*)$',re.M)

def patch_markup(markup,pid):
    pat=rf'(<figure\b[^>]*data-v188-plate="{re.escape(pid)}"[^>]*>)([\s\S]*?)(</figure>)'
    m=re.search(pat,markup)
    if not m: return markup,0
    opening,body,closing=m.groups()
    if 'data-source-pass2-labels="1"' in opening: raise SystemExit(f'Pass2 labels already applied: {pid}')
    if '</svg>' not in body: raise SystemExit(f'No SVG terminator: {pid}')
    body=body.replace('</svg>',callout_group(C[pid])+'</svg>',1)
    en,ta=L[pid]
    body+=f'<div class="v188-figure-legend v188-pass2-legend" data-legend-en="{en}" data-legend-ta="{ta}"></div>'
    opening=opening[:-1]+' data-source-pass2-labels="1">'
    return markup[:m.start()]+opening+body+closing+markup[m.end():],1

def patch_assignments(source):
    seen={pid:0 for pid in C}
    def repl(m):
        import json
        try: markup=json.loads(m.group('json'))
        except Exception: return m.group(0)
        changed=False
        for pid in C:
            markup,n=patch_markup(markup,pid)
            if n: seen[pid]+=n; changed=True
        if not changed: return m.group(0)
        return m.group('prefix')+json.dumps(markup,ensure_ascii=False)+m.group('suffix')
    out=ASSIGN_RE.sub(repl,source)
    bad={k:v for k,v in seen.items() if v!=1}
    if bad: raise SystemExit('Batch-3 figure occurrence mismatch: '+repr(bad))
    return out

def main(root):
    root=Path(root).resolve()
    files=[root/'academic_payload/index.html',root/'app/src/main/assets/www/index.html']
    for p in files:
        s=p.read_text(encoding='utf-8')
        if 'id="v188-pass2-batch3-callout-css"' in s: raise SystemExit('Pass2 Batch-3 CSS already applied')
        s=patch_assignments(s)
        if '</head>' in s: s=s.replace('</head>',CSS+'\n</head>',1)
        else: s=s.replace('</style>', '</style>\n'+CSS,1)
        p.write_text(s,encoding='utf-8',newline='\n')
    if files[0].read_bytes()!=files[1].read_bytes(): raise SystemExit('payload copies diverged')
    print('PASS2_BATCH3_LABELLED='+str(len(C)))
    print('BUILD_ELIGIBLE=NO')

if __name__=='__main__': main(sys.argv[1] if len(sys.argv)>1 else '.')
