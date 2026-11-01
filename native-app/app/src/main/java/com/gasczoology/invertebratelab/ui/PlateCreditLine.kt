package com.gasczoology.invertebratelab.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.gasczoology.invertebratelab.data.AppLanguage
import com.gasczoology.invertebratelab.data.PlateCredit
import com.gasczoology.invertebratelab.data.PlateCreditParser

/** Credit for a "organism/plate-id" slot, or null when the slot has no entry. */
@Composable
internal fun rememberPlateCredit(slot: String): PlateCredit? {
    val context = LocalContext.current
    val all = remember(context) {
        PlateCreditParser.parse(
            runCatching {
                context.assets.open("type_studies/plate_credits.json")
                    .bufferedReader(Charsets.UTF_8).use { it.readText() }
            }.getOrDefault("")
        )
    }
    return all[slot]
}

/** Visible credit line under a plate; renders nothing when the slot has no credit entry. */
@Composable
internal fun PlateCreditCaption(slot: String, language: AppLanguage, modifier: Modifier = Modifier) {
    rememberPlateCredit(slot)?.let { credit ->
        Text(credit.text(language), style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = modifier.testTag("plate-credit-" + slot.replace('/', '-')))
    }
}
