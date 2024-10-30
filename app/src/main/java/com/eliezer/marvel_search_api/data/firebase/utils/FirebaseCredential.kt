package com.eliezer.marvel_search_api.data.firebase.utils

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.CredentialOption
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse

object FirebaseCredential {
    suspend fun getCredential(context: Context,credentialManager: CredentialManager, credentialOption: CredentialOption) : GetCredentialResponse
    {
        // Request credentials
        val request = requestCredential(credentialOption)
        // Get the credential result
        val response =  credentialManager.getCredential(context, request)
        return response
    }
    private fun requestCredential(credentialOption: CredentialOption) =GetCredentialRequest.Builder()
            .addCredentialOption(credentialOption)
            .build()
}