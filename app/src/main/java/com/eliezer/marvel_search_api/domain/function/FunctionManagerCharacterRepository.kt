package com.eliezer.marvel_search_api.domain.function

import com.eliezer.marvel_search_api.data.repository.characters.mock.GetCharactersRepository
import com.eliezer.marvel_search_api.data.repository.characters.mock.SetCharactersRepository
import com.eliezer.marvel_search_api.data.repository.firebase.mock.FireStoreDeleteCharacter
import com.eliezer.marvel_search_api.data.repository.firebase.mock.FireStoreInsertCharacter
import com.eliezer.marvel_search_api.models.dataclass.Character
import com.eliezer.marvel_search_api.models.dataclass.Characters
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class FunctionManagerCharacterRepository @Inject constructor(
    private val getCharactersRepository: GetCharactersRepository,
    private val setCharactersRepository: SetCharactersRepository,
    private val insertCharacter: FireStoreInsertCharacter,
    private val deleteCharacter: FireStoreDeleteCharacter
)
{
    fun getListRepository(character:String) = getCharactersRepository.getListRepository(character)
    fun insertFavoriteCharacterInDatabase(character : Character) = setCharactersRepository.setInRoomDatabase(character)
    fun deleteFavoriteCharacterInDatabase(vararg character : Character) = setCharactersRepository.deleteInRoomDatabase(*character)
    fun insertFavoriteCharacterFireStore(characterInt:Int) = insertCharacter.insertFavoriteCharacter(characterInt)
    fun deleteFavoriteCharacterFireStore(characterInt:Int) = deleteCharacter.deleteFavoriteCharacter(characterInt)
    fun setListTmpCharacters(characters : Characters) = setCharactersRepository.setListTmpCharacters(characters.search,characters)
}