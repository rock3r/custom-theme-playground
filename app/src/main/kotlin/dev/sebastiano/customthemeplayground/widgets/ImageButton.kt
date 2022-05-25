package dev.sebastiano.customthemeplayground.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.isSpecified
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.semantics.Role

@Composable
fun ImageButton(
    painter: Painter,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    tintColor: Color = Color.Unspecified,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val colorFilter = if (tintColor.isSpecified) ColorFilter.tint(tintColor) else null
    Image(
        painter = painter,
        contentDescription = contentDescription,
        modifier = modifier.clickable(
            enabled = enabled,
            onClick = onClick,
            role = Role.Button,
            interactionSource = interactionSource,
            indication = null
        ),
        colorFilter = colorFilter
    )
}
