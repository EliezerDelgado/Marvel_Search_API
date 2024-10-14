package com.eliezer.marvel_search_api.ui.fragments.marvel_search.viewmodel.usecase

import com.eliezer.marvel_search_api.domain.usecase.GetAuthResultGoogleAddNewAccountUseCase
import com.eliezer.marvel_search_api.domain.usecase.GetAuthResultGoogleExistingAccountUseCase
import com.eliezer.marvel_search_api.domain.usecase.GetGoogleAuthResultWithCredentialUseCase
import com.eliezer.marvel_search_api.domain.usecase.GetListCharactersByNameUseCase
import com.eliezer.marvel_search_api.domain.usecase.GetListComicsByTitleUseCase
import javax.inject.Inject

data class MarvelSearchUseCases @Inject constructor(
    val getCharactersUseCase: GetListCharactersByNameUseCase,
    val getComicsUseCase: GetListComicsByTitleUseCase,
    val getGoogleAuthResultWithCredentialUseCase: GetGoogleAuthResultWithCredentialUseCase,
    val getAuthResultGoogleExistingAccountUseCase: GetAuthResultGoogleExistingAccountUseCase,
    val getAuthResultGoogleAddNewAccountUseCase: GetAuthResultGoogleAddNewAccountUseCase
)