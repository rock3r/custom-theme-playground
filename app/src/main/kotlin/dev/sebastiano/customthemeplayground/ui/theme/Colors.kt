package dev.sebastiano.customthemeplayground.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class Colors(
    val windowBackground: Color,
    val foreground: Color,
    val accent: Color,
    val control: Color,
)

fun darkColors(
    windowBackground: Color = Color(0xFF4B4B4B),
    foreground: Color = Color(0xFFFFFFFF),
    accent: Color = Color(0xFF2E6803),
    control: Color = Color(0xFF487510),
) = Colors(windowBackground, foreground, accent, control)

fun lightColors(
    windowBackground: Color = Color(0xFFFFFFFF),
    foreground: Color = Color(0xFF4B4B4B),
    accent: Color = Color(0xFF58CC02),
    control: Color = Color(0xFF89e219),
) = Colors(windowBackground, foreground, accent, control)
