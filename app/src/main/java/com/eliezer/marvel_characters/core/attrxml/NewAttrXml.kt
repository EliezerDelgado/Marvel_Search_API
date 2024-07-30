package com.eliezer.marvel_characters.core.attrxml

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
@BindingAdapter("set_background_image_bitmap")
fun AppCompatImageView.setBitmap( bitmap: Bitmap?) {
    setImageBitmap(bitmap)
}