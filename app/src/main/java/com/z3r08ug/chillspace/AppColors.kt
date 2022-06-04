package com.z3r08ug.chillspace

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/**
 * Colors specific to the App
 *
 * NOTE:
 * The MDC Colors uses mutableStateOf(primary, structuralEqualityPolicy())
 */
data class AppColors(
    val primary: Color,
    val primaryDark: Color,
    val primaryVariant: Color,
    val secondary: Color,
    val error: Color,

    val surface: Color,
    val background: Color,
    val backgroundStripe: Color,
    val backgroundDrawerTile: Color,
    val placeholder: Color,
    val inputLabel: Color,
    val notificationDot: Color,

    val navigationBarItem: Color,
    val navigationBar: Color,

    val text100: Color,
    val text80: Color,
    val text60: Color,
    val text40: Color,
    val textFocus: Color,

    val mediumGray: Color,
    val darkGray: Color,
    val silver: Color,
    val white: Color,
    val almostBlack: Color,

    val accountStatusConnected: Color,
    val accountStatusDisconnected: Color,

    val elementScoreText: Color,
    val elementScoreCardBackground: Color,

    val taxRate: Color,
    val liquidTerm: Color,
    val profitabilityRate: Color,
    val businessTerm: Color,
    val burnRate: Color,
    val qualifiedTerm: Color,
    val estateElement: Color,
    val realEstateTerm: Color,
    val insuranceRate: Color,
    val savingsRate: Color,
    val debtRate: Color,
    val equityRate: Color,
    val totalTerm: Color,
) {

    @Composable
    fun toolbarButton(): ButtonColors {
        return ButtonDefaults.buttonColors(
            backgroundColor = secondary,
            contentColor = darkGray,
            disabledBackgroundColor = secondary.copy(alpha = 0.2f),
            disabledContentColor = darkGray
        )
    }

    @Composable
    fun filledButton(
        color: Color = secondary
    ): ButtonColors {
        return ButtonDefaults.buttonColors(
            backgroundColor = color.copy(alpha = 0.2f),
            contentColor = color,
            disabledBackgroundColor = color.copy(alpha = 0.05f),
            disabledContentColor = mediumGray
        )
    }

    @Composable
    fun textField(): TextFieldColors {
        return TextFieldDefaults.outlinedTextFieldColors(
            textColor = AppTheme.colors.text100.copy(LocalContentAlpha.current),
            disabledTextColor = AppTheme.colors.text100.copy(LocalContentAlpha.current).copy(
                ContentAlpha.disabled),

            cursorColor = AppTheme.colors.secondary,
            errorCursorColor = AppTheme.colors.secondary,

            focusedBorderColor = AppTheme.colors.secondary,
            unfocusedBorderColor = AppTheme.colors.inputLabel.copy(alpha = 0.4f),
            errorBorderColor = AppTheme.colors.error,
            disabledBorderColor = AppTheme.colors.inputLabel.copy(alpha = 0.4f),

            leadingIconColor = AppTheme.colors.inputLabel.copy(alpha = TextFieldDefaults.IconOpacity),
            disabledLeadingIconColor = AppTheme.colors.inputLabel.copy(alpha = 0.4f),
            errorLeadingIconColor = AppTheme.colors.inputLabel.copy(alpha = TextFieldDefaults.IconOpacity),

            trailingIconColor = AppTheme.colors.secondary.copy(alpha = TextFieldDefaults.IconOpacity),
            disabledTrailingIconColor = AppTheme.colors.inputLabel.copy(alpha = 0.4f),
            errorTrailingIconColor = AppTheme.colors.error,

            backgroundColor = Color.Transparent,

            focusedLabelColor = AppTheme.colors.secondary,
            unfocusedLabelColor = AppTheme.colors.inputLabel,
            disabledLabelColor = AppTheme.colors.inputLabel,
            errorLabelColor = AppTheme.colors.error,

            placeholderColor = AppTheme.colors.inputLabel.copy(ContentAlpha.medium),
            disabledPlaceholderColor = AppTheme.colors.inputLabel.copy(ContentAlpha.disabled)
        )
    }

    @Composable
    fun checkBox(): CheckboxColors {
        return CheckboxDefaults.colors(
            checkedColor = MaterialTheme.colors.secondary,
            uncheckedColor = AppTheme.colors.mediumGray.copy(alpha = 0.6f),
            disabledColor = AppTheme.colors.mediumGray.copy(alpha = ContentAlpha.disabled)
        )
    }

    @Composable
    fun radioButton(): RadioButtonColors {
        return RadioButtonDefaults.colors(
            selectedColor = MaterialTheme.colors.secondary,
            unselectedColor = AppTheme.colors.mediumGray.copy(alpha = 0.6f),
            disabledColor = AppTheme.colors.mediumGray.copy(alpha = ContentAlpha.disabled)
        )
    }
}