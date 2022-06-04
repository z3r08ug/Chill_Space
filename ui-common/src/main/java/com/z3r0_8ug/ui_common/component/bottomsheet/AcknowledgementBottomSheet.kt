package com.getelements.elements.ui.component.bottomsheet

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.getelements.elements.ui.R
import com.getelements.elements.ui.theme.AppTheme
import com.google.accompanist.insets.navigationBarsPadding

@Composable
private fun PreviewFrame(
  content: @Composable () -> Unit
) {
  AppTheme {
    val shape = AppTheme.shapes.large.copy(
      bottomStart = CornerSize(0),
      bottomEnd = CornerSize(0)
    )

    Box(
      modifier = Modifier.clip(shape)
    ) {
      content()
    }
  }
}


@Preview(name = "Day")
@Preview(name = "Night", uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun PreviewAcknowledgementBottomSheet() {
  Column {
    PreviewFrame {
      AcknowledgementBottomSheet(
        title = "Congratulations! You're connected with your advisor",
        onCloseClick = {}
      ) {
        Text(
          text = "Your advisor, James Smidt, can now see your data and make changes.",
          style = AppTheme.typography.acknowledgementsBody
        )
      }
    }

    Spacer(modifier = Modifier.height(24.dp))
    PreviewFrame {
      AcknowledgementBottomSheet(
        title = "Hurrah! You've added family members",
        onCloseClick = {}
      ) {
        Text(
          text = "Erica O`Donnell has been added as an editor to your data",
          style = AppTheme.typography.acknowledgementsBody
        )
      }
    }
  }
}

@Composable
fun AcknowledgementBottomSheet(
  title: String,
  onCloseClick: () -> Unit,
  content: @Composable () -> Unit
) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .background(AppTheme.colors.secondary)
      .navigationBarsPadding()
      .padding(
        start = 24.dp,
        top = 24.dp,
        end = 24.dp,
        bottom = 40.dp
      )
  ) {
    IconButton(
      onClick = onCloseClick,
      modifier = Modifier
        .align(Alignment.TopEnd)
        .offset(x = 12.dp, y = -(12.dp))
    ) {
      Icon(
        painter = painterResource(id = R.drawable.ic_close_24dp),
        contentDescription = stringResource(id = R.string.navigate_close)
      )
    }

    Column {
      Text(
        text = title,
        modifier = Modifier.padding(end = 40.dp),
        color = Color.Black.copy(alpha = 0.88f),
        fontSize = 17.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 22.sp
      )

      Spacer(modifier = Modifier.height(8.dp))
      content()
    }
  }
}