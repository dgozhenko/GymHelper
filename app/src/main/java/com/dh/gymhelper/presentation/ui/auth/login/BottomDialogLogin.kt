package com.dh.gymhelper.presentation.ui.auth.login

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.dh.gymhelper.R
import com.dh.gymhelper.databinding.BottomDialogLoginBinding
import com.dh.gymhelper.presentation.extensions.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class BottomDialogLogin: BottomSheetDialogFragment() {

    private var confirmButtonListener: View.OnClickListener? = null
    private val binding by viewBinding(BottomDialogLoginBinding::bind)
    private val viewModel: LoginViewModel by viewModels()

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
        initObservers()
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

    private fun initObservers() {
        viewModel.loginError.observe(viewLifecycleOwner) {
            Snackbar.make(dialog!!.window!!.decorView, it, Snackbar.LENGTH_SHORT).show()
        }

        viewModel.loginLoading.observe(viewLifecycleOwner) {
               when  (it) {
                   true -> binding.calendarPb.visibility = View.VISIBLE
                   false -> binding.calendarPb.visibility = View.GONE
               }
        }

        viewModel.loginSuccess.observe(viewLifecycleOwner) {
            Snackbar.make(dialog!!.window!!.decorView, it, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun login(email: String, password: String) {
        viewModel.login(email, password)
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