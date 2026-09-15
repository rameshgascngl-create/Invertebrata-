#!/usr/bin/env python3
from pathlib import Path
import json,sys

# INVERTEBRATA v1.8.8 — Penaeus p34 targeted rectification only.
# Authorized: PENAEUS-01,03,05,08,09. 02/04/06/07 are intentionally untouched.
# No Gradle invocation. No workflow modification. No pipeline reconciliation.

STYLE='''<defs><pattern id="pH" width="9" height="9" patternUnits="userSpaceOnUse" patternTransform="rotate(18)"><path d="M0 0V9" stroke="#777" stroke-width=".55" opacity=".28"/></pattern></defs>'''
def fig(pid,title,ta,aria,body,enlegend):
 return f'''<figure class="sys-fig v188-pencil-plate v188-penaeus-remediation" data-v188-plate="{pid}" data-v188-requirement="{pid.split('-')[1]}"><figcaption><strong>{title}</strong><span class="ta-explain" lang="ta">{ta}</span></figcaption><svg class="sys-svg" viewBox="0 0 760 470" role="img" aria-label="{aria}" xmlns="http://www.w3.org/2000/svg"><rect width="760" height="470" fill="#fffefa"/>{STYLE}<g fill="none" stroke="#303030" stroke-linecap="round" stroke-linejoin="round">{body}</g></svg><div class="v188-figure-legend" data-legend-en="{enlegend}" data-legend-ta="TERMINOLOGY REVIEW PENDING"></div></figure>'''

EXT=fig('PENAEUS-01-R1','Penaeus indicus — external morphology','பெனேயஸ் இண்டிகஸ் — புற அமைப்பு','Pencil-style Penaeus external morphology showing rostrum, eye, antennules, antennae, cephalothorax, six abdominal somites, appendage-bearing regions, uropods and telson.',r'''
<path d="M150 210 Q185 115 390 135 Q525 145 625 205 Q650 225 620 250 Q500 285 380 260 Q200 278 145 230Z" fill="url(#pH)" stroke-width="3.2"/>
<!-- anatomically distinct rostrum from anterior carapace --> <path d="M158 180 L65 155 L145 205" stroke-width="3.2"/><path d="M75 158l12-9m5 14l12-9m5 14l12-9" stroke-width="1.2"/>
<circle cx="170" cy="178" r="10" fill="#303030"/><path d="M170 178l-28-38" stroke-width="2"/><path d="M160 195Q95 95 40 95 M165 200Q95 140 35 145" stroke-width="1.8"/>
<path d="M390 135V260 M432 142V266 M474 151V266 M516 162V262 M558 177V255 M596 193V248" stroke-width="1.2"/>
<!-- thoracic walking appendage region --> <path d="M225 245l-35 75 M260 250l-25 82 M295 253l-10 85 M330 255l8 82 M360 257l25 75" stroke-width="2"/>
<!-- abdominal pleopods --> <path d="M420 262l-18 55 M455 266l-14 55 M490 265l-10 52 M525 260l-6 48 M560 252l0 48" stroke-width="1.8"/>
<!-- tail fan --> <path d="M620 210l70-48 M625 225l78 0 M620 242l70 48" stroke-width="2.4"/>
<!-- exact leaders --> <g stroke-width="1"><path d="M105 166L45 230"/><path d="M170 178L95 260"/><path d="M95 115L40 55"/><path d="M105 150L150 55"/><path d="M285 140L280 55"/><path d="M455 150L475 55"/><path d="M285 300L250 405"/><path d="M480 300L480 405"/><path d="M670 225L690 350"/></g>
<g fill="#222" stroke="none" font-family="sans-serif" font-size="13"><text x="10" y="245">Rostrum</text><text x="25" y="280">Stalked compound eye</text><text x="5" y="45">Antennules</text><text x="135" y="45">Antennae</text><text x="220" y="45">Cephalothorax / carapace</text><text x="420" y="45">Abdomen — six somites</text><text x="180" y="430">Pereiopod region</text><text x="420" y="430">Pleopod region</text><text x="625" y="375">Uropods + telson</text></g>
''','Rostrum; stalked compound eye; antennules; antennae; cephalothorax/carapace; six abdominal somites; pereiopod region; pleopod region; uropods and telson')

# PENAEUS-03 child figures: one requirement, four child SVGs; do not count as four organism requirements.
SERIAL=fig('PENAEUS-03-OVERVIEW','Penaeus — specialised appendage serial map','பெனேயஸ் — சிறப்புமாற்றமடைந்த இணைப்புறுப்புகளின் தொடர் வரைபடம்','Serial map of Penaeus appendages by cephalic, thoracic and abdominal body region.',r'''
<path d="M70 180H690" stroke-width="3"/><path d="M70 160V200 M275 150V210 M520 150V210 M690 160V200" stroke-width="2"/>
<g fill="#222" stroke="none" font-family="sans-serif" font-size="13"><text x="95" y="115">CEPHALIC</text><text x="310" y="115">THORACIC</text><text x="555" y="115">ABDOMINAL</text><text x="80" y="240">Antennule → Antenna → Mandible → Maxillula → Maxilla</text><text x="290" y="275">Maxillipeds I–III → Pereiopods I–V</text><text x="525" y="310">Pleopods I–V → Uropods on somite VI</text><text x="80" y="365">Serial position is shown by body region; detailed modifications are separated below to preserve phone readability.</text></g>
''','Cephalic: antennule, antenna, mandible, maxillula, maxilla; thoracic: maxillipeds I–III, pereiopods I–V; abdominal: pleopods I–V, uropods on sixth abdominal somite')
CEPH=fig('PENAEUS-03-CEPHALIC','Penaeus — cephalic appendages','பெனேயஸ் — தலைப்பகுதி இணைப்புறுப்புகள்','Cephalic appendage series of Penaeus showing identity and distinct sensory versus feeding modifications.',r'''
<g stroke-width="2"><path d="M95 170v90m0-45l-45-65m45 65l45-65"/><path d="M220 170v90m0-45l-75-80m75 80l70-45"/><path d="M350 170q35 45 0 90l-35-15m35 15l35-15"/><path d="M475 170q25 45 0 90l-35-35m35 35l40-25"/><path d="M610 170q25 45 0 90l-40-45m40 45l55-20"/></g><g fill="#222" stroke="none" font-family="sans-serif" font-size="13" text-anchor="middle"><text x="95" y="305">1 Antennule</text><text x="220" y="305">2 Antenna</text><text x="350" y="305">3 Mandible</text><text x="475" y="305">4 Maxillula</text><text x="610" y="305">5 Maxilla</text><text x="380" y="365">Sensory appendages → feeding appendages; attached serially to the cephalic region</text></g>
''','1 Antennule — sensory/equilibrium; 2 Antenna — sensory, scale-like exopod; 3 Mandible — jaw; 4 Maxillula — food handling; 5 Maxilla — food handling')
THOR=fig('PENAEUS-03-THORACIC','Penaeus — thoracic appendages','பெனேயஸ் — மார்புப்பகுதி இணைப்புறுப்புகள்','Thoracic appendage series of Penaeus showing three maxillipeds followed by five pereiopods.',r'''
<path d="M70 125H690" stroke-width="2"/><g stroke-width="2"><path d="M115 125l-30 130 M180 125l-25 130 M245 125l-20 130 M340 125l-35 155 M410 125l-20 155 M480 125v155 M550 125l20 155 M620 125l35 155"/></g><g fill="#222" stroke="none" font-family="sans-serif" font-size="12" text-anchor="middle"><text x="115" y="300">Mxpd I</text><text x="180" y="300">Mxpd II</text><text x="245" y="300">Mxpd III</text><text x="340" y="325">P1</text><text x="410" y="325">P2</text><text x="480" y="325">P3</text><text x="550" y="325">P4</text><text x="620" y="325">P5</text><text x="380" y="380">Three maxillipeds for food handling precede five pereiopods for walking/feeding</text></g>
''','Maxillipeds I–III; Pereiopods I–V. Serial attachment: eight thoracic appendage pairs in this teaching sequence')
ABD=fig('PENAEUS-03-ABDOMINAL','Penaeus — abdominal appendages','பெனேயஸ் — வயிற்றுப்பகுதி இணைப்புறுப்புகள்','Abdominal appendage map of Penaeus showing pleopods on somites one to five and uropods on somite six.',r'''
<path d="M80 140H680" stroke-width="3"/><g stroke-width="1"><path d="M170 120V160 M260 120V160 M350 120V160 M440 120V160 M530 120V160"/></g><g stroke-width="2"><path d="M125 140l-15 120 M215 140l-12 120 M305 140l-8 120 M395 140l8 120 M485 140l12 120"/><path d="M610 140l-55 100m55-100l55 100" stroke-width="3"/></g><g fill="#222" stroke="none" font-family="sans-serif" font-size="12" text-anchor="middle"><text x="125" y="300">Pleopod I</text><text x="215" y="300">II</text><text x="305" y="300">III</text><text x="395" y="300">IV</text><text x="485" y="300">V</text><text x="610" y="300">Uropods — somite VI</text><text x="380" y="370">Pleopods are swimmerets; uropods flank the telson to form the tail fan</text></g>
''','Pleopods I–V on abdominal somites I–V; paired uropods on somite VI; swimming and tail-fan specialisation')
APP=SERIAL+CEPH+THOR+ABD

DIG=fig('PENAEUS-05-R1','Penaeus — digestive system and gastric regions','பெனேயஸ் — செரிமான அமைப்பு மற்றும் இரைப்பைப் பகுதிகள்','Penaeus digestive system showing mouth, oesophagus, differentiated foregut stomach, gastric-mill region, hepatopancreas communicating with the digestive tract, intestine and anus.',r'''
<path d="M70 225H135L180 195" stroke-width="6"/><path d="M180 195Q210 145 285 165Q340 185 330 240Q315 285 255 275Q195 270 180 225Z" fill="url(#pH)" stroke-width="3"/>
<!-- differentiated gastric mill/filtering region without invented ossicle detail --> <path d="M245 175V265 M260 178V262 M275 180V258" stroke-width="1.4"/><path d="M330 220H675" stroke-width="6"/>
<!-- hepatopancreatic lobes with paired ducts visibly entering digestive tract --> <path d="M190 255q-70 25-45 100q55 15 85-45 M300 260q65 30 45 95q-55 15-85-45" fill="url(#pH)" stroke-width="2"/><path d="M215 280L235 235 M285 280L295 235" stroke-width="2.2"/>
<g stroke-width="1"><path d="M90 225L35 120"/><path d="M165 205L125 95"/><path d="M260 200L260 80"/><path d="M180 320L70 380"/><path d="M470 220L470 90"/><path d="M675 220L715 220"/></g><g fill="#222" stroke="none" font-family="sans-serif" font-size="13"><text x="10" y="110">Mouth</text><text x="80" y="85">Oesophagus</text><text x="195" y="70">Stomach: gastric-mill/filtering region</text><text x="10" y="405">Hepatopancreas / digestive gland; ducts enter digestive tract</text><text x="440" y="80">Intestine</text><text x="700" y="205">Anus</text></g>
''','Mouth; oesophagus; stomach with identifiable gastric-mill/filtering region; hepatopancreas/digestive gland with duct relationship; intestine; anus')

EXC=fig('PENAEUS-08-N1','Penaeus — antennal (green) gland excretory system','பெனேயஸ் — ஆண்டென்னல் (கிரீன்) சுரப்பி கழிவுநீக்க அமைப்பு','Penaeus-specific excretory plate locating the antennal or green gland at the antenna base and showing only the opening relationship supported by the accepted lesson/reference.',r'''
<!-- anterior cephalothorax/antenna base context --> <path d="M120 240Q190 125 390 165Q450 180 490 220" stroke-width="3"/><path d="M160 205Q95 120 35 110" stroke-width="2"/><circle cx="165" cy="205" r="8" fill="#303030"/>
<!-- gland shown at antennal base; unsupported internal sac/duct subdivisions deliberately omitted --> <path d="M190 215q35-35 70 0q-35 55-70 0Z" fill="url(#pH)" stroke-width="2.5"/><path d="M190 215Q175 205 165 205" stroke-width="2"/>
<g stroke-width="1"><path d="M165 205L65 300"/><path d="M225 220L365 300"/><path d="M180 208L330 95"/></g><g fill="#222" stroke="none" font-family="sans-serif" font-size="13"><text x="10" y="325">Antenna base / external opening region</text><text x="330" y="325">Antennal (green) gland</text><text x="300" y="85">Position in anterior cephalothorax</text><text x="150" y="400">Finer sac/bladder/duct subdivisions: REFERENCE RESOLUTION REQUIRED — not invented</text></g>
''','Antennal (green) gland; position at antenna base; external opening region. Finer internal pathway remains REFERENCE RESOLUTION REQUIRED')

REP=fig('PENAEUS-09-R1','Penaeus — male and female reproductive organisation','பெனேயஸ் — ஆண் மற்றும் பெண் இனப்பெருக்க அமைப்பு','Separate male and female Penaeus reproductive schematics showing only sex-specific structures and relationships supported by accepted evidence.',r'''
<path d="M380 60V400" stroke-width="1" stroke-dasharray="7 6"/>
<!-- male --> <g stroke-width="2.4"><ellipse cx="185" cy="135" rx="55" ry="25" fill="url(#pH)"/><path d="M185 160Q210 225 225 295"/><path d="M225 295L260 330"/><path d="M150 345q35-35 70 0q-35 35-70 0Z"/></g>
<!-- female --> <g stroke-width="2.4"><ellipse cx="565" cy="135" rx="70" ry="28" fill="url(#pH)"/><path d="M565 163Q545 225 530 295"/><path d="M530 295L500 330"/><path d="M535 345q30-22 60 0q-30 22-60 0Z"/></g>
<g stroke-width="1"><path d="M185 135L75 95"/><path d="M225 250L80 245"/><path d="M260 330L100 380"/><path d="M565 135L680 95"/><path d="M530 250L680 245"/><path d="M500 330L660 380"/></g><g fill="#222" stroke="none" font-family="sans-serif" font-size="13"><text x="155" y="45">MALE</text><text x="535" y="45">FEMALE</text><text x="15" y="85">Testis</text><text x="15" y="250">Male reproductive duct</text><text x="15" y="405">Terminal opening region</text><text x="650" y="85">Ovary</text><text x="625" y="250">Oviduct</text><text x="600" y="405">Female genital-opening region</text><text x="80" y="440">Exact internal duct/opening micro-topology beyond p34/accepted lesson: REFERENCE RESOLUTION REQUIRED</text></g>
''','Male: testis, reproductive duct, terminal opening region. Female: ovary, oviduct, genital-opening region. Sexes are separate; unsupported finer duct topology is not asserted')

NEW=EXT+APP+DIG+EXC+REP

def main(root):
 root=Path(root).resolve(); targets=[root/'academic_payload/index.html',root/'app/src/main/assets/www/index.html']
 inj='<script id="v188-penaeus-targeted-rectification">\nwindow.ORG_SYSTEM_DIAGRAMS["u4-penaeus"]=(window.ORG_SYSTEM_DIAGRAMS["u4-penaeus"]||"")+'+json.dumps(NEW,ensure_ascii=False)+';\n</script>\n'
 for p in targets:
  s=p.read_text(encoding='utf-8')
  if 'id="v188-penaeus-targeted-rectification"' in s: raise SystemExit('already applied')
  anchor='<script id="v188-contextual-atlas">'
  if anchor not in s: raise SystemExit('contextual atlas anchor missing')
  s=s.replace(anchor,inj+anchor,1); p.write_text(s,encoding='utf-8',newline='\n')
 if targets[0].read_bytes()!=targets[1].read_bytes(): raise SystemExit('payload copies diverged')
 print('RECTIFIED_EXISTING=PENAEUS-01,PENAEUS-05,PENAEUS-09')
 print('NEW_REQUIRED=PENAEUS-03,PENAEUS-08')
 print('UNTOUCHED=PENAEUS-02,PENAEUS-04,PENAEUS-06,PENAEUS-07')
 print('BUILD_ELIGIBLE=NO')
if __name__=='__main__': main(sys.argv[1])
