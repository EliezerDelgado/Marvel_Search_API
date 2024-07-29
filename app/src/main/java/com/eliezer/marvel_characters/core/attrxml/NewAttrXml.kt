package com.eliezer.marvel_characters.core.attrxml

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.eliezer.marvel_characters.core.utils.getImageBitmap

class NewAttrXml {
    @BindingAdapter("set_background_image_bitmap_with_url")
    fun setBackgroundImageBitmapWithUrl(imageView: ImageView,url: String) {
       imageView.setImageBitmap(getImageBitmap(url))
    }
}