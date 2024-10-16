package com.eliezer.marvel_search_api.ui.fragments.marvel_search.viewmodel.usecase

import com.eliezer.marvel_search_api.domain.usecase.GetGoogleAddNewAccountUseCase
import com.eliezer.marvel_search_api.domain.usecase.GetGoogleExistingAccountUseCase
import com.eliezer.marvel_search_api.domain.usecase.GetListCharactersByNameUseCase
import com.eliezer.marvel_search_api.domain.usecase.GetListComicsByTitleUseCase
import javax.inject.Inject

data class MarvelSearchUseCases @Inject constructor(
    val getCharactersUseCase: GetListCharactersByNameUseCase,
    val getComicsUseCase: GetListComicsByTitleUseCase,
    val getGoogleExistingAccountUseCase: GetGoogleExistingAccountUseCase,
    val getGoogleAddNewAccountUseCase: GetGoogleAddNewAccountUseCase
)