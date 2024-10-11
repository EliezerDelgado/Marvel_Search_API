package com.eliezer.marvel_search_api.data.room_database.converter

import android.os.Bundle
import android.os.Parcel
import androidx.room.TypeConverter
import com.eliezer.marvel_search_api.data.mappers.decodeBase64
import com.eliezer.marvel_search_api.data.mappers.toBase64


class MarvelFavoriteDatabaseConverter {
    @TypeConverter
    fun bundlesToBase64(bundle: Bundle) : ByteArray
    {
        val parcel = Parcel.obtain() //creating empty parcel object
        bundle.writeToParcel(parcel, 0) //saving bundle as parcel
        val byteArray = parcel.createByteArray()!!
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