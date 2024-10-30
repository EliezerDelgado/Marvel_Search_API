package com.eliezer.marvel_search_api.ui.fragments.comic_description.viewmodel

import com.eliezer.marvel_search_api.core.base.BaseViewModel
import com.eliezer.marvel_search_api.domain.viewmodel.CharactersViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
data class ComicDescriptionViewModel @Inject constructor(
    val charactersViewModel: CharactersViewModel
): BaseViewModel()