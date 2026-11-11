package com.gasczoology.invertebratelab.ui

import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Alignment
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ButtonDefaults
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.background
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.itemsIndexed
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

/** Units 1-5 are the validated syllabus (86 five-mark questions). */
private const val VALIDATED_UNIT_COUNT = 5

private object Routes {
    const val HOME = "home"
    const val UNIT = "unit/{unitNumber}"
    const val CHAPTER = "chapter/{chapterId}"
    const val ASSESSMENT = "assessment"
    const val PARAMECIUM = "paramecium-interactive-lab"
    const val EARTHWORM = "u4-earthworm-native-textbook"
    const val PRIVACY = "privacy-policy"

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
    val readingPositions by viewModel.readingPositions.collectAsState()

    // Never construct navigation until the disk record and academic corpus load.
    // Remember only the initial route; later writes cannot reset the NavHost.
    val corpus = academicState as? AcademicLoadState.Ready
    val stored = persisted as? PersistedLearningLoadState.Ready
    if (corpus == null || stored == null || readingPositions == null) {
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
    // Student practice uses ONLY the academically validated A5 questions (Units 1-5).
    // Extension questions (Units 6-7) are DRAFT_UNVERIFIED: they stay inside their own
    // lessons but are excluded from the practice list and its count.
    val questions = chapters.filter { it.unitNumber <= VALIDATED_UNIT_COUNT }
        .flatMap { it.a5Questions }
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

    val bookmarks by viewModel.bookmarks.collectAsState()
    androidx.compose.runtime.CompositionLocalProvider(
        LocalReadingPositions provides ReadingPositionAccess(readingPositions.orEmpty(), viewModel::saveReadingPosition),
        LocalBookmarks provides BookmarkAccess(bookmarks, viewModel::toggleBookmark),
    ) {
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
                onPrivacy = { navController.navigate(Routes.PRIVACY) },
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
                        val returnToLab = navController.previousBackStackEntry?.destination?.route == Routes.PARAMECIUM
                        viewModel.persistLearning(state.copy(
                            destination = if (returnToLab) StudyDestination.PARAMECIUM_LAB else StudyDestination.UNIT,
                            unitNumber = chapter.unitNumber,
                            chapterId = if (returnToLab) "u1-paramecium" else "", questionId = "", answerRevealed = false,
                        )) { backOrRoute(if (returnToLab) Routes.PARAMECIUM else Routes.unit(chapter.unitNumber)) }
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
                onPractice = { position ->
                    viewModel.persistLearning(position.copy(
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
        composable(Routes.PRIVACY) {
            PrivacyPolicyScreen(
                language = language,
                onBack = { backOrRoute(Routes.HOME) },
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
    onPrivacy: () -> Unit,
) {
    // Keep the existing seven-tap credit shortcut without exposing a phantom
    // TalkBack action. All other actions retain their navigation and test tags.
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
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.testTag("credit-dialog"),
                ) {
                    Text(stringResource(R.string.credit_line))
                    Text(
                        label(language, "Government Arts and Science College, Nagercoil",
                            "அரசு கலை மற்றும் அறிவியல் கல்லூரி, நாகர்கோவில்"),
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
            },
        )
    }

    // The home page alone gets a warmer academic palette. Lesson readers and
    // their approved figure assets remain unchanged.
    val ink = Color(0xFF173D38)
    val mutedInk = Color(0xFF506B64)
    val softBorder = Color(0xFFD8E5DF)
    val unitTints = listOf(
        Color(0xFFEAF5F1), Color(0xFFFFF3E6), Color(0xFFF1EEFA),
        Color(0xFFEAF2FB), Color(0xFFFFF0ED), Color(0xFFF2F6E7),
        Color(0xFFECF4F6),
    )

    Scaffold { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding)
                .background(Color(0xFFF8FAF7)).testTag("native-home"),
            contentPadding = PaddingValues(horizontal = 18.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                ) {
                Card(
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F3ED)),
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        Text(
                            label(language, "ZOOLOGY · DIGITAL TEXTBOOK",
                                "விலங்கியல் · மின்னணுப் பாடநூல்"),
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.5.sp,
                            ),
                            color = Color(0xFF306B5A),
                        )
                        Text(
                            "INVERTEBRATA",
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = 0.3.sp,
                            ),
                            color = ink,
                            modifier = Modifier.pointerInput(Unit) {
                                detectTapGestures(onTap = {
                                    val now = System.currentTimeMillis()
                                    titleTaps = if (now - lastTitleTap[0] > 1500L) 1 else titleTaps + 1
                                    lastTitleTap[0] = now
                                    if (titleTaps >= 7) {
                                        titleTaps = 0
                                        showCredit = true
                                    }
                                })
                            },
                        )
                        Text(
                            label(language,
                                "An illustrated guide to invertebrate zoology",
                                "முதுகெலும்பற்ற விலங்கியல் — படவிளக்கப் பாடநூல்"),
                            style = MaterialTheme.typography.bodyMedium,
                            color = mutedInk,
                        )
                        Spacer(Modifier.height(2.dp))
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            if (language == AppLanguage.ENGLISH) {
                                Button(
                                    onClick = { onLanguageChange(AppLanguage.ENGLISH) },
                                    modifier = Modifier.heightIn(min = 48.dp)
                                        .testTag("language-english"),
                                ) { Text("English") }
                            } else {
                                OutlinedButton(
                                    onClick = { onLanguageChange(AppLanguage.ENGLISH) },
                                    modifier = Modifier.heightIn(min = 48.dp)
                                        .testTag("language-english"),
                                ) { Text("English") }
                            }
                            if (language == AppLanguage.TAMIL) {
                                Button(
                                    onClick = { onLanguageChange(AppLanguage.TAMIL) },
                                    modifier = Modifier.heightIn(min = 48.dp)
                                        .testTag("language-tamil"),
                                ) { Text("தமிழ்") }
                            } else {
                                OutlinedButton(
                                    onClick = { onLanguageChange(AppLanguage.TAMIL) },
                                    modifier = Modifier.heightIn(min = 48.dp)
                                        .testTag("language-tamil"),
                                ) { Text("தமிழ்") }
                            }
                        }
                    }
                }
                Text(
                    label(language, "Start exploring", "கற்றலைத் தொடங்குங்கள்"),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                    ),
                    color = ink,
                )
                Spacer(Modifier.height(9.dp))
                Button(
                    onClick = onParamecium,
                    shape = RoundedCornerShape(18.dp),
                    contentPadding = PaddingValues(16.dp),
                    modifier = Modifier.fillMaxWidth().heightIn(min = 64.dp)
                        .testTag("r1-home-open-paramecium"),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        Text("🔬", style = MaterialTheme.typography.headlineSmall)
                        Text(
                            label(language,
                                "Read Paramecium — illustrated native textbook",
                                "பாரமீசியம் — படவிளக்கப் பாடநூலைப் படி"),
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.SemiBold,
                            ),
                            modifier = Modifier.weight(1f),
                        )
                    }
                }
                Text(
                    label(language, "Explore the units", "பாட அலகுகள்"),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                    ),
                    color = ink,
                )
                }
            }
            items(units, key = { it.number }) { unit ->
                val tint = unitTints[(unit.number - 1).coerceIn(0, unitTints.lastIndex)]
                OutlinedButton(
                    onClick = { onUnit(unit.number) },
                    shape = RoundedCornerShape(18.dp),
                    border = BorderStroke(1.dp, softBorder),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = tint,
                        contentColor = ink,
                    ),
                    contentPadding = PaddingValues(horizontal = 15.dp, vertical = 14.dp),
                    modifier = Modifier.fillMaxWidth().heightIn(min = 72.dp)
                        .testTag("unit-" + unit.number),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
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
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(3.dp),
                        ) {
                            Text(
                                label(language, "Unit ${unit.number}", "அலகு ${unit.number}"),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                ),
                                color = mutedInk,
                            )
                            Text(
                                readableUnitTitle(unit, language),
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.SemiBold,
                                ),
                                color = ink,
                            )
                            Text(
                                unit.chapters.size.toString() +
                                    label(language, " chapters", " அத்தியாயங்கள்"),
                                style = MaterialTheme.typography.bodySmall,
                                color = mutedInk,
                            )
                        }
                        Text("›", style = MaterialTheme.typography.headlineSmall, color = mutedInk)
                    }
                }
            }
            item {
                val questionCount = units.filter { it.number <= VALIDATED_UNIT_COUNT }
                    .sumOf { u -> u.chapters.sumOf { it.a5Questions.size } }
                Spacer(Modifier.height(4.dp))
                Button(
                    onClick = onAssessment,
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF785118),
                        contentColor = Color.White,
                    ),
                    contentPadding = PaddingValues(16.dp),
                    modifier = Modifier.fillMaxWidth().heightIn(min = 56.dp)
                        .testTag("a5-assessment"),
                ) {
                    Text(
                        label(language,
                            "Five-mark practice · $questionCount questions",
                            "ஐந்து மதிப்பெண் பயிற்சி · $questionCount வினாக்கள்"),
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.SemiBold,
                        ),
                        textAlign = TextAlign.Center,
                    )
                }
            }
            item {
                OutlinedButton(
                    onClick = onPrivacy,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp)
                        .testTag("open-privacy-policy"),
                ) {
                    Text(
                        label(language, "Privacy policy & developer contact",
                            "தனியுரிமைக் கொள்கை மற்றும் உருவாக்குநர் தொடர்பு"),
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}

/** Student-facing headings are presentation-only; validated A5 source IDs and prose stay unchanged. */
private fun readableUnitTitle(unit: AcademicUnit, language: AppLanguage): String = when (unit.number) {
    1 -> label(language, "Introduction and Protozoa", "அறிமுகம் மற்றும் புரோட்டோசோவா")
    2 -> label(language, "Porifera and Cnidaria", "துளையுடலிகள் மற்றும் நிடேரியா")
    3 -> label(language, "Platyhelminthes and Nematoda", "தட்டைப்புழுக்கள் மற்றும் உருளைப்புழுக்கள்")
    4 -> label(language, "Annelida and Arthropoda", "வளையப்புழுக்கள் மற்றும் கணுக்காலிகள்")
    5 -> label(language, "Mollusca and Echinodermata", "மெல்லுடலிகள் மற்றும் முட்தோலிகள்")
    6 -> label(language, "Arthropods, Insects and Applied Entomology",
        "கணுக்காலிகள், பூச்சிகள் மற்றும் பயன்பாட்டுப் பூச்சியியல்")
    7 -> label(language, "Supplementary Invertebrate Zoology",
        "முதுகெலும்பிலிகள் விலங்கியலின் கூடுதல் தலைப்புகள்")
    else -> unit.title.value(language)
}

/** Every existing printed-book chapter already contains an authored bilingual title. */
private fun readableChapterTitle(chapter: Chapter, language: AppLanguage): String =
    BookChapters.forLesson(chapter.id)?.title?.value(language) ?: chapter.title.value(language)

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
    val ink = Color(0xFF173D38)
    val secondaryInk = Color(0xFF536D64)
    val borderColor = Color(0xFFD9E5DF)
    // The overview must remain item 0, and chapter n must remain at index n:
    // original API34 instrumentation navigates by exact LazyColumn positions.
    // If the optional reference gallery is expanded it alone becomes item 1.
    LaunchedEffect(unit.number, showProtozoaReferences) {
        if (unit.number == 1 && showProtozoaReferences) {
            unitScrollState.scrollToItem(1)
        }
    }
    Scaffold { padding ->
        LazyColumn(
            state = unitScrollState,
            modifier = Modifier.fillMaxSize().padding(padding)
                .background(Color(0xFFF9FAF7)).testTag("native-unit-" + unit.number),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 18.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedButton(
                        onClick = onBack,
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.heightIn(min = 48.dp),
                    ) {
                        Text(label(language, "Back", "பின்செல்"))
                    }
                    Card(
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F3ED)),
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Column(
                            modifier = Modifier.padding(18.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp),
                        ) {
                            Text(
                                label(language, "UNIT ", "அலகு ") + unit.number,
                                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                                color = Color(0xFF276B58),
                            )
                            Text(
                                readableUnitTitle(unit, language),
                                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                                color = ink,
                            )
                            Text(
                                unit.chapters.size.toString() +
                                    label(language, " chapters", " அத்தியாயங்கள்"),
                                style = MaterialTheme.typography.bodyMedium,
                                color = secondaryInk,
                            )
                        }
                    }
                    if (unit.number == 4) {
                        OutlinedButton(
                            onClick = onEarthworm,
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp)
                                .testTag("u4-open-earthworm-textbook"),
                        ) {
                            Text(label(language, "Earthworm · illustrated lesson",
                                "மண்புழு · படவிளக்கப் பாடம்"))
                        }
                    }
                    if (unit.number == 1) {
                        Text(
                            label(language, "Learning resources", "கற்றல் வளங்கள்"),
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = ink,
                        )
                        Button(
                            onClick = onParamecium,
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier.fillMaxWidth().heightIn(min = 52.dp)
                                .testTag("r14-unit-open-textbook"),
                        ) {
                            Text(label(language, "Paramecium · interactive lessons",
                                "பாரமீசியம் · ஊடாடும் பாடங்கள்"))
                        }
                        OutlinedButton(
                            onClick = onParameciumAtlas,
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp)
                                .testTag("u1-open-diagram-atlas"),
                        ) {
                            Text(label(language, "Paramecium anatomy gallery",
                                "பாரமீசியம் உடற்கூறுப் படத்தொகுப்பு"))
                        }
                        Text(
                            label(language,
                                "Compare life-cycle diagrams with the corresponding parasite lessons.",
                                "ஒட்டுண்ணிகளின் வாழ்க்கைச் சுழற்சிப் படங்களை உரிய பாடங்களுடன் ஒப்பிடுக."),
                            style = MaterialTheme.typography.bodySmall,
                            color = secondaryInk,
                            modifier = Modifier.testTag("u1-missing-protozoa-plates"),
                        )
                        OutlinedButton(
                            onClick = { showProtozoaReferences = !showProtozoaReferences },
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp)
                                .testTag("u1-protozoa-reference-toggle"),
                        ) {
                            Text(label(language,
                                if (showProtozoaReferences) "Hide parasite figures"
                                    else "Entamoeba & Plasmodium figures",
                                if (showProtozoaReferences) "ஒட்டுண்ணிப் படங்களை மறை"
                                    else "எண்டமீபா மற்றும் பிளாஸ்மோடியம் படங்கள்"))
                        }
                    }
                    Text(
                        label(language, "Chapters", "அத்தியாயங்கள்"),
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = ink,
                        modifier = Modifier.padding(top = 4.dp),
                    )
                }
            }
            if (unit.number == 1 && showProtozoaReferences) {
                item(key = "u1-protozoa-reference-panel") {
                    UnitIProtozoaReferenceGallery(language)
                }
            }
            itemsIndexed(unit.chapters, key = { _, chapter -> chapter.id }) { index, chapter ->
                OutlinedButton(
                    onClick = { onChapter(chapter.id) },
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, borderColor),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = if (index % 2 == 0) Color.White else Color(0xFFF0F6F3),
                        contentColor = ink,
                    ),
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 10.dp),
                    modifier = Modifier.fillMaxWidth().heightIn(min = 64.dp)
                        .testTag("chapter-" + chapter.id),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        Text(
                            (index + 1).toString().padStart(2, '0'),
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = Color(0xFF317461),
                        )
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(3.dp),
                        ) {
                            Text(
                                readableChapterTitle(chapter, language),
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                                color = ink,
                            )
                        }
                        Text("›", style = MaterialTheme.typography.titleLarge, color = secondaryInk)
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
    androidx.activity.compose.BackHandler(onBack = onBack)
    // Consume the system status-bar inset before placing the chapter-local
    // language switch. Descendant readers inherit the consumed inset, so their
    // own statusBarsPadding() does not introduce a second full-height gap.
    Column(Modifier.fillMaxSize().statusBarsPadding()) {
        // Make the SELECTED language visually obvious. Previously the active
        // language was a grey disabled TextButton while the inactive option was
        // highlighted, which made English text look like a failed Tamil switch.
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .testTag("chapter-language-toolbar"),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (language == AppLanguage.ENGLISH) {
                Button(
                    onClick = {},
                    enabled = false,
                    colors = ButtonDefaults.buttonColors(
                        disabledContainerColor = Color(0xFF155E4E),
                        disabledContentColor = Color.White,
                    ),
                    modifier = Modifier.heightIn(min = 48.dp).testTag("chapter-language-english"),
                ) { Text("English") }
            } else {
                OutlinedButton(
                    onClick = { onLanguageChange(AppLanguage.ENGLISH) },
                    modifier = Modifier.heightIn(min = 48.dp).testTag("chapter-language-english"),
                ) { Text("English") }
            }
            Spacer(Modifier.padding(horizontal = 3.dp))
            if (language == AppLanguage.TAMIL) {
                Button(
                    onClick = {},
                    enabled = false,
                    colors = ButtonDefaults.buttonColors(
                        disabledContainerColor = Color(0xFF155E4E),
                        disabledContentColor = Color.White,
                    ),
                    modifier = Modifier.heightIn(min = 48.dp).testTag("chapter-language-tamil"),
                ) { Text("தமிழ்") }
            } else {
                OutlinedButton(
                    onClick = { onLanguageChange(AppLanguage.TAMIL) },
                    modifier = Modifier.heightIn(min = 48.dp).testTag("chapter-language-tamil"),
                ) { Text("தமிழ்") }
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
        UnitBookTeachingChapter(chapter, book, language, onBack, revealedQuestionId, onToggle,
            onOpenFullLesson = onOpenFullLesson, onOpenAnatomy = onOpenAnatomy)
        return
    }
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
                Text(readableChapterTitle(chapter, language),
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
