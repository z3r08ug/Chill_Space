package com.getelements.elements.ui.component.chart

import java.math.BigInteger

/**
 * @param value The numerical value that represents the amount to display for a single Bar
 * @param bridge `true` if this [ChartValue] is generated to fill (bridge) a gap of missing data between
 *                known values.
 */
data class ChartValue(
  val value: BigInteger,
  val bridge: Boolean = false
)