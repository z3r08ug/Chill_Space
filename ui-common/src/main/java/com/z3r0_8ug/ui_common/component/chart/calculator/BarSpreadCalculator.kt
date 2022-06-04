package com.getelements.elements.ui.component.chart.calculator

import com.getelements.elements.ui.component.chart.ChartValue
import java.math.BigInteger

internal class BarSpreadCalculator {
  fun calculateSpread(items: List<ChartValue>): BarSpread<BigInteger> {
    // NOTE: min and max are calculated by the abs values (but stored as the original number)
    var minNegative: BigInteger? = null
    var maxNegative: BigInteger? = null
    var minPositive: BigInteger? = null
    var maxPositive: BigInteger? = null

    // NOTE: Due to https://youtrack.jetbrains.com/issue/KT-7186 we do know better than the compiler there
    // and use !! for the "smart cast"
    items.forEach {
      if (it.value < BigInteger.ZERO) {
        if (maxNegative == null || it.value < maxNegative!!) {
          maxNegative = it.value
        }

        if (minNegative == null || it.value > minNegative!!) {
          minNegative = it.value
        }
      } else {
        if (maxPositive == null || it.value > maxPositive!!) {
          maxPositive = it.value
        }

        if (minPositive == null || it.value < minPositive!!) {
          minPositive = it.value
        }
      }
    }

    val negativeSpread = maxNegative?.let {
      val min = minNegative ?: it
      val delta = when {
        items.size <= 1 -> -it
        else -> BigInteger.ZERO - it
      }

      NumericalSpread(
        minValue = min,
        maxValue = it,
        delta = delta
      )
    }

    val positiveSpread = maxPositive?.let {
      val min = minPositive ?: it
      val delta = when {
        items.size <= 1 -> it
        else -> it - BigInteger.ZERO
      }

      NumericalSpread(
        minValue = min,
        maxValue = it,
        delta = delta
      )
    }

    val delta = negativeSpread?.let { neg ->
      positiveSpread?.let { pos ->
        pos.maxValue - neg.maxValue
      } ?: neg.delta
    } ?: positiveSpread?.delta

    return BarSpread(
      positiveSpread = positiveSpread,
      negativeSpread = negativeSpread,
      delta = delta ?: BigInteger.ZERO
    )
  }

  data class BarSpread<T: Number>(
    val positiveSpread: NumericalSpread<T>?,
    val negativeSpread: NumericalSpread<T>?,
    val delta: T
  )

  data class NumericalSpread<T: Number>(
    val minValue: T,
    val maxValue: T,
    val delta: T
  )
}