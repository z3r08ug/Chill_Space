package com.z3r08ug.chillspace.ui.createAccount

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.z3r0_8ug.ui_common.framework.ui.lifecycle.asInputData
import com.z3r0_8ug.ui_common.framework.ui.lifecycle.merge
import com.z3r0_8ug.ui_common.framework.util.Dispatchers
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateAccountViewModel @Inject constructor(
    savedState: SavedStateHandle,
    dispatchers: Dispatchers?,
) : ViewModel() {
    private val TAG = CreateAccountViewModel::class.simpleName

    val email = MutableLiveData("").asInputData()

    val username = MutableLiveData("").asInputData()

    val password = MutableLiveData("").asInputData()

    private val credentialsValid = email.merge(username, password) { emailAddress, user, pass ->
        emailAddress.value.isNotBlank() &&
                user.value.isNotBlank() &&
                pass.value.isNotBlank()
    }

    fun createAccount(auth: FirebaseAuth?, context: Context, onNavigateHomeScreen: () -> Unit) {
        val email = email.value?.value.orEmpty()
        val password = password.value?.value.orEmpty()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth?.createUserWithEmailAndPassword(email, password)
                ?.addOnCompleteListener(context as Activity) { task ->
                    if (task.isSuccessful && task.isComplete) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")
                        onNavigateHomeScreen()
                    } else {
                        if (task.isComplete) {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.exception)
                            Toast.makeText(
                                context, "Authentication failed.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
        }
    }
}