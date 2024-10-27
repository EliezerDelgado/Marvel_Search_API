package com.eliezer.marvel_search_api.ui.fragments.comic_list.viewmodel

import com.eliezer.marvel_search_api.core.base.BaseViewModel
import com.eliezer.marvel_search_api.domain.viewmodel.ComicsViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
data class ComicsListViewModel @Inject constructor(
    val comicsViewModel: ComicsViewModel
): BaseViewModel()