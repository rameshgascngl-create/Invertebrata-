package com.gasczoology.invertebratelab.data

import org.junit.Assert.assertThrows
import org.junit.Test

class NativeAcademicContentContractTest {
    private fun text(en: String = "Example English", ta: String = "தமிழ் எடுத்துக்காட்டு") =
        BilingualText(en, ta)

    private fun draftLesson() = NativeBilingualLesson(
        lessonId = NativeCurriculumManifest.EARTHWORM_LESSON_ID,
        title = text(),
        sections = listOf(LessonSection(
            id = "introduction",
            heading = text(),
            paragraphs = listOf(text()),
            scientificSources = listOf("fixture-only-not-a-scientific-review"),
        )),
    )

    @Test
    fun bilingualSyntheticFixtureIsAnUnverifiedDraft() {
        BilingualLessonContract.validate(draftLesson())
    }

    @Test
    fun missingTamilSourcesOrKnownLessonIdentityIsRejected() {
        val good = draftLesson()
        assertThrows(IllegalArgumentException::class.java) {
            BilingualLessonContract.validate(good.copy(title = text(ta = "")))
        }
        assertThrows(IllegalArgumentException::class.java) {
            BilingualLessonContract.validate(good.copy(sections =
                listOf(good.sections.single().copy(scientificSources = emptyList()))))
        }
        assertThrows(IllegalArgumentException::class.java) {
            BilingualLessonContract.validate(good.copy(lessonId = "u4-invented"))
        }
        assertThrows(IllegalArgumentException::class.java) {
            BilingualLessonContract.validate(good.copy(sections =
                listOf(good.sections.single(), good.sections.single())))
        }
    }

    @Test
    fun acceptedLessonsNeedRecordedAcademicAndTamilReview() {
        assertThrows(IllegalArgumentException::class.java) {
            BilingualLessonContract.validate(draftLesson().copy(
                status = AcademicWorkStatus.ACCEPTED))
        }
        assertThrows(IllegalArgumentException::class.java) {
            BilingualLessonContract.validate(draftLesson().copy(
                status = AcademicWorkStatus.ACCEPTED,
                review = AcademicReviewRecord("reviewer", "science-file", "")))
        }
    }

    private fun draftPlate() = NativeAnatomyPlate(
        organismId = "earthworm",
        plateId = "digestive",
        features = listOf(
            AnatomyFeatureSpec("feature-a", text(), "geometry-a", "touch-a"),
            AnatomyFeatureSpec("feature-b", text(), "geometry-b", "touch-b"),
        ),
        relationships = listOf(FeatureRelationship(
            "feature-a", "feature-b", AnatomicalRelationship.ADJACENT_TO)),
        scientificSources = listOf("synthetic-test-only"),
    )

    @Test
    fun anatomyRejectsUnlistedPlatesDuplicatesAndDanglingTopology() {
        AnatomySpecificationContract.validate(draftPlate())
        assertThrows(IllegalArgumentException::class.java) {
            AnatomySpecificationContract.validate(draftPlate().copy(organismId = "unknown"))
        }
        assertThrows(IllegalArgumentException::class.java) {
            AnatomySpecificationContract.validate(draftPlate().copy(plateId = "not-approved"))
        }
        assertThrows(IllegalArgumentException::class.java) {
            AnatomySpecificationContract.validate(draftPlate().copy(relationships = listOf(
                FeatureRelationship("feature-a", "missing", AnatomicalRelationship.CONTAINS))))
        }
        assertThrows(IllegalArgumentException::class.java) {
            AnatomySpecificationContract.validate(draftPlate().copy(features =
                listOf(draftPlate().features.first(), draftPlate().features.first())))
        }
    }

    @Test
    fun acceptanceRejectsMissingOrPartialAnatomyReview() {
        val accepted = draftPlate().copy(status = AcademicWorkStatus.ACCEPTED)
        assertThrows(IllegalArgumentException::class.java) {
            AnatomySpecificationContract.validate(accepted)
        }
        val partial = AnatomyReviewRecord("fixture-reference", "fixture-biologist",
            "fixture-tamil-reviewer", setOf("feature-a"),
            setOf("geometry-a", "geometry-b"))
        assertThrows(IllegalArgumentException::class.java) {
            AnatomySpecificationContract.validate(accepted.copy(review = partial))
        }
        assertThrows(IllegalArgumentException::class.java) {
            AnatomySpecificationContract.validate(draftPlate().copy(
                features = listOf(draftPlate().features.first().copy(narration = text(ta = ""))),
                relationships = emptyList()))
        }
    }
}
