package com.z3r08ug.chillspace.ui.shared.ui.component

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.z3r08ug.chillspace.R
import com.z3r0_8ug.ui_common.framework.ext.pluralStringResource
import com.z3r0_8ug.ui_common.theme.AppTheme

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun Preview() {
  AppTheme {
    Row {
      PromptIndicator(
        promptCount = 0,
        onClick = {}
      )

      Spacer(modifier = Modifier.width(16.dp))
      PromptIndicator(
        promptCount = 3,
        onClick = {}
      )

      // NOTE: As indicated below, we shouldn't have more than 3-4 prompts however
      // we support it in the component so we want to preview it
      Spacer(modifier = Modifier.width(16.dp))
      PromptIndicator(
        promptCount = 15,
        onClick = {}
      )
    }
  }
}

@Composable
fun PromptIndicator(
  promptCount: Int,
  onClick: () -> Unit
) {
  IconButton(
    onClick = onClick,
    modifier = Modifier.size(48.dp)
  ) {
    Box(
      modifier = Modifier.fillMaxSize()
    ) {
      Icon(
//        painter = painterResource(R.drawable.ic_notifications_24dp),
        painter = painterResource(com.z3r0_8ug.ui_common.R.drawable.ic_percentage_24dp),
        contentDescription = pluralStringResource(R.plurals.prompts_title, promptCount, promptCount),
        modifier = Modifier.align(Alignment.Center),
        tint = AppTheme.colors.secondary
      )

      // Unread indicator (dot)
      if (promptCount > 0) {
        Box(
          modifier = Modifier
            .padding(top = 9.dp, end = 5.dp)
            .align(Alignment.TopEnd)
            .size(19.dp)
            .clip(AppTheme.shapes.pill)
            .background(AppTheme.colors.background, AppTheme.shapes.pill)
            .padding(2.dp)
            .background(AppTheme.colors.notificationDot, AppTheme.shapes.pill)
        ) {
          val count = remember(promptCount) {
            // NOTE: We shouldn't have counts greater than 3 or 4 due to the engine,
            // however we want to make sure that it looks decent if it does
            when (promptCount) {
              in 0..9 -> promptCount.toString()
              else -> "!"
            }
          }

          Text(
            text = count,
            modifier = Modifier.align(Alignment.Center),
            color = AppTheme.colors.surface,
            fontSize = 10.sp,
            fontWeight = FontWeight.Black
          )
        }
      }
    }
  }
}