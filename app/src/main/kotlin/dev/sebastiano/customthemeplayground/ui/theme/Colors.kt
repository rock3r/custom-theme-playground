package dev.sebastiano.customthemeplayground.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class Colors(
    val windowBackground: Color,
    val foreground: Color,
    val accent: Color,
    val control: Color,
    val indication: IndicationColors,
) {

    data class IndicationColors(
        val pressed: Color,
        val hovered: Color,
        val focused: Color,
    )
}

private fun darkColors(
    windowBackground: Color = Color(0xFF4B4B4B),
    foreground: Color = Color(0xFFFFFFFF),
    accent: Color = Color(0xFF2E6803),
    control: Color = Color(0xFF487510),
    indication: Colors.IndicationColors = Colors.IndicationColors(
        pressed = Color(0x410AA056),
        hovered = Color(0x210AA056),
        focused = Color(0x660C874A),
    ),
) = Colors(windowBackground, foreground, accent, control, indication)

private fun lightColors(
    windowBackground: Color = Color(0xFFFFFFFF),
    foreground: Color = Color(0xFF4B4B4B),
    accent: Color = Color(0xFF58CC02),
    control: Color = Color(0xFF89e219),
    indication: Colors.IndicationColors = Colors.IndicationColors(
        pressed = Color(0x410C6338),
        hovered = Color(0x21028D48),
        focused = Color(0x66078949),
    ),
) = Colors(windowBackground, foreground, accent, control, indication)

val LocalPalette = compositionLocalOf { LightColorPalette }

val DarkColorPalette = darkColors()

val LightColorPalette = lightColors()
