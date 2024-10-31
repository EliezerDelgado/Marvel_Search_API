package com.eliezer.marvel_search_api.core.utils

import android.content.ContentValues.TAG
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.util.Log
import java.io.BufferedInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.URL


fun loadImageFromWebOperationsBitmap(url: String): Bitmap? {
    var bm: Bitmap? = null
    try {
        val `is` = URL(url).content as InputStream
        val bis = BufferedInputStream(`is`)
        bm = BitmapFactory.decodeStream(bis)
        bis.close()
        `is`.close()
    } catch (e: IOException) {
        Log.e(TAG, e.message, e)
    }
    return bm
}
fun Bitmap.toByteArray() : ByteArray
{
    var baos: ByteArrayOutputStream? = null
    try {
        baos = ByteArrayOutputStream()
        compress(Bitmap.CompressFormat.PNG, 100, baos)
        return baos.toByteArray()
    } finally {
        if (baos != null) {
            try {
                baos.close()
            } catch (e: IOException) {
                Log.e("***","ByteArrayOutputStream was not closed"  )
            }
        }
    }
}

fun ByteArray.toBitmap() : Bitmap
{
    val bitmap = BitmapFactory.decodeByteArray(this, 0, size);
    return bitmap
}
fun loadImageFromWebOperations(url: String?): Drawable? {
    try {
        val `is` = URL(url).content as InputStream
        val d =  Drawable.createFromStream(`is`, null)
        `is`.close()
        return d
    } catch (ignored: IOException) {
        return null
    }
}