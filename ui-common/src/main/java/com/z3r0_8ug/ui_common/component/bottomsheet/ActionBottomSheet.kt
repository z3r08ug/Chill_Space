package com.z3r0_8ug.ui_common.component.bottomsheet

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.z3r0_8ug.ui_common.component.button.AppFilledButton
import com.google.accompanist.insets.navigationBarsPadding
import com.z3r0_8ug.ui_common.R
import com.z3r0_8ug.ui_common.R.*
import com.z3r0_8ug.ui_common.theme.AppTheme

@Composable
private fun PreviewFrame(
  content: @Composable () -> Unit
) {
  AppTheme {
    val shape = AppTheme.shapes.large.copy(bottomStart = CornerSize(0), bottomEnd = CornerSize(0))

    Box(
      modifier = Modifier
        .clip(shape)
        .background(AppTheme.colors.surface)
    ) {
      content()
    }
  }
}

@Preview(name = "Day")
@Preview(name = "Night", uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun Preview() {
  PreviewFrame {
    ActionBottomSheet(
      title = "Whoops... Somethings wrong. Try again later",
      message = "An error occurred performing the requested action. Please try again later.",
      actionText = "Got it",
      onCloseClick = {},
      footerContent = {
        Column(
          modifier = Modifier.fillMaxWidth()
        ) {
          Spacer(modifier = Modifier.height(16.dp))
          AppFilledButton(
            text = "Don't got it".uppercase(),
            modifier = Modifier
              .fillMaxWidth()
              .defaultMinSize(minHeight = 48.dp),
          ) {}
        }
      }
    )
  }
}

@Composable
fun ActionBottomSheet(
  title: String,
  message: String,
  actionText: String,
  onCloseClick: () -> Unit,
  onActionClick: () -> Unit = onCloseClick,
  buttonColor: Color = AppTheme.colors.secondary,
  headerContent: (@Composable () -> Unit)? = null,
  footerContent: (@Composable () -> Unit)? = null
) {
  ActionBottomSheet(
    title = title,
    message = message,
    headerContent = headerContent,
    onCloseClick = onCloseClick,
    content = {
      AppFilledButton(
        text = actionText.uppercase(),
        modifier = Modifier
          .fillMaxWidth()
          .defaultMinSize(minHeight = 48.dp),
        color = buttonColor,
        onClick = onActionClick
      )

      footerContent?.invoke()
    }
  )
}

@Composable
fun ActionBottomSheet(
  title: String,
  message: String,
  onCloseClick: () -> Unit,
  headerContent: (@Composable () -> Unit)? = null,
  content: (@Composable ColumnScope.() -> Unit)
) {
  Box(
    modifier = Modifier
      .navigationBarsPadding()
      .padding(
        start = 24.dp,
        top = 20.dp,
        end = 24.dp,
        bottom = 22.dp
      )
  ) {
    IconButton(
      onClick = onCloseClick,
      modifier = Modifier
        .align(Alignment.TopEnd)
        .offset(x = 12.dp, y = -(8.dp))
    ) {
      Icon(
        painter = painterResource(drawable.ic_close_24dp),
        contentDescription = stringResource(string.navigate_close),
        tint = AppTheme.colors.secondary
      )
    }

    Column {
      headerContent?.invoke()

      Text(
        text = title,
        modifier = Modifier.padding(end = 52.dp),
        style = AppTheme.typography.h3,
        color = AppTheme.colors.text100
      )

      Spacer(modifier = Modifier.height(8.dp))
      Text(
        text = message,
        modifier = Modifier.fillMaxWidth(),
        style = AppTheme.typography.body2,
        color = AppTheme.colors.text80
      )

      Spacer(modifier = Modifier.height(32.dp))

      content.invoke(this)
    }
  }
}

