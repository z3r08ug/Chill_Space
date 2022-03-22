package com.z3r0_8ug.ui_common.framework.navigation

import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalLifecycleOwner

/**
 * Provides the ability to intercept the back button/gesture action to perform custom logic
 * such as closing a navigation drawer or bottom sheet. When [enabled] is `true` this will
 * intercept **all** back navigation requests so make sure to only enable it when applicable.
 *
 * @param enabled `true` if the back button/gestures should be intercepted. This is typically
 *                only be enabled when certain states are met such as a navigation drawer
 *                being open or a bottom sheet being shown
 * @param onBack The function to call when the back button/gesture is triggered
 */
@Composable
fun InterceptBackNavigation(enabled: Boolean, onBack: () -> Unit) {
  val currentOnBack by rememberUpdatedState(onBack)
  val callback = remember {
    object: OnBackPressedCallback(enabled) {
      override fun handleOnBackPressed() {
        currentOnBack()
      }
    }
  }

  callback.isEnabled = enabled
  val dispatcher = LocalOnBackPressedDispatcherOwner.current
  val lifecycleOwner = LocalLifecycleOwner.current

  DisposableEffect(lifecycleOwner, dispatcher) {
    dispatcher?.onBackPressedDispatcher?.addCallback(lifecycleOwner, callback)
    onDispose {
      callback.remove()
    }
  }
}
