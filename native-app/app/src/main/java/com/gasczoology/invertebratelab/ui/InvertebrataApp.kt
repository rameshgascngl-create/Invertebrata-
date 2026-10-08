package com.gasczoology.invertebratelab.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.gasczoology.invertebratelab.MainViewModel
import com.gasczoology.invertebratelab.data.AppLanguage

private object Routes {
    const val HOME = "home"
    const val UNIT = "unit/{unitNumber}"
    const val ASSESSMENT = "assessment"

    fun unit(number: Int) = "unit/$number"
}

@Composable
fun InvertebrataApp(viewModel: MainViewModel) {
    val language by viewModel.language.collectAsState()
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.HOME) {
        composable(Routes.HOME) {
            FoundationHome(
                language = language,
                onLanguageChange = viewModel::setLanguage,
                onUnit = { navController.navigate(Routes.unit(it)) },
                onAssessment = { navController.navigate(Routes.ASSESSMENT) },
            )
        }
        composable(
            route = Routes.UNIT,
            arguments = listOf(navArgument("unitNumber") { type = NavType.IntType }),
        ) { entry ->
            val unit = entry.arguments?.getInt("unitNumber") ?: 1
            PlaceholderScreen("Unit $unit native content migration pending")
        }
        composable(Routes.ASSESSMENT) {
            PlaceholderScreen("Validated A5 corpus migration pending")
        }
    }
}

@Composable
private fun FoundationHome(
    language: AppLanguage,
    onLanguageChange: (AppLanguage) -> Unit,
    onUnit: (Int) -> Unit,
    onAssessment: () -> Unit,
) {
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text("INVERTEBRATA")
            Text(
                if (language == AppLanguage.TAMIL)
                    "சரிபார்க்கப்பட்ட கல்வி உள்ளடக்கத்தின் நேட்டிவ் மாற்றம்"
                else
                    "Native migration of validated academic content"
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Button(onClick = { onLanguageChange(AppLanguage.ENGLISH) }) { Text("English") }
                Button(onClick = { onLanguageChange(AppLanguage.TAMIL) }) { Text("தமிழ்") }
            }
            for (unit in 1..5) {
                Button(onClick = { onUnit(unit) }, modifier = Modifier.fillMaxWidth()) {
                    Text(if (language == AppLanguage.TAMIL) "அலகு $unit" else "Unit $unit")
                }
            }
            Button(onClick = onAssessment, modifier = Modifier.fillMaxWidth()) {
                Text(if (language == AppLanguage.TAMIL) "மதிப்பீடு" else "Assessment")
            }
        }
    }
}

@Composable
private fun PlaceholderScreen(message: String) {
    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        Text(message)
    }
}
