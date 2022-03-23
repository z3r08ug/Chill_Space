package com.getelements.elements.ui.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.insets.LocalWindowInsets
import com.z3r0_8ug.ui_common.R
import com.z3r0_8ug.ui_common.theme.AppTheme

/**
 * A container for the content of an edit text to simplify
 * the previewing of different states
 */
private data class PreviewContent(
  val value: String?,
  val placeholder: String? = null,
  val errorMessage: String? = null,
  val hasError: Boolean = false
)

// NOTE: @PreviewParameter doesn't accept private classes so we just use this as
// a holder for th preview content for now
private class InputInfoProvider : PreviewParameterProvider<PreviewContent> {
  companion object {
    val items = sequenceOf(
      PreviewContent("$150,000"),
      PreviewContent("", placeholder = "Your Full Name"),
      PreviewContent("", errorMessage = "Name is required", hasError = true)
    )
  }

  override val values = items
}

@Preview
@Composable
private fun PreviewAppTextField() {
  LazyColumn {
    item {
      AppTheme {
        Box(
          modifier = Modifier
            .background(AppTheme.colors.background)
            .padding(8.dp)
        ) {
          var value by remember { mutableStateOf("")}

          AppTextField(
            value = value,
            onValueChange = {
              value = it
            },
            placeholder = "$0",
            style = TextFieldStyle.UNDERLINE
          )
        }
      }

      AppTheme(isDark = true) {
        Box(
          modifier = Modifier
            .background(AppTheme.colors.background)
            .padding(8.dp)
        ) {
          var value by remember { mutableStateOf("")}

          AppTextField(
            value = value,
            onValueChange = {
              value = it
            },
            placeholder = "$0",
            style = TextFieldStyle.UNDERLINE
          )
        }
      }

      Spacer(modifier = Modifier.height(24.dp))
    }

    items(InputInfoProvider.items.toList()) { content ->
      AppTheme {
        Box(
          modifier = Modifier
            .background(AppTheme.colors.background)
            .padding(8.dp)
        ) {
          AppTextField(
            value = content.value,
            placeholder = content.placeholder,
            errorMessage = content.errorMessage,
            hasError = content.hasError,
            label = "Preview"
          )
        }
      }

      AppTheme(isDark = true) {
        Box(
          modifier = Modifier
            .background(AppTheme.colors.background)
            .padding(8.dp)
        ) {
          AppTextField(
            value = content.value,
            placeholder = content.placeholder,
            errorMessage = content.errorMessage,
            hasError = content.hasError,
            label = "Preview"
          )
        }
      }

      Spacer(modifier = Modifier.height(24.dp))
    }
  }
}

val AppTextFieldTopPadding = 8.dp
enum class TextFieldStyle {
  OUTLINE,
  UNDERLINE
}

/**
 * A TextField that follows the styling needed for our app.
 *
 * **NOTE:**
 * The TextField has a top padding of `8.dp` to accommodate the "docked" label. See [AppTextFieldTopPadding]
 *
 * @param value the input text to be shown in the text field
 * @param modifier The [Modifier] for the view
 * @param onValueChange the callback that is triggered when the input service updates the text. An
 *                      updated text comes as a parameter of the callback
 * @param label The text that is displayed above the field when it's selected or has data.
 * @param placeholder the optional placeholder to be displayed when the text field is in focus and
 *                    the input text is empty.
 * @param helperText The text to show below the input field that supports the input. This will
 *                   usually be used for things like "* Required".
 *                   NOTE: This will be replaced by the [inputInfo] `errorText` when there's an
 *                   error represented by `inputInfo.hasError`
 * @param errorMessage The text to show below the input field when [hasError] is true
 * @param hasError indicates if the text field's current value is in error.
 * @param trailingIcon The composable to show as the trailing (end) icon
 * @param style The style to use for the AppTextField
 * @param keyboardOptions The options to use for the keyboard such as password input
 * @param singleLine `true` if this field should be a single-line entry
 * @param alwaysLayoutHelperText `true` if the Helper/Error text should always be included when
 *                                laying out this view. This is to provide stable sizing when
 *                                errors or help is dynamic / not always present.
 * @param visualTransformation The [VisualTransformation] to apply to the text entered in this field;
 *                             A common transformation is the [PasswordVisualTransformation]
 * @param readOnly controls the editable state of the [AppTextField]. When `true`, the text
 *                 field can not be modified, however, a user can focus it and copy text from it.
 *                 Read-only text fields are usually used to display pre-filled forms that user can
 *                 not edit
 */
@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun AppTextField(
  value: String?,
  modifier: Modifier = Modifier,
  onValueChange: (String) -> Unit = {},
  label: String? = null,
  placeholder: String? = null,
  helperText: String? = null,
  errorMessage: String? = null,
  hasError: Boolean = false,
  trailingIcon: @Composable (() -> Unit)? = null,
  style: TextFieldStyle = TextFieldStyle.OUTLINE,
  keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
  keyboardActions: KeyboardActions = KeyboardActions.Default,
  singleLine: Boolean = false,
  alwaysLayoutHelperText: Boolean = false,
  visualTransformation: VisualTransformation = VisualTransformation.None,
  readOnly: Boolean = false,
  enabled: Boolean = true,
  colors: TextFieldColors = AppTheme.colors.textField()
) {
  // NOTE:
  // We wrap this in an AppTheme so that the selection handles and
  // selection highlighting use the correct colors (secondary)
  AppTheme(
    colors = AppTheme.colors.copy(
      primary = AppTheme.colors.secondary
    )
  ) {
    // Note: we make sure to set the endIcon to null when not needed to prevent
    // unexpected padding at the end of the TextField
    val endIcon: (@Composable () -> Unit)? = if (hasError || trailingIcon != null) {
      @Composable {
        EndIcon(hasError, trailingIcon)
      }
    } else {
      null
    }

    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    var bringToFocus by remember { mutableStateOf(false)}
    val imeInset = LocalWindowInsets.current.ime

    Column(
      modifier = modifier.bringIntoViewRequester(bringIntoViewRequester)
    ) {
      val textFieldModifier = remember {
        Modifier
          .fillMaxWidth()
          .onFocusChanged {
            bringToFocus = it.isFocused
          }
      }

      // Makes sure that the view is visible when Focused w/the IME visible
      LaunchedEffect(bringToFocus, imeInset.isVisible, imeInset.animationInProgress) {
        if (bringToFocus && imeInset.isVisible && !imeInset.animationInProgress) {
          // NOTE: The relocationRequester doesn't currently (Oct. 2, 2021) take the IME
          // in to account when shifting the composable in to the view, meaning that the
          // composable will still be behind the IME.
          // Related issue: https://issuetracker.google.com/issues/192043120
          bringIntoViewRequester.bringIntoView()
        }
      }

      if (style == TextFieldStyle.OUTLINE) {
        OutlinedTextField(
          value = value.orEmpty(),
          onValueChange = onValueChange,
          modifier = textFieldModifier,
          visualTransformation = visualTransformation,
          textStyle = AppTheme.typography.body1,
          label = label?.let {
            @Composable {
              Text(text = it)
            }
          },
          trailingIcon = endIcon,
          placeholder = placeholder?.let {
            @Composable {
              Text(
                text = it,
                color = colors.placeholderColor(true).value
              )
            }
          },
          isError = hasError,
          keyboardOptions = keyboardOptions,
          keyboardActions = keyboardActions,
          singleLine = singleLine,
          colors = colors,
          readOnly = readOnly,
          enabled = enabled,
        )
      }

      if (style == TextFieldStyle.UNDERLINE) {
        val textTypography = AppTheme.typography.h1.copy(
          fontSize = 41.sp,
          letterSpacing = 0.36.sp
        )

        TextField(
          value = value.orEmpty(),
          onValueChange = onValueChange,
          modifier = textFieldModifier,
          visualTransformation = visualTransformation,
          textStyle = textTypography,
          label = label?.let {
            @Composable {
              Text(text = it)
            }
          },
          trailingIcon = endIcon,
          placeholder = placeholder?.let {
            @Composable {
              Text(
                text = it,
                color = colors.placeholderColor(true).value,
                style = textTypography
              )
            }
          },
          isError = hasError,
          keyboardOptions = keyboardOptions,
          keyboardActions = keyboardActions,
          singleLine = singleLine,
          colors = colors,
          readOnly = readOnly,
          enabled = enabled,
        )
      }

      if (alwaysLayoutHelperText || helperText != null || (hasError && errorMessage != null)) {
        val helper = if (hasError) errorMessage else helperText

        Text(
          text = helper.orEmpty(),
          modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 20.dp)
            .padding(start = 16.dp, end = 16.dp, top = 3.dp),
          style = AppTheme.typography.caption,
          color = if (hasError) AppTheme.colors.error else AppTheme.colors.inputLabel
        )
      }
    }
  }
}

/**
 * Makes sure that the trailing icon supports the error state
 * for accessibility (color impaired).
 */
@Composable
private fun EndIcon(hasError: Boolean, specifiedIcon: @Composable (() -> Unit)?) {
  if (hasError) {
    Icon(
      painter = painterResource(R.drawable.ic_error_24),
      contentDescription = stringResource(R.string.input_error)
    )
  } else {
    specifiedIcon?.invoke()
  }
}