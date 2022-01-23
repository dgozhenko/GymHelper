package com.dh.gymhelper.presentation.ui.dashboard

import android.os.Bundle
import android.transition.Fade
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.dh.gymhelper.R
import com.dh.gymhelper.databinding.FragmentDashboardBinding
import com.dh.gymhelper.presentation.extensions.viewBinding
import com.dh.gymhelper.presentation.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class DashboardFragment : BaseFragment(R.layout.fragment_dashboard) {

    private val binding by viewBinding(FragmentDashboardBinding::bind)
    private val viewModel: DashboardViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profileImageClicked()
    }

    private fun profileImageClicked() {
        binding.profileImage.setOnClickListener {
            val extras = FragmentNavigatorExtras(
                binding.profileImage to "profileImage"
            )
            enterTransition = Fade(Fade.MODE_IN)
            exitTransition = Fade(Fade.MODE_OUT)
            findNavController().navigate(R.id.profileFragment, null, null, extras)
        }
    }

//    private fun logout() {
//        binding.loginButton.setOnClickListener {
//            viewModel.logout(requireContext())
//        }
//    }

//    private fun getProfileImage() {
//        viewModel.getProfileSuccess.observe(viewLifecycleOwner) {
//            val imageByteArray: ByteArray = Base64.decode(it, Base64.DEFAULT)
//            Glide.with(requireContext())
//                .load(imageByteArray)
//                .placeholder(R.drawable.ic_upload)
//                .fallback(R.drawable.ic_upload)
//                .skipMemoryCache(true)
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .into(binding.imageTest)
//        }
//    }

//    private fun setupTestUi() {
//        viewModel.getUserError.observe(viewLifecycleOwner) {
//            Snackbar.make(requireView(), it, Snackbar.LENGTH_SHORT).show()
//        }
//
//        viewModel.getUserLoading.observe(viewLifecycleOwner) {
//            when (it) {
//                true -> binding.progressBar.visibility = View.VISIBLE
//                false -> binding.progressBar.visibility = View.GONE
//            }
//        }
//
//        // createUser success
//        viewModel.getUserSuccess.observe(viewLifecycleOwner) {
//            binding.emailTextView.text = it.email
//        }
//
//        viewModel.logoutSuccess.observe(viewLifecycleOwner) {
//            findNavController().navigate(DashboardFragmentDirections.actionDashboardFragmentToWelcomeFragment())
//        }
//    }

}