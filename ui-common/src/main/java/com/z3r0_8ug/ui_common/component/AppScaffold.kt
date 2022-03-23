package com.z3r0_8ug.ui_common.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import com.z3r0_8ug.ui_common.framework.ui.navigation.InterceptBackNavigation
import com.z3r0_8ug.ui_common.theme.AppTheme
import kotlinx.coroutines.launch

@Composable
@ExperimentalMaterialApi
fun AppScaffold(
  toolbar: @Composable () -> Unit,
  showContentScrim: Boolean = false,
  bottomSheet: (@Composable ColumnScope.() -> Unit)? = null,
  bottomSheetState: ModalBottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden),
  showBottomSheet: Boolean = false,
  onBottomSheetClosed: (() -> Unit)? = null,
  content: @Composable (PaddingValues) -> Unit
) {
  if (bottomSheet == null) {
    MainScaffold(
      showContentScrim = showContentScrim,
      toolbar = toolbar,
      content = content
    )

    return
  }

  //TODO When this bug fix is released we need to update to prevent bottom sheets only opening half way
  // https://issuetracker.google.com/issues/186669820
  // https://android-review.googlesource.com/c/platform/frameworks/support/+/1891098/

  ModalBottomSheetLayout(
    sheetContent = bottomSheet,
    sheetShape = AppTheme.shapes.bottomSheet,
    sheetState = bottomSheetState
  ) {
    MainScaffold(
      showContentScrim = showContentScrim,
      toolbar = toolbar,
      content = content
    )
  }

  // Updates the visibility in a LaunchedEffect to handle animations
  LaunchedEffect(showBottomSheet) {
    if (showBottomSheet) {
      bottomSheetState.show()
    } else {
      bottomSheetState.hide()
    }
  }

  // Captures the os back navigation to dismiss the bottom sheet
  val coroutineScope = rememberCoroutineScope()
  InterceptBackNavigation(showBottomSheet) {
    coroutineScope.launch {
      bottomSheetState.hide()
    }
  }

  // Watches the BottomSheetState to inform the onBottomSheetClosed
  var ignoredInitialBottomSheetState by remember { mutableStateOf(false) }
  LaunchedEffect(bottomSheetState.isVisible) {
    if (ignoredInitialBottomSheetState) {
      if (!bottomSheetState.isVisible) {
        onBottomSheetClosed?.invoke()
      }
    } else {
      ignoredInitialBottomSheetState = true
    }
  }
}

@Composable
@OptIn(ExperimentalAnimationApi::class)
private fun MainScaffold(
  showContentScrim: Boolean,
  toolbar: @Composable () -> Unit,
  content: @Composable (PaddingValues) -> Unit
) {
  var showScrim by remember { mutableStateOf(false) }
  showScrim = showContentScrim

  Scaffold(
    topBar = toolbar
  ) {
    content(it)

    AnimatedVisibility(
      visible = showScrim,
      enter = fadeIn(),
      exit = fadeOut()
    ) {
      // NOTE: by accepting pointerInput we prevent any of the overlaid fields from being interacted with
      Box(
        modifier = Modifier
          .fillMaxSize()
          .background(MaterialTheme.colors.onSurface.copy(alpha = 0.32f))
          .pointerInput(showContentScrim) {}
      ) {}
    }
  }
}