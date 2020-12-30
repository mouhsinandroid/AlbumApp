package com.bourqaiba.leboncoin.util

import android.annotation.SuppressLint
import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bourqaiba.leboncoin.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun getProgressDrawable(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 4f
        centerRadius = 20f
        start()
    }
}

fun ImageView.loadImage(uri: String?, progressDrawable: CircularProgressDrawable) {
    val options = RequestOptions()
            .placeholder(progressDrawable)
            .error(R.drawable.ic_album)

    Glide.with(context)
            .setDefaultRequestOptions(options)
            .load(uri)
            .into(this)

}

@BindingAdapter("android:imageUrl")
fun loadImage(view: ImageView, url: String) {
    view.loadImage(url, getProgressDrawable(view.context))
}
