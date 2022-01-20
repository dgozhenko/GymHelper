package com.dh.gymhelper.presentation.ui.dashboard

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.dh.gymhelper.R
import com.dh.gymhelper.databinding.FragmentDashboardBinding
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
    }

}