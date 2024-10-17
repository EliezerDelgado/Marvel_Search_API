package com.eliezer.marvel_search_api.ui.fragments.character_list.functionImp.function

import com.eliezer.marvel_search_api.data.repository.characters.mock.GetCharactersRepository
import com.eliezer.marvel_search_api.data.repository.characters.mock.SetCharactersRepository
import com.eliezer.marvel_search_api.data.repository.firebase.mock.FireStoreDeleteCharacter
import com.eliezer.marvel_search_api.data.repository.firebase.mock.FireStoreInsertCharacter
import com.eliezer.marvel_search_api.models.dataclass.Character
import javax.inject.Inject


class CharacterListFunctionManagerRepository @Inject constructor(
    private val getCharactersRepository: GetCharactersRepository,
    private val setCharactersRepository: SetCharactersRepository,
    private val insertCharacter: FireStoreInsertCharacter,
    private val deleteCharacter: FireStoreDeleteCharacter
)
{
    fun getListRepository(character:String) = getCharactersRepository.getListRepository(character)
    fun insertFavoriteCharacterInDatabase(vararg character : Character) = setCharactersRepository.setInRoomDatabase(*character)
    fun deleteFavoriteCharacterInDatabase(vararg character : Character) = setCharactersRepository.deleteInRoomDatabase(*character)
    fun insertFavoriteCharacterFireStore(characterInt:Int) = insertCharacter.insertFavoriteCharacter(characterInt)
    fun deleteFavoriteCharacterFireStore(characterInt:Int) = deleteCharacter.deleteFavoriteCharacter(characterInt)
}