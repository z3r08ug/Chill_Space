package com.getelements.elements.ui.framework.util

import java.text.DecimalFormatSymbols

class TextNumberFormatter(
  val fractionalDigits: Int,
  val prefix: String? = null,
  val suffix: String? = null,
) {
  private val numberRegex = Regex("[0-9]")
  val formatSymbols = DecimalFormatSymbols()

  fun format(number: String): String {
    return StringBuilder(number).apply {
      ensurePrefix(this)
      ensureSuffix(this)

      enforceDecimalPlaces(this, fractionalDigits)
      enforceWholeNumber(this)
      enforceGroupSeparators(this)
    }.toString()
  }

  /**
   * Makes sure that the prefix exists when we have data entered, and is
   * removed when it's the only remaining text
   */
  fun ensurePrefix(text: StringBuilder) {
    val pre = prefix
    if (text.isBlank() || pre == null) {
      return
    }

    // Add the symbol when we have a number, or remove it if it's the only text
    if (!text.startsWith(pre)) {
      text.insert(0, pre)
    } else if (text.length == pre.length) {
      text.delete(0, pre.length)
    }
  }

  /**
   * Makes sure that the suffix exists when we have data entered, and is
   * removed when it's the only remaining text
   */
  fun ensureSuffix(text: StringBuilder) {
    val suf = suffix
    if (text.isBlank() || suf == null) {
      return
    }

    // Add the symbol when we have a number, or remove it if it's the only text
    if (!text.endsWith(suf)) {
      text.insert(text.length, suf)
    } else if (text.length == suf.length) {
      text.delete(0, suf.length)
    }
  }

  /**
   * Makes sure that if a decimal is defined that the correct
   * amount of digits are displayed afterwards.
   */
  fun enforceDecimalPlaces(text: StringBuilder, decimalPlaces: Int) {
    if (!text.contains(formatSymbols.decimalSeparator)) {
      return
    }

    val suffixLength = suffix?.length ?: 0
    val end = text.length - suffixLength

    // Make sure we always have the appropriate fractional digits
    val firstFractionalPosition = text.lastIndexOf(formatSymbols.decimalSeparator) + 1
    val fractionalDigits = end - firstFractionalPosition

    // Add any missing fractional digits
    if (fractionalDigits < decimalPlaces) {
      val fill = (0 until decimalPlaces - fractionalDigits).map { formatSymbols.zeroDigit }.joinToString(separator = "")
      text.insert(end, fill)
      return
    }

    // Truncate
    var startPosition = firstFractionalPosition + decimalPlaces
    if (decimalPlaces == 0) {
      startPosition -= 1
    }

    text.delete(startPosition, end)
  }

  /**
   * Makes sure that we always have a whole number when a decimal is present.
   */
  fun enforceWholeNumber(text: StringBuilder) {
    if (!text.contains(formatSymbols.decimalSeparator)) {
      return
    }

    val startPosition = prefix?.length ?: 0
    val firstDigit = text.elementAt(startPosition).toString()

    // If the first digit isn't a number then we can assume it's a decimal separator
    if (!numberRegex.matches(firstDigit)) {
      text.insert(startPosition, formatSymbols.zeroDigit.toString())
    }
  }

  /**
   * Makes sure that the number portion correctly handles thousand separators
   */
  fun enforceGroupSeparators(text: StringBuilder) {
    if (text.isBlank() || text.length < 3) {
      return
    }

    // Find the range for the whole number
    var decimalPosition = text.lastIndexOf(formatSymbols.decimalSeparator)
    if (decimalPosition == -1) {
      val suffixLength = suffix?.length ?: 0
      decimalPosition = text.length - suffixLength
    }

    val start = prefix?.length ?: 0
    reformatWholeNumber(text, start, decimalPosition)
  }

  /**
   * Reformats the whole number portion of the [text] represented by the
   * data between [start] and [end]. This removes any previous formatting and
   * adds the group separators.
   *
   * @param text The [StringBuilder] to modify the whole number formatting for
   * @param start The position that represents the first character in the whole number.
   *              Unless there is any formatting applied before the number this will
   *              typically be `0`
   * @param end The position that represents the last character in the whole number.
   *            This may be the end of the input or the position where the decimal
   *            separator exists.
   */
  private fun reformatWholeNumber(text: StringBuilder, start: Int, end: Int) {
    // Get the number text without any formatting
    val numberBuilder = StringBuilder()
    text.substring(0, end).filterTo(numberBuilder) {
      numberRegex.matches(it.toString())
    }

    // Walk backwards in sets of 3 adding the group separator
    var position = numberBuilder.length - 3
    while (position > 0) {
      numberBuilder.insert(position, formatSymbols.groupingSeparator.toString())
      position -= 3
    }

    // Actually updates the text
    text.replace(start, end, numberBuilder.toString())
  }
}