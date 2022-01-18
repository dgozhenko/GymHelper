package com.dh.gymhelper.presentation.ui.auth.login

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dh.gymhelper.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputLayout

class BottomDialogLogin: BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_dialog_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        colorListenerForTextFields(view)
    }

    private fun colorListenerForTextFields(view: View) {
        val loginField = view.findViewById<TextInputLayout>(R.id.email_input_layout)
        loginField.setOnFocusChangeListener { _, hasFocus ->
            val color = if (hasFocus) Color.BLUE else Color.GRAY
            loginField.setStartIconTintList(ColorStateList.valueOf(color))
        }
    }
}