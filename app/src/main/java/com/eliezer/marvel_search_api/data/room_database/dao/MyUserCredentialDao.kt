package com.eliezer.marvel_search_api.data.room_database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.eliezer.marvel_search_api.models.dataclass.Character
import com.eliezer.marvel_search_api.models.dataclass.MyUserCredential

@Dao
interface MyUserCredentialDao {
    @Query("SELECT * FROM myusercredential limit 1")
    fun getLocalUser(): MyUserCredential
    @Insert
    fun insert(myUserCredential : MyUserCredential)
    @Update
    fun update(myUserCredential : MyUserCredential)
    @Query("DELETE FROM myusercredential")
    fun clear()
}