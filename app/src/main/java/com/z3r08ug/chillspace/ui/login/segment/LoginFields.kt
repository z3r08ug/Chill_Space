package com.z3r08ug.chillspace.ui.login.segment

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.AutofillType
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.z3r0_8ug.ui_common.component.AppTextField
import com.z3r0_8ug.ui_common.component.AppTextFieldTopPadding
import com.z3r08ug.chillspace.R
import com.z3r08ug.chillspace.ui.shared.ui.component.PasswordTextField
import com.z3r0_8ug.ui_common.framework.ui.InputData
import com.z3r0_8ug.ui_common.framework.ui.view.autofill
import com.z3r0_8ug.ui_common.theme.AppTheme

@Preview
@Composable
private fun Preview() {
  AppTheme {
    LoginFields(
      login = InputData("podonnell", {}),
      password = InputData("pass", {})
    ) {}
  }
}

@Composable
@OptIn(ExperimentalComposeUiApi::class)
fun LoginFields(
  login: InputData<String?>,
  password: InputData<String?>,
  onLogin: () -> Unit
) {
  val focusManager = LocalFocusManager.current

  Column(
    modifier = Modifier.fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    AppTextField(
      value = login.value,
      onValueChange = login.onValueChange,
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 24.dp)
        .autofill(AutofillType.Username) {
          login.onValueChange(it)
        },
      label = stringResource(R.string.auth_id_or_login),
      keyboardOptions = KeyboardOptions(
        autoCorrect = false,
        keyboardType = KeyboardType.Email,
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
        .autofill(AutofillType.Password) {
          password.onValueChange(it)
        },
      keyboardActions = KeyboardActions {
        focusManager.clearFocus()
        onLogin()
      }
    )
  }
}


