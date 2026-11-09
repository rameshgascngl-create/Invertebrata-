package com.gasczoology.invertebratelab.data

import android.content.Context

/**
 * Loads every JSON bank in the assets/mcq folder. No bank ships until a reviewed file is supplied. A file that
 * fails validation is rejected whole and logged; students then simply see no quiz, never a partial one.
 */
object McqAssets {
    fun load(context: Context): McqBank {
        val names = runCatching { context.assets.list("mcq")?.filter { it.endsWith(".json") }.orEmpty() }.getOrDefault(emptyList())
        val banks = names.mapNotNull { name ->
            runCatching { McqBank.parse(context.assets.open("mcq/$name").bufferedReader().use { it.readText() }) }
                .onFailure { android.util.Log.e("NATIVE_MCQ", "Rejected MCQ bank $name: ${it.message}") }
                .getOrNull()
        }
        if (banks.isEmpty()) return McqBank.EMPTY
        val all = banks.flatMap { it.questions }
        if (all.map { it.id }.toSet().size != all.size) {
            android.util.Log.e("NATIVE_MCQ", "Rejected MCQ banks: duplicate question ids across files")
            return McqBank.EMPTY
        }
        return McqBank(all, banks.all { it.verified })
    }
}
