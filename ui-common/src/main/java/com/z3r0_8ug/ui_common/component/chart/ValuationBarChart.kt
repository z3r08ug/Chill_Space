package com.getelements.elements.ui.component.chart

import android.content.res.Configuration
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.getelements.elements.common.time.DateRange
import com.getelements.elements.ui.component.chart.calculator.BarAlphaCalculator
import com.getelements.elements.ui.component.chart.calculator.BarSpreadCalculator
import com.getelements.elements.ui.component.chart.calculator.ChartCalculator
import com.getelements.elements.ui.component.chart.calculator.ChartCalculatorImpl
import com.getelements.elements.ui.shared.ext.drawText
import com.getelements.elements.ui.theme.AppTheme
import java.math.BigInteger
import java.time.OffsetDateTime

internal class ChartPreviewProvider : PreviewParameterProvider<List<ChartValue>> {
  companion object {
    private fun chartValueOf(value: Int, bridge: Boolean = false): ChartValue {
      return ChartValue(
        value = BigInteger.valueOf(value.toLong()),
        bridge = bridge
      )
    }

    val items = sequenceOf(
      listOf(
        chartValueOf(-50), chartValueOf(-40), chartValueOf(-10),
        chartValueOf(30), chartValueOf(-5),
        chartValueOf(35), chartValueOf(60),
        chartValueOf(50), chartValueOf(65), chartValueOf(65),
        chartValueOf(75), chartValueOf(120)
      ),
      listOf(
        chartValueOf(-150), chartValueOf(-120), chartValueOf(-75),
        chartValueOf(-80), chartValueOf(-65), chartValueOf(-50),
        chartValueOf(-60), chartValueOf(-35), chartValueOf(5),
        chartValueOf(-30)
      ),
      listOf(
        chartValueOf(10), chartValueOf(25), chartValueOf(25, true),
        chartValueOf(20), chartValueOf(30), chartValueOf(40)
      ),
      listOf(
        chartValueOf(-45), chartValueOf(-37), chartValueOf(-29),
        chartValueOf(-35), chartValueOf(-28), chartValueOf(-17)
      ),
      listOf(
        chartValueOf(-50)
      ),
      listOf(
        chartValueOf(50)
      )
    )
  }

  override val values = items
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun Preview(
  @PreviewParameter(ChartPreviewProvider::class) items: List<ChartValue>
) {
  AppTheme {
    val chartColors = ChartColors(
      labelColor = AppTheme.colors.text40,
      barColor = ColorGradient(
        listOf(
          AppTheme.colors.secondary,
          AppTheme.colors.qualifiedTerm,
          AppTheme.colors.burnRate
        )
      )
    )

    val dateRange = remember {
      DateRange(OffsetDateTime.now().minusMonths(3), OffsetDateTime.now())
    }

    Box(
      modifier = Modifier
        .background(AppTheme.colors.background)
        .padding(vertical = 16.dp)
    ) {
      ValuationBarChart(
        items = items,
        dateRange = dateRange,
        colors = chartColors
      )
    }
  }
}

/**
 * A Vertical Bar Chart that is used to display numerical changes over a period of time.
 * This chart is not scrollable and the x-axis position will be determined by the values
 * defined in [items].
 *
 * @param items The list of [ChartValue]s that represent the numerical change. These items should be
 *              in the order they should be displayed from the start (left) to end (right) in equal increments.
 * @param dateRange The range of dates that represents the [items] which will be used to generate the x-axis labels
 * @param colors The colors to use when displaying the chart data
 * @param modifier The [Modifier] to apply to the chart
 */
@Composable
fun ValuationBarChart(
  items: List<ChartValue>,
  dateRange: DateRange,
  colors: ChartColors,
  modifier: Modifier = Modifier
) {
  val density = LocalDensity.current
  val calculator: ChartCalculator = remember {
    ChartCalculatorImpl(BarSpreadCalculator(), BarAlphaCalculator())
  }

  // NOTE: the width should be 360.dp, however there appears to be an inconsistency in what the
  // constraints is reporting by a few pixels (when accounting for dp). So we've slightly increased
  // the amount to make sure that we can fit 12 bars.
  BoxWithConstraints(
    modifier = modifier
      .size(
        width = 361.dp,
        height = 238.dp
      )
      .padding(horizontal = 14.dp)
  ) {
    val chartData = remember(items, dateRange, density, constraints, colors) {
      val canvasSize = IntSize(constraints.maxWidth, constraints.maxHeight)
      calculator.calculateChartData(items, dateRange, density, canvasSize, colors)
    }

    Canvas(modifier = Modifier) {
      drawBars(chartData.bars)
      drawXAxisLabels(chartData.labels)
    }
  }
}

private fun DrawScope.drawXAxisLabels(
  labels: List<ChartCalculator.LabelData>
) {
  labels.forEach {
    drawText(
      text = it.text,
      position = it.position,
      style = it.style
    )
  }
}

private fun DrawScope.drawBars(
  bars: List<ChartCalculator.BarData>
) {
  bars.forEach {
    drawRoundRect(
      brush = it.brush,
      topLeft = it.topLeft,
      size = it.size,
      cornerRadius = CornerRadius(it.size.width / 2),
      alpha = it.alpha
    )
  }
}