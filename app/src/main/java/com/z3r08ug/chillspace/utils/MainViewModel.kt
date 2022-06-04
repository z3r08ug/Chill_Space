package com.z3r08ug.chillspace.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.z3r08ug.chillspace.Screen

class MainViewModel : ViewModel() {

    private val _currentScreen = MutableLiveData<Screen>(Screen.HomeScreen)
    val currentScreen: LiveData<Screen> = _currentScreen

    fun setCurrentScreen(screen: Screen) {
        _currentScreen.value = screen
    }
}