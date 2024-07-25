package com.eliezer.marvel_characters.core.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
abstract class BaseViewModel : ViewModel() {
    protected val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> get() = _loading

    protected val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable> get() = _error
}