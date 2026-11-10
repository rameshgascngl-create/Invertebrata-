package com.gasczoology.invertebratelab.ui

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.gasczoology.invertebratelab.OfflineNarrator
import com.gasczoology.invertebratelab.data.*

/**
 * REAL native text/interactive teaching, not a substitute A5 Q&A screen.
 * Never generate pseudo pencil morphology or clickable anatomy coordinates.
 * Learning interaction uses the reviewed terminology, NOT a fabricated plate.
 */
@Composable
internal fun UnitIVPenaeusIntroduction(language: AppLanguage) {
    val ta=language==AppLanguage.TAMIL
    Column(verticalArrangement=Arrangement.spacedBy(9.dp),
        modifier=Modifier.fillMaxWidth().testTag("u4-penaeus-textbook")) {
        Text(
            if(ta) "Penaeus — விரிவான இறால் இன ஆய்வுப் பாடநூல்"
            else "Penaeus — complete native prawn type-study textbook",
            style=MaterialTheme.typography.headlineSmall,
            modifier=Modifier.semantics { heading() }.testTag("u4-penaeus-textbook-title"),
        )
        Text(
            if(ta) "Penaeus பேரினத்தில் பல சிற்றினங்கள் உள்ளன. குறிப்பிட்ட சிற்றினத்தின் பண்புகளையும் பொதுவான இறால் உடற்கூற்றையும் வேறுபடுத்திப் படிக்கவும்."
            else "Penaeus contains several species. Distinguish general prawn anatomy from characters diagnostic of an identified species.",
            color=MaterialTheme.colorScheme.error,
            modifier=Modifier.testTag("u4-penaeus-academic-hold"),
        )
        Text(
            if(ta) "12 விளக்கப் பாடப் பிரிவுகள் · 19 இணைக் கால் ஜோடிகளின் அமைப்பு · 7 வாழ்க்கைநிலைப் படிகள் · இயல்பான A5 வினாக்கள் பின்னர்"
            else "12 structured explanatory sections · 19 appendage pairs · 7 larval/life stages · original A5 questions remain below.",
            style=MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
internal fun UnitIVPenaeusLessonSection(
    item:PenaeusTeachingSection, language:AppLanguage,
) {
    Column(
        modifier=Modifier.fillMaxWidth()
            .testTag("u4-penaeus-section-"+item.id),
        verticalArrangement=Arrangement.spacedBy(8.dp),
    ) {
        HorizontalDivider()
        Text(item.heading.value(language),
            style=MaterialTheme.typography.titleLarge,
            modifier=Modifier.semantics { heading() })
        item.paragraphs.forEach { paragraph ->
            Text(paragraph.value(language),style=MaterialTheme.typography.bodyLarge)
        }
        Text(
            (if(language==AppLanguage.TAMIL) "அறிவியல் ஆதாரம்: " else "Science reference: ") +
                item.source,
            style=MaterialTheme.typography.labelSmall,
        )
    }
}

@Composable
internal fun UnitIVPenaeusAppendageExplorer(
    language:AppLanguage,
    selectedIndex:Int,
    onSelected:(Int)->Unit,
) {
    val context=LocalContext.current
    val narrator=remember(context) { OfflineNarrator(context.applicationContext) }
    val voice by narrator.phase.collectAsState()
    DisposableEffect(narrator) { onDispose { narrator.close() } }
    val ta=language==AppLanguage.TAMIL
    val options=UnitIVPenaeusTeaching.appendages
    val index=selectedIndex.coerceIn(options.indices)
    val selected=options[index]

    Card(modifier=Modifier.fillMaxWidth().testTag("u4-penaeus-appendage-explorer")) {
        Column(modifier=Modifier.fillMaxWidth().padding(14.dp),
            verticalArrangement=Arrangement.spacedBy(10.dp)) {
            Text(if(ta)"கண்ட இணைக் கால்கள் — பணிச்சிறப்புகள்"
                else "Interactive serial-appendage study",
                style=MaterialTheme.typography.titleLarge,
                modifier=Modifier.semantics { heading() })
            Text(if(ta)
                "தலை 5 + மார்பு 8 + வயிறு 6 = 19 ஜோடி; இது உறுப்புகளின் வகைப்பாடாகும், சரிபார்க்கப்பட்ட தொடுபுள்ளி வரைபடமல்ல."
                else "Head 5 + thorax 8 + abdomen 6 = 19 PAIRS. This is a segment/organ-function explorer, NOT an approved anatomical hotspot image.",
                color=MaterialTheme.colorScheme.error,
                modifier=Modifier.testTag("u4-penaeus-appendage-disclosure"))
            Row(
                modifier=Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                horizontalArrangement=Arrangement.spacedBy(8.dp),
            ) {
                options.forEachIndexed { i, option ->
                    FilterChip(
                        selected=index==i,
                        onClick={onSelected(i);narrator.stop()},
                        label={Text(option.region.value(language))},
                        modifier=Modifier.heightIn(min=48.dp)
                            .testTag("u4-penaeus-appendage-option-"+option.id),
                    )
                }
            }
            Text(selected.names.value(language),
                style=MaterialTheme.typography.titleMedium,
                modifier=Modifier.testTag("u4-penaeus-appendage-name"))
            Text((if(ta)"இந்தப் பகுதி: " else "Paired appendages here: ")+
                selected.numberOfPairs.toString(),
                modifier=Modifier.testTag("u4-penaeus-appendage-count"))
            Text(selected.role.value(language),style=MaterialTheme.typography.bodyLarge,
                modifier=Modifier.testTag("u4-penaeus-appendage-role"))
            Text(selected.caution.value(language),
                color=MaterialTheme.colorScheme.error,
                modifier=Modifier.testTag("u4-penaeus-appendage-warning"))
            Row(horizontalArrangement=Arrangement.spacedBy(8.dp),
                modifier=Modifier.fillMaxWidth()) {
                OutlinedButton(
                    onClick={
                        onSelected((index-1+options.size)%options.size)
                        narrator.stop()
                    },
                    modifier=Modifier.weight(1f).heightIn(min=48.dp)
                        .testTag("u4-penaeus-appendage-previous")
                ) { Text(if(ta)"முந்தையது" else "Previous") }
                OutlinedButton(
                    onClick={
                        onSelected((index+1)%options.size)
                        narrator.stop()
                    },
                    modifier=Modifier.weight(1f).heightIn(min=48.dp)
                        .testTag("u4-penaeus-appendage-next")
                ) { Text(if(ta)"அடுத்தது" else "Next") }
            }
            OutlinedButton(
                onClick={narrator.speak(
                    BilingualText(
                        selected.names.english+". "+selected.role.english+". "+selected.caution.english,
                        selected.names.tamil+". "+selected.role.tamil+". "+selected.caution.tamil),
                    language)},
                modifier=Modifier.fillMaxWidth().heightIn(min=48.dp)
                    .testTag("u4-penaeus-appendage-speak")
            ) { Text(if(ta)"அமைப்பின் விளக்க ஒலி" else "Speak structure explanation offline") }
            Text((if(ta)"ஒலி நிலை: " else "Offline voice state: ")+voice.name,
                style=MaterialTheme.typography.labelSmall,
                modifier=Modifier.testTag("u4-penaeus-appendage-voice-state"))
        }
    }
}

@Composable
internal fun UnitIVPenaeusLifeCycleExplorer(
    language:AppLanguage,
    selectedIndex:Int,
    onSelected:(Int)->Unit,
) {
    val context=LocalContext.current
    val narrator=remember(context) { OfflineNarrator(context.applicationContext) }
    DisposableEffect(narrator){onDispose{narrator.close()}}
    val ta=language==AppLanguage.TAMIL
    val stages=UnitIVPenaeusTeaching.stages
    val index=selectedIndex.coerceIn(stages.indices)
    val stage=stages[index]
    Card(modifier=Modifier.fillMaxWidth().testTag("u4-penaeus-life-explorer")) {
        Column(modifier=Modifier.fillMaxWidth().padding(14.dp),
            verticalArrangement=Arrangement.spacedBy(10.dp)) {
            Text(if(ta)"பெனேயிட் இறாலின் வாழ்க்கைச் சுழற்சி"
                else "Penaeid life-cycle stage explorer",
                style=MaterialTheme.typography.titleLarge,
                modifier=Modifier.semantics { heading() })
            Text((index+1).toString()+" / "+stages.size+
                " — "+stage.name.value(language),
                style=MaterialTheme.typography.titleMedium,
                modifier=Modifier.testTag("u4-penaeus-life-stage"))
            Text(stage.morphology.value(language),
                modifier=Modifier.testTag("u4-penaeus-life-morphology"))
            Text(stage.ecology.value(language),
                modifier=Modifier.testTag("u4-penaeus-life-ecology"))
            Row(modifier=Modifier.fillMaxWidth(),
                horizontalArrangement=Arrangement.spacedBy(8.dp)) {
                OutlinedButton(
                    onClick={onSelected((index-1+stages.size)%stages.size);narrator.stop()},
                    modifier=Modifier.weight(1f).heightIn(min=48.dp)
                        .testTag("u4-penaeus-life-previous"),
                ){Text(if(ta)"முந்தைய நிலை" else "Previous stage")}
                OutlinedButton(
                    onClick={onSelected((index+1)%stages.size);narrator.stop()},
                    modifier=Modifier.weight(1f).heightIn(min=48.dp)
                        .testTag("u4-penaeus-life-next"),
                ){Text(if(ta)"அடுத்த நிலை" else "Next stage")}
            }
            OutlinedButton(
                onClick={narrator.speak(BilingualText(
                    stage.name.english+". "+stage.morphology.english+" "+stage.ecology.english,
                    stage.name.tamil+". "+stage.morphology.tamil+" "+stage.ecology.tamil
                ), language)},
                modifier=Modifier.fillMaxWidth().heightIn(min=48.dp)
                    .testTag("u4-penaeus-life-speak"),
            ){Text(if(ta)"இந்த வளர்நிலையின் ஒலி விளக்கம்" else "Speak current stage offline")}
            Text(if(ta)
                "நௌப்ளியஸ், புரோட்டோசோயியா, மைசிஸ், பிந்தைய இளநிலை ஆகியவற்றின் உடலமைப்பை ஒப்பிடுக. வளர்ச்சி நேரம் சிற்றினத்திற்கேற்ப மாறலாம்."
                else "Compare nauplius, protozoea, mysis and postlarval structures. Developmental duration varies with species and environmental conditions.",
                color=MaterialTheme.colorScheme.error,
                modifier=Modifier.testTag("u4-penaeus-life-review-hold"))
        }
    }
}

@Composable
internal fun UnitIVPenaeusAtlasHold(language:AppLanguage) {
    val ta=language==AppLanguage.TAMIL
    Column(verticalArrangement=Arrangement.spacedBy(8.dp),
        modifier=Modifier.fillMaxWidth().testTag("u4-penaeus-atlas-pending")) {
        Text(if(ta)"நான்கு உடற்கூற்றுப் படிப்பு தலைப்புகள்"
            else "FOUR ANATOMICAL STUDY TOPICS",
            style=MaterialTheme.typography.titleLarge)
        listOf(
            BilingualText("External with rostrum and appendages","ரோஸ்ட்ரம் மற்றும் இணைக்கால்களுடனான வெளியமைப்பு"),
            BilingualText("Specialized appendage series","சிறப்பமைந்த இணைக்கால் தொடர்"),
            BilingualText("Digestive, respiratory and reproductive anatomy","செரிமானம், சுவாசம், இனப்பெருக்க உடற்கூறு"),
            BilingualText("Nauplius, protozoea, mysis and postlarval series","நௌப்ளியஸ், புரோட்டோசோயா, மைசிஸ், பிந்தைய இளநிலை")
        ).forEachIndexed {i,item->
            Text((i+1).toString()+". "+item.value(language))
        }
        Text(if(ta)
            "ஒவ்வொரு உறுப்பையும் இன ஆய்வுப் படத்துடனும் அதன் பாட விளக்கத்துடனும் ஒப்பிடுக. படம் காட்டாத விவரங்களை ஊகிக்க வேண்டாம்."
            else "Compare each organ with the type-study illustration and explanation. Do not infer structures that the figure does not show.",
            color=MaterialTheme.colorScheme.error)
    }
}
