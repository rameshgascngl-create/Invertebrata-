from pathlib import Path
p=Path("native-app/app/src/main/java/com/gasczoology/invertebratelab/ui/InvertebrataApp.kt")
s=p.read_text(encoding="utf-8")
if "chapter-learning-outcomes" in s:
    raise SystemExit("Already integrated")
def one(a,b):
    global s
    assert s.count(a)==1, a[:80]
    s=s.replace(a,b,1)
one("import com.gasczoology.invertebratelab.data.Chapter\n",
    "import com.gasczoology.invertebratelab.data.Chapter\nimport com.gasczoology.invertebratelab.data.ChapterStudyGuides\n")
one("    val restoredLesson = remember(chapter.id) { FiveUnitRestorationLessons.byId[chapter.id] }",
    "    val restoredLesson = remember(chapter.id) { FiveUnitRestorationLessons.byId[chapter.id] }\n    val studyGuide = remember(chapter.id) { ChapterStudyGuides.byChapterId[chapter.id] }")
one('setOf("u1-paramecium", "u4-penaeus", "u5-pila", "u5-asterias")',
    'setOf("u1-paramecium", "u4-penaeus")')
anchor='                        Text(label(language,\n                            "TEXTBOOK FIRST · English–Tamil academic draft;'
assert s.count(anchor)==1
s=s.replace(anchor,'''                        if (studyGuide != null) {
                            Text(label(language, "LEARNING OUTCOMES", "கற்றல் விளைவுகள்"),
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.testTag("chapter-learning-outcomes"))
                            studyGuide.objectives.forEachIndexed { index, outcome ->
                                Text("${index + 1}. ${outcome.value(language)}",
                                    style = MaterialTheme.typography.bodyLarge)
                            }
                        }
'''+anchor,1)
start=s.index('            if (restoredLesson != null) {')
end=s.index('            if (lessonDraft != null) {',start)
old=s[start:end]
tail='                }\n            }\n'
assert old.endswith(tail)
addition='''                }
                if (chapter.id == "u5-pila" || chapter.id == "u5-asterias") {
                    item(key = "r2-type-study-gallery") {
                        val study = NativeCurriculumManifest.typeStudies.single { it.lessonId == chapter.id }
                        RequiredTypeStudyPlates(study, language)
                    }
                }
                if (studyGuide != null) {
                    item(key = "chapter-review-prompts") {
                        Column(Modifier.fillMaxWidth().testTag("chapter-review-prompts"),
                            verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text(label(language, "CHECK YOUR UNDERSTANDING", "புரிதலைச் சரிபார்க்கவும்"),
                                style = MaterialTheme.typography.titleLarge)
                            studyGuide.reviewPrompts.forEachIndexed { index, prompt ->
                                Text("${index + 1}. ${prompt.value(language)}",
                                    style = MaterialTheme.typography.bodyLarge)
                            }
                        }
                    }
                }
            }
'''
s=s[:start]+old[:-len(tail)]+addition+s[end:]
p.write_text(s,encoding="utf-8")
print("Student study guide UI integrated")
