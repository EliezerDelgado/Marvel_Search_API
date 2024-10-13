package com.eliezer.marvel_search_api.ui.fragments.marvel_search.viewmodel.usecase

import com.eliezer.marvel_search_api.data.repository.characters.mock.SetCharactersRepository
import com.eliezer.marvel_search_api.data.repository.comics.mock.SetComicsRepository
import com.eliezer.marvel_search_api.domain.usecase.GetAuthResultGoogleAddNewAccountUseCase
import com.eliezer.marvel_search_api.domain.usecase.GetAuthResultGoogleExistingAccountUseCase
import com.eliezer.marvel_search_api.domain.usecase.GetGoogleAuthResultWithCredentialUseCase
import com.eliezer.marvel_search_api.domain.usecase.GetListCharactersOffNameUseCase
import com.eliezer.marvel_search_api.domain.usecase.GetListComicsOffNameUseCase
import javax.inject.Inject

data class MarvelSearchUseCases @Inject constructor(
    val getCharactersUseCase: GetListCharactersOffNameUseCase,
    val getComicsUseCase: GetListComicsOffNameUseCase,
    val getGoogleAuthResultWithCredentialUseCase: GetGoogleAuthResultWithCredentialUseCase,
    val getAuthResultGoogleExistingAccountUseCase: GetAuthResultGoogleExistingAccountUseCase,
    val getAuthResultGoogleAddNewAccountUseCase: GetAuthResultGoogleAddNewAccountUseCase
)