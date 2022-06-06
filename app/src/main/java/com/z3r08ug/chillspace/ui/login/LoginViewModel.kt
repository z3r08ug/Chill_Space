package com.z3r08ug.chillspace.ui.login

import android.app.Activity
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.z3r08ug.chillspace.AuthResult
import com.z3r08ug.chillspace.Screen.HomeScreen
import com.z3r08ug.chillspace.utils.FirebaseUtils
import com.z3r0_8ug.ui_common.framework.ui.lifecycle.asInputData
import com.z3r0_8ug.ui_common.framework.ui.lifecycle.merge
import com.z3r0_8ug.ui_common.framework.util.Dispatchers
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    savedState: SavedStateHandle,
    dispatchers: Dispatchers,
) : ViewModel() {
    val username = savedState.getLiveData("login", "").asInputData()
    val password = MutableLiveData("").asInputData()

    val loginAllowed = username.merge(password) { username, pass ->
        !username.value.isNullOrBlank() && !pass.value.isNullOrBlank()
    }

    val loginState = MutableLiveData(LoginState.PENDING)

    fun login(activity: Activity?, auth: FirebaseAuth?, emailText: String, passwordText: String, onLogin: () -> Unit) {
        if (loginAllowed.value == true) {
            loginState.postValue(LoginState.SENDING)
            val user = FirebaseUtils.loginUser(activity, auth, emailText, passwordText)
            if (user != null) {
                onLogin()
            } else {
                loginState.postValue(LoginState.FAILURE)
            }
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