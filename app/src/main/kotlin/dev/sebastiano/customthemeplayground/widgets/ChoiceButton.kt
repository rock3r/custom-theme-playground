package dev.sebastiano.customthemeplayground.widgets

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.annotation.Px
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.sebastiano.customthemeplayground.ui.theme.LocalPalette
import dev.sebastiano.customthemeplayground.ui.theme.LocalTextStyle
import dev.sebastiano.customthemeplayground.ui.theme.MyDesignSystemTheme
import dev.sebastiano.customthemeplayground.ui.theme.MyTheme
import dev.sebastiano.customthemeplayground.ui.theme.ProvideTextStyle
import kotlin.math.max

@Composable
fun ChoiceButton(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: PaddingValues = PaddingValues(16.dp),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable BoxScope.() -> Unit,
) {
    val isPressed by interactionSource.collectIsPressedAsState()
    val metrics = MyTheme.metrics.choiceButton

    check(metrics.lipSize - metrics.borderSize >= 0.dp) { "The lip must be at least as big as the border" }

    @Px val cornerRadiusPx = with(LocalDensity.current) { metrics.cornerRadius.toPx() }
    @Px val borderWidthPx = with(LocalDensity.current) { metrics.borderSize.toPx() }

    val lipContentPaddingDp by derivedStateOf { metrics.lipSize - metrics.borderSize }
    val lipSizeDp by derivedStateOf { if (isPressed) 0.dp else metrics.lipSize }
    @Px val lipSizePx = with(LocalDensity.current) { lipSizeDp.toPx() }

    val colors = MyTheme.colors.choiceButton

    val backgroundColor by colors.backgroundColor(selected, enabled)
    val borderColor by colors.borderColor(selected, enabled)
    val outerCornerRadius by derivedStateOf { CornerRadius(cornerRadiusPx) }
    val innerCornerRadius by derivedStateOf { CornerRadius(cornerRadiusPx - borderWidthPx) }

    Box(
        modifier = modifier
            .selectable(
                selected = selected,
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled,
                role = Role.RadioButton,
                onClick = onClick
            )
            .drawBehind {
                // Draw border (we'll "carve out" the centre by drawing the background)
                drawRoundRect(color = borderColor, cornerRadius = outerCornerRadius)

                // Draw background
                drawRoundRect(
                    color = backgroundColor,
                    cornerRadius = innerCornerRadius,
                    topLeft = Offset(borderWidthPx, borderWidthPx),
                    size = Size(size.width - 2 * borderWidthPx, size.height - borderWidthPx - max(lipSizePx, borderWidthPx))
                )
            }
            .padding(metrics.borderSize)
            .padding(top = if (isPressed) lipContentPaddingDp else 0.dp, bottom = if (isPressed) 0.dp else lipContentPaddingDp)
            .padding(contentPadding),
        contentAlignment = Alignment.Center,
    ) {
        val foreground by colors.foregroundColor(selected, enabled)

        CompositionLocalProvider(
            LocalPalette provides MyTheme.colors.copy(foreground = foreground),
        ) {
            ProvideTextStyle(value = MyTheme.typography.choiceButton) {
                content()
            }
        }
    }
}

@Immutable
class ChoiceButtonColors(
    private val background: Color,
    private val foreground: Color,
    private val border: Color,
    private val selectedBackground: Color,
    private val selectedForeground: Color,
    private val selectedBorder: Color,
    private val disabledBackground: Color,
    private val disabledForeground: Color,
    private val disabledBorder: Color,
) {

    @Composable
    fun backgroundColor(selected: Boolean, enabled: Boolean): State<Color> =
        rememberUpdatedState(when {
            !enabled -> disabledBackground
            selected -> selectedBackground
            else -> background
        })

    @Composable
    fun foregroundColor(selected: Boolean, enabled: Boolean): State<Color> =
        rememberUpdatedState(when {
            !enabled -> selectedForeground
            selected -> selectedForeground
            else -> foreground
        })

    @Composable
    fun borderColor(selected: Boolean, enabled: Boolean): State<Color> =
        rememberUpdatedState(when {
            !enabled -> disabledBorder
            selected -> selectedBorder
            else -> border
        })

    fun withDefaultsFrom(defaults: ChoiceButtonColors) = ChoiceButtonColors(
        background = background.takeOrElse { defaults.background },
        foreground = foreground.takeOrElse { defaults.foreground },
        border = border.takeOrElse { defaults.border },
        selectedBackground = selectedBackground.takeOrElse { defaults.selectedBackground },
        selectedForeground = selectedForeground.takeOrElse { defaults.selectedForeground },
        selectedBorder = selectedBorder.takeOrElse { defaults.selectedBorder },
        disabledBackground = disabledBackground.takeOrElse { defaults.disabledBackground },
        disabledForeground = disabledForeground.takeOrElse { defaults.disabledForeground },
        disabledBorder = disabledBorder.takeOrElse { defaults.disabledBorder },
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ChoiceButtonColors

        if (background != other.background) return false
        if (foreground != other.foreground) return false
        if (border != other.border) return false
        if (selectedBackground != other.selectedBackground) return false
        if (selectedForeground != other.selectedForeground) return false
        if (selectedBorder != other.selectedBorder) return false
        if (disabledBackground != other.disabledBackground) return false
        if (disabledForeground != other.disabledForeground) return false
        if (disabledBorder != other.disabledBorder) return false

        return true
    }

    override fun hashCode(): Int {
        var result = background.hashCode()
        result = 31 * result + foreground.hashCode()
        result = 31 * result + border.hashCode()
        result = 31 * result + selectedBackground.hashCode()
        result = 31 * result + selectedForeground.hashCode()
        result = 31 * result + selectedBorder.hashCode()
        result = 31 * result + disabledBackground.hashCode()
        result = 31 * result + disabledForeground.hashCode()
        result = 31 * result + disabledBorder.hashCode()
        return result
    }
}

fun choiceButtonColors(
    darkTheme: Boolean,
    background: Color = Color.Unspecified,
    foreground: Color = Color.Unspecified,
    lip: Color = Color.Unspecified,
    disabledBackground: Color = Color.Unspecified,
    disabledForeground: Color = Color.Unspecified,
    disabledLip: Color = Color.Unspecified,
) = if (darkTheme) {
    darkChoiceButtonColors(background, foreground, lip, disabledBackground, disabledForeground, disabledLip)
} else {
    lightChoiceButtonColors(background, foreground, lip, disabledBackground, disabledForeground, disabledLip)
}

@Composable
fun choiceButtonColors(
    background: Color = Color.Unspecified,
    foreground: Color = Color.Unspecified,
    border: Color = Color.Unspecified,
    selectedBackground: Color = Color.Unspecified,
    selectedForeground: Color = Color.Unspecified,
    selectedBorder: Color = Color.Unspecified,
    disabledBackground: Color = Color.Unspecified,
    disabledForeground: Color = Color.Unspecified,
    disabledBorder: Color = Color.Unspecified,
) = if (isSystemInDarkTheme()) {
    darkChoiceButtonColors(
        background,
        foreground,
        border,
        selectedBackground,
        selectedForeground,
        selectedBorder,
        disabledBackground,
        disabledForeground,
        disabledBorder
    )
} else {
    lightChoiceButtonColors(
        background,
        foreground,
        border,
        selectedBackground,
        selectedForeground,
        selectedBorder,
        disabledBackground,
        disabledForeground,
        disabledBorder
    )
}

private fun darkChoiceButtonColors(
    background: Color = Color.Unspecified,
    foreground: Color = Color.Unspecified,
    border: Color = Color.Unspecified,
    selectedBackground: Color = Color.Unspecified,
    selectedForeground: Color = Color.Unspecified,
    selectedBorder: Color = Color.Unspecified,
    disabledBackground: Color = Color.Unspecified,
    disabledForeground: Color = Color.Unspecified,
    disabledBorder: Color = Color.Unspecified,
) = ChoiceButtonColors(
    background.takeOrElse { Color(0xFF131F22) },
    foreground.takeOrElse { Color(0xFFF1F7FB) },
    border.takeOrElse { Color(0xFF37464F) },
    selectedBackground.takeOrElse { Color(0xFF202F36) },
    selectedForeground.takeOrElse { Color(0xFF1CB0F6) },
    selectedBorder.takeOrElse { Color(0xFF3F85A7) },
    disabledBackground.takeOrElse { Color(0xFF202F36) },
    disabledForeground.takeOrElse { Color(0xFF52656D) },
    disabledBorder.takeOrElse { Color(0xFF37464F) },
)

private fun lightChoiceButtonColors(
    background: Color = Color.Unspecified,
    foreground: Color = Color.Unspecified,
    border: Color = Color.Unspecified,
    selectedBackground: Color = Color.Unspecified,
    selectedForeground: Color = Color.Unspecified,
    selectedBorder: Color = Color.Unspecified,
    disabledBackground: Color = Color.Unspecified,
    disabledForeground: Color = Color.Unspecified,
    disabledBorder: Color = Color.Unspecified,
) = ChoiceButtonColors(
    background.takeOrElse { Color(0xFFFFFFFD) }, // Snow-ish (would be pure white)
    foreground.takeOrElse { Color(0xFF4B4B4B) }, // Eel
    border.takeOrElse { Color(0xFFE5E5E5) }, // Swan
    selectedBackground.takeOrElse { Color(0xFFDDF4FF) },
    selectedForeground.takeOrElse { Color(0xFF1CB0F6) },
    selectedBorder.takeOrElse { Color(0xFF84D8FF) },
    disabledBackground.takeOrElse { Color(0xFFF7F7F7) }, // Polar
    disabledForeground.takeOrElse { Color(0xFFAFAFAF) }, // Hare
    disabledBorder.takeOrElse { Color(0xFFE5E5E5) }, // Swan
)

@Preview(name = "Day mode", showBackground = true, uiMode = UI_MODE_NIGHT_NO)
@Preview(name = "Night mode", showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun ChoiceButtonPreview() {
    MyDesignSystemTheme {
        var selected by remember { mutableStateOf(false) }
        ChoiceButton(
            selected = selected,
            onClick = { selected = !selected }
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "ni  hon  go", fontSize = 12.sp, color = if (selected) LocalTextStyle.current.color else Color(0xFFAFAFAF))
                Text(text = "日本語", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))
                Text(text = "Romanized")
            }
        }
    }
}

@Preview(name = "Day mode", showBackground = true, uiMode = UI_MODE_NIGHT_NO)
@Preview(name = "Night mode", showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun ChoiceButtonFillSizePreview() {
    MyDesignSystemTheme {
        var selected by remember { mutableStateOf(false) }
        ChoiceButton(
            selected = selected,
            onClick = { selected = !selected },
            modifier = Modifier.size(256.dp)
        ) {
            Text(text = "日本語", fontSize = 24.sp, fontWeight = FontWeight.Bold, modifier = Modifier.border(1.dp, Color.Red))
        }
    }
}
