package com.eliezer.marvel_search_api.models.dataclass

import android.os.Bundle
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MyUserCredential(
    @PrimaryKey @ColumnInfo(name = "type") val  type : String,
    @ColumnInfo(name = "data") val data : Bundle
)
