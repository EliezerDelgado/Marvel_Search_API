package com.eliezer.marvel_search_api.data.firebase.utils

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.CredentialOption
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import com.eliezer.marvel_search_api.data.firebase.configuration.FireStoreConfiguration
import com.eliezer.marvel_search_api.data.firebase.controllers.FirebaseController
import com.eliezer.marvel_search_api.domain.local_property.LocalAccount
import okhttp3.Request

object FirebaseCredential {
    suspend fun getCredential(context: Context,credentialManager: CredentialManager, credentialOption: CredentialOption) : GetCredentialResponse
    {
        // Request credentials
        val request = requestCredential(credentialOption)
        // Get the credential result
        val credential =  credentialManager.getCredential(context, request)
        return credential
    }
    private fun requestCredential(credentialOption: CredentialOption) =GetCredentialRequest.Builder()
            .addCredentialOption(credentialOption)
            .build()
}