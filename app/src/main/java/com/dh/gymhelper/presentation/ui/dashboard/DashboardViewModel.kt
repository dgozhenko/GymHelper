package com.dh.gymhelper.presentation.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dh.gymhelper.data.usecase.GetUser
import com.dh.gymhelper.domain.user.Credentials
import com.dh.gymhelper.domain.user.User
import com.dh.gymhelper.presentation.extensions.mapToViewError
import com.dh.gymhelper.presentation.extensions.onError
import com.dh.gymhelper.presentation.extensions.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class DashboardViewModel @Inject constructor(private val getUser: GetUser): ViewModel() {

    private val _getUserSuccess = MutableLiveData<User>()
    val getUserSuccess: LiveData<User> get() = _getUserSuccess

    private val _getUserLoading = MutableLiveData<Boolean>()
    val getUserLoading: LiveData<Boolean> get() = _getUserLoading

    private val _getUserError = MutableLiveData<String>()
    val getUserError: LiveData<String> get() = _getUserError

    init {
        getUser()
    }

    private fun getUser() {
        getUser.get()
            .onStart {
                _getUserLoading.postValue(true)
            }
            .onSuccess {
                _getUserSuccess.postValue(it)
            }
            .onError {
                _getUserError.postValue(it.mapToViewError().message)
            }
            .onCompletion {
                _getUserLoading.postValue(false)
            }
            .launchIn(viewModelScope)
    }
}