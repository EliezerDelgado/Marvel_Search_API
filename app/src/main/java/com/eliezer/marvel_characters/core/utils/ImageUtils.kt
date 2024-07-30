package com.eliezer.marvel_characters.core.utils

import android.content.ContentValues.TAG
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.util.Log
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.net.URL


fun getImageBitmap(url: String): Bitmap? {
    var bm: Bitmap? = null
    try {
        val `is` = URL(url).content as InputStream
        val bis = BufferedInputStream(`is`)
        bm = BitmapFactory.decodeStream(bis)
        bis.close()
        `is`.close()
    } catch (e: IOException) {
        Log.e(TAG, "Error getting bitmap", e)
    }
    return bm
}
fun loadImageFromWebOperations(url: String?): Drawable? {
    try {
        val `is` = URL(url).content as InputStream
        val d =  Drawable.createFromStream(`is`, "src name")
        `is`.close()
        return d
    } catch (ignored: IOException) {
        return null
    }
}