package com.gasczoology.invertebratelab.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.gasczoology.invertebratelab.AcademicLoadState
import com.gasczoology.invertebratelab.MainViewModel
import com.gasczoology.invertebratelab.PersistedLearningLoadState
import com.gasczoology.invertebratelab.data.A5Question
import com.gasczoology.invertebratelab.data.AcademicUnit
import com.gasczoology.invertebratelab.data.AppLanguage
import com.gasczoology.invertebratelab.data.Chapter
import com.gasczoology.invertebratelab.data.NativeLearningState
import com.gasczoology.invertebratelab.data.NativeLessonDrafts
import com.gasczoology.invertebratelab.data.StudyDestination
import kotlinx.coroutines.launch

private object Routes {
    const val HOME = "home"
    const val UNIT = "unit/{unitNumber}"
    const val CHAPTER = "chapter/{chapterId}"
    const val ASSESSMENT = "assessment"
    const val PARAMECIUM = "paramecium-interactive-lab"

    fun unit(number: Int) = "unit/" + number
    fun chapter(id: String) = "chapter/" + id

    fun restoredDestination(state: NativeLearningState): String = when (state.destination) {
        StudyDestination.HOME -> HOME
        StudyDestination.UNIT -> unit(state.unitNumber)
        StudyDestination.CHAPTER -> chapter(state.chapterId)
        StudyDestination.PRACTICE -> ASSESSMENT
        StudyDestination.PARAMECIUM_LAB -> PARAMECIUM
    }
}

@Composable
fun InvertebrataApp(viewModel: MainViewModel) {
    val language by viewModel.language.collectAsState()
    val academicState by viewModel.academicState.collectAsState()
    val persisted by viewModel.persistedLearning.collectAsState()

    // Never construct navigation until the disk record and academic corpus load.
    // Remember only the initial route; later writes cannot reset the NavHost.
    val corpus = academicState as? AcademicLoadState.Ready
    val stored = persisted as? PersistedLearningLoadState.Ready
    if (corpus == null || stored == null) {
        when (academicState) {
            is AcademicLoadState.Failure ->
                ErrorScreen((academicState as AcademicLoadState.Failure).reason, language)
            else -> LoadingScreen(language)
        }
        return
    }
    val units = corpus.units
    val state = stored.state.validatedAgainst(units)
    val chapters = units.flatMap { it.chapters }
    val questions = chapters.flatMap { it.a5Questions }
    val startRoute = remember { Routes.restoredDestination(state) }
    val navController = rememberNavController()

    fun selectedQuestionState(question: A5Question, revealed: Boolean = false): NativeLearningState {
        val chapter = chapters.first { it.id == question.chapterId }
        return state.copy(
            destination = StudyDestination.PRACTICE,
            unitNumber = chapter.unitNumber,
            chapterId = chapter.id,
            questionId = question.id,
            answerRevealed = revealed,
        )
    }

    fun backOrRoute(fallback: String) {
        if (!navController.popBackStack()) navController.navigate(fallback) {
            launchSingleTop = true
        }
    }

    NavHost(
        navController = navController,
        startDestination = startRoute,
        modifier = Modifier.semantics { testTagsAsResourceId = true },
    ) {
        composable(Routes.HOME) {
            HomeScreen(
                units = units,
                language = language,
                onLanguageChange = viewModel::setLanguage,
                onUnit = { number ->
                    viewModel.persistLearning(state.copy(
                        destination = StudyDestination.UNIT,
                        unitNumber = number, chapterId = "", questionId = "",
                        answerRevealed = false,
                    )) { navController.navigate(Routes.unit(number)) }
                },
                onParamecium = {
                    viewModel.persistLearning(state.copy(
                        destination = StudyDestination.PARAMECIUM_LAB, unitNumber = 1,
                        chapterId = "u1-paramecium", questionId = "",
                        answerRevealed = false,
                    )) { navController.navigate(Routes.PARAMECIUM) }
                },
                onAssessment = {
                    val selected = questions.firstOrNull { it.id == state.questionId }
                        ?: questions.first()
                    viewModel.persistLearning(
                        selectedQuestionState(selected,
                            state.answerRevealed && state.questionId == selected.id)
                    ) { navController.navigate(Routes.ASSESSMENT) }
                },
            )
        }
        composable(
            route = Routes.UNIT,
            arguments = listOf(navArgument("unitNumber") { type = NavType.IntType }),
        ) { entry ->
            val number = entry.arguments?.getInt("unitNumber")
            val unit = units.singleOrNull { it.number == number }
            if (unit == null) {
                ErrorScreen("Invalid unit", language)
            } else {
                UnitScreen(
                    unit = unit,
                    language = language,
                    onParamecium = {
                        viewModel.persistLearning(state.copy(
                            destination = StudyDestination.PARAMECIUM_LAB,
                            unitNumber = 1, chapterId = "u1-paramecium",
                            questionId = "", answerRevealed = false,
                        )) { navController.navigate(Routes.PARAMECIUM) }
                    },
                    onBack = {
                        viewModel.persistLearning(state.copy(
                            destination = StudyDestination.HOME, chapterId = "",
                            questionId = "", answerRevealed = false,
                        )) { backOrRoute(Routes.HOME) }
                    },
                    onChapter = { chapterId ->
                        viewModel.persistLearning(state.copy(
                            destination = StudyDestination.CHAPTER,
                            unitNumber = unit.number, chapterId = chapterId,
                            questionId = "", answerRevealed = false,
                        )) { navController.navigate(Routes.chapter(chapterId)) }
                    },
                )
            }
        }
        composable(
            route = Routes.CHAPTER,
            arguments = listOf(navArgument("chapterId") { type = NavType.StringType }),
        ) { entry ->
            val id = entry.arguments?.getString("chapterId")
            val chapter = chapters.singleOrNull { it.id == id }
            if (chapter == null) {
                ErrorScreen("Invalid chapter", language)
            } else {
                ChapterScreen(
                    chapter = chapter,
                    language = language,
                    onBack = {
                        viewModel.persistLearning(state.copy(
                            destination = StudyDestination.UNIT,
                            unitNumber = chapter.unitNumber,
                            chapterId = "", questionId = "", answerRevealed = false,
                        )) { backOrRoute(Routes.unit(chapter.unitNumber)) }
                    },
                    onOpenFullLesson = {
                        viewModel.persistLearning(state.copy(
                            destination = StudyDestination.PARAMECIUM_LAB,
                            unitNumber = 1, chapterId = "u1-paramecium",
                            questionId = "", answerRevealed = false,
                        )) { navController.navigate(Routes.PARAMECIUM) }
                    },
                    revealedQuestionId = if (state.answerRevealed) state.questionId else "",
                    onToggle = { question ->
                        val revealed = if (question.id == state.questionId)
                            !state.answerRevealed else true
                        viewModel.persistLearning(state.copy(
                            destination = StudyDestination.CHAPTER,
                            unitNumber = chapter.unitNumber, chapterId = chapter.id,
                            questionId = question.id, answerRevealed = revealed,
                        ))
                    },
                )
            }
        }
        composable(Routes.PARAMECIUM) {
            ParameciumTeachingLab(
                language = language,
                onBack = {
                    viewModel.persistLearning(state.copy(
                        destination = StudyDestination.HOME,
                        chapterId = "", questionId = "", answerRevealed = false,
                    )) { backOrRoute(Routes.HOME) }
                },
                onPractice = {
                    viewModel.persistLearning(state.copy(
                        destination = StudyDestination.CHAPTER, unitNumber = 1,
                        chapterId = "u1-paramecium", questionId = "",
                        answerRevealed = false,
                    )) { navController.navigate(Routes.chapter("u1-paramecium")) }
                },
            )
        }
        composable(Routes.ASSESSMENT) {
            AssessmentScreen(
                questions = questions,
                currentQuestionId = state.questionId,
                answerRevealed = state.answerRevealed,
                language = language,
                onBack = {
                    viewModel.persistLearning(state.copy(
                        destination = StudyDestination.HOME, chapterId = "",
                        questionId = "", answerRevealed = false,
                    )) { backOrRoute(Routes.HOME) }
                },
                onPrevious = { question ->
                    viewModel.persistLearning(selectedQuestionState(question))
                },
                onNext = { question ->
                    viewModel.persistLearning(selectedQuestionState(question))
                },
                onToggle = { question ->
                    val revealed = if (question.id == state.questionId)
                        !state.answerRevealed else true
                    viewModel.persistLearning(selectedQuestionState(question, revealed))
                },
            )
        }
    }
}

private fun label(language: AppLanguage, en: String, ta: String) =
    if (language == AppLanguage.TAMIL) ta else en

@Composable
private fun LoadingScreen(language: AppLanguage) {
    Column(modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)) {
        CircularProgressIndicator()
        Text(label(language, "Loading verified offline A5 questions…", "சரிபார்க்கப்பட்ட ஐந்து மதிப்பெண் வினாக்கள் ஏற்றப்படுகின்றன…"))
    }
}

@Composable
private fun ErrorScreen(reason: String, language: AppLanguage) {
    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        Text(label(language, "The validated academic corpus could not be loaded.", "சரிபார்க்கப்பட்ட கல்வித் தரவை ஏற்ற இயலவில்லை."))
        Text(reason)
    }
}

@Composable
private fun HomeScreen(
    units: List<AcademicUnit>,
    language: AppLanguage,
    onLanguageChange: (AppLanguage) -> Unit,
    onUnit: (Int) -> Unit,
    onAssessment: () -> Unit,
    onParamecium: () -> Unit,
) {
    Scaffold { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding).testTag("native-home"),
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            item {
                Text("INVERTEBRATA")
                Text(label(language,
                    "Interactive Zoology laboratory · study, anatomy, physiology and audio",
                    "ஊடாடும் விலங்கியல் ஆய்வகம் · பாடம், உடலமைப்பு, உடலியல், ஒலி"))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedButton(onClick = { onLanguageChange(AppLanguage.ENGLISH) },
                        modifier = Modifier.heightIn(min = 48.dp).testTag("language-english")) { Text("English") }
                    OutlinedButton(onClick = { onLanguageChange(AppLanguage.TAMIL) },
                        modifier = Modifier.heightIn(min = 48.dp).testTag("language-tamil")) { Text("தமிழ்") }
                }
                Button(onClick = onParamecium,
                    modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp)
                        .testTag("r1-home-open-paramecium")) {
                    Text(label(language, "Read Paramecium — illustrated native textbook",
                        "பாரமீசியம் — படவிளக்கப் பாடநூலைப் படி"))
                }
            }
            items(units, key = { it.number }) { unit ->
                Button(onClick = { onUnit(unit.number) },
                    modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp).testTag("unit-" + unit.number)) {
                    Text(unit.title.value(language) + " · " + unit.chapters.size.toString() +
                        label(language, " chapters", " அத்தியாயங்கள்"))
                }
            }
            item {
                Button(onClick = onAssessment, modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp).testTag("a5-assessment")) {
                    Text(label(language, "Five-mark practice · 86 questions", "ஐந்து மதிப்பெண் பயிற்சி · 86 வினாக்கள்"))
                }
            }
        }
    }
}

@Composable
private fun UnitScreen(
    unit: AcademicUnit,
    language: AppLanguage,
    onParamecium: () -> Unit,
    onBack: () -> Unit,
    onChapter: (String) -> Unit,
) {
    Scaffold { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding).testTag("native-unit-" + unit.number),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            item {
                OutlinedButton(onClick = onBack, modifier = Modifier.heightIn(min = 48.dp)) { Text(label(language, "Back", "பின்செல்")) }
                Text(unit.title.value(language))
                Text(label(language,
                    "Open a teaching lesson where available. Remaining syllabus chapters are assessment-only previews until complete lessons and original atlas plates are authored.",
                    "கற்பித்தல் பாடம் தயாராக உள்ள இடத்தில் அதைத் திறக்கவும். மற்ற அத்தியாயங்களில் முழுமையான பாடமும் அசல் உடலமைப்புப் படங்களும் உருவாகும்வரை வினாப் பயிற்சி முன்னோட்டம் மட்டுமே உள்ளது."))
                if (unit.number == 1) {
                    Button(onClick = onParamecium,
                        modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp)
                            .testTag("r14-unit-open-textbook")) {
                        Text(label(language,
                            "START READING · Paramecium illustrated textbook",
                            "பாடத்தைத் தொடங்கு · பாரமீசியம் படவிளக்கப் பாடநூல்"))
                    }
                }
            }
            items(unit.chapters, key = { it.id }) { chapter ->
                Button(onClick = { onChapter(chapter.id) },
                    modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp).testTag("chapter-" + chapter.id)) {
                    Column {
                        Text(chapter.id.replace("-", " ").uppercase())
                        Text(if (chapter.id == "u1-paramecium")
                            label(language,
                                "Full textbook: select the Start Reading button above; practice questions here.",
                                "முழுப் பாடத்திற்கு மேலுள்ள பாடத்தைத் தொடங்கு பொத்தானைத் தேர்க; இங்கு வினாப் பயிற்சி.")
                            else label(language,
                                "Assessment-only preview; full illustrated lesson not yet authored.",
                                "வினாப் பயிற்சி முன்னோட்டம்; முழுப் படவிளக்கப் பாடம் இன்னும் தயாரிக்கப்படவில்லை."))
                    }
                }
            }
        }
    }
}

@Composable
private fun ChapterScreen(
    chapter: Chapter,
    language: AppLanguage,
    onBack: () -> Unit,
    onOpenFullLesson: () -> Unit,
    revealedQuestionId: String,
    onToggle: (A5Question) -> Unit,
) {
    val lessonDraft = remember(chapter.id) { NativeLessonDrafts.forChapter(chapter.id) }
    val chapterListState = rememberLazyListState()
    val chapterScope = rememberCoroutineScope()
    Scaffold { padding ->
        LazyColumn(
            state = chapterListState,
            modifier = Modifier.fillMaxSize().padding(padding).testTag("native-chapter-" + chapter.id),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            item {
                OutlinedButton(onClick = onBack, modifier = Modifier.heightIn(min = 48.dp)) {
                    Text(label(language, "Back", "பின்செல்"))
                }
                Text(label(language, "Chapter: ", "அத்தியாயம்: ") + chapter.id)
                if (chapter.id == "u1-paramecium") {
                    Button(onClick = onOpenFullLesson,
                        modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp)
                            .testTag("r14-chapter-open-textbook")) {
                        Text(label(language,
                            "READ THE ILLUSTRATED LESSON FIRST",
                            "முதலில் படவிளக்கப் பாடத்தைப் படி"))
                    }
                }
                Text(label(language, "EXAM PRACTICE — after the teaching lesson",
                    "தேர்வுப் பயிற்சி — கற்பித்தல் பாடத்திற்குப் பின்"))
            }
            items(chapter.a5Questions, key = { it.id }) { question ->
                QuestionCard(
                    question = question,
                    language = language,
                    showAnswer = question.id == revealedQuestionId,
                    onToggle = { onToggle(question) },
                )
            }
            if (lessonDraft != null) {
                item(key = "n22-draft-heading") {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth().testTag("n22-draft-notice")) {
                        Text(label(language, "Teaching lesson — editorial draft",
                            "கற்பித்தல் பாடம் — ஆசிரியர் மதிப்பாய்வு நிலுவை"))
                        Text(label(language,
                            "Science and Tamil reviews are pending. This material is not yet approved as the final syllabus lesson.",
                            "அறிவியல் மற்றும் தமிழ் மதிப்பாய்வுகள் இன்னும் முடியவில்லை. இது இறுதியாக அங்கீகரிக்கப்பட்ட பாடம் அல்ல."))
                        Text(lessonDraft.title.value(language))
                    }
                }
                items(lessonDraft.sections, key = { "n22-" + it.id }) { section ->
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth().testTag("n22-section-" + section.id),
                    ) {
                        Text(section.heading.value(language))
                        section.paragraphs.forEach { paragraph ->
                            Text(paragraph.value(language))
                        }
                    }
                }
                item(key = "n22-references") {
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.fillMaxWidth().testTag("n22-references")) {
                        Text(label(language, "Scientific reading and verification sources:",
                            "அறிவியல் மேலாய்வுக்கான மூலநூல்கள்:"))
                        lessonDraft.sections.flatMap { it.scientificSources }
                            .distinct().forEach { source -> Text(source) }
                    }
                }
                if (chapter.id == "u1-paramecium") {
                    item(key = "n23b-external-canvas") {
                        ParameciumExternalCanvas(language,
                            onJumpToPlate = {
                                chapterScope.launch {
                                    chapterListState.scrollToItem(
                                        3 + chapter.a5Questions.size + lessonDraft.sections.size
                                    )
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun QuestionCard(
    question: A5Question,
    language: AppLanguage,
    showAnswer: Boolean,
    onToggle: () -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth().testTag("a5-card-" + question.id)) {
        Text(question.id)
        Text(question.question.value(language),
            modifier = Modifier.testTag("a5-question-" + question.id))
        OutlinedButton(onClick = onToggle,
            modifier = Modifier.heightIn(min = 48.dp).testTag("a5-toggle-" + question.id)) {
            Text(if (showAnswer) label(language, "Hide answer", "விடையை மறை")
                else label(language, "Show five answer points", "ஐந்து விடைக் குறிப்புகளைக் காட்டு"))
        }
        if (showAnswer) {
            for ((index, point) in question.answerPoints.withIndex()) {
                Text((index + 1).toString() + ". " + point.value(language),
                    modifier = Modifier.testTag("a5-point-" + question.id + "-" + (index + 1)))
            }
        }
    }
}

@Composable
private fun AssessmentScreen(
    questions: List<A5Question>,
    currentQuestionId: String,
    answerRevealed: Boolean,
    language: AppLanguage,
    onBack: () -> Unit,
    onPrevious: (A5Question) -> Unit,
    onNext: (A5Question) -> Unit,
    onToggle: (A5Question) -> Unit,
) {
    val currentIndex = questions.indexOfFirst { it.id == currentQuestionId }.coerceAtLeast(0)
    val assessmentScroll = rememberScrollState()
    // Never leave the next question outside the visible/merged accessibility tree.
    // Answer reveal is the same question ID and must not trigger a scroll reset.
    LaunchedEffect(currentQuestionId) { assessmentScroll.scrollTo(0) }
    Scaffold { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding)
                .verticalScroll(assessmentScroll)
                .padding(20.dp).testTag("native-assessment"),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            OutlinedButton(onClick = onBack, modifier = Modifier.heightIn(min = 48.dp)) {
                Text(label(language, "Back", "பின்செல்"))
            }
            Text(label(language, "Five-mark self-study", "ஐந்து மதிப்பெண் சுயபயிற்சி"))
            Text((currentIndex + 1).toString() + " / " + questions.size)
            if (questions.isNotEmpty()) {
                val question = questions[currentIndex]
                Text(label(language, "Chapter: ", "அத்தியாயம்: ") + question.chapterId)
                QuestionCard(
                    question = question, language = language,
                    showAnswer = answerRevealed, onToggle = { onToggle(question) },
                )
                Spacer(Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedButton(
                        onClick = { onPrevious(questions[currentIndex - 1]) },
                        enabled = currentIndex > 0,
                        modifier = Modifier.heightIn(min = 48.dp).testTag("assessment-prev"),
                    ) {
                        Text(label(language, "Previous", "முந்தையது"))
                    }
                    Button(
                        onClick = { onNext(questions[currentIndex + 1]) },
                        enabled = currentIndex < questions.lastIndex,
                        modifier = Modifier.heightIn(min = 48.dp).testTag("assessment-next"),
                    ) {
                        Text(label(language, "Next", "அடுத்தது"))
                    }
                }
            }
        }
    }
}
