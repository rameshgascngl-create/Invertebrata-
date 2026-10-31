package com.gasczoology.invertebratelab.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.gasczoology.invertebratelab.AcademicLoadState
import com.gasczoology.invertebratelab.MainViewModel
import com.gasczoology.invertebratelab.PersistedLearningLoadState
import com.gasczoology.invertebratelab.R
import com.gasczoology.invertebratelab.data.BookChapters
import com.gasczoology.invertebratelab.data.Extensions
import com.gasczoology.invertebratelab.data.A5Question
import com.gasczoology.invertebratelab.data.AcademicUnit
import com.gasczoology.invertebratelab.data.AppLanguage
import com.gasczoology.invertebratelab.data.Chapter
import com.gasczoology.invertebratelab.data.NativeLearningState
import com.gasczoology.invertebratelab.data.NativeLessonDrafts
import com.gasczoology.invertebratelab.data.NativeCurriculumManifest
import com.gasczoology.invertebratelab.data.UnitIVPenaeusTeaching
import com.gasczoology.invertebratelab.data.UnitIVNereisLessons
import com.gasczoology.invertebratelab.data.UnitIVSupplementaryLessons
import com.gasczoology.invertebratelab.data.FiveUnitRestorationLessons
import com.gasczoology.invertebratelab.data.StudyDestination
import kotlinx.coroutines.launch

private object Routes {
    const val HOME = "home"
    const val UNIT = "unit/{unitNumber}"
    const val CHAPTER = "chapter/{chapterId}"
    const val ASSESSMENT = "assessment"
    const val PARAMECIUM = "paramecium-interactive-lab"
    const val EARTHWORM = "u4-earthworm-native-textbook"

    fun unit(number: Int) = "unit/" + number
    fun chapter(id: String) = "chapter/" + id

    fun restoredDestination(state: NativeLearningState): String = when (state.destination) {
        StudyDestination.HOME -> HOME
        StudyDestination.UNIT -> unit(state.unitNumber)
        StudyDestination.CHAPTER -> chapter(state.chapterId)
        StudyDestination.PRACTICE -> ASSESSMENT
        StudyDestination.PARAMECIUM_LAB -> PARAMECIUM
        StudyDestination.EARTHWORM_TEXTBOOK -> EARTHWORM
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
                    onParameciumAtlas = {
                        viewModel.persistLearning(state.copy(
                            destination = StudyDestination.PARAMECIUM_LAB,
                            unitNumber = 1, chapterId = "u1-paramecium",
                            questionId = "", answerRevealed = false,
                            laboratoryTab = "anatomy",
                        )) { navController.navigate(Routes.PARAMECIUM) }
                    },
                    onEarthworm = {
                        viewModel.persistLearning(state.copy(
                            destination = StudyDestination.EARTHWORM_TEXTBOOK,
                            unitNumber = 4,
                            chapterId = NativeCurriculumManifest.EARTHWORM_LESSON_ID,
                            questionId = "", answerRevealed = false,
                            textbookSectionId = "identity",
                        )) { navController.navigate(Routes.EARTHWORM) }
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
                    onLanguageChange = viewModel::setLanguage,
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
                    onOpenAnatomy = {
                        viewModel.persistLearning(state.copy(
                            destination = StudyDestination.PARAMECIUM_LAB,
                            unitNumber = 1, chapterId = "u1-paramecium",
                            questionId = "", answerRevealed = false,
                            laboratoryTab = "anatomy",
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
                learningState = state,
                onLearningChanged = { viewModel.persistLearning(it) },
                onBack = { position ->
                    viewModel.persistLearning(position.copy(
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
        composable(Routes.EARTHWORM) {
            UnitIVEarthwormReader(
                language = language,
                onBack = {
                    viewModel.persistLearning(state.copy(
                        destination = StudyDestination.UNIT, unitNumber = 4,
                        chapterId = "", questionId = "", answerRevealed = false,
                    )) { backOrRoute(Routes.unit(4)) }
                }
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
        Text(label(language, "Loading your lessons…", "உங்கள் பாடங்கள் ஏற்றப்படுகின்றன…"))
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
    // Hidden attribution: seven quick taps on the title open a credit dialog.
    // pointerInput (not clickable) adds no accessibility semantics, so TalkBack
    // users never hear a phantom button.
    var showCredit by remember { mutableStateOf(false) }
    var titleTaps by remember { mutableIntStateOf(0) }
    val lastTitleTap = remember { longArrayOf(0L) }
    if (showCredit) {
        AlertDialog(
            onDismissRequest = { showCredit = false },
            confirmButton = {
                TextButton(onClick = { showCredit = false },
                    modifier = Modifier.heightIn(min = 48.dp).testTag("credit-close")) {
                    Text(label(language, "Close", "மூடு"))
                }
            },
            title = { Text("INVERTEBRATA") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.testTag("credit-dialog")) {
                    Text(stringResource(R.string.credit_line))
                    Text(label(language,
                        "Government Arts and Science College, Nagercoil",
                        "அரசு கலை மற்றும் அறிவியல் கல்லூரி, நாகர்கோவில்"),
                        style = MaterialTheme.typography.bodySmall)
                }
            },
        )
    }
    Scaffold { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding).testTag("native-home"),
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            item {
                Text("INVERTEBRATA", style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.pointerInput(Unit) {
                        detectTapGestures(onTap = {
                            val now = System.currentTimeMillis()
                            titleTaps = if (now - lastTitleTap[0] > 1500L) 1 else titleTaps + 1
                            lastTitleTap[0] = now
                            if (titleTaps >= 7) { titleTaps = 0; showCredit = true }
                        })
                    })
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
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        Text(
                            when (unit.number) {
                                1 -> "🦠"
                                2 -> "🪸"
                                3 -> "🪱"
                                4 -> "🦐"
                                5 -> "🐚"
                                6 -> "🦋"
                                else -> "🦑"
                            },
                            style = MaterialTheme.typography.headlineMedium,
                        )
                        Column {
                            Text(unit.title.value(language),
                                style = MaterialTheme.typography.titleMedium)
                            Text(unit.chapters.size.toString() +
                                label(language, " chapters", " அத்தியாயங்கள்"),
                                style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
            item {
                Button(onClick = onAssessment, modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp).testTag("a5-assessment")) {
                    val questionCount = units.sumOf { u -> u.chapters.sumOf { it.a5Questions.size } }
                    Text(label(language, "Five-mark practice · $questionCount questions", "ஐந்து மதிப்பெண் பயிற்சி · $questionCount வினாக்கள்"))
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
    onParameciumAtlas: () -> Unit,
    onEarthworm: () -> Unit,
    onBack: () -> Unit,
    onChapter: (String) -> Unit,
) {
    var showProtozoaReferences by rememberSaveable { mutableStateOf(false) }
    val unitScrollState = rememberLazyListState()
    // At 200% font the overview may fill the entire viewport. Opening the
    // reference gallery must reveal its first heading without further scrolling.
    // Its location is the second LazyColumn item; existing A5 chapters follow it.
    LaunchedEffect(unit.number, showProtozoaReferences) {
        if (unit.number == 1 && showProtozoaReferences) {
            unitScrollState.scrollToItem(1)
        }
    }
    Scaffold { padding ->
        LazyColumn(
            state = unitScrollState,
            modifier = Modifier.fillMaxSize().padding(padding).testTag("native-unit-" + unit.number),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            item {
                OutlinedButton(onClick = onBack, modifier = Modifier.heightIn(min = 48.dp)) { Text(label(language, "Back", "பின்செல்")) }
                Text(unit.title.value(language))
                // Show the chapter index directly; no app-level release-status paragraph.
                if (unit.number == 4) {
                    Button(onClick = onEarthworm,
                        modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp)
                            .testTag("u4-open-earthworm-textbook")) {
                        Text(label(language,
                            "START READING · Earthworm (Metaphire posthuma) native textbook",
                            "பாடத்தைத் தொடங்கு · மண்புழு (Metaphire posthuma) விரிவான பாடநூல்"))
                    }
                }
                if (unit.number == 1) {
                    OutlinedButton(onClick = onParameciumAtlas,
                        modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp)
                            .testTag("u1-open-diagram-atlas")) {
                        Text(label(language,
                            "VIEW ANATOMICAL SLOTS · Paramecium replacement plates pending",
                            "பாரமீசியம் உடற்கூறு படப் பகுதிகள் · புதிய படங்கள் நிலுவையில் உள்ளன"))
                    }
                    Text(label(language,
                        "Compare the available Entamoeba and Plasmodium transmission figures with their life-cycle lessons.",
                        "எண்டமீபா, பிளாஸ்மோடியம் தொற்றுச் சுழற்சிப் படங்களை அவற்றின் வாழ்க்கைச் சுழற்சிப் பாடங்களுடன் ஒப்பிட்டுப் படிக்கவும்."),
                        modifier = Modifier.testTag("u1-missing-protozoa-plates"))
                    OutlinedButton(
                        onClick = { showProtozoaReferences = !showProtozoaReferences },
                        modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp)
                            .testTag("u1-protozoa-reference-toggle"),
                    ) {
                        Text(label(language,
                            if (showProtozoaReferences) "HIDE ENTAMOEBA & PLASMODIUM REFERENCES" else "VIEW ENTAMOEBA & PLASMODIUM SOURCE REFERENCES",
                            if (showProtozoaReferences) "இரு ஆதாரப் படங்களை மறை" else "இரு ஆதாரப் படங்களைப் பார்"))
                    }
                    Button(onClick = onParamecium,
                        modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp)
                            .testTag("r14-unit-open-textbook")) {
                        Text(label(language,
                            "START READING · Paramecium illustrated textbook",
                            "பாடத்தைத் தொடங்கு · பாரமீசியம் படவிளக்கப் பாடநூல்"))
                    }
                }
            }
            if (unit.number == 1 && showProtozoaReferences) {
                item(key = "u1-protozoa-reference-panel") {
                    UnitIProtozoaReferenceGallery(language)
                }
            }
            items(unit.chapters, key = { it.id }) { chapter ->
                Button(onClick = { onChapter(chapter.id) },
                    modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp).testTag("chapter-" + chapter.id)) {
                    Column {
                        Text(chapter.title.value(language),
                            style = MaterialTheme.typography.titleMedium)
                        Text(
                            if (BookChapters.forLesson(chapter.id) != null) label(language,
                                "READ TEXTBOOK CHAPTER · explanations, key terms and review questions.",
                                "பாடநூல் அத்தியாயத்தைப் படி · விளக்கங்கள், கலைச்சொற்கள், மீளாய்வு வினாக்கள்.")
                            else label(language,
                                "OPEN BILINGUAL LESSON · study content and examination practice.",
                                "இருமொழிப் பாடத்தைத் திற · பாடவிளக்கமும் தேர்வுப் பயிற்சியும்."),
                            style = MaterialTheme.typography.bodySmall,
                        )
                    }
                }
            }
        }
    }
}

/** The persistent app language can also be changed directly from any chapter. */
@Composable
private fun ChapterScreen(
    chapter: Chapter,
    language: AppLanguage,
    onLanguageChange: (AppLanguage) -> Unit,
    onBack: () -> Unit,
    onOpenFullLesson: () -> Unit,
    onOpenAnatomy: () -> Unit,
    revealedQuestionId: String,
    onToggle: (A5Question) -> Unit,
) {
    // Consume the system status-bar inset before placing the chapter-local
    // language switch. Descendant readers inherit the consumed inset, so their
    // own statusBarsPadding() does not introduce a second full-height gap.
    Column(Modifier.fillMaxSize().statusBarsPadding()) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 2.dp)
                .testTag("chapter-language-toolbar"),
            horizontalArrangement = Arrangement.End,
        ) {
            TextButton(onClick = { onLanguageChange(AppLanguage.ENGLISH) },
                enabled = language != AppLanguage.ENGLISH,
                modifier = Modifier.heightIn(min = 48.dp).testTag("chapter-language-english")) {
                Text("English")
            }
            TextButton(onClick = { onLanguageChange(AppLanguage.TAMIL) },
                enabled = language != AppLanguage.TAMIL,
                modifier = Modifier.heightIn(min = 48.dp).testTag("chapter-language-tamil")) {
                Text("தமிழ்")
            }
        }
        Box(Modifier.weight(1f)) {
            ChapterBody(chapter, language, onBack, onOpenFullLesson, onOpenAnatomy,
                revealedQuestionId, onToggle)
        }
    }
}

@Composable
private fun ChapterBody(
    chapter: Chapter,
    language: AppLanguage,
    onBack: () -> Unit,
    onOpenFullLesson: () -> Unit,
    onOpenAnatomy: () -> Unit,
    revealedQuestionId: String,
    onToggle: (A5Question) -> Unit,
) {
    if (chapter.unitNumber == 2) {
        UnitIITeachingChapter(chapter, language, onBack, revealedQuestionId, onToggle)
        return
    }
    if (chapter.unitNumber == 3) {
        UnitIIITeachingChapter(chapter, language, onBack, revealedQuestionId, onToggle)
        return
    }
    BookChapters.forLesson(chapter.id)?.let { book ->
        UnitBookTeachingChapter(chapter, book, language, onBack, revealedQuestionId, onToggle)
        return
    }
    val lessonDraft = remember(chapter.id) { NativeLessonDrafts.forChapter(chapter.id) }
    val restoredLesson = remember(chapter.id) { FiveUnitRestorationLessons.byId[chapter.id] }
    // U1.9a: keep life-cycle stages ABOVE the lazy list. At 200% font a
    // single teaching card is taller than the screen; disposing/recomposing
    // the lazy item must never silently return the learner to stage one.
    var trypanosomaStage by rememberSaveable(chapter.id) { mutableIntStateOf(0) }
    var leishmaniaStage by rememberSaveable(chapter.id) { mutableIntStateOf(0) }
    // Keep Penaeus interactive state above LazyColumn items: the stage must
    // survive disposal at 200% Tamil without silently resetting.
    var penaeusExternalPart by rememberSaveable(chapter.id) { mutableStateOf("carapace") }
    var penaeusAppendage by rememberSaveable(chapter.id) { mutableIntStateOf(0) }
    var penaeusLifeStage by rememberSaveable(chapter.id) { mutableIntStateOf(0) }
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
                // Internal database IDs are not student-facing chapter titles.
                Text(chapter.title.value(language),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.testTag("r2-human-readable-chapter-title"))
                if (chapter.id == "u1-paramecium") {
                    Button(onClick = onOpenFullLesson,
                        modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp)
                            .testTag("r14-chapter-open-textbook")) {
                        Text(label(language,
                            "READ THE ILLUSTRATED LESSON FIRST",
                            "முதலில் படவிளக்கப் பாடத்தைப் படி"))
                    }
                    OutlinedButton(
                        onClick = onOpenAnatomy,
                        modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp)
                            .testTag("u1-paramecium-open-diagrams"),
                    ) {
                        Text(label(language, "VIEW PARAMECIUM ANATOMICAL DIAGRAMS",
                            "பாரமீசியம் உடலமைப்புப் படங்களைப் பார்"))
                    }
                }
                Text(
                    if (chapter.id == "u4-penaeus")
                        label(language,
                            "READ THE NATIVE TYPE STUDY FIRST · the original A5 questions follow",
                            "முதலில் விரிவான இன ஆய்வைப் படிக்கவும் · அசல் A5 வினாக்கள் பின்னர்")
                    else label(language, "Study the lesson, then practise the questions.",
                        "பாடத்தைப் படித்த பின் வினாக்களைப் பயிற்சி செய்க.")
                )
            }
            if (chapter.id in setOf("u1-paramecium", "u4-penaeus", "u5-pila", "u5-asterias")) {
                item(key = "r2-type-study-gallery") {
                    val study = NativeCurriculumManifest.typeStudies.single { it.lessonId == chapter.id }
                    RequiredTypeStudyPlates(study, language)
                }
            }
            if (chapter.id == "u1-proto-parasites") {
                item(key = "u15-entamoeba-native-textbook") {
                    UnitIEntamoebaMorphology(language)
                }
            }
            if (chapter.id == "u1-proto-parasites") {
                item(key = "u17-flagellate-native-heading") {
                    UnitIFlagellateTypeStudiesHeader(language)
                }
                item(key = "u17-trypanosoma-figure") {
                    UnitIFlagellateStudyIntroduction("trypanosoma", language)
                }
                item(key = "u17-trypanosoma-morphology-section") {
                    UnitIFlagellateLessonSection("trypanosoma", "morphology", language)
                }
                item(key = "u17-trypanosoma-mammal-section") {
                    UnitIFlagellateLessonSection("trypanosoma", "mammal", language)
                }
                item(key = "u17-trypanosoma-vector-section") {
                    UnitIFlagellateLessonSection("trypanosoma", "vector", language)
                }
                item(key = "u17-trypanosoma-contrast-section") {
                    UnitIFlagellateLessonSection("trypanosoma", "contrast", language)
                }
                item(key = "u18-trypanosoma-life-cycle") {
                    UnitIFlagellateLifeCycleSimulator("trypanosoma", language, trypanosomaStage) { trypanosomaStage = it }
                }
                item(key = "u17-leishmania-figure") {
                    UnitIFlagellateStudyIntroduction("leishmania", language)
                }
                item(key = "u17-leishmania-morphology-section") {
                    UnitIFlagellateLessonSection("leishmania", "morphology", language)
                }
                item(key = "u17-leishmania-mammal-section") {
                    UnitIFlagellateLessonSection("leishmania", "mammal", language)
                }
                item(key = "u17-leishmania-vector-section") {
                    UnitIFlagellateLessonSection("leishmania", "vector", language)
                }
                item(key = "u17-leishmania-limits-section") {
                    UnitIFlagellateLessonSection("leishmania", "limits", language)
                }
                item(key = "u18-leishmania-life-cycle") {
                    UnitIFlagellateLifeCycleSimulator("leishmania", language, leishmaniaStage) { leishmaniaStage = it }
                }
                // An unverified Canvas anatomy atlas is prohibited in the static pencil-image build.
                // Organism-specific microscopy and anatomy remain available as narrative content
                // until authentic raster masters are present.
            }
            // Each source image belongs to a single relevant teaching route.
            // Do not duplicate old flagellate art below their organism chapters.
            if (chapter.id == "u1-host-parasite") {
                item(key = "u1-independent-protozoa-reference-atlas") {
                    UnitIProtozoaReferenceGallery(language)
                }
            }
            if (chapter.id == "u4-nereis") {
                item(key = "u44-nereis-introduction") {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth().testTag("u44-nereis-introduction")) {
                        Text(label(language,
                            "Nereis · complete type-study reader and eight static drawings",
                            "நெரீஸ் · விரிவான இன ஆய்வும் எட்டு நிலைப் படங்களும்"),
                            style = MaterialTheme.typography.headlineSmall)
                        Text(label(language,
                            "Original black-and-white teaching sketches; NOT authenticated pencil scans. No organ hotspots or touch interactions. Bilingual terminology requires comparison with the official college practical manual.",
                            "கருப்பு–வெள்ளை கற்பித்தல் வரைபடங்கள்; உண்மையான பென்சில் ஸ்கேன் அல்ல. உறுப்புத் தொடுதல் இல்லை. தமிழ் உடற்கூறு சொற்கள் கல்லூரிச் செய்முறை கையேட்டுடன் ஒப்பிடப்பட வேண்டும்."),
                            modifier = Modifier.testTag("u44-nereis-editorial-disclosure"))
                    }
                }
                items((0..7).toList(), key = { "u44-nereis-plate-$it" }) { plateIndex ->
                    UnitIVNereisStaticPlate(plateIndex,language)
                }
                item(key = "u44-nereis-lessons-heading") {
                    Text(label(language,
                        "NEREIS BIOLOGY · 14 EXPLANATORY SECTIONS",
                        "நெரீஸ் உயிரியல் · 14 விளக்கப் பாடப் பகுதிகள்"),
                        style=MaterialTheme.typography.titleLarge,
                        modifier=Modifier.testTag("u44-nereis-lessons-heading"))
                }
                items(UnitIVNereisLessons.sections,key = { "u44-nereis-"+it.id }) { section ->
                    Column(Modifier.fillMaxWidth().testTag("u44-nereis-section-"+section.id),
                        verticalArrangement=Arrangement.spacedBy(9.dp)) {
                        Text(section.title.value(language),style=MaterialTheme.typography.titleLarge)
                        section.paragraphs.forEach { paragraph ->
                            Text(paragraph.value(language),style=MaterialTheme.typography.bodyLarge)
                        }
                    }
                }
                item(key="u44-nereis-sources") {
                    Text(label(language,"Scientific references: ","அறிவியல் ஆதாரங்கள்: ")+
                        UnitIVNereisLessons.sources.joinToString(" · "),
                        style=MaterialTheme.typography.bodySmall)
                }
            }
            if (chapter.id == "u4-penaeus") {
                item(key = "u4-penaeus-study-intro") {
                    UnitIVPenaeusIntroduction(language)
                }
                // Vector anatomy disabled: student-facing figures must be imported pencil rasters.
                items(UnitIVPenaeusTeaching.sections,
                    key = { "u4-penaeus-"+it.id }) { section ->
                    UnitIVPenaeusLessonSection(section,language)
                }
                item(key = "u4-penaeus-appendages") {
                    UnitIVPenaeusAppendageExplorer(
                        language,penaeusAppendage) { penaeusAppendage=it }
                }
                item(key = "u4-penaeus-larvae") {
                    UnitIVPenaeusLifeCycleExplorer(
                        language,penaeusLifeStage) { penaeusLifeStage=it }
                }
                item(key = "u4-penaeus-image-approval-hold") {
                    UnitIVPenaeusAtlasHold(language)
                }
                item(key = "u4-penaeus-a5-divider") {
                    Text(label(language,
                        "FIVE-MARK PRACTICE",
                        "ஐந்து மதிப்பெண் வினாப் பயிற்சி"),
                        modifier=Modifier.testTag("u4-penaeus-a5-original-heading"),
                        style=MaterialTheme.typography.titleLarge)
                }
            }
            if (UnitIVSupplementaryLessons.byId.containsKey(chapter.id)) {
                val supplementary = UnitIVSupplementaryLessons.byId.getValue(chapter.id)
                item(key = "u56-lesson-heading") {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                            .testTag("u56-lesson-heading"),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        Text(
                            supplementary.heading.value(language),
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier.testTag("u56-lesson-title"),
                        )
                        Text(
                            label(language,
                                "Read each topic, then check your understanding.",
                                "ஒவ்வொரு பாடப்பகுதியையும் படித்து உங்கள் புரிதலைச் சரிபார்க்கவும்."),
                        )
                    }
                }
                items(supplementary.sections, key = { "u56-" + it.id }) { section ->
                    Column(
                        modifier = Modifier.fillMaxWidth()
                            .testTag("u56-section-" + section.id),
                        verticalArrangement = Arrangement.spacedBy(9.dp),
                    ) {
                        Text(section.title.value(language),style = MaterialTheme.typography.titleLarge)
                        section.paragraphs.forEach { p ->
                            Text(p.value(language),style = MaterialTheme.typography.bodyLarge)
                        }
                    }
                }
                item(key = "u56-science-reference-heading") {
                    Text(
                        label(language, "Further scientific reading", "மேலும் அறிவியல் வாசிப்பு"),
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.testTag("u56-science-reference-heading"),
                    )
                    supplementary.reading.forEach { url ->
                        Text(url,style = MaterialTheme.typography.bodySmall)
                    }
                }
                item(key = "u56-a5-divider") {
                    Text(
                        label(language,
                            "FIVE-MARK PRACTICE",
                            "ஐந்து மதிப்பெண் வினாப் பயிற்சி"),
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.testTag("u56-original-a5-heading"),
                    )
                }
            }
            if (restoredLesson != null) {
                item(key = "restoration-lesson-heading") {
                    Column(
                        modifier = Modifier.fillMaxWidth().testTag("restoration-lesson-heading"),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(restoredLesson.heading.value(language),
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier.testTag("restoration-lesson-title"))
                    }
                }
                items(restoredLesson.sections, key = { "restored-section-" + it.id }) { section ->
                    Column(Modifier.fillMaxWidth().testTag("restored-section-" + section.id),
                        verticalArrangement = Arrangement.spacedBy(9.dp)) {
                        Text(section.title.value(language),
                            style = MaterialTheme.typography.titleLarge)
                        section.paragraphs.forEach { paragraph ->
                            Text(paragraph.value(language), style = MaterialTheme.typography.bodyLarge)
                        }
                    }
                }
            }
            if (lessonDraft != null) {
                item(key = "n22-draft-heading") {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth().testTag("n22-draft-notice")) {
                        Text(lessonDraft.title.value(language),
                            style = MaterialTheme.typography.headlineSmall)
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

            }
            item(key = "restoration-a5-divider") {
                Text(label(language,"EXAM PRACTICE · Five-mark questions",
                    "தேர்வுப் பயிற்சி · ஐந்து மதிப்பெண் வினாக்கள்"),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.testTag("restoration-a5-heading"))
            }
            items(chapter.a5Questions, key = { it.id }) { question ->
                QuestionCard(
                    question = question,
                    language = language,
                    showAnswer = question.id == revealedQuestionId,
                    onToggle = { onToggle(question) },
                )
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
