package com.eliezer.marvel_search_api.data.room_database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.eliezer.marvel_search_api.models.dataclass.Character

@Dao
interface CharacterDao
{
    @Query("SELECT * FROM character")
    fun getFavoriteCharacter(): List<Character>
    @Insert
    fun insertAll(vararg character: Character) : List<Long>
    @Insert
    fun insertAll(character: Character) : Long
    @Delete
    fun delete(vararg character: Character)
    @Query("DELETE FROM character")
    fun clear()
}
