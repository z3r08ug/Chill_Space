package com.getelements.elements.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.getelements.elements.ui.theme.AppTheme

/**
 * Wraps the [content] in a clickable wrapper that intercepts
 * all focus requests. This is intended to be used with the
 * [AppTextField] and has appropriate padding for the click
 * highlighting to appear visually consistent.
 */
@Composable
fun ClickableTextFieldWrapper(
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  content: @Composable () -> Unit
) {
  // NOTE:
  // The `clickable` modifier doesn't really work on a TextField so we use a box
  // to wrap it since the TextField is Read-Only
  Box(
    modifier = modifier
  ) {
    content()
    Box(
      modifier = Modifier
        .padding(top = AppTextFieldTopPadding)
        .fillMaxWidth()
        .height(TextFieldDefaults.MinHeight)
        .clip(AppTheme.shapes.small)
        .clickable(onClick = onClick)
    )
  }
}