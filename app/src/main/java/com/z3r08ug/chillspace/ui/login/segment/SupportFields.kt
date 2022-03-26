package com.z3r08ug.chillspace.ui.login.segment

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.z3r08ug.chillspace.R
import com.z3r0_8ug.ui_common.theme.AppTheme

@Preview
@Composable
private fun Preview() {
  AppTheme {
    SupportFields(
      onForgotPasswordClick = {}
    ) {}
  }
}

@Composable
fun SupportFields(
  onForgotPasswordClick: () -> Unit,
  onCreateAccountClick: () -> Unit
) {
  Column(
    modifier = Modifier.fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {

    Spacer(modifier = Modifier.height(24.dp))
    TextButton(
      onClick = onForgotPasswordClick
    ) {
      Text(
        text = stringResource(R.string.auth_forgot_password),
        style = AppTheme.typography.body1.copy(
          color = AppTheme.colors.secondary,
          fontSize = 14.sp,
          fontWeight = FontWeight.Medium
        )
      )
    }

    Spacer(modifier = Modifier.height(24.dp))
    TextButton(
      onClick = onCreateAccountClick
    ) {
      Text(
        text = stringResource(R.string.auth_no_account_question) + " ",
        style = AppTheme.typography.body1.copy(
          color = AppTheme.colors.text80,
          fontSize = 14.sp,
          fontWeight = FontWeight.Medium
        )
      )

      Text(
        text = stringResource(R.string.auth_create_account),
        style = AppTheme.typography.body1.copy(
          color = AppTheme.colors.secondary,
          fontSize = 14.sp,
          fontWeight = FontWeight.Medium
        )
      )
    }
  }
}