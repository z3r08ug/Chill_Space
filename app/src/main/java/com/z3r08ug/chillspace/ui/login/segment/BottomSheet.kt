package com.z3r08ug.chillspace.ui.login.segment

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.z3r08ug.chillspace.R
import com.z3r0_8ug.ui_common.component.bottomsheet.ActionBottomSheet
import com.z3r0_8ug.ui_common.theme.AppTheme

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun Preview() {
  AppTheme {
    CredentialsErrorBottomSheet {}
  }
}

@Composable
fun CredentialsErrorBottomSheet(
  onCloseClick: () -> Unit
) {
  ActionBottomSheet(
    title = stringResource(R.string.auth_invalid_credentials),
    message = stringResource(R.string.auth_invalid_credentials_message),
    actionText = stringResource(R.string.got_it),
    onCloseClick = onCloseClick
  )
}