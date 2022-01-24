package com.dh.gymhelper.presentation.ui.profile

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dh.gymhelper.data.network.SessionCookieJar
import com.dh.gymhelper.data.usecase.GetProfileImage
import com.dh.gymhelper.data.usecase.GetUser
import com.dh.gymhelper.domain.user.User
import com.dh.gymhelper.presentation.extensions.mapToViewError
import com.dh.gymhelper.presentation.extensions.onError
import com.dh.gymhelper.presentation.extensions.onSuccess
import com.dh.gymhelper.presentation.util.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUser: GetUser,
    private val sessionManager: SessionManager,
    private val getProfileImage: GetProfileImage
): ViewModel() {

    private val _getUserSuccess = MutableLiveData<User>()
    val getUserSuccess: LiveData<User> get() = _getUserSuccess

    private val _getUserLoading = MutableLiveData<Boolean>()
    val getUserLoading: LiveData<Boolean> get() = _getUserLoading

    private val _getUserError = MutableLiveData<String>()
    val getUserError: LiveData<String> get() = _getUserError

    private val _logoutSuccess = MutableLiveData<Boolean>()
    val logoutSuccess: LiveData<Boolean> get() = _logoutSuccess

    private val _getProfileImageSuccess = MutableLiveData<String>()
    val getProfileImageSuccess: LiveData<String> get() = _getProfileImageSuccess

    init {
        getUser()
    }

    private fun loadProfileImage() {
        getProfileImage.getProfileImage()
            .onStart {  }
            .onError {

            }
            .onCompletion {  }
            .onSuccess {
                _getProfileImageSuccess.postValue(it.profileImage)
            }
            .launchIn(viewModelScope)
    }

    private fun getUser() {
        getUser.get()
            .onStart {
                _getUserLoading.postValue(true)
            }
            .onSuccess {
                _getUserSuccess.postValue(it)
                loadProfileImage()
            }
            .onError {
                _getUserError.postValue(it.mapToViewError().message)
            }
            .onCompletion {
                _getUserLoading.postValue(false)
            }
            .launchIn(viewModelScope)
    }

    fun logout(context: Context) {
        sessionManager.clearAuthToken()
        SessionCookieJar(context).clearCookieJar()
        _logoutSuccess.postValue(true)
    }

}