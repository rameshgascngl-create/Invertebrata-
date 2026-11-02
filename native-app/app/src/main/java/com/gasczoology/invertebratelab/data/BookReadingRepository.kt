package com.gasczoology.invertebratelab.data

import android.content.Context
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

private val Context.bookReadingStore by preferencesDataStore(
    name = "book_reading_positions",
    corruptionHandler = ReplaceFileCorruptionHandler { emptyPreferences() },
)

/** Separate atomic store: saving a question or lab stage cannot overwrite reading positions. */
class BookReadingRepository(private val context: Context) {
    private val key = stringPreferencesKey("positions")
    private val gson = Gson()
    private val type = object : TypeToken<Map<String, BookReadingPosition>>() {}.type
    private fun decode(raw: String?): Map<String, BookReadingPosition> = runCatching {
        val positions: Map<String, BookReadingPosition> = gson.fromJson(raw ?: "{}", type)
        positions.filter { (chapter, p) -> chapter.isNotBlank() && p.pageId.isNotBlank() && p.itemIndex >= 0 && p.itemOffset >= 0 }
    }.getOrDefault(emptyMap())
    val positions = context.bookReadingStore.data.catch { error ->
        if (error is IOException) emit(emptyPreferences()) else throw error
    }.map { decode(it[key]) }

    internal suspend fun clear() { context.bookReadingStore.edit { it.clear() } }

    suspend fun save(chapter: String, position: BookReadingPosition) {
        context.bookReadingStore.edit { preferences ->
            preferences[key] = gson.toJson(decode(preferences[key]) + (chapter to position))
        }
    }
}
