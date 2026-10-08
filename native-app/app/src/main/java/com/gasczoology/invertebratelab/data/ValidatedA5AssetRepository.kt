package com.gasczoology.invertebratelab.data

import android.content.Context
import java.nio.charset.StandardCharsets

/**
 * Reads exactly the five bundled, offline authoritative A5 JSON assets.
 * No browser runtime, network, mutable seed content or external files.
 */
class ValidatedA5AssetRepository(private val context: Context) {
    fun load(): List<AcademicUnit> {
        val sourceByUnit = (1..5).associateWith { number ->
            val name = "a5/U" + number + "_A5_VALIDATED.json"
            context.assets.open(name).bufferedReader(StandardCharsets.UTF_8).use { it.readText() }
        }
        return ValidatedA5Parser.parse(sourceByUnit)
    }
}
