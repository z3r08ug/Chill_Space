package com.z3r0_8ug.ui_common.component.bottomsheet

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.z3r0_8ug.ui_common.R
import com.z3r0_8ug.ui_common.theme.AppTheme

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun Preview() {
  AppTheme {
    val shape = AppTheme.shapes.large.copy(bottomStart = CornerSize(0), bottomEnd = CornerSize(0))

    Box(
      modifier = Modifier
        .clip(shape)
        .background(AppTheme.colors.surface)
    ) {
      UnknownErrorBottomSheet {}
    }
  }
}

@Composable
fun UnknownErrorBottomSheet(
  onCloseClick: () -> Unit
) {
  ActionBottomSheet(
    title = stringResource(R.string.error_unknown),
    message = stringResource(R.string.error_unknown_message),
    actionText = stringResource(R.string.okay),
    onCloseClick = onCloseClick
  )
}