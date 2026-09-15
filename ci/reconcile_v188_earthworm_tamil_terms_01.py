#!/usr/bin/env python3
from pathlib import Path
import sys

# Narrow Earthworm terminology overlay only.
# Apply after reconcile_v188_earthworm_remediation.py.
# Does not invoke Gradle, alter workflows, reconcile the build pipeline, or touch other organisms.
OLD='மண்புழு — இரத்த நாள அமைப்பு'
NEW='மண்புழு — இரத்த ஓட்ட மண்டலம்'

def main(root):
    root=Path(root)
    targets=[root/'academic_payload/index.html',root/'app/src/main/assets/www/index.html']
    for p in targets:
        s=p.read_text(encoding='utf-8')
        n=s.count(OLD)
        if n!=1:
            raise RuntimeError(f'{p}: expected exactly one Earthworm revised vascular Tamil caption, found {n}')
        s=s.replace(OLD,NEW,1)
        p.write_text(s,encoding='utf-8')
    if targets[0].read_bytes()!=targets[1].read_bytes():
        raise RuntimeError('payload copies diverged')
    print('EARTHWORM_TAMIL_TERM_CORRECTION=vascular caption: இரத்த நாள அமைப்பு -> இரத்த ஓட்ட மண்டலம்')
    print('BUILD_ELIGIBLE=NO')

if __name__=='__main__':
    if len(sys.argv)!=2: raise SystemExit('usage: reconcile_v188_earthworm_tamil_terms_01.py <source-root>')
    main(sys.argv[1])
