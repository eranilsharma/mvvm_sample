package com.mvvm.mvvmandroid.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter


@BindingAdapter("bindImage")
fun bindImage(imageView: ImageView, url: String?) {
    url?.let {
        if (it.isNotEmpty())
            xtnLoadImage(any = it, imageView = imageView)
    }
}