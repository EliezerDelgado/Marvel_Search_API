package com.eliezer.marvel_characters.core.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eliezer.marvel_characters.domain.usecase.GetListCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
abstract class BaseViewModel : ViewModel() {
    protected val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> get() = _loading

    protected val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable> get() = _error
}