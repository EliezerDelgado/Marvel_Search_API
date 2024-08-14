package com.eliezer.marvel_characters.ui.fragments.character_profile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.eliezer.marvel_characters.core.base.BaseViewModel
import com.eliezer.marvel_characters.data.repository.characters.mock.SetCharactersRepository
import com.eliezer.marvel_characters.data.repository.comics.mock.SetComicsRepository
import com.eliezer.marvel_characters.domain.usecase.GetListCharacterComicsUseCase
import com.eliezer.marvel_characters.domain.usecase.GetListCharactersUseCase
import com.eliezer.marvel_characters.domain.usecase.GetListComicsUseCase
import com.eliezer.marvel_characters.models.dataclass.Comic
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterProfileViewModel @Inject constructor(
    private val setComicsUseCase : SetComicsRepository,
    private val getComicsUseCase: GetListCharacterComicsUseCase,
): BaseViewModel()  {

    private var _listComic  = MutableLiveData<List<Comic>>()
    val listComic: LiveData<List<Comic>> get() = _listComic
    fun searchComicsList(characterId: Int) {
        viewModelScope.launch {
            getComicsUseCase.invoke(characterId)
                .onStart { _loading.value = true }
                .onCompletion { _loading.value = false }
                .catch {
                    _error.value = it
                }
                .collect {
                    onResultOfGetListComics(it)
                }
        }
    }

    private fun onResultOfGetListComics(comics: List<Comic>) {
        setComicsUseCase.setListRepository(comics)
        _listComic.postValue(comics)
    }

    fun resetComics() {
        _listComic  = MutableLiveData<List<Comic>>()
    }
}