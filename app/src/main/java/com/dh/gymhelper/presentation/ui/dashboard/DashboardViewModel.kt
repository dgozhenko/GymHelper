package com.dh.gymhelper.presentation.ui.dashboard

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.load.model.GlideUrl
import com.dh.gymhelper.data.network.SessionCookieJar
import com.dh.gymhelper.data.usecase.GetProfileImage
import com.dh.gymhelper.data.usecase.GetUser
import com.dh.gymhelper.domain.user.Credentials
import com.dh.gymhelper.domain.user.User
import com.dh.gymhelper.presentation.extensions.mapToViewError
import com.dh.gymhelper.presentation.extensions.onError
import com.dh.gymhelper.presentation.extensions.onSuccess
import com.dh.gymhelper.presentation.util.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getProfileImage: GetProfileImage
    ): ViewModel() {

    private val _getProfileSuccess = MutableLiveData<String>()
    val getProfileSuccess: LiveData<String> get() = _getProfileSuccess

    init {
        loadProfileImage()
    }
    private fun loadProfileImage() {
        getProfileImage.getProfileImage()
            .onStart {  }
            .onError {

            }
            .onCompletion {  }
            .onSuccess {
                _getProfileSuccess.postValue(it.profileImage)
            }
            .launchIn(viewModelScope)
    }
}