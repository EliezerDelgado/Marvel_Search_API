package com.eliezer.marvel_search_api.ui.fragments.character_profile.viewmodel

import com.eliezer.marvel_search_api.core.base.BaseViewModel
import com.eliezer.marvel_search_api.domain.viewmodel.ComicsViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
data class CharacterProfileViewModel @Inject constructor(
    val comicsViewModel: ComicsViewModel
): BaseViewModel()