package com.eliezer.marvel_search_api.ui.fragments.marvel_search.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.eliezer.marvel_search_api.core.base.BaseViewModel
import com.eliezer.marvel_search_api.data.repository.characters.mock.SetCharactersRepository
import com.eliezer.marvel_search_api.domain.usecase.GetListCharactersUseCase
import com.eliezer.marvel_search_api.data.repository.comics.mock.SetComicsRepository
import com.eliezer.marvel_search_api.domain.usecase.GetListComicsUseCase
import com.eliezer.marvel_search_api.domain.usecase.GetFirebaseResultSignInGoogleUseCase
import com.eliezer.marvel_search_api.models.dataclass.Characters
import com.eliezer.marvel_search_api.models.dataclass.Comics
import com.google.firebase.auth.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarvelSearchViewModel @Inject constructor(
    private val setCharactersUseCase : SetCharactersRepository,
    private val setComicsUseCase : SetComicsRepository,
    private val getCharactersUseCase: GetListCharactersUseCase,
    private val getComicsUseCase: GetListComicsUseCase,
    private val getFirebaseResultSignInGoogleUseCase: GetFirebaseResultSignInGoogleUseCase
)  : BaseViewModel() {

    private var _sizeResult  = MutableLiveData<Int>()
    val sizeResult: LiveData<Int> get() = _sizeResult

    private  var _resultSignIn = MutableLiveData<AuthResult>()
    val resultSignIn : LiveData<AuthResult> get() = _resultSignIn

    fun searchCharactersList(name: String) {
        viewModelScope.launch {
            getCharactersUseCase.invoke(name)
                .onStart { _loading.value = true }
                .onCompletion { _loading.value = false }
                .catch {
                    _error.value = it
                }
                .collect {
                    onResultOfGetListCharacter(it,name)
                }
        }
    }
    fun searchComicsList(title: String) {
        viewModelScope.launch {
            getComicsUseCase.invoke(title)
                .onStart { _loading.value = true }
                .onCompletion { _loading.value = false }
                .catch {
                    _error.value = it
                }
                .collect {
                    onResultOfGetListComics(it,title)
                }
        }
    }

    fun signInGoogle(context: Context) {
        viewModelScope.launch {
            getFirebaseResultSignInGoogleUseCase.invoke(context)
                .onStart { _loading.value = true }
                .onCompletion { _loading.value = false }
                .catch {
                    _error.value = it
                }
                .collect {
                    notifySignIn(it)
                }
        }
    }
    private fun notifySignIn(result: Result<AuthResult>) {
        result.fold(
            onSuccess = {
                _resultSignIn.value = it
            },
            onFailure = { e ->
                _error.value = e
            }
        )
    }

    private fun onResultOfGetListCharacter(characters: Characters,name: String){
        if(characters.listCharacters.isNotEmpty())
            setCharactersUseCase.setListRepository(name,characters)
        _sizeResult.postValue(characters.listCharacters.size)
    }

    private fun onResultOfGetListComics(comics: Comics, title: String){
        if(comics.listComics.isNotEmpty())
           setComicsUseCase.setListRepository(title,comics)
        _sizeResult.postValue(comics.listComics.size)
    }

    fun resetSizeResult() {
        _sizeResult  = MutableLiveData<Int>()
    }

    fun resetLists() {
        setComicsUseCase.resetListRepository()
        setCharactersUseCase.resetListRepository()
    }
}
/*
val pedingGetCredentialRequest = object : CredentialManagerCallback<GetCredentialResponse, GetCredentialException>{


    override fun onError(e: GetCredentialException) {
        Log.e("AUTH", e.message.toString())
    }

    override fun onResult(result: GetCredentialResponse) {
        handleSignIn(result)
    }
}
@SuppressLint("SuspiciousIndentation")
@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
suspend fun prueba(context: Context)
{
    val client_id = context.getString(R.string.default_web_client_id)
    val googleIdOption: GetSignInWithGoogleOption = GetSignInWithGoogleOption.Builder(client_id)
      //  .setFilterByAuthorizedAccounts(false)
   //     .setServerClientId(client_id)
   //     .setAutoSelectEnabled(true)
        //   .setNonce()
        .build()
    val credentialManager = CredentialManager.create(context)
    val request: GetCredentialRequest =  GetCredentialRequest.Builder()
        .addCredentialOption(googleIdOption)
        .build()


    coroutineScope {
        try {
            val result = credentialManager.getCredentialAsync(
                request = request,
                context = context,
                cancellationSignal = CancellationSignal(),
                executor = Executors.newSingleThreadExecutor(),
                callback = pedingGetCredentialRequest
            )
        } catch (e: Exception) {
            Log.e("AUTH", e.message.toString())
        }
    }
}
fun handleSignIn(result: GetCredentialResponse) {
    // Handle the successfully returned credential.
    val credential = result.credential

    when (credential) {

        // Passkey credential
        is PublicKeyCredential -> {
            // Share responseJson such as a GetCredentialResponse on your server to
            // validate and authenticate
            var responseJson = credential.authenticationResponseJson
        }

        // Password credential
        is PasswordCredential -> {
            // Send ID and password to your server to validate and authenticate.
            val username = credential.id
            val password = credential.password
        }

        // GoogleIdToken credential
        is CustomCredential -> {
            if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                try {
                    // Use googleIdTokenCredential and extract the ID to validate and
                    // authenticate on your server.
                    val googleIdTokenCredential = GoogleIdTokenCredential
                        .createFrom(credential.data)
                    // You can use the members of googleIdTokenCredential directly for UX
                    // purposes, but don't use them to store or control access to user
                    // data. For that you first need to validate the token:
                    // pass googleIdTokenCredential.getIdToken() to the backend server.
                    //GoogleIdTokenVerifier verifier = ... // see validation instructions
                    //GoogleIdToken idToken = verifier.verify(idTokenString);
                    // To get a stable account identifier (e.g. for storing user data),
                    // use the subject ID:
                    // idToken.getPayload().getSubject()
                } catch (e: GoogleIdTokenParsingException) {
                    Log.e(TAG, "Received an invalid google id token response", e)
                }
            } else {
                // Catch any unrecognized custom credential type here.
                Log.e(TAG, "Unexpected type of credential")
            }
        }

        else -> {
            // Catch any unrecognized credential type here.
            Log.e(TAG, "Unexpected type of credential")
        }
    }
}
@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
fun run(context: Context)
{
    runBlocking {
            prueba(context)
        }
}
*/