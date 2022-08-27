package com.z3r08ug.chillspace.ui.login

import android.app.Activity
import android.content.res.Configuration
import android.os.Bundle
import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.z3r0_8ug.ui_common.component.AppTextFieldTopPadding
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.firebase.auth.FirebaseAuth
import com.z3r08ug.chillspace.R
import com.z3r08ug.chillspace.R.string
import com.z3r08ug.chillspace.ui.util.Screen.NewLoginScreen
import com.z3r08ug.chillspace.ui.login.segment.CredentialsErrorBottomSheet
import com.z3r08ug.chillspace.ui.login.segment.LoginFields
import com.z3r08ug.chillspace.ui.login.segment.SupportFields
import com.z3r08ug.chillspace.utils.MainViewModel
import com.z3r0_8ug.ui_common.component.AppScaffold
import com.z3r0_8ug.ui_common.component.NavIconStyle
import com.z3r0_8ug.ui_common.component.Toolbar
import com.z3r0_8ug.ui_common.ext.rememberTopBarElevation
import com.z3r0_8ug.ui_common.framework.ext.emptyInput
import com.z3r0_8ug.ui_common.framework.ui.InputData
import kotlinx.coroutines.launch

@Composable
fun NewLoginScreen(
    viewModel: LoginViewModel?,
    mainViewModel: MainViewModel?,
    arguments: Bundle?,
    auth: FirebaseAuth?,
    activity: Activity?,
    onClose: () -> Unit,
    onComplete: () -> Unit,
    onForgotPassword: () -> Unit,
    onCreateAccount: () -> Unit,
    onLogin: () -> Unit
) {
    mainViewModel?.setCurrentScreen(NewLoginScreen)
    val coroutineScope = rememberCoroutineScope()
    viewModel?.loginState?.let {
        ScreenContent(
            loginState = it,
            loginAllowed = viewModel.loginAllowed,
            username = viewModel.username,
            password = viewModel.password,
            login = {
                coroutineScope.launch {
                    viewModel.login(
                        activity = activity,
                        auth = auth,
                        emailText = viewModel.username.value?.value ?: "",
                        passwordText = viewModel.password.value?.value ?: "",
                        onLogin = { onLogin() }
                    )
                }

            },
            onForgotPassword = { onForgotPassword() },
            onCreateAccount = { onCreateAccount() },
            onClose = { onClose }
        ) { onComplete() }
    }
}

@Composable
fun ScreenContent(
    loginState: MutableLiveData<LoginViewModel.LoginState>,
    loginAllowed: MutableLiveData<Boolean>,
    username: LiveData<InputData<String?>>,
    password: LiveData<InputData<String?>>,
    login: () -> Unit,
    onForgotPassword: () -> Unit,
    onCreateAccount: () -> Unit,
    onClose: () -> Unit,
    onComplete: () -> Unit
) {
    val loginState by loginState.observeAsState(LoginViewModel.LoginState.PENDING)
    val allowLogin by loginAllowed.observeAsState(false)

    val username by username.observeAsState(emptyInput())
    val password by password.observeAsState(emptyInput())

    Screen(
        loginState = loginState,
        allowLogin = allowLogin,
        username = username as InputData<String?>,
        password = password,
        login = login,
        onForgotPassword = onForgotPassword,
        onCreateAccount = onCreateAccount,
        onClose = onClose,
        onComplete = onComplete
    )
}

@Composable
private fun Screen(
    loginState: LoginViewModel.LoginState,
    allowLogin: Boolean,
    username: InputData<String?>,
    password: InputData<String?>,
    login: () -> Unit,
    onForgotPassword: () -> Unit,
    onCreateAccount: () -> Unit,
    onClose: () -> Unit,
    onComplete: () -> Unit
) {
    val scrollState = rememberScrollState()

    ScreenFrame(
        allowLogin = allowLogin,
        loginState = loginState,
        scrollState = scrollState,
        login = login,
        onClose = onClose,
        onComplete = onComplete
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .padding(bottom = 56.dp)
                .navigationBarsWithImePadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            val image: Painter = painterResource(id = R.drawable.chill_space)
            Image(
                painter = image,
                contentDescription = "Chill Space Logo",
                modifier = Modifier
                    .fillMaxWidth()

            )

            Spacer(modifier = Modifier.height(24.dp - AppTextFieldTopPadding))
            LoginFields(
                login = username,
                password = password,
                onLogin = { login() }
            )

            SupportFields(
                onForgotPasswordClick = onForgotPassword,
                onCreateAccountClick = {
                    onCreateAccount()
                }
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterialApi::class)
private fun ScreenFrame(
    allowLogin: Boolean,
    loginState: LoginViewModel.LoginState,
    scrollState: ScrollState,
    login: () -> Unit,
    onClose: () -> Unit,
    onComplete: () -> Unit,
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
                    login()
                },
                onNavIconClick = { onClose() },
                elevation = topBarElevation
            )
        },
        bottomSheet = {
            CredentialsErrorBottomSheet(
                onCloseClick = closeBottomSheet
            )
        },
        bottomSheetState = bottomSheetState,
        showBottomSheet = loginState == LoginViewModel.LoginState.FAILURE,
        onBottomSheetClosed = {

        },
        content = content
    )
}

@Preview(showBackground = true)
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun LoginScreenPreview() {
    NewLoginScreen(
        null,
        null,
        null,
        null,
        null,
        {},
        {},
        {},
        {}
    ) {}
}