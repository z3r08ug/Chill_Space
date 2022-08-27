package com.z3r08ug.chillspace.ui.createAccount

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.z3r08ug.chillspace.utils.DateUtils
import com.z3r08ug.chillspace.utils.FirebaseUtils
import com.z3r0_8ug.ui_common.framework.ui.lifecycle.asInputData
import com.z3r0_8ug.ui_common.framework.ui.lifecycle.merge
import com.z3r0_8ug.ui_common.framework.util.Dispatchers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber
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
    val dob = MutableLiveData("").asInputData()

    private val credentialsValid = email.merge(username, password) { emailAddress, user, pass ->

        emailAddress.value.isNotBlank() &&
                user.value.isNotBlank() &&
                pass.value.isNotBlank()
    }

    val allowCreateAccount = credentialsValid.merge(dob) { credentials, dob ->
        if (dob.value.isNotEmpty()) {
            credentials &&
                    DateUtils.isValidDob(DateUtils.convertToLocalDate(dob.value))
        } else {
            false
        }
    }

    fun createAccount(
        auth: FirebaseAuth?,
        context: Context,
        onNavigateHomeScreen: () -> Unit,
        coroutineScope: CoroutineScope
    ) {
        val email = email.value?.value.orEmpty()
        val password = password.value?.value.orEmpty()
        val username = username.value?.value.orEmpty()
        val dob = dob.value?.value.orEmpty()

        if (allowCreateAccount.value == true) {
            auth?.createUserWithEmailAndPassword(email, password)
                ?.addOnCompleteListener(context as Activity) { task ->
                    if (task.isSuccessful && task.isComplete) {
                        // Sign in success, update UI with the signed-in user's information
                        Timber.d(TAG, "createUserWithEmail:success ${task.result.user?.uid}")
                        val user = task.result.user
                        coroutineScope.launch {
                            DateUtils.convertToLocalDate(dob)?.let {
                                FirebaseUtils.saveUser(
                                    user = user,
                                    userName = username,
                                    email = email,
                                    dob = it,
                                    context = context
                                )
                            }
                        }

                        onNavigateHomeScreen()
                    } else {
                        if (task.isComplete) {
                            // If sign in fails, display a message to the user.
                            Timber.w(TAG, "createUserWithEmail:failure", task.exception)
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