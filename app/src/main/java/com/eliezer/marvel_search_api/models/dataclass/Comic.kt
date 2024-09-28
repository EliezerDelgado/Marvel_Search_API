package com.eliezer.marvel_search_api.models.dataclass

import android.os.Parcel
import android.os.Parcelable

data class Comic(
    val id : Int,
val title              : String ,
val urlPicture: String,
    val description : String,
    var favorite : Boolean = false
    ): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readByte() != 0.toByte()
    ) {
    }
    fun changeFavorite(){
        favorite = !favorite
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(urlPicture)
        parcel.writeString(description)
        parcel.writeByte(if (favorite) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Comic> {
        override fun createFromParcel(parcel: Parcel): Comic {
            return Comic(parcel)
        }

        override fun newArray(size: Int): Array<Comic?> {
            return arrayOfNulls(size)
        }
    }

}
