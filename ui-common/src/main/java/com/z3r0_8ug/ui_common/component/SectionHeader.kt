package com.z3r0_8ug.ui_common.component

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.z3r0_8ug.ui_common.theme.AppTheme

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewSectionHeader() {
  AppTheme {
    SectionHeader(
      header = "Debts"
    )
  }
}

@Composable
fun SectionHeader(
  @StringRes header: Int,
  background: Color = AppTheme.colors.background
) = SectionHeader(
  header = stringResource(id = header),
  background = background
)

@Composable
fun SectionHeader(
  header: String,
  background: Color = AppTheme.colors.background
) {
  Text(
    text = header.uppercase(),
    modifier = Modifier
      .fillMaxWidth()
      .defaultMinSize(minHeight = 48.dp)
      .background(background)
      .padding(16.dp),
    color = AppTheme.colors.text40,
    style = AppTheme.typography.h5
  )
}
