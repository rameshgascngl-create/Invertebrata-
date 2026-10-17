package com.gasczoology.invertebratelab

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.gasczoology.invertebratelab.data.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NativeUnitIVNereisTest {
    @get:Rule val rule=createAndroidComposeRule<MainActivity>()

    @Before fun resetState() {
        runBlocking {
            val ctx=androidx.test.platform.app.InstrumentationRegistry.getInstrumentation().targetContext
            NativeLearningRepository(ctx).save(NativeLearningState())
            LanguagePreferenceRepository(ctx).setLanguage(AppLanguage.ENGLISH)
        }
        rule.activityRule.scenario.recreate()
        rule.waitUntil(20_000) {
            rule.onAllNodesWithTag("native-home").fetchSemanticsNodes().isNotEmpty()
        }
    }
    private fun enter(tamil:Boolean) {
        if(tamil)rule.onNodeWithTag("language-tamil").performClick()
        rule.onNodeWithTag("unit-4").performScrollTo().performClick()
        rule.waitUntil(20_000){
            rule.onAllNodesWithTag("native-unit-4").fetchSemanticsNodes().isNotEmpty()
        }
        rule.onNodeWithTag("native-unit-4")
            .performScrollToNode(hasTestTag("chapter-u4-nereis"))
        rule.onNodeWithTag("chapter-u4-nereis").performClick()
        rule.waitUntil(20_000){
            rule.onAllNodesWithTag("native-chapter-u4-nereis").fetchSemanticsNodes().isNotEmpty()
        }
    }
    private fun find(tag:String) {
        rule.onNodeWithTag("native-chapter-u4-nereis")
            .performScrollToNode(hasTestTag(tag))
    }
    // Check the ORIGINAL APK PNG pixels against actual SurfaceFlinger output.
    // The previous test expected the obsolete Canvas cream-paper colour and
    // rejected the new white-background PNG despite correctly rendered figures.
    // This geometry-sensitive test still rejects blank/stale chapter-menu frames.
    private val expectedNereisPngs=arrayOf(
        "nereis/01_external_morphology.png",
        "nereis/02_enlarged_head.png",
        "nereis/03_biramous_parapodium.png",
        "nereis/04_digestive_system.png",
        "nereis/05_circulatory_system.png",
        "nereis/06_nervous_system.png",
        "nereis/07_excretory_system.png",
        "nereis/08_trochophore_larva.png"
    )
    private fun rasterInkAgreement(
        screenshot:android.graphics.Bitmap,
        master:android.graphics.Bitmap,
        bounds:androidx.compose.ui.geometry.Rect
    ):Pair<Int,Int> {
        var sourceInk=0
        var matchedInk=0
        for(ys in 65 until master.height-80 step 31) {
            for(xs in 65 until master.width-65 step 31) {
                val sourcePixel=master.getPixel(xs,ys)
                val darkness=(android.graphics.Color.red(sourcePixel)+
                    android.graphics.Color.green(sourcePixel)+
                    android.graphics.Color.blue(sourcePixel))/3
                if(darkness>=150)continue
                sourceInk++
                val x=(bounds.left+(xs+.5f)*bounds.width/master.width).toInt()
                val y=(bounds.top+(ys+.5f)*bounds.height/master.height).toInt()
                var found=false
                for(dy in -5..5) {
                    for(dx in -5..5) {
                        val px=x+dx
                        val py=y+dy
                        if(px !in 0 until screenshot.width || py !in 0 until screenshot.height)
                            continue
                        val observed=screenshot.getPixel(px,py)
                        val luma=(android.graphics.Color.red(observed)+
                            android.graphics.Color.green(observed)+
                            android.graphics.Color.blue(observed))/3
                        if(luma<205) { found=true;break }
                    }
                    if(found)break
                }
                if(found)matchedInk++
            }
        }
        return Pair(sourceInk,matchedInk)
    }
    // Small raster fingerprint detects screenshots taken before the changed
    // Compose frame has actually reached SurfaceFlinger.
    private fun rasterSignature(bitmap:android.graphics.Bitmap):Long {
        var state=1469598103934665603L
        for(y in 120 until bitmap.height-100 step 41) {
            for(x in 45 until bitmap.width-45 step 47) {
                state=(state xor bitmap.getPixel(x,y).toLong())*1099511628211L
            }
        }
        return state
    }
    private fun screen(
        name:String,
        requirePlate:Boolean=false,
        changedSince:Long?=null,
        plateIndex:Int?=null,
        plateBounds:androidx.compose.ui.geometry.Rect?=null
    ):Long {
        rule.waitForIdle()
        val scale=androidx.test.platform.app.InstrumentationRegistry.getInstrumentation()
            .targetContext.resources.configuration.fontScale
        val suffix=if(scale>=1.95f)"200" else "100"
        val nameOnDisk="/sdcard/Download/unit4-qa/unit4-"+suffix+"-"+name+".png"
        val ui=androidx.test.platform.app.InstrumentationRegistry.getInstrumentation().uiAutomation
        require(!requirePlate || (plateIndex!=null && plateIndex in 1..8 && plateBounds!=null))
        val source=if(requirePlate) {
            val ctx=androidx.test.platform.app.InstrumentationRegistry
                .getInstrumentation().targetContext
            ctx.assets.open(expectedNereisPngs[plateIndex!!-1]).use {
                android.graphics.BitmapFactory.decodeStream(it)
            }
        } else null
        org.junit.Assert.assertTrue(
            "Nereis master missing before screenshot test",!requirePlate || source!=null
        )
        var bytes=byteArrayOf()
        var sourceInk=0
        var matchedInk=0
        var actualSignature=0L
        var decoded:android.graphics.Bitmap?=null
        for(attempt in 1..8) {
            android.os.ParcelFileDescriptor.AutoCloseInputStream(
                ui.executeShellCommand("screencap -p "+nameOnDisk)).use {it.readBytes()}
            bytes=android.os.ParcelFileDescriptor.AutoCloseInputStream(
                ui.executeShellCommand("cat "+nameOnDisk)).use {it.readBytes()}
            decoded=android.graphics.BitmapFactory.decodeByteArray(bytes,0,bytes.size)
            if(decoded!=null && source!=null && plateBounds!=null) {
                val agreement=rasterInkAgreement(decoded,source,plateBounds)
                sourceInk=agreement.first
                matchedInk=agreement.second
            }
            actualSignature=if(decoded==null) 0L else rasterSignature(decoded)
            val validPlate=!requirePlate || (sourceInk>=60 && matchedInk*100>=sourceInk*55)
            val validFrame=changedSince==null || actualSignature!=changedSince
            if(validPlate && validFrame) break
            // SurfaceFlinger screenshots can precede a newly-idle Compose frame.
            // Retry only until source-image ink appears in the correct position.
            android.os.SystemClock.sleep(200)
        }
        org.junit.Assert.assertTrue("Nereis screenshot missing",bytes.size>12000)
        org.junit.Assert.assertNotNull("Nereis screenshot invalid PNG",decoded)
        if(requirePlate) org.junit.Assert.assertTrue(
            "Nereis drawing raster missing from $name; actual PNG ink match=$matchedInk/$sourceInk",
            sourceInk>=60 && matchedInk*100>=sourceInk*55
        )
        if(changedSince!=null) org.junit.Assert.assertTrue(
            "Nereis screenshot did not advance to final term: $name",
            actualSignature!=changedSince
        )
        return actualSignature
    }

    private fun capturePlate(locale:String,index:Int) {
        val serial=index.toString().padStart(2,'0')
        val drawing="u44-nereis-pencil-style-drawing-"+index
        find(drawing)
        val node=rule.onNodeWithTag(drawing).assertIsDisplayed()
        val box=node.fetchSemanticsNode().boundsInWindow
        val content=rule.activity.window.decorView
        // The whole figure must fit inside the screenshot; a mere 1 px
        // intersection does not count as evidence of a usable plate.
        org.junit.Assert.assertTrue(
            "Nereis plate $index drawing clipped: $box / ${content.width}x${content.height}",
            box.width>100f && box.height>100f &&
                box.left>=-2f && box.top>=-2f &&
                box.right<=content.width+2f &&
                box.bottom<=content.height+2f
        )
        screen("nereis-$locale-plate-$serial",requirePlate=true,
            plateIndex=index,plateBounds=box)
    }

    private val plateLabelCounts=intArrayOf(6,6,6,6,4,5,4,5)

    // LazyColumn.scrollToNode only brings the containing Card into view;
    // it does NOT necessarily scroll to a descendant term in a tall Card.
    private fun scrollUntilLegendTermVisible(tag:String):Boolean {
        val list=rule.onNodeWithTag("native-chapter-u4-nereis")
        val h=rule.activity.window.decorView.height.toFloat()
        var scrolled=false
        for(attempt in 0..9) {
            val target=rule.onNodeWithTag(tag).fetchSemanticsNode().boundsInWindow
            if(target.top>=130f && target.bottom<=h-170f) return scrolled
            if(target.top<130f) {
                list.performTouchInput {
                    swipeDown(startY=h*.48f,endY=h*.70f)
                }
            } else {
                list.performTouchInput {
                    swipeUp(startY=h*.70f,endY=h*.48f)
                }
            }
            scrolled=true
            rule.waitForIdle()
        }
        val target=rule.onNodeWithTag(tag).fetchSemanticsNode().boundsInWindow
        org.junit.Assert.assertTrue(
            "Nereis final legend term not in viewport: $tag bounds=$target screen=$h",
            target.top>=130f && target.bottom<=h-170f
        )
        return scrolled
    }

    @Test fun eightStaticNoninteractiveNereisPlatesAndOriginalA5() {
        enter(false)
        find("u44-nereis-introduction")
        rule.onNodeWithTag("u44-nereis-editorial-disclosure").assertExists()
        for(i in 1..8) {
            capturePlate("english",i)
            find("u44-nereis-image-provenance-"+i)
            rule.onNodeWithTag("u44-nereis-image-provenance-"+i).assertExists()
            find("u44-nereis-plate-"+i+"-term-0")
            rule.onNodeWithTag("u44-nereis-plate-"+i+"-term-0").assertIsDisplayed()
        }
        find("u44-nereis-lessons-heading")
        rule.onNodeWithTag("u44-nereis-lessons-heading")
            .assertTextContains("14 EXPLANATORY",substring=true)
        find("a5-card-u4-nereis-A5-1")
        rule.onNodeWithTag("a5-card-u4-nereis-A5-1").assertExists()
    }

    @Test fun tamilPlateLegendsAndSubstantiveNereisBiologyAtLargeFonts() {
        enter(true)
        for(i in 1..8) {
            capturePlate("tamil",i)
            val first="u44-nereis-plate-"+i+"-term-0"
            val last="u44-nereis-plate-"+i+"-term-"+(plateLabelCounts[i-1]-1)
            val serial=i.toString().padStart(2,'0')
            find(first)
            rule.onNodeWithTag(first).assertIsDisplayed()
            val startSignature=screen("nereis-tamil-legend-start-"+serial)
            find(last)
            val scrolled=scrollUntilLegendTermVisible(last)
            rule.onNodeWithTag(last).assertIsDisplayed()
            screen(
                "nereis-tamil-legend-end-"+serial,
                changedSince=if(scrolled) startSignature else null
            )
            for(j in 0 until plateLabelCounts[i-1]) {
                rule.onNodeWithTag("u44-nereis-plate-"+i+"-term-"+j).assertExists()
            }
        }
        find("u44-nereis-plate-7-term-0")
        rule.onNodeWithTag("u44-nereis-plate-7-term-0")
            .assertTextContains("நெப்ரோஸ்டோம்",substring=true)
        find("u44-nereis-plate-8-term-1")
        rule.onNodeWithTag("u44-nereis-plate-8-term-1")
            .assertTextContains("புரோட்டோட்ரோக்",substring=true)
        find("u44-nereis-section-larvae")
        rule.onNodeWithTag("u44-nereis-section-larvae").assertExists()
        find("u44-nereis-section-parapodia")
        rule.onNodeWithTag("u44-nereis-section-parapodia").assertExists()
        find("u44-nereis-static-plate-3")
    }
}
