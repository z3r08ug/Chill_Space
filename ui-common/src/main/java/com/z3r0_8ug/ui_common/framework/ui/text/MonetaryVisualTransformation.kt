package com.z3r0_8ug.ui_common.framework.ui.text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.text.SpanStyle
import com.getelements.elements.ui.framework.util.TextNumberFormatter
import com.z3r0_8ug.ui_common.theme.AppTheme
import java.util.*

private val monetaryTextNumberFormatter by lazy {
  val currency = Currency.getInstance(Locale.getDefault())

  TextNumberFormatter(
    fractionalDigits = currency.defaultFractionDigits,
    prefix = currency.symbol,
    suffix = null
  )
}

/**
 * Provides the visual transformation for numerical entry.
 *
 * **NOTE:**
 * This only handles the visual aspect of the numerical entry, you will need
 * to make sure that the text entry is filtering out non-numerical entry.
 * To filter out non-numerical text entry use [com.getelements.elements.ui.framework.ext.filterNumbers]
 * in the `ViewModel`
 *
 * Usage:
 * ```
 * AppTextField(
 *   ...
 *   visualTransformation = monetaryVisualTransformation()
 * )
 * ```
 */
@Composable
@ReadOnlyComposable
fun monetaryVisualTransformation(): NumberVisualTransformation {
  return NumberVisualTransformation(
    formatter = monetaryTextNumberFormatter,
    formattingStyle = SpanStyle(color = AppTheme.colors.inputLabel)
  )
}