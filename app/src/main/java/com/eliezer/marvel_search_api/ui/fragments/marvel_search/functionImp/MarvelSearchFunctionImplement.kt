package com.eliezer.marvel_search_api.ui.fragments.marvel_search.functionImp

import android.content.ContentValues.TAG
import android.content.Context
import android.credentials.GetCredentialException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.PasswordCredential
import androidx.credentials.PublicKeyCredential
import androidx.lifecycle.LifecycleOwner
import com.eliezer.marvel_search_api.R
import com.eliezer.marvel_search_api.data.local_property.LocalAccount
import com.eliezer.marvel_search_api.domain.actions.NavigationMainActions
import com.eliezer.marvel_search_api.databinding.FragmentMarvelSearchBinding
import com.eliezer.marvel_search_api.ui.fragments.marvel_search.viewmodel.MarvelSearchViewModel
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

class MarvelSearchFunctionImplement(
    private val binding: FragmentMarvelSearchBinding,
    private val viewModel: MarvelSearchViewModel,
    private val navigationMainActions: NavigationMainActions,
    private val owner : LifecycleOwner
) {
    private var nameButtonPulse : String? = null

    fun buttonListener(context: Context){
        binding.apply {
            marvelSearchButtonGoComicsList.apply {
                setOnClickListener {
                    val textSearch  = marvelSearchTextInputSearch.editText?.text.toString()
                    setObservesVM()
                    nameButtonPulse = id.toString()
                    searchListComics(textSearch)
                }
            }
            marvelSearchButtonGoCharacterList.apply {
                setOnClickListener {
                    val textSearch  = marvelSearchTextInputSearch.editText?.text.toString()
                    setObservesVM()
                    nameButtonPulse = id.toString()
                    searchListCharacters(textSearch)
                }
            }
            marvelSearchImageButtonGoFavorite.apply {
                setOnClickListener {
                    nameButtonPulse = id.toString()
                    moveFragment()
                }
            }
            marvelSearchImageButtonGoogleSignIn.apply {
                setOnClickListener {
                    nameButtonPulse = id.toString()
                    googleSignIn(context)
                }
            }
        }
    }

    private fun googleSignIn(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            viewModel.signInGoogle(context)
        }
    }



    private fun goCharacterListFragment()=
        navigationMainActions.doActionMarvelSearchFragmentToCharacterListFragment(   binding.marvelSearchTextInputSearch.editText?.text.toString())
    private fun goComicsListFragment() = navigationMainActions.doActionMarvelSearchFragmentToComicListFragment(   binding.marvelSearchTextInputSearch.editText?.text.toString())
    private fun setObservesVM() {
        viewModel.sizeResult.observe(owner,::getSizeResultList)
        viewModel.resultSignIn.observe(owner,::setAccount)
    }

    private fun setAccount(authResult: AuthResult) {
        LocalAccount.authResult = authResult
    }

    private fun searchListCharacters(name:String)
    {
        disableButtons()
        viewModel.searchCharactersList(name)
    }
    private fun searchListComics(title: String) {
        disableButtons()
        viewModel.searchComicsList(title)
    }
    private fun disableButtons() {
        binding.marvelSearchButtonGoComicsList.isEnabled = false
        binding.marvelSearchButtonGoCharacterList.isEnabled = false
    }
    private fun enableButtons() {
        binding.marvelSearchButtonGoComicsList.isEnabled = true
        binding.marvelSearchButtonGoCharacterList.isEnabled = true
    }

    private fun getSizeResultList(size: Int){
        if(size>0) {
            moveFragment()
        }
        else if(size==0)
            showError(R.string.marvel_search_button_go_character_list__text)
        enableButtons()
        setNotObservesVM()
    }

    private fun moveFragment() {
        when(nameButtonPulse)
        {
            binding.marvelSearchButtonGoComicsList.id.toString() -> goComicsListFragment()
            binding.marvelSearchButtonGoCharacterList.id.toString() -> goCharacterListFragment()
            binding.marvelSearchImageButtonGoFavorite.id.toString() -> goFavoriteFragment()
        }
        nameButtonPulse=null
    }

    private fun goFavoriteFragment() {
        navigationMainActions.doActionMarvelSearchFragmentToFavoritesFragment()
    }

    private fun showError(@StringRes idError: Int) {
        //todo e un utils
    }

    private fun setNotObservesVM() {
        viewModel.sizeResult.removeObservers(owner)
        viewModel.resetSizeResult()
    }

    fun resetLists() {
        viewModel.resetLists()
    }
}

