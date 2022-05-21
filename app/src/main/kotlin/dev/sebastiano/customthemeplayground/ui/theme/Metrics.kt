package dev.sebastiano.customthemeplayground.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class Metrics(
    val indication: Indication = Indication()
) {

    data class Indication(val strokeWidth: Dp = 4.dp)
}

val LocalMetrics = compositionLocalOf { Metrics() }
