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

/** Bookmarks: chapter id -> semantic page ids. Pure so that add/remove/decode can be unit-tested. */
object Bookmarks {
    private val gson = Gson()
    private val type = object : TypeToken<Map<String, List<String>>>() {}.type

    fun decode(raw: String?): Map<String, Set<String>> = runCatching {
        val parsed: Map<String, List<String>> = gson.fromJson(raw ?: "{}", type)
        parsed.filterKeys { it.isNotBlank() }
            .mapValues { (_, pages) -> pages.filter { it.isNotBlank() }.toSet() }
            .filterValues { it.isNotEmpty() }
    }.getOrDefault(emptyMap())

    fun encode(all: Map<String, Set<String>>): String =
        gson.toJson(all.filterValues { it.isNotEmpty() }.mapValues { it.value.toList() })

    fun toggle(all: Map<String, Set<String>>, chapter: String, pageId: String): Map<String, Set<String>> {
        val current = all[chapter].orEmpty()
        val next = if (pageId in current) current - pageId else current + pageId
        return if (next.isEmpty()) all - chapter else all + (chapter to next)
    }
}

private val Context.bookmarkStore by preferencesDataStore(
    name = "book_bookmarks",
    corruptionHandler = ReplaceFileCorruptionHandler { emptyPreferences() },
)

/** Same DataStore mechanism as [BookReadingRepository], in its own file so one cannot overwrite the other. */
class BookmarkRepository(private val context: Context) {
    private val key = stringPreferencesKey("bookmarks")
    val bookmarks = context.bookmarkStore.data.catch { error ->
        if (error is IOException) emit(emptyPreferences()) else throw error
    }.map { Bookmarks.decode(it[key]) }

    internal suspend fun clear() { context.bookmarkStore.edit { it.clear() } }

    /** Reads, toggles and writes in one atomic edit, so two quick taps never lose each other. */
    suspend fun toggle(chapter: String, pageId: String) {
        context.bookmarkStore.edit { p ->
            p[key] = Bookmarks.encode(Bookmarks.toggle(Bookmarks.decode(p[key]), chapter, pageId))
        }
    }
}
