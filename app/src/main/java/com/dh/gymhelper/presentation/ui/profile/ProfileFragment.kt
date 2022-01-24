package com.dh.gymhelper.presentation.ui.profile

import android.os.Bundle
import android.transition.Fade
import android.transition.TransitionInflater
import android.util.Base64
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.dh.gymhelper.R
import com.dh.gymhelper.databinding.FragmentProfileBinding
import com.dh.gymhelper.presentation.extensions.viewBinding
import com.dh.gymhelper.presentation.ui.base.BaseFragment
import com.dh.gymhelper.presentation.ui.dashboard.DashboardFragmentDirections
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ProfileFragment : BaseFragment(R.layout.fragment_profile) {

    private val binding by viewBinding(FragmentProfileBinding::bind)
    private val viewModel by viewModels<ProfileViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        logout()
        initObservers()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = Fade(Fade.MODE_IN)
        exitTransition = Fade(Fade.MODE_OUT)
        sharedElementEnterTransition =
            TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
    }

    private fun initToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

        private fun logout() {
        binding.signOutButton.setOnClickListener {
            viewModel.logout(requireContext())
        }
    }

    private fun initObservers() {
        // user success
        viewModel.getUserSuccess.observe(viewLifecycleOwner) {
            binding.profileName.text = it.firstName
            binding.profileLastName.text = it.lastName
        }

        // user loading
        viewModel.getUserLoading.observe(viewLifecycleOwner) {
            when (it) {
                true -> binding.progressBar.visibility = View.VISIBLE
                false -> binding.progressBar.visibility = View.GONE
            }
        }

        // user error
        viewModel.getUserError.observe(viewLifecycleOwner) {
            Snackbar.make(requireView(), it, Snackbar.LENGTH_SHORT).show()
        }

        // profile image success
        viewModel.getProfileImageSuccess.observe(viewLifecycleOwner) {
            val imageByteArray: ByteArray = Base64.decode(it, Base64.DEFAULT)
            Glide.with(requireContext())
                .load(imageByteArray)
                .placeholder(R.drawable.ic_upload)
                .fallback(R.drawable.ic_upload)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(binding.profileImage)
        }

        // logout
        viewModel.logoutSuccess.observe(viewLifecycleOwner) {
            findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToWelcomeFragment())
        }
    }
}