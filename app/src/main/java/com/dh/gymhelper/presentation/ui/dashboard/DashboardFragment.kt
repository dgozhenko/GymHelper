package com.dh.gymhelper.presentation.ui.dashboard

import android.os.Bundle
import android.transition.Fade
import android.util.Base64
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
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
        getProfileImage()
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

    private fun getProfileImage() {
        viewModel.getProfileSuccess.observe(viewLifecycleOwner) {
            val imageByteArray: ByteArray = Base64.decode(it, Base64.DEFAULT)
            Glide.with(requireContext())
                .load(imageByteArray)
                .placeholder(R.drawable.ic_upload)
                .fallback(R.drawable.ic_upload)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(binding.profileImage)
        }
    }

}