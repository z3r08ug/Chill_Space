package com.z3r0_8ug.ui_common.component.selector

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalViewConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.z3r0_8ug.ui_common.theme.AppTheme

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewRadioItem() {
  AppTheme {
    Column {
      RadioItem(
        text = "Perry O'Donnell",
        selected = true,
        onClick = {}
      )

      RadioItem(
        text = "Shanon O'Donnell",
        selected = false,
        onClick = {}
      )
    }
  }
}

@Composable
@OptIn(ExperimentalMaterialApi::class)
fun RadioItem(
  text: String,
  selected: Boolean,
  modifier: Modifier = Modifier,
  onClick: () -> Unit
) {
  // With the click modifier we need to ensure we are consistent
  val buttonMinWidth = when {
    LocalMinimumTouchTargetEnforcement.current -> LocalViewConfiguration.current.minimumTouchTargetSize.width
    else -> 0.dp
  }

  // A modifier to make sure the RadioButton is aligned correctly
  val offsetModifier = remember(buttonMinWidth) {
    // 24.dp is the default size for the RadioButton without being clickable
    if (buttonMinWidth <= 24.dp) {
      Modifier
    } else {
      val offset = (buttonMinWidth - 24.dp) / 2
      Modifier.offset(x = -offset)
    }
  }

  Box(
    modifier = modifier
      .defaultMinSize(minHeight = 48.dp)
      .clickable(onClick = onClick)
      .padding(horizontal = 16.dp)
  ) {
    RadioButton(
      selected = selected,
      onClick = onClick,
      modifier = offsetModifier.align(Alignment.CenterStart),
      colors = RadioButtonDefaults.colors(
        unselectedColor = AppTheme.colors.text40
      )
    )

    Text(
      text = text,
      modifier = Modifier
        .padding(start = 56.dp)
        .align(Alignment.CenterStart),
      color = AppTheme.colors.text80,
      style = AppTheme.typography.body1
    )
  }
}