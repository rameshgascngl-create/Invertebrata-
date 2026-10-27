package com.gasczoology.invertebratelab.data

import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File

/** Scans every native textbook chapter, not only the five echinoderm chapters. */
class WholeBookTamilTerminologyAuditTest {
    @Test fun noObsoleteWaterVascularOrMalformedTubeFootTermsAnywhereInChapters() {
        val directory = File("src/main/java/com/gasczoology/invertebratelab/data")
        val chapters = directory.listFiles()
            ?.filter { it.isFile && it.name.endsWith("BookChapter.kt") }
            .orEmpty()
        assertTrue("Native textbook chapter sources not found", chapters.size >= 40)

        val obsolete = listOf(
            "நீர் குருதியோட்ட மண்டலம்",
            "நீர் நாள மண்டலம்",
            "நீர்க்குழல் மண்டலம்",
            "நீர்க் குழல் மண்டலம்",
            "குழாய் கால்ங்கள்",
            "குழாய் கால்ங்கள"
        )
        val findings = chapters.flatMap { file ->
            file.readLines(Charsets.UTF_8).flatMapIndexed { index, line ->
                obsolete.filter { line.contains(it) }
                    .map { term -> "${file.name}:${index + 1}: $term" }
            }
        }
        assertTrue("Tamil terminology audit findings:\n" + findings.joinToString("\n"), findings.isEmpty())
    }
}
