package com.z3r0_8ug.ui_common.ext

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.AppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Allows us to tie the elevation of a TopAppBar to the scroll position of a list,
 * this provides the "lift on scroll" functionality to help distinguish between the
 * App Bar and the content.
 *
 * example usage
 * ```kotlin
 * val scrollState = rememberScrollState()
 * val topBarElevation = scrollState.rememberTopBarElevation()
 *
 * Scaffold(
 *   topBar = {
 *     Toolbar(
 *       ...
 *       elevation = topBarElevation
 *     )
 *   }
 * ) {
 *   Column(
 *     modifier = Modifier.verticalScroll(scrollState)
 *   ) { }
 * }
 *
 * ```
 */
@Composable
fun ScrollState.rememberTopBarElevation(): Dp {
  return rememberTopBarElevation(value)
}

@Composable
fun LazyListState.rememberTopBarElevation(): Dp {
  // NOTE: This is fairly naive and assumes the first item height is > the elevation
  if (firstVisibleItemIndex > 0) {
    return AppBarDefaults.TopAppBarElevation
  }

  return rememberTopBarElevation(firstVisibleItemScrollOffset)
}

private val ZERO_DP = 0.dp

@Composable
private fun rememberTopBarElevation(scrollPosition: Int): Dp {
  if (scrollPosition <= 0) {
    return ZERO_DP
  }

  val density = LocalDensity.current
  val maxPx = remember(density) {
    AppBarDefaults.TopAppBarElevation.toPx(density)
  }

  if (scrollPosition > maxPx) {
    return AppBarDefaults.TopAppBarElevation
  }

  return remember(density, maxPx) {
    with(density) {
      (maxPx / 2f).toDp()
    }
  }
}