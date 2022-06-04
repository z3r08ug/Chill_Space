package com.getelements.elements.ui.component.chart.calculator

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.getelements.elements.common.time.DateRange
import com.getelements.elements.ui.component.chart.ChartColors
import com.getelements.elements.ui.component.chart.ChartValue
import com.getelements.elements.ui.shared.ext.DrawTextStyle
import com.getelements.elements.ui.shared.ext.toPx
import java.math.BigInteger
import java.time.Period
import java.time.format.DateTimeFormatter

internal class ChartCalculatorImpl(
  private val spreadCalculator: BarSpreadCalculator,
  private val alphaCalculator: BarAlphaCalculator
): ChartCalculator {

  override fun calculateChartData(
    items: List<ChartValue>,
    dateRange: DateRange,
    density: Density,
    canvasSize: IntSize,
    colors: ChartColors
  ): ChartCalculator.ChartData {
    val labelStyle = DrawTextStyle(
      color = colors.labelColor,
      density = density,
      fontSize = 10.sp,
      fontWeight = FontWeight.Bold,
      // NOTE: this should be 0.33.sp, however something in our text drawing isn't correct, 0.1.sp visually matches
      letterSpacing = 0.1.sp
    )

    val textHeight = maxOf(16.dp.toPx(density), labelStyle.nativePaint.descent() - labelStyle.nativePaint.ascent())
    val labelHeight = textHeight + 16.dp.toPx(density)

    val barData = calculateBarData(density, canvasSize, labelHeight, items, colors)
    val bars = getBarData(barData, canvasSize, labelHeight)
    val labels = getLabelData(density, canvasSize, labelHeight, labelStyle, barData, dateRange)

    return ChartCalculator.ChartData(
      bars = bars,
      labels = labels
    )
  }

  fun getBarData(
    data: IntermediaryBarData,
    canvasSize: IntSize,
    labelHeight: Float
  ): List<ChartCalculator.BarData> {
    if (data.items.isEmpty()) {
      return emptyList()
    }

    // Used to gravitate the bars to the end (right) of the canvas
    val usedWidth = data.items.count() * data.barSize.width + maxOf(0, data.items.count() -1) * data.barSize.spacing
    val leftOffset = canvasSize.width - usedWidth

    var previousItem: ChartCalculator.BarData? = null
    return data.items.mapIndexedNotNull { index, item ->
      val spread = when {
        item.value >= BigInteger.ZERO -> data.itemSpread.positiveSpread
        else -> data.itemSpread.negativeSpread
      } ?: return@mapIndexedNotNull null

      val maxHeight = when {
        item.value >= BigInteger.ZERO -> data.barSize.maxPositiveHeight
        else -> data.barSize.maxNegativeHeight
      }

      val relativeValue = spread.delta - (spread.maxValue - item.value).abs()
      val percentHeight = when {
        relativeValue > BigInteger.ZERO -> relativeValue.toFloat() / spread.delta.toFloat()
        else -> 0f
      }

      val height = maxOf(data.barSize.minHeight, percentHeight * maxHeight)
      val top = when {
        item.value >= BigInteger.ZERO -> data.barSize.maxPositiveHeight - height
        else -> data.barSize.maxPositiveHeight + labelHeight
      }

      val left = leftOffset + index * (data.barSize.width + data.barSize.spacing)
      val brush = when (item.bridge) {
        true -> data.brushes.bridge
        else -> data.brushes.normal
      }

      ChartCalculator.BarData(
        value = item.value,
        topLeft = Offset(left, top),
        size = Size(data.barSize.width, height),
        brush = brush,
        alpha = alphaCalculator.calculateAlpha(item.value, previousItem)
      ).also {
        previousItem = it
      }
    }
  }

  fun getLabelData(
    density: Density,
    canvasSize: IntSize,
    labelHeight: Float,
    style: DrawTextStyle,
    barData: IntermediaryBarData,
    dateRange: DateRange
  ): List<ChartCalculator.LabelData> {
    val labelFormatter = DateTimeFormatter.ofPattern("MMM yy")
    val rangeDelta = Period.between(dateRange.start.toLocalDate(), dateRange.end.toLocalDate())


    // NOTE:
    // This isn't 100% accurate because the character width and kerning can be different between different character sets
    // but this is close enough for our use here.
    val maxLabelWidth = style.nativePaint.measureText(dateRange.start.format(labelFormatter).uppercase())
    val labelWidth = maxOf(40.dp.toPx(density), maxLabelWidth)
    val minSpacing = 10.dp.toPx(density)


    // Determine how may labels we should use
    val requestedMaxLabels = when {
      rangeDelta.toTotalMonths() <= 3 -> 4
      else -> 7
    }

    val labelExtension = labelWidth - barData.barSize.width
    val availableWidth = canvasSize.width + labelExtension
    val calculatedMaxLabels = maxPossibleItems(availableWidth, labelWidth, minSpacing)
    val maxLabels = minOf(requestedMaxLabels, calculatedMaxLabels)


    // Generate the labels
    val labels = dateRange.calculateStepDates(maxLabels -1).map {
      it.format(labelFormatter).uppercase()
    }


    // Fix positioning and generate LabelDrawData
    val usedLabelWidth = maxLabels * labelWidth
    val spacing = (availableWidth - usedLabelWidth) / maxOf(1, maxLabels -1)

    val yPos = barData.barSize.maxPositiveHeight + (labelHeight / 2)
    val xStart = -(labelExtension / 2).toInt()

    return labels.mapIndexed { index, item ->
      val textWidth = style.nativePaint.measureText(item)
      val xShift = (labelWidth - textWidth) / 2
      val xPos = xStart + xShift + index * (labelWidth + spacing)

      ChartCalculator.LabelData(
        text = item,
        position = Offset(x = xPos, y = yPos),
        style = style
      )
    }
  }

  fun calculateBarData(
    density: Density,
    canvasSize: IntSize,
    labelHeight: Float,
    items: List<ChartValue>,
    colors: ChartColors
  ): IntermediaryBarData {
    val barWidth = 24.dp.toPx(density)
    val barSpacing = 4.dp.toPx(density)
    val maxBarCount = maxPossibleItems(canvasSize.width.toFloat(), barWidth, barSpacing)

    // Limit to the maxBarCount (end of list)
    val displayableItems = items.subList(
      fromIndex = maxOf(0, items.size - maxBarCount),
      toIndex = items.size
    )

    val spread = spreadCalculator.calculateSpread(displayableItems)
    val barSize = calculateBarSize(density, canvasSize, spread, labelHeight, barWidth, barSpacing)
    val brushes = buildBrushes(colors, barSize, labelHeight)

    return IntermediaryBarData(
      items = displayableItems,
      itemSpread = spread,
      barSize = barSize,
      brushes = brushes
    )
  }

  private fun buildBrushes(
    colors: ChartColors,
    barSize: BarSize,
    labelHeight: Float
  ): Brushes {
    val gradientHeight = labelHeight + (barSize.totalHeight * 2)
    val gradientTop = barSize.maxPositiveHeight - barSize.totalHeight

    val definedBrush = Brush.verticalGradient(
      *colors.barColor.gradientColors(),
      startY = gradientTop,
      endY = gradientHeight + gradientTop
    )

    return Brushes(
      normal = definedBrush,
      bridge = SolidColor(colors.bridgeBarColor)
    )
  }

  fun calculateBarSize(
    density: Density,
    canvasSize: IntSize,
    spread: BarSpreadCalculator.BarSpread<BigInteger>,
    labelHeight: Float,
    width: Float,
    spacing: Float
  ): BarSize {
    val minHeight = 24.dp.toPx(density)

    val availableHeight = canvasSize.height - labelHeight

    val maxPositiveHeight = when {
      spread.positiveSpread != null && spread.negativeSpread != null -> {
        val positiveDelta = spread.positiveSpread.delta.toFloat()
        val calculatedHeight = availableHeight * (positiveDelta / spread.delta.toFloat())

        // Make sure to leave at least minHeight for the negative
        calculatedHeight.coerceIn(minHeight, availableHeight - minHeight)
      }
      spread.positiveSpread != null -> availableHeight
      else -> 0f
    }

    val maxNegativeHeight = availableHeight - maxPositiveHeight

    return BarSize(
      width = width,
      spacing = spacing,
      minHeight = minHeight,
      maxPositiveHeight = maxPositiveHeight,
      maxNegativeHeight = maxNegativeHeight,
      totalHeight = availableHeight
    )
  }

  fun maxPossibleItems(availableSize: Float, itemSize: Float, spacing: Float): Int {
    val count = availableSize / (itemSize + spacing).toInt()
    val remainder = availableSize % (itemSize + spacing).toInt()

    // Check if an item can fit in to the remainder since the last item doesn't need spacing
    return when (remainder >= itemSize) {
      true -> count + 1
      false -> count
    }.toInt()
  }

  data class IntermediaryBarData(
    val items: List<ChartValue>,
    val itemSpread: BarSpreadCalculator.BarSpread<BigInteger>,
    val barSize: BarSize,
    val brushes: Brushes
  )

  data class Brushes(
    val normal: Brush,
    val bridge: Brush
  )

  data class BarSize(
    val width: Float,
    val spacing: Float,
    val minHeight: Float,
    val maxPositiveHeight: Float,
    val maxNegativeHeight: Float,
    val totalHeight: Float
  )
}