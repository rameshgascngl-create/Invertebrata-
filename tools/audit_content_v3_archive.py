#!/usr/bin/env python3
"""Read-only safety and inventory check of the uploaded tar.gz; never extract."""
import hashlib
import json
import sys
import tarfile
from pathlib import Path, PurePosixPath

MAX_MEMBERS = 1000
MAX_TOTAL_BYTES = 100 * 1024 * 1024
MAX_FILE_BYTES = 30 * 1024 * 1024

def audit(archive: Path):
    issues, entries, total = [], [], 0
    with tarfile.open(archive, mode="r:gz") as tar:
        members = tar.getmembers()
        if len(members) > MAX_MEMBERS:
            issues.append("too many archive members")
        names = set()
        for m in members:
            path = PurePosixPath(m.name)
            if path.is_absolute() or ".." in path.parts or "\\" in m.name or not m.name.strip():
                issues.append(f"unsafe path: {m.name!r}")
            if m.name in names:
                issues.append(f"duplicate path: {m.name!r}")
            names.add(m.name)
            if not (m.isfile() or m.isdir()):
                issues.append(f"unsupported entry type: {m.name!r}")
            if m.size < 0 or m.size > MAX_FILE_BYTES:
                issues.append(f"invalid/oversize member: {m.name!r}")
            total += m.size
            if m.isfile() and m.size <= MAX_FILE_BYTES:
                stream = tar.extractfile(m)
                if stream is None:
                    issues.append(f"unreadable file: {m.name!r}")
                else:
                    digest = hashlib.sha256()
                    count = 0
                    while chunk := stream.read(1024 * 1024):
                        count += len(chunk)
                        digest.update(chunk)
                    if count != m.size:
                        issues.append(f"size mismatch: {m.name!r}")
                    entries.append({"path": m.name, "bytes": count, "sha256": digest.hexdigest()})
        if total > MAX_TOTAL_BYTES:
            issues.append("archive uncompressed size limit exceeded")
    return {"archive_sha256": hashlib.sha256(archive.read_bytes()).hexdigest(),
            "member_count": len(members), "uncompressed_bytes": total,
            "files": entries, "issues": issues}

if __name__ == "__main__":
    path = Path(sys.argv[1] if len(sys.argv) > 1 else "invertebrata_update_package.tar.gz")
    try:
        result = audit(path)
    except (OSError, tarfile.TarError, ValueError) as exc:
        print(f"FAIL: {exc}", file=sys.stderr)
        sys.exit(2)
    print(json.dumps(result, ensure_ascii=False, indent=2))
    sys.exit(1 if result["issues"] else 0)
