package com.z3r0_8ug.ui_common.framework.ui.text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import com.getelements.elements.ui.framework.util.TextNumberFormatter
import com.z3r0_8ug.ui_common.R
import com.z3r0_8ug.ui_common.theme.AppTheme

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
 *   visualTransformation = percentVisualTransformation()
 * )
 * ```
 */
@Composable
@ReadOnlyComposable
fun percentVisualTransformation(
  fractionDigits: Int = 2
): NumberVisualTransformation {
  val formatter = TextNumberFormatter(
    fractionalDigits = fractionDigits,
    prefix = null,
    suffix = stringResource(R.string.percent_symbol)
  )

  return NumberVisualTransformation(
    formatter = formatter,
    formattingStyle = SpanStyle(color = AppTheme.colors.inputLabel)
  )
}