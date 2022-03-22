package com.getelements.elements.ui.component.menu

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.LocalAbsoluteElevation
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Face
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.getelements.elements.ui.theme.AppTheme
import com.google.accompanist.insets.navigationBarsPadding

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun PreviewSpeedDialMenuItem() {
  AppTheme {
    Box(
      modifier = Modifier.padding(16.dp)
    ) {
      SpeedDialMenuItem(
        text = "First",
        icon = {
          Icon(
            imageVector = Icons.Rounded.AccountCircle,
            contentDescription = null,
            tint = AppTheme.colors.text100
          )
        },
        onClick = {}
      )
    }
  }
}

@Composable
fun SpeedDialMenuItem(
  text: String,
  icon: @Composable () -> Unit,
  elevation: Dp = 2.dp,
  onClick: () -> Unit
) {
  val absoluteElevation = LocalAbsoluteElevation.current + elevation
  val verticalPadding = remember { Animatable(0f) }
  LaunchedEffect(text) {
    verticalPadding.animateTo(8.dp.value)
  }

  CompositionLocalProvider(
    LocalAbsoluteElevation provides absoluteElevation
  ) {
    Row(
      modifier = Modifier.padding(vertical = Dp(verticalPadding.value))
    ) {
      Box(
        modifier = Modifier
          .height(40.dp)
          .shadow(elevation, AppTheme.shapes.pill, false)
          .background(AppTheme.colors.surface, AppTheme.shapes.pill)
          .clip(AppTheme.shapes.pill)
          .clickable {
            onClick()
          }
          .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart
      ) {
        Text(
          text = text,
          fontSize = 14.sp,
          color = AppTheme.colors.text80
        )
      }

      Spacer(modifier = Modifier.width(16.dp))

      Box(
        modifier = Modifier
          .size(40.dp)
          .shadow(elevation, AppTheme.shapes.pill, false)
          .background(AppTheme.colors.surface, AppTheme.shapes.pill)
          .clip(AppTheme.shapes.pill)
          .clickable {
            onClick()
          }
      ) {
        Box(
          modifier = Modifier.padding(8.dp)
        ) {
          icon()
        }
      }
    }
  }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun PreviewSpeedDialMenu() {
  AppTheme {
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    ) {
      SpeedDialMenu(
        fabContent = {
          Icon(
            imageVector = Icons.Rounded.Add,
            contentDescription = null,
            tint = AppTheme.colors.surface
          )
        },
        modifier = Modifier.fillMaxSize()
      ) {
        SpeedDialMenuItem(
          text = "First",
          icon = {
            Icon(
              imageVector = Icons.Rounded.AccountCircle,
              contentDescription = null,
              tint = AppTheme.colors.text100
            )
          },
          onClick = {}
        )

        SpeedDialMenuItem(
          text = "Second",
          icon = {
            Icon(
              imageVector = Icons.Rounded.Face,
              contentDescription = null,
              tint = AppTheme.colors.text100
            )
          },
          onClick = {}
        )
      }
    }
  }
}

@Composable
@OptIn(ExperimentalAnimationApi::class)
fun SpeedDialMenu(
  fabContent: @Composable () -> Unit,
  modifier: Modifier = Modifier,
  fabModifier: Modifier = Modifier.navigationBarsPadding(),
  expanded: MutableState<Boolean> = remember { mutableStateOf(false) },
  onFabClick: () -> Unit = { expanded.value = !expanded.value},
  content: @Composable ColumnScope.() -> Unit
) {
  val transition = updateTransition(expanded, "main-transition")
  val fabRotation by transition.animateFloat(label = "fab-rotation") { expand ->
    if (expand.value) {
      45f
    } else {
      0f
    }
  }

  Box(
    modifier = modifier,
    contentAlignment = Alignment.BottomEnd
  ) {
    // Background Scrim
    AnimatedVisibility(
      visible = expanded.value,
      enter = fadeIn(),
      exit = fadeOut()
    ) {
      Box(
        modifier = Modifier
          .fillMaxSize()
          .background(AppTheme.colors.background.copy(alpha = 0.72f))
          .pointerInput("speed-dial-scrim") {
            detectTapGestures {
              onFabClick()
            }
          }
      ) {}
    }

    Column(
      modifier = fabModifier
        .padding(end = 16.dp, bottom = 16.dp),
      horizontalAlignment = Alignment.End,
    ) {
      AnimatedVisibility(
        visible = expanded.value,
        enter = fadeIn(),
        exit = fadeOut()
      ) {
        Column(
          modifier = Modifier.padding(end = 8.dp),
          horizontalAlignment = Alignment.End
        ) {
          content(this)
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      FloatingActionButton(
        onClick = onFabClick
      ) {
        Box(
          modifier = Modifier.rotate(fabRotation)
        ) {
          fabContent()
        }
      }
    }
  }
}