package com.z3r0_8ug.ui_common.component.selector

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.z3r0_8ug.ui_common.theme.AppTheme

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun PreviewToggleGroup() {
  AppTheme {
    Column(
      modifier = Modifier.padding(16.dp)
    ) {
      ToggleGroup(
        options = listOf("First", "Second", "Third"),
        itemText = { it },
        selection = "First",
        onItemSelected = {},
      )

      Spacer(modifier = Modifier.height(24.dp))
      ToggleGroup(
        options = listOf("A", "B", "C"),
        itemText = { it },
        selection = "A",
        onItemSelected = {}
      )
    }
  }
}

@Composable
fun <T> ToggleGroup(
  options: List<T>,
  itemText: @Composable (T) -> String,
  modifier: Modifier = Modifier,
  selection: T? = null,
  onItemSelected: (T) -> Unit = {},
) {
  var selectedItem: T? by remember(selection) { mutableStateOf(selection) }

  EqualWidthRow(
    modifier = modifier
      .height(44.dp)
      .clip(RoundedCornerShape(16.dp))
      .background(AppTheme.colors.primaryVariant.copy(alpha = 0.2f))
      .padding(6.dp)
  ) {
    options.forEach {
      ToggleItem(
        text = itemText(it),
        selected = it == selectedItem,
        onClick = {
          onItemSelected(it)
          selectedItem = it
        }
      )
    }
  }
}

@Composable
private fun ToggleItem(
  text: String,
  selected: Boolean,
  onClick: () -> Unit
) {
  val transparent = remember {
    Color(0)
  }

  val background = when {
    selected -> AppTheme.colors.secondary
    else -> transparent
  }

  val textColor = when {
    selected -> AppTheme.colors.surface
    else -> AppTheme.colors.secondary
  }

  Box(
    modifier = Modifier
      .fillMaxHeight()
      .clip(RoundedCornerShape(10.dp))
      .background(background)
      .clickable {
        onClick()
      }
      .padding(horizontal = 16.dp)
  ) {
    Text(
      text = text.uppercase(),
      modifier = Modifier.align(Alignment.Center),
      color = textColor,
      style = AppTheme.typography.subtitle2.copy(
        letterSpacing = 0.5.sp
      ),
    )
  }
}

/**
 * A simplified "Row" layout that makes sure the children are equal widths,
 * calculated by the max requested child width
 */
@Composable
private fun EqualWidthRow(
  modifier: Modifier = Modifier,
  content: @Composable () -> Unit
) {
  Layout(
    content = content,
    modifier = modifier
  ) { measurables, constraints ->
    val maxChildWidth = measurables.maxOf {
      it.minIntrinsicWidth(constraints.minHeight)
    }

    val placeables = measurables.map {
      it.measure(
        constraints.copy(minWidth = maxChildWidth)
      )
    }

    layout(maxChildWidth * placeables.size, constraints.maxHeight) {
      var xPos = 0

      placeables.forEach { placeable ->
        placeable.placeRelative(x = xPos, y = 0)
        xPos += maxChildWidth
      }
    }
  }
}