import ast
import json
import pathlib
import tempfile
import unittest

ROOT = pathlib.Path(__file__).resolve().parents[1]

class CreditRegenerationTest(unittest.TestCase):
    def test_curated_historical_credit_survives_and_registered_credit_updates(self):
        tree = ast.parse((ROOT / "tools/plate_intake.py").read_text())
        function = next(n for n in tree.body if isinstance(n, ast.FunctionDef) and n.name == "build_credits")
        with tempfile.TemporaryDirectory() as directory:
            target = pathlib.Path(directory) / "credits.json"
            original = {"historical/plate": {"en": "Original author, Fig. 63", "ta": "அசல் மூலம்"},
                        "registered/plate": {"en": "Old credit", "ta": "Old credit"}}
            target.write_text(json.dumps(original))
            scope = {"json": json, "CREDITS": target}
            exec(compile(ast.Module(body=[function], type_ignores=[]), "<credits>", "exec"), scope)
            record = {"plates": {"registered/plate": {"credit_en": "Correct source", "credit_ta": "சரியான மூலம்"}}}
            scope["build_credits"](record)
            result = json.loads(target.read_text())
            self.assertEqual(original["historical/plate"], result["historical/plate"])
            self.assertEqual("Correct source", result["registered/plate"]["en"])
            scope["build_credits"](record)
            self.assertEqual(result, json.loads(target.read_text()))

    def test_fasciola_credit_does_not_misclassify_historical_plate(self):
        credits = json.loads((ROOT / "native-app/app/src/main/assets/type_studies/plate_credits.json").read_text())
        credit = credits["fasciola/life-cycle"]
        self.assertIn("Chandler (1918)", credit["en"])
        self.assertIn("63", credit["ta"])
        self.assertNotIn("AI-generated", credit["en"])
        for slot in ("unit1/references/trypanosoma", "unit1/references/leishmania"):
            self.assertIn("AI-generated", credits[slot]["en"])

if __name__ == "__main__":
    unittest.main()
