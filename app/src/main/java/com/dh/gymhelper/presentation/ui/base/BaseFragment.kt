package com.dh.gymhelper.presentation.ui.base

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment {

    constructor()
    constructor(@LayoutRes resId: Int) : super(resId)
}