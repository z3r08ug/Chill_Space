package com.z3r0_8ug.ui_common.theme.text

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private object SharedTypography {
  val font = TextStyle(
    fontWeight = FontWeight.Normal,
    fontStyle = FontStyle.Normal,
    fontFamily = FontFamily.SansSerif,
    letterSpacing = 0.01.sp
  )
}

val defaultTypography = AppTypography(
  fontFamily = FontFamily.SansSerif,

  h1 = SharedTypography.font.copy(
    fontSize = 48.sp,
    fontWeight = FontWeight.Black
  ),
  h2 = SharedTypography.font.copy(
    // TODO: Copied from Material
    fontWeight = FontWeight.Light,
    fontSize = 60.sp,
    letterSpacing = (-0.5).sp
  ),
  h3 = SharedTypography.font.copy(
    fontSize = 22.sp,
    fontWeight = FontWeight.Bold,
    lineHeight = 26.sp
  ),
  h4 = SharedTypography.font.copy(
    fontSize = 20.sp,
    fontWeight = FontWeight.Medium,
    lineHeight = 24.sp,
    letterSpacing = 0.15.sp
  ),
  h5 = SharedTypography.font.copy(
    fontSize = 14.sp,
    fontWeight = FontWeight.Black,
    lineHeight = 24.sp,
    letterSpacing = 0.8.sp
    // allCaps (Just Documentation for now, Looks like we'll have to handle that in the views)
  ),
  h6 = SharedTypography.font.copy(
    fontWeight = FontWeight.Medium,
    fontSize = 20.sp,
    letterSpacing = 0.15.sp
  ),


  subtitle1 = SharedTypography.font.copy(
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp,
    letterSpacing = 0.15.sp
  ),
  subtitle2 = SharedTypography.font.copy(
    fontWeight = FontWeight.Medium,
    fontSize = 14.sp,
    letterSpacing = 1.25.sp
  ),


  body1 = SharedTypography.font.copy(
    fontSize = 16.sp,
    lineHeight = 24.sp,
    letterSpacing = 0.15.sp
  ),
  body2 = SharedTypography.font.copy(
    fontSize = 16.sp,
    lineHeight = 24.sp,
    letterSpacing = 0.5.sp
  ),
  body3 = SharedTypography.font.copy(
    fontSize = 13.sp,
    lineHeight = 19.sp,
    letterSpacing = 0.5.sp,
  ),


  overline = SharedTypography.font.copy(
    fontSize = 13.sp,
    fontWeight = FontWeight.Medium
    // allCaps (Just Documentation for now, Looks like we'll have to handle that in the views)
  ),
  button = SharedTypography.font.copy(
    fontSize = 14.sp,
    fontWeight = FontWeight.Black,
    lineHeight = 16.sp,
    letterSpacing = 1.25.sp
  ),
  caption = SharedTypography.font.copy(
    // TODO: Copied from Material
    fontWeight = FontWeight.Normal,
    fontSize = 12.sp,
    letterSpacing = 0.4.sp
  ),


  toolbarTitle = SharedTypography.font.copy(
    fontSize = 20.sp,
    fontWeight = FontWeight.Black,
    lineHeight = 24.sp
  ),
  toolbarTitleSmall = SharedTypography.font.copy(
    fontSize = 18.sp,
    fontWeight = FontWeight.Black,
    lineHeight = 24.sp
  ),
  toolbarSubtitle = SharedTypography.font.copy(
    fontSize = 13.sp,
    lineHeight = 16.sp,
  ),
  acknowledgementsBody = SharedTypography.font.copy(
    fontSize = 16.sp,
    lineHeight = 24.sp,
    color = Color.Black.copy(alpha = 0.70f)
  )
)

internal val LocalAppTypography = staticCompositionLocalOf { defaultTypography }