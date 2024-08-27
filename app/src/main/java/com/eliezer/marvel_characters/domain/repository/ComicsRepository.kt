package com.eliezer.marvel_characters.domain.repository
import com.eliezer.marvel_characters.models.dataclass.Comic
import com.eliezer.marvel_characters.models.dataclass.Comics
import kotlinx.coroutines.flow.Flow

interface ComicsRepository {
    fun getListTmpComics(name : String) : Comics?
    fun getListComics(title : String): Flow<Comics>
    fun getListCharacterComics(characterId : Int): Flow<Comics>
    fun setListComics(id :String,params: Comics)
    fun resetList()
}