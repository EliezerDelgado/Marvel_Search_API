package com.eliezer.marvel_search_api.domain.local_property

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.AuthResult


object LocalAccount {
    var authResult = MutableLiveData<AuthResult?>()
    val email get() = authResult.value?.user?.email.toString()
}