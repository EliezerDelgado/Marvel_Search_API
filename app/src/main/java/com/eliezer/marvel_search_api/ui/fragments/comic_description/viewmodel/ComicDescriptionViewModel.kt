package com.eliezer.marvel_search_api.ui.fragments.comic_description.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.eliezer.marvel_search_api.core.base.BaseViewModel
import com.eliezer.marvel_search_api.data.repository.characters.mock.SetCharactersRepository
import com.eliezer.marvel_search_api.domain.usecase.GetListCharactersByComicUseCase
import com.eliezer.marvel_search_api.domain.viewmodel.CharactersViewModel
import com.eliezer.marvel_search_api.domain.viewmodel.ComicsViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.eliezer.marvel_search_api.models.dataclass.Characters

@HiltViewModel
data class ComicDescriptionViewModel @Inject constructor(
    val charactersViewModel: CharactersViewModel
): BaseViewModel()