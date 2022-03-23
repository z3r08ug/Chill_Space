package com.z3r0_8ug.ui_common.component.menu

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import com.getelements.elements.ui.component.AppTextField
import com.getelements.elements.ui.component.AppTextFieldTopPadding
import com.z3r0_8ug.ui_common.R
import com.z3r0_8ug.ui_common.component.ClickableTextFieldWrapper
import com.z3r0_8ug.ui_common.theme.AppTheme


@Preview
@Composable
private fun Preview() {
  AppTheme {
    ExposedDropdownMenu(
      options = listOf("One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight"),
      selection = null,
      onSelectionChange = {},
      modifier = Modifier.fillMaxWidth(),
      label = "Selection"
    ) { it }
  }
}

@Composable
fun <T> ExposedDropdownMenu(
  options: List<T>,
  selection: T?,
  onSelectionChange: (T) -> Unit,
  modifier: Modifier = Modifier,
  label: String,
  helperText: String? = null,
  alwaysLayoutHelperText: Boolean = false,
  itemText: @Composable (T) -> String
) {
  var expanded by remember { mutableStateOf(false) }

  Box(
    modifier = modifier
  ) {
    var textFieldSize by remember { mutableStateOf(IntSize(0, 0)) }

    ClickableTextFieldWrapper(
      onClick = {
        expanded = !expanded
      },
      modifier = Modifier.fillMaxWidth()
    ) {
      AppTextField(
        value = selection?.let {
          itemText(it)
        },
        modifier = Modifier
          .fillMaxWidth()
          .onSizeChanged {
            textFieldSize = it
          },
        onValueChange = {},
        label = label,
        helperText = helperText,
        trailingIcon = {
          Icon(
            painter = painterResource(R.drawable.ic_arrow_drop_down_24dp),
            contentDescription = null
          )
        },
        singleLine = true,
        alwaysLayoutHelperText = alwaysLayoutHelperText,
        readOnly = true
      )
    }

    // The Dropdown is using a medium shape however our designs use the small shape corners
    AppTheme(shapes = AppTheme.shapes.let { it.copy(medium = it.small) }) {
      val textFieldHeight = with(LocalDensity.current) {
        textFieldSize.height.toDp()
      }
      val yOffset = remember(textFieldHeight) {
        (AppTextFieldTopPadding + TextFieldDefaults.MinHeight) - textFieldHeight
      }

      DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
        properties = PopupProperties(focusable = true, clippingEnabled = false),
        modifier = Modifier
          .heightIn(max = 330.dp)
          .width(with(LocalDensity.current) { textFieldSize.width.toDp() }),
        offset = DpOffset(0.dp, yOffset)
      ) {
        options.forEach { option ->
          DropdownMenuItem(
            onClick = {
              onSelectionChange(option)
              expanded = false
            }
          ) {
            // NOTE:
            // Ideally we would be asking for the entire view to display, however because we are using the
            // AppTextField for it's styling we need to have a string that can be displayed in the `value` field.
            // If we have a need for more custom items then we'll want to stop using the AppTextField and instead
            // use a customized layout that duplicates the work (label animations, etc.) from the TextField.
            DropDownItem(value = itemText(option))
          }
        }
      }
    }
  }
}

@Composable
private fun DropDownItem(
  value: String
) {
  Text(
    text = value,
    modifier = Modifier.fillMaxWidth(),
    style = AppTheme.typography.body1,
    color = AppTheme.colors.text80
  )
}