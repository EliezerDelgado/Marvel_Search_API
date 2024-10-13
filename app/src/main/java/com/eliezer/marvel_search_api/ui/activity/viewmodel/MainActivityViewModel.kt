package com.eliezer.marvel_search_api.ui.activity.viewmodel

import android.content.Context
import androidx.collection.emptyLongSet
import androidx.credentials.Credential
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.eliezer.marvel_search_api.core.base.BaseViewModel
import com.eliezer.marvel_search_api.domain.usecase.GetGoogleAuthResultWithCredentialUseCase
import com.eliezer.marvel_search_api.domain.usecase.GetLocalUserAccountCredential
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
    private val getGoogleAuthResultWithCredentialUseCase: GetGoogleAuthResultWithCredentialUseCase
): BaseViewModel() {

    private var _myUserCredentialLiveData = MutableLiveData<MyUserCredential?>()
    val myUserCredentialLiveData: LiveData<MyUserCredential?> get() = _myUserCredentialLiveData


    private var _googleAuthResult = MutableLiveData<AuthResult>()
    val googleAuthResult: LiveData<AuthResult> get() = _googleAuthResult

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

    fun updateLocalDatabase() {
        //Todo
    }
}