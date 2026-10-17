package com.gasczoology.invertebratelab.ui

import androidx.compose.runtime.Composable
import com.gasczoology.invertebratelab.data.AppLanguage
import com.gasczoology.invertebratelab.data.BilingualText

/**
 * Accessible English–Tamil terminology for eight fixed Nereis plates.
 * Tamil text is provisional until the college manual is available.
 * Anatomy is NEVER drawn using Compose Canvas here: the plates are
 * actual offline PNG images loaded by UnitIVNereisRasterPlate.
 */
internal fun nereisPlateTerms(index:Int):List<BilingualText>{
    fun b(en:String,ta:String)=BilingualText(en,ta)
    return when(index){
        0->listOf(b("Prostomium","புரோஸ்டோமியம்"),b("Peristomium","பெரிஸ்டோமியம்"),b("Chaetigerous segments","சீட்டா கொண்ட கண்டங்கள்"),b("Paired parapodia","இணை பாராப்போடியா"),b("Pygidium","பைகிடியம்"),b("Anal cirri","ஆசனச் சிர்ரைகள்"))
        1->listOf(b("Prostomium","புரோஸ்டோமியம்"),b("Palps","பல்புகள்"),b("Antennae","உணர்கொம்புகள்"),b("Eyes","கண்கள்"),b("Peristomium","பெரிஸ்டோமியம்"),b("Tentacular cirri","கூடாரச் சிர்ரைகள்"))
        2->listOf(b("Notopodium","நோட்டோபோடியம்"),b("Neuropodium","நியூரோபோடியம்"),b("Dorsal cirrus","முதுகுச் சிர்ரஸ்"),b("Ventral cirrus","வயிற்றுச் சிர்ரஸ்"),b("Acicula","அசிகுலா"),b("Notochaetae / neurochaetae","நோட்டோசீட்டா / நியூரோசீட்டா"))
        3->listOf(b("Mouth","வாய்"),b("Eversible pharynx","வெளிநீளும் தொண்டை"),b("Paired jaws","இணைத் தாடைகள்"),b("Oesophagus","உணவுக்குழாய்"),b("Intestine","குடல்"),b("Anus","ஆசனவாய்"))
        4->listOf(b("Dorsal vessel","முதுகு இரத்த நாளம்"),b("Ventral vessel","வயிற்று இரத்த நாளம்"),b("Segmental connections","கண்டநாள இணைப்புகள்"),b("Dorsal–ventral cross section","முதுகு–வயிறு குறுக்குவெட்டு"))
        5->listOf(b("Cerebral ganglia","மூளை நரம்புத்திரள்கள்"),b("Circumpharyngeal connectives","தொண்டைச் சுற்று இணைப்புகள்"),b("Suboesophageal ganglion","தொண்டைக்கீழ் நரம்புத்திரள்"),b("Ventral nerve cord","வயிற்று நரம்புக்கயிறு"),b("Segmental ganglia","கண்ட நரம்புத்திரள்கள்"))
        6->listOf(b("Ciliated nephrostome","சிலியா கொண்ட நெப்ரோஸ்டோம்"),b("Septum","இடைத்தடுப்பு"),b("Coiled nephridial tubule","சுருண்ட நெப்ரிடியக் குழாய்"),b("Nephridiopore","நெப்ரிடியத் துளை"))
        else->listOf(b("Apical tuft","உச்சிச் சிலியாக் கொத்து"),b("Prototroch","புரோட்டோட்ரோக்"),b("Mouth","வாய்"),b("Alimentary canal","உணவுக் குழாய்"),b("Anus","ஆசனவாய்"))
    }
}

@Composable
internal fun UnitIVNereisStaticPlate(index:Int,language:AppLanguage) {
    UnitIVNereisRasterPlate(index,language)
}
