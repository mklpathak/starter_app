package com.example.samplemovieapp

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import com.example.core.Constants

class BindingAdaptor {
    companion object {
        @BindingAdapter("banner")
        @JvmStatic
        fun bindImageView(view: ImageView, src: String) {
            view.load(Constants.IMAGE_PATH+src)
        }
    }
}