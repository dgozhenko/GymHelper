package com.dh.gymhelper.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dh.gymhelper.presentation.util.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val sessionManager: SessionManager): ViewModel() {

    var loggedIn: Boolean? = null
    init {
        checkForToken()
    }

    private fun checkForToken() {
        loggedIn = !sessionManager.fetchAuthToken().isNullOrBlank()
    }

}