package com.z3r08ug.chillspace.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.z3r08ug.chillspace.AuthResult
import com.z3r0_8ug.ui_common.framework.ui.lifecycle.asInputData
import com.z3r0_8ug.ui_common.framework.ui.lifecycle.merge
import com.z3r0_8ug.ui_common.framework.util.Dispatchers
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    savedState: SavedStateHandle,
    dispatchers: Dispatchers,
//    private val accountService: AccountService,
) : ViewModel() {
    val username = savedState.getLiveData("login", "").asInputData()
    val password = MutableLiveData("").asInputData()

    val loginAllowed = username.merge(password) { username, pass ->
        !username.value.isNullOrBlank() && !pass.value.isNullOrBlank()
    }

    val loginState = MutableLiveData(LoginState.PENDING)

    private val loginRequested = MutableLiveData<Void?>()
//    val loginResult = loginRequested.switchMap {
//        val username = login.value?.value ?: ""
//        val pass = password.value?.value ?: ""
//
//        accountService.login(username, pass).onEach {
//            updateLoginState(it)
//        }.asLiveData(dispatchers.io)
//    }

    fun login() {
        if (loginAllowed.value == true) {
            loginState.postValue(LoginState.SENDING)
            loginRequested.postValue(null)
        }
    }

    private fun updateLoginState(result: AuthResult) {
        when (result) {
            is AuthResult.Failure -> loginState.postValue(LoginState.FAILURE)
            is AuthResult.Success -> loginState.postValue(LoginState.SUCCESS)
        }
    }

    enum class LoginState {
        PENDING,
        SENDING,
        FAILURE,
        SUCCESS
    }
}