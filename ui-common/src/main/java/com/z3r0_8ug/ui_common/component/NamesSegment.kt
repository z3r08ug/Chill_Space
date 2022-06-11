package com.z3r0_8ug.ui_common.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.AutofillType
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import com.z3r0_8ug.ui_common.R.*
import com.z3r0_8ug.ui_common.framework.ui.InputData
import com.z3r0_8ug.ui_common.framework.ui.view.autofill

@Composable
@OptIn(ExperimentalComposeUiApi::class)
fun AccountNames(
  givenName: InputData<String?>,
  familyName: InputData<String?>,
  requireFamilyName: Boolean? = true
) {
  val focusManager = LocalFocusManager.current

  Column(
    modifier = Modifier.fillMaxWidth()
  ) {
    AppTextField(
      value = givenName.value,
      onValueChange = givenName.onValueChange,
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 24.dp)
        .autofill(AutofillType.PersonFirstName) {
          givenName.onValueChange(it)
        },
      label = stringResource(string.first_name),
      helperText = stringResource(string.required_field),
      errorMessage = stringResource(string.first_name_required),
      hasError = givenName.hasError,
      keyboardOptions = KeyboardOptions(
        imeAction = ImeAction.Next,
        capitalization = KeyboardCapitalization.Words
      ),
      keyboardActions = KeyboardActions {
        focusManager.moveFocus(FocusDirection.Down)
      },
      singleLine = true,
      alwaysLayoutHelperText = true
    )

    val helperText = if (requireFamilyName == true) {
      stringResource(string.required_field)
    } else {
      ""
    }
    Spacer(modifier = Modifier.height(16.dp - AppTextFieldTopPadding))
    AppTextField(
      value = familyName.value,
      onValueChange = familyName.onValueChange,
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 24.dp)
        .autofill(AutofillType.PersonLastName) {
          familyName.onValueChange(it)
        },
      label = stringResource(string.last_name),
      helperText = helperText,
      errorMessage = stringResource(string.last_name_required),
      hasError = familyName.hasError,
      keyboardOptions = KeyboardOptions(
        imeAction = ImeAction.Next,
        capitalization = KeyboardCapitalization.Words
      ),
      keyboardActions = KeyboardActions {
        focusManager.moveFocus(FocusDirection.Down)
      },
      singleLine = true,
      alwaysLayoutHelperText = true,
    )
  }
}