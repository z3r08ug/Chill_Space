package com.z3r0_8ug.ui_common.component

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.z3r0_8ug.ui_common.theme.AppTheme

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun Preview() {
  AppTheme {
    Column {
      InfoBanner(
        text = "Showing discounted values due to partial ownership.",
        leadingIconPainter = rememberVectorPainter(Icons.Rounded.Info)
      )

      Spacer(modifier = Modifier.height(24.dp))
      InfoBanner(
        text = "Showing discounted values due to partial ownership.",
        leadingIconPainter = rememberVectorPainter(Icons.Rounded.Info),
        color = AppTheme.colors.savingsRate
      )
    }
  }
}

@Composable
fun InfoBanner(
  text: String,
  leadingIconPainter: Painter,
  color: Color = AppTheme.colors.secondary,
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .defaultMinSize(minHeight = 56.dp)
      .background(color.copy(alpha = 0.20f))
      .padding(
        vertical = 12.dp,
        horizontal = 16.dp
      )
  ) {
    Icon(
      painter = leadingIconPainter,
      contentDescription = null,
      modifier = Modifier.align(Alignment.CenterVertically),
      tint = color
    )

    Spacer(modifier = Modifier.width(8.dp))
    Text(
      text = text,
      modifier = Modifier
        .fillMaxWidth()
        .align(Alignment.CenterVertically),
      color = color.copy(alpha = 0.60f),
      fontSize = 13.sp,
      fontWeight = FontWeight.Medium,
      lineHeight = 16.sp,
      letterSpacing = 0.33.sp
    )
  }
}