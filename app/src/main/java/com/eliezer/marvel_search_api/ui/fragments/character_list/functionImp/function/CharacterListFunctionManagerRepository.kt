package com.eliezer.marvel_search_api.ui.fragments.character_list.functionImp.function

import com.eliezer.marvel_search_api.data.repository.characters.mock.GetCharactersRepository
import com.eliezer.marvel_search_api.data.repository.firebase.mock.FireStoreDeleteCharacter
import com.eliezer.marvel_search_api.data.repository.firebase.mock.FireStoreInsertCharacter
import javax.inject.Inject


data class CharacterListFunctionManagerRepository @Inject constructor(
    val getCharactersRepository: GetCharactersRepository,
    val insertCharacter: FireStoreInsertCharacter,
    val deleteCharacter: FireStoreDeleteCharacter
)