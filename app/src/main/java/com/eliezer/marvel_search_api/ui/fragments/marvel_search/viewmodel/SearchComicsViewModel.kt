package com.eliezer.marvel_search_api.ui.fragments.marvel_search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.eliezer.marvel_search_api.R
import com.eliezer.marvel_search_api.core.base.BaseViewModel
import com.eliezer.marvel_search_api.domain.usecase.GetListComicsByTitleUseCase
import com.eliezer.marvel_search_api.models.dataclass.Comics
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchComicsViewModel @Inject constructor(
    val getComicsUseCase: GetListComicsByTitleUseCase
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

    fun resetComics() {
        _comics = MutableLiveData<Comics>()
    }
}