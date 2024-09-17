package com.eliezer.marvel_search_api.core.attrxml

import android.graphics.Bitmap
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
@BindingAdapter("set_background_image_bitmap")
fun AppCompatImageView.setBitmap( bitmap: Bitmap?) {
    setImageBitmap(bitmap)
}