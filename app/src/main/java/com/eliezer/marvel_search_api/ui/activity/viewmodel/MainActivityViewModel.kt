package com.eliezer.marvel_search_api.ui.activity.viewmodel

import androidx.credentials.Credential
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.eliezer.marvel_search_api.core.base.BaseViewModel
import com.eliezer.marvel_search_api.domain.usecase.GetFavoriteIdCharactersUseCase
import com.eliezer.marvel_search_api.domain.usecase.GetFavoriteIdComicsUseCase
import com.eliezer.marvel_search_api.domain.usecase.GetGoogleAuthResultWithCredentialUseCase
import com.eliezer.marvel_search_api.domain.usecase.GetListCharactersByListIdsUseCase
import com.eliezer.marvel_search_api.domain.usecase.GetListComicsByListIdsUseCase
import com.eliezer.marvel_search_api.domain.usecase.GetLocalUserAccountCredential
import com.eliezer.marvel_search_api.models.dataclass.Characters
import com.eliezer.marvel_search_api.models.dataclass.Comics
import com.eliezer.marvel_search_api.models.dataclass.MyUserCredential
import com.google.firebase.auth.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val getLocalUserAccountCredential: GetLocalUserAccountCredential,
    private val getGoogleAuthResultWithCredentialUseCase: GetGoogleAuthResultWithCredentialUseCase,
    private val getFavoriteIdComicsUseCase: GetFavoriteIdComicsUseCase,
    private val getFavoriteIdCharactersUseCase: GetFavoriteIdCharactersUseCase,
    private val getListComicsByListIdsUseCase: GetListComicsByListIdsUseCase,
    private val getListCharactersByListIdsUseCase: GetListCharactersByListIdsUseCase
): BaseViewModel() {

    private var _myUserCredentialLiveData = MutableLiveData<MyUserCredential?>()
    val myUserCredentialLiveData: LiveData<MyUserCredential?> get() = _myUserCredentialLiveData


    private var _googleAuthResult = MutableLiveData<AuthResult>()
    val googleAuthResult: LiveData<AuthResult> get() = _googleAuthResult



    private var _listComic  = MutableLiveData<Comics>()
    val listComic: LiveData<Comics> get() = _listComic

    private var _favoriteIdComics = MutableLiveData<ArrayList<Int>>()
    val favoriteIdComics: LiveData<ArrayList<Int>> get() = _favoriteIdComics



    private var _listCharacter  = MutableLiveData<Characters>()
    val listCharacter: LiveData<Characters> get() = _listCharacter

    private var _favoriteIdCharacters = MutableLiveData<ArrayList<Int>>()
    val favoriteIdCharacters: LiveData<ArrayList<Int>> get() = _favoriteIdCharacters

    fun getLocalUserCredential() {
        viewModelScope.launch {
            getLocalUserAccountCredential.invoke(null)
                .onStart { _loading.value = true }
                .onCompletion { _loading.value = false }
                .catch {
                    _error.value = it
                }
                .collect {
                    checkLocalUserResult(it)
                }
        }
    }
    fun signInGoogle(credential: Credential) {
        viewModelScope.launch {
            getGoogleAuthResultWithCredentialUseCase.invoke(credential)
                .onStart { _loading.value = true }
                .onCompletion { _loading.value = false }
                .catch {
                    _error.value = it
                }
                .collect {
                    notifySignIn(
                        it
                    )
                }
        }
    }

    fun getFavoriteIdComicsList() =
        viewModelScope.launch {
            getFavoriteIdComicsUseCase.invoke(null)
                .onStart { _loading.value = true }
                .onCompletion { _loading.value = false }
                .catch {
                    _error.value = it
                }
                .collect {
                    checkComicsListResult(it)
                }
        }
    fun getFavoriteComicsList(ids: ArrayList<Int>) =
        viewModelScope.launch {
            getListComicsByListIdsUseCase.invoke(ids)
                .onStart { _loading.value = true }
                .onCompletion { _loading.value = false }
                .catch {
                    _error.value = it
                }
                .collect {
                    _listComic.postValue(it)
                }
        }


    fun getFavoriteIdCharactersList() =
        viewModelScope.launch {
            getFavoriteIdCharactersUseCase.invoke(null)
                .onStart { _loading.value = true }
                .onCompletion { _loading.value = false }
                .catch {
                    _error.value = it
                }
                .collect {
                    checkCharactersListResult(it)
                }
        }
    fun getFavoriteCharactersList(ids: ArrayList<Int>) =
        viewModelScope.launch {
            getListCharactersByListIdsUseCase.invoke(ids)
                .onStart { _loading.value = true }
                .onCompletion { _loading.value = false }
                .catch {
                    _error.value = it
                }
                .collect {
                    _listCharacter.postValue(it)
                }
        }

    private fun notifySignIn(result : Result<AuthResult>) {
        result.fold(
            onSuccess = {
                _googleAuthResult.postValue(it)
            },
            onFailure = { e ->
                _error.value = e
            }
        )
    }
    private fun checkComicsListResult(result: Result<ArrayList<Int>>)=
        result.fold(
            onFailure = { e ->
                _error.value = e
            },
            onSuccess = {
                _favoriteIdComics.postValue(it)
            }
        )
    private fun checkCharactersListResult(result: Result<ArrayList<Int>>)=
        result.fold(
            onFailure = { e ->
                _error.value = e
            },
            onSuccess = {
                _favoriteIdCharacters.postValue(it)
            }
        )

    private fun checkLocalUserResult(myUserCredential: MyUserCredential?){
        myUserCredential?.also {
            if(it.type !="" && it.data.size()>0)
                _myUserCredentialLiveData.postValue(it)
            else
                _myUserCredentialLiveData.postValue(null)
        }
    }
    fun resetLocalAccountCredentialResult()
    {
        _myUserCredentialLiveData = MutableLiveData<MyUserCredential?>()
    }
    fun resetGoogleAuthResult()
    {
        _googleAuthResult = MutableLiveData<AuthResult>()
    }
    fun resetFavoriteIdComics() {
        _favoriteIdComics  = MutableLiveData<ArrayList<Int>>()
    }

    fun resetComics() {
        _listComic  = MutableLiveData<Comics>()
    }
    fun resetFavoriteIdCharacters() {
        _favoriteIdCharacters  = MutableLiveData<ArrayList<Int>>()
    }

    fun resetCharacters() {
        _listCharacter  = MutableLiveData<Characters>()
    }
}