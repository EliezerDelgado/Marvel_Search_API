package com.eliezer.marvel_search_api.data.firebase.services

import android.content.Context
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.CredentialOption
import androidx.credentials.CustomCredential
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.NoCredentialException
import com.eliezer.marvel_search_api.data.firebase.utils.FirebaseCredential
import com.eliezer.marvel_search_api.data.firebase.utils.FirebaseGoogle
import com.eliezer.marvel_search_api.data.firebase.utils.FirebaseNonce
import com.eliezer.marvel_search_api.domain.local_property.LocalAccount
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MyFirebaseAuth @Inject constructor() {
    //https://medium.com/@TonyGnk/streamlining-authentication-using-android-credential-manager-with-firebase-for-google-sign-in-e4e75b6bd97d
    private val firebaseAuth = FirebaseAuth.getInstance()

    suspend fun googleSignInAddNewAccount(context: Context): Result<AuthResult> {
        try {
                // Initialize Credential Manager
                val credentialManager: CredentialManager = CredentialManager.create(context)
                // Generate a nonce (a random number used once)
                val googleOption: GetSignInWithGoogleOption =getSignInWithGoogleOption(context)
                // Check if the received credential is a valid Google ID Token
                val credential = getCredential(context,credentialManager,googleOption)
                return checkCredentials(credential)
            } catch (e: GetCredentialCancellationException) {
                return Result.failure(e)
            } catch (e: Exception) {
                return Result.failure(e)
            }
        }
    suspend fun googleSignInExistingAccount(context: Context): Result<AuthResult> {
        try {
                // Initialize Credential Manage
                val credentialManager: CredentialManager = CredentialManager.create(context)
                // Generate a nonce (a random number used once)
                val googleIdOption: GetGoogleIdOption =getGoogleExistingId(context)
                // Check if the received credential is a valid Google ID Token
                val credential = getCredential(context,credentialManager,googleIdOption)
                return checkCredentials(credential)
            } catch (e: GetCredentialCancellationException) {
                return Result.failure(e)
            }catch (e: NoCredentialException) {
            return Result.failure(e)
        } catch (e: Exception) {
                return Result.failure(e)
            }
    }
    suspend fun googleSignInWithCredentialAccount(credential: Credential): Result<AuthResult>
        = try {
            checkCredentials(credential)
        } catch (e: GetCredentialCancellationException) {
            Result.failure(e)
        }catch (e: NoCredentialException) {
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }
    private fun getGoogleExistingId(context : Context) : GetGoogleIdOption
    {
        val hashedNonce = FirebaseNonce.generateNonce()
        return FirebaseGoogle.getExistingGoogleAccount(context,hashedNonce)
    }
    private fun getSignInWithGoogleOption(context: Context): GetSignInWithGoogleOption
    {
        val hashedNonce = FirebaseNonce.generateNonce()
        return FirebaseGoogle.getAddNewGoogleAccount(context,hashedNonce)
    }
    private suspend fun getCredential(context: Context, credentialManager: CredentialManager, credentialOption: CredentialOption) : Credential
    {
        val result  = FirebaseCredential.getCredential(context,credentialManager,credentialOption)
        return result.credential
    }

    private suspend fun checkCredentials(credential: Credential) : Result<AuthResult> {
            if (credential is CustomCredential && credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                val googleIdTokenCredential =
                    GoogleIdTokenCredential.createFrom(credential.data)
                val authCredential =
                    GoogleAuthProvider.getCredential(googleIdTokenCredential.idToken, null)
                LocalAccount.requestCredential = credential
                val authResult = firebaseAuth.signInWithCredential(authCredential).await()
                return Result.success(authResult)
            } else {
                return Result.failure(RuntimeException("Received an invalid credential type"))
            }

    }
}