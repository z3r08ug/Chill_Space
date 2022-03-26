package com.z3r08ug.chillspace.ui.shared.ui.component

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.getelements.elements.ui.component.AppTextField
import com.z3r08ug.chillspace.R
import com.z3r0_8ug.ui_common.theme.AppTheme


@Preview
@Composable
private fun Preview() {
  AppTheme {
    PasswordTextField(
      value = "pass",
      onValueChange = {}
    )
  }
}

@Composable
fun PasswordTextField(
  value: String?,
  onValueChange: (String) -> Unit,
  modifier: Modifier = Modifier,
  label: String = stringResource(R.string.auth_password),
  helperText: String? = null,
  errorMessage: String? = null,
  hasError: Boolean = false,
  keyboardActions: KeyboardActions = KeyboardActions.Default
) {
  val showPassword = rememberSaveable { mutableStateOf(false) }

  AppTextField(
    value = value,
    onValueChange = onValueChange,
    modifier = modifier,
    label = label,
    helperText = helperText,
    errorMessage = errorMessage,
    hasError = hasError,
    trailingIcon = {
      PasswordVisibilityToggle(showPassword)
    },
    alwaysLayoutHelperText = true,
    keyboardOptions = KeyboardOptions(
      autoCorrect = false,
      keyboardType = KeyboardType.Password,
      imeAction = ImeAction.Done
    ),
    keyboardActions = keyboardActions,
    singleLine = true,
    visualTransformation = if (showPassword.value) {
      VisualTransformation.None
    } else {
      PasswordVisualTransformation()
    }
  )
}

@Composable
private fun PasswordVisibilityToggle(shouldShow: MutableState<Boolean>) {
  IconButton(
    onClick = {
      shouldShow.value = !shouldShow.value
    }
  ) {
    if (shouldShow.value) {
      Icon(
        painter = painterResource(R.drawable.ic_visibility_24),
        contentDescription = stringResource(R.string.hide_password),
        tint = AppTheme.colors.secondary
      )
      Icons.Default.MoreVert
    } else {
      Icon(
        painter = painterResource(R.drawable.ic_visibility_off_24),
        contentDescription = stringResource(R.string.show_password),
        tint = AppTheme.colors.secondary
      )
    }
  }
}