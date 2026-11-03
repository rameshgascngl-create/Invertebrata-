import hashlib,json,pathlib,unittest
ROOT=pathlib.Path(__file__).resolve().parents[1]
class TamilTextbookAlignmentTest(unittest.TestCase):
    def test_echinoderm_reader_uses_textbook_term(self):
        source=(ROOT/'native-app/app/src/main/java/com/gasczoology/invertebratelab/data/WvsBookChapter.kt').read_text()
        self.assertIn('நீர்க்குழல் மண்டலம்',source)
        self.assertNotIn('நீர் குருதியோட்ட மண்டல',source)
        self.assertNotIn('நீரோட்டக் குழாய் மண்டல',source)
    def test_distinct_physiological_meanings_remain_explicit(self):
        directory=ROOT/'native-app/app/src/main/java/com/gasczoology/invertebratelab/data'
        sycon=(directory/'SyconBookChapter.kt').read_text()
        self.assertNotIn('பிறவி கருவுறு',sycon)
        self.assertIn('அயல் கருவுறுதல்',sycon)
        nutrition=(directory/'NutritionBookChapter.kt').read_text()
        self.assertIn('செரிக்காத பொருளை செல்லிலிருந்து வெளியேற்றுதல்',nutrition)
        flatworm=(directory/'PlatyhelminthesBookChapter.kt').read_text()
        self.assertNotIn('ஒழுங்குபடுத்தல்ம்',flatworm)
        self.assertIn('நீர்–உப்புச் சமநிலைப் பராமரிப்பையும்',flatworm)
    def test_frozen_a5_files_remain_identical_to_audit_baseline(self):
        expected=json.loads("{\"native-app/app/src/main/assets/a5/U1_A5_VALIDATED.json\":\"e87523da1cc31887e1878ecf026ca771aadec7e0\",\"native-app/app/src/main/assets/a5/U2_A5_VALIDATED.json\":\"481fb1e02f65f145a7cf016e02bf8e6e9aa0f63f\",\"native-app/app/src/main/assets/a5/U3_A5_VALIDATED.json\":\"7921fa90c30fd021171c85ad6be25a123395a518\",\"native-app/app/src/main/assets/a5/U4_A5_VALIDATED.json\":\"60550d545f8f9389f2cea176380ff3adbf2522d3\",\"native-app/app/src/main/assets/a5/U5_A5_VALIDATED.json\":\"f8d8024c9310e33aea3ff8b2a1c1618911ba221f\"}")
        self.assertEqual(5,len(expected))
        for name,sha in expected.items():
            data=(ROOT/name).read_bytes()
            self.assertEqual(sha,hashlib.sha1(b'blob '+str(len(data)).encode()+bytes([0])+data).hexdigest(),name)
if __name__=='__main__':unittest.main()
