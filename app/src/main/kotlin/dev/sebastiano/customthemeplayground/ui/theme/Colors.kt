package dev.sebastiano.customthemeplayground.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.takeOrElse
import dev.sebastiano.customthemeplayground.widgets.ButtonColors
import dev.sebastiano.customthemeplayground.widgets.buttonColors

@Immutable
data class Colors(
    val windowBackground: Color,
    val foreground: Color,
    val accent: Color,
    val indication: IndicationColors,
    val button: ButtonColors
)

@Composable
fun blueThemeColors() = blueThemeColors(isSystemInDarkTheme())

fun blueThemeColors(darkTheme: Boolean) = if (darkTheme) {
    darkColors(
        button = buttonColors(
            darkTheme = true,
            background = Color(0xFF2ABAF0),
            lip = Color(0xFF24A3D3)
        )
    )
} else {
    lightColors(
        button = buttonColors(
            darkTheme = false,
            background = Color(0xFF55C9F4),
            lip = Color(0xFF24A3D3)
        )
    )
}

private fun darkColors(
    windowBackground: Color = Color(0xFF152021),
    foreground: Color = Color(0xFFF2F7FA),
    accent: Color = Color(0xFF55C9F4),
    indication: IndicationColors = indicationColors(darkTheme = true),
    button: ButtonColors = buttonColors(darkTheme = true)
) = Colors(windowBackground, foreground, accent, indication, button)

private fun lightColors(
    windowBackground: Color = Color.Unspecified,
    foreground: Color = Color.Unspecified,
    accent: Color = Color.Unspecified,
    indication: IndicationColors = indicationColors(darkTheme = false),
    button: ButtonColors = buttonColors(darkTheme = false)
) = Colors(
    windowBackground.takeOrElse { Color(0xFFFFFFFF) },
    foreground.takeOrElse { Color(0xFF4B4B4B) },
    accent.takeOrElse { Color(0xFF58CC02) },
    indication,
    button
)

val LocalPalette = compositionLocalOf { LightColorPalette }

val DarkColorPalette = darkColors()

val LightColorPalette = lightColors()
