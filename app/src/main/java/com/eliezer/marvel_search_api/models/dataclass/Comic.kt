package com.eliezer.marvel_search_api.models.dataclass

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class Comic @Ignore constructor(
    @PrimaryKey val id : Int,
    @ColumnInfo(name = "title")val title : String,
    @ColumnInfo(name = "urlPicture")val urlPicture: String,
    @Nullable @ColumnInfo(name = "image") var image : ByteArray?,
    @ColumnInfo(name = "description")val description : String,
    @Ignore var favorite : Boolean = false
    ): Parcelable {
    constructor(
        id : Int,
        title : String,
        urlPicture: String,
        image: ByteArray?,
       description : String
    ): this(
        id,
        title,
        urlPicture,
        image,
        description,
        false
    )

    @Ignore
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.createByteArray(),
        parcel.readString() ?: "",
        parcel.readByte() != 0.toByte()
    )

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
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Comic

        if (id != other.id) return false
        if (title != other.title) return false
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
        result = 31 * result + title.hashCode()
        result = 31 * result + urlPicture.hashCode()
        result = 31 * result + (image?.contentHashCode() ?: 0)
        result = 31 * result + description.hashCode()
        result = 31 * result + favorite.hashCode()
        return result
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
