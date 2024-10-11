package com.eliezer.marvel_search_api.data.room_database.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.eliezer.marvel_search_api.data.room_database.converter.MarvelFavoriteDatabaseConverter
import com.eliezer.marvel_search_api.data.room_database.dao.CharacterDao
import com.eliezer.marvel_search_api.data.room_database.dao.ComicDao
import com.eliezer.marvel_search_api.data.room_database.dao.MyUserCredentialDao
import com.eliezer.marvel_search_api.models.dataclass.Character
import com.eliezer.marvel_search_api.models.dataclass.Comic
import com.eliezer.marvel_search_api.models.dataclass.MyUserCredential

@Database(entities = [Character::class, Comic::class,MyUserCredential::class], version = 1)
@TypeConverters(MarvelFavoriteDatabaseConverter::class)
abstract class MarvelFavoriteDatabase : RoomDatabase(){
    abstract fun characterDao(): CharacterDao
    abstract fun comicDao(): ComicDao
    abstract fun myUserCredential() : MyUserCredentialDao
}