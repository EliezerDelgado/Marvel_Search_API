package com.eliezer.marvel_search_api.ui.fragments.marvel_search.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.eliezer.marvel_search_api.core.base.BaseViewModel
import com.eliezer.marvel_search_api.domain.usecase.GetGoogleAddNewAccountUseCase
import com.eliezer.marvel_search_api.domain.usecase.GetGoogleExistingAccountUseCase
import com.eliezer.marvel_search_api.models.dataclass.UserAccount
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class GoogleAuthResultViewModel @Inject constructor(
    val getGoogleExistingAccountUseCase: GetGoogleExistingAccountUseCase,
    val getGoogleAddNewAccountUseCase: GetGoogleAddNewAccountUseCase
) : BaseViewModel() {
    private var _googleAuthResult = MutableLiveData<UserAccount>()
    val googleAuthResult: LiveData<UserAccount> get() = _googleAuthResult

    fun signInGoogleAccount(context: Context) {
        viewModelScope.launch {
            getGoogleExistingAccountUseCase.invoke(context)
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

    fun signInNewGoogleAccount(context: Context) {
        viewModelScope.launch {
            getGoogleAddNewAccountUseCase.invoke(context)
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
            }
        )
    }

    fun resetAuthResult() {
        _googleAuthResult = MutableLiveData<UserAccount>()
    }
}