package com.eliezer.marvel_search_api.domain.local_property

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.eliezer.marvel_search_api.data.room_database.database.MarvelFavoriteDatabase

object LocalDatabase {
    var db : MarvelFavoriteDatabase? = null

    val MIGRATION_2_3 = object : Migration(2, 3) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE character ADD COLUMN image BLOB")
            database.execSQL("ALTER TABLE comic ADD COLUMN image BLOB")
        }
    }

    fun setLocalDatabase(context: Context)
    {
        db = Room.databaseBuilder(
            context,
            MarvelFavoriteDatabase::class.java, "FavoriteMarvelDB"
        ).addMigrations(MIGRATION_2_3).build()
    }
}