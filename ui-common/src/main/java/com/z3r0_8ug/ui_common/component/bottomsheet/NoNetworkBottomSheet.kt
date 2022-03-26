package com.z3r0_8ug.ui_common.component.bottomsheet

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.z3r0_8ug.ui_common.R
import com.z3r0_8ug.ui_common.theme.AppTheme

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun Preview() {
  AppTheme {
    val shape = AppTheme.shapes.large.copy(
      bottomStart = CornerSize(0),
      bottomEnd = CornerSize(0)
    )

    Box(
      modifier = Modifier
        .clip(shape)
        .background(AppTheme.colors.surface)
    ) {
      NoNetworkBottomSheet(
        onCloseClick = {}
      )
    }
  }
}

@Composable
fun NoNetworkBottomSheet(
  onCloseClick: () -> Unit
) {
  ActionBottomSheet(
    title = stringResource(id = R.string.offline_title),
    message = stringResource(id = R.string.offline_message),
    actionText = stringResource(id = R.string.okay),
    headerContent = {
      Image(
        painter = painterResource(id = R.drawable.no_connection_illustration),
        contentDescription = null,
        modifier = Modifier.fillMaxWidth()
      )
      Spacer(modifier = Modifier.height(32.dp))
    },
    onCloseClick = onCloseClick,
  )
}

