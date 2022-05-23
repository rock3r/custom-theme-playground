package dev.sebastiano.customthemeplayground.widgets

import android.content.res.Configuration
import android.util.Log
import androidx.annotation.FloatRange
import androidx.annotation.Px
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.progressSemantics
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import dev.sebastiano.customthemeplayground.ui.theme.MyDesignSystemTheme
import dev.sebastiano.customthemeplayground.ui.theme.MyTheme

@Composable
fun ProgressBar(
    @FloatRange(from = 0.0, to = 1.0) progress: Float,
    modifier: Modifier = Modifier,
    colors: ProgressBarColors = MyTheme.colors.progressBar,
) {
    check(progress in 0f..1f) { "Progress must be in the [0f, 1f] range (inclusive)" }
    val baseCornerRadiusDp = MyTheme.metrics.progressBar.cornerRadius
    val highlightMarginDp = MyTheme.metrics.progressBar.highlightHorizontalMargin
    val progressBounds = Bounds()
    val highlightBounds = Bounds()

    val animatedProgress by animateFloatAsState(targetValue = progress)

    Box(
        modifier = modifier
            .progressSemantics(progress)
            .clip(RoundedCornerShape(baseCornerRadiusDp))
            .defaultMinSize(minHeight = MyTheme.metrics.progressBar.minHeight)
            .drawWithContent {
                @Px val baseCornerRadiusPx = baseCornerRadiusDp.toPx()

                // Track
                drawRect(color = colors.track)

                // Progress
                updateProgressBounds(animatedProgress, baseCornerRadiusPx, progressBounds)
                drawRoundRect(
                    color = colors.bar,
                    topLeft = progressBounds.offset,
                    size = progressBounds.size,
                    cornerRadius = CornerRadius(baseCornerRadiusPx, baseCornerRadiusPx)
                )

                // Progress highlight
                @Px val highlightCornerRadiusPx = size.height * 3 / 20
                @Px val highlightHorizontalMarginPx = highlightMarginDp.toPx()
                updateHighlightBounds(progressBounds, baseCornerRadiusPx, highlightCornerRadiusPx, highlightHorizontalMarginPx, highlightBounds)
                drawRoundRect(
                    color = colors.barHighlight,
                    topLeft = highlightBounds.offset,
                    size = highlightBounds.size,
                    cornerRadius = CornerRadius(highlightCornerRadiusPx, highlightCornerRadiusPx)
                )
            }

    )
}

private fun DrawScope.updateProgressBounds(
    progress: Float,
    @Px cornerRadiusPx: Float,
    progressBounds: Bounds,
) {
    if (size.isEmpty()) {
        progressBounds.apply {
            offset = Offset.Zero
            size = Size.Zero
        }
        return
    }
    Log.e("Progress Calc", "Size: $size")
    val width = size.width

    val isLtr = layoutDirection == LayoutDirection.Ltr
    val barStart = if (isLtr) -cornerRadiusPx else (1f - progress) * width
    val progressWidth = progress * width + cornerRadiusPx

    progressBounds.apply {
        offset = Offset(barStart, 0f)
        size = Size(progressWidth, this@updateProgressBounds.size.height)
        Log.e("Progress Calc", "Progress size: $size, pos: $offset")
    }
}

private fun updateHighlightBounds(
    progressBounds: Bounds,
    @Px progressCornerRadiusPx: Float,
    @Px highlightBarCornerRadiusPx: Float,
    @Px highlightBarMarginHorizontalPx: Float,
    highlightBounds: Bounds,
) {
    if (progressBounds.size.isEmpty() || progressBounds.size.width < highlightBarMarginHorizontalPx * 2) {
        highlightBounds.apply {
            offset = Offset.Zero
            size = Size.Zero
        }
        return
    }

    val halfHeight = progressBounds.size.height / 2f
    val highlightBarHeight = highlightBarCornerRadiusPx * 2

    highlightBounds.apply {
        offset = Offset(
            x = progressBounds.offset.x + highlightBarMarginHorizontalPx + progressCornerRadiusPx,
            y = progressBounds.offset.y + halfHeight - highlightBarHeight
        )
        size = Size(
            width = progressBounds.size.width - highlightBarMarginHorizontalPx * 2 - progressCornerRadiusPx,
            height = highlightBarHeight
        )
    }
}

@Stable
private class Bounds {

    @Stable
    var offset by mutableStateOf(Offset.Zero)

    @Stable
    var size by mutableStateOf(Size.Zero)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Bounds

        if (offset != other.offset) return false
        if (size != other.size) return false

        return true
    }

    override fun hashCode(): Int {
        var result = offset.hashCode()
        result = 31 * result + size.hashCode()
        return result
    }
}

@Immutable
data class ProgressBarColors(
    val bar: Color,
    val barHighlight: Color,
    val track: Color,
) {

    fun withDefaultsFrom(defaults: ProgressBarColors) = ProgressBarColors(
        bar = bar.takeOrElse { defaults.bar },
        barHighlight = barHighlight.takeOrElse { defaults.barHighlight },
        track = track.takeOrElse { defaults.track },
    )
}

fun progressBarColors(
    darkTheme: Boolean,
    background: Color = Color.Unspecified,
    foreground: Color = Color.Unspecified,
    lip: Color = Color.Unspecified,
) = if (darkTheme) {
    darkProgressBarColors(background, foreground, lip)
} else {
    lightProgressBarColors(background, foreground, lip)
}

@Composable
fun progressBarColors(
    bar: Color = Color.Unspecified,
    barHighlight: Color = Color.Unspecified,
    track: Color = Color.Unspecified,
) = if (isSystemInDarkTheme()) {
    darkProgressBarColors(bar, barHighlight, track)
} else {
    lightProgressBarColors(bar, barHighlight, track)
}

private fun darkProgressBarColors(
    bar: Color = Color.Unspecified,
    barHighlight: Color = Color.Unspecified,
    track: Color = Color.Unspecified,
) = ProgressBarColors(
    bar.takeOrElse { Color(0xFF99E439) },
    barHighlight.takeOrElse { Color(0xFFADEA61) },
    track.takeOrElse { Color(0xFF38474E) },
)

private fun lightProgressBarColors(
    bar: Color = Color.Unspecified,
    barHighlight: Color = Color.Unspecified,
    track: Color = Color.Unspecified,
) = ProgressBarColors(
    bar.takeOrElse { Color(0xFF60E509) },
    barHighlight.takeOrElse { Color(0xFF80EB3A) },
    track.takeOrElse { Color(0xFFE5E5E5) },
)

@Preview(name = "Light theme", showBackground = true)
@Preview(name = "Dark theme", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ProgressBarPreview() {
    MyDesignSystemTheme {
        Column(
            modifier = Modifier.background(MyTheme.colors.windowBackground),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ProgressBar(0f, Modifier.fillMaxWidth())
            ProgressBar(.1f, Modifier.fillMaxWidth())
            ProgressBar(.25f, Modifier.fillMaxWidth())
            ProgressBar(.18f, Modifier.size(164.dp, 12.dp))
        }
    }
}
