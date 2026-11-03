import json,pathlib,re,unittest
ROOT=pathlib.Path(__file__).resolve().parents[1]
DATA=ROOT/'native-app/app/src/main/java/com/gasczoology/invertebratelab/data'
class TamilWholeAppAuditTest(unittest.TestCase):
    def bank(self):
        entries={}
        files=sorted((ROOT/'native-app/app/src/main/assets/a5').glob('*.json'))
        self.assertEqual(5,len(files))
        for path in files:
            for key,value in json.loads(path.read_text()).items():
                self.assertNotIn(key,entries)
                self.assertEqual(key,value['id'])
                entries[key]=value
        return entries
    def test_all_43_validated_chapters_have_native_teaching_sources(self):
        required=set(self.bank());self.assertEqual(43,len(required));found={}
        for path in DATA.glob('*BookChapter.kt'):
            match=re.search(r'lessonId\s*=\s*"([^"]+)"',path.read_text())
            if match and match[1] in required:
                self.assertNotIn(match[1],found)
                found[match[1]]=path
        self.assertEqual(required,set(found))
    def test_all_86_questions_and_430_points_are_bilingual_and_complete(self):
        entries=self.bank()
        for language in ['en','ta']:
            questions=points=0
            for key,value in entries.items():
                qs=value['q5_'+language];answers=value['a5_'+language]
                self.assertEqual(2,len(qs),key);self.assertEqual(len(qs),len(answers),key)
                for question,answer in zip(qs,answers):
                    self.assertTrue(question.strip(),key);self.assertEqual(5,len(answer),key)
                    for text in [question]+answer:
                        self.assertTrue(isinstance(text,str) and text.strip(),key)
                        if language=='ta':self.assertRegex(text,r'[\u0B80-\u0BFF]')
                    questions+=1;points+=len(answer)
            self.assertEqual(86,questions);self.assertEqual(430,points)
    def test_high_risk_tamil_regressions_remain_clear(self):
        sycon=(DATA/'SyconBookChapter.kt').read_text()
        self.assertNotIn('பிறவி கருவுறு',sycon)
        fluke=(DATA/'FasciolaBookChapter.kt').read_text()
        self.assertNotIn('ஊடைப்ப',fluke)
        self.assertIn('முட்டை உருவாக்க அறை (Ootype)',fluke)
        self.assertNotIn('கட்டுப்பாட்டின் திறவுகோல்',(DATA/'NematodeAdaptationsBookChapter.kt').read_text())
    def test_replaced_rasters_do_not_reuse_historical_author_claims(self):
        ui=ROOT/'native-app/app/src/main/java/com/gasczoology/invertebratelab/ui'
        plates=(ui/'RequiredTypeStudyPlates.kt').read_text()
        self.assertNotIn('Hans Hillewaert',plates)
        self.assertNotIn('A. F. Arnold (1903)',plates)
        gallery=(ui/'UnitIProtozoaReferenceGallery.kt').read_text()
        self.assertNotIn('Sina Adl',gallery)
        self.assertNotIn('LadyofHats',gallery)
if __name__=='__main__':unittest.main()
