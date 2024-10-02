package com.eliezer.marvel_search_api.ui.fragments.marvel_search.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.eliezer.marvel_search_api.core.base.BaseViewModel
import com.eliezer.marvel_search_api.data.repository.characters.mock.SetCharactersRepository
import com.eliezer.marvel_search_api.domain.usecase.GetListCharactersOffNameUseCase
import com.eliezer.marvel_search_api.data.repository.comics.mock.SetComicsRepository
import com.eliezer.marvel_search_api.domain.usecase.GetListComicsOffNameUseCase
import com.eliezer.marvel_search_api.domain.usecase.GetAuthResultGoogleExistingAccountUseCase
import com.eliezer.marvel_search_api.models.dataclass.Characters
import com.eliezer.marvel_search_api.models.dataclass.Comics
import com.google.firebase.auth.AuthResult
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
    private val getCharactersUseCase: GetListCharactersOffNameUseCase,
    private val getComicsUseCase: GetListComicsOffNameUseCase,
    private val getAuthResultGoogleExistingAccountUseCase: GetAuthResultGoogleExistingAccountUseCase
)  : BaseViewModel() {

    private var _sizeResult  = MutableLiveData<Int>()
    val sizeResult: LiveData<Int> get() = _sizeResult

    private  var _authResult = MutableLiveData<AuthResult>()
    val authResult : LiveData<AuthResult> get() = _authResult

    fun searchCharactersList(name: String) {
        viewModelScope.launch {
            getCharactersUseCase.invoke(name)
                .onStart { _loading.value = true }
                .onCompletion { _loading.value = false }
                .catch {
                    _error.value = it
                }
                .collect {
                    onResultOfGetListCharacter(it,name)
                }
        }
    }
    fun searchComicsList(title: String) {
        viewModelScope.launch {
            getComicsUseCase.invoke(title)
                .onStart { _loading.value = true }
                .onCompletion { _loading.value = false }
                .catch {
                    _error.value = it
                }
                .collect {
                    onResultOfGetListComics(it,title)
                }
        }
    }

    fun signInGoogle(context: Context) {
        viewModelScope.launch {
            getAuthResultGoogleExistingAccountUseCase.invoke(context)
                .onStart { _loading.value = true }
                .onCompletion { _loading.value = false }
                .catch {
                    _error.value = it
                }
                .collect {
                    notifySignIn(it)
                }
        }
    }




    private fun notifySignIn(result: Result<AuthResult>) {
        result.fold(
            onSuccess = {
                _authResult.postValue( it)
            },
            onFailure = { e ->
                _error.value = e
            }
        )
    }

    private fun onResultOfGetListCharacter(characters: Characters,name: String){
        if(characters.listCharacters.isNotEmpty())
            setCharactersUseCase.setListRepository(name,characters)
        _sizeResult.postValue(characters.listCharacters.size)
    }

    private fun onResultOfGetListComics(comics: Comics, title: String){
        if(comics.listComics.isNotEmpty())
           setComicsUseCase.setListRepository(title,comics)
        _sizeResult.postValue(comics.listComics.size)
    }

    fun resetSizeResult() {
        _sizeResult  = MutableLiveData<Int>()
    }

    fun resetLists() {
        setComicsUseCase.resetListRepository()
        setCharactersUseCase.resetListRepository()
    }

    fun resetAuthResult() {
        _authResult = MutableLiveData<AuthResult>()
    }
}
/*
        // GoogleIdToken credential
        is CustomCredential -> {
            if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                try {
                    // Use googleIdTokenCredential and extract the ID to validate and
                    // authenticate on your server.
                    val googleIdTokenCredential = GoogleIdTokenCredential
                        .createFrom(credential.data)
                    // You can use the members of googleIdTokenCredential directly for UX
                    // purposes, but don't use them to store or control access to user
                    // data. For that you first need to validate the token:
                    // pass googleIdTokenCredential.getIdToken() to the backend server.
                    //GoogleIdTokenVerifier verifier = ... // see validation instructions
                    //GoogleIdToken idToken = verifier.verify(idTokenString);
                    // To get a stable account identifier (e.g. for storing user data),
                    // use the subject ID:
                    // idToken.getPayload().getSubject()
                } catch (e: GoogleIdTokenParsingException) {
                    Log.e(TAG, "Received an invalid google id token response", e)
                }
            } else {
                // Catch any unrecognized custom credential type here.
                Log.e(TAG, "Unexpected type of credential")
            }
        }

        else -> {
            // Catch any unrecognized credential type here.
            Log.e(TAG, "Unexpected type of credential")
        }*/