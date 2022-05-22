package dev.sebastiano.customthemeplayground.ui.theme

import androidx.compose.foundation.Indication
import androidx.compose.foundation.IndicationInstance
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.unit.Dp

object MyIndication : Indication {

    private class MyIndicationInstance(
        private val isPressed: State<Boolean>,
        private val fillColor: Color,
        private val strokeColor: Color,
        private val strokeWidth: Dp,
    ) : IndicationInstance {

        override fun ContentDrawScope.drawIndication() {
            drawContent()
            if (isPressed.value) {
                drawRect(color = fillColor, size = size, style = Fill)
                drawRect(color = strokeColor, size = size, style = Stroke(strokeWidth.value))
            }
        }
    }

    @Composable
    override fun rememberUpdatedInstance(interactionSource: InteractionSource): IndicationInstance {
        val isPressed = interactionSource.collectIsPressedAsState()
        return MyIndicationInstance(
            isPressed = isPressed,
            fillColor = MyTheme.colors.indication.fill,
            strokeColor = MyTheme.colors.indication.stroke,
            strokeWidth = MyTheme.metrics.indication.strokeWidth,
        )
    }
}

@Immutable
data class IndicationColors(
    val fill: Color,
    val stroke: Color,
)

@Composable
fun indicationColors(
    fill: Color = Color.Unspecified,
    stroke: Color = Color.Unspecified,
) = indicationColors(isSystemInDarkTheme(), fill, stroke)

fun indicationColors(
    darkTheme: Boolean,
    fill: Color = Color.Unspecified,
    stroke: Color = Color.Unspecified,
) = if (darkTheme) lightIndicationColors(fill, stroke) else darkIndicationColors(fill, stroke)

private fun lightIndicationColors(
    fill: Color = Color.Unspecified,
    stroke: Color = Color.Unspecified,
) = IndicationColors(
    fill = fill.takeOrElse { Color(0xFFDDF4FF) },
    stroke = stroke.takeOrElse { Color(0xFF84D8FF) },
)

private fun darkIndicationColors(
    fill: Color = Color.Unspecified,
    stroke: Color = Color.Unspecified,
) = IndicationColors(
    fill = fill.takeOrElse { Color(0xFF202F36) },
    stroke = stroke.takeOrElse { Color(0xFF3F85A7) },
)
