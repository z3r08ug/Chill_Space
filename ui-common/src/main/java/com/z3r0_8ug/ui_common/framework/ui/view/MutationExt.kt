package com.z3r0_8ug.ui_common.framework.ui.view

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.AutofillNode
import androidx.compose.ui.autofill.AutofillType
import androidx.compose.ui.composed
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalAutofill
import androidx.compose.ui.platform.LocalAutofillTree


@OptIn(ExperimentalComposeUiApi::class)
fun Modifier.autofill(
  type: AutofillType,
  onFill: ((String) -> Unit)
) = composed {
  val autofill = LocalAutofill.current
  val autofillNode = AutofillNode(autofillTypes = listOf(type), onFill = onFill)
  LocalAutofillTree.current += autofillNode

  onGloballyPositioned {
    autofillNode.boundingBox = it.boundsInWindow()
  }.onFocusChanged { focusState ->
    autofill?.apply {
      if (focusState.isFocused) {
        requestAutofillForNode(autofillNode)
      } else {
        cancelAutofillForNode(autofillNode)
      }
    }
  }
}