package dev.sebastiano.customthemeplayground.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class Metrics(
    val indication: IndicationMetrics = IndicationMetrics(),
    val button: ButtonMetrics = ButtonMetrics(),
    val progressBar: ProgressBarMetrics = ProgressBarMetrics(),
    val choiceButton: ChoiceButtonMetrics = ChoiceButtonMetrics(),
) {

    @Immutable
    data class IndicationMetrics(val strokeWidth: Dp = 4.dp)

    @Immutable
    data class ButtonMetrics(
        val cornerRadius: Dp = 16.dp,
        val lipSize: Dp = 4.dp,
        val lipSizePressed: Dp = 0.dp,
        val horizontalPadding: Dp = 16.dp,
        val minHeight: Dp = 52.dp,
    )

    @Immutable
    data class ChoiceButtonMetrics(
        val cornerRadius: Dp = 16.dp,
        val borderSize: Dp = 2.dp,
        val lipSize: Dp = 4.dp,
        val lipSizePressed: Dp = 0.dp,
    )

    @Immutable
    data class ProgressBarMetrics(
        val minHeight: Dp = 16.dp,
        val cornerRadius: Dp = 16.dp,
        val highlightHorizontalMargin: Dp = 16.dp,
    )
}

val LocalMetrics = compositionLocalOf { Metrics() }
