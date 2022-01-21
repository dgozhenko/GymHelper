package com.dh.gymhelper.presentation.ui.auth.welcome

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.dh.gymhelper.R
import com.dh.gymhelper.databinding.FragmentWelcomeScreenBinding
import com.dh.gymhelper.presentation.extensions.viewBinding
import com.dh.gymhelper.presentation.ui.auth.login.BottomDialogLogin
import com.dh.gymhelper.presentation.ui.base.BaseFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class WelcomeFragment : BaseFragment(R.layout.fragment_welcome_screen) {

    private val binding by viewBinding(FragmentWelcomeScreenBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
        setupAuthButtonsListener()
    }

    private fun setupViewPager() {
        val images = listOf(
            R.drawable.ic_login_1_vector,
            R.drawable.ic_login_2_vector,
            R.drawable.ic_login_3_vector,
            R.drawable.ic_login_final_vector
        )
        val texts = listOf(
            "We will help you with notes for your training",
            "Focus on training, we will observe your performance",
            "Here you can store your best achievements",
            "You can train everywhere consistency - it’s a key"
        )
        val imageAdapter = WelcomePagerAdapter(imageList = images, textList = texts)
        binding.welcomeImagePager.adapter = imageAdapter
        binding.tabDots.setupWithViewPager(binding.welcomeImagePager)
    }

    private fun setupAuthButtonsListener() {
        binding.loginButton.setOnClickListener {
            BottomDialogLogin().setOnClickListener {

            }.show(childFragmentManager, "login_dialog")
        }

        binding.sighUpButton.setOnClickListener {
            findNavController().navigate(WelcomeFragmentDirections.actionWelcomeFragmentToSignUpFragment())
        }
    }
}