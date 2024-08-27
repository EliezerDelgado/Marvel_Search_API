package com.eliezer.marvel_characters.ui.fragments.comic_list.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.eliezer.marvel_characters.core.base.BaseViewModel
import com.eliezer.marvel_characters.data.repository.comics.mock.SetComicsRepository
import com.eliezer.marvel_characters.domain.usecase.GetListCharacterComicsUseCase
import com.eliezer.marvel_characters.domain.usecase.GetListComicsUseCase
import com.eliezer.marvel_characters.models.dataclass.Comics
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ComicsListViewModel @Inject constructor(
    private val setComicsRepository : SetComicsRepository,
    private val getComicsUseCase: GetListComicsUseCase,
): BaseViewModel()  {

    private var _listComic  = MutableLiveData<Comics>()
    val listComic: LiveData<Comics> get() = _listComic
    fun searchComicsList(title: String) {
        viewModelScope.launch {
            getComicsUseCase.invoke(title)
                .onStart { _loading.value = true }
                .onCompletion { _loading.value = false }
                .catch {
                    _error.value = it
                }
                .collect {
                    onResultOfGetListComics(title,it)
                }
        }
    }

    private fun onResultOfGetListComics(title : String,comics: Comics) {
        setComicsRepository.setListRepository(title,comics)
        _listComic.postValue(comics)
    }

    fun resetComics() {
        _listComic  = MutableLiveData<Comics>()
    }
}