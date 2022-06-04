package com.getelements.elements.ui.component.button

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.getelements.elements.ui.theme.AppTheme

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewCenteredIconButtonRow() {
  Column {
    CenteredIconButtonRow(
      painter = rememberVectorPainter(Icons.Rounded.Lock),
      text = "Update Password",
      tint = AppTheme.colors.secondary,
      onClick = {}
    )

    Spacer(modifier = Modifier.height(16.dp))
    CenteredIconButtonRow(
      painter = rememberVectorPainter(Icons.Rounded.Warning),
      text = "Delete Account",
      tint = AppTheme.colors.error,
      onClick = {}
    )
  }
}

@Composable
fun CenteredIconButtonRow(
  painter: Painter,
  text: String,
  tint: Color,
  onClick: () -> Unit
) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .defaultMinSize(minHeight = 36.dp)
      .padding(vertical = 6.dp),
    contentAlignment = Alignment.Center
  ) {
    TextButton(
      onClick = onClick,
    ) {
      Icon(
        painter = painter,
        contentDescription = null,
        tint = tint
      )
      Spacer(modifier = Modifier.width(4.5.dp))
      Text(
        text = text.uppercase(),
        color = tint
      )
    }
  }
}

