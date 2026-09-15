#!/usr/bin/env python3
from pathlib import Path
import json,re,sys

# Pass 2 terminology closure: no anatomy. Scientific/eponym terms are retained where
# the accepted project does not establish a stable Tamil equivalent.
ASSIGN_RE=re.compile(r'^(?P<prefix>window\.ORG_SYSTEM_DIAGRAMS\[[^\n]*?\]\s*=\s*[^\n]*?\+)(?P<json>"(?:\\.|[^"\\])*")(?P<suffix>\s*;\s*)$',re.M)

REPL={
'FASCIOLA-REP-R1':(
 'ஃபாசியோலா — இனப்பெருக்க அமைப்பு',
 "Fasciola reproductive system: testes, male ducts, ovary, oviduct, Ootype / Mehlis’ gland, vitellaria and uterus — specialised scientific terms are retained rather than assigned an unverified Tamil equivalent."
),
'PILA-EXC-N1':(
 'பைலா — கழிவுநீக்க அமைப்பு',
 'Organ of Bojanus (சிறுநீரகம்); nephrostome தொடர்பு; mantle cavity-க்கு excretory opening. Organ of Bojanus என்ற அறிவியல் eponym தக்கவைக்கப்பட்டுள்ளது.'
),
'NEREIS-07-N1':(
 'நெரீஸ் — இனப்பெருக்க epitoky மற்றும் வளர்ச்சி',
 'Heteronereis — இனப்பெருக்க epitoke வடிவம்; gamete release மற்றும் external fertilisation; trochophore larva. Heteronereis என்ற அறிவியல் பெயர் தக்கவைக்கப்பட்டுள்ளது.'
),
'PENAEUS-09-R1':(
 'பெனேயஸ் — ஆண் மற்றும் பெண் இனப்பெருக்க அமைப்பு',
 'ஆண்: testis மற்றும் reproductive duct; பெண்: ovary மற்றும் oviduct. பெட்டாஸ்மா (petasma) மற்றும் தெலிக்கம் (thelycum) என்பது ஏற்றுக்கொள்ளப்பட்ட பாடத் தமிழாக்க/ஒலிபெயர்ப்பு வடிவங்கள்.'
),
}

def patch_figure(markup,pid,caption_ta,legend_ta):
    pat=rf'(<figure\b[^>]*data-v188-plate="{re.escape(pid)}"[^>]*>)([\s\S]*?)(</figure>)'
    m=re.search(pat,markup)
    if not m: return markup,0
    opening,body,closing=m.groups()
    if 'data-source-pass2-terminology="1"' in opening: raise SystemExit(f'terminology already applied: {pid}')
    body,n1=re.subn(r'<span class="ta-explain" lang="ta">TERMINOLOGY REVIEW PENDING</span>',f'<span class="ta-explain" lang="ta">{caption_ta}</span>',body,count=1)
    body,n2=re.subn(r'data-legend-ta="TERMINOLOGY REVIEW PENDING"',f'data-legend-ta="{legend_ta}"',body,count=1)
    if n1+n2==0: raise SystemExit(f'no terminology placeholder found in {pid}')
    opening=opening[:-1]+' data-source-pass2-terminology="1">'
    return markup[:m.start()]+opening+body+closing+markup[m.end():],1

def transform(source):
    counts={k:0 for k in REPL}
    def repl(m):
        try: markup=json.loads(m.group('json'))
        except Exception: return m.group(0)
        original=markup
        for pid,(cap,leg) in REPL.items():
            markup,n=patch_figure(markup,pid,cap,leg); counts[pid]+=n
        if markup==original: return m.group(0)
        return m.group('prefix')+json.dumps(markup,ensure_ascii=False)+m.group('suffix')
    out=ASSIGN_RE.sub(repl,source)
    bad={k:v for k,v in counts.items() if v!=1}
    if bad: raise SystemExit('terminology figure occurrence mismatch: '+repr(bad))
    return out

def main(root):
    root=Path(root).resolve(); files=[root/'academic_payload/index.html',root/'app/src/main/assets/www/index.html']
    for p in files:
        p.write_text(transform(p.read_text(encoding='utf-8')),encoding='utf-8',newline='\n')
    if files[0].read_bytes()!=files[1].read_bytes(): raise SystemExit('payload copies diverged')
    print('PASS2_TERMINOLOGY_PLACEHOLDERS_RESOLVED=4')
    print('PASS2_PETASMA_TAMIL=பெட்டாஸ்மா')
    print('PASS2_THELYCUM_TAMIL=தெலிக்கம்')
    print('NONBLOCKING_SCIENTIFIC_TERM_REVIEW=Fasciola ootype/Mehlis;Pila Organ of Bojanus;Nereis Heteronereis;Earthworm specialist terms')
    print('BUILD_ELIGIBLE=NO')

if __name__=='__main__': main(sys.argv[1] if len(sys.argv)>1 else '.')
