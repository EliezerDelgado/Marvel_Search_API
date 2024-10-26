package com.eliezer.marvel_search_api.ui.fragments.marvel_search.viewmodel

import com.eliezer.marvel_search_api.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
data class MarvelSearchViewModel @Inject constructor(
    val searchComicsViewModel: SearchComicsViewModel,
    val searchCharactersViewModel: SearchCharactersViewModel,
    val googleAuthResultViewModel: GoogleAuthResultViewModel
)  : BaseViewModel()