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
        val cornerRadius: Dp = 12.dp,
        val lipSize: Dp = 4.dp,
        val lipSizePressed: Dp = 0.dp,
        val minWidth: Dp = 64.dp,
        val minHeight: Dp = 24.dp
    )
}

val LocalMetrics = compositionLocalOf { Metrics() }