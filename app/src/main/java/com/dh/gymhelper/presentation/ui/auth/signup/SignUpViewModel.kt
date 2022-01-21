package com.dh.gymhelper.presentation.ui.auth.signup

import android.graphics.Bitmap
import android.util.Base64
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dh.gymhelper.data.usecase.CreateUserUseCase
import com.dh.gymhelper.data.usecase.UpdateProfileImage
import com.dh.gymhelper.domain.user.ProfileImage
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
import java.io.ByteArrayOutputStream
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val createUserUseCase: CreateUserUseCase,
    private val updateProfileImage: UpdateProfileImage,
    private val sessionManager: SessionManager
    ): ViewModel() {

    private val _createUserSuccess = MutableLiveData<Boolean>()
    val createUserSuccess: LiveData<Boolean> get() = _createUserSuccess

    private val _createUserLoading = MutableLiveData<Boolean>()
    val createUserLoading: LiveData<Boolean> get() = _createUserLoading

    private val _createUserError = MutableLiveData<String>()
    val createUserError: LiveData<String> get() = _createUserError

    private fun updateProfileImage(imageBitmap: Bitmap) {
        val out = ByteArrayOutputStream()
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out)
        val byteArray: ByteArray = out.toByteArray()
//        val header = "data:image/jpeg;base64," // this needs to be pre-pended to the encoded image
        val content = Base64.encodeToString(byteArray, Base64.DEFAULT or Base64.NO_WRAP)

        updateProfileImage.updateProfileImage(ProfileImage(profileImage = content))
            .onError {
                _createUserError.postValue(it.mapToViewError().message)
            }
            .launchIn(viewModelScope)
    }

    fun createUser(firstName: String, lastName: String, email: String, password: String, imageBitmap: Bitmap?) {
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
                _createUserSuccess.postValue(true)
                sessionManager.saveAuthToken(it)
                if (imageBitmap != null) {
                    updateProfileImage(imageBitmap)
                }
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