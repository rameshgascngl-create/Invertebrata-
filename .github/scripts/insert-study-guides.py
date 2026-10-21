from pathlib import Path
p=Path("native-app/app/src/main/java/com/gasczoology/invertebratelab/ui/InvertebrataApp.kt")
s=p.read_text()
old="import com.gasczoology.invertebratelab.data.Chapter\n"
new=old+"import com.gasczoology.invertebratelab.data.ChapterStudyGuides\n"
assert s.count(old)==1
p.write_text(s.replace(old,new))
