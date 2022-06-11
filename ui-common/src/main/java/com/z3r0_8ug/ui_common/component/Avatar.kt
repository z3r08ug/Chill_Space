package com.z3r0_8ug.ui_common.component

import android.content.res.Configuration
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.z3r0_8ug.ui_common.R.*
import com.z3r0_8ug.ui_common.ext.animatedPlaceholder
import com.z3r0_8ug.ui_common.ext.toPx
import com.z3r0_8ug.ui_common.theme.AppTheme


@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewAvatar() {
  AppTheme {
    Column(
      modifier = Modifier.padding(16.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Avatar(
          monogram = "CC",
          color = AppTheme.colors.liquidTerm,
          showEditIcon = true
        )

        Spacer(modifier = Modifier.width(24.dp))
        Avatar(
          monogram = "PH",
          color = AppTheme.colors.liquidTerm,
          showPlaceholder = true
        )
      }

      Spacer(modifier = Modifier.height(24.dp))
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Avatar(
          monogram = "PO",
          color = AppTheme.colors.totalTerm,
          size = 72.dp
        )

        Spacer(modifier = Modifier.width(24.dp))
        Avatar(
          monogram = "AO",
          color = AppTheme.colors.insuranceRate,
          size = 40.dp
        )
        Spacer(modifier = Modifier.width(24.dp))
        Avatar(
          monogram = "",
          color = AppTheme.colors.insuranceRate,
          showImagePlaceholder = true
        )
      }
    }
  }
}

@Composable
fun Avatar(
  monogram: String,
  color: Color,
  showPlaceholder: Boolean = false,
  size: Dp = 104.dp,
  showImagePlaceholder: Boolean = false,
  showEditIcon: Boolean = false,
  onEditClicked: () -> Unit = {}
) {
  // Simple "Breakpoint" for text sizing
  val monogramTextStyle = remember(size) {
    when {
      size < 56.dp -> {
        TextStyle(
          fontSize = 16.sp,
          fontWeight = FontWeight.Black,
          letterSpacing = 0.2.sp
        )
      }
      size < 96.dp -> {
        TextStyle(
          fontSize = 26.sp,
          fontWeight = FontWeight.Black,
          letterSpacing = 0.3.sp
        )
      }
      else -> {
        TextStyle(
          fontSize = 34.sp,
          fontWeight = FontWeight.Black,
          letterSpacing = 0.4.sp
        )
      }
    }
  }

  Box(
    modifier = Modifier
        .size(size),
    contentAlignment = Alignment.Center
  ) {
    Box(
      modifier = if (showEditIcon) {
        Modifier
          .size(size)
          .clip(AppTheme.shapes.pill)
          .let {
            when (showPlaceholder) {
              true -> it
              false -> it.background(color)
            }
          }
          .animatedPlaceholder(visible = showPlaceholder)
          .clickable {
            onEditClicked()
          }
      } else {
        Modifier
          .size(size)
          .clip(AppTheme.shapes.pill)
          .let {
            when (showPlaceholder) {
              true -> it
              false -> it.background(color)
            }
          }
          .animatedPlaceholder(visible = showPlaceholder)
      },
      contentAlignment = Alignment.Center
    ) {
      if (!showPlaceholder) {
        Text(
          text = monogram,
          color = AppTheme.colors.darkGray,
          textAlign = TextAlign.Center,
          style = monogramTextStyle
        )
        if (showImagePlaceholder) {
          Image(
            painter = painterResource(id = drawable.ic_baseline_portrait_24),
            contentDescription = "Portrait",
            modifier = Modifier.size(40.dp)
          )
        }
      }
    }
    if (showEditIcon) {
      val density = LocalDensity.current
      val surface = AppTheme.colors.surface
      val editIconTint = AppTheme.colors.secondary
      Box(
        modifier = Modifier
          .align(Alignment.BottomEnd)
          .offset((-4).dp, (-4).dp)
      ) {
        Box(
          contentAlignment = Alignment.Center
        ) {
          Canvas (
            modifier = Modifier
              .align(Alignment.Center)
              .clickable {
                onEditClicked()
              }
          ) {
            drawCircle(
              color = surface,
              radius = 18.dp.toPx(density),
            )
            drawCircle(
              color = editIconTint,
              radius = 16.dp.toPx(density),
            )
          }
          Icon(
            painter = painterResource(id = drawable.ic_edit_24),
            contentDescription = stringResource(string.edit),
            tint = surface
          )
        }
      }
    }
  }
}