package com.z3r08ug.chillspace.utils

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.z3r0_8ug.ui_common.model.Photo
import com.z3r08ug.chillspace.ui.login.LoginViewModel
import com.z3r08ug.chillspace.utils.DateUtils.Companion.isUserUnder18
import com.z3r0_8ug.ui_common.model.UserInfo
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.HashMap

class FirebaseUtils {
    companion object {
        suspend fun loginUser(
            activity: Activity?,
            auth: FirebaseAuth?,
            email: String,
            password: String,
            onLogin: () -> Unit,
            loginState: MutableLiveData<LoginViewModel.LoginState>
        ) {
            if (activity != null) {
                try {
                    val authResult = auth?.signInWithEmailAndPassword(email, password)?.await()
                    // Sign in success, update UI with the signed-in user's information
                    Timber.d("signInWithEmail:success")
                    onLogin()
                    loginState.postValue(LoginViewModel.LoginState.SUCCESS)
                } catch (exception: Exception) {
                    // If sign in fails, display a message to the user.
                    Timber.e("signInWithEmail:failure")
                    Toast.makeText(
                        activity, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    loginState.postValue(LoginViewModel.LoginState.FAILURE)
                }
            }
        }

        suspend fun savePhoto(
            imageUri: Pair<Uri?, MediaType>,
            user: FirebaseUser?,
            context: Context
        ) {
            var storageResult: UploadTask.TaskSnapshot? = null
            val storageRef = user?.uid?.let { uid ->
                FirebaseStorage.getInstance().reference.child("photos").child(
                    uid
                ).child(UUID.randomUUID().toString())
            }
            imageUri.first?.let { uri ->
                try {
                    storageResult = storageRef?.putFile(uri)?.await()
                    Timber.d("Successfully uploaded photo")
                } catch (exception: Exception) {
                    Timber.d("Error uploading photo")
                }

                try {
                    val photo = hashMapOf(
                        "url" to storageResult?.storage?.downloadUrl?.await(),
                        "mediaType" to imageUri.second.type,
                        "timeStamp" to LocalDateTime.now()
                    )

                    val ref = user?.let {
                        FirebaseFirestore.getInstance()
                            .collection("users")
                            .document(it.uid)
                            .collection("photos")
                            .document()
                    }

                    val databaseResult = ref?.set(photo)?.await()
                    Timber.d("Successfully logged uploaded photo into db")
                    Toast.makeText(
                        context,
                        "Photo uploaded successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (exception: Exception) {
                    Timber.d("Error saving photo data to db ${exception.cause}")
                }
            }
        }

        suspend fun getPhotos(user: FirebaseUser?): List<Photo> {
            val photos = user?.uid?.let {
                FirebaseFirestore.getInstance()
                    .collection("users")
                    .document(it)
                    .collection("photos")
                    .get()
                    .await()
            }?.documents

            val photoList = mutableListOf<Photo>()
            if (photos != null) {
                for (photo in photos) {
                    photoList.add(
                        Photo(
                            photo.data?.get("url") as String,
                            photo.data?.get("mediaType") as String,
                            photo.data?.get("timeStamp") as HashMap<Any, Any>
                        )
                    )
                }
            }
            return photoList.toList()
        }

        suspend fun getUserInfo(
            user: FirebaseUser?
        ): UserInfo {
            val userData = user?.uid?.let {
                FirebaseFirestore.getInstance()
                    .collection("users")
                    .document(it)
                    .collection("user_info")
                    .document()
                    .get()
                    .await()
            }?.data

            return UserInfo(
                dob = userData?.get("dob") as LocalDate?,
                email = userData?.get("email") as String?,
                uid = userData?.get("uid") as String?,
                under_18 = userData?.get("under_18") as Boolean?,
                userName = userData?.get("userName") as String?
            )
        }

        suspend fun saveUser(
            user: FirebaseUser?,
            userName: String,
            email: String,
            dob: LocalDate,
            context: Context
        ) {
            if (user != null) {
                try {
                    val userObj = hashMapOf(
                        "uid" to user.uid,
                        "userName" to userName,
                        "email" to email,
                        "dob" to dob.atStartOfDay().toString(),
                        "under_18" to isUserUnder18(dob)
                    )

                    val ref = user.let {
                        FirebaseFirestore.getInstance()
                            .collection("users")
                            .document(it.uid)
                            .collection("user_info")
                            .document()
                    }

                    val databaseResult = ref.set(userObj).await()
                    Timber.d("Successfully logged user into db")
                    Toast.makeText(
                        context,
                        "User created successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (exception: Exception) {
                    Timber.d("Error saving user data to db $exception")
                }
            }
        }
    }
}