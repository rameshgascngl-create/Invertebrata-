package com.gasczoology.invertebratelab

import androidx.compose.ui.test.assertExists
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performScrollToNode
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.gasczoology.invertebratelab.data.AppLanguage
import com.gasczoology.invertebratelab.data.LanguagePreferenceRepository
import com.gasczoology.invertebratelab.data.NativeLearningRepository
import com.gasczoology.invertebratelab.data.NativeLearningState
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NativePrivacyPolicyTest {
    @get:Rule val rule = createAndroidComposeRule<MainActivity>()

    @Before fun reset() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        runBlocking {
            NativeLearningRepository(context).save(NativeLearningState())
            LanguagePreferenceRepository(context).setLanguage(AppLanguage.ENGLISH)
        }
        rule.activityRule.scenario.recreate()
    }

    @Test fun englishAndTamilPolicyAccessibleOfflineFromHome() {
        rule.onNodeWithTag("native-home").performScrollToNode(hasTestTag("open-privacy-policy"))
        rule.onNodeWithTag("open-privacy-policy").performClick()
        rule.onNodeWithTag("privacy-policy-title").assertTextContains("Privacy Policy")
        rule.onNodeWithTag("privacy-policy-screen").performScrollToNode(
            hasTestTag("privacy-policy-contact-1"))
        rule.onNodeWithTag("privacy-policy-contact-1").assertTextContains("rameshgascngl@gmail.com")
        rule.onNodeWithTag("privacy-policy-back").performClick()
        rule.onNodeWithTag("language-tamil").performClick()
        rule.onNodeWithTag("native-home").performScrollToNode(hasTestTag("open-privacy-policy"))
        rule.onNodeWithTag("open-privacy-policy").performClick()
        rule.onNodeWithTag("privacy-policy-title").assertTextContains("தனியுரிமைக் கொள்கை")
        rule.onNodeWithTag("privacy-policy-screen").performScrollToNode(
            hasTestTag("privacy-policy-contact-1"))
        rule.onNodeWithTag("privacy-policy-contact-1").assertTextContains("rameshgascngl@gmail.com")
    }
}
