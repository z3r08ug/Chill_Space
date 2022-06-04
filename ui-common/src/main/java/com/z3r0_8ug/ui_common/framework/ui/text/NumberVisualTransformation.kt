package com.z3r0_8ug.ui_common.framework.ui.text

import androidx.annotation.VisibleForTesting
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import com.getelements.elements.ui.framework.util.TextNumberFormatter
import java.text.DecimalFormatSymbols

/**
 * Provides the visual transformation for numerical entry to support:
 * - Show all requested decimal places (fraction digits) when a decimal separator is typed
 * - Show a requested prefix when a number exists
 * - Show a suffix when a number exists
 *
 * **NOTE:**
 * This only handles the visual aspect of the numerical entry, you will need
 * to make sure that the text entry is filtering out non-numerical entry.
 * To filter out non-numerical text entry use [com.getelements.elements.ui.framework.ext.filterNumbers]
 * in the `ViewModel`
 *
 * Usage:
 * ```
 * val currency = Currency.getInstance(Locale.getDefault())
 * val numberVisualTransformation = remember {
 *   NumberVisualTransformation(
 *     formatter = TextNumberFormatter(
 *       fractionDigits = currency.defaultFractionDigits
 *       prefix = currency.symbol
 *     )
 *   )
 * }
 *
 * AppTextField(
 *   ...
 *   visualTransformation = numberVisualTransformation
 * )
 * ```
 */
class NumberVisualTransformation(
  val formatter: TextNumberFormatter,
  val formattingStyle: SpanStyle? = null
) : VisualTransformation {

  override fun filter(text: AnnotatedString): TransformedText {
    if (text.isEmpty()) {
      return TransformedText(
        text = text,
        offsetMapping = OffsetMapping.Identity
      )
    }

    val formattedText = formatter.format(text.text)
    val offsetMap = buildOffsetMap(formattedText, formatter.formatSymbols, formatter.prefix, formatter.suffix)

    return TransformedText(
      text = AnnotatedString(formattedText, getSpanStyles(formattedText)),
      offsetMapping = offsetMap
    )
  }

  private fun getSpanStyles(formattedText: String): List<AnnotatedString.Range<SpanStyle>> {
    // TODO: it would be nice to style all formatting characters (prefix, suffix, thousand separator, added 0s)

    val style = formattingStyle
    if (style == null || formattedText.isEmpty()) {
      return emptyList()
    }

    val prefixRange = formatter.prefix?.let {
      AnnotatedString.Range(style, 0, 1)
    }

    val suffixRange = formatter.suffix?.let {
      AnnotatedString.Range(style, formattedText.length - 1, formattedText.length)
    }

    return mutableListOf<AnnotatedString.Range<SpanStyle>>().apply {
      prefixRange?.let { add(it) }
      suffixRange?.let { add(it) }
    }
  }

  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  fun buildOffsetMap(
    text: String,
    decimalSymbols: DecimalFormatSymbols,
    prefix: String?,
    suffix: String?
  ): MappedOffsetMapping {
    val prefixLength = prefix?.length ?: 0
    val suffixLength = suffix?.length ?: 0

    val map = mutableMapOf<Int, MappedOffsetMapping.TransformedOffsets>()

    var originalPosition = 0
    val bodyText = text.substring(prefixLength, text.length - suffixLength)
    val transformedOffsets = mutableListOf<Int>()

    bodyText.toCharArray().forEachIndexed { index, char ->
      transformedOffsets.add(index + prefixLength)
      if (char != decimalSymbols.groupingSeparator) {
        map[originalPosition++] = MappedOffsetMapping.TransformedOffsets(transformedOffsets.toList())
        transformedOffsets.clear()
      }
    }

    // Fills an end position
    transformedOffsets.add(bodyText.length + prefixLength)
    map[originalPosition++] = MappedOffsetMapping.TransformedOffsets(transformedOffsets.toList())

    return MappedOffsetMapping(map)
  }
}
