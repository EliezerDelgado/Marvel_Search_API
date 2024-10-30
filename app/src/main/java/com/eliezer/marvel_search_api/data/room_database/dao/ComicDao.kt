package com.eliezer.marvel_search_api.data.room_database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.eliezer.marvel_search_api.models.dataclass.Comic

@Dao
interface ComicDao {
    @Query("SELECT * FROM comic")
    fun getFavoriteComic(): List<Comic>

    @Insert
    fun insertAll(vararg  comic:Comic) : List<Long>

    @Insert
    fun insert(comic:Comic) : Long

    @Delete
    fun delete(vararg comic : Comic)

    @Query("DELETE FROM comic")
    fun clear()
}