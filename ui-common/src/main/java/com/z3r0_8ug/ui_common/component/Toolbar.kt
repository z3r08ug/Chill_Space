package com.z3r0_8ug.ui_common.component

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.z3r0_8ug.ui_common.R
import com.z3r0_8ug.ui_common.theme.AppTheme

@Preview(name = "NightDisabled", uiMode = UI_MODE_NIGHT_YES)
@Preview(name = "DayDisabled", uiMode = UI_MODE_NIGHT_NO)
@Composable
private fun PreviewToolbar() {
  AppTheme {
    Toolbar(
      title = "Jake's Checking",
      navIconStyle = NavIconStyle.BACK,
      endButtonAction = {},
      onNavIconClick = {}
    )
  }
}

@Preview(name = "NightEnabled", uiMode = UI_MODE_NIGHT_YES)
@Preview(name = "DayEnabled", uiMode = UI_MODE_NIGHT_NO)
@Composable
private fun PreviewToolbarEnabled() {
  AppTheme {
    Toolbar(
      title = "Jake's Checking account with some long text",
      subtitle = "Subtitle",
      navIconStyle = NavIconStyle.CLOSE,
      endButtonEnabled = true,
      endButtonAction = {},
      onNavIconClick = {}
    )
  }
}

enum class NavIconStyle {
  BACK,
  CLOSE,
  MENU,
  NONE
}

@Composable
fun Toolbar(
  title: String,
  subtitle: String? = null,
  navIconStyle: NavIconStyle = NavIconStyle.BACK,
  endButtonTitle: String = stringResource(id = R.string.save),
  endButtonEnabled: Boolean = false,
  endButtonLoading: Boolean = false,
  endButtonAction: (() -> Unit)? = null,
  onNavIconClick: () -> Unit,
  elevation: Dp = 0.dp
) {
  val endButton: @Composable (RowScope.() -> Unit) = {
    endButtonAction?.let {
      EndButton(
        text = endButtonTitle,
        enabled = endButtonEnabled,
        loading = endButtonLoading,
        onClick = it
      )
      EndButton(
        text = endButtonTitle,
        enabled = endButtonEnabled,
        loading = endButtonLoading,
        onClick = it
      )
    }
  }

  Toolbar(
    title = title,
    subtitle = subtitle,
    navIconStyle = navIconStyle,
    endActions = endButton,
    onNavIconClick = onNavIconClick,
    elevation = elevation
  )
}

@Composable
fun Toolbar(
  title: String,
  subtitle: String? = null,
  navIconStyle: NavIconStyle = NavIconStyle.BACK,
  endActions: @Composable RowScope.() -> Unit = {},
  onNavIconClick: () -> Unit,
  elevation: Dp = 0.dp
  ) {
  val actions: @Composable (RowScope.() -> Unit) = {
    Row(
      modifier = Modifier.padding(start = 16.dp)
    ) {
      endActions()
    }
  }

  val navIcon: (@Composable () -> Unit)? = when(navIconStyle) {
    NavIconStyle.NONE -> null
    else -> {
      @Composable {
        IconButton(
          onClick = onNavIconClick,
          content = { NavIcon(navIconStyle) }
        )
      }
    }
  }

  com.google.accompanist.insets.ui.TopAppBar(
    title = {
      if (subtitle != null) {
        TitleWithSubtitle(title = title, subtitle = subtitle)
      } else {
        Title(title = title)
      }
    },
    contentPadding = rememberInsetsPaddingValues(
      LocalWindowInsets.current.statusBars,
      applyBottom = false,
    ),
    navigationIcon = navIcon,
    actions = actions,
    backgroundColor = AppTheme.colors.primary,
    elevation = elevation
  )
}

@Composable
private fun NavIcon(
  style: NavIconStyle
) {
  when (style) {
    NavIconStyle.BACK -> {
      Icon(
        painter = painterResource(R.drawable.ic_arrow_back_24dp),
        contentDescription = stringResource(R.string.navigate_up),
        tint = AppTheme.colors.secondary
      )
    }
    NavIconStyle.CLOSE -> {
      Icon(
        painter = painterResource(R.drawable.ic_close_24dp),
        contentDescription = stringResource(R.string.navigate_close),
        tint = AppTheme.colors.secondary
      )
    }
    NavIconStyle.MENU -> {
      Icon(
        imageVector = Icons.Rounded.Menu,
        contentDescription = stringResource(R.string.menu),
        tint = AppTheme.colors.secondary
      )
    }
    NavIconStyle.NONE -> {}
  }
}

@Preview
@Composable
private fun PreviewTitle() {
  Title(title = "Jake's Checking")
}

@Composable
private fun Title(title: String) {
  Text(
    text = title,
    color = AppTheme.colors.textFocus,
    overflow = TextOverflow.Ellipsis,
    softWrap = false,
    style = AppTheme.typography.toolbarTitle
  )
}

@Preview
@Composable
private fun PreviewTitleWithSubtitle() {
  TitleWithSubtitle(title = "Jake's Checking", subtitle = "Jake's Checking")
}

@Composable
private fun TitleWithSubtitle(title: String, subtitle: String) {
  Column {
    Text(
      text = title,
      color = AppTheme.colors.textFocus,
      overflow = TextOverflow.Ellipsis,
      softWrap = false,
      style = AppTheme.typography.toolbarTitleSmall
    )
    Text(
      text = subtitle,
      color = AppTheme.colors.mediumGray,
      overflow = TextOverflow.Ellipsis,
      softWrap = false,
      style = AppTheme.typography.toolbarSubtitle
    )
  }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun PreviewEndButton() {
  AppTheme {
    Row(
      modifier = Modifier.padding(8.dp)
    ) {
      EndButton(
        text = "Sign In",
        enabled = true,
        loading = false
      ) {}

      Spacer(modifier = Modifier.width(16.dp))

      EndButton(
        text = "Sign In",
        enabled = true,
        loading = true
      ) {}
    }
  }
}

@Composable
private fun EndButton(
  text: String,
  enabled: Boolean,
  loading: Boolean,
  icon: Int? = null,
  onClick: () -> Unit
) {
  val colors = AppTheme.colors.toolbarButton()

  // Ensures that the width is stable when switching from text to progress
  var measuredWidth by remember { mutableStateOf(0) }
  val minWidth = maxOf(52.dp, with(LocalDensity.current) {
    measuredWidth.toDp()
  })

  Button(
    onClick = {
      if (!loading) {
        onClick()
      }
    },
    modifier = Modifier
      .height(48.dp)
      .defaultMinSize(minWidth = minWidth)
      .onSizeChanged {
        measuredWidth = it.width
      }
      .padding(vertical = 10.dp),
    shape = AppTheme.shapes.pill,
    colors = colors,
    enabled = enabled,
    contentPadding = PaddingValues(
      horizontal = 12.dp
    )
  ) {
    if (loading) {
      CircularProgressIndicator(
        modifier = Modifier
          .size(20.dp)
          .align(Alignment.CenterVertically),
        color = AppTheme.colors.surface,
        strokeWidth = 2.dp
      )
    } else {
      Text(
        text = text,
        style = AppTheme.typography.button.copy(
          letterSpacing = 0.01.sp,
          fontSize = 12.sp,
          color = AppTheme.colors.surface
        )
      )
      Icon(
        painter = painterResource(R.drawable.ic_arrow_back_24dp),
        contentDescription = stringResource(R.string.navigate_up),
        tint = AppTheme.colors.secondary
      )
    }
  }

  Spacer(modifier = Modifier.width(16.dp))
}