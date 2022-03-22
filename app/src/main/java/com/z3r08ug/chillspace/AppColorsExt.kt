package com.z3r08ug.chillspace

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

private object SharedColors {
    val error = Color(0xffff144f)
    val mediumGray = Color(0xff585f66)
    val darkGray = Color(0xff17181a)
    val lightGray = Color(0xffacb7b2)
    val white = Color(0xffffffff)
    val almostBlack = Color(0xff101112)

    val accountStatusConnected = Color(0xff4af196)
    val accountStatusDisconnected = error
}

val darkColors = AppColors(
    primary = SharedColors.darkGray,
    primaryDark = SharedColors.almostBlack,
    primaryVariant = Color(0xff585f66),
    secondary = Color(0xff4af196),
    error = SharedColors.error,

    surface = Color(0xff242629),
    background = SharedColors.darkGray,
    backgroundStripe = Color(0x14585f66),
    backgroundDrawerTile = Color(0x29585f66),
    placeholder = SharedColors.darkGray,
    inputLabel = SharedColors.mediumGray,
    notificationDot = Color(0xffffffff),

    navigationBarItem = Color(0xff828b8c),
    navigationBar = SharedColors.almostBlack,

    text100 = Color(0xfff6f9f9),
    text80 = SharedColors.lightGray,
    text60 = Color(0x99acb7b2),
    text40 = SharedColors.mediumGray,
    textFocus = Color(0xffffffff),

    mediumGray = SharedColors.mediumGray,
    darkGray = SharedColors.darkGray,
    silver = SharedColors.lightGray,
    white = SharedColors.white,
    almostBlack = SharedColors.almostBlack,

    accountStatusConnected = SharedColors.accountStatusConnected,
    accountStatusDisconnected = SharedColors.accountStatusDisconnected,

    elementScoreText = Color(0xffffffff),
    elementScoreCardBackground = Color(0xff242629),

    taxRate = Color(0xffffbe00),
    liquidTerm = Color(0xffff8c0e),
    profitabilityRate = Color(0xffff4f0e),
    businessTerm = Color(0xffff144f),
    burnRate = Color(0xffc036dd),
    qualifiedTerm = Color(0xff8b31f1),
    estateElement = Color(0xff5a40df),
    realEstateTerm = Color(0xff284ecd),
    insuranceRate = Color(0xff169fff),
    savingsRate = Color(0xff31948f),
    debtRate = Color(0xff016e42),
    equityRate = Color(0xff16a104),
    totalTerm = Color(0xff93ce00),
)

val lightColors = darkColors.copy(
    primary = Color(0xfff6f9f9),
    primaryDark = SharedColors.mediumGray,
    primaryVariant = Color(0xffffffff),
    secondary = Color(0xff19be81),

    surface = Color(0xffffffff),
    background = Color(0xfff6f9f9),
    backgroundStripe = Color(0x14838c89),
    backgroundDrawerTile = Color(0xfff6f9f9),
    placeholder = Color(0xff838c89),
    inputLabel = Color(0xff838c89),
    notificationDot = Color(0xffff2aac),

    navigationBarItem = Color(0xffacb7b2),
    navigationBar = Color(0xffe9f5f2),

    text100 = SharedColors.almostBlack,
    text80 = SharedColors.darkGray,
    text60 = SharedColors.mediumGray,
    text40 = Color(0xff838c89),
    textFocus = SharedColors.darkGray,

    elementScoreCardBackground = Color(0x14838c89),
)

internal val LocalAppColors = staticCompositionLocalOf { darkColors }