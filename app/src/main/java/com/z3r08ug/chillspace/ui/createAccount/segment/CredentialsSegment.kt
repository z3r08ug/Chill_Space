package com.z3r08ug.chillspace.ui.createAccount.segment

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.AutofillType
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.z3r08ug.chillspace.R.*
import com.z3r08ug.chillspace.ui.shared.ui.component.PasswordTextField
import com.z3r08ug.chillspace.utils.MaskVisualTransformation
import com.z3r0_8ug.ui_common.component.AppTextField
import com.z3r0_8ug.ui_common.component.AppTextFieldTopPadding
import com.z3r0_8ug.ui_common.framework.ui.InputData
import com.z3r0_8ug.ui_common.framework.ui.view.autofill

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Credentials(
  email: InputData<String?>,
  username: InputData<String?>,
  password: InputData<String?>,
  dob: InputData<String?>,
  onEntryCompeteClick: () -> Unit
) {
  val focusManager = LocalFocusManager.current

  Column(
    modifier = Modifier.fillMaxWidth()
  ) {
    AppTextField(
      value = email.value,
      onValueChange = email.onValueChange,
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 24.dp)
        .autofill(AutofillType.EmailAddress) {
          email.onValueChange(it)
        },
      label = stringResource(string.auth_email),
      helperText = stringResource(string.required_field),
      errorMessage = stringResource(string.email_invalid),
      hasError = email.hasError,
      keyboardOptions = KeyboardOptions(
        autoCorrect = false,
        keyboardType = KeyboardType.Email,
        imeAction = ImeAction.Next
      ),
      keyboardActions = KeyboardActions {
        focusManager.moveFocus(FocusDirection.Down)
      },
      singleLine = true,
      alwaysLayoutHelperText = true,
      trailingIcon = if (email.modificationsAllowed) {
        null
      } else {
        {
          Icon(
            painter = painterResource(drawable.ic_lock_24),
            contentDescription = null,
          )
        }
      },
      enabled = email.modificationsAllowed,
    )

    Spacer(modifier = Modifier.height(16.dp - AppTextFieldTopPadding))
    AppTextField(
      value = username.value,
      onValueChange = username.onValueChange,
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 24.dp)
        .autofill(AutofillType.NewUsername) {
          username.onValueChange(it)
        },
      label = stringResource(string.auth_username),
      helperText = stringResource(string.required_field),
      errorMessage = stringResource(string.auth_invalid_username_email),
      hasError = username.hasError,
      keyboardOptions = KeyboardOptions(
        autoCorrect = false,
        imeAction = ImeAction.Next
      ),
      keyboardActions = KeyboardActions {
        focusManager.moveFocus(FocusDirection.Down)
      },
      singleLine = true,
      alwaysLayoutHelperText = true
    )

    Spacer(modifier = Modifier.height(16.dp - AppTextFieldTopPadding))
    PasswordTextField(
      value = password.value,
      onValueChange = password.onValueChange,
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 24.dp)
        .autofill(AutofillType.NewPassword) {
          password.onValueChange(it)
        },
      helperText = stringResource(string.required_field),
      errorMessage = stringResource(string.auth_invalid_password_requirements),
      hasError = password.hasError,
      keyboardActions = KeyboardActions {
        focusManager.clearFocus()
        onEntryCompeteClick()
      }
    )

    Spacer(modifier = Modifier.height(16.dp - AppTextFieldTopPadding))
    AppTextField(
      value = dob.value,
      onValueChange = dob.onValueChange,
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 24.dp)
        .autofill(AutofillType.BirthDateFull) {
          if (it.length <= 8) {
            dob.onValueChange(it)
          }
        },
      label = stringResource(string.auth_dob),
      helperText = stringResource(string.birth_date_format),
      errorMessage = stringResource(string.auth_invalid_birthdate),
      hasError = username.hasError,
      keyboardOptions = KeyboardOptions(
        autoCorrect = false,
        imeAction = ImeAction.Next,
        keyboardType = KeyboardType.Number
      ),
      keyboardActions = KeyboardActions {
        focusManager.moveFocus(FocusDirection.Down)
      },
      singleLine = true,
      alwaysLayoutHelperText = true,
      visualTransformation = MaskVisualTransformation("##/##/####")
    )
  }
}