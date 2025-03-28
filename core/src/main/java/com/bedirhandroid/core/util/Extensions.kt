package com.bedirhandroid.core.util

import android.widget.ImageView
import com.bedirhandroid.core.R
import com.bumptech.glide.Glide

fun ImageView.loadImage(url: String) {
    Glide.with(this)
        .load(url)
        .placeholder(R.drawable.ic_error)
        .error(R.drawable.ic_error)
        .into(this)
}
