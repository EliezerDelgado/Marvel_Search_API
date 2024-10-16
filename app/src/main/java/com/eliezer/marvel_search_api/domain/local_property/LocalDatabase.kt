package com.eliezer.marvel_search_api.domain.local_property

import android.content.Context
import androidx.room.Room
import com.eliezer.marvel_search_api.data.room_database.database.MarvelFavoriteDatabase

object LocalDatabase {
    var db : MarvelFavoriteDatabase? = null

    fun setLocalDatabase(context: Context)
    {
        db = Room.databaseBuilder(
            context,
            MarvelFavoriteDatabase::class.java, "marvelDB"
        ).build()
    }
}