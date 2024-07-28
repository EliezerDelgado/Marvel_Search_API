package com.eliezer.marvel_characters.core.utils

import android.content.ContentValues.TAG
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import java.io.BufferedInputStream
import java.io.IOException
import java.net.URL


fun getImageBitmap(url: String): Bitmap? {
    var bm: Bitmap? = null
    try {
        val aURL = URL(url)
        val conn = aURL.openConnection()
        conn.connect()
        val `is` = conn.getInputStream()
        val bis = BufferedInputStream(`is`)
        bm = BitmapFactory.decodeStream(bis)
        bis.close()
        `is`.close()
    } catch (e: IOException) {
        Log.e(TAG, "Error getting bitmap", e)
    }
    return bm
}