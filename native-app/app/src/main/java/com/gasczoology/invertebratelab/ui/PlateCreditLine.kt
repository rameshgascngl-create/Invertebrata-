package com.gasczoology.invertebratelab.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
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
