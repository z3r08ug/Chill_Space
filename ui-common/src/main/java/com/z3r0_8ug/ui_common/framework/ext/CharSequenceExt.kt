package com.z3r0_8ug.ui_common.framework.ext

import java.math.BigDecimal
import java.text.DecimalFormatSymbols

private val decimalFormatSymbols = DecimalFormatSymbols()
private val numberReplaceRegex = Regex("[^0-9${decimalFormatSymbols.decimalSeparator}]")
private val numberRegex = Regex("^[0-9]*(${decimalFormatSymbols.decimalSeparator}[0-9]*)?$")

/**
 * Converts the [CharSequence] to a [BigDecimal] if possible by stripping non-numerical
 * characters.
 *
 * @return The [BigDecimal] represented by this sequence or `null` if the sequence couldn't
 *         be coerced in to a [BigDecimal]
 */
fun CharSequence.asBigDecimal(): BigDecimal? {
  if (isBlank()) {
    return null
  }

  // Trim all formatting except the decimal separator
  val strippedNumber = replace(numberReplaceRegex, "")
  if (strippedNumber.isBlank() || !strippedNumber.matches(numberRegex)) {
    return null
  }

  return BigDecimal(strippedNumber)
}

fun <T : CharSequence> T.nullWhenEmpty(): T? {
  return if (isEmpty()) {
    null
  } else {
    this
  }
}