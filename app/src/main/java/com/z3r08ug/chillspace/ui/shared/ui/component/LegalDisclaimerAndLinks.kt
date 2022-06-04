package com.z3r08ug.chillspace.ui.shared.ui.component

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.z3r08ug.chillspace.R
import com.z3r0_8ug.ui_common.theme.AppTheme

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun Preview() {
  AppTheme {
    LegalDisclaimerAndLinks(
      onPrivacyPolicyClicked = {},
      onTermsOfUseClicked = {},
      onDisclaimersClicked = {}
    )
  }
}

@Composable
fun LegalDisclaimerAndLinks(
  onPrivacyPolicyClicked: () -> Unit,
  onTermsOfUseClicked: () -> Unit,
  onDisclaimersClicked: () -> Unit,
) {

  val style = AppTheme.typography.body2.copy(
    fontSize = 13.sp,
    lineHeight = 20.sp,
    letterSpacing = (-0.14).sp
  )
  val styleBold = style.copy(fontWeight = FontWeight.Bold)

  Column {
    Text(
      text = stringResource(id = R.string.welcome_legal),
      style = style,
      color = AppTheme.colors.textFocus,
      modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center
    )
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.Center,
    ) {
      Text(
        text = stringResource(id = R.string.privacy_policy),
        style = styleBold,
        color = AppTheme.colors.secondary,
        modifier = Modifier
          .clip(AppTheme.shapes.xSmall)
          .clickable(onClick = onPrivacyPolicyClicked)
      )
      Text(
        text = stringResource(id = R.string.comma),
        style = style,
        color = AppTheme.colors.textFocus
      )

      Spacer(modifier = Modifier.width(4.dp))
      Text(
        text = stringResource(id = R.string.terms_of_use),
        style = styleBold,
        color = AppTheme.colors.secondary,
        modifier = Modifier
          .clip(AppTheme.shapes.xSmall)
          .clickable(onClick = onTermsOfUseClicked)
      )
      Text(
        text = stringResource(id = R.string.comma),
        style = style,
        color = AppTheme.colors.textFocus
      )

      Spacer(modifier = Modifier.width(4.dp))
      Text(
        text = stringResource(id = R.string.and),
        style = style,
        color = AppTheme.colors.textFocus
      )
    }
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.Center,
    ) {
      Text(
        text = stringResource(id = R.string.disclaimers),
        style = styleBold,
        color = AppTheme.colors.secondary,
        modifier = Modifier
          .clip(AppTheme.shapes.xSmall)
          .clickable(onClick = onDisclaimersClicked)
      )
      Text(
        text = stringResource(id = R.string.dot),
        style = style,
        color = AppTheme.colors.textFocus
      )
    }
  }
}