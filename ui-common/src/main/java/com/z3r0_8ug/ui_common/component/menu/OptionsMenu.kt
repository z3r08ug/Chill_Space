package com.z3r0_8ug.ui_common.component.menu

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.z3r0_8ug.ui_common.R
import com.z3r0_8ug.ui_common.theme.AppTheme

@Composable
fun OptionsMenu(
  showMenu: MutableState<Boolean> = remember { mutableStateOf(false) },
  content: @Composable ColumnScope.() -> Unit
) {
  IconButton(
    onClick = {
      showMenu.value = !showMenu.value
    }
  ) {
    Icon(
      imageVector = Icons.Rounded.MoreVert,
      contentDescription = stringResource(id = R.string.menu),
      tint = AppTheme.colors.secondary
    )
  }

  // The Dropdown is using a medium shape however our designs use the small shape corners
  AppTheme(shapes = AppTheme.shapes.let { it.copy(medium = it.small) }) {
    DropdownMenu(
      expanded = showMenu.value,
      onDismissRequest = {
        showMenu.value = false
      }
    ) {
      content(this)
    }
  }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun PreviewOptionMenuItem() {
  AppTheme {
    Column {
      OptionMenuItem(
        text = "Edit",
        onClick = {}
      )

      OptionMenuItem(
        text = "Sync",
        onClick = {},
        trailingIcon = {
          IconButton(
            onClick = {}
          ) {
            Icon(
              imageVector = Icons.Rounded.Refresh,
              contentDescription = "sync",
              tint = AppTheme.colors.text40
            )
          }
        }
      )

      OptionMenuItem(
        text = "Delete",
        onClick = {},
        leadingIcon = {
          IconButton(
            onClick = {}
          ) {
            Icon(
              imageVector = Icons.Rounded.Delete,
              contentDescription = "delete",
              tint = AppTheme.colors.error
            )
          }
        }
      )
    }
  }
}

@Composable
fun OptionMenuItem(
  text: String,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  leadingIcon: @Composable (() -> Unit)? = null,
  trailingIcon: @Composable (() -> Unit)? = null
) {
  DropdownMenuItem(
    onClick = onClick
  ) {
    leadingIcon?.let {
      it()
      Spacer(modifier = Modifier.width(16.dp))
    }

    Text(
      text = text,
      modifier = modifier
        .fillMaxWidth()
        .weight(1F),
      style = AppTheme.typography.body1,
      color = AppTheme.colors.text80
    )

    trailingIcon?.let {
      Spacer(modifier = Modifier.width(16.dp))
      it()
    }
  }
}