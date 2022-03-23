package com.z3r0_8ug.ui_common.framework.ext

import java.text.DecimalFormatSymbols

private val decimalFractionRegex = mutableMapOf<Int, Regex>()

/**
 * Checks if the [requestedValue] is an acceptable number, if so then
 * [onValueChange] is called with the [requestedValue] as a parameter.
 *
 * @param requestedValue The value that needs to be checked against the number formatting
 * @param maxDecimalPlaces The maximum number of decimal places to support
 * @param onValueChange The lambda to call when the [requestedValue] is a correctly formatted number
 */
fun filterNumbers(
  requestedValue: String,
  maxDecimalPlaces: Int = 2,
  onValueChange: (String) -> Unit
) {
  val numberRegex = decimalFractionRegex.getOrPut(maxDecimalPlaces) {
    val decimalSeparator = DecimalFormatSymbols().decimalSeparator
    if (maxDecimalPlaces > 0) {
      Regex("([0-9]+\\$decimalSeparator?[0-9]{0,$maxDecimalPlaces})?")
    } else {
      Regex("([0-9]+)?")
    }
  }

  filterRegex(numberRegex, requestedValue, onValueChange)
}

/**
 * Checks if the [requestedValue] matches the [regex], if so then
 * [onValueChange] is called with the [requestedValue] as a parameter.
 *
 * @param regex The [Regex] to use to check the [requestedValue] against
 * @param requestedValue The value that needs to be checked against the number formatting
 * @param onValueChange The lambda to call when the [requestedValue] is a correctly formatted number
 */
inline fun filterRegex(
  regex: Regex,
  requestedValue: String,
  onValueChange: (String) -> Unit
) {
  if (requestedValue.matches(regex)) {
    onValueChange(requestedValue)
  }
}