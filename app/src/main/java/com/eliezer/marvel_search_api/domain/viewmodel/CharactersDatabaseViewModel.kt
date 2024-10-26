package com.eliezer.marvel_search_api.domain.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.eliezer.marvel_search_api.core.base.BaseViewModel
import com.eliezer.marvel_search_api.domain.usecase.ClearCharactersDatabaseUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharactersDatabaseViewModel @Inject constructor(
    private val clearCharactersDatabaseUseCase: ClearCharactersDatabaseUseCase
) :  BaseViewModel() {

    private var _isClear  = MutableLiveData<Boolean>()
    val isClear: LiveData<Boolean> get() = _isClear


    fun clearFavoritesCharactersList() =
        viewModelScope.launch {
            clearCharactersDatabaseUseCase.invoke(null)
                .onStart { _loading.value = true }
                .onCompletion { _loading.value = false }
                .catch {
                    _error.value = it
                }
                .collect {
                    _isClear.value = true
                }
        }
}