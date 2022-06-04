package com.z3r08ug.chillspace.utils

import android.app.Activity
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import timber.log.Timber

class FirebaseUtils {
    companion object {
        fun loginUser(activity: Activity?, auth: FirebaseAuth?, email: String, password: String): FirebaseUser? {
            var user: FirebaseUser? = null
            if (activity != null) {
                auth?.signInWithEmailAndPassword(email, password)
                    ?.addOnCompleteListener(activity) { task ->
                        user = if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Timber.d("signInWithEmail:success")
                            auth.currentUser
                        } else {
                            // If sign in fails, display a message to the user.
                            Timber.e("signInWithEmail:failure", task.exception)
                            Toast.makeText(activity, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                            null
                        }
                    }
            }
            return user
        }
    }
}