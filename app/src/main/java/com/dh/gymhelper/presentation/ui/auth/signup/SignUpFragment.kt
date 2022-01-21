package com.dh.gymhelper.presentation.ui.auth.signup

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.PickImageContractOptions
import com.canhub.cropper.options
import com.dh.gymhelper.R
import com.dh.gymhelper.databinding.FragmentSignUpScreenBinding
import com.dh.gymhelper.presentation.extensions.viewBinding
import com.dh.gymhelper.presentation.ui.base.BaseFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SignUpFragment : BaseFragment(R.layout.fragment_sign_up_screen) {

    private val binding by viewBinding(FragmentSignUpScreenBinding::bind)
    private val viewModel: SignUpViewModel by viewModels()

    private var outputUri: Uri? = null
    private var imageBitmap: Bitmap? = null

    private val cropImage =
        registerForActivityResult(CropImageContract()) { result ->
            if (result.isSuccessful) {
                val loadingTarget =
                    object : CustomTarget<Bitmap>(360, 360) {
                        override fun onResourceReady(
                            resource: Bitmap,
                            transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?
                        ) {
                            imageBitmap = resource
                            binding.profileImage.setImageBitmap(imageBitmap)
                            binding.addAPhotoText.visibility = View.GONE
                        }
                        override fun onLoadCleared(placeholder: Drawable?) {}
                    }


                val uriContent = result.uriContent
                outputUri = uriContent!!
                Glide.with(requireContext())
                    .asBitmap()
                    .load(outputUri)
                    .skipMemoryCache(true)
                    .into(loadingTarget)

            } else {
                // an error occurred
                val exception = result.error
                Toast.makeText(requireContext(), exception?.message, Toast.LENGTH_SHORT).show()
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        imagePickerCalled()
        confirmButtonListener()
        initObservers()
    }

    private fun imagePickerCalled() {
        binding.profileImage.setOnClickListener{
            cropImage.launch(
                options {
                    setImagePickerContractOptions(
                        PickImageContractOptions(includeGallery = true, includeCamera = true)
                    )
                }
            )
        }
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun confirmButtonListener() {
        val firstNameFieldText = binding.firstNameTextField.text
        val lastNameFieldText = binding.lastNameTextField.text
        val emailFieldText = binding.emailTextField.text
        val passwordFieldText = binding.passwordTextField.text
        val confirmPasswordFieldText = binding.confirmPasswordTextField.text
        binding.confirmButton.setOnClickListener {
            binding.firstNameTextField.error = null
            binding.lastNameTextField.error = null
            binding.emailTextField.error = null
            if (!checkForError(
                    firstNameFieldText = firstNameFieldText,
                    lastNameFieldText = lastNameFieldText,
                    emailFieldText = emailFieldText,
                    passwordFieldText = passwordFieldText,
                    confirmPasswordFieldText = confirmPasswordFieldText
                )
            ) {
                viewModel.createUser(
                    firstName = binding.firstNameTextField.text.toString(),
                    lastName = binding.lastNameTextField.text.toString(),
                    email = binding.emailTextField.text.toString(),
                    password = binding.passwordTextField.text.toString(),
                    imageBitmap = imageBitmap
                )
            }
        }
    }

    private fun initObservers() {

        viewModel.createUserError.observe(viewLifecycleOwner) {
            Snackbar.make(requireView(), it, Snackbar.LENGTH_SHORT).show()
        }

        viewModel.createUserLoading.observe(viewLifecycleOwner) {
            when (it) {
                true -> binding.progressBar.visibility = View.VISIBLE
                false -> binding.progressBar.visibility = View.GONE
            }
        }

        // createUser success
        viewModel.createUserSuccess.observe(viewLifecycleOwner) {
            if (it) {
                 findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToDashboardFragment())
            }
        }
    }

    private fun checkForError(
        firstNameFieldText: Editable?,
        lastNameFieldText: Editable?,
        emailFieldText: Editable?,
        passwordFieldText: Editable?,
        confirmPasswordFieldText: Editable?
    ): Boolean {
        return when {
            firstNameFieldText.isNullOrBlank() -> firstNameError()
            lastNameFieldText.isNullOrBlank() -> lastNameError()
            emailFieldText.isNullOrBlank() -> emailError()
            passwordFieldText.isNullOrBlank() -> passwordError()
            confirmPasswordFieldText.isNullOrBlank() -> confirmPasswordError()
            passwordFieldText.toString() != confirmPasswordFieldText.toString() -> passwordDifferenceError()
            else -> false
        }
    }

    private fun firstNameError(): Boolean {
        binding.firstNameTextField.error = "Missing first name"
        Snackbar.make(requireView(), "Enter first name please", Snackbar.LENGTH_SHORT).show()
        return true
    }

    private fun lastNameError(): Boolean {
        binding.lastNameTextField.error = "Missing last name"
        Snackbar.make(requireView(), "Enter last name please", Snackbar.LENGTH_SHORT).show()
        return true
    }

    private fun emailError(): Boolean {
        binding.emailTextField.error = "Missing email"
        Snackbar.make(requireView(), "Enter email please", Snackbar.LENGTH_SHORT).show()
        return true
    }

    private fun passwordError(): Boolean {
        Snackbar.make(requireView(), "Enter password please", Snackbar.LENGTH_SHORT).show()
        return true
    }

    private fun confirmPasswordError(): Boolean {
        Snackbar.make(requireView(), "Enter confirm password please", Snackbar.LENGTH_SHORT).show()
        return true
    }

    private fun passwordDifferenceError(): Boolean {
        Snackbar.make(
            requireView(),
            "Password and confirm password are not same",
            Snackbar.LENGTH_SHORT
        ).show()
        return true
    }
}