package dev.sebastiano.customthemeplayground.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.takeOrElse
import dev.sebastiano.customthemeplayground.widgets.ButtonColors
import dev.sebastiano.customthemeplayground.widgets.ProgressBarColors
import dev.sebastiano.customthemeplayground.widgets.buttonColors
import dev.sebastiano.customthemeplayground.widgets.progressBarColors

@Immutable
data class Colors(
    val windowBackground: Color,
    val foreground: Color,
    val accent: Color,
    val indication: IndicationColors,
    val button: ButtonColors,
    val progressBar: ProgressBarColors,
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
    windowBackground: Color = Color.Unspecified,
    foreground: Color = Color.Unspecified,
    accent: Color = Color.Unspecified,
    indication: IndicationColors = indicationColors(darkTheme = true),
    button: ButtonColors = buttonColors(darkTheme = true),
    progressBar: ProgressBarColors = progressBarColors(darkTheme = true),
) = Colors(
    windowBackground.takeOrElse { Color(0xFF152021) },
    foreground.takeOrElse { Color(0xFFF2F7FA) },
    accent.takeOrElse { Color(0xFF55C9F4) },
    indication,
    button,
    progressBar
)

private fun lightColors(
    windowBackground: Color = Color.Unspecified,
    foreground: Color = Color.Unspecified,
    accent: Color = Color.Unspecified,
    indication: IndicationColors = indicationColors(darkTheme = false),
    button: ButtonColors = buttonColors(darkTheme = false),
    progressBar: ProgressBarColors = progressBarColors(darkTheme = false),
) = Colors(
    windowBackground.takeOrElse { Color(0xFFFFFFFF) },
    foreground.takeOrElse { Color(0xFF4B4B4B) },
    accent.takeOrElse { Color(0xFF58CC02) },
    indication,
    button,
    progressBar
)

val LocalPalette = compositionLocalOf { LightColorPalette }

val DarkColorPalette = darkColors()

val LightColorPalette = lightColors()
