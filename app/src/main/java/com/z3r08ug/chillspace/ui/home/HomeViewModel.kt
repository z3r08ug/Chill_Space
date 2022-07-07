package com.z3r08ug.chillspace.ui.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.z3r0_8ug.ui_common.model.Photo
import com.z3r08ug.chillspace.utils.FirebaseUtils
import com.z3r0_8ug.ui_common.framework.util.Dispatchers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    savedState: SavedStateHandle,
    dispatchers: Dispatchers
) : ViewModel() {
    private val _photos: MutableState<List<Photo>> = mutableStateOf(mutableListOf())
    val photos = _photos

    fun getPhotoList(coroutineScope: CoroutineScope, user: FirebaseUser?) {
        coroutineScope.launch {
            _photos.value = FirebaseUtils.getPhotos(user)
        }
    }
}