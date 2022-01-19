package com.dh.gymhelper.presentation.ui.auth.signup

import android.os.Bundle
import android.text.Editable
import android.view.View
import androidx.navigation.fragment.findNavController
import com.dh.gymhelper.R
import com.dh.gymhelper.databinding.FragmentSignUpScreenBinding
import com.dh.gymhelper.presentation.extensions.viewBinding
import com.dh.gymhelper.presentation.ui.base.BaseFragment
import com.google.android.material.snackbar.Snackbar

class SignUpFragment: BaseFragment(R.layout.fragment_sign_up_screen) {

    private val binding by viewBinding(FragmentSignUpScreenBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        confirmButtonListener()
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
                )) {
                Snackbar.make(requireView(), "User created", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkForError(
        firstNameFieldText: Editable?,
        lastNameFieldText: Editable?,
        emailFieldText: Editable?,
        passwordFieldText: Editable?,
        confirmPasswordFieldText: Editable?): Boolean {
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
        Snackbar.make(requireView(), "Password and confirm password are not same", Snackbar.LENGTH_SHORT).show()
        return true
    }
}