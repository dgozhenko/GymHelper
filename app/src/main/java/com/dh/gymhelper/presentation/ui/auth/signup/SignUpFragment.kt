package com.dh.gymhelper.presentation.ui.auth.signup

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.dh.gymhelper.R
import com.dh.gymhelper.databinding.FragmentSignUpScreenBinding
import com.dh.gymhelper.presentation.extensions.viewBinding
import com.dh.gymhelper.presentation.ui.base.BaseFragment

class SignUpFragment: BaseFragment(R.layout.fragment_sign_up_screen) {

    private val binding by viewBinding(FragmentSignUpScreenBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }
}