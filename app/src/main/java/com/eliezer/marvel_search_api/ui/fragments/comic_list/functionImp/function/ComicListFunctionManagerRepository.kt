package com.eliezer.marvel_search_api.ui.fragments.character_list.functionImp.function

import com.eliezer.marvel_search_api.data.repository.characters.mock.GetCharactersRepository
import com.eliezer.marvel_search_api.data.repository.comics.mock.GetComicsRepository
import com.eliezer.marvel_search_api.data.repository.firebase.mock.FireStoreDeleteCharacter
import com.eliezer.marvel_search_api.data.repository.firebase.mock.FireStoreDeleteComic
import com.eliezer.marvel_search_api.data.repository.firebase.mock.FireStoreInsertCharacter
import com.eliezer.marvel_search_api.data.repository.firebase.mock.FireStoreInsertComic
import javax.inject.Inject


class ComicListFunctionManagerRepository @Inject constructor(
    private val getComicsRepository: GetComicsRepository,
    private val insertComic: FireStoreInsertComic,
    private val deleteComic: FireStoreDeleteComic
)
{
    fun getListRepository(comic: String) = getComicsRepository.getListRepository(comic)
    fun insertFavoriteComic(comicId : Int) = insertComic.insertFavoriteComic(comicId)
    fun deleteFavoriteComic(comicId : Int) = deleteComic.deleteFavoriteComic(comicId)

}