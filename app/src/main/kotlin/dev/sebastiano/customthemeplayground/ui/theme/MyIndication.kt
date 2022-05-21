package dev.sebastiano.customthemeplayground.ui.theme

import androidx.compose.foundation.Indication
import androidx.compose.foundation.IndicationInstance
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp

object MyIndication : Indication {

    private class MyIndicationInstance(
        private val isPressed: State<Boolean>,
        private val isHovered: State<Boolean>,
        private val isFocused: State<Boolean>,
        private val pressedColor: Color,
        private val hoveredColor: Color,
        private val focusedColor: Color,
        private val focusedStrokeWidth: Dp,
    ) : IndicationInstance {

        override fun ContentDrawScope.drawIndication() {
            drawContent()
            if (isPressed.value) {
                drawRect(color = pressedColor, size = size)
            } else if (isHovered.value) {
                drawRect(color = hoveredColor, size = size)
            } else if (isFocused.value) {
                drawRect(color = focusedColor, size = size, style = Stroke(width = focusedStrokeWidth.value))
            }
        }
    }

    @Composable
    override fun rememberUpdatedInstance(interactionSource: InteractionSource): IndicationInstance {
        val isPressed = interactionSource.collectIsPressedAsState()
        val isHovered = interactionSource.collectIsHoveredAsState()
        val isFocused = interactionSource.collectIsFocusedAsState()
        return MyIndicationInstance(
            isPressed,
            isHovered,
            isFocused,
            MyTheme.colors.indication.pressed,
            MyTheme.colors.indication.hovered,
            MyTheme.colors.indication.focused,
            MyTheme.metrics.indication.strokeWidth
        )
    }
}
