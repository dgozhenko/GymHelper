package com.dh.gymhelper.presentation.ui.auth.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dh.gymhelper.data.usecase.CreateUserUseCase
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
class SignUpViewModel @Inject constructor(private val createUserUseCase: CreateUserUseCase) :
    ViewModel() {

    private val _createUserSuccess = MutableLiveData<String>()
    val createUserSuccess: LiveData<String> get() = _createUserSuccess

    private val _createUserLoading = MutableLiveData<Boolean>()
    val createUserLoading: LiveData<Boolean> get() = _createUserLoading

    private val _createUserError = MutableLiveData<String>()
    val createUserError: LiveData<String> get() = _createUserError

    fun createUser(firstName: String, lastName: String, email: String, password: String) {
        val user = User(
            firstName = firstName,
            lastName = lastName,
            email = email,
            passwordHash = password
        )
        createUserUseCase.createUser(user = user)
            .onStart {
                _createUserLoading.postValue(true)
            }
            .onSuccess {
                _createUserSuccess.postValue(it)
            }
            .onError {
                _createUserError.postValue(it.mapToViewError().message)
            }
            .onCompletion {
                _createUserLoading.postValue(false)
            }
            .launchIn(viewModelScope)
    }
}