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
    require(sha(p)==OLD_PAYLOAD,'unexpected academic payload before v1.8.8 display patch')
    marker='</style>'
    require(marker in s,'style terminator missing')
    css=r'''
/* v1.8.8 Android mobile reading-surface correction.
   Prevent WebView text autosizing and descendant intrinsic widths from
   widening the lesson pane. Figures/tables retain their own controlled
   horizontal/zoom behaviour. */
html {
  -webkit-text-size-adjust: 100%;
  text-size-adjust: 100%;
}
html, body, #widget, #inv-type-lab-v4,
#inv-type-lab-v4 .theory-mount,
#inv-type-lab-v4 .theory-pane,
#inv-type-lab-v4 .chapter-section,
#inv-type-lab-v4 .expanded-lesson,
#inv-type-lab-v4 .lesson-group,
#inv-type-lab-v4 .lesson-group-body,
#inv-type-lab-v4 .lesson-point {
  min-width: 0 !important;
  max-width: 100% !important;
}
#inv-type-lab-v4 .theory-pane,
#inv-type-lab-v4 .chapter-section,
#inv-type-lab-v4 .expanded-lesson,
#inv-type-lab-v4 .lesson-point {
  overflow-x: clip;
}
#inv-type-lab-v4 .theory-pane :is(p,li,h1,h2,h3,h4,h5,h6,dd,dt,blockquote),
#inv-type-lab-v4 .expanded-lesson :is(p,li,h1,h2,h3,h4,h5,h6,dd,dt,blockquote) {
  max-inline-size: 100%;
  overflow-wrap: anywhere;
  word-break: normal;
  white-space: normal;
}
#inv-type-lab-v4 .textbook-visuals,
#inv-type-lab-v4 .sys-fig,
#inv-type-lab-v4 .pencil-atlas-figure {
  min-width: 0;
  max-width: 100%;
}
#inv-type-lab-v4 .sys-fig svg,
#inv-type-lab-v4 .pencil-atlas-figure svg {
  display: block;
  width: 100%;
  height: auto;
  max-width: 100%;
}
@media (max-width: 600px) {
  #inv-type-lab-v4 .theory-pane {
    width: 100%;
    margin-inline: 0;
  }
}
'''
    s=s.replace(marker,css+'\n'+marker,1)
    p.write_text(s,encoding='utf-8',newline='\n')

require(sha(files[0])==sha(files[1]),'payload copies diverged')
new_payload=sha(files[0])

b=root/'app/build.gradle'; t=b.read_text(encoding='utf-8')
require("versionCode 18700" in t and "versionName '1.8.7'" in t,'unexpected Android version')
t=t.replace('versionCode 18700','versionCode 18800',1).replace("versionName '1.8.7'","versionName '1.8.8'",1)
b.write_text(t,encoding='utf-8',newline='\n')

report=root/'provenance/V188_MOBILE_DISPLAY_CORRECTION.md'
report.write_text(f'''# INVERTEBRATA v1.8.8 — mobile display correction\n\nDevice QA on v1.8.7 exposed right-edge lesson-text clipping on a narrow Android WebView. v1.8.8 corrects presentation only: WebView text autosizing is normalized, lesson descendants are constrained to the viewport, and prose wrapping is made fail-safe. Pencil-atlas and organ-system SVG figures remain embedded and responsive.\n\nAcademic payload text/content is unchanged; presentation CSS changed.\n\nNew payload SHA-256: `{new_payload}`\n''',encoding='utf-8',newline='\n')

mf=root/'provenance/SOURCE_MANIFEST_SHA256.txt'
tracked=[]
for line in mf.read_text(encoding='utf-8').splitlines():
    if line.strip(): tracked.append(line.split('  ',1)[1])
if 'provenance/V188_MOBILE_DISPLAY_CORRECTION.md' not in tracked: tracked.append('provenance/V188_MOBILE_DISPLAY_CORRECTION.md')
lines=[]
for rel in sorted(tracked):
    fp=root/rel; require(fp.is_file(),f'missing tracked file: {rel}')
    lines.append(f'{sha(fp)}  {rel}')
mf.write_text('\n'.join(lines)+'\n',encoding='utf-8',newline='\n')
print('V188_PAYLOAD_SHA256='+new_payload)
