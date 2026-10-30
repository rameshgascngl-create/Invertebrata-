package com.gasczoology.invertebratelab.data

import java.security.MessageDigest

/**
 * U1.16 — bytes-level provenance of historical illustrations.
 *
 * Matching a published source file is NOT a zoologist's endorsement of any
 * individual figure or the authenticity of a physically hand-pencilled plate.
 * This material is a 1922 *printed* plate, not a recent pencil drawing.
 */
internal data class HistoricalFigureGroup(
    val id: String,
    val captionEn: String,
    val captionTa: String,
    val stageClassification: String,
)

internal data class HistoricalArchiveIdentity(
    val archiveId: String,
    val sourcePage: String,
    val sourceImageUrl: String,
    val authorAndPublication: String,
    val rights: String,
    val sourceByteCount: Int,
    val sourceWidth: Int,
    val sourceHeight: Int,
    val publishedSha1: String,
    val figureGroups: List<HistoricalFigureGroup>,
) {
    init {
        require(publishedSha1.matches(Regex("[a-f0-9]{40}")))
        require(sourcePage.startsWith("https://commons.wikimedia.org/"))
        require(sourceByteCount > 0 && sourceWidth > 0 && sourceHeight > 0)
    }

    fun exactSourceMatches(bytes: ByteArray, width: Int, height: Int): Boolean {
        if (bytes.size != sourceByteCount || width != sourceWidth || height != sourceHeight)
            return false
        val checksum = MessageDigest.getInstance("SHA-1").digest(bytes)
            .joinToString("") { "%02x".format(it) }
        return checksum == publishedSha1
    }
}

internal data class HistoricalPixelCrop(
    val left: Int, val top: Int, val rightExclusive: Int, val bottomExclusive: Int,
) {
    val width: Int get() = rightExclusive - left
    val height: Int get() = bottomExclusive - top

    fun inBounds(sourceWidth: Int, sourceHeight: Int): Boolean =
        sourceWidth > 0 && sourceHeight > 0 &&
            left >= 0 && top >= 0 && rightExclusive <= sourceWidth &&
            bottomExclusive <= sourceHeight && width >= 50 && height >= 50

    companion object {
        /** Derive an exact, unscaled pixel rectangle from two reviewer taps. */
        fun between(
            x1: Float, y1: Float, x2: Float, y2: Float,
            width: Int, height: Int,
        ): HistoricalPixelCrop? {
            if (width <= 0 || height <= 0 ||
                !x1.isFinite() || !y1.isFinite() || !x2.isFinite() || !y2.isFinite()) return null
            val a = minOf(x1, x2).toInt()
            val b = minOf(y1, y2).toInt()
            val c = kotlin.math.ceil(maxOf(x1, x2).toDouble()).toInt()
            val d = kotlin.math.ceil(maxOf(y1, y2).toDouble()).toInt()
            return HistoricalPixelCrop(a, b, c, d).takeIf { it.inBounds(width, height) }
        }
    }
}

internal object UnitIHistoricalPlateArchive {
    /**
     * 1922 Fig 14 printed illustration, catalogued as "Leishmania donovani (02).jpg".
     * Wikimedia Commons structured-data SHA-1 and source dimensions/bytes:
     * https://commons.wikimedia.org/wiki/File:Leishmania_donovani_(02).jpg
     *
     * A-E are tissue/host-associated figures; F-O depict CULTURE DEVELOPMENT,
     * not ten independently verified promastigotes. J-L are captioned
     * "flagellated" culture forms. Stage sign-off remains an external task.
     */
    val leishmania1922 = HistoricalArchiveIdentity(
        archiveId = "leishmania-chandler-1922-fig14",
        sourcePage = "https://commons.wikimedia.org/wiki/File:Leishmania_donovani_(02).jpg",
        sourceImageUrl = "https://upload.wikimedia.org/wikipedia/commons/0/0a/Leishmania_donovani_%2802%29.jpg",
        authorAndPublication = "Asa C. Chandler, Animal Parasites and Human Disease (1922), Fig. 14, after Leishman",
        rights = "Public Domain Mark; Wikimedia Commons archived book illustration",
        sourceByteCount = 314025,
        sourceWidth = 1552,
        sourceHeight = 1584,
        publishedSha1 = "3a08d99564510b548416ffb0bb4318eef68c1948",
        figureGroups = listOf(
            HistoricalFigureGroup(
                "tissue-a-e",
                "A–E: tissue-derived and host-associated parasites; figure-level stage approval required.",
                "A–E: திசு மற்றும் ஓம்புயிரிச் செல்களுடன் தொடர்புடைய நிலைகள்; ஒவ்வொரு படத்திற்கும் தனிச் சரிபார்ப்பு தேவை.",
                "UNVERIFIED_TISSUE_FORMS",
            ),
            HistoricalFigureGroup(
                "culture-f-o",
                "F–O: cultured developmental forms; do not classify every form as a promastigote.",
                "F–O: வளர்ப்பு நிலைகளின் மாறுபாடுகள்; அனைத்தையும் புரோமாஸ்டிகோட் எனக் கூறக் கூடாது.",
                "UNVERIFIED_CULTURE_FORMS",
            ),
            HistoricalFigureGroup(
                "flagellated-j-l",
                "J–L: captioned flagellated culture forms; species/morphotype review still required.",
                "J–L: கசையிழையுடைய வளர்ப்புநிலைகள் என விளக்கப்பட்டுள்ளன; அறிவியல் ஒப்புதல் இன்னும் தேவை.",
                "UNVERIFIED_FLAGELLATED_CULTURE",
            ),
        ),
    )

    fun forOrganism(id: String): HistoricalArchiveIdentity? =
        if (id == "leishmania") leishmania1922 else null
}
