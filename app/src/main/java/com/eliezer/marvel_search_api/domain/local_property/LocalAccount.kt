package com.eliezer.marvel_search_api.domain.local_property

import androidx.lifecycle.MutableLiveData
import com.eliezer.marvel_search_api.models.dataclass.UserAccount
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


object LocalAccount {
    var user = MutableLiveData<UserAccount?>()
    val auth get() = Firebase.auth
    val email get() = Firebase.auth.currentUser?.email
}