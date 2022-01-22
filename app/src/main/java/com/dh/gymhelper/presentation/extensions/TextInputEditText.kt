package com.dh.gymhelper.presentation.extensions

import android.text.InputFilter
import com.google.android.material.textfield.TextInputEditText

fun TextInputEditText.allowOnlyAlphaNumericCharacters() {
    filters = arrayOf(
        InputFilter { src, start, end, dst, dstart, dend ->
            if (src.toString().matches(Regex("[a-zA-Z 0-9]+"))) {
                src
            } else ""
        }
    )
}