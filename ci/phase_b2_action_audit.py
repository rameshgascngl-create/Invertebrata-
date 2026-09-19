#!/usr/bin/env python3
"""Temporary Phase-B2 GitHub Actions audit harness.

Audit infrastructure only. It imports the persisted Phase-B1 reconstruction
driver and executes the accepted 19-step chain without changing academic
content, release workflows, Gradle configuration, versioning, or Android build
state.
"""
from __future__ import annotations

import argparse
import csv
import hashlib
import html.parser
import importlib.util
import json
import os
import re
import shutil
import subprocess
import sys
from collections import Counter, defaultdict
from pathlib import Path

BASELINE = "8416ec813a7b8bb783896a4c4024ef22c1b401ad"
EXPECTED_RELEASE_WORKFLOW_BLOB = "8a5a851a17669fc5d7e26c076cce4b2fcd1e04df"
EXPECTED_ZIP_BLOB = "4bbb361886a8b1f20ba7ed47677ba54325dc45bc"
EXPECTED_ZIP_SHA256 = "281e5f80a14f80df6ad87427a8e29859050f35430ec3158ad52b84ae607df1a3"
EXPECTED_DRIVER_BLOB = "96ee2ade682dbbe747f4a88aa27b3b9333d00488"
EXPECTED_MANIFEST_BLOB = "e102cdabdafe00932b6b264a720c784b5a8da9fe"
EXPECTED_MATRIX_BLOB = "958c8bacd91600213b64bb591027eafaf118a7f8"
EXPECTED_ROWS = 76
EXPECTED_STEP4_BLOB = "a716562c70db61fbf91a9a73d19bde8372e95f0d"
EXPECTED_STEP2_BLOB = "a6c27042df66a728b320db8d9c26fce660ebc669"

AUDIT_ALLOWED_DIFFS = {
    ".github/workflows/phase-b2-reconstruction-audit.yml",
    "ci/phase_b2_action_audit.py",
}

BATCH3_PLATE_EXCEPTIONS = {
    "arthropod-pest-ipm": "pest-ipm",
    "molluscan-body-plan": "mollusc-plan",
}
BATCH2_PLATE_EXCEPTIONS = {
    "fasciola_lifecycle": "fasciola-life-cycle",
    "obelia-zooids-left": "obelia-zooids",
    "obelia-zooids-right": "obelia-zooids",
}
PEN03_IDS = [
    "PENAEUS-03-OVERVIEW",
    "PENAEUS-03-CEPHALIC",
    "PENAEUS-03-THORACIC",
    "PENAEUS-03-ABDOMINAL",
]

LESSON_BY_REQ = {
    "PAR-01": "u1-paramecium", "PAR-02": "u1-paramecium",
    "AMO-01": "u1-protozoa",
    "ENTA-01": "u1-proto-parasites", "TRYP-01": "u1-proto-parasites", "LEISH-01": "u1-proto-parasites",
    "PLASM-01": "u1-host-parasite",
    "SYC-01": "u2-sycon", "SYC-02": "u2-sycon", "SYC-03": "u2-sycon", "SYC-04": "u2-sycon",
    "POR-01": "u2-porifera",
    "OBE-01": "u2-obelia", "OBE-02": "u2-obelia", "OBE-03": "u2-obelia", "OBE-04": "u2-obelia", "OBE-05": "u2-obelia",
    "AUR-01": "u2-cnidaria", "PHY-01": "u2-cnidaria", "COR-01": "u2-coral-economics",
    "FAS-01": "u3-fasciola", "FAS-02": "u3-fasciola", "FAS-03": "u3-fasciola", "FAS-04": "u3-fasciola", "FAS-05": "u3-fasciola",
    "TAE-01": "u3-platy", "TAE-02": "u3-platy-adapt",
    "ASC-01": "u3-ascaris", "ASC-02": "u3-ascaris", "ASC-03": "u3-ascaris", "ASC-04": "u3-ascaris", "ASC-05": "u3-ascaris", "ASC-06": "u3-ascaris",
    "NEM-01": "u3-nematode-adapt",
    "EWT-01": "u4-annelida", "EWT-02": "u4-annelida", "EWT-03": "u4-annelida", "EWT-04": "u4-annelida", "EWT-05": "u4-annelida", "EWT-06": "u4-annelida",
    "NER-01": "u4-nereis", "NER-02": "u4-nereis", "NER-03": "u4-nereis", "NER-04": "u4-nereis", "NER-05": "u4-nereis", "NER-06": "u4-nereis", "NER-07": "u4-nereis",
    "PEN-01": "u4-penaeus", "PEN-02": "u4-penaeus", "PEN-03": "u4-penaeus", "PEN-04": "u4-penaeus", "PEN-05": "u4-penaeus", "PEN-06": "u4-penaeus", "PEN-07": "u4-penaeus", "PEN-08": "u4-penaeus", "PEN-09": "u4-penaeus",
    "PERI-01": "u4-modes-life", "CRUST-01": "u4-penaeus", "IPM-01": "u4-arthropoda",
    "PILA-01": "u5-pila", "PILA-02": "u5-pila", "PILA-03": "u5-pila", "PILA-04": "u5-pila", "PILA-05": "u5-pila", "PILA-06": "u5-pila", "PILA-07": "u5-pila",
    "MOL-01": "u5-mollusca", "TOR-01": "u5-pila", "CEPH-01": "u5-cephalopods",
    "AST-01": "u5-asterias", "AST-02": "u5-asterias", "AST-03": "u5-asterias", "AST-04": "u5-asterias", "AST-05": "u5-asterias", "AST-06": "u5-asterias",
    "ECHL-01": "u5-echinodermata",
}

KNOWN_REJECTED_FIGURE_TITLES = [
    "Ascaris lumbricoides — male and female reproductive systems",
]

SUPERSEDED_PLATE_IDS = [
    "earthworm-external",
    "earthworm-digestive",
    "earthworm-vascular",
    "earthworm-excretory",
    "earthworm-reproductive",
    "obelia-colony",
    "obelia-medusa",
    "obelia-life-cycle",
    "nereis-external",
    "nereis-parapodium",
    "NEREIS-06-N1",
    "pila-external",
    "pila-pallial",
    "pila-digestive",
    "pila-nervous",
    "pila-reproductive",
    "asterias-digestive",
    "asterias-reproductive",
    "master-fasciola-reproductive",
]

OBSOLETE_MAPPING_PATTERNS = [
    r"Obelia.{0,100}\bp\.?\s*17\b",
    r"Nereis.{0,100}\bp\.?\s*30\b",
    r"Pila.{0,100}\bp\.?\s*42\b",
    r"Asterias.{0,100}\bp\.?\s*45(?:\s*[–-]\s*46)?\b",
]

RESOURCE_RE = re.compile(
    r"<(?:script|img|iframe|link|source)\b[^>]*(?:src|href)\s*=\s*['\"](https?://[^'\"]+)",
    re.I,
)
ID_RE = re.compile(r"\bid\s*=\s*['\"]([^'\"]+)['\"]", re.I)
PLATE_RE = re.compile(r"\bdata-v188-plate\s*=\s*['\"]([^'\"]+)['\"]", re.I)
REF_RE = re.compile(r"url\(\s*#([^\s)]+)\s*\)|(?:href|xlink:href)\s*=\s*['\"]#([^'\"]+)['\"]", re.I)


def sha256_file(path: Path) -> str:
    h = hashlib.sha256()
    with path.open("rb") as f:
        for chunk in iter(lambda: f.read(1024 * 1024), b""):
            h.update(chunk)
    return h.hexdigest()


def git(*args: str, cwd: Path) -> str:
    cp = subprocess.run(["git", *args], cwd=cwd, text=True, stdout=subprocess.PIPE, stderr=subprocess.STDOUT)
    if cp.returncode != 0:
        raise RuntimeError(f"git {' '.join(args)} failed ({cp.returncode}):\n{cp.stdout}")
    return cp.stdout.strip()


def import_driver(repo: Path):
    p = repo / "ci/reconstruct_v188_accepted_source.py"
    spec = importlib.util.spec_from_file_location("v188_driver", p)
    if spec is None or spec.loader is None:
        raise RuntimeError("cannot load persisted reconstruction driver")
    mod = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(mod)
    return mod


def hash_tree(root: Path) -> dict[str, str]:
    result = {}
    for p in sorted(x for x in root.rglob("*") if x.is_file()):
        rel = p.relative_to(root).as_posix()
        result[rel] = sha256_file(p)
    return result


def write_hash_manifest(hashes: dict[str, str], path: Path) -> None:
    path.write_text("".join(f"{h}  {p}\n" for p, h in sorted(hashes.items())), encoding="utf-8")



JS_ASSIGN_MARKER = "window.ORG_SYSTEM_DIAGRAMS["


def _skip_ws(text: str, pos: int) -> int:
    while pos < len(text) and text[pos].isspace():
        pos += 1
    return pos


def _parse_js_string_literal(text: str, pos: int) -> tuple[str, int, str]:
    """Parse one JavaScript string literal without evaluating surrounding JS.

    Supports the single- and double-quoted forms needed by accepted
    ORG_SYSTEM_DIAGRAMS assignments. Double-quoted strings produced by
    json.dumps are decoded with JSON semantics; single-quoted literals use a
    small JS-compatible escape decoder. The returned raw literal is preserved
    only for evidence and is never written back to the reconstructed source.
    """
    pos = _skip_ws(text, pos)
    if pos >= len(text) or text[pos] not in ("'", '"'):
        raise ValueError(f"JavaScript string literal expected at offset {pos}")
    quote = text[pos]
    i = pos + 1
    while i < len(text):
        ch = text[i]
        if ch == "\\":
            i += 2
            continue
        if ch == quote:
            raw = text[pos:i + 1]
            if quote == '"':
                try:
                    return json.loads(raw), i + 1, raw
                except json.JSONDecodeError as e:
                    raise ValueError(f"invalid JSON-compatible JS string at {pos}: {e}") from e

            body = text[pos + 1:i]
            out: list[str] = []
            j = 0
            simple = {
                "n": "\n", "r": "\r", "t": "\t", "b": "\b",
                "f": "\f", "v": "\v", "0": "\0", "\\": "\\",
                "'": "'", '"': '"',
            }
            while j < len(body):
                if body[j] != "\\":
                    out.append(body[j])
                    j += 1
                    continue
                j += 1
                if j >= len(body):
                    raise ValueError(f"trailing escape in JS string at {pos}")
                esc = body[j]
                j += 1
                if esc in simple:
                    out.append(simple[esc])
                elif esc == "u":
                    if j + 4 > len(body):
                        raise ValueError(f"truncated \\u escape in JS string at {pos}")
                    out.append(chr(int(body[j:j + 4], 16)))
                    j += 4
                elif esc == "x":
                    if j + 2 > len(body):
                        raise ValueError(f"truncated \\x escape in JS string at {pos}")
                    out.append(chr(int(body[j:j + 2], 16)))
                    j += 2
                elif esc == "\n":
                    # JavaScript line continuation.
                    continue
                elif esc == "\r":
                    if j < len(body) and body[j] == "\n":
                        j += 1
                    continue
                else:
                    # JavaScript non-escape character semantics.
                    out.append(esc)
            return "".join(out), i + 1, raw
        i += 1
    raise ValueError(f"unterminated JavaScript string literal at offset {pos}")


def _expect_js(text: str, pos: int, token: str) -> int:
    pos = _skip_ws(text, pos)
    if not text.startswith(token, pos):
        raise ValueError(
            f"expected {token!r} at offset {pos}; observed {text[pos:pos + 60]!r}"
        )
    return pos + len(token)


def parse_materialized_assignments(text: str) -> list[dict]:
    """Structurally decode accepted ORG_SYSTEM_DIAGRAMS additive assignments.

    This deliberately accepts semantically equivalent empty-string fallbacks
    written as either ||'' or ||"". It does not rewrite or normalize the
    source. A malformed left-hand assignment is a hard failure.
    """
    out: list[dict] = []
    pos = 0
    while True:
        begin = text.find(JS_ASSIGN_MARKER, pos)
        if begin < 0:
            break
        cursor = begin + len(JS_ASSIGN_MARKER)
        try:
            lesson, cursor, lesson_raw = _parse_js_string_literal(text, cursor)
            cursor = _expect_js(text, cursor, "]")
        except ValueError:
            # This occurrence can be unrelated text; move forward until a
            # syntactically valid bracketed lesson is found.
            pos = begin + len(JS_ASSIGN_MARKER)
            continue

        eq_pos = _skip_ws(text, cursor)
        if not text.startswith("=", eq_pos):
            # Right-hand read inside an assignment, not an assignment LHS.
            pos = cursor
            continue

        try:
            cursor = _expect_js(text, eq_pos, "=")
            cursor = _expect_js(text, cursor, "(")
            cursor = _expect_js(text, cursor, JS_ASSIGN_MARKER)
            lesson2, cursor, lesson2_raw = _parse_js_string_literal(text, cursor)
            if lesson2 != lesson:
                raise ValueError(
                    f"lesson mismatch: LHS {lesson!r} vs RHS {lesson2!r}"
                )
            cursor = _expect_js(text, cursor, "]")
            cursor = _expect_js(text, cursor, "||")
            fallback, cursor, fallback_raw = _parse_js_string_literal(text, cursor)
            if fallback != "":
                raise ValueError(
                    f"fallback must be empty string, observed {fallback!r}"
                )
            cursor = _expect_js(text, cursor, ")")
            cursor = _expect_js(text, cursor, "+")
            payload, cursor, payload_raw = _parse_js_string_literal(text, cursor)
            cursor = _expect_js(text, cursor, ";")
        except ValueError as e:
            raise ValueError(
                f"malformed ORG_SYSTEM_DIAGRAMS assignment at offset {begin}: {e}"
            ) from e

        out.append({
            "index": len(out),
            "lesson": lesson,
            "payload": payload,
            "start": begin,
            "end": cursor,
            "lesson_literal": lesson_raw,
            "rhs_lesson_literal": lesson2_raw,
            "fallback_literal": fallback_raw,
            "payload_literal": payload_raw,
        })
        pos = cursor

    if not out:
        raise ValueError("no ORG_SYSTEM_DIAGRAMS assignments decoded")
    return out


SVG_URL_REF_RE = re.compile(
    r"url\(\s*['\"]?#([^\s)'\";]+)['\"]?\s*\)",
    re.I,
)


class MaterializedSVGScanner(html.parser.HTMLParser):
    """Inspect literal IDs/references only inside materialized SVG markup.

    Script text is data to HTMLParser and is intentionally not treated as DOM
    markup. Therefore source expressions such as spec-'+id+'-fill are not
    counted as literal IDs. Serialized overlays are decoded first and their
    resulting HTML/SVG payloads are scanned separately.
    """

    def __init__(self) -> None:
        super().__init__(convert_charrefs=True)
        self.svg_depth = 0
        self.style_depth = 0
        self.ids: list[str] = []
        self.refs: list[str] = []

    def handle_starttag(self, tag: str, attrs) -> None:
        low = tag.lower()
        if low == "svg":
            self.svg_depth += 1
        if self.svg_depth:
            self._scan_attrs(attrs)
        if self.svg_depth and low == "style":
            self.style_depth += 1

    def handle_startendtag(self, tag: str, attrs) -> None:
        low = tag.lower()
        entered = low == "svg"
        if entered:
            self.svg_depth += 1
        if self.svg_depth:
            self._scan_attrs(attrs)
        if entered:
            self.svg_depth -= 1

    def handle_endtag(self, tag: str) -> None:
        low = tag.lower()
        if self.svg_depth and low == "style" and self.style_depth:
            self.style_depth -= 1
        if low == "svg" and self.svg_depth:
            self.svg_depth -= 1

    def handle_data(self, data: str) -> None:
        if self.svg_depth and self.style_depth:
            self.refs.extend(SVG_URL_REF_RE.findall(data))

    def _scan_attrs(self, attrs) -> None:
        for key, value in attrs:
            if value is None:
                continue
            low = key.lower()
            if low == "id":
                self.ids.append(value)
            self.refs.extend(SVG_URL_REF_RE.findall(value))
            if low in ("href", "xlink:href") and value.startswith("#"):
                self.refs.append(value[1:])


def scan_svg_fragment(markup: str) -> dict:
    scanner = MaterializedSVGScanner()
    scanner.feed(markup)
    counts = Counter(scanner.ids)
    duplicates = {k: v for k, v in sorted(counts.items()) if v > 1}
    idset = set(scanner.ids)
    broken = sorted(set(scanner.refs) - idset)
    return {
        "ids": scanner.ids,
        "refs": scanner.refs,
        "duplicate_ids": duplicates,
        "broken_refs": broken,
    }


def scan_all_materialized_svg(final_html: str, assignments: list[dict]) -> dict:
    all_ids: list[str] = []
    all_refs: list[str] = []

    top = MaterializedSVGScanner()
    top.feed(final_html)
    all_ids.extend(top.ids)
    all_refs.extend(top.refs)

    for item in assignments:
        scanner = MaterializedSVGScanner()
        scanner.feed(item["payload"])
        all_ids.extend(scanner.ids)
        all_refs.extend(scanner.refs)

    counts = Counter(all_ids)
    duplicates = {k: v for k, v in sorted(counts.items()) if v > 1}
    broken = sorted(set(all_refs) - set(all_ids))
    return {
        "ids": all_ids,
        "refs": all_refs,
        "duplicate_ids": duplicates,
        "broken_refs": broken,
    }


class ResourceScanner(html.parser.HTMLParser):
    RESOURCE_TAGS = {"script", "img", "iframe", "link", "source"}

    def __init__(self) -> None:
        super().__init__(convert_charrefs=True)
        self.remote: list[str] = []

    def handle_starttag(self, tag: str, attrs) -> None:
        if tag.lower() not in self.RESOURCE_TAGS:
            return
        for key, value in attrs:
            if value is None:
                continue
            if key.lower() in ("src", "href") and re.match(r"^https?://", value, re.I):
                if "appassets.androidplatform.net" not in value:
                    self.remote.append(value)


def scan_remote_resources(final_html: str, assignments: list[dict]) -> list[str]:
    urls: list[str] = []
    scanner = ResourceScanner()
    scanner.feed(final_html)
    urls.extend(scanner.remote)
    for item in assignments:
        s = ResourceScanner()
        s.feed(item["payload"])
        urls.extend(s.remote)
    return sorted(set(urls))


def expected_plate_ids(row: dict[str, str]) -> list[str]:
    rid = row["Requirement ID"]
    cand = row["Active Candidate"].strip()
    if rid == "PEN-03":
        return PEN03_IDS[:]
    if cand.startswith("batch3#"):
        raw = cand.split("#", 1)[1].split(" +", 1)[0].strip()
        return [BATCH3_PLATE_EXCEPTIONS.get(raw, raw)]
    if cand.startswith("batch2#"):
        raw = cand.split("#", 1)[1].split(" +", 1)[0].strip()
        raw = BATCH2_PLATE_EXCEPTIONS.get(raw, raw.replace("_", "-"))
        return [raw]
    raw = cand.split(" +", 1)[0].strip()
    if raw.endswith(" child series"):
        raise RuntimeError(f"unhandled child-series candidate: {rid}: {cand}")
    return [raw]


def figure_sections(assignments: list[dict]) -> tuple[Counter, dict[str, set[str]], dict[str, str], dict[str, list[dict]]]:
    counts: Counter = Counter()
    lessons: dict[str, set[str]] = defaultdict(set)
    snippets: dict[str, str] = {}
    locations: dict[str, list[dict]] = defaultdict(list)

    figure_re = re.compile(
        r"<figure\b[^>]*data-v188-plate=['\"]([^'\"]+)['\"][^>]*>[\s\S]*?</figure>",
        re.I,
    )
    for item in assignments:
        for m in figure_re.finditer(item["payload"]):
            pid = m.group(1)
            counts[pid] += 1
            lessons[pid].add(item["lesson"])
            snippets[pid] = m.group(0)
            locations[pid].append({
                "assignment_index": item["index"],
                "lesson": item["lesson"],
                "assignment_source_offset": item["start"],
                "payload_offset": m.start(),
            })
    return counts, lessons, snippets, locations


PEN_REQUIREMENT_MARKERS = {
    "PEN-01": "01",
    "PEN-03": "03",
    "PEN-05": "05",
    "PEN-08": "08",
    "PEN-09": "09",
}


def governing_transform_for_row(row: dict[str, str]) -> str:
    rid = row["Requirement ID"]
    if rid in {"PEN-01", "PEN-03", "PEN-05", "PEN-08"}:
        return "ci/v188_penaeus_targeted_rectification.py"
    if rid == "PEN-09":
        return (
            "ci/v188_penaeus_targeted_rectification.py + "
            "ci/v188_source_blocker_pass2_systems.py + "
            "ci/v188_source_blocker_pass2_terminology.py"
        )
    return row.get("Evidence/Exact Finding", "")


def evaluate_matrix_row(
    row: dict[str, str],
    counts: Counter,
    lessons: dict[str, set[str]],
    snippets: dict[str, str],
    locations: dict[str, list[dict]],
) -> dict:
    rid = row["Requirement ID"]
    ids = expected_plate_ids(row)
    expected_lesson = LESSON_BY_REQ.get(rid)
    if not expected_lesson:
        raise RuntimeError(f"no expected lesson mapping for {rid}")

    observed_counts = {pid: counts.get(pid, 0) for pid in ids}
    presence_ok = all(v == 1 for v in observed_counts.values())
    dup_ok = all(v <= 1 for v in observed_counts.values())
    lesson_detail = {pid: sorted(lessons.get(pid, set())) for pid in ids}
    lesson_ok = all(lessons.get(pid, set()) == {expected_lesson} for pid in ids)

    marker_ok = True
    marker_note = "accepted candidate/lesson provenance"
    cand = row["Active Candidate"]

    if cand.startswith("batch3#"):
        marker_ok = all(
            'data-source-pass2-labels="1"' in snippets.get(pid, "")
            and "v188-pass2-callouts" in snippets.get(pid, "")
            and "v188-pass2-legend" in snippets.get(pid, "")
            for pid in ids
        )
        marker_note = (
            "Batch-3 visible Pass-2 callouts/legend present"
            if marker_ok else
            "Batch-3 visible Pass-2 marker/callout/legend missing"
        )
    elif "Pass2" in cand and rid != "SYC-04":
        marker_ok = all('data-source-pass2="1"' in snippets.get(pid, "") for pid in ids)
        marker_note = (
            "Pass-2 system marker present"
            if marker_ok else
            "Pass-2 system marker missing"
        )
    elif rid == "SYC-04":
        snippet = snippets.get(ids[0], "")
        old_sentence = (
            "Water: ostia → incurrent canal → prosopyle → radial canal → "
            "apopyle → spongocoel → osculum"
        )
        marker_ok = old_sentence not in snippet
        marker_note = (
            "old narrow-width in-SVG prose absent"
            if marker_ok else
            "old narrow-width in-SVG prose restored"
        )

    if rid in PEN_REQUIREMENT_MARKERS:
        req = PEN_REQUIREMENT_MARKERS[rid]
        pen_marker_ok = all(
            "v188-penaeus-remediation" in snippets.get(pid, "")
            and f'data-v188-requirement="{req}"' in snippets.get(pid, "")
            for pid in ids
        )
        if rid == "PEN-09":
            p9 = snippets.get("PENAEUS-09-R1", "")
            pen_marker_ok = (
                pen_marker_ok
                and 'data-source-pass2="1"' in p9
                and "Petasma" in p9
                and "Thelycum" in p9
            )
        marker_ok = marker_ok and pen_marker_ok
        marker_note = (
            f"Penaeus native-overlay provenance requirement={req} present"
            if pen_marker_ok else
            f"Penaeus native-overlay provenance requirement={req} missing"
        )

    per_figure_svg = {}
    svg_ok = True
    for pid in ids:
        scan = scan_svg_fragment(snippets.get(pid, ""))
        per_figure_svg[pid] = {
            "literal_ids": scan["ids"],
            "literal_refs": scan["refs"],
            "duplicate_ids": scan["duplicate_ids"],
            "broken_refs": scan["broken_refs"],
        }
        if scan["duplicate_ids"] or scan["broken_refs"]:
            svg_ok = False

    ok = presence_ok and dup_ok and lesson_ok and marker_ok and svg_ok
    return {
        "rid": rid,
        "ids": ids,
        "expected_lesson": expected_lesson,
        "observed_counts": observed_counts,
        "lesson_detail": lesson_detail,
        "locations": {pid: locations.get(pid, []) for pid in ids},
        "presence_ok": presence_ok,
        "dup_ok": dup_ok,
        "lesson_ok": lesson_ok,
        "marker_ok": marker_ok,
        "marker_note": marker_note,
        "svg_ok": svg_ok,
        "per_figure_svg": per_figure_svg,
        "ok": ok,
    }


def run_matrix_regression(
    repo: Path,
    final_html: str,
    out_csv: Path,
    only_ids: set[str] | None = None,
) -> tuple[int, list[str]]:
    matrix = repo / "provenance/V188_SOURCE_GATE_MATRIX_02.csv"
    with matrix.open("r", encoding="utf-8", newline="") as f:
        rows = list(csv.DictReader(f))
    if len(rows) != EXPECTED_ROWS:
        raise RuntimeError(f"Matrix 02 row count {len(rows)} != {EXPECTED_ROWS}")

    assignments = parse_materialized_assignments(final_html)
    counts, lessons, snippets, locations = figure_sections(assignments)

    selected = [r for r in rows if only_ids is None or r["Requirement ID"] in only_ids]
    if only_ids is not None:
        found = {r["Requirement ID"] for r in selected}
        if found != only_ids:
            raise RuntimeError(f"requested Matrix row IDs not found: {sorted(only_ids - found)}")

    fields = [
        "Requirement ID", "Organism", "Figure/System", "Active Candidate",
        "Governing Transformation", "Expected Materialization", "Expected Lesson",
        "Decoded Source Location", "Actual Identifier Counts",
        "Provenance Marker", "Duplication Check", "Reference Integrity",
        "Result", "Exact Finding",
    ]
    failed: list[str] = []
    with out_csv.open("w", encoding="utf-8", newline="") as f:
        w = csv.DictWriter(f, fieldnames=fields)
        w.writeheader()
        for row in selected:
            ev = evaluate_matrix_row(row, counts, lessons, snippets, locations)
            if not ev["ok"]:
                failed.append(ev["rid"])

            locations_text = json.dumps(ev["locations"], sort_keys=True)
            refs_text = json.dumps(
                {
                    pid: {
                        "duplicate_ids": ev["per_figure_svg"][pid]["duplicate_ids"],
                        "broken_refs": ev["per_figure_svg"][pid]["broken_refs"],
                    }
                    for pid in ev["ids"]
                },
                sort_keys=True,
            )
            w.writerow({
                "Requirement ID": ev["rid"],
                "Organism": row["Organism"],
                "Figure/System": row["Figure/System"],
                "Active Candidate": row["Active Candidate"],
                "Governing Transformation": governing_transform_for_row(row),
                "Expected Materialization": ";".join(ev["ids"]),
                "Expected Lesson": ev["expected_lesson"],
                "Decoded Source Location": locations_text,
                "Actual Identifier Counts": json.dumps(ev["observed_counts"], sort_keys=True),
                "Provenance Marker": (
                    ("PASS " if ev["lesson_ok"] and ev["marker_ok"] else "FAIL ")
                    + ev["marker_note"]
                    + " "
                    + json.dumps(ev["lesson_detail"], sort_keys=True)
                ),
                "Duplication Check": "PASS" if ev["dup_ok"] else "FAIL",
                "Reference Integrity": ("PASS " if ev["svg_ok"] else "FAIL ") + refs_text,
                "Result": "PASS" if ev["ok"] else "FAIL",
                "Exact Finding": (
                    "accepted materialization decoded structurally and present exactly once"
                    if ev["ok"] else
                    "one or more decoded materialization/provenance/SVG checks failed"
                ),
            })

    return len(selected) - len(failed), failed


def _fixture_assignment(lesson: str, payload: str, fallback_literal: str) -> str:
    return (
        "window.ORG_SYSTEM_DIAGRAMS["
        + json.dumps(lesson, ensure_ascii=False)
        + "]=(window.ORG_SYSTEM_DIAGRAMS["
        + json.dumps(lesson, ensure_ascii=False)
        + "]||"
        + fallback_literal
        + ")+"
        + json.dumps(payload, ensure_ascii=False)
        + ";"
    )


def _synthetic_candidate_ok(
    text: str,
    expected_ids: list[str],
    expected_lesson: str,
    requirement: str | None = None,
) -> bool:
    try:
        assignments = parse_materialized_assignments(text)
    except Exception:
        return False
    counts, lessons, snippets, _ = figure_sections(assignments)
    if not all(counts.get(pid, 0) == 1 for pid in expected_ids):
        return False
    if not all(lessons.get(pid, set()) == {expected_lesson} for pid in expected_ids):
        return False
    if requirement is not None:
        if not all(
            f'data-v188-requirement="{requirement}"' in snippets.get(pid, "")
            for pid in expected_ids
        ):
            return False
    return True


def harness_fixture_tests(out_json: Path) -> dict:
    payload = (
        '<figure data-v188-plate="FIX-01" data-v188-requirement="01">'
        '<svg><defs><pattern id="FIX-01--pH"></pattern></defs>'
        '<rect fill="url(#FIX-01--pH)"/></svg>'
        '<span>தமிழ் "quote" \\ slash\\nline</span></figure>'
    )
    ordinary = _fixture_assignment("u-test", payload, "''")
    native = _fixture_assignment("u-test", payload, '""')

    parsed_a = parse_materialized_assignments(ordinary)
    parsed_b = parse_materialized_assignments(native)
    ordinary_ok = len(parsed_a) == 1 and parsed_a[0]["payload"] == payload
    native_ok = len(parsed_b) == 1 and parsed_b[0]["payload"] == payload
    semantic_equal = parsed_a[0]["payload"] == parsed_b[0]["payload"]

    malformed_rejected = False
    try:
        parse_materialized_assignments(native[:-2])
    except Exception:
        malformed_rejected = True

    valid_svg = scan_svg_fragment(
        '<svg><pattern id="PENAEUS-01-R1--pH"></pattern>'
        '<rect fill="url(#PENAEUS-01-R1--pH)"/></svg>'
    )
    valid_svg_ok = not valid_svg["duplicate_ids"] and not valid_svg["broken_refs"]

    duplicate_svg = scan_svg_fragment(
        '<svg><g id="dup"></g><path id="dup"></path></svg>'
    )
    duplicate_detected = bool(duplicate_svg["duplicate_ids"])

    broken_svg = scan_svg_fragment(
        '<svg><rect fill="url(#missing-id)"/></svg>'
    )
    broken_detected = "missing-id" in broken_svg["broken_refs"]

    dynamic_source = (
        "<script>const x='id=\"spec-'+id+'-fill\"';"
        "const y='url(#spec-'+id+'-fill)';</script>"
    )
    dynamic_scan = scan_svg_fragment(dynamic_source)
    dynamic_ignored = not dynamic_scan["ids"] and not dynamic_scan["refs"]

    penaeus_payload = (
        '<figure class="v188-penaeus-remediation" '
        'data-v188-plate="PENAEUS-01-R1" data-v188-requirement="01">'
        '<svg><defs><pattern id="PENAEUS-01-R1--pH"></pattern></defs>'
        '<rect fill="url(#PENAEUS-01-R1--pH)"/></svg></figure>'
    )
    penaeus_serialized = _fixture_assignment("u4-penaeus", penaeus_payload, '""')
    penaeus_assignments = parse_materialized_assignments(penaeus_serialized)
    penaeus_scan = scan_all_materialized_svg(penaeus_serialized, penaeus_assignments)
    penaeus_svg_ok = (
        not penaeus_scan["duplicate_ids"]
        and not penaeus_scan["broken_refs"]
        and "PENAEUS-01-R1--pH" in penaeus_scan["ids"]
    )

    # Candidate negative fixtures: every one must be rejected.
    good_candidate = _fixture_assignment("u4-penaeus", penaeus_payload, '""')
    absent_candidate = _fixture_assignment("u4-penaeus", "<div>none</div>", '""')
    wrong_id_candidate = _fixture_assignment(
        "u4-penaeus",
        penaeus_payload.replace("PENAEUS-01-R1", "PENAEUS-01-WRONG"),
        '""',
    )
    wrong_provenance = _fixture_assignment("u4-wrong", penaeus_payload, '""')
    duplicated_candidate = _fixture_assignment(
        "u4-penaeus", penaeus_payload + penaeus_payload, '""'
    )

    pen03_three = "".join(
        f'<figure class="v188-penaeus-remediation" data-v188-plate="{pid}" '
        f'data-v188-requirement="03"><svg></svg></figure>'
        for pid in PEN03_IDS[:3]
    )
    omitted_candidate = _fixture_assignment("u4-penaeus", pen03_three, '""')

    negative_results = {
        "genuinely_absent_penaeus_plate": not _synthetic_candidate_ok(
            absent_candidate, ["PENAEUS-01-R1"], "u4-penaeus", "01"
        ),
        "wrong_penaeus_identifier": not _synthetic_candidate_ok(
            wrong_id_candidate, ["PENAEUS-01-R1"], "u4-penaeus", "01"
        ),
        "wrong_provenance_marker": not _synthetic_candidate_ok(
            wrong_provenance, ["PENAEUS-01-R1"], "u4-penaeus", "01"
        ),
        "duplicated_candidate": not _synthetic_candidate_ok(
            duplicated_candidate, ["PENAEUS-01-R1"], "u4-penaeus", "01"
        ),
        "omitted_candidate": not _synthetic_candidate_ok(
            omitted_candidate, PEN03_IDS, "u4-penaeus", "03"
        ),
        "malformed_serialized_assignment": malformed_rejected,
        "duplicated_literal_svg_id": duplicate_detected,
        "broken_literal_svg_reference": broken_detected,
    }

    positive_results = {
        "ordinary_serialized_assignment": ordinary_ok,
        "penaeus_native_serialized_assignment": native_ok,
        "equivalent_decoded_payload_semantics": semantic_equal,
        "literal_valid_definition_reference": valid_svg_ok,
        "dynamic_js_constructor_not_literal_id": dynamic_ignored,
        "penaeus_serialized_svg_reference_integrity": penaeus_svg_ok,
        "good_candidate_control": _synthetic_candidate_ok(
            good_candidate, ["PENAEUS-01-R1"], "u4-penaeus", "01"
        ),
    }

    report = {
        "positive": positive_results,
        "negative": negative_results,
        "positive_pass": all(positive_results.values()),
        "negative_pass": all(negative_results.values()),
    }
    report["pass"] = report["positive_pass"] and report["negative_pass"]
    out_json.write_text(
        json.dumps(report, ensure_ascii=False, indent=2, sort_keys=True) + "\n",
        encoding="utf-8",
    )
    return report


def validate_harness_against_prior_run(
    repo: Path,
    prior_html: Path,
    out: Path,
) -> int:
    out.mkdir(parents=True, exist_ok=True)
    fixture = harness_fixture_tests(out / "HARNESS_FIXTURE_TESTS.json")
    if not fixture["pass"]:
        raise RuntimeError("audit harness fixture validation failed")

    final_html = prior_html.read_text(encoding="utf-8")
    assignments = parse_materialized_assignments(final_html)
    fallback_literals = Counter(item["fallback_literal"] for item in assignments)

    target_ids = {"PEN-01", "PEN-03", "PEN-05", "PEN-08", "PEN-09"}
    passed, failed = run_matrix_regression(
        repo,
        final_html,
        out / "PRIOR_RUN_TARGETED_PENAEUS_ROWS.csv",
        only_ids=target_ids,
    )

    svg = scan_all_materialized_svg(final_html, assignments)
    report = {
        "prior_run_html": str(prior_html),
        "prior_run_html_sha256": sha256_file(prior_html),
        "assignments_decoded": len(assignments),
        "fallback_literals": dict(fallback_literals),
        "ordinary_serialized_assignment_seen": "''" in fallback_literals,
        "penaeus_native_serialized_assignment_seen": '""' in fallback_literals,
        "targeted_rows_passed": passed,
        "targeted_rows_failed": failed,
        "targeted_rows_expected": 5,
        "literal_svg_duplicate_ids": svg["duplicate_ids"],
        "broken_materialized_svg_refs": svg["broken_refs"],
        "fixture_positive_pass": fixture["positive_pass"],
        "fixture_negative_pass": fixture["negative_pass"],
    }
    report["pass"] = (
        passed == 5
        and not failed
        and not svg["duplicate_ids"]
        and not svg["broken_refs"]
        and report["ordinary_serialized_assignment_seen"]
        and report["penaeus_native_serialized_assignment_seen"]
        and fixture["pass"]
    )
    (out / "PRIOR_RUN_HARNESS_VALIDATION.json").write_text(
        json.dumps(report, ensure_ascii=False, indent=2, sort_keys=True) + "\n",
        encoding="utf-8",
    )
    print(json.dumps(report, ensure_ascii=False, sort_keys=True))
    return 0 if report["pass"] else 6



def integrity_audit(repo: Path, source_root: Path, final_html: str, out_json: Path) -> dict:
    assignments = parse_materialized_assignments(final_html)
    decoded_payload = "\n".join(item["payload"] for item in assignments)

    svg = scan_all_materialized_svg(final_html, assignments)
    dup_ids = svg["duplicate_ids"]
    broken_refs = svg["broken_refs"]

    counts, _, _, _ = figure_sections(assignments)
    duplicate_plates = sorted(k for k, v in counts.items() if v > 1)

    remotes = scan_remote_resources(final_html, assignments)

    rejected_restored = [
        title for title in KNOWN_REJECTED_FIGURE_TITLES if title in decoded_payload
    ]
    superseded_plate_restored = sorted(
        pid for pid in SUPERSEDED_PLATE_IDS if counts.get(pid, 0) > 0
    )

    obsolete_mapping_hits = []
    for pat in OBSOLETE_MAPPING_PATTERNS:
        if re.search(pat, final_html, re.I | re.S):
            obsolete_mapping_hits.append(pat)

    changed = set(
        git("diff", "--name-only", f"{BASELINE}..HEAD", cwd=repo).splitlines()
    )
    unexpected_repo_drift = sorted(changed - AUDIT_ALLOWED_DIFFS)

    release_blob = git(
        "hash-object", ".github/workflows/invertebrata-v188-debug.yml", cwd=repo
    )
    zip_blob = git(
        "hash-object", "INVERTEBRATA_v1.8.7_RECONCILED_ANDROID_SOURCE.zip", cwd=repo
    )
    step4_blob = git("hash-object", "ci/reconcile_v188_svg_batch2.py", cwd=repo)
    step4_sha256 = sha256_file(repo / "ci/reconcile_v188_svg_batch2.py")

    required_text = {
        "earthworm_limited_scope": "earthworm-reproductive-r2",
        "penaeus_petasma": "Petasma",
        "penaeus_thelycum": "Thelycum",
        "obelia_corrected_colony": "OBELIA-01-R1",
        "obelia_corrected_medusa": "OBELIA-02-R1",
        "obelia_corrected_lifecycle": "OBELIA-03-R1",
        "nereis_excretory_r2": "NEREIS-06-R2",
        "pila_excretory": "PILA-EXC-N1",
        "asterias_wvs": "master-asterias-wvs",
        "fasciola_reproductive": "FASCIOLA-REP-R1",
        "ascaris_female": "ASCARIS-FEMALE-REP-R1",
        "ascaris_male": "ASCARIS-MALE-REP-R1",
        "paramecium_cv_cilia": "PARAMECIUM-CV-CILIA-N1",
    }
    missing_guards = [
        key for key, token in required_text.items() if token not in decoded_payload
    ]

    report = {
        "accepted_transformations_expected": 19,
        "superseded_transformations_executed": 0,
        "release_workflow_blob": release_blob,
        "release_workflow_blob_expected": EXPECTED_RELEASE_WORKFLOW_BLOB,
        "release_workflow_drift": 0 if release_blob == EXPECTED_RELEASE_WORKFLOW_BLOB else 1,
        "canonical_zip_blob": zip_blob,
        "canonical_zip_blob_expected": EXPECTED_ZIP_BLOB,
        "canonical_zip_blob_match": zip_blob == EXPECTED_ZIP_BLOB,
        "step2_blob": step2_blob,\n        "step2_blob_expected": EXPECTED_STEP2_BLOB,\n        "step2_blob_match": step2_blob == EXPECTED_STEP2_BLOB,\n        "step2_sha256": step2_sha256,\n        "step2_blob": step2_blob,\n        "step2_blob_expected": EXPECTED_STEP2_BLOB,\n        "step2_sha256": step2_sha256,\n        "step4_blob": step4_blob,
        "step4_blob_expected": EXPECTED_STEP4_BLOB,
        "step4_blob_match": step4_blob == EXPECTED_STEP4_BLOB,
        "step4_sha256": step4_sha256,
        "serialized_assignments_decoded": len(assignments),
        "serialized_fallback_literals": dict(
            Counter(item["fallback_literal"] for item in assignments)
        ),
        "literal_svg_ids_count": len(svg["ids"]),
        "literal_svg_refs_count": len(svg["refs"]),
        "duplicate_dom_svg_ids_count": len(dup_ids),
        "duplicate_dom_svg_ids": dup_ids,
        "broken_internal_svg_refs_count": len(broken_refs),
        "broken_internal_svg_refs": broken_refs,
        "duplicate_production_plate_ids_count": len(duplicate_plates),
        "duplicate_production_plate_ids": duplicate_plates,
        "remote_resource_dependencies_count": len(remotes),
        "remote_resource_dependencies": remotes,
        "rejected_figure_title_restoration_count": len(rejected_restored),
        "rejected_figure_title_restoration": rejected_restored,
        "superseded_plate_id_restoration_count": len(superseded_plate_restored),
        "superseded_plate_id_restoration": superseded_plate_restored,
        "rejected_or_superseded_plate_restoration_count": (
            len(rejected_restored) + len(superseded_plate_restored)
        ),
        "rejected_or_superseded_plate_restoration": (
            rejected_restored + superseded_plate_restored
        ),
        "obsolete_page_mapping_restoration_count": len(obsolete_mapping_hits),
        "obsolete_page_mapping_patterns": obsolete_mapping_hits,
        "unexpected_repository_drift_count": len(unexpected_repo_drift),
        "unexpected_repository_drift": unexpected_repo_drift,
        "accepted_state_guard_failures_count": len(missing_guards),
        "accepted_state_guard_failures": missing_guards,
        "tree_files": len(hash_tree(source_root)),
    }
    report["pass"] = all([
        report["release_workflow_drift"] == 0,
        report["canonical_zip_blob_match"],
        report["step2_blob_match"],\n        report["step4_blob_match"],
        report["duplicate_dom_svg_ids_count"] == 0,
        report["broken_internal_svg_refs_count"] == 0,
        report["duplicate_production_plate_ids_count"] == 0,
        report["remote_resource_dependencies_count"] == 0,
        report["rejected_or_superseded_plate_restoration_count"] == 0,
        report["obsolete_page_mapping_restoration_count"] == 0,
        report["unexpected_repository_drift_count"] == 0,
        report["accepted_state_guard_failures_count"] == 0,
    ])
    out_json.write_text(
        json.dumps(report, ensure_ascii=False, indent=2, sort_keys=True) + "\n",
        encoding="utf-8",
    )
    return report


def run_once(repo: Path, label: str, out: Path) -> int:
    out.mkdir(parents=True, exist_ok=True)
    driver = import_driver(repo)

    state = driver.preflight(repo)
    manifest = state["manifest"]
    canonical = state["canonical"]

    zip_blob = git("hash-object", str(canonical.relative_to(repo)), cwd=repo)
    if zip_blob != EXPECTED_ZIP_BLOB:
        raise RuntimeError(f"canonical ZIP Git blob mismatch: {zip_blob}")
    actual_zip_sha = sha256_file(canonical)
    if actual_zip_sha != EXPECTED_ZIP_SHA256:
        raise RuntimeError(f"canonical ZIP SHA-256 mismatch: {actual_zip_sha}")

    work = out / "work"
    if work.exists():
        shutil.rmtree(work)
    work.mkdir()
    source_root = driver.safe_extract_zip(canonical, work / "extracted")

    log_path = out / f"RUN_{label}_19_STEP_EXECUTION_LOG.jsonl"
    step_records = []
    with log_path.open("w", encoding="utf-8") as logf:
        for step in manifest["steps"]:
            before = hash_tree(source_root)
            record = {
                "step": step["step"],
                "script": step["path"],
                "governing_commit": step["governing_commit"],
                "git_blob": step["git_blob"],
                "mode": step["mode"],
                "exit_code": None,
                "warnings_or_output": "",
                "changed_files": [],
            }
            try:
                output = driver.execute_step(repo, source_root, step, work)
                record["exit_code"] = 0
                record["warnings_or_output"] = output
            except BaseException as e:
                record["exit_code"] = 1
                record["warnings_or_output"] = repr(e)
                after = hash_tree(source_root)
                record["changed_files"] = sorted(set(before) | set(after))
                logf.write(json.dumps(record, ensure_ascii=False) + "\n")
                logf.flush()
                raise
            after = hash_tree(source_root)
            changed = sorted(p for p in set(before) | set(after) if before.get(p) != after.get(p))
            record["changed_files"] = changed
            step_records.append(record)
            logf.write(json.dumps(record, ensure_ascii=False) + "\n")
            logf.flush()

    if len(step_records) != 19 or [r["step"] for r in step_records] != list(range(1, 20)):
        raise RuntimeError("19-step execution ledger incomplete or out of order")

    a, b = driver.payload_paths(source_root)
    if a.read_bytes() != b.read_bytes():
        raise RuntimeError("final payload copies diverged")
    final_html = a.read_text(encoding="utf-8")

    authoritative = out / "AUTHORITATIVE_RECONSTRUCTED_SOURCE.html"
    shutil.copyfile(a, authoritative)

    hashes = hash_tree(source_root)
    hash_manifest = out / f"RUN_{label}_SHA256_MANIFEST.txt"
    write_hash_manifest(hashes, hash_manifest)

    matrix_report = out / f"RUN_{label}_MATRIX02_RECONSTRUCTION.csv"
    matrix_pass, failed_rows = run_matrix_regression(repo, final_html, matrix_report)

    integrity_path = out / f"RUN_{label}_FULL_SOURCE_INTEGRITY.json"
    integrity = integrity_audit(repo, source_root, final_html, integrity_path)
    integrity["accepted_transformations_executed"] = 19
    integrity["accepted_transformations_exactly_once"] = True
    integrity["skipped_transformations"] = 0
    integrity["duplicate_transformations"] = 0
    integrity["matrix_pass_count"] = matrix_pass
    integrity["matrix_fail_count"] = len(failed_rows)
    integrity["matrix_failed_row_ids"] = failed_rows
    integrity_path.write_text(json.dumps(integrity, ensure_ascii=False, indent=2, sort_keys=True) + "\n", encoding="utf-8")

    summary = {
        "run": label,
        "canonical_zip_sha256": actual_zip_sha,
        "canonical_zip_git_blob": zip_blob,
        "accepted_transforms_executed": 19,
        "superseded_transforms_executed": 0,
        "source_tree_files": len(hashes),
        "source_tree_manifest_sha256": sha256_file(hash_manifest),
        "authoritative_html_sha256": sha256_file(authoritative),
        "authoritative_html_size": authoritative.stat().st_size,
        "matrix_pass": matrix_pass,
        "matrix_failed": failed_rows,
        "full_source_integrity_pass": integrity["pass"],
    }
    (out / f"RUN_{label}_SUMMARY.json").write_text(
        json.dumps(summary, indent=2, sort_keys=True) + "\n", encoding="utf-8"
    )

    print(json.dumps(summary, sort_keys=True))
    if failed_rows or not integrity["pass"]:
        return 2
    return 0


def read_manifest(path: Path) -> dict[str, str]:
    out = {}
    for line in path.read_text(encoding="utf-8").splitlines():
        if not line.strip():
            continue
        h, rel = line.split("  ", 1)
        out[rel] = h
    return out


def compare_runs(a_dir: Path, b_dir: Path, out_dir: Path) -> int:
    out_dir.mkdir(parents=True, exist_ok=True)
    a = read_manifest(a_dir / "RUN_A_SHA256_MANIFEST.txt")
    b = read_manifest(b_dir / "RUN_B_SHA256_MANIFEST.txt")
    paths = sorted(set(a) | set(b))
    differing = [p for p in paths if p in a and p in b and a[p] != b[p]]
    only_a = [p for p in paths if p in a and p not in b]
    only_b = [p for p in paths if p in b and p not in a]
    identical = [p for p in paths if p in a and p in b and a[p] == b[p]]

    ma = a_dir / "RUN_A_MATRIX02_RECONSTRUCTION.csv"
    mb = b_dir / "RUN_B_MATRIX02_RECONSTRUCTION.csv"
    matrix_equal = ma.read_bytes() == mb.read_bytes()
    ia = json.loads((a_dir / "RUN_A_FULL_SOURCE_INTEGRITY.json").read_text(encoding="utf-8"))
    ib = json.loads((b_dir / "RUN_B_FULL_SOURCE_INTEGRITY.json").read_text(encoding="utf-8"))
    matrix_a_pass = ia.get("matrix_pass_count") == 76 and ia.get("matrix_fail_count") == 0
    matrix_b_pass = ib.get("matrix_pass_count") == 76 and ib.get("matrix_fail_count") == 0

    report = {
        "files_in_run_a": len(a),
        "files_in_run_b": len(b),
        "files_compared": len(paths),
        "identical": len(identical),
        "differing": len(differing),
        "only_in_a": len(only_a),
        "only_in_b": len(only_b),
        "differing_paths": differing,
        "only_in_a_paths": only_a,
        "only_in_b_paths": only_b,
        "manifest_equality": not differing and not only_a and not only_b,
        "matrix_reports_byte_identical": matrix_equal,
        "matrix_run_a_76_of_76": matrix_a_pass,
        "matrix_run_b_76_of_76": matrix_b_pass,
        "integrity_run_a_pass": bool(ia.get("pass")),
        "integrity_run_b_pass": bool(ib.get("pass")),
    }
    report["phase_b2_pass"] = all([
        report["manifest_equality"],
        report["matrix_reports_byte_identical"],
        report["matrix_run_a_76_of_76"],
        report["matrix_run_b_76_of_76"],
        report["integrity_run_a_pass"],
        report["integrity_run_b_pass"],
    ])
    (out_dir / "DETERMINISM_COMPARISON.json").write_text(
        json.dumps(report, indent=2, sort_keys=True) + "\n", encoding="utf-8"
    )
    (out_dir / "DETERMINISM_COMPARISON.txt").write_text(
        "\n".join([
            f"FILES_IN_RUN_A={len(a)}",
            f"FILES_IN_RUN_B={len(b)}",
            f"FILES_COMPARED={len(paths)}",
            f"IDENTICAL={len(identical)}",
            f"DIFFERING={len(differing)}",
            f"ONLY_IN_A={len(only_a)}",
            f"ONLY_IN_B={len(only_b)}",
            f"MANIFEST_EQUALITY={'PASS' if report['manifest_equality'] else 'FAIL'}",
            f"MATRIX_A_76_OF_76={'PASS' if matrix_a_pass else 'FAIL'}",
            f"MATRIX_B_76_OF_76={'PASS' if matrix_b_pass else 'FAIL'}",
            f"FULL_SOURCE_INTEGRITY_A={'PASS' if ia.get('pass') else 'FAIL'}",
            f"FULL_SOURCE_INTEGRITY_B={'PASS' if ib.get('pass') else 'FAIL'}",
            f"PHASE_B2={'PASS' if report['phase_b2_pass'] else 'FAIL'}",
            "",
        ]),
        encoding="utf-8",
    )
    print(json.dumps(report, sort_keys=True))
    return 0 if report["phase_b2_pass"] else 3


def preflight(repo: Path, out: Path) -> int:
    out.mkdir(parents=True, exist_ok=True)
    driver = import_driver(repo)
    state = driver.preflight(repo)

    head = git("rev-parse", "HEAD", cwd=repo)
    if not git("merge-base", "--is-ancestor", BASELINE, head, cwd=repo) == "":
        # git merge-base --is-ancestor has no stdout; successful command is enough.
        pass
    driver_blob = git("hash-object", "ci/reconstruct_v188_accepted_source.py", cwd=repo)
    manifest_blob = git("hash-object", "ci/v188_accepted_transform_manifest.json", cwd=repo)
    matrix_blob = git("hash-object", "provenance/V188_SOURCE_GATE_MATRIX_02.csv", cwd=repo)
    release_blob = git("hash-object", ".github/workflows/invertebrata-v188-debug.yml", cwd=repo)
    zip_blob = git("hash-object", "INVERTEBRATA_v1.8.7_RECONCILED_ANDROID_SOURCE.zip", cwd=repo)
    zip_sha = sha256_file(repo / "INVERTEBRATA_v1.8.7_RECONCILED_ANDROID_SOURCE.zip")
    step4_blob = git("hash-object", "ci/reconcile_v188_svg_batch2.py", cwd=repo)
    step4_sha256 = sha256_file(repo / "ci/reconcile_v188_svg_batch2.py")
    branch = os.environ.get("GITHUB_REF_NAME", "")
    changed = set(git("diff", "--name-only", f"{BASELINE}..HEAD", cwd=repo).splitlines())
    unexpected = sorted(changed - AUDIT_ALLOWED_DIFFS)

    manifest = state["manifest"]
    record = {
        "head": head,
        "baseline": BASELINE,
        "branch": branch,
        "driver_blob": driver_blob,
        "driver_blob_expected": EXPECTED_DRIVER_BLOB,
        "manifest_blob": manifest_blob,
        "manifest_blob_expected": EXPECTED_MANIFEST_BLOB,
        "matrix_blob": matrix_blob,
        "matrix_blob_expected": EXPECTED_MATRIX_BLOB,
        "release_workflow_blob": release_blob,
        "release_workflow_blob_expected": EXPECTED_RELEASE_WORKFLOW_BLOB,
        "canonical_zip_blob": zip_blob,
        "canonical_zip_blob_expected": EXPECTED_ZIP_BLOB,
        "canonical_zip_sha256": zip_sha,
        "canonical_zip_sha256_expected": EXPECTED_ZIP_SHA256,
        "step4_blob": step4_blob,
        "step4_blob_expected": EXPECTED_STEP4_BLOB,
        "step4_sha256": step4_sha256,
        "accepted_steps": len(manifest["steps"]),
        "step_order": [s["step"] for s in manifest["steps"]],
        "superseded_in_executable_set": sum(1 for s in manifest["steps"] if s["governing_commit"] in manifest.get("superseded_commits", [])),
        "matrix_rows": EXPECTED_ROWS,
        "unexpected_repo_drift": unexpected,
    }
    checks = [
        branch == "audit/phase-b2-after-routing-20260919",
        driver_blob == EXPECTED_DRIVER_BLOB,
        manifest_blob == EXPECTED_MANIFEST_BLOB,
        matrix_blob == EXPECTED_MATRIX_BLOB,
        release_blob == EXPECTED_RELEASE_WORKFLOW_BLOB,
        zip_blob == EXPECTED_ZIP_BLOB,
        zip_sha == EXPECTED_ZIP_SHA256,
        step4_blob == EXPECTED_STEP4_BLOB,
        len(manifest["steps"]) == 19,
        [s["step"] for s in manifest["steps"]] == list(range(1, 20)),
        record["superseded_in_executable_set"] == 0,
        not unexpected,
    ]
    record["pass"] = all(checks)
    (out / "CANONICAL_ZIP_SHA256_VERIFICATION.json").write_text(
        json.dumps(record, indent=2, sort_keys=True) + "\n", encoding="utf-8"
    )
    print(json.dumps(record, sort_keys=True))
    return 0 if record["pass"] else 4



def diagnose_step4_url(repo: Path, out: Path) -> int:
    """Reconstruct only through accepted Step 3, then enumerate URL-like strings
    in the exact payload that Step 4 receives. No source mutation is persisted.
    """
    out.mkdir(parents=True, exist_ok=True)
    driver = import_driver(repo)
    state = driver.preflight(repo)
    manifest = state["manifest"]
    canonical = state["canonical"]

    actual_zip_sha = sha256_file(canonical)
    zip_blob = git("hash-object", str(canonical.relative_to(repo)), cwd=repo)
    if actual_zip_sha != EXPECTED_ZIP_SHA256:
        raise RuntimeError(f"canonical ZIP SHA-256 mismatch: {actual_zip_sha}")
    if zip_blob != EXPECTED_ZIP_BLOB:
        raise RuntimeError(f"canonical ZIP Git blob mismatch: {zip_blob}")

    work = out / "work"
    if work.exists():
        shutil.rmtree(work)
    work.mkdir()
    source_root = driver.safe_extract_zip(canonical, work / "extracted")

    step_log = []
    for step in manifest["steps"][:3]:
        output = driver.execute_step(repo, source_root, step, work)
        step_log.append({
            "step": step["step"],
            "script": step["path"],
            "governing_commit": step["governing_commit"],
            "git_blob": step["git_blob"],
            "output": output,
        })

    a, b = driver.payload_paths(source_root)
    if a.read_bytes() != b.read_bytes():
        raise RuntimeError("payload copies diverged after Step 3")
    payload = a.read_text(encoding="utf-8")

    token_re = re.compile(r'https?://[^\\s"\'<>]+')
    hits = []
    for m in token_re.finditer(payload):
        start = max(0, m.start() - 180)
        end = min(len(payload), m.end() + 180)
        context = payload[start:end].replace("\n", "\\n")
        hits.append({
            "scheme": "https" if m.group(0).startswith("https://") else "http",
            "value": m.group(0),
            "offset": m.start(),
            "context": context,
        })

    # Also record generic occurrences even if punctuation made the URL regex stop early.
    raw_https_offsets = [m.start() for m in re.finditer("https://", payload)]
    raw_http_offsets = [m.start() for m in re.finditer("http://", payload)]

    # Classify HTML resource-loading constructs separately from mere strings.
    remote_loads = []
    load_pat = re.compile(
        r'<(?:script|img|iframe|link|source)\b[^>]*(?:src|href)\s*=\s*["\'](https?://[^"\']+)["\']',
        re.I,
    )
    for m in load_pat.finditer(payload):
        remote_loads.append({"url": m.group(1), "context": m.group(0)})

    js_network = []
    js_pat = re.compile(
        r'\b(?:fetch|XMLHttpRequest|WebSocket)\s*\([^\n]{0,300}?https?://[^\s"\'<>]+',
        re.I,
    )
    for m in js_pat.finditer(payload):
        js_network.append(m.group(0))

    report = {
        "canonical_zip_sha256": actual_zip_sha,
        "canonical_zip_git_blob": zip_blob,
        "steps_executed": [x["step"] for x in step_log],
        "step_log": step_log,
        "payload_sha256_after_step3": sha256_file(a),
        "payload_size_after_step3": a.stat().st_size,
        "raw_https_occurrences": len(raw_https_offsets),
        "raw_http_occurrences": len(raw_http_offsets),
        "url_like_hits": hits,
        "remote_resource_loads": remote_loads,
        "javascript_network_calls": js_network,
    }
    (out / "STEP3_URL_DIAGNOSTIC.json").write_text(
        json.dumps(report, ensure_ascii=False, indent=2, sort_keys=True) + "\n",
        encoding="utf-8",
    )
    with (out / "STEP3_URL_DIAGNOSTIC.txt").open("w", encoding="utf-8") as fh:
        fh.write(f"PAYLOAD_SHA256_AFTER_STEP3={report['payload_sha256_after_step3']}\n")
        fh.write(f"RAW_HTTPS_OCCURRENCES={report['raw_https_occurrences']}\n")
        fh.write(f"RAW_HTTP_OCCURRENCES={report['raw_http_occurrences']}\n")
        fh.write(f"REMOTE_RESOURCE_LOADS={len(remote_loads)}\n")
        fh.write(f"JAVASCRIPT_NETWORK_CALLS={len(js_network)}\n\n")
        for i, hit in enumerate(hits, 1):
            fh.write(f"[{i}] {hit['value']}\nOFFSET={hit['offset']}\nCONTEXT={hit['context']}\n\n")

    print(json.dumps({
        "payload_sha256_after_step3": report["payload_sha256_after_step3"],
        "raw_https_occurrences": report["raw_https_occurrences"],
        "raw_http_occurrences": report["raw_http_occurrences"],
        "remote_resource_loads": len(remote_loads),
        "javascript_network_calls": len(js_network),
        "urls": [x["value"] for x in hits],
    }, ensure_ascii=False, sort_keys=True))
    return 0


def validate_step4_security_patch(repo: Path, out: Path) -> int:
    """Validate only the narrowed Batch-2 offline-safety assertion.

    Reconstruct through accepted Steps 1-3, execute the patched Step 4 once,
    prove the accepted local appassets CSP is permitted, and prove unapproved
    remote HTTP(S) or iframe/eval constructs are still rejected.
    """
    out.mkdir(parents=True, exist_ok=True)
    driver = import_driver(repo)

    canonical = repo / "INVERTEBRATA_v1.8.7_RECONCILED_ANDROID_SOURCE.zip"
    if sha256_file(canonical) != EXPECTED_ZIP_SHA256:
        raise RuntimeError("canonical ZIP SHA-256 mismatch")
    if git("hash-object", canonical.name, cwd=repo) != EXPECTED_ZIP_BLOB:
        raise RuntimeError("canonical ZIP Git blob mismatch")

    manifest = json.loads((repo / "ci/v188_accepted_transform_manifest.json").read_text(encoding="utf-8"))
    if len(manifest["steps"]) != 19:
        raise RuntimeError("accepted manifest is not 19 steps")

    # Phase-B1 identity is required for steps 1-3; Step 4 is intentionally the
    # audit-branch remediation candidate under test.
    for step in manifest["steps"][:3]:
        observed = git("hash-object", step["path"], cwd=repo)
        if observed != step["git_blob"]:
            raise RuntimeError(f"pre-Step4 accepted blob drift at step {step['step']}: {observed}")

    work = out / "work"
    if work.exists():
        shutil.rmtree(work)
    work.mkdir()
    source_root = driver.safe_extract_zip(canonical, work / "extracted")

    exec_log = []
    for step in manifest["steps"][:3]:
        output = driver.execute_step(repo, source_root, step, work)
        exec_log.append({
            "step": step["step"],
            "script": step["path"],
            "git_blob": step["git_blob"],
            "exit_code": 0,
            "output": output,
        })

    a, b = driver.payload_paths(source_root)
    before_step4 = a.read_text(encoding="utf-8")
    trusted_csp = "img-src 'self' data: blob: https://appassets.androidplatform.net;"
    exact_https = "https://appassets.androidplatform.net"
    if before_step4.count(exact_https) != 1:
        raise RuntimeError(f"expected exactly one appassets HTTPS origin before Step4, found {before_step4.count(exact_https)}")
    if before_step4.count(trusted_csp) != 1:
        raise RuntimeError("accepted appassets origin is not confined to the expected CSP directive")

    # Execute the patched Step 4 directly.  The persisted Phase-B1 manifest is
    # deliberately not changed during this narrow remediation audit.
    patched_step4 = repo / "ci/reconcile_v188_svg_batch2.py"
    cp = subprocess.run(
        [sys.executable, str(patched_step4), str(source_root)],
        text=True,
        stdout=subprocess.PIPE,
        stderr=subprocess.STDOUT,
        check=False,
    )
    exec_log.append({
        "step": 4,
        "script": "ci/reconcile_v188_svg_batch2.py",
        "git_blob": git("hash-object", "ci/reconcile_v188_svg_batch2.py", cwd=repo),
        "exit_code": cp.returncode,
        "output": cp.stdout,
    })
    if cp.returncode != 0:
        raise RuntimeError(f"patched Step 4 failed ({cp.returncode}):\\n{cp.stdout}")

    a, b = driver.payload_paths(source_root)
    if a.read_bytes() != b.read_bytes():
        raise RuntimeError("payload copies diverged after patched Step 4")
    payload = a.read_text(encoding="utf-8")

    # Import the patched assertion and exercise it independently.
    spec = importlib.util.spec_from_file_location("batch2_patched", patched_step4)
    if spec is None or spec.loader is None:
        raise RuntimeError("cannot import patched Batch-2 module")
    mod = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(mod)

    # Accepted payload must pass.
    mod.assert_offline_safety(payload)

    negative_cases = {
        "external_https_image": payload + '\\n<img src="https://example.invalid/x.png">\\n',
        "external_http_image": payload + '\\n<img src="http://example.invalid/x.png">\\n',
        "iframe": payload + '\\n<iframe src="about:blank"></iframe>\\n',
        "eval": payload + '\\n<script>eval("1")</script>\\n',
        "new_function": payload + '\\n<script>new Function("return 1")</script>\\n',
        "second_appassets_occurrence": payload + '\\nhttps://appassets.androidplatform.net\\n',
    }
    negative_results = {}
    for name, sample in negative_cases.items():
        rejected = False
        message = ""
        try:
            mod.assert_offline_safety(sample)
        except SystemExit as e:
            rejected = True
            message = str(e)
        negative_results[name] = {"rejected": rejected, "message": message}
        if not rejected:
            raise RuntimeError(f"offline assertion negative test did not reject: {name}")

    # Independent structural checks: the single HTTPS occurrence is the trusted
    # local CSP origin; no remote-loading HTML element is present.
    remote_load_pat = re.compile(
        r"<(?:script|img|iframe|link|source)\b[^>]*(?:src|href)\s*=\s*['\"](https?://[^'\"]+)['\"]",
        re.I,
    )
    remote_loads = [m.group(1) for m in remote_load_pat.finditer(payload)]
    all_https = [m.start() for m in re.finditer("https://", payload)]
    all_http = [m.start() for m in re.finditer("http://", payload)]
    svg_ns_count = payload.count("http://www.w3.org/2000/svg")

    report = {
        "canonical_zip_sha256": sha256_file(canonical),
        "payload_sha256_before_step4": hashlib.sha256(before_step4.encode("utf-8")).hexdigest(),
        "payload_sha256_after_step4": sha256_file(a),
        "patched_step4_blob": git("hash-object", "ci/reconcile_v188_svg_batch2.py", cwd=repo),
        "steps_1_to_4": exec_log,
        "trusted_local_origin": exact_https,
        "trusted_csp_directive": trusted_csp,
        "trusted_origin_occurrences_after_step4": payload.count(exact_https),
        "raw_https_occurrences_after_step4": len(all_https),
        "raw_http_occurrences_after_step4": len(all_http),
        "svg_namespace_occurrences_after_step4": svg_ns_count,
        "remote_resource_loads": remote_loads,
        "negative_security_tests": negative_results,
        "accepted_payload_assertion": "PASS",
        "academic_content_patch": "NONE — assertion only",
    }
    report["pass"] = (
        payload.count(exact_https) == 1
        and len(all_https) == 1
        and not remote_loads
        and all(x["rejected"] for x in negative_results.values())
    )

    (out / "BATCH2_SECURITY_ASSERTION_VALIDATION.json").write_text(
        json.dumps(report, ensure_ascii=False, indent=2, sort_keys=True) + "\\n",
        encoding="utf-8",
    )
    (out / "BATCH2_SECURITY_ASSERTION_VALIDATION.txt").write_text(
        "\\n".join([
            f"TRUSTED_LOCAL_ORIGIN={exact_https}",
            f"TRUSTED_ORIGIN_OCCURRENCES={payload.count(exact_https)}",
            f"RAW_HTTPS_OCCURRENCES={len(all_https)}",
            f"REMOTE_RESOURCE_LOADS={len(remote_loads)}",
            f"NEGATIVE_SECURITY_TESTS={'PASS' if all(x['rejected'] for x in negative_results.values()) else 'FAIL'}",
            f"PATCHED_STEP4_EXECUTION={'PASS' if cp.returncode == 0 else 'FAIL'}",
            f"VALIDATION={'PASS' if report['pass'] else 'FAIL'}",
            "",
        ]),
        encoding="utf-8",
    )
    print(json.dumps({
        "trusted_local_origin": exact_https,
        "trusted_origin_occurrences": payload.count(exact_https),
        "raw_https_occurrences": len(all_https),
        "remote_resource_loads": len(remote_loads),
        "negative_security_tests": negative_results,
        "patched_step4_exit_code": cp.returncode,
        "pass": report["pass"],
    }, sort_keys=True))
    return 0 if report["pass"] else 5

def main() -> None:
    ap = argparse.ArgumentParser()
    sub = ap.add_subparsers(dest="cmd", required=True)

    p = sub.add_parser("preflight")
    p.add_argument("--repo", type=Path, default=Path("."))
    p.add_argument("--out", type=Path, required=True)

    p = sub.add_parser("diagnose-step4-url")
    p.add_argument("--repo", type=Path, default=Path("."))
    p.add_argument("--out", type=Path, required=True)

    p = sub.add_parser("validate-step4-security-patch")
    p.add_argument("--repo", type=Path, default=Path("."))
    p.add_argument("--out", type=Path, required=True)

    p = sub.add_parser("harness-validate")
    p.add_argument("--repo", type=Path, default=Path("."))
    p.add_argument("--prior-html", type=Path, required=True)
    p.add_argument("--out", type=Path, required=True)

    p = sub.add_parser("run")
    p.add_argument("--repo", type=Path, default=Path("."))
    p.add_argument("--label", choices=["A", "B"], required=True)
    p.add_argument("--out", type=Path, required=True)

    p = sub.add_parser("compare")
    p.add_argument("--a", type=Path, required=True)
    p.add_argument("--b", type=Path, required=True)
    p.add_argument("--out", type=Path, required=True)

    args = ap.parse_args()
    repo = args.repo.resolve() if hasattr(args, "repo") else None
    if args.cmd == "preflight":
        raise SystemExit(preflight(repo, args.out.resolve()))
    if args.cmd == "diagnose-step4-url":
        raise SystemExit(diagnose_step4_url(repo, args.out.resolve()))
    if args.cmd == "validate-step4-security-patch":
        raise SystemExit(validate_step4_security_patch(repo, args.out.resolve()))
    if args.cmd == "harness-validate":
        raise SystemExit(validate_harness_against_prior_run(
            repo, args.prior_html.resolve(), args.out.resolve()
        ))
    if args.cmd == "run":
        raise SystemExit(run_once(repo, args.label, args.out.resolve()))
    if args.cmd == "compare":
        raise SystemExit(compare_runs(args.a.resolve(), args.b.resolve(), args.out.resolve()))


if __name__ == "__main__":
    main()
