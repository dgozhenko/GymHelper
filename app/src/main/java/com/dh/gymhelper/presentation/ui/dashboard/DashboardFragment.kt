package com.dh.gymhelper.presentation.ui.dashboard

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.dh.gymhelper.R
import com.dh.gymhelper.databinding.FragmentDashboardBinding
import com.dh.gymhelper.presentation.extensions.collectFlow
import com.dh.gymhelper.presentation.extensions.viewBinding
import com.dh.gymhelper.presentation.ui.base.BaseFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class DashboardFragment : BaseFragment(R.layout.fragment_dashboard) {

    private val binding by viewBinding(FragmentDashboardBinding::bind)
    private val viewModel: DashboardViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTestUi()
        getProfileImage()
        logout()
    }

    private fun logout() {
        binding.loginButton.setOnClickListener {
            viewModel.logout(requireContext())
        }
    }

    private fun getProfileImage() {
        viewModel.getProfileSuccess.observe(viewLifecycleOwner) {
            val imageByteArray: ByteArray = Base64.decode(it, Base64.DEFAULT)
            Glide.with(requireContext())
                .load(imageByteArray)
                .placeholder(R.drawable.ic_upload)
                .fallback(R.drawable.ic_upload)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(binding.imageTest)
        }
    }

    private fun setupTestUi() {
        viewModel.getUserError.observe(viewLifecycleOwner) {
            Snackbar.make(requireView(), it, Snackbar.LENGTH_SHORT).show()
        }

        viewModel.getUserLoading.observe(viewLifecycleOwner) {
            when (it) {
                true -> binding.progressBar.visibility = View.VISIBLE
                false -> binding.progressBar.visibility = View.GONE
            }
        }

        // createUser success
        viewModel.getUserSuccess.observe(viewLifecycleOwner) {
            binding.emailTextView.text = it.email
        }

        viewModel.logoutSuccess.observe(viewLifecycleOwner) {
            findNavController().navigate(DashboardFragmentDirections.actionDashboardFragmentToWelcomeFragment())
        }
    }

}