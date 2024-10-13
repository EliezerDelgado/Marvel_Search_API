package com.eliezer.marvel_search_api.data.room_database.converter

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Parcel
import androidx.room.TypeConverter


class MarvelLocalDatabaseConverter {
    @SuppressLint("Recycle")
    @TypeConverter
    fun bundlesToBase64(bundle: Bundle) : ByteArray
    {
        val parcel = Parcel.obtain() //creating empty parcel object
        parcel.writeBundle(bundle) //saving bundle as parcel
        val byteArray = parcel.marshall()!!
        return byteArray
    }
    @TypeConverter
    fun base64ToBundle(arrayBytes: ByteArray) : Bundle
    {
        val parcel = Parcel.obtain() //creating empty parcel object
        parcel.unmarshall(arrayBytes, 0, arrayBytes.size);
        parcel.setDataPosition(0);
        val bundle = Bundle.CREATOR.createFromParcel(parcel)
        parcel.recycle()
        return bundle
    }
}