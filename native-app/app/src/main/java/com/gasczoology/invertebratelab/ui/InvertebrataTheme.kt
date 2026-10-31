package com.gasczoology.invertebratelab.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/**
 * Brand colour scheme (colour only: typography and shapes are deliberately the
 * Material 3 defaults so that 200% Tamil layouts and the instrumentation
 * viewport assertions are unaffected).
 *
 * Contrast ratios (WCAG 2.x, computed): primary/onPrimary 7.9:1,
 * onPrimaryContainer/primaryContainer 13.6:1, secondary/surface 7.1:1,
 * onSurfaceVariant/surface 9.1:1. All meet AAA for normal text.
 *
 * LIGHT ONLY. Many reader cards use fixed light container colours
 * (e.g. Color(0xFFF0F5F3)); a dark scheme must not be enabled until those are
 * migrated to semantic roles, or text would become unreadable.
 */
private val InvertebrataLightColors = lightColorScheme(
    primary = Color(0xFF0F5C4D),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFCDEBE2),
    onPrimaryContainer = Color(0xFF00201A),
    secondary = Color(0xFF7A4F00),
    onSecondary = Color(0xFFFFFFFF),
    onSurfaceVariant = Color(0xFF3F4945),
)

@Composable
fun InvertebrataTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = InvertebrataLightColors, content = content)
}
