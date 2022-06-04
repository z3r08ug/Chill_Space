package com.getelements.elements.ui.component.chart

import androidx.compose.ui.graphics.Color

/**
 * @param labelColor The color to use for the xAxis labels (dates determined from the [DateRange])
 * @param barColor The coloring to use for the Positive and Negative Bars. If a [ChartValue] indicates
 *                 that it is a `bridge` then the [bridgeBarColor] will be used instead of this.
 * @param bridgeBarColor The color to use for Bars that are defined as a `bridge` in the [ChartValue]
 */
data class ChartColors(
    val labelColor: Color,
    val barColor: ColorGradient,
    val bridgeBarColor: Color = labelColor
)