package com.eliezer.marvel_search_api.ui.fragments.character_profile.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.eliezer.marvel_search_api.core.base.BaseViewModel
import com.eliezer.marvel_search_api.data.repository.comics.mock.SetComicsRepository
import com.eliezer.marvel_search_api.domain.usecase.GetListComicsByCharacterUseCase
import com.eliezer.marvel_search_api.domain.viewmodel.CharactersViewModel
import com.eliezer.marvel_search_api.domain.viewmodel.ComicsViewModel
import com.eliezer.marvel_search_api.models.dataclass.Comics
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
data class CharacterProfileViewModel @Inject constructor(
    val comicsViewModel: ComicsViewModel
): BaseViewModel()