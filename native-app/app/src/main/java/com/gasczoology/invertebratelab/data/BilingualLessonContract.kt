package com.gasczoology.invertebratelab.data

/** Typed N2.1 content contracts; no science lesson is authored by these types. */
data class LessonSection(
    val id: String,
    val heading: BilingualText,
    val paragraphs: List<BilingualText>,
    val scientificSources: List<String>,
)

data class AcademicReviewRecord(
    val reviewer: String,
    val referenceRecord: String,
    val languageReviewRecord: String,
)

data class NativeBilingualLesson(
    val lessonId: String,
    val title: BilingualText,
    val sections: List<LessonSection>,
    val status: AcademicWorkStatus = AcademicWorkStatus.DRAFT_UNVERIFIED,
    val review: AcademicReviewRecord? = null,
)

object BilingualLessonContract {
    private val identifier = Regex("[a-z0-9]+(-[a-z0-9]+)*")

    fun validate(
        lesson: NativeBilingualLesson,
        slots: List<NativeLessonSlot> = NativeCurriculumManifest.lessonSlots,
    ) {
        require(slots.count { it.id == lesson.lessonId } == 1) {
            "Unknown or ambiguous native lesson slot"
        }
        require(lesson.status != AcademicWorkStatus.NOT_AUTHORED)
        requireComplete(lesson.title)
        require(lesson.sections.isNotEmpty()) { "Headings alone are not lessons" }
        require(lesson.sections.map { it.id }.toSet().size == lesson.sections.size)
        for (section in lesson.sections) {
            require(section.id.matches(identifier))
            requireComplete(section.heading)
            require(section.paragraphs.isNotEmpty())
            section.paragraphs.forEach(::requireComplete)
            require(section.scientificSources.isNotEmpty())
            require(section.scientificSources.all { it.isNotBlank() })
        }
        if (lesson.status == AcademicWorkStatus.ACCEPTED) {
            val record = requireNotNull(lesson.review) {
                "Accepted lesson requires scientific and Tamil reviews"
            }
            require(record.reviewer.isNotBlank())
            require(record.referenceRecord.isNotBlank())
            require(record.languageReviewRecord.isNotBlank())
        }
    }

    private fun requireComplete(text: BilingualText) {
        require(text.english.isNotBlank() && text.tamil.isNotBlank()) {
            "Both language fields are required"
        }
        require(text.english.trim() == text.english && text.tamil.trim() == text.tamil)
    }
}
