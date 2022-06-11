package com.z3r08ug.chillspace.ui.createAccount.segment

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.z3r08ug.chillspace.R
import com.z3r0_8ug.ui_common.component.bottomsheet.ActionBottomSheet
import com.z3r0_8ug.ui_common.theme.AppTheme

@Composable
fun GeneralErrorBottomSheet(
  onCloseClick: () -> Unit
) {
  BottomSheet(
    title = stringResource(R.string.auth_create_failed),
    message = stringResource(R.string.auth_create_failed_message),
    onCloseClick = onCloseClick
  )
}

@Composable
@OptIn(ExperimentalMaterialApi::class)
fun UsernameErrorBottomSheet(
  onCloseClick: () -> Unit,
  onSignInClick: () -> Unit
) {
  BottomSheet(
    title = stringResource(R.string.auth_username_unavailable),
    message = stringResource(R.string.auth_username_unavailable_message),
    onCloseClick = onCloseClick,
    footerContent = {
      SignInFooter(
        onSignInClick = onSignInClick
      )
    }
  )
}

@Composable
@OptIn(ExperimentalMaterialApi::class)
private fun BottomSheet(
  title: String,
  message: String,
  onCloseClick: () -> Unit,
  footerContent: (@Composable () -> Unit)? = null
) {
  ActionBottomSheet(
    title = title,
    message = message,
    actionText = stringResource(R.string.got_it),
    onCloseClick = onCloseClick,
    footerContent = footerContent
  )
}

@Composable
private fun SignInFooter(
  onSignInClick: () -> Unit
) {
  Column(
    modifier = Modifier.fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Spacer(modifier = Modifier.height(24.dp))

    TextButton(
      onClick = onSignInClick
    ) {
      Text(
        text = stringResource(R.string.auth_have_account) + " ",
        style = AppTheme.typography.body1.copy(
          color = AppTheme.colors.text80,
          fontSize = 14.sp,
          fontWeight = FontWeight.Medium
        )
      )

      Text(
        text = stringResource(R.string.auth_sign_in),
        style = AppTheme.typography.body1.copy(
          color = AppTheme.colors.secondary,
          fontSize = 14.sp,
          fontWeight = FontWeight.Medium
        )
      )
    }
  }
}