package com.z3r08ug.chillspace.ui.login

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.getelements.elements.ui.component.AppTextFieldTopPadding
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.z3r08ug.chillspace.R.string
import com.z3r08ug.chillspace.ui.login.segment.LoginFields
import com.z3r08ug.chillspace.ui.login.segment.SupportFields
import com.z3r0_8ug.ui_common.component.AppScaffold
import com.z3r0_8ug.ui_common.component.NavIconStyle
import com.z3r0_8ug.ui_common.component.Toolbar
import com.z3r0_8ug.ui_common.ext.rememberTopBarElevation
import com.z3r0_8ug.ui_common.framework.ext.emptyInput
import com.z3r0_8ug.ui_common.framework.ui.InputData
import kotlinx.coroutines.launch

class NewLoginScreen(
    private val viewModel: LoginViewModel,
    private val onClose: () -> Unit,
    private val onComplete: () -> Unit,
    private val onForgotPassword: () -> Unit,
    private val onCreateAccount: () -> Unit
) {
    object Segment

    @Composable
    fun Content() {
        val loginState by viewModel.loginState.observeAsState(LoginViewModel.LoginState.PENDING)
        val allowLogin by viewModel.loginAllowed.observeAsState(false)

        val login by viewModel.login.observeAsState(emptyInput())
        val password by viewModel.password.observeAsState(emptyInput())

//        val loginResult by viewModel.loginResult.observeAsState(null)
//        LaunchedEffect(loginResult) {
//            loginResult?.let {
//                if (it is AuthResult.Success) {
//                    onComplete()
//                }
//            }
//        }
        Screen(
            loginState = loginState,
            allowLogin = allowLogin,
//            loginResult = loginResult,
            login = login,
            password = password
        )
    }

    @Composable
    @OptIn(ExperimentalMaterialApi::class)
    private fun Screen(
        loginState: LoginViewModel.LoginState,
        allowLogin: Boolean,
//        loginResult: AuthResult?,
        login: InputData<String?>,
        password: InputData<String?>
    ) {
        val scrollState = rememberScrollState()

        ScreenFrame(
            allowLogin = allowLogin,
            loginState = loginState,
//            loginResult = loginResult,
            scrollState = scrollState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState)
                    .padding(bottom = 56.dp)
                    .navigationBarsWithImePadding(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(24.dp - AppTextFieldTopPadding))
                LoginFields(
                    login = login,
                    password = password,
                    onLogin = { viewModel.login() }
                )

                SupportFields(
                    onForgotPasswordClick = onForgotPassword,
                    onCreateAccountClick = onCreateAccount
                )
            }
        }
    }

    @Composable
    @OptIn(ExperimentalMaterialApi::class)
    private fun ScreenFrame(
        allowLogin: Boolean,
        loginState: LoginViewModel.LoginState,
//        loginResult: AuthResult?,
        scrollState: ScrollState,
        content: @Composable (PaddingValues) -> Unit
    ) {
        val focusManager = LocalFocusManager.current
        val sending = loginState == LoginViewModel.LoginState.SENDING
        val coroutineScope = rememberCoroutineScope()

        val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
        val closeBottomSheet = {
            coroutineScope.launch {
                bottomSheetState.hide()
            }

            Unit
        }

        val topBarElevation = scrollState.rememberTopBarElevation()

        AppScaffold(
            showContentScrim = sending,
            toolbar = {
                Toolbar(
                    title = stringResource(string.auth_sign_in),
                    navIconStyle = NavIconStyle.CLOSE,
                    endButtonTitle = stringResource(string.auth_sign_in),
                    endButtonEnabled = allowLogin,
                    endButtonLoading = sending,
                    endButtonAction = {
                        focusManager.clearFocus()
                        viewModel.login()
                    },
                    onNavIconClick = onClose,
                    elevation = topBarElevation
                )
            },
            bottomSheet = {
//                if (loginResult is AuthResult.Failure && loginResult.error == AuthResult.Error.NO_NETWORK_CONNECTION) {
//                    NoNetworkBottomSheet(
//                        onCloseClick = closeBottomSheet
//                    )
//                } else {
//                    CredentialsErrorBottomSheet(
//                        onCloseClick = closeBottomSheet
//                    )
//                }
            },
            bottomSheetState = bottomSheetState,
            showBottomSheet = loginState == LoginViewModel.LoginState.FAILURE,
            onBottomSheetClosed = {
                viewModel.loginState.postValue(LoginViewModel.LoginState.PENDING)
            },
            content = content
        )
    }
}