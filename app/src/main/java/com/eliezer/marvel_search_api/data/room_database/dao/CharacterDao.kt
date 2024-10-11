package com.eliezer.marvel_search_api.data.room_database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.eliezer.marvel_search_api.models.dataclass.Character
import com.eliezer.marvel_search_api.models.dataclass.Comic

@Dao
interface CharacterDao
{
    @Query("SELECT * FROM character")
    fun getFavoriteCharacter(): List<Character>
    @Insert
    fun insertAll(vararg characters: Character)
    @Delete
    fun delete(character: Character)
}
