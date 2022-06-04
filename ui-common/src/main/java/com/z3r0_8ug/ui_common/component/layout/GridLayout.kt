package com.z3r0_8ug.ui_common.component.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.z3r0_8ug.ui_common.ext.toPx
import com.z3r0_8ug.ui_common.theme.AppTheme

@Preview(showBackground = true)
@Composable
private fun PreviewGrid() {
  AppTheme {
    Box(
      modifier = Modifier
        .padding(16.dp)
        .background(AppTheme.colors.surface)
    ) {
      Grid(
        columns = 3,
        modifier = Modifier.size(300.dp, 300.dp),
        itemPadding = 15.dp
      ) {
        (1..10).forEach {
          Box(
            modifier = Modifier
              .size(90.dp, 90.dp)
              .background(AppTheme.colors.estateElement)
          ) {
            Text(
              text = "Item $it",
              modifier = Modifier.align(Alignment.Center)
            )
          }
        }
      }
    }
  }
}

/**
 * A simple grid layout that supports adding padding between the items. This layout
 * does not verify that [columns] number of items will fit within the specified
 * size.
 */
@Composable
fun Grid(
  columns: Int,
  modifier: Modifier = Modifier,
  itemPadding: Dp = 0.dp,
  content: @Composable () -> Unit
) {
  val itemPaddingPx = itemPadding.toPx(LocalDensity.current).toInt()

  Layout(
    content = content,
    modifier = modifier.clipToBounds()
  ) { measurables, constraints ->
    // Limit the item widths
    val useableWidth = constraints.maxWidth - (columns -1) * itemPaddingPx
    val childWidth = useableWidth / columns
    val childConstraints = constraints.copy(
      minWidth = childWidth,
      maxWidth = childWidth
    )

    val placeables = measurables.map {
      it.measure(childConstraints.copy(
        minHeight = it.minIntrinsicHeight(childWidth),
        maxHeight = it.maxIntrinsicWidth(childWidth)
      ))
    }

    layout(constraints.maxWidth, constraints.maxHeight) {
      var columnCount = 0
      var rowMaxHeight = 0

      var xPos = 0
      var yPos = 0

      placeables.forEach { placeable ->
        placeable.placeRelative(x = xPos, y = yPos)

        if (++columnCount >= columns) {
          xPos = 0
          yPos += (rowMaxHeight + itemPaddingPx)

          columnCount = 0
          rowMaxHeight = 0
        } else {
          xPos += (placeable.width + itemPaddingPx)
          rowMaxHeight = maxOf(rowMaxHeight, placeable.height)
        }
      }
    }
  }
}