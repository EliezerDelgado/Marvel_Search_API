package com.eliezer.marvel_search_api.domain.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.eliezer.marvel_search_api.R
import com.eliezer.marvel_search_api.core.base.BaseViewModel
import com.eliezer.marvel_search_api.domain.usecase.GetListComicsByListIdsUseCase
import com.eliezer.marvel_search_api.domain.usecase.GetListComicsByTitleUseCase
import com.eliezer.marvel_search_api.models.dataclass.Comics
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class ComicsViewModel @Inject constructor(
    private val getComicsUseCase: GetListComicsByTitleUseCase,
    private val getListComicsByListIdsUseCase: GetListComicsByListIdsUseCase,
) :BaseViewModel() {
    private var _comics = MutableLiveData<Comics>()
    val comics: LiveData<Comics> get() = _comics

    fun searchComicsList(title: String) {
        viewModelScope.launch {
            getComicsUseCase.invoke(title)
                .onStart { _loading.value = true }
                .onCompletion { _loading.value = false }
                .catch {
                    _error.value = it
                    _userErrorMessage.value = R.string.error_empty_search
                }
                .collect {
                    it.search = title
                    _comics.value = it
                }
        }
    }
    fun getFavoriteComicsList(ids: ArrayList<Int>) =
        viewModelScope.launch {
            getListComicsByListIdsUseCase.invoke(ids)
                .onStart { _loading.value = true }
                .onCompletion { _loading.value = false }
                .catch {
                    _error.value = it
                }
                .collect {
                    _comics.postValue(it)
                }
        }

    fun resetComics() {
        _comics = MutableLiveData<Comics>()
    }
}