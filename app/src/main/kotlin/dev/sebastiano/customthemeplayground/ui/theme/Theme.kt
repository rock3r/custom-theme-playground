package dev.sebastiano.customthemeplayground.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.structuralEqualityPolicy
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import dev.sebastiano.customthemeplayground.R

val LocalPalette = compositionLocalOf { LightColorPalette }

val LocalContentAlpha = compositionLocalOf { 1f }

val LocalTextStyle = compositionLocalOf(structuralEqualityPolicy()) { TextStyle.Default }

private val DarkColorPalette = darkColors()

private val LightColorPalette = lightColors()

val LocalTypography = compositionLocalOf { MyTypography }

private val nunito = FontFamily(
    Font(R.font.nunito_extralight, FontWeight.W200, FontStyle.Normal),
    Font(R.font.nunito_extralight_italic, FontWeight.W200, FontStyle.Italic),
    Font(R.font.nunito_light, FontWeight.W300, FontStyle.Normal),
    Font(R.font.nunito_light_italic, FontWeight.W300, FontStyle.Italic),
    Font(R.font.nunito_regular, FontWeight.W400, FontStyle.Normal),
    Font(R.font.nunito_italic, FontWeight.W400, FontStyle.Italic),
    Font(R.font.nunito_medium, FontWeight.W500, FontStyle.Normal),
    Font(R.font.nunito_medium_italic, FontWeight.W500, FontStyle.Italic),
    Font(R.font.nunito_semibold, FontWeight.W600, FontStyle.Normal),
    Font(R.font.nunito_semibold_italic, FontWeight.W600, FontStyle.Italic),
    Font(R.font.nunito_bold, FontWeight.W700, FontStyle.Normal),
    Font(R.font.nunito_bold_italic, FontWeight.W700, FontStyle.Italic),
    Font(R.font.nunito_extrabold, FontWeight.W800, FontStyle.Normal),
    Font(R.font.nunito_extrabold_italic, FontWeight.W800, FontStyle.Italic),
    Font(R.font.nunito_black, FontWeight.W900, FontStyle.Normal),
    Font(R.font.nunito_black_italic, FontWeight.W900, FontStyle.Italic),
)

private val MyTypography = Typography(
    body = TextStyle(
        fontFamily = nunito,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    button = TextStyle(
        fontFamily = nunito,
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
        LocalTypography provides MyTypography,
        LocalTextStyle provides MyTypography.body
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
