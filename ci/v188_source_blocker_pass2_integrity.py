#!/usr/bin/env python3
from pathlib import Path
from collections import Counter
import json,re,sys

# Pass 2 source-integrity remediation. Apply after every accepted illustration/remediation
# script and after the Pass-2 content scripts. It makes no anatomical changes.
ASSIGN_RE=re.compile(r'^(?P<prefix>.*\+)(?P<json>"(?:\\.|[^"\\])*")(?P<suffix>\s*;\s*)$',re.M)
FIG_RE=re.compile(r'(<figure\b[^>]*data-v188-plate="(?P<pid>[^"]+)"[^>]*>)(?P<body>[\s\S]*?)(</figure>)')
ID_RE=re.compile(r'\bid="([^"]+)"')
REF_RE=re.compile(r'(?:url\(#|(?:xlink:)?href="#)([^)"\s]+)')

def safe_pid(pid): return re.sub(r'[^A-Za-z0-9_.:-]+','-',pid)

def decode_assignments(source):
    items=[]
    for m in ASSIGN_RE.finditer(source):
        try: markup=json.loads(m.group('json'))
        except Exception: continue
        items.append((m,markup))
    return items

def patch_sycon(markup):
    if 'data-v188-plate="master-sycon-canal"' not in markup: return markup,0
    old='Water: ostia → incurrent canal → prosopyle → radial canal → apopyle → spongocoel → osculum'
    if old not in markup: raise SystemExit('Sycon long flow sentence not found')
    markup=markup.replace(old,'Water flow — follow arrows; full pathway below',1)
    pat=r'(<figure\b[^>]*data-v188-plate="master-sycon-canal"[^>]*>)([\s\S]*?)(</figure>)'
    m=re.search(pat,markup)
    if not m: raise SystemExit('Sycon master figure not found')
    opening,body,closing=m.groups()
    opening=opening[:-1]+' data-source-pass2-presentation="1">'
    extra='<div class="v188-figure-legend v188-pass2-flow-legend" data-legend-en="Water pathway: ostia → incurrent canal → prosopyle → radial canal → apopyle → spongocoel → osculum" data-legend-ta="Water pathway: ostia → உட்பாயும் கால்வாய் → prosopyle → ஆரைக் கால்வாய் → apopyle → ஸ்பாஞ்சோகோயல் → ஆஸ்குலம்"></div>'
    markup=markup[:m.start()]+opening+body+extra+closing+markup[m.end():]
    return markup,1

def all_ids(markups):
    return [x for markup in markups for x in ID_RE.findall(markup)]

def scope_duplicate_ids(markups):
    counts=Counter(all_ids(markups)); dups={k for k,v in counts.items() if v>1}
    out=[]; renamed=0
    for markup in markups:
        def f_repl(fm):
            nonlocal renamed
            opening,body,closing=fm.group(1),fm.group('body'),fm.group(4)
            pid=re.search(r'data-v188-plate="([^"]+)"',opening).group(1)
            ids=ID_RE.findall(body)
            for old in sorted(set(ids)&dups):
                if ids.count(old)!=1:
                    raise SystemExit(f'ambiguous repeated ID inside one figure: {pid}:{old}')
                new=safe_pid(pid)+'--'+old
                body=body.replace(f'id="{old}"',f'id="{new}"',1)
                body=body.replace(f'url(#{old})',f'url(#{new})')
                body=body.replace(f'href="#{old}"',f'href="#{new}"')
                body=body.replace(f'xlink:href="#{old}"',f'xlink:href="#{new}"')
                renamed+=1
            return opening+body+closing
        out.append(FIG_RE.sub(f_repl,markup))
    return out,renamed,dups

def audit(markups):
    ids=all_ids(markups); dup_count=sum(v-1 for v in Counter(ids).values() if v>1)
    idset=set(ids); refs=[r for m in markups for r in REF_RE.findall(m)]
    broken=sorted({r for r in refs if r not in idset})
    figure_ids=[pid for m in markups for pid in re.findall(r'data-v188-plate="([^"]+)"',m)]
    duplicate_figure_ids=sum(v-1 for v in Counter(figure_ids).values() if v>1)
    return dup_count,broken,duplicate_figure_ids,len(ids),len(refs)

def transform_source(source):
    items=decode_assignments(source)
    if not items: raise SystemExit('no serialized ORG_SYSTEM_DIAGRAMS assignments found')
    markups=[x[1] for x in items]
    sycon_hits=0
    for i,m in enumerate(markups):
        markups[i],n=patch_sycon(m); sycon_hits+=n
    if sycon_hits!=1: raise SystemExit(f'expected one Sycon master, found {sycon_hits}')
    markups,renamed,initial_dups=scope_duplicate_ids(markups)
    dup,broken,dup_fig,total_ids,total_refs=audit(markups)
    if dup: raise SystemExit(f'duplicate SVG IDs remain: {dup}')
    if broken: raise SystemExit('broken internal SVG refs: '+repr(broken))
    if dup_fig: raise SystemExit(f'duplicate data-v188-plate IDs remain: {dup_fig}')
    pieces=[]; last=0
    for (m,_),markup in zip(items,markups):
        pieces.append(source[last:m.start()])
        pieces.append(m.group('prefix')+json.dumps(markup,ensure_ascii=False)+m.group('suffix'))
        last=m.end()
    pieces.append(source[last:])
    out=''.join(pieces)
    return out,renamed,len(initial_dups),total_ids,total_refs

def main(root):
    root=Path(root).resolve(); files=[root/'academic_payload/index.html',root/'app/src/main/assets/www/index.html']
    results=[]
    for p in files:
        s=p.read_text(encoding='utf-8')
        if 'data-source-pass2-presentation=\\"1\\"' in s or 'data-source-pass2-presentation="1"' in s:
            raise SystemExit('Pass2 integrity already applied')
        out,renamed,classes,total_ids,total_refs=transform_source(s)
        p.write_text(out,encoding='utf-8',newline='\n'); results.append((renamed,classes,total_ids,total_refs))
    if files[0].read_bytes()!=files[1].read_bytes(): raise SystemExit('payload copies diverged')
    if results[0]!=results[1]: raise SystemExit('integrity results diverged')
    renamed,classes,total_ids,total_refs=results[0]
    print('PASS2_DUPLICATE_ID_CLASSES_SCOPED='+str(classes))
    print('PASS2_ID_DEFINITIONS_RENAMED='+str(renamed))
    print('PASS2_DUPLICATE_SVG_DOM_IDS=0')
    print('PASS2_BROKEN_INTERNAL_SVG_REFERENCES=0')
    print('PASS2_TOTAL_SVG_IDS='+str(total_ids))
    print('PASS2_TOTAL_INTERNAL_REFS='+str(total_refs))
    print('PASS2_SYCON_TOPOLOGY_CHANGED=NO')
    print('BUILD_ELIGIBLE=NO')

if __name__=='__main__': main(sys.argv[1] if len(sys.argv)>1 else '.')
