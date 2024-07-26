package com.eliezer.marvel_characters.feature.fragments.marvel_search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.eliezer.marvel_characters.core.base.BaseViewModel
import com.eliezer.marvel_characters.domain.usecase.GetListCharactersUseCase
import com.eliezer.marvel_characters.data.repository.SetCharactersRepository
import com.eliezer.marvel_characters.models.dataclass.Character
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarvelSearchViewModel @Inject constructor(
    private val getcharactersUseCase: GetListCharactersUseCase,
    private val setcharactersUseCase: SetCharactersRepository
)  : BaseViewModel() {

    private val _sizecharacterList = MutableLiveData<Int>()
    val sizecharacterList: LiveData<Int> get() = _sizecharacterList
    fun searchCharacterList(name: String) {
        viewModelScope.launch {
            getcharactersUseCase.execute(name)
                .onStart { _loading.value = true }
                .onCompletion { _loading.value = false }
                .catch {
                    val r = it.cause
                    _error.value = it
                }
                .collect {
                    onResultOfGetListCharacter(it)
                }
        }
    }

    private fun onResultOfGetListCharacter(characters: List<Character>){
        if(characters.isNotEmpty())
            setcharactersUseCase.setListRepository(characters)
        _sizecharacterList.value = characters.size
    }
}