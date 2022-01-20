package com.dh.gymhelper.presentation.ui.auth.welcome

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.dh.gymhelper.R
import com.google.android.material.textview.MaterialTextView

class WelcomePagerAdapter(private val imageList: List<Int>, private val textList: List<String>): PagerAdapter() {

    private lateinit var layoutInflater: LayoutInflater

    override fun getCount(): Int {
        return imageList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = LayoutInflater.from(container.context)
        val view = layoutInflater.inflate(R.layout.view_pager_welcome_item, container, false)
        val image = view.findViewById<ImageView>(R.id.welcome_image)
        val text = view.findViewById<MaterialTextView>(R.id.welcome_text)
        image.setImageResource(imageList[position])
        text.text = textList[position]
        container.addView(view, 0)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}