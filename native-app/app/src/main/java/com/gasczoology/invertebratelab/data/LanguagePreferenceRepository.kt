package com.gasczoology.invertebratelab.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.invertebrataPreferences by preferencesDataStore(name = "invertebrata_preferences")

class LanguagePreferenceRepository(private val context: Context) {
    private val languageKey = stringPreferencesKey("language")

    val language: Flow<AppLanguage> = context.invertebrataPreferences.data.map { prefs ->
        when (prefs[languageKey]) {
            AppLanguage.TAMIL.name -> AppLanguage.TAMIL
            else -> AppLanguage.ENGLISH
        }
    }

    suspend fun setLanguage(language: AppLanguage) {
        context.invertebrataPreferences.edit { prefs ->
            prefs[languageKey] = language.name
        }
    }
}
