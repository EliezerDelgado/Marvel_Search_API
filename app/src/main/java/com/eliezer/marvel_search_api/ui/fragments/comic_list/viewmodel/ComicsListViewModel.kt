package com.eliezer.marvel_search_api.ui.fragments.comic_list.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.eliezer.marvel_search_api.core.base.BaseViewModel
import com.eliezer.marvel_search_api.data.repository.comics.mock.SetComicsRepository
import com.eliezer.marvel_search_api.domain.usecase.GetFavoriteIdComicsUseCase
import com.eliezer.marvel_search_api.domain.usecase.GetListComicsOffListIdsUseCase
import com.eliezer.marvel_search_api.domain.usecase.GetListComicsOffNameUseCase
import com.eliezer.marvel_search_api.models.dataclass.Comics
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ComicsListViewModel @Inject constructor(
    private val setComicsRepository : SetComicsRepository,
    private val getListComicsOffNameUseCase: GetListComicsOffNameUseCase,
    private val getFavoriteIdComicsUseCase: GetFavoriteIdComicsUseCase,
    private val getListComicsOffListIdsUseCase: GetListComicsOffListIdsUseCase
): BaseViewModel()  {

    private var _listComic  = MutableLiveData<Comics>()
    val listComic: LiveData<Comics> get() = _listComic

    private var _favoriteIdComics = MutableLiveData<ArrayList<Int>>()
    val favoriteIdComics: LiveData<ArrayList<Int>> get() = _favoriteIdComics

    fun searchComicsList(title: String) =
        viewModelScope.launch {
            getListComicsOffNameUseCase.invoke(title)
                .onStart { _loading.value = true }
                .onCompletion { _loading.value = false }
                .catch {
                    _error.value = it
                }
                .collect {
                    onResultOfGetListComics(title,it)
                }
        }
    fun getFavoriteIdComicsList() =
        viewModelScope.launch {
            getFavoriteIdComicsUseCase.invoke(null)
                .onStart { _loading.value = true }
                .onCompletion { _loading.value = false }
                .catch {
                    _error.value = it
                }
                .collect {
                    checkComicsListResult(it)
                }
        }
    fun getFavoriteComicsList(ids: ArrayList<Int>) =
        viewModelScope.launch {
            getListComicsOffListIdsUseCase.invoke(ids)
                .onStart { _loading.value = true }
                .onCompletion { _loading.value = false }
                .catch {
                    _error.value = it
                }
                .collect {
                    _listComic.postValue(it)
                }
        }

    private fun onResultOfGetListComics(title : String,comics: Comics) {
        setComicsRepository.setListRepository(title,comics)
        _listComic.postValue(comics)
    }
    private fun checkComicsListResult(result: Result<ArrayList<Int>>)=
        result.fold(
            onFailure = { e ->
                _error.value = e
            },
            onSuccess = {
                _favoriteIdComics.postValue(it)
            }
        )
    fun resetFavoriteIdComics() {
        _favoriteIdComics  = MutableLiveData<ArrayList<Int>>()
    }

    fun resetComics() {
        _listComic  = MutableLiveData<Comics>()
    }
}