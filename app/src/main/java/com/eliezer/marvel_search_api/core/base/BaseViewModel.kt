package com.eliezer.marvel_search_api.core.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    protected val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> get() = _loading

    protected val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable> get() = _error

    protected val _userErrorMessage = MutableLiveData<Int>()
    val userErrorMessage: LiveData<Int> get() = _userErrorMessage
}