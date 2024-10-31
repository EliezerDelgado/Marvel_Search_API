package com.eliezer.marvel_search_api.domain.function

import com.eliezer.marvel_search_api.data.repository.comics.mock.GetComicsRepository
import com.eliezer.marvel_search_api.data.repository.comics.mock.SetComicsRepository
import com.eliezer.marvel_search_api.data.repository.firebase.mock.FireStoreDeleteComic
import com.eliezer.marvel_search_api.data.repository.firebase.mock.FireStoreInsertComic
import com.eliezer.marvel_search_api.models.dataclass.Comic
import com.eliezer.marvel_search_api.models.dataclass.Comics
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FunctionManagerComicRepository @Inject constructor(
    private val getComicsRepository: GetComicsRepository,
    private val setComicsRepository: SetComicsRepository,
    private val insertComic: FireStoreInsertComic,
    private val deleteComic: FireStoreDeleteComic
)
{
    fun getListRepository(comic: String) = getComicsRepository.getListRepository(comic)
    fun insertFavoriteComicInDatabase(comic : Comic) = setComicsRepository.setInRoomDatabase(comic)
    fun deleteFavoriteComicInDatabase(vararg comic : Comic) = setComicsRepository.deleteInRoomDatabase(*comic)
    fun insertFavoriteComicFireStore(comicId : Int) = insertComic.insertFavoriteComic(comicId)
    fun deleteFavoriteComicFireStore(comicId : Int) = deleteComic.deleteFavoriteComic(comicId)
    fun setListTmpComics(comics: Comics) = setComicsRepository.setListTmpComics(comics.search,comics)
}