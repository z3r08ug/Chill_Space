package com.z3r08ug.chillspace.ui.createAccount

import android.content.res.Configuration
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.SavedStateHandle
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.z3r08ug.chillspace.R.*
import com.z3r08ug.chillspace.ui.createAccount.segment.Credentials
import com.z3r08ug.chillspace.ui.createAccount.segment.GeneralErrorBottomSheet
import com.z3r08ug.chillspace.ui.createAccount.segment.UsernameErrorBottomSheet
import com.z3r0_8ug.ui_common.component.AppScaffold
import com.z3r0_8ug.ui_common.component.AppTextFieldTopPadding
import com.z3r0_8ug.ui_common.component.NavIconStyle
import com.z3r0_8ug.ui_common.component.Toolbar
import com.z3r0_8ug.ui_common.component.bottomsheet.NoNetworkBottomSheet
import com.z3r0_8ug.ui_common.ext.rememberTopBarElevation
import com.z3r0_8ug.ui_common.framework.ext.emptyInput
import com.z3r0_8ug.ui_common.framework.ui.InputData
import kotlinx.coroutines.launch

@Composable
fun CreateAccountScreen(
    viewModel: CreateAccountViewModel,
    auth: FirebaseAuth?,
    onClose: () -> Unit,
    onComplete: () -> Unit,
    onLogin: () -> Unit,
    onNavigateHomeScreen: () -> Unit
) {
    Content(
        viewModel,
        auth,
        onClose,
        onComplete,
        onLogin,
        onNavigateHomeScreen
    )
}

@Composable
fun Content(
    viewModel: CreateAccountViewModel,
    auth: FirebaseAuth?,
    onClose: () -> Unit,
    onComplete: () -> Unit,
    onLogin: () -> Unit,
    onNavigateHomeScreen: () -> Unit
) {
    val emailInput by viewModel.email.observeAsState(emptyInput())
    val usernameInput by viewModel.username.observeAsState(emptyInput())
    val passwordInput by viewModel.password.observeAsState(emptyInput())
    val dobInput by viewModel.dob.observeAsState(emptyInput())
    val allowCreateAccount by viewModel.allowCreateAccount.observeAsState(initial = false)

    Screen(
        viewModel = viewModel,
        email = emailInput,
        username = usernameInput,
        password = passwordInput,
        dob = dobInput,
        allowCreateAccount = allowCreateAccount,
        auth = auth,
        onClose = onClose,
        onLogin = onLogin,
        onNavigateHomeScreen = onNavigateHomeScreen
    )
}


@Composable
private fun Screen(
    viewModel: CreateAccountViewModel?,
    email: InputData<String?>,
    username: InputData<String?>,
    password: InputData<String?>,
    dob: InputData<String?>,
    allowCreateAccount: Boolean,
    auth: FirebaseAuth?,
    onClose: () -> Unit,
    onLogin: () -> Unit,
    onNavigateHomeScreen: () -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    ScreenFrame(
        viewModel = viewModel,
        focusManager = focusManager,
        scrollState = scrollState,
        email = email,
        username = username,
        password = password,
        dob = dob,
        allowCreateAccount = allowCreateAccount,
        auth = auth,
        onClose = onClose,
        onLogin = onLogin,
        onNavigateHomeScreen = onNavigateHomeScreen
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .padding(bottom = 56.dp)
                .navigationBarsWithImePadding()
        ) {
            Spacer(modifier = Modifier.height(24.dp - AppTextFieldTopPadding))

            Credentials(
                email = email,
                username = username,
                password = password,
                dob = dob,
                onEntryCompeteClick = {
                    val result = viewModel?.createAccount(
                        auth,
                        context,
                        onNavigateHomeScreen,
                        coroutineScope
                    )
                    if (result != null) {
                        onLogin()
                    }
                }
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterialApi::class)
private fun ScreenFrame(
    viewModel: CreateAccountViewModel?,
    focusManager: FocusManager,
    scrollState: ScrollState,
    email: InputData<String?>,
    username: InputData<String?>,
    password: InputData<String?>,
    dob: InputData<String?>,
    allowCreateAccount: Boolean,
    auth: FirebaseAuth?,
    onClose: () -> Unit,
    onLogin: () -> Unit,
    onNavigateHomeScreen: () -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val closeBottomSheet = {
        coroutineScope.launch {
            bottomSheetState.hide()
        }
    }

    val topBarElevation = scrollState.rememberTopBarElevation()
    val context = LocalContext.current

    var creating by remember {
        mutableStateOf(false)
    }
    var showBottomSheet by remember {
        mutableStateOf(false)
    }

    AppScaffold(
        showContentScrim = creating,
        toolbar = {
            Toolbar(
                title = stringResource(string.auth_create_account),
                navIconStyle = NavIconStyle.CLOSE,
                endButtonTitle = stringResource(string.auth_create),
                endButtonEnabled = allowCreateAccount,
                endButtonLoading = creating,
                endButtonAction = {
                    creating = true
                    focusManager.clearFocus()
                    val result = viewModel?.createAccount(
                        auth = auth,
                        context = context,
                        coroutineScope = coroutineScope,
                        onNavigateHomeScreen = onNavigateHomeScreen
                    )
                    if (result != null) {
                        onLogin()
                    }
                },
                onNavIconClick = onClose,
                elevation = topBarElevation
            )
        },
        bottomSheet = {
            if (email.hasError) {
                showBottomSheet = true
                UsernameErrorBottomSheet(
                    onCloseClick = { closeBottomSheet() },
                    onSignInClick = {
                        closeBottomSheet()
                        onLogin()
                        creating = false
                    }
                )
            } else if (false) {
                creating = false
                NoNetworkBottomSheet(
                    onCloseClick = { closeBottomSheet() }
                )
            } else {
                creating = false
                GeneralErrorBottomSheet(
                    onCloseClick = { closeBottomSheet() }
                )
            }
        },
        bottomSheetState = bottomSheetState,
        showBottomSheet = showBottomSheet,
        onBottomSheetClosed = {
//            viewModel.creationState.postValue(AccountCreationViewModel.CreationState.PENDING)
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
fun CreateAccountScreenPreview() {
    CreateAccountScreen(
        CreateAccountViewModel(
            SavedStateHandle(emptyMap()),
        null
        ),
        null,
        {},
        {},
        {},
        {}
    )
}