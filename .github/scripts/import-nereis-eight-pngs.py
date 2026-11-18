#!/usr/bin/env python3
"""Import exact eight Nereis PNG bytes from a local upload ZIP. No sketches."""
from pathlib import Path
from hashlib import sha256
import runpy, struct, zipfile, sys
root = Path(__file__).resolve().parents[2]
archive = Path(sys.argv[1]) if len(sys.argv) > 1 else root / 'qa-intake/nereis_u53_eight_originals.zip'
manifest = runpy.run_path(str(root / '.github/scripts/verify-nereis-png-masters.py'))
expected = manifest['EXPECTED']
prefix = 'native-app/app/src/main/assets/nereis/'
destination = root / prefix
if not archive.is_file(): sys.exit('MISSING_EXACT_PNG_ARCHIVE: ' + str(archive))
with zipfile.ZipFile(archive) as z:
    members = z.infolist()
    if sorted(i.filename for i in members) != sorted(prefix + n for n in expected):
        sys.exit('INVALID_ARCHIVE_CONTENTS: Expected eight exact PNG paths only')
    for info in members:
        filename = info.filename.removeprefix(prefix)
        if info.is_dir() or info.file_size < 100000 or info.file_size > 3000000:
            sys.exit('INVALID_SOURCE_FILE_SIZE: ' + filename)
        data = z.read(info)
        if sha256(data).hexdigest() != expected[filename]:
            sys.exit('SHA256_MISMATCH: ' + filename)
        if not data.startswith(bytes([137,80,78,71,13,10,26,10])):
            sys.exit('PNG_SIGNATURE_MISMATCH: ' + filename)
        if struct.unpack('>II', data[16:24]) != (1122,1402):
            sys.exit('WRONG_PNG_DIMENSIONS: ' + filename)
        destination.mkdir(parents=True,exist_ok=True)
        (destination / filename).write_bytes(data)
        print('EXACT_BINARY_IMPORTED', filename, sha256(data).hexdigest())
if manifest['check'](destination) != 0:
    sys.exit('POST_IMPORT_INTEGRITY_FAIL')
print('EIGHT_ORIGINAL_PNG_IMPORT=PASS; ACADEMIC_REVIEW=PENDING')
