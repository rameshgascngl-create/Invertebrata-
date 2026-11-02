#!/usr/bin/env python3
"""Produce canonical JUnit copies; retain raw Gradle reports and genuine failures.

AGP can turn Android Test Platform's explicit ignored result into an empty JUnit
failure. Only the matching structured ignored result may reclassify that entry.
Unknown protobuf versions, malformed records and non-empty failures stay failures.
"""
import argparse
import json
import sys
from pathlib import Path
import xml.etree.ElementTree as ET


def fields(data):
    def integer(at):
        value = shift = 0
        while at < len(data) and shift < 70:
            byte = data[at]; at += 1
            value |= (byte & 127) << shift
            if byte < 128:
                return value, at
            shift += 7
        raise ValueError("Malformed protobuf integer")
    at = 0
    result = []
    while at < len(data):
        tag, at = integer(at)
        number, wire = tag >> 3, tag & 7
        if not number:
            raise ValueError("Invalid protobuf field")
        if wire == 0:
            value, at = integer(at)
        elif wire == 2:
            size, at = integer(at)
            end = at + size
            if end > len(data):
                raise ValueError("Truncated protobuf field")
            value, at = data[at:end], end
        elif wire in (1, 5):
            end = at + (8 if wire == 1 else 4)
            if end > len(data):
                raise ValueError("Truncated protobuf fixed field")
            value, at = data[at:end], end
        else:
            raise ValueError("Unsupported protobuf wire type")
        result.append((number, value))
    return result


def ignored_cases(data):
    ignored = set()
    try:
        for number, record in fields(data):
            if number != 3 or not isinstance(record, bytes):
                continue
            result = dict(fields(record))
            if result.get(2) != 6:
                continue
            identity = dict(fields(result.get(1, b"")))
            error = dict(fields(result.get(3, b"")))
            if error.get(1) != b"Test ignored" or error.get(2) != b"org.opentest4j.TestAbortedException":
                continue
            name = identity.get(3, b"").decode("utf-8")
            class_name = identity.get(1, b"").decode("utf-8")
            package = identity.get(2, b"").decode("utf-8")
            if name and class_name and package:
                ignored.add((package + "." + class_name, name))
    except (ValueError, UnicodeError, TypeError):
        return set()
    return ignored


def canonicalise(root, ignored):
    counts = dict(passed=0, failed=0, skipped=0)
    cases = []
    for case in root.iter("testcase"):
        key = (case.get("classname", ""), case.get("name", ""))
        failures = list(case.findall("failure")) + list(case.findall("error"))
        empty_failure = len(failures) == 1 and not failures[0].attrib and not (failures[0].text or "").strip() and len(failures[0]) == 0
        if empty_failure and key in ignored:
            case.remove(failures[0])
            ET.SubElement(case, "skipped", {"message": "Test ignored (confirmed by Android Test Platform)"})
            failures = []
        status = "failed" if failures else "skipped" if case.find("skipped") is not None else "passed"
        counts[status] += 1
        cases.append(dict(test=".".join(key), result=status))
        if status == "failed":
            # Surface the failing test and message in the job log; the XML is otherwise only in the artifact.
            detail = " | ".join(((f.get("message") or "") + " " + (f.text or "").strip().splitlines()[0:1].__str__()).strip()
                                for f in failures)[:900]
            print("FAILED_TEST " + ".".join(key) + " :: " + detail, file=sys.stderr)
    for suite in root.iter("testsuite"):
        entries = list(suite.iter("testcase"))
        suite.set("tests", str(len(entries)))
        suite.set("failures", str(sum(c.find("failure") is not None for c in entries)))
        suite.set("errors", str(sum(c.find("error") is not None for c in entries)))
        suite.set("skipped", str(sum(c.find("skipped") is not None for c in entries)))
    return counts, cases


def summarise(directory):
    directory = Path(directory)
    destination = directory / "canonical-junit"
    paths = sorted(p for p in directory.rglob("TEST-*.xml") if destination not in p.parents)
    report = dict(reports=[], totals=dict(passed=0, failed=0, skipped=0))
    for path in paths:
        ignored = set()
        for result in path.parent.rglob("test-result.pb"):
            ignored.update(ignored_cases(result.read_bytes()))
        root = ET.parse(path).getroot()
        counts, cases = canonicalise(root, ignored)
        output = destination / path.relative_to(directory)
        output.parent.mkdir(parents=True, exist_ok=True)
        ET.ElementTree(root).write(output, encoding="utf-8", xml_declaration=True)
        report["reports"].append(dict(source=str(path.relative_to(directory)), counts=counts, tests=cases))
        for status, count in counts.items():
            report["totals"][status] += count
    directory.mkdir(parents=True, exist_ok=True)
    (directory / "canonical-test-summary.json").write_text(json.dumps(report, indent=2) + "\n")
    return report


if __name__ == "__main__":
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("directory")
    options = parser.parse_args()
    result = summarise(options.directory)
    print(json.dumps(result["totals"]))
    raise SystemExit(1 if result["totals"]["failed"] else 0)
