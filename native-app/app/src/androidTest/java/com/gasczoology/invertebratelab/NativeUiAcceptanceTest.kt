package com.gasczoology.invertebratelab

import android.content.pm.ActivityInfo
import android.graphics.BitmapFactory
import android.os.ParcelFileDescriptor
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertHeightIsAtLeast
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.assertWidthIsAtLeast
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.click
import androidx.compose.ui.test.swipeUp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.geometry.Offset
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.gasczoology.invertebratelab.data.AcademicUnit
import com.gasczoology.invertebratelab.data.NativeLearningRepository
import com.gasczoology.invertebratelab.data.NativeLearningState
import com.gasczoology.invertebratelab.data.NativeLessonDrafts
import com.gasczoology.invertebratelab.data.ParameciumExternalGeometry
import com.gasczoology.invertebratelab.data.PrototypeCanvasViewport
import com.gasczoology.invertebratelab.data.ValidatedA5AssetRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlinx.coroutines.runBlocking
import org.junit.Ignore
import androidx.compose.ui.test.performScrollToNode

/**
 * Save genuine device-rendered screenshots of the Canvas, not a generated or
 * screenshot-traced anatomy substitute. Captures are technical review evidence.
 */
private fun saveN23bReviewScreenshot(fileName: String) {
    check(fileName in setOf(
        "paramecium-normal-oral-groove.png",
        "paramecium-tamil200-cytoproct.png",
        "r14-textbook-normal.png",
        "r14-textbook-tamil200.png",
        "r15-feeding-normal.png",
        "r15-feeding-tamil200.png",
    ))

    val automation = InstrumentationRegistry.getInstrumentation().uiAutomation
    val appPackage = InstrumentationRegistry.getInstrumentation().targetContext.packageName
    val activePackage = automation.rootInActiveWindow?.packageName?.toString()
    val file = "/sdcard/Download/native-anatomy-evidence/$fileName"
    // UiAutomation.rootInActiveWindow may legitimately be null on API34
    // despite the activity's Compose semantics remaining active. It is only
    // diagnostic: acceptance requires actual displayed pencil/cell/selected
    // pixels below, and a captured launcher or blank screen fails those gates.
    // Never make a nullable accessibility root a prerequisite for screencap.

    // Proven API34 screen capture path: save ACTUAL hardware display pixels
    // through the Android shell, not an independently rasterized Compose view.
    ParcelFileDescriptor.AutoCloseInputStream(
        automation.executeShellCommand("screencap -p $file")
    ).use { it.readBytes() }
    val bytes = ParcelFileDescriptor.AutoCloseInputStream(
        automation.executeShellCommand("cat $file")
    ).use { it.readBytes() }
    check(bytes.size > 1000) {
        "Actual Android screenshot is missing/truncated: $file"
    }
    val bitmap = checkNotNull(BitmapFactory.decodeByteArray(bytes, 0, bytes.size)) {
        "Android screenshot not a valid PNG: $file"
    }

    var samples = 0
    var canvasBackground = 0
    var cellFill = 0
    var graphite = 0
    var selectedHighlight = 0
    val observedColors = mutableMapOf<Int, Int>()
    try {
        for (y in 0 until bitmap.height step 14) {
            for (x in 0 until bitmap.width step 14) {
                val rgb = bitmap.getPixel(x, y) and 0x00FFFFFF
                observedColors[rgb] = (observedColors[rgb] ?: 0) + 1
                if (rgb == 0x00FBFAF6) canvasBackground++
                if (rgb == 0x00F1EFE9) cellFill++
                if (rgb == 0x0044413F || rgb == 0x00302E2C ||
                    rgb == 0x008A8580) graphite++
                if (rgb == 0x00B55B16 || rgb == 0x00E28D3E) selectedHighlight++
                samples++
            }
        }
        // Preserve exact pixel thresholds, sample stride, and screenshot
        // provenance. Most-common observed colors are diagnostics ONLY.
        // R1.5 is a small functional flowchart rather than a full-cell plate.
        // Its own nonzero graphite/cell/highlight requirement is additional;
        // original R1.3 anatomical screenshot thresholds remain unchanged.
        val r15Plate = fileName.startsWith("r15-feeding-")
        val pixelsPass = if (r15Plate)
            canvasBackground >= 50 && cellFill >= 5 &&
                graphite >= 5 && selectedHighlight >= 2
        else canvasBackground * 100 >= samples * 12 &&
            cellFill * 100 >= samples * 6 && graphite >= 8
        check(pixelsPass) {
            val palette = observedColors.entries.sortedByDescending { it.value }
                .take(6).joinToString { (color, count) ->
                    "0x" + color.toString(16).padStart(6, '0') + "=$count"
                }
            "Insufficient anatomical Canvas area in $file: " +
                "paper=$canvasBackground, graphite=$graphite, cell=$cellFill, " +
                "samples=$samples, foreground=$activePackage, topColors=[$palette]"
        }
        if (fileName == "paramecium-tamil200-cytoproct.png") {
            check(selectedHighlight >= 4) {
                "Selected cytoproct highlight is not visible in Tamil 200% PNG: " +
                    "highlight=$selectedHighlight, samples=$samples"
            }
        }
    } finally {
        bitmap.recycle()
    }
}

/**
 * N1.3 device-level acceptance, executed on the installed native Android app.
 * Expected text is parsed afresh from the bundled authoritative source assets.
 * No coordinate tapping, OCR, browser, HTML or network dependency is used.
 */
@RunWith(AndroidJUnit4::class)
class NativeUiAcceptanceTest {
    @get:Rule val rule = createAndroidComposeRule<MainActivity>()

    // Isolation only: persistence intentionally survives individual instrumentation
    // tests, so each existing N1.3 assertion needs a clean navigation fixture.
    @Before fun resetLearningPosition() {
        runBlocking {
            NativeLearningRepository(InstrumentationRegistry.getInstrumentation().targetContext)
                .save(NativeLearningState())
        }
        rule.activityRule.scenario.recreate()
    }

    private val units: List<AcademicUnit> by lazy {
        ValidatedA5AssetRepository(InstrumentationRegistry.getInstrumentation().targetContext).load()
    }

    private fun waitFor(tag: String) {
        rule.waitUntil(timeoutMillis = 20_000L) {
            rule.onAllNodesWithTag(tag).fetchSemanticsNodes().isNotEmpty()
        }
    }

    private fun englishHome() {
        waitFor("native-home")
        rule.onNodeWithTag("language-english").performClick()
        rule.waitUntil(timeoutMillis = 10_000L) {
            rule.homeUnitOneShows(tamil = false)
        }
    }

    @Test
    fun allFiveUnitsAndAll43ChaptersDisplayTheExact86EnglishQuestions() {
        englishHome()
        assertEquals(5, units.size)
        assertEquals(43, units.sumOf { it.chapters.size })
        for (unit in units) {
            rule.onNodeWithTag("native-home").performScrollToIndex(unit.number)
            rule.onNodeWithTag("unit-" + unit.number).performClick()
            waitFor("native-unit-" + unit.number)
            for ((chapterIndex, chapter) in unit.chapters.withIndex()) {
                rule.onNodeWithTag("native-unit-" + unit.number)
                    .performScrollToIndex(chapterIndex + 1)
                rule.onNodeWithTag("chapter-" + chapter.id).performClick()
                // Each chapter opens in its own reader (see ReaderRoute); wait for that
                // reader's container, not the generic native-chapter-<id> list.
                val container = readerContainerTag(chapter)
                waitFor(container)
                assertEquals(2, chapter.a5Questions.size)
                for (question in chapter.a5Questions) {
                    val questionTag = readerQuestionTag(chapter, question)
                    rule.onNodeWithTag(container).performScrollToNode(hasTestTag(questionTag))
                    rule.onNodeWithTag(questionTag).assertTextEquals(question.question.english)
                }
                rule.onNodeWithTag(container).performScrollToIndex(0)
                leaveReader(rule, chapter)
                waitFor("native-unit-" + unit.number)
            }
            rule.onNodeWithTag("native-unit-" + unit.number).performScrollToIndex(0)
            rule.onNodeWithText("Back").performClick()
            waitFor("native-home")
        }
    }

    @Test
    fun practiceTraversesAll86QuestionsAndRevealsAll430ExactEnglishAnswerPoints() {
        englishHome()
        val allQuestions = units.flatMap { it.chapters }.flatMap { it.a5Questions }
        assertEquals(86, allQuestions.size)
        rule.onNodeWithTag("native-home").performScrollToIndex(6)
        rule.onNodeWithTag("a5-assessment").performClick()
        waitFor("native-assessment")

        for ((index, question) in allQuestions.withIndex()) {
            rule.onNodeWithTag("a5-question-" + question.id)
                .assertTextEquals(question.question.english)
            rule.onNodeWithTag("a5-toggle-" + question.id).performScrollTo().performClick()
            // DataStore state publication is asynchronous. Observe the exact
            // revealed question before interrogating its off-screen children;
            // do not bypass the existing 430 full-text assertions.
            rule.waitUntil(timeoutMillis = 10_000L) {
                rule.onAllNodesWithTag("a5-point-" + question.id + "-1",
                    useUnmergedTree = true).fetchSemanticsNodes().isNotEmpty()
            }
            assertEquals(5, question.answerPoints.size)
            for ((pointIndex, point) in question.answerPoints.withIndex()) {
                val pointTag = "a5-point-" + question.id + "-" + (pointIndex + 1)
                // Preserve exact-text assertions while making the real scroller
                // expose an off-screen answer point to the merged semantics tree.
                rule.onNodeWithTag(pointTag, useUnmergedTree = true).performScrollTo()
                rule.onNodeWithTag(pointTag)
                    .assertTextEquals((pointIndex + 1).toString() + ". " + point.english)
            }
            if (index < allQuestions.lastIndex) {
                rule.onNodeWithTag("assessment-next").performScrollTo().performClick()
                val nextId = allQuestions[index + 1].id
                rule.waitUntil(timeoutMillis = 10_000L) {
                    rule.onAllNodesWithTag("a5-question-" + nextId,
                        useUnmergedTree = true).fetchSemanticsNodes().isNotEmpty()
                }
            }
        }
        rule.onNodeWithTag("assessment-next").assertIsNotEnabled()
    }

    @Test
    fun tamilAnswerAndQuestionPositionSurviveRotation() {
        waitFor("native-home")
        rule.onNodeWithTag("language-tamil").performClick()
        rule.waitUntil(timeoutMillis = 10_000L) {
            rule.homeUnitOneShows(tamil = true)
        }

        val questions = units.flatMap { it.chapters }.flatMap { it.a5Questions }
        rule.onNodeWithTag("native-home").performScrollToIndex(6)
        rule.onNodeWithTag("a5-assessment").performClick()
        waitFor("native-assessment")
        repeat(4) {
            rule.onNodeWithTag("assessment-next").performScrollTo().performClick()
        }
        val fifth = questions[4]
        rule.onNodeWithTag("a5-question-" + fifth.id).assertTextEquals(fifth.question.tamil)
        rule.onNodeWithTag("a5-toggle-" + fifth.id).performScrollTo().performClick()
        val tamilPointTag = "a5-point-" + fifth.id + "-1"
        rule.onNodeWithTag(tamilPointTag, useUnmergedTree = true).performScrollTo()
        rule.onNodeWithTag(tamilPointTag)
            .assertTextEquals("1. " + fifth.answerPoints[0].tamil)

        try {
            rule.activityRule.scenario.onActivity {
                it.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            }
            waitFor("native-assessment")
            val tamilQuestionTag = "a5-question-" + fifth.id
            rule.onNodeWithTag(tamilQuestionTag, useUnmergedTree = true).performScrollTo()
            rule.onNodeWithTag(tamilQuestionTag).assertTextEquals(fifth.question.tamil)
            rule.onNodeWithTag(tamilPointTag, useUnmergedTree = true).performScrollTo()
            rule.onNodeWithTag(tamilPointTag)
                .assertTextEquals("1. " + fifth.answerPoints[0].tamil)
            rule.onNodeWithText("5 / 86").assertExists()
        } finally {
            rule.activityRule.scenario.onActivity {
                it.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }
        }
    }

    @Test
    fun parameciumDraftIsReachableInEnglishAndTamilWithoutChangingA5() {
        // The Paramecium chapter opens in the book reader (not the generic list). It must carry a
        // visible bilingual draft notice, and its validated A5 questions must be unchanged.
        englishHome()
        rule.onNodeWithTag("native-home").performScrollToIndex(1)
        rule.onNodeWithTag("unit-1").performClick()
        waitFor("native-unit-1")
        rule.onNodeWithTag("native-unit-1").performScrollToIndex(3)
        rule.onNodeWithTag("chapter-u1-paramecium").performClick()
        val chapter = units.first().chapters.single { it.id == "u1-paramecium" }
        val container = readerContainerTag(chapter)
        waitFor(container)
        rule.onNodeWithTag(container).performScrollToIndex(0)
        rule.onNodeWithTag("chapter-draft-notice", useUnmergedTree = true).assertExists().assertTextEquals(
            "Academic review pending: illustrations and Tamil vocabulary remain drafts.")
        val first = chapter.a5Questions.first()
        rule.onNodeWithTag(container).performScrollToNode(hasTestTag(readerQuestionTag(chapter, first)))
        rule.onNodeWithTag(readerQuestionTag(chapter, first)).assertTextEquals(first.question.english)
        rule.onNodeWithTag(container).performScrollToIndex(0)
        leaveReader(rule, chapter)
        waitFor("native-unit-1")
        rule.onNodeWithTag("native-unit-1").performScrollToIndex(0)
        rule.onNodeWithText("Back").performClick()
        waitFor("native-home")
        rule.onNodeWithTag("language-tamil").performClick()
        rule.waitUntil(timeoutMillis = 10_000L) { rule.homeUnitOneShows(tamil = true) }
        rule.onNodeWithTag("native-home").performScrollToIndex(1)
        rule.onNodeWithTag("unit-1").performClick()
        waitFor("native-unit-1")
        rule.onNodeWithTag("native-unit-1").performScrollToIndex(3)
        rule.onNodeWithTag("chapter-u1-paramecium").performClick()
        waitFor(container)
        rule.onNodeWithTag(container).performScrollToIndex(0)
        rule.onNodeWithTag("chapter-draft-notice", useUnmergedTree = true).assertExists().assertTextEquals(
            "அறிவியல் மதிப்பாய்வு நிலுவை: வரைபடங்களும் தமிழ் சொற்களும் வரைவு நிலையில் உள்ளன.")
        rule.onNodeWithTag(container).performScrollToNode(hasTestTag(readerQuestionTag(chapter, first)))
        rule.onNodeWithTag(readerQuestionTag(chapter, first)).assertTextEquals(first.question.tamil)
    }

    // Plate gallery students can actually reach from the Anatomy tab (organ-level selection is
    // a documented known gap: native-app/KNOWN_GAPS.md). Lives in this class because the
    // R1.6.1 established-UI job runs this class by name.
    @Test
    fun oralApparatusAndContractileVacuolePlatesAreReachableFromAnatomy() {
        englishHome()
        rule.onNodeWithTag("r1-home-open-paramecium").performScrollTo().performClick()
        waitFor("r1-paramecium-lab")
        rule.onNodeWithTag("r1-tab-anatomy").performScrollTo().performClick()
        for (plateId in listOf("oral-apparatus", "contractile-vacuoles")) {
            rule.onNodeWithTag("r2-plate-bitmap-paramecium-$plateId", useUnmergedTree = true)
                .performScrollTo().assertExists()
            rule.onNodeWithTag("r2-plate-missing-paramecium-$plateId", useUnmergedTree = true)
                .assertDoesNotExist()
        }
        // Real emulator evidence of the gallery students can reach (runner requires this file).
        rule.onNodeWithTag("r2-plate-bitmap-paramecium-oral-apparatus", useUnmergedTree = true)
            .performScrollTo()
        rule.waitForIdle()
        saveGalleryEvidenceScreenshot(
            "paramecium-anatomy-gallery-normal.png",
            rule.onNodeWithTag("r2-plate-bitmap-paramecium-oral-apparatus", useUnmergedTree = true)
                .fetchSemanticsNode().boundsInRoot,
            "type_studies/paramecium/oral-apparatus.png",
        )
    }

    @Test
    fun plateOpensFullScreenViewerWithZoomControls() {
        englishHome()
        rule.onNodeWithTag("r1-home-open-paramecium").performScrollTo().performClick()
        waitFor("r1-paramecium-lab")
        rule.onNodeWithTag("r1-tab-anatomy").performScrollTo().performClick()
        rule.onNodeWithTag("r2-plate-expand-paramecium-contractile-vacuoles", useUnmergedTree = true)
            .performScrollTo().performClick()
        rule.waitUntil(timeoutMillis = 10_000L) {
            rule.onAllNodesWithTag("r2-fullscreen-dialog", useUnmergedTree = true)
                .fetchSemanticsNodes().isNotEmpty()
        }
        for (control in listOf("r2-zoom-in", "r2-zoom-out", "r2-zoom-reset", "r2-fullscreen-close")) {
            rule.onNodeWithTag(control, useUnmergedTree = true).assertExists().assertHeightIsAtLeast(48.dp)
        }
        rule.onNodeWithTag("r6-zoom-level", useUnmergedTree = true).assertExists()
        rule.waitForIdle()
        saveGalleryEvidenceScreenshot("paramecium-plate-viewer-normal.png")
        rule.onNodeWithTag("r2-zoom-in", useUnmergedTree = true).performClick()
        rule.onNodeWithTag("r2-zoom-reset", useUnmergedTree = true).performClick()
        rule.onNodeWithTag("r2-fullscreen-close", useUnmergedTree = true).performClick()
        rule.waitUntil(timeoutMillis = 10_000L) {
            rule.onAllNodesWithTag("r2-fullscreen-dialog", useUnmergedTree = true)
                .fetchSemanticsNodes().isEmpty()
        }
    }

    @Ignore("Known gap (owner decision 2026-11-01): organ-level atlas selection is unreachable; see native-app/KNOWN_GAPS.md")

    @Test
    fun nativeParameciumCanvasButtonsHighlightTheExpectedBilingualOrgan() {
        englishHome()
        rule.onNodeWithTag("native-home").performScrollToIndex(1)
        rule.onNodeWithTag("unit-1").performClick()
        waitFor("native-unit-1")
        rule.onNodeWithTag("native-unit-1").performScrollToIndex(3)
        rule.onNodeWithTag("chapter-u1-paramecium").performClick()
        waitFor("native-chapter-u1-paramecium")
        rule.onNodeWithTag("native-chapter-u1-paramecium").performScrollToIndex(5 + NativeLessonDrafts.paramecium.sections.size)
        waitFor("n23b-external-preview")
        rule.onNodeWithTag("r13-pencil-atlas-style", useUnmergedTree = true)
            .performScrollTo().assertExists()
        rule.onNodeWithTag("n23b-external-canvas").assertExists()
        rule.onNodeWithTag("n23b-orientation", useUnmergedTree = true)
            .assertTextEquals(
                "Anterior (rounded) ←   Posterior (tapered) →   Oral/ventral side: bottom"
            )

        // Test the actual native 48 dp Compose controls rather than tapping by coordinates.
        val cilia = rule.onNodeWithTag("n23b-select-somatic-cilia", useUnmergedTree = true)
        cilia.performScrollTo()
        cilia.assertHasClickAction()
        cilia.assertHeightIsAtLeast(48.dp)
        cilia.performClick()
        rule.onNodeWithTag("n23b-selected-label", useUnmergedTree = true)
            .performScrollTo()
        rule.onNodeWithTag("n23b-selected-label")
            .assertTextEquals("Highlighted structure: Somatic cilia")

        val groove = rule.onNodeWithTag("n23b-select-oral-groove", useUnmergedTree = true)
        groove.performScrollTo()
        groove.performClick()
        rule.onNodeWithTag("n23b-selected-label", useUnmergedTree = true)
            .performScrollTo()
        rule.onNodeWithTag("n23b-selected-label")
            .assertTextEquals("Highlighted structure: Oral groove")
        rule.onNodeWithTag("n23c3-oral-geometry-limit", useUnmergedTree = true)
            .assertExists()
        rule.onNodeWithTag("n23d-review-summary", useUnmergedTree = true)
            .performScrollTo()
            .assertTextEquals("N2.3D1 review records — biology: 0/5; Tamil: 0/5; physical-device QA: pending")
        rule.onNodeWithTag("n23d-source-limit", useUnmergedTree = true)
            .performScrollTo().assertExists()
        rule.onNodeWithTag("n23e2-source-access", useUnmergedTree = true)
            .performScrollTo().assertExists()
        rule.onNodeWithTag("n23e2-image-rights", useUnmergedTree = true)
            .performScrollTo().assertExists()
        val jump = rule.onNodeWithTag("r13-jump-to-pencil-atlas",
            useUnmergedTree = true)
        jump.performScrollTo().assertHeightIsAtLeast(48.dp).performClick()
        rule.waitForIdle()
        rule.onNodeWithTag("n23b-external-canvas", useUnmergedTree = true)
            .assertExists()
        saveN23bReviewScreenshot("paramecium-normal-oral-groove.png")
    }

    @Ignore("Known gap (owner decision 2026-11-01): organ-level atlas selection is unreachable; see native-app/KNOWN_GAPS.md")

    @Test
    fun provisionalCanvasDirectTapUsesSameFittedCoordinatesAsDrawing() {
        englishHome()
        rule.onNodeWithTag("native-home").performScrollToIndex(1)
        rule.onNodeWithTag("unit-1").performClick()
        waitFor("native-unit-1")
        rule.onNodeWithTag("native-unit-1").performScrollToIndex(3)
        rule.onNodeWithTag("chapter-u1-paramecium").performClick()
        waitFor("native-chapter-u1-paramecium")
        rule.onNodeWithTag("native-chapter-u1-paramecium").performScrollToIndex(5 + NativeLessonDrafts.paramecium.sections.size)
        waitFor("n23b-external-preview")
        val canvas = rule.onNodeWithTag("n23b-external-canvas", useUnmergedTree = true)
        canvas.performScrollTo()
        val location = ParameciumExternalGeometry.hotspots.single {
            it.featureId == "cytoproct"
        }
        // SemanticsNode has measured root bounds; TouchInjectionScope does
        // not expose a `size` property under this Compose UI testing version.
        val bounds = canvas.fetchSemanticsNode().boundsInRoot
        val viewport = PrototypeCanvasViewport.fit(bounds.width, bounds.height)
        val point = viewport.referenceToCanvas(
            location.x * ParameciumExternalGeometry.REFERENCE_WIDTH,
            location.y * ParameciumExternalGeometry.REFERENCE_HEIGHT)
        canvas.performTouchInput {
            click(Offset(point.first, point.second))
        }
        rule.onNodeWithTag("n23b-selected-label", useUnmergedTree = true)
            .performScrollTo()
        rule.onNodeWithTag("n23b-selected-label")
            .assertTextEquals("Highlighted structure: Cytoproct (cell anus)")
        rule.onNodeWithTag("n23c2-cytoproct-evidence-limit", useUnmergedTree = true)
            .assertExists()
    }

    @Ignore("Known gap (owner decision 2026-11-01): organ-level atlas selection is unreachable; see native-app/KNOWN_GAPS.md")

    @Test
    fun r14UnitOpensIllustratedTextbookAndAnatomyInsteadOfStartingWithQuestions() {
        englishHome()
        rule.onNodeWithTag("native-home").performScrollToIndex(1)
        rule.onNodeWithTag("unit-1").performClick()
        waitFor("native-unit-1")
        rule.onNodeWithTag("r14-unit-open-textbook", useUnmergedTree = true)
            .performScrollTo().assertHeightIsAtLeast(48.dp).performClick()
        waitFor("r1-paramecium-lab")
        rule.onNodeWithTag("r1-study-title", useUnmergedTree = true)
            .performScrollTo().assertExists()
        rule.onNodeWithTag("r14-jump-to-pencil-plate", useUnmergedTree = true)
            .performScrollTo().assertHeightIsAtLeast(48.dp).performClick()
        rule.waitForIdle()
        val textbookCanvas = rule.onNodeWithTag("n23b-external-canvas", useUnmergedTree = true)
        val textbookBounds = textbookCanvas.fetchSemanticsNode().boundsInRoot
        val visibleBounds = rule.onNodeWithTag("r1-paramecium-lab")
            .fetchSemanticsNode().boundsInRoot
        assertTrue("Textbook plate must be fully visible after learner's jump action",
            textbookBounds.top >= visibleBounds.top - 1f &&
            textbookBounds.bottom <= visibleBounds.bottom + 1f)
        saveN23bReviewScreenshot("r14-textbook-normal.png")
        rule.onNodeWithTag("r14-contents-toggle", useUnmergedTree = true)
            .performScrollTo().performClick()
        rule.onNodeWithTag("r14-open-section-feeding-and-digestion",
            useUnmergedTree = true).performScrollTo().performClick()
        rule.onNodeWithTag("r14-section-heading", useUnmergedTree = true)
            .performScrollTo().assertTextEquals("Feeding and intracellular digestion")
        // The source-driven native diagram replaces the earlier static
        // oral figure; verify the live stage selection and actual explanatory text.
        rule.onNodeWithTag("r15-feeding-pathway-canvas", useUnmergedTree = true)
            .performScrollTo().assertExists()
        rule.onNodeWithTag("r15-feeding-stage-egestion", useUnmergedTree = true)
            .performScrollTo().assertHeightIsAtLeast(48.dp).performClick()
        rule.onNodeWithTag("r15-feeding-explanation", useUnmergedTree = true)
            .performScrollTo().assertTextEquals(
                com.gasczoology.invertebratelab.data.ParameciumLearningEngine
                    .simulation(com.gasczoology.invertebratelab.data.ParameciumProcess.FEEDING)
                    .stages.last().explanation.english
            )
        rule.onNodeWithTag("r15-feeding-replay", useUnmergedTree = true)
            .performScrollTo().assertHeightIsAtLeast(48.dp)
        rule.onNodeWithTag("r15-view-feeding-diagram", useUnmergedTree = true)
            .performScrollTo().assertHeightIsAtLeast(48.dp).performClick()
        rule.waitForIdle()
        val normalFeeding = rule.onNodeWithTag("r15-feeding-pathway-canvas",
            useUnmergedTree = true).fetchSemanticsNode().boundsInRoot
        val normalRoot = rule.onRoot().fetchSemanticsNode().boundsInRoot
        assertTrue("Normal feeding diagram must occupy actual visible screen: " +
            "canvas=$normalFeeding root=$normalRoot",
            normalFeeding.height > 0f &&
                normalFeeding.top >= normalRoot.top - 1f &&
                normalFeeding.bottom <= normalRoot.bottom + 1f)
        saveN23bReviewScreenshot("r15-feeding-normal.png")
        rule.onNodeWithTag("r14-from-study-anatomy", useUnmergedTree = true)
            .performScrollTo().assertHeightIsAtLeast(48.dp).performClick()
        rule.onNodeWithTag("r1-atlas-heading", useUnmergedTree = true)
            .performScrollTo().assertExists()
    }

    @Test
    fun interactiveParameciumLabOpensWithFiveProcessesAnatomyAndNarration() {
        englishHome()
        rule.onNodeWithTag("r1-home-open-paramecium").performClick()
        waitFor("r1-paramecium-lab")
        rule.onNodeWithTag("r1-tab-simulate").performClick()
        rule.onNodeWithTag("r1-process-osmoregulation").assertExists()
        rule.onNodeWithTag("r1-stage-title")
            .assertTextEquals("Stage 1/4 — Water enters")
        rule.onNodeWithTag("r1-next").performScrollTo().performClick()
        rule.onNodeWithTag("r1-stage-title")
            .assertTextEquals("Stage 2/4 — Collecting network")
        rule.onNodeWithTag("r1-process-conjugation")
            .performScrollTo().performClick()
        rule.onNodeWithTag("r1-stage-title")
            .assertTextEquals("Stage 1/7 — Compatible cells pair")  // conjugation model has 7 stages
        rule.onNodeWithTag("r1-tab-anatomy").performScrollTo().performClick()
        // The legacy organ-selection atlas is unreachable (KNOWN_GAPS.md); check the
        // plate gallery students can actually open.
        rule.onNodeWithTag("r2-plate-bitmap-paramecium-oral-apparatus", useUnmergedTree = true)
            .performScrollTo().assertExists()
        rule.onNodeWithTag("r1-tab-listen").performScrollTo().performClick()
        rule.onNodeWithTag("r1-narrate-macronucleus")
            .performScrollTo().assertHasClickAction()
    }

    @Ignore("Known gap (owner decision 2026-11-01): organ-level atlas selection is unreachable; see native-app/KNOWN_GAPS.md")

    @Test
    fun r11VacuoleAtlasSeparatesFoodFromOsmoregulationAndOpensNativeCycle() {
        englishHome()
        rule.onNodeWithTag("r1-home-open-paramecium").performClick()
        waitFor("r1-paramecium-lab")
        rule.onNodeWithTag("r1-tab-anatomy").performScrollTo().performClick()
        rule.onNodeWithTag("r1-atlas-vacuole").performScrollTo().performClick()
        rule.onNodeWithTag("r11-vacuole-heading", useUnmergedTree = true)
            .performScrollTo().assertExists()
        rule.onNodeWithTag("r11-vacuole-canvas", useUnmergedTree = true)
            .performScrollTo().assertExists()
        val food = rule.onNodeWithTag(
            "r11-vacuole-select-digestive-food-vacuole", useUnmergedTree = true)
        food.performScrollTo().assertHeightIsAtLeast(48.dp)
        food.performClick()
        rule.onNodeWithTag("r11-vacuole-selected", useUnmergedTree = true)
            .performScrollTo().assertTextEquals(
                "Selected landmark: Digestive food vacuole")
        rule.onNodeWithTag(
            "r11-vacuole-select-posterior-contractile-complex",
            useUnmergedTree = true).performScrollTo().performClick()
        rule.onNodeWithTag("r11-vacuole-selected", useUnmergedTree = true)
            .performScrollTo().assertTextEquals(
                "Selected landmark: Posterior contractile-vacuole complex")
        rule.onNodeWithTag("r11-vacuole-review-warning", useUnmergedTree = true)
            .performScrollTo().assertExists()
        rule.onNodeWithTag("r11-open-osmoregulation", useUnmergedTree = true)
            .performScrollTo().performClick()
        rule.onNodeWithTag("r1-stage-title", useUnmergedTree = true)
            .performScrollTo().assertTextEquals("Stage 1/4 — Water enters")
        rule.onNodeWithTag("r1-simulation-caution", useUnmergedTree = true)
            .performScrollTo().assertExists()
    }

    @Ignore("Known gap (owner decision 2026-11-01): organ-level atlas selection is unreachable; see native-app/KNOWN_GAPS.md")

    @Test
    fun r12ContractileComplexMechanismUsesNativeAccessibleFourStageReview() {
        englishHome()
        rule.onNodeWithTag("r1-home-open-paramecium").performClick()
        waitFor("r1-paramecium-lab")
        rule.onNodeWithTag("r1-tab-anatomy").performScrollTo().performClick()
        rule.onNodeWithTag("r1-atlas-vacuole").performScrollTo().performClick()
        rule.onNodeWithTag("r12-cvc-mechanism-title", useUnmergedTree = true)
            .performScrollTo().assertTextEquals(
                "Contractile-vacuole complex: mechanism close-up")
        rule.onNodeWithTag("r12-cvc-mechanism-canvas", useUnmergedTree = true)
            .performScrollTo().assertExists()
        rule.onNodeWithTag("r12-cvc-stage-title", useUnmergedTree = true)
            .performScrollTo().assertTextEquals("Stage 1/4 — Water enters")
        val next = rule.onNodeWithTag("r12-cvc-next", useUnmergedTree = true)
        next.performScrollTo().assertHeightIsAtLeast(48.dp)
        val stageNames = listOf(
            "Stage 2/4 — Collecting network",
            "Stage 3/4 — Vacuole fills",
            "Stage 4/4 — Fluid is discharged",
            "Stage 1/4 — Water enters",
        )
        for (expected in stageNames) {
            next.performScrollTo().performClick()
            rule.onNodeWithTag("r12-cvc-stage-title", useUnmergedTree = true)
                .performScrollTo().assertTextEquals(expected)
        }
        rule.onNodeWithTag("r12-cvc-structure-legend", useUnmergedTree = true)
            .performScrollTo().assertExists()
        rule.onNodeWithTag("r12-cvc-review-warning", useUnmergedTree = true)
            .performScrollTo().assertExists()
        rule.onNodeWithTag("r12-cvc-speak", useUnmergedTree = true)
            .performScrollTo().assertHasClickAction()
    }

    @Test
    fun primaryActionSemanticsAndTouchTargetsArePresent() {
        englishHome()
        for (tag in listOf("language-english", "language-tamil", "unit-1", "a5-assessment")) {
            if (tag == "a5-assessment") rule.onNodeWithTag("native-home").performScrollToIndex(6)
            rule.onNodeWithTag(tag)
                .assertHasClickAction()
                .assertWidthIsAtLeast(48.dp)
                .assertHeightIsAtLeast(48.dp)
        }
    }
}

/** Execute this class separately on API 34 with system font_scale=2.0. */
@RunWith(AndroidJUnit4::class)
class NativeTamilLargeTextAcceptanceTest {
    @get:Rule val rule = createAndroidComposeRule<MainActivity>()

    @Before fun resetLearningPosition() {
        runBlocking {
            NativeLearningRepository(InstrumentationRegistry.getInstrumentation().targetContext)
                .save(NativeLearningState())
        }
        rule.activityRule.scenario.recreate()
    }

    @Test
    fun anatomyGalleryIsReadableAtTamilTwoHundredPercent() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        assertTrue(context.resources.configuration.fontScale >= 1.95f)
        rule.waitUntil(timeoutMillis = 20_000L) {
            rule.onAllNodesWithTag("native-home").fetchSemanticsNodes().isNotEmpty()
        }
        rule.onNodeWithTag("language-tamil").performClick()
        rule.waitUntil(timeoutMillis = 10_000L) { rule.homeUnitOneShows(tamil = true) }
        rule.onNodeWithTag("r1-home-open-paramecium").performScrollTo().performClick()
        rule.waitUntil(timeoutMillis = 20_000L) {
            rule.onAllNodesWithTag("r1-paramecium-lab").fetchSemanticsNodes().isNotEmpty()
        }
        rule.onNodeWithTag("r1-tab-anatomy").performScrollTo().performClick()
        rule.onNodeWithTag("r2-plate-bitmap-paramecium-oral-apparatus", useUnmergedTree = true)
            .performScrollTo().assertExists()
        rule.onNodeWithTag("r2-plate-expand-paramecium-oral-apparatus", useUnmergedTree = true)
            .assertExists().assertHeightIsAtLeast(48.dp)
        rule.waitForIdle()
        // Real Tamil 200% emulator evidence (runner requires this file).
        saveGalleryEvidenceScreenshot(
            "paramecium-gallery-tamil200.png",
            rule.onNodeWithTag("r2-plate-bitmap-paramecium-oral-apparatus", useUnmergedTree = true)
                .fetchSemanticsNode().boundsInRoot,
            "type_studies/paramecium/oral-apparatus.png",
        )
    }

    @Ignore("Known gap (owner decision 2026-11-01): organ-level atlas selection is unreachable; see native-app/KNOWN_GAPS.md")

    @Test
    fun r14NativeReaderRetainsReadableTamilHeadingsAtActualTwoHundredPercent() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        assertTrue(context.resources.configuration.fontScale >= 1.95f)
        rule.waitUntil(timeoutMillis = 20_000L) {
            rule.onAllNodesWithTag("native-home").fetchSemanticsNodes().isNotEmpty()
        }
        rule.onNodeWithTag("language-tamil").performClick()
        rule.onNodeWithTag("r1-home-open-paramecium")
            .performScrollTo().performClick()
        rule.waitUntil(timeoutMillis = 20_000L) {
            rule.onAllNodesWithTag("r1-paramecium-lab").fetchSemanticsNodes().isNotEmpty()
        }
        rule.onNodeWithTag("r14-jump-to-pencil-plate", useUnmergedTree = true)
            .performScrollTo().assertHeightIsAtLeast(48.dp).performClick()
        rule.waitForIdle()
        val tamilPlate = rule.onNodeWithTag("n23b-external-canvas", useUnmergedTree = true)
            .fetchSemanticsNode().boundsInRoot
        val tamilViewport = rule.onNodeWithTag("r1-paramecium-lab")
            .fetchSemanticsNode().boundsInRoot
        assertTrue("Tamil 200% pencil atlas must fit the actual viewport",
            tamilPlate.top >= tamilViewport.top - 1f &&
            tamilPlate.bottom <= tamilViewport.bottom + 1f)
        saveN23bReviewScreenshot("r14-textbook-tamil200.png")
        rule.onNodeWithTag("r14-contents-toggle", useUnmergedTree = true)
            .performScrollTo().assertHeightIsAtLeast(48.dp).performClick()
        rule.onNodeWithTag("r14-open-section-osmoregulation", useUnmergedTree = true)
            .performScrollTo().performClick()
        rule.onNodeWithTag("r14-section-heading", useUnmergedTree = true)
            .performScrollTo().assertTextEquals("சுருங்கும் நுண்குமிழ்களும் நீர்ச்சமநிலையும்")
        rule.onNodeWithTag("r14-pencil-vacuole", useUnmergedTree = true)
            .performScrollTo().assertExists()
        // Native feeding model is also usable with the real Android 200% scale.
        rule.onNodeWithTag("r14-contents-toggle", useUnmergedTree = true)
            .performScrollTo().performClick()
        rule.onNodeWithTag("r14-open-section-feeding-and-digestion", useUnmergedTree = true)
            .performScrollTo().performClick()
        rule.onNodeWithTag("r15-feeding-stage-egestion", useUnmergedTree = true)
            .performScrollTo().assertHeightIsAtLeast(48.dp).performClick()
        rule.onNodeWithTag("r15-feeding-explanation", useUnmergedTree = true)
            .performScrollTo().assertTextEquals(
                com.gasczoology.invertebratelab.data.ParameciumLearningEngine
                    .simulation(com.gasczoology.invertebratelab.data.ParameciumProcess.FEEDING)
                    .stages.last().explanation.tamil
            )
        // Use the SAME user-visible button provided for large-font readers.
        // Merely finding a Canvas semantics node did not scroll the actual
        // rendered diagram into the viewport on the previous failing commit.
        rule.onNodeWithTag("r15-view-feeding-diagram", useUnmergedTree = true)
            .performScrollTo().assertHeightIsAtLeast(48.dp).performClick()
        rule.waitForIdle()
        val feedingBounds = rule.onNodeWithTag("r15-feeding-pathway-canvas",
            useUnmergedTree = true).fetchSemanticsNode().boundsInRoot
        val feedingViewport = rule.onRoot().fetchSemanticsNode().boundsInRoot
        assertTrue("R1.5 Tamil 200% canvas must occupy ACTUAL visible screen: " +
            "canvas=$feedingBounds root=$feedingViewport",
            feedingBounds.height > 0f &&
            feedingBounds.top >= feedingViewport.top - 1f &&
            feedingBounds.bottom <= feedingViewport.bottom + 1f)
        saveN23bReviewScreenshot("r15-feeding-tamil200.png")
        rule.onNodeWithTag("r14-from-study-simulation", useUnmergedTree = true)
            .performScrollTo().assertHeightIsAtLeast(48.dp).performClick()
        rule.onNodeWithTag("r1-stage-title", useUnmergedTree = true)
            .performScrollTo().assertTextEquals("நிலை 1/4 — நீர் உள்ளேறுதல்")
    }

    @Test
    fun tamilNavigationAndFullAnswerRemainReachableAtTwoHundredPercent() {
        val units = ValidatedA5AssetRepository(
            InstrumentationRegistry.getInstrumentation().targetContext
        ).load()
        val first = units.first().chapters.first().a5Questions.first()
        val fontScale = InstrumentationRegistry.getInstrumentation().targetContext
            .resources.configuration.fontScale
        assertTrue("Expected actual 200% font scale, found " + fontScale,
            fontScale >= 1.95f)

        rule.waitUntil(timeoutMillis = 20_000L) {
            rule.onAllNodesWithTag("native-home").fetchSemanticsNodes().isNotEmpty()
        }
        rule.onNodeWithTag("language-tamil").performClick()
        rule.waitUntil(timeoutMillis = 10_000L) {
            rule.homeUnitOneShows(tamil = true)
        }
        rule.onNodeWithTag("native-home").performScrollToIndex(1)
        rule.onNodeWithTag("unit-1").performClick()
        val firstChapter = units.first().chapters.first()
        // The first chapter opens in the book reader; its questions sit far down a lazy list,
        // so scroll to each node instead of assuming it is already composed.
        val container = readerContainerTag(firstChapter)
        rule.onNodeWithTag("native-unit-1").performScrollToIndex(1)
        rule.onNodeWithTag("chapter-" + firstChapter.id).performClick()
        rule.waitUntil(timeoutMillis = 20_000L) {
            rule.onAllNodesWithTag(container).fetchSemanticsNodes().isNotEmpty()
        }
        val questionTag = readerQuestionTag(firstChapter, first)
        rule.onNodeWithTag(container).performScrollToNode(hasTestTag(questionTag))
        rule.onNodeWithTag(questionTag).assertTextEquals(first.question.tamil)
        rule.onNodeWithTag("a5-toggle-" + first.id).performScrollTo().performClick()
        // DataStore state publication is asynchronous: wait for the revealed answer first.
        rule.waitUntil(timeoutMillis = 10_000L) {
            rule.onAllNodesWithTag("a5-point-" + first.id + "-1", useUnmergedTree = true)
                .fetchSemanticsNodes().isNotEmpty()
        }
        for ((index, point) in first.answerPoints.withIndex()) {
            val pointTag = "a5-point-" + first.id + "-" + (index + 1)
            rule.onNodeWithTag(container).performScrollToNode(hasTestTag(pointTag))
            rule.onNodeWithTag(pointTag)
                .assertTextEquals((index + 1).toString() + ". " + point.tamil)
        }
    }
    @Ignore("Known gap (owner decision 2026-11-01): organ-level atlas selection is unreachable; see native-app/KNOWN_GAPS.md")
    @Test
    fun tamilTwoHundredPercentCanvasControlsRemainReachable() {
        val targetContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertTrue(targetContext.resources.configuration.fontScale >= 1.95f)
        rule.waitUntil(timeoutMillis = 20_000L) {
            rule.onAllNodesWithTag("native-home").fetchSemanticsNodes().isNotEmpty()
        }
        rule.onNodeWithTag("language-tamil").performClick()
        rule.waitUntil(timeoutMillis = 10_000L) {
            rule.homeUnitOneShows(tamil = true)
        }
        rule.onNodeWithTag("native-home").performScrollToIndex(1)
        rule.onNodeWithTag("unit-1").performClick()
        rule.waitUntil(timeoutMillis = 20_000L) {
            rule.onAllNodesWithTag("native-unit-1").fetchSemanticsNodes().isNotEmpty()
        }
        rule.onNodeWithTag("native-unit-1").performScrollToIndex(3)
        rule.onNodeWithTag("chapter-u1-paramecium").performClick()
        rule.waitUntil(timeoutMillis = 20_000L) {
            rule.onAllNodesWithTag("native-chapter-u1-paramecium")
                .fetchSemanticsNodes().isNotEmpty()
        }
        rule.onNodeWithTag("native-chapter-u1-paramecium").performScrollToIndex(5 + NativeLessonDrafts.paramecium.sections.size)
        rule.waitUntil(timeoutMillis = 20_000L) {
            rule.onAllNodesWithTag("n23b-external-preview")
                .fetchSemanticsNodes().isNotEmpty()
        }
        rule.onNodeWithTag("n23b-orientation", useUnmergedTree = true)
            .assertTextEquals(
                "முன்புறம் (வட்டம்) ←   பின்புறம் (கூர்மை) →   வாய்ப்புறம்: கீழ்ப்பக்கம்"
            )
        val target = rule.onNodeWithTag("n23b-select-cytoproct", useUnmergedTree = true)
        target.performScrollTo()
        target.assertHeightIsAtLeast(48.dp)
        target.performClick()
        rule.onNodeWithTag("n23b-selected-label", useUnmergedTree = true)
            .performScrollTo()
        rule.onNodeWithTag("n23b-selected-label")
            .assertTextEquals("தேர்ந்தெடுத்த உறுப்பு: செல் கழிவுத்துளை")
        rule.onNodeWithTag("n23c2-cytoproct-evidence-limit", useUnmergedTree = true)
            .assertExists()
        // Use the actual bilingual return-to-atlas action; it calls
        // LazyListState.scrollToItem() and snaps the oversized last item to
        // its beginning even at genuine Tamil 200% system font scale.
        val chapterScroll = rule.onNodeWithTag("native-chapter-u1-paramecium")
        val jump = rule.onNodeWithTag("r13-jump-to-pencil-atlas",
            useUnmergedTree = true)
        jump.performScrollTo().assertHeightIsAtLeast(48.dp).performClick()
        rule.waitForIdle()
        val pencilCanvas = rule.onNodeWithTag("n23b-external-canvas", useUnmergedTree = true)
        val canvasBounds = pencilCanvas.fetchSemanticsNode().boundsInRoot
        val scrollerBounds = chapterScroll.fetchSemanticsNode().boundsInRoot
        assertTrue("Pencil Canvas must fit within the visible Tamil 200% viewport",
            canvasBounds.top >= scrollerBounds.top - 1f &&
            canvasBounds.bottom <= scrollerBounds.bottom + 1f)
        saveN23bReviewScreenshot("paramecium-tamil200-cytoproct.png")
        // Assert the new geometry disclaimer can also be reached in Tamil at 200%.
        val oral = rule.onNodeWithTag("n23b-select-oral-groove", useUnmergedTree = true)
        oral.performScrollTo().performClick()
        rule.onNodeWithTag("n23c3-oral-geometry-limit", useUnmergedTree = true)
            .performScrollTo().assertExists()
        rule.onNodeWithTag("n23d-review-summary", useUnmergedTree = true)
            .performScrollTo()
            .assertTextEquals("N2.3D1 மதிப்பாய்வு நிலை — உயிரியல்: 0/5; தமிழ்: 0/5; நேரடி சாதனச் சோதனை: நிலுவை")
        rule.onNodeWithTag("n23d-source-support", useUnmergedTree = true)
            .performScrollTo().assertExists()
        rule.onNodeWithTag("n23e2-source-access", useUnmergedTree = true)
            .performScrollTo().assertExists()
        rule.onNodeWithTag("n23e2-image-rights", useUnmergedTree = true)
            .performScrollTo().assertExists()
    }

    @Ignore("Known gap (owner decision 2026-11-01): organ-level atlas selection is unreachable; see native-app/KNOWN_GAPS.md")

    @Test
    fun r12MechanismControlsAndTamilTerminologyWorkAtTwoHundredPercent() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        assertTrue(context.resources.configuration.fontScale >= 1.95f)
        rule.waitUntil(timeoutMillis = 20_000L) {
            rule.onAllNodesWithTag("native-home").fetchSemanticsNodes().isNotEmpty()
        }
        rule.onNodeWithTag("language-tamil").performClick()
        rule.onNodeWithTag("r1-home-open-paramecium").performScrollTo().performClick()
        rule.waitUntil(timeoutMillis = 20_000L) {
            rule.onAllNodesWithTag("r1-paramecium-lab")
                .fetchSemanticsNodes().isNotEmpty()
        }
        rule.onNodeWithTag("r1-tab-anatomy").performScrollTo().performClick()
        rule.onNodeWithTag("r1-atlas-vacuole").performScrollTo().performClick()
        rule.onNodeWithTag("r12-cvc-mechanism-title", useUnmergedTree = true)
            .performScrollTo().assertTextEquals(
                "சுருங்கும் நுண்குமிழ் தொகுதி: செயல்முறை விரிவுக் காட்சி")
        rule.onNodeWithTag("r12-cvc-stage-title", useUnmergedTree = true)
            .performScrollTo().assertTextEquals("நிலை 1/4 — நீர் உள்ளேறுதல்")
        rule.onNodeWithTag("r12-cvc-next", useUnmergedTree = true)
            .performScrollTo().assertHeightIsAtLeast(48.dp)
        rule.onNodeWithTag("r12-cvc-next", useUnmergedTree = true)
            .performClick()
        rule.onNodeWithTag("r12-cvc-stage-title", useUnmergedTree = true)
            .performScrollTo().assertTextEquals("நிலை 2/4 — சேகரிக்கும் அமைப்பு")
        rule.onNodeWithTag("r12-cvc-structure-legend", useUnmergedTree = true)
            .performScrollTo().assertExists()
        rule.onNodeWithTag("r12-cvc-review-warning", useUnmergedTree = true)
            .performScrollTo().assertExists()
    }

    @Test
    fun parameciumChapterDraftNoticeAndQuestionsAreReachableAtActualTamil200Percent() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        assertTrue(context.resources.configuration.fontScale >= 1.95f)
        rule.waitUntil(timeoutMillis = 20_000L) {
            rule.onAllNodesWithTag("native-home").fetchSemanticsNodes().isNotEmpty()
        }
        rule.onNodeWithTag("language-tamil").performClick()
        rule.waitUntil(timeoutMillis = 10_000L) {
            rule.homeUnitOneShows(tamil = true)
        }
        rule.onNodeWithTag("native-home").performScrollToIndex(1)
        rule.onNodeWithTag("unit-1").performClick()
        rule.waitUntil(timeoutMillis = 20_000L) {
            rule.onAllNodesWithTag("native-unit-1").fetchSemanticsNodes().isNotEmpty()
        }
        // The old draft-lesson sections (n22-section-*) are no longer reachable: the Paramecium
        // chapter opens in the book reader. Check what students actually get at 200% Tamil:
        // the draft notice at the top and the validated questions at the bottom.
        rule.onNodeWithTag("native-unit-1").performScrollToIndex(3)
        rule.onNodeWithTag("chapter-u1-paramecium").performClick()
        val chapter = ValidatedA5AssetRepository(context).load()
            .flatMap { it.chapters }.single { it.id == "u1-paramecium" }
        val container = readerContainerTag(chapter)
        rule.waitUntil(timeoutMillis = 20_000L) {
            rule.onAllNodesWithTag(container).fetchSemanticsNodes().isNotEmpty()
        }
        rule.onNodeWithTag(container).performScrollToIndex(0)
        rule.onNodeWithTag("chapter-draft-notice", useUnmergedTree = true).assertExists()
        val last = chapter.a5Questions.last()
        rule.onNodeWithTag(container).performScrollToNode(hasTestTag(readerQuestionTag(chapter, last)))
        rule.onNodeWithTag(readerQuestionTag(chapter, last)).assertExists()
    }
}
