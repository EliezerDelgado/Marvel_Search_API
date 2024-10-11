package com.eliezer.marvel_search_api.models.dataclass

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
@Entity
data class Character(
    @PrimaryKey val id : Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "urlPicture") val urlPicture: String,
    @ColumnInfo(name = "description")val description : String,
    @Ignore var favorite : Boolean = false
) : Parcelable {

    @Ignore
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString()?: "",
        parcel.readString()?: "",
        parcel.readByte() != 0.toByte()
    )

    fun changeFavorite(){
        favorite = !favorite
    }
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(urlPicture)
        parcel.writeString(description)
        parcel.writeByte(if (favorite) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Character> {
        override fun createFromParcel(parcel: Parcel): Character {
            return Character(parcel)
        }

        override fun newArray(size: Int): Array<Character?> {
            return arrayOfNulls(size)
        }
    }
}