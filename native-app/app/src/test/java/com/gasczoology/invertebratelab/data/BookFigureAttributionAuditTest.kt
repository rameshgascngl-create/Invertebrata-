package com.gasczoology.invertebratelab.data

import org.junit.Assert.*
import org.junit.Test

class BookFigureAttributionAuditTest {
    @Test fun suppliedImagesRemainReachableWithoutInventedOriginClaims() {
        val pending = listOf("nereis-external", "nereis-head", "nereis-parapodium", "nereis-digestive", "nereis-circulatory", "nereis-nervous", "nereis-excretory", "nereis-trochophore", "entamoeba-mature-cyst", "ascaris-fertilized-egg", "entamoeba-cycle", "pila-excretory-system", "pila-male-reproductive-system", "pila-female-reproductive-system", "plasmodium-cycle")
        pending.forEach { id ->
            assertNotNull(id, BookFigureLibrary.stemFor("lib/" + id))
            assertEquals(id, FigureAttributionState.PENDING,
                BookFigureLibrary.all.single { it.id == id }.attributionState)
            assertNull(id, BookFigureLibrary.creditFor("lib/" + id))
        }
    }
    @Test fun userConfirmedParasiteAiCreditsAreRetained() {
        listOf("trypanosoma-cartoon", "leishmania-amastigote").forEach { id ->
            assertEquals(FigureAttributionState.RECORDED,
                BookFigureLibrary.all.single { it.id == id }.attributionState)
            val credit = requireNotNull(BookFigureLibrary.creditFor("lib/" + id))
            assertTrue(credit.en.contains("AI-generated"))
            assertTrue(credit.ta.isNotBlank())
        }
    }
    @Test fun libraryReferencesResolveToTheirActualCreditRegistrySlot() {
        assertEquals("obelia/colony", BookFigureLibrary.creditSlotFor("lib/obelia-colony"))
        assertEquals("unit1/references/trypanosoma", BookFigureLibrary.creditSlotFor("lib/trypanosoma-cartoon"))
        assertEquals("fasciola/life-cycle", BookFigureLibrary.creditSlotFor("fasciola/life-cycle"))
    }
}
