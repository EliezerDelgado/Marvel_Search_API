package com.eliezer.marvel_search_api.ui.fragments.comic_list.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.eliezer.marvel_search_api.core.base.BaseViewModel
import com.eliezer.marvel_search_api.data.repository.comics.mock.SetComicsRepository
import com.eliezer.marvel_search_api.domain.usecase.GetFavoriteListComicsOnDatabaseUseCase
import com.eliezer.marvel_search_api.domain.usecase.GetListComicsByTitleUseCase
import com.eliezer.marvel_search_api.models.dataclass.Comic
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
    private val getListComicsByTitleUseCase: GetListComicsByTitleUseCase,
    private val getFavoriteListComicsOnDatabaseUseCase: GetFavoriteListComicsOnDatabaseUseCase
): BaseViewModel()  {

    private var _listComic  = MutableLiveData<Comics>()
    val listComic: LiveData<Comics> get() = _listComic

    fun searchComicsList(title: String) =
        viewModelScope.launch {
            getListComicsByTitleUseCase.invoke(title)
                .onStart { _loading.value = true }
                .onCompletion { _loading.value = false }
                .catch {
                    _error.value = it
                }
                .collect {
                    onResultOfGetListComics(title,it)
                }
        }
    fun getFavoriteComicsList() =
        viewModelScope.launch {
            getFavoriteListComicsOnDatabaseUseCase.invoke(null)
                .onStart { _loading.value = true }
                .onCompletion { _loading.value = false }
                .catch {
                    _error.value = it
                }
                .collect {
                    setListComic(it)
                }
        }

    private fun setListComic(list: List<Comic>?) {
        list?.also {
            val comics = Comics()
            comics.total = it.size
            comics.listComics.addAll(it)
            _listComic.postValue(comics)
        }
    }

    private fun onResultOfGetListComics(title : String,comics: Comics) {
        setComicsRepository.setListTmpComics(title,comics)
        _listComic.postValue(comics)
    }
    fun resetComics() {
        _listComic  = MutableLiveData<Comics>()
    }
}