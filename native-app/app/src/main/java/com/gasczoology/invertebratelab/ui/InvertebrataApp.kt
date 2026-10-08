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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
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
import com.gasczoology.invertebratelab.data.StudyDestination

private object Routes {
    const val HOME = "home"
    const val UNIT = "unit/{unitNumber}"
    const val CHAPTER = "chapter/{chapterId}"
    const val ASSESSMENT = "assessment"

    fun unit(number: Int) = "unit/" + number
    fun chapter(id: String) = "chapter/" + id

    fun restoredDestination(state: NativeLearningState): String = when (state.destination) {
        StudyDestination.HOME -> HOME
        StudyDestination.UNIT -> unit(state.unitNumber)
        StudyDestination.CHAPTER -> chapter(state.chapterId)
        StudyDestination.PRACTICE -> ASSESSMENT
    }
}

@Composable
fun InvertebrataApp(viewModel: MainViewModel) {
    val language by viewModel.language.collectAsState()
    val academicState by viewModel.academicState.collectAsState()
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.HOME) {
        composable(Routes.HOME) {
            when (val state = academicState) {
                AcademicLoadState.Loading -> LoadingScreen(language)
                is AcademicLoadState.Failure -> ErrorScreen(state.reason, language)
                is AcademicLoadState.Ready -> HomeScreen(
                    units = state.units,
                    language = language,
                    onLanguageChange = viewModel::setLanguage,
                    onUnit = { navController.navigate(Routes.unit(it)) },
                    onAssessment = { navController.navigate(Routes.ASSESSMENT) },
                )
            }
        }
        composable(
            route = Routes.UNIT,
            arguments = listOf(navArgument("unitNumber") { type = NavType.IntType }),
        ) { entry ->
            when (val state = academicState) {
                AcademicLoadState.Loading -> LoadingScreen(language)
                is AcademicLoadState.Failure -> ErrorScreen(state.reason, language)
                is AcademicLoadState.Ready -> {
                    val number = entry.arguments?.getInt("unitNumber")
                    val unit = state.units.singleOrNull { it.number == number }
                    if (unit == null) {
                        ErrorScreen("Invalid unit", language)
                    } else {
                        UnitScreen(
                            unit = unit,
                            language = language,
                            onBack = { navController.popBackStack() },
                            onChapter = { navController.navigate(Routes.chapter(it)) },
                        )
                    }
                }
            }
        }
        composable(
            route = Routes.CHAPTER,
            arguments = listOf(navArgument("chapterId") { type = NavType.StringType }),
        ) { entry ->
            when (val state = academicState) {
                AcademicLoadState.Loading -> LoadingScreen(language)
                is AcademicLoadState.Failure -> ErrorScreen(state.reason, language)
                is AcademicLoadState.Ready -> {
                    val id = entry.arguments?.getString("chapterId")
                    val chapter = state.units.flatMap { it.chapters }.singleOrNull { it.id == id }
                    if (chapter == null) {
                        ErrorScreen("Invalid chapter", language)
                    } else {
                        ChapterScreen(chapter, language) { navController.popBackStack() }
                    }
                }
            }
        }
        composable(Routes.ASSESSMENT) {
            when (val state = academicState) {
                AcademicLoadState.Loading -> LoadingScreen(language)
                is AcademicLoadState.Failure -> ErrorScreen(state.reason, language)
                is AcademicLoadState.Ready -> AssessmentScreen(
                    questions = state.units.flatMap { it.chapters }.flatMap { it.a5Questions },
                    language = language,
                    onBack = { navController.popBackStack() },
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
                    "Native Kotlin edition · validated five-mark questions",
                    "நேட்டிவ் Kotlin பதிப்பு · சரிபார்க்கப்பட்ட ஐந்து மதிப்பெண் வினாக்கள்"))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedButton(onClick = { onLanguageChange(AppLanguage.ENGLISH) },
                        modifier = Modifier.heightIn(min = 48.dp).testTag("language-english")) { Text("English") }
                    OutlinedButton(onClick = { onLanguageChange(AppLanguage.TAMIL) },
                        modifier = Modifier.heightIn(min = 48.dp).testTag("language-tamil")) { Text("தமிழ்") }
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
                Text(label(language, "Select a chapter to study its validated questions.",
                    "சரிபார்க்கப்பட்ட வினாக்களைப் படிக்க ஓர் அத்தியாயத்தைத் தேர்ந்தெடுக்கவும்."))
            }
            items(unit.chapters, key = { it.id }) { chapter ->
                Button(onClick = { onChapter(chapter.id) },
                    modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp).testTag("chapter-" + chapter.id)) {
                    Column {
                        Text(chapter.id)
                        Text(chapter.a5Questions.first().question.value(language))
                    }
                }
            }
        }
    }
}

@Composable
private fun ChapterScreen(chapter: Chapter, language: AppLanguage, onBack: () -> Unit) {
    Scaffold { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding).testTag("native-chapter-" + chapter.id),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            item {
                OutlinedButton(onClick = onBack, modifier = Modifier.heightIn(min = 48.dp)) { Text(label(language, "Back", "பின்செல்")) }
                Text(label(language, "Chapter: ", "அத்தியாயம்: ") + chapter.id)
                Text(label(language, "Validated five-mark questions",
                    "சரிபார்க்கப்பட்ட ஐந்து மதிப்பெண் வினாக்கள்"))
            }
            items(chapter.a5Questions, key = { it.id }) { question ->
                QuestionCard(question, language)
            }
        }
    }
}

@Composable
private fun QuestionCard(question: A5Question, language: AppLanguage) {
    var showAnswer by rememberSaveable(question.id) { mutableStateOf(false) }
    Column(verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth().testTag("a5-card-" + question.id)) {
        Text(question.id)
        Text(question.question.value(language),
            modifier = Modifier.testTag("a5-question-" + question.id))
        OutlinedButton(onClick = { showAnswer = !showAnswer },
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
    language: AppLanguage,
    onBack: () -> Unit,
) {
    var index by rememberSaveable { mutableIntStateOf(0) }
    Scaffold { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp).testTag("native-assessment"),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            OutlinedButton(onClick = onBack, modifier = Modifier.heightIn(min = 48.dp)) { Text(label(language, "Back", "பின்செல்")) }
            Text(label(language, "Five-mark self-study", "ஐந்து மதிப்பெண் சுயபயிற்சி"))
            Text((index + 1).toString() + " / " + questions.size)
            if (questions.isNotEmpty()) {
                val question = questions[index.coerceIn(0, questions.lastIndex)]
                Text(label(language, "Chapter: ", "அத்தியாயம்: ") + question.chapterId)
                QuestionCard(question, language)
                Spacer(Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedButton(onClick = { index -= 1 }, enabled = index > 0,
                        modifier = Modifier.heightIn(min = 48.dp).testTag("assessment-prev")) {
                        Text(label(language, "Previous", "முந்தையது"))
                    }
                    Button(onClick = { index += 1 }, enabled = index < questions.lastIndex,
                        modifier = Modifier.heightIn(min = 48.dp).testTag("assessment-next")) {
                        Text(label(language, "Next", "அடுத்தது"))
                    }
                }
            }
        }
    }
}
