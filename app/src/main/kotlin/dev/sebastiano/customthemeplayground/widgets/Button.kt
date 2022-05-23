package dev.sebastiano.customthemeplayground.widgets

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import dev.sebastiano.customthemeplayground.ui.theme.LocalPalette
import dev.sebastiano.customthemeplayground.ui.theme.MyDesignSystemTheme
import dev.sebastiano.customthemeplayground.ui.theme.MyTheme
import dev.sebastiano.customthemeplayground.ui.theme.ProvideTextStyle

@Composable
fun Button(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    colors: ButtonColors = unspecifiedButtonColors,
    contentPadding: PaddingValues = PaddingValues(horizontal = MyTheme.metrics.button.horizontalPadding),
    content: @Composable RowScope.() -> Unit
) {
    val buttonColors = colors.withDefaultsFrom(LocalPalette.current.button)
    val metrics = MyTheme.metrics
    val cornerRadius = metrics.button.cornerRadius
    val shape = RoundedCornerShape(cornerRadius)
    val isPressed by interactionSource.collectIsPressedAsState()
    val lipSize by derivedStateOf { if (isPressed) metrics.button.lipSizePressed else metrics.button.lipSize }

    val topPadding by derivedStateOf { if (isPressed) metrics.button.lipSize else metrics.button.lipSizePressed }
    val lipColor = buttonColors.lipColor(enabled).value

    Box(
        modifier = modifier
            .defaultMinSize(minHeight = MyTheme.metrics.button.minHeight)
            .clickable(
                enabled = enabled,
                onClick = onClick,
                role = Role.Button,
                interactionSource = interactionSource,
                indication = null
            )
            .drawBehind {
                val lipSizePx = metrics.button.lipSize.toPx()
                val cornerRadiusPx = cornerRadius.toPx()
                drawRoundRect(
                    color = lipColor,
                    topLeft = Offset(0f, lipSizePx),
                    size = Size(size.width, size.height - lipSizePx),
                    cornerRadius = CornerRadius(cornerRadiusPx, cornerRadiusPx)
                )
            }
            .padding(top = topPadding, bottom = lipSize),
        propagateMinConstraints = true
    ) {
        val foreground by buttonColors.foregroundColor(enabled)

        CompositionLocalProvider(
            LocalPalette provides MyTheme.colors.copy(foreground = foreground),
        ) {
            ProvideTextStyle(value = MyTheme.typography.button) {
                Row(
                    Modifier
                        .background(buttonColors.backgroundColor(enabled).value, shape)
                        .padding(contentPadding),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    content = content
                )
            }
        }
    }
}

val unspecifiedButtonColors = ButtonColors(
    background = Color.Unspecified,
    foreground = Color.Unspecified,
    lip = Color.Unspecified,
    disabledBackground = Color.Unspecified,
    disabledForeground = Color.Unspecified,
    disabledLip = Color.Unspecified
)

@Immutable
class ButtonColors(
    private val background: Color,
    private val foreground: Color,
    private val lip: Color,
    private val disabledBackground: Color,
    private val disabledForeground: Color,
    private val disabledLip: Color,
) {

    @Composable
    fun backgroundColor(enabled: Boolean): State<Color> =
        rememberUpdatedState(if (enabled) background else disabledBackground)

    @Composable
    fun foregroundColor(enabled: Boolean): State<Color> =
        rememberUpdatedState(if (enabled) foreground else disabledForeground)

    @Composable
    fun lipColor(enabled: Boolean): State<Color> =
        rememberUpdatedState(if (enabled) lip else disabledLip)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ButtonColors

        if (background != other.background) return false
        if (foreground != other.foreground) return false
        if (lip != other.lip) return false
        if (disabledBackground != other.disabledBackground) return false
        if (disabledForeground != other.disabledForeground) return false
        if (disabledLip != other.disabledLip) return false

        return true
    }

    override fun hashCode(): Int {
        var result = background.hashCode()
        result = 31 * result + foreground.hashCode()
        result = 31 * result + lip.hashCode()
        result = 31 * result + disabledBackground.hashCode()
        result = 31 * result + disabledForeground.hashCode()
        result = 31 * result + disabledLip.hashCode()
        return result
    }

    fun withDefaultsFrom(defaults: ButtonColors) = ButtonColors(
        background = background.takeOrElse { defaults.background },
        foreground = foreground.takeOrElse { defaults.foreground },
        lip = lip.takeOrElse { defaults.lip },
        disabledBackground = disabledBackground.takeOrElse { defaults.disabledBackground },
        disabledForeground = disabledForeground.takeOrElse { defaults.disabledForeground },
        disabledLip = disabledLip.takeOrElse { defaults.disabledLip },
    )
}

fun buttonColors(
    darkTheme: Boolean,
    background: Color = Color.Unspecified,
    foreground: Color = Color.Unspecified,
    lip: Color = Color.Unspecified,
    disabledBackground: Color = Color.Unspecified,
    disabledForeground: Color = Color.Unspecified,
    disabledLip: Color = Color.Unspecified,
) = if (darkTheme) {
    darkButtonColors(background, foreground, lip, disabledBackground, disabledForeground, disabledLip)
} else {
    lightButtonColors(background, foreground, lip, disabledBackground, disabledForeground, disabledLip)
}

@Composable
fun buttonColors(
    background: Color = Color.Unspecified,
    foreground: Color = Color.Unspecified,
    lip: Color = Color.Unspecified,
    disabledBackground: Color = Color.Unspecified,
    disabledForeground: Color = Color.Unspecified,
    disabledLip: Color = Color.Unspecified,
) = if (isSystemInDarkTheme()) {
    darkButtonColors(background, foreground, lip, disabledBackground, disabledForeground, disabledLip)
} else {
    lightButtonColors(background, foreground, lip, disabledBackground, disabledForeground, disabledLip)
}

private fun darkButtonColors(
    background: Color = Color.Unspecified,
    foreground: Color = Color.Unspecified,
    lip: Color = Color.Unspecified,
    disabledBackground: Color = Color.Unspecified,
    disabledForeground: Color = Color.Unspecified,
    disabledLip: Color = Color.Unspecified,
) = ButtonColors(
    background.takeOrElse { Color(0xFF99E439) },
    foreground.takeOrElse { Color(0xFF152021) },
    lip.takeOrElse { Color(0xFF7DC937) },
    disabledBackground.takeOrElse { Color(0xFF38474E) },
    disabledForeground.takeOrElse { Color(0xFF55666C) },
    disabledLip.takeOrElse { Color(0xFF38474E) },
)

private fun lightButtonColors(
    background: Color = Color.Unspecified,
    foreground: Color = Color.Unspecified,
    lip: Color = Color.Unspecified,
    disabledBackground: Color = Color.Unspecified,
    disabledForeground: Color = Color.Unspecified,
    disabledLip: Color = Color.Unspecified,
) = ButtonColors(
    background.takeOrElse { Color(0xFF60E509) },
    foreground.takeOrElse { Color.White },
    lip.takeOrElse { Color(0xFF5EBB05) },
    disabledBackground.takeOrElse { Color(0xFFE5E5E5) },
    disabledForeground.takeOrElse { Color(0xFFAFAFAF) },
    disabledLip.takeOrElse { Color(0xFFE5E5E5) },
)

@Preview(name = "Light theme")
@Preview(name = "Dark theme", uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun ButtonPreview() {
    MyDesignSystemTheme {
        Button(onClick = { }) {
            Text("BUTTON TEST")
        }
    }
}

@Preview(name = "Light theme")
@Preview(name = "Dark theme", uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun WideButtonPreview() {
    MyDesignSystemTheme {
        Button(onClick = { }, Modifier.fillMaxWidth()) {
            Text("BUTTON TEST")
        }
    }
}
