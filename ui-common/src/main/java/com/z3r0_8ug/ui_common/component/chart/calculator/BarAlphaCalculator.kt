package com.getelements.elements.ui.component.chart.calculator

import java.math.BigInteger


internal class BarAlphaCalculator {
  companion object {
    private const val MIN = 0.3f
    private const val MAX = 1.0f
    private const val BASELINE = 0.5f
    private const val GREAT_GROWTH = 0.4f
  }

  fun calculateAlpha(value: BigInteger, previousValue: ChartCalculator.BarData?): Float {
    if (previousValue == null || value == previousValue.value) {
      return BASELINE
    }

    // Focus on going positive
    if (previousValue.value < BigInteger.ZERO  && value >= BigInteger.ZERO ) {
      return MAX
    }

    // Try not to Focus going negative
    if (previousValue.value >= BigInteger.ZERO  && value < BigInteger.ZERO ) {
      return MIN
    }

    // Creep upwards with continued improvement
    val adjustedMin = if (value > previousValue.value && previousValue.alpha > BASELINE) {
      previousValue.alpha + 0.05f
    } else {
      MIN
    }

    if (adjustedMin >= MAX) {
      return MAX
    }

    // Determine the Alpha based on the growth rate
    val termGrowthRate = ((value - previousValue.value).toFloat() / previousValue.value.toFloat())
    val growthAlpha = (termGrowthRate / GREAT_GROWTH) * (1 - BASELINE)
    val alpha = growthAlpha + BASELINE

    return alpha.coerceIn(adjustedMin, MAX)
  }
}