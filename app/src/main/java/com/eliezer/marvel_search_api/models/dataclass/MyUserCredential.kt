package com.eliezer.marvel_search_api.models.dataclass

import android.os.Bundle
import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["type", "data"])
data class MyUserCredential(
    @ColumnInfo(name = "type") val  type : String,
    @ColumnInfo(name = "data") val data : Bundle
)
