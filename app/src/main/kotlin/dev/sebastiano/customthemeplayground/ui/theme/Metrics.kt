package dev.sebastiano.customthemeplayground.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class Metrics(
    val indication: IndicationMetrics = IndicationMetrics(),
    val button: ButtonMetrics = ButtonMetrics()
) {

    data class IndicationMetrics(val strokeWidth: Dp = 4.dp)

    data class ButtonMetrics(
        val cornerRadius: Dp = 16.dp,
        val lipSize: Dp = 4.dp,
        val lipSizePressed: Dp = 0.dp,
        val horizontalPadding: Dp = 16.dp,
        val minHeight: Dp = 52.dp
    )
}

val LocalMetrics = compositionLocalOf { Metrics() }
