import pathlib,re,unittest
ROOT=pathlib.Path(__file__).resolve().parents[1]
class TamilCiliationMeaningTest(unittest.TestCase):
    def test_cilia_not_labelled_as_flagella_in_bilingual_chapters(self):
        directory=ROOT/'native-app/app/src/main/java/com/gasczoology/invertebratelab/data'
        chapters=list(directory.glob('*BookChapter.kt'))
        self.assertGreaterEqual(len(chapters),40)
        pair=re.compile(r'"((?:[^"\\]|\\.)*)"\s*(?:to|,)\s*"((?:[^"\\]|\\.)*)"')
        findings=[]
        pairs_checked=0
        for path in chapters:
            source=path.read_text(encoding='utf-8')
            for match in pair.finditer(source):
                pairs_checked+=1
                english,tamil=match.groups()
                if re.search(r'\bcili(?:a|ated|ary|um)\b',english,re.I) and not re.search('flagell',english,re.I) and 'கசையிழை' in tamil:
                    findings.append(str(path.name)+':'+str(source.count('\n',0,match.start())+1))
        self.assertGreater(pairs_checked,1000)
        self.assertEqual([],findings)
if __name__=='__main__':unittest.main()
