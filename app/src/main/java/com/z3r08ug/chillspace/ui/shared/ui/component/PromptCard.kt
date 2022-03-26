package com.z3r08ug.chillspace.ui.shared.ui.component

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.z3r08ug.chillspace.R
import com.z3r0_8ug.ui_common.R.*
import com.z3r0_8ug.ui_common.component.button.AppFilledButton
import com.z3r0_8ug.ui_common.theme.AppTheme

@Preview(name = "Day", showBackground = true)
@Preview(name = "Night", showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun Preview() {
  AppTheme {
    Box(
      modifier = Modifier.padding(16.dp)
    ) {
      PromptCard(
        title = "Add your documents to \"LOAN\"",
        subtitle = "Completeness Check",
        message = "It helps to be organized, you can add your loan documents to \"LOAN\"",
        onSnoozeClick = {},
      ) {
        Column(
          modifier = Modifier.fillMaxWidth()
        ) {
          AppFilledButton(
            text = "Add Documents",
            modifier = Modifier
              .fillMaxWidth()
              .defaultMinSize(minHeight = 48.dp),
          ) {}

          Spacer(modifier = Modifier.height(16.dp))
          AppFilledButton(
            text = "Skip Adding Documents",
            modifier = Modifier
              .fillMaxWidth()
              .defaultMinSize(minHeight = 48.dp),
          ) {}
        }
      }
    }
  }
}

@Composable
fun PromptCard(
  title: String,
  subtitle: String? = null,
  message: String,
  actionText: String,
  onActionClick: () -> Unit,
  onSnoozeClick: (() -> Unit)? = null,
  tint: Color = AppTheme.colors.secondary
) {
  PromptCard(
    title = title,
    subtitle = subtitle,
    onSnoozeClick = onSnoozeClick,
    tint = tint,
    content = {
      Text(
        text = message,
        modifier = Modifier.fillMaxWidth(),
        style = AppTheme.typography.body2,
        color = AppTheme.colors.text80
      )
    },
    buttons = {
      AppFilledButton(
        text = actionText,
        modifier = Modifier
          .fillMaxWidth()
          .defaultMinSize(minHeight = 48.dp),
        onClick = onActionClick
      )
    }
  )
}

@Composable
fun PromptCard(
  title: String,
  subtitle: String? = null,
  message: String,
  onSnoozeClick: (() -> Unit)? = null,
  tint: Color = AppTheme.colors.secondary,
  buttons: @Composable () -> Unit
) {
  PromptCard(
    title = title,
    subtitle = subtitle,
    onSnoozeClick = onSnoozeClick,
    tint = tint,
    content = {
      Text(
        text = message,
        modifier = Modifier.fillMaxWidth(),
        style = AppTheme.typography.body2,
        color = AppTheme.colors.text80
      )
    },
    buttons = buttons
  )
}

@Composable
fun PromptCard(
  title: String,
  subtitle: String? = null,
  onSnoozeClick: (() -> Unit)? = null,
  tint: Color = AppTheme.colors.secondary,
  content: @Composable () -> Unit,
  buttons: @Composable () -> Unit
) {
  val titleEndPadding = remember(onSnoozeClick) {
    when(onSnoozeClick) {
      null -> 0.dp
      else -> 52.dp
    }
  }

  Card {
    Box(
      modifier = Modifier.padding(
        start = 24.dp,
        top = 20.dp,
        end = 24.dp,
        bottom = 24.dp
      )
    ) {
      onSnoozeClick?.let {
        IconButton(
          onClick = onSnoozeClick,
          modifier = Modifier
            .align(Alignment.TopEnd)
            .offset(x = 12.dp, y = -(8.dp))
        ) {
          Icon(
            painter = painterResource(id = drawable.ic_close_24dp),
            contentDescription = stringResource(R.string.snooze),
            tint = tint
          )
        }
      }

      Column {
        Text(
          text = title,
          modifier = Modifier.padding(end = titleEndPadding),
          style = AppTheme.typography.h3,
          color = AppTheme.colors.text100
        )

        subtitle?.let {
          Spacer(modifier = Modifier.height(2.dp))
          Text(
            text = it,
            modifier = Modifier.padding(end = titleEndPadding),
            color = AppTheme.colors.text80,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            letterSpacing = 0.25.sp,
            lineHeight = 20.sp
          )
        }

        Spacer(modifier = Modifier.height(18.dp))
        content()

        Spacer(modifier = Modifier.height(32.dp))
        buttons()
      }
    }
  }
}
