package com.eliezer.marvel_search_api.domain.usecase

import com.eliezer.marvel_search_api.core.base.BaseFlowUseCase
import com.eliezer.marvel_search_api.core.domain.IoDispatcher
import com.eliezer.marvel_search_api.domain.repository.FirebaseRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteIdCharactersUseCase @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val firebaseRepository: FirebaseRepository
) : BaseFlowUseCase<Void?, Result<ArrayList<Int>>>(dispatcher){
    override fun execute(params:Void?): Flow<Result<ArrayList<Int>>> = firebaseRepository.getFavoriteIdCharacters()
}