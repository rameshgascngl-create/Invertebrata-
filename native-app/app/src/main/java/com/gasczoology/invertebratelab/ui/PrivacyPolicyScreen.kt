package com.gasczoology.invertebratelab.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.gasczoology.invertebratelab.data.AppLanguage

/**
 * Accessible, fully offline privacy policy. The canonical English and Tamil policy files
 * are packaged as APK assets; this view performs no network or WebView operations.
 */
@Composable
internal fun PrivacyPolicyScreen(language: AppLanguage, onBack: () -> Unit) {
    BackHandler(onBack = onBack)
    val context = LocalContext.current
    val assetName = if (language == AppLanguage.TAMIL) "privacy_policy_ta.txt" else "privacy_policy_en.txt"
    val policyText = remember(context, assetName) {
        context.assets.open(assetName).bufferedReader(Charsets.UTF_8).use { it.readText() }
    }
    val blocks = remember(policyText) { policyText.trim().split(Regex("\\n\\s*\\n")) }
    LazyColumn(
        modifier = Modifier.fillMaxSize().testTag("privacy-policy-screen"),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        item {
            OutlinedButton(
                onClick = onBack,
                modifier = Modifier.heightIn(min = 48.dp).testTag("privacy-policy-back"),
            ) { Text(if (language == AppLanguage.TAMIL) "பின்செல்" else "Back") }
        }
        itemsIndexed(blocks) { index, block ->
            val first = block.trim()
            val title = first.startsWith("# ")
            val sectionHeading = first.startsWith("## ")
            Text(
                text = when {
                    title -> first.removePrefix("# ")
                    sectionHeading -> first.removePrefix("## ")
                    else -> first
                },
                style = when {
                    title -> MaterialTheme.typography.headlineSmall
                    sectionHeading -> MaterialTheme.typography.titleMedium
                    else -> MaterialTheme.typography.bodyLarge
                },
                modifier = Modifier
                    .then(if (title || sectionHeading) Modifier.semantics { heading() } else Modifier)
                    .testTag(
                        when {
                            index == 0 -> "privacy-policy-title"
                            first.contains("rameshgascngl@gmail.com") -> "privacy-policy-contact-$index"
                            else -> "privacy-policy-paragraph-$index"
                        }
                    ),
            )
        }
    }
}
