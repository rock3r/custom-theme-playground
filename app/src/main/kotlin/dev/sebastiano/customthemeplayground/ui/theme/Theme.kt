package dev.sebastiano.customthemeplayground.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val LocalPalette = compositionLocalOf { LightColorPalette }

private val DarkColorPalette = darkColors()

private val LightColorPalette = lightColors()

val LocalTypography = compositionLocalOf { MyTypography }

private val MyTypography = Typography(
    body = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
)

@Composable
fun MyDesignSystemTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    CompositionLocalProvider(
        LocalPalette provides colors,
        LocalTypography provides MyTypography
    ) {
        content()
    }
}

object MyTheme {

    val colors: Colors
        @Composable
        @ReadOnlyComposable
        get() = LocalPalette.current

    val typography: Typography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current
}
