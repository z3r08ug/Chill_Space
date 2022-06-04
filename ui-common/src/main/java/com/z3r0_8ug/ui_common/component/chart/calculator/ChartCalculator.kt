package com.getelements.elements.ui.component.chart.calculator

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntSize
import com.getelements.elements.common.time.DateRange
import com.getelements.elements.ui.component.chart.ChartColors
import com.getelements.elements.ui.component.chart.ChartValue
import com.getelements.elements.ui.shared.ext.DrawTextStyle
import java.math.BigInteger

internal interface ChartCalculator {
  fun calculateChartData(
    items: List<ChartValue>,
    dateRange: DateRange,
    density: Density,
    canvasSize: IntSize,
    colors: ChartColors
  ): ChartData

  data class BarData(
    val value: BigInteger,
    val topLeft: Offset,
    val size: Size,
    val brush: Brush,
    val alpha: Float
  )

  data class LabelData(
    val text: String,
    val position: Offset,
    val style: DrawTextStyle
  )

  data class ChartData(
    val bars: List<BarData>,
    val labels: List<LabelData>
  )
}