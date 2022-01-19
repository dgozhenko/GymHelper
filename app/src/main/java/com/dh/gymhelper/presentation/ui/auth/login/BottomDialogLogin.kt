package com.dh.gymhelper.presentation.ui.auth.login

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.dh.gymhelper.R
import com.dh.gymhelper.databinding.BottomDialogLoginBinding
import com.dh.gymhelper.presentation.extensions.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class BottomDialogLogin: BottomSheetDialogFragment() {

    private var confirmButtonListener: View.OnClickListener? = null
    private val binding by viewBinding(BottomDialogLoginBinding::bind)

    fun setOnClickListener(listener: View.OnClickListener): BottomSheetDialogFragment {
        confirmButtonListener = listener
        return this
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_dialog_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        confirmButtonListener()
    }

    private fun confirmButtonListener() {
        binding.confirmButton.setOnClickListener {
            confirmButtonListener?.onClick(it)
            binding.emailTextField.error = null
            when {
                binding.emailTextField.text.isNullOrBlank() && binding.passwordTextField.text.isNullOrBlank() -> emptyEmailAndPassword()
                binding.emailTextField.text.isNullOrBlank() -> emptyEmail()
                binding.passwordTextField.text.isNullOrBlank() -> emptyPassword()
                else -> login(
                    email = binding.emailTextField.text.toString(),
                    password = binding.passwordTextField.text.toString()
                )
            }
        }
    }

    private fun login(email: String, password: String) {
        Snackbar.make(dialog!!.window!!.decorView, "Success: $email $password", Snackbar.LENGTH_SHORT).show()
    }

    private fun emptyEmailAndPassword() {
        binding.emailTextField.error = "Enter E-mail"
        Snackbar.make(dialog!!.window!!.decorView, "Enter E-mail and Password please", Snackbar.LENGTH_SHORT).show()
    }

    private fun emptyPassword() {
        Snackbar.make(dialog!!.window!!.decorView, "Enter Password please", Snackbar.LENGTH_SHORT).show()
    }

    private fun emptyEmail() {
        binding.emailTextField.error = "Enter E-mail"
        Snackbar.make(dialog!!.window!!.decorView, "Enter E-mail please", Snackbar.LENGTH_SHORT).show()
    }
}