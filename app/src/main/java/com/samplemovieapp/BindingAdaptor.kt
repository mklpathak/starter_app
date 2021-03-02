package com.samplemovieapp

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import com.core.Constants

class BindingAdaptor {
    companion object {
        @BindingAdapter("banner")
        @JvmStatic
        fun bindImageView(view: ImageView, src: String) {
            view.load(Constants.IMAGE_PATH+src)
        }
    }
}