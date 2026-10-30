#!/usr/bin/env python3
"""Exact-byte gate for the eight offline Nereis pencil-style PNG candidates.

English/Tamil leader-line accuracy and the college practical manual remain
human scholarly checks; hash matching cannot grant academic acceptance.
A missing plate is ALWAYS a build/QA failure, never a Canvas fallback.
"""
from pathlib import Path
import hashlib
import struct
import sys

EXPECTED = {
    "01_external_morphology.png": "b55ba2f1503fe7cc76950cb2388f12351648527cd3eb73b88c81a31cd1a9afcc",
    "02_enlarged_head.png": "e65ecf8b82cd667322ac6e5b7a93e43ed84bd3186363f9d2f334616863ebf65c",
    "03_biramous_parapodium.png": "214338f39ce064e613332fb309759f33444894bc55705d2c70127449a22c9eaa",
    "04_digestive_system.png": "4bd3ece904b0606cc9be3de04224df72f94fc599c97d980a4c5fc1c1102439c7",
    "05_circulatory_system.png": "1b4f3564bc6a954bf638ab9c496ad483d3bf00bac0c13e5c341a5b74077a3913",
    "06_nervous_system.png": "05402d11f8d13a5d7d538ce6e1f045c6fdca6be9b02f9f9f5372303f639abcc4",
    "07_excretory_system.png": "53d2830ac5ce4ecb4a96b34bd5349fbc259fd4903aa21e10c3be99b2fb57bca5",
    "08_trochophore_larva.png": "a43fe27151d261de94420eae93d85cd20bda6b89faef49766d5472bc1e027414",
}
def check(root: Path) -> int:
    errors = []
    for filename, expected_sha in EXPECTED.items():
        path = root / filename
        if not path.is_file():
            errors.append(f"MISSING NEREIS MASTER: {path}")
            continue
        blob = path.read_bytes()
        if blob[:8] != b"\x89PNG\r\n\x1a\n" or len(blob) < 64:
            errors.append(f"INVALID PNG: {path}")
            continue
        width, height = struct.unpack(">II", blob[16:24])
        digest = hashlib.sha256(blob).hexdigest()
        if (width, height) != (1122, 1402):
            errors.append(f"WRONG DIMENSIONS: {path}: {width}x{height}")
        if digest != expected_sha:
            errors.append(f"CHECKSUM MISMATCH: {path}: {digest}")
        else:
            print(f"PNG_BYTES_PASS {filename} 1122x1402 sha256={digest}")
    if errors:
        for error in errors:
            print(error, file=sys.stderr)
        print(f"NEREIS_RASTER_ASSET_GATE=FAIL errors={len(errors)}", file=sys.stderr)
        return 2
    print("NEREIS_RASTER_ASSET_GATE=PASS count=8")
    print("ACADEMIC_ZOOLOGY_TAMIL_SOURCE_APPROVAL=PENDING")
    return 0

if __name__ == "__main__":
    base = (Path(sys.argv[1]) if len(sys.argv)>1
            else Path("native-app/app/src/main/assets/nereis"))
    sys.exit(check(base))
