package com.eliezer.marvel_search_api.ui.activity.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.eliezer.marvel_search_api.core.base.BaseViewModel
import com.eliezer.marvel_search_api.domain.usecase.GetLocalUserAccountUseCase
import com.eliezer.marvel_search_api.models.dataclass.MyUserCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val getLocalUserAccountUseCase: GetLocalUserAccountUseCase
): BaseViewModel() {

    private var _myUserCredentialLiveData = MutableLiveData<MyUserCredential>()
    val myUserCredentialLiveData: LiveData<MyUserCredential> get() = _myUserCredentialLiveData

    fun getLocalUser() {
        viewModelScope.launch {
            getLocalUserAccountUseCase.invoke(null)
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

    private fun checkLocalUserResult(myUserCredential: MyUserCredential?){
        myUserCredential?.also {
            _myUserCredentialLiveData.postValue(it)
        }
    }
    fun resetLocalAccountResult()
    {
        _myUserCredentialLiveData = MutableLiveData<MyUserCredential>()
    }
}