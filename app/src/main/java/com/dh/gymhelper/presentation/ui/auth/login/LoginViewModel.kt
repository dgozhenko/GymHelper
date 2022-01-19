package com.dh.gymhelper.presentation.ui.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dh.gymhelper.data.datasource.UserDataSource
import com.dh.gymhelper.data.datasource.UserDataSourceImpl
import com.dh.gymhelper.data.usecase.LoginUseCase
import com.dh.gymhelper.domain.user.Credentials
import com.dh.gymhelper.presentation.extensions.mapToViewError
import com.dh.gymhelper.presentation.extensions.onError
import com.dh.gymhelper.presentation.extensions.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase): ViewModel() {

    private val _loginSuccess = MutableLiveData<String>()
            val loginSuccess: LiveData<String> get() = _loginSuccess

    private val _loginLoading = MutableLiveData<Boolean>()
    val loginLoading: LiveData<Boolean> get() = _loginLoading

    private val _loginError = MutableLiveData<String>()
    val loginError: LiveData<String> get() = _loginError

    fun login(email: String, password: String) {
            loginUseCase.login(Credentials(email = email, password = password))
                .onStart {
                    _loginLoading.postValue(true)
                }
                .onSuccess {
                    _loginSuccess.postValue(it)
                }
                .onError {
                    _loginError.postValue(it.mapToViewError().message)
                }
                .onCompletion {
                    _loginLoading.postValue(false)
                }
                .launchIn(viewModelScope)
    }

}