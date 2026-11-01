package com.gasczoology.invertebratelab

import androidx.compose.ui.test.assertHeightIsAtLeast
import androidx.compose.ui.test.assertDoesNotExist
import androidx.compose.ui.test.assertExists
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Anatomy tab acceptance for what students can ACTUALLY reach today: the Paramecium
 * plate gallery (RequiredTypeStudyPlates) and its full-screen zoom viewer. Organ-level
 * touch selection is a documented known gap (native-app/KNOWN_GAPS.md), not tested here.
 */
@RunWith(AndroidJUnit4::class)
class NativeParameciumAnatomyGalleryTest {
    @get:Rule val rule = createAndroidComposeRule<MainActivity>()

    private fun openAnatomy() {
        rule.waitUntil(timeoutMillis = 20_000L) {
            rule.onAllNodesWithTag("native-home").fetchSemanticsNodes().isNotEmpty()
        }
        rule.onNodeWithTag("language-english").performClick()
        rule.waitUntil(timeoutMillis = 10_000L) { rule.homeUnitOneShows(tamil = false) }
        rule.onNodeWithTag("r1-home-open-paramecium").performScrollTo().performClick()
        rule.waitUntil(timeoutMillis = 20_000L) {
            rule.onAllNodesWithTag("r1-paramecium-lab").fetchSemanticsNodes().isNotEmpty()
        }
        rule.onNodeWithTag("r1-tab-anatomy").performScrollTo().performClick()
    }

    @Test
    fun oralApparatusAndContractileVacuolePlatesAreReachableFromAnatomy() {
        openAnatomy()
        for (plateId in listOf("oral-apparatus", "contractile-vacuoles")) {
            rule.onNodeWithTag("r2-plate-bitmap-paramecium-$plateId", useUnmergedTree = true)
                .performScrollTo().assertExists()
            rule.onNodeWithTag("r2-plate-missing-paramecium-$plateId", useUnmergedTree = true)
                .assertDoesNotExist()
        }
    }

    @Test
    fun plateOpensFullScreenViewerWithZoomControls() {
        openAnatomy()
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
        rule.onNodeWithTag("r2-zoom-in", useUnmergedTree = true).performClick()
        rule.onNodeWithTag("r2-zoom-reset", useUnmergedTree = true).performClick()
        rule.onNodeWithTag("r2-fullscreen-close", useUnmergedTree = true).performClick()
        rule.waitUntil(timeoutMillis = 10_000L) {
            rule.onAllNodesWithTag("r2-fullscreen-dialog", useUnmergedTree = true)
                .fetchSemanticsNodes().isEmpty()
        }
    }
}
