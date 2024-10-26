package com.eliezer.marvel_search_api.ui.activity.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eliezer.marvel_search_api.core.base.BaseViewModel
import com.eliezer.marvel_search_api.domain.usecase.GetListComicsByListIdsUseCase
import com.eliezer.marvel_search_api.domain.usecase.GetListComicsByTitleUseCase
import com.eliezer.marvel_search_api.models.dataclass.Comics
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class ListComicViewModel (
    private val getListComicsByListIdsUseCase: GetListComicsByListIdsUseCase
) : BaseViewModel() {

    private var _listComic  = MutableLiveData<Comics>()
    val listComic: LiveData<Comics> get() = _listComic

    fun getFavoriteComicsList(ids: ArrayList<Int>) =
        viewModelScope.launch {
            getListComicsByListIdsUseCase.invoke(ids)
                .onStart { _loading.value = true }
                .onCompletion { _loading.value = false }
                .catch {
                    _error.value = it
                }
                .collect {
                    _listComic.postValue(it)
                }
        }

    fun resetComics() {
        _listComic  = MutableLiveData<Comics>()
    }
}