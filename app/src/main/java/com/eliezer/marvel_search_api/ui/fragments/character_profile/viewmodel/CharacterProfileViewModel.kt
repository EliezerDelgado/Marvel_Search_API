package com.eliezer.marvel_search_api.ui.fragments.character_profile.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.eliezer.marvel_search_api.core.base.BaseViewModel
import com.eliezer.marvel_search_api.data.repository.comics.mock.SetComicsRepository
import com.eliezer.marvel_search_api.domain.usecase.GetListCharacterComicsUseCase
import com.eliezer.marvel_search_api.models.dataclass.Comics
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterProfileViewModel @Inject constructor(
    private val setComicsRepository : SetComicsRepository,
    private val getComicsUseCase: GetListCharacterComicsUseCase,
): BaseViewModel()  {

    private var _listComic  = MutableLiveData<Comics>()
    val listComic: LiveData<Comics> get() = _listComic
    fun searchComicsList(characterId: Int) {
        viewModelScope.launch {
            getComicsUseCase.invoke(characterId)
                .onStart { _loading.value = true }
                .onCompletion { _loading.value = false }
                .catch {
                    _error.value = it
                }
                .collect {
                    onResultOfGetListComics(characterId,it)
                }
        }
    }

    private fun onResultOfGetListComics(characterId : Int,comics: Comics) {
        setComicsRepository.setListRepository(characterId.toString(),comics)
        Log.d("ComicsVM","Llego")
        _listComic.postValue(comics)
    }

    fun resetComics() {
        _listComic  = MutableLiveData<Comics>()
    }
}