package com.eliezer.marvel_search_api.ui.fragments.character_list.functionImp.function

import com.eliezer.marvel_search_api.data.repository.characters.mock.GetCharactersRepository
import com.eliezer.marvel_search_api.data.repository.firebase.mock.FireStoreDeleteCharacter
import com.eliezer.marvel_search_api.data.repository.firebase.mock.FireStoreInsertCharacter
import javax.inject.Inject


class CharacterListFunctionManagerRepository @Inject constructor(
    private val getCharactersRepository: GetCharactersRepository,
    private val insertCharacter: FireStoreInsertCharacter,
    private val deleteCharacter: FireStoreDeleteCharacter
)
{
    fun getListRepository(character:String) = getCharactersRepository.getListRepository(character)
    fun insertFavoriteCharacter(characterInt:Int) = insertCharacter.insertFavoriteCharacter(characterInt)
    fun deleteFavoriteCharacter(characterInt:Int) = deleteCharacter.deleteFavoriteCharacter(characterInt)
}