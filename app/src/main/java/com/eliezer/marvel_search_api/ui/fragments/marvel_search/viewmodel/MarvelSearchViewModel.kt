package com.eliezer.marvel_search_api.ui.fragments.marvel_search.viewmodel

import com.eliezer.marvel_search_api.core.base.BaseViewModel
import com.eliezer.marvel_search_api.domain.viewmodel.CharactersViewModel
import com.eliezer.marvel_search_api.domain.viewmodel.ComicsViewModel
import com.eliezer.marvel_search_api.domain.viewmodel.GoogleAuthResultViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
data class MarvelSearchViewModel @Inject constructor(
    val comicsViewModel: ComicsViewModel,
    val charactersViewModel: CharactersViewModel,
    val googleAuthResultViewModel: GoogleAuthResultViewModel
)  : BaseViewModel()