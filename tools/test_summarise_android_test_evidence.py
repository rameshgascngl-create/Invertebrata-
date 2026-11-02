import unittest
import xml.etree.ElementTree as ET
from summarise_android_test_evidence import canonicalise, ignored_cases


def varint(value):
    result = bytearray()
    while value > 127:
        result.append((value & 127) | 128); value >>= 7
    return bytes(result) + bytes([value])


def message(field, data):
    return varint(field * 8 + 2) + varint(len(data)) + data


def result(status=6, text=b"Test ignored"):
    identity = message(1, b"ExampleTest") + message(2, b"org.example") + message(3, b"example")
    error = message(1, text) + message(2, b"org.opentest4j.TestAbortedException")
    return message(3, message(1, identity) + varint(16) + varint(status) + message(3, error))


class SummaryTests(unittest.TestCase):
    def run_case(self, failure, protobuf):
        root = ET.fromstring('<testsuite><testcase classname="org.example.ExampleTest" name="example">' + failure + '</testcase></testsuite>')
        return root, canonicalise(root, ignored_cases(protobuf))[0]

    def test_explicit_ignored_result_corrects_empty_failure(self):
        root, counts = self.run_case('<failure/>', result())
        self.assertEqual(dict(passed=0, failed=0, skipped=1), counts)
        self.assertEqual('0', root.get('failures'))
        self.assertEqual('1', root.get('skipped'))

    def test_real_failure_is_never_hidden(self):
        _, counts = self.run_case('<failure>AssertionError: missing diagram</failure>', result())
        self.assertEqual(1, counts['failed'])

    def test_empty_failure_without_matching_ignored_record_stays_failed(self):
        for raw in [b'', b'broken', result(status=4), result(text=b'Assertion failed')]:
            _, counts = self.run_case('<failure/>', raw)
            self.assertEqual(1, counts['failed'])

    def test_other_test_identity_cannot_mask_failure(self):
        raw = result().replace(b'example', b'changed')
        _, counts = self.run_case('<failure/>', raw)
        self.assertEqual(1, counts['failed'])

    def test_standard_skip_and_pass_are_preserved(self):
        self.assertEqual(1, self.run_case('<skipped/>', b'')[1]['skipped'])
        self.assertEqual(1, self.run_case('', b'')[1]['passed'])


if __name__ == '__main__':
    unittest.main()
