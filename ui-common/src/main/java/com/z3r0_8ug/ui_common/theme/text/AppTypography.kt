package com.z3r0_8ug.ui_common.theme.text

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily

data class AppTypography(
    val fontFamily: FontFamily,

    val h1: TextStyle,
    val h2: TextStyle,
    val h3: TextStyle,
    val h4: TextStyle,
    val h5: TextStyle,
    val h6: TextStyle,

    val subtitle1: TextStyle,
    val subtitle2: TextStyle,

    val body1: TextStyle,
    val body2: TextStyle,
    val body3: TextStyle,

    val overline: TextStyle,
    val button: TextStyle,
    val caption: TextStyle,

    val toolbarTitle: TextStyle,
    val toolbarTitleSmall: TextStyle,
    val toolbarSubtitle: TextStyle,
    val acknowledgementsBody: TextStyle,
)
