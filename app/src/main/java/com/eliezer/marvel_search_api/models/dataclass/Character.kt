package com.eliezer.marvel_search_api.models.dataclass

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
@Entity
data class Character  @Ignore constructor(
    @PrimaryKey  @ColumnInfo(name = "id") val id : Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "urlPicture") val urlPicture: String,
    @Nullable @ColumnInfo(name = "image") var image : ByteArray?,
    @ColumnInfo(name = "description")val description : String,
    var favorite : Boolean = false
) : Parcelable {
    constructor(
        id : Int,
        name: String,
        urlPicture: String,
        image: ByteArray?,
        description : String
    ) :this(
        id,
        name,
        urlPicture,
        image,
        description,
        false
    )

    @Ignore
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString()?: "",
        parcel.createByteArray(),
        parcel.readString()?: "",
        parcel.readByte() != 0.toByte()
    )
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Character

        if (id != other.id) return false
        if (name != other.name) return false
        if (urlPicture != other.urlPicture) return false
        if (image != null) {
            if (other.image == null) return false
            if (!image.contentEquals(other.image)) return false
        } else if (other.image != null) return false
        if (description != other.description) return false
        if (favorite != other.favorite) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name.hashCode()
        result = 31 * result + urlPicture.hashCode()
        result = 31 * result + (image?.contentHashCode() ?: 0)
        result = 31 * result + description.hashCode()
        result = 31 * result + favorite.hashCode()
        return result
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