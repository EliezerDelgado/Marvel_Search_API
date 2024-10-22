package com.eliezer.marvel_search_api.ui.fragments.marvel_search.viewmodel

import android.content.Context
import androidx.credentials.exceptions.NoCredentialException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.eliezer.marvel_search_api.core.base.BaseViewModel
import com.eliezer.marvel_search_api.data.repository.characters.mock.SetCharactersRepository
import com.eliezer.marvel_search_api.data.repository.comics.mock.SetComicsRepository
import com.eliezer.marvel_search_api.models.dataclass.Characters
import com.eliezer.marvel_search_api.models.dataclass.Comics
import com.eliezer.marvel_search_api.models.dataclass.UserAccount
import com.eliezer.marvel_search_api.ui.fragments.marvel_search.viewmodel.usecase.MarvelSearchUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarvelSearchViewModel @Inject constructor(
    private val setCharactersUseCase : SetCharactersRepository,
    private val setComicsUseCase : SetComicsRepository,
    private val marvelSearchUseCases: MarvelSearchUseCases
)  : BaseViewModel() {

    private var _sizeResult = MutableLiveData<Int>()
    val sizeResult: LiveData<Int> get() = _sizeResult

    private var _googleAuthResult = MutableLiveData<UserAccount>()
    val googleAuthResult: LiveData<UserAccount> get() = _googleAuthResult

    fun searchCharactersList(name: String) {
        viewModelScope.launch {
            marvelSearchUseCases.getCharactersUseCase.invoke(name)
                .onStart { _loading.value = true }
                .onCompletion { _loading.value = false }
                .catch {
                    _error.value = it
                }
                .collect {
                    onResultOfGetListCharacter(it, name)
                }
        }
    }

    fun searchComicsList(title: String) {
        viewModelScope.launch {
            marvelSearchUseCases.getComicsUseCase.invoke(title)
                .onStart { _loading.value = true }
                .onCompletion { _loading.value = false }
                .catch {
                    _error.value = it
                }
                .collect {
                    onResultOfGetListComics(it, title)
                }
        }
    }

    fun signInGoogleAccount(context: Context) {
        viewModelScope.launch {
            marvelSearchUseCases.getGoogleExistingAccountUseCase.invoke(context)
                .onStart { _loading.value = true }
                .onCompletion { _loading.value = false }
                .catch {
                    _error.value = it
                }
                .collect {
                    notifySignInGoogleAccount(
                        it, context
                    )
                }
        }
    }

    private fun signInNewGoogleAccount(context: Context) {
        viewModelScope.launch {
            marvelSearchUseCases.getGoogleAddNewAccountUseCase.invoke(context)
                .onStart { _loading.value = true }
                .onCompletion { _loading.value = false }
                .catch {
                    _error.value = it
                }
                .collect {
                    notifySignInGoogleAccount(
                        it, context
                    )
                }
        }
    }


    private fun notifySignInGoogleAccount(
        result: Result<UserAccount>, context: Context?
    ) {
        result.fold(
            onSuccess = {
                _googleAuthResult.postValue(it)
            },
            onFailure = { e ->
                _error.value = e
                //TODO Cambiar
                if (e is NoCredentialException)
                    context?.also { signInNewGoogleAccount(it) }
            }
        )
    }

    private fun onResultOfGetListCharacter(characters: Characters, name: String) {
        if (characters.listCharacters.isNotEmpty())
            setCharactersUseCase.setListRepository(name, characters)
        _sizeResult.postValue(characters.listCharacters.size)
    }

    private fun onResultOfGetListComics(comics: Comics, title: String) {
        if (comics.listComics.isNotEmpty())
            setComicsUseCase.setListRepository(title, comics)
        _sizeResult.postValue(comics.listComics.size)
    }

    fun resetSizeResult() {
        _sizeResult = MutableLiveData<Int>()
    }

    fun resetLists() {
        setComicsUseCase.resetListRepository()
        setCharactersUseCase.resetListRepository()
    }

    fun resetAuthResult() {
        _googleAuthResult = MutableLiveData<UserAccount>()
    }
}