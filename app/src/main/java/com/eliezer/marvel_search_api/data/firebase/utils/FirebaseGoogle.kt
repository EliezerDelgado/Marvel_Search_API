package com.eliezer.marvel_search_api.data.firebase.utils

import android.content.Context
import com.eliezer.marvel_search_api.R
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption

object FirebaseGoogle {
    fun getExistingGoogleAccount(context: Context, hashedNonce: String)= GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(context.getString(
                R.string.default_web_client_id))
            .setNonce(hashedNonce)
            .build()
    fun getAddNewGoogleAccount(context: Context, hashedNonce: String)= GetSignInWithGoogleOption.Builder(context.getString(
        R.string.default_web_client_id))
        .setNonce(hashedNonce)
        .build()
}