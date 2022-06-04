package com.getelements.elements.ui.component.button

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.getelements.elements.ui.theme.AppTheme

@Preview
@Composable
private fun Preview() {
  AppTheme {
    Column(modifier = Modifier.padding(8.dp)) {
      AppFilledButton(
        text = "Update Data Point"
      ) {}

      Spacer(modifier = Modifier.height(24.dp))
      AppFilledButton(
        text = "Not Loading",
        loading = false
      ) {}

      Spacer(modifier = Modifier.height(16.dp))
      AppFilledButton(
        text = "Loading",
        loading = true
      ) {}
    }
  }
}

@Composable
fun AppFilledButton(
  text: String,
  modifier: Modifier = Modifier,
  color: Color = AppTheme.colors.secondary,
  onClick: () -> Unit
) {
  Button(
    onClick = onClick,
    modifier = modifier,
    elevation = ButtonDefaults.elevation(
      defaultElevation = 0.dp,
      pressedElevation = 0.dp
    ),
    shape = AppTheme.shapes.pill,
    colors = AppTheme.colors.filledButton(color)
  ) {
    Text(
      text = text.uppercase(),
      style = AppTheme.typography.button,
      color = color
    )
  }
}

@Composable
fun AppFilledButton(
  text: String,
  loading: Boolean,
  modifier: Modifier = Modifier,
  color: Color = AppTheme.colors.secondary,
  onClick: () -> Unit
) {
  // Ensures that the size is stable when switching from text to progress
  var measuredSize by remember { mutableStateOf(IntSize(0, 0)) }
  val minWidth = when {
    measuredSize.width > 0 -> with(LocalDensity.current) {
      measuredSize.width.toDp()
    }
    else -> Dp.Unspecified
  }
  val minHeight =  when {
    measuredSize.height > 0 -> with(LocalDensity.current) {
      measuredSize.height.toDp()
    }
    else -> Dp.Unspecified
  }

  Button(
    onClick = onClick,
    modifier = Modifier
      .defaultMinSize(
        minWidth = minWidth,
        minHeight = minHeight
      )
      .onSizeChanged {
        measuredSize = it
      }
      .then(modifier),
    elevation = ButtonDefaults.elevation(
      defaultElevation = 0.dp,
      pressedElevation = 0.dp
    ),
    shape = AppTheme.shapes.pill,
    colors = AppTheme.colors.filledButton(color)
  ) {
    if (loading) {
      CircularProgressIndicator(
        modifier = Modifier
          .size(20.dp)
          .align(Alignment.CenterVertically),
        color = color,
        strokeWidth = 2.dp
      )
    } else {
      Text(
        text = text.uppercase(),
        style = AppTheme.typography.button,
        color = color
      )
    }
  }
}