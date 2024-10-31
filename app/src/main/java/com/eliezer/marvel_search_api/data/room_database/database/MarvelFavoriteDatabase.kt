package com.eliezer.marvel_search_api.data.room_database.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.eliezer.marvel_search_api.data.room_database.dao.CharacterDao
import com.eliezer.marvel_search_api.data.room_database.dao.ComicDao
import com.eliezer.marvel_search_api.models.dataclass.Character
import com.eliezer.marvel_search_api.models.dataclass.Comic

@Database(entities = [Character::class, Comic::class], version = 3,
    autoMigrations = [
        AutoMigration (from = 2, to = 3)
    ])
abstract class MarvelFavoriteDatabase : RoomDatabase(){

    abstract fun characterDao(): CharacterDao
    abstract fun comicDao(): ComicDao
}
