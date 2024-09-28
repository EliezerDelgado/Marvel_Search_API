package com.eliezer.marvel_search_api.ui.fragments.marvel_search.functionImp

import android.content.Context
import android.os.Build
import androidx.annotation.StringRes
import androidx.lifecycle.LifecycleOwner
import com.eliezer.marvel_search_api.R
import com.eliezer.marvel_search_api.data.local_property.LocalAccount
import com.eliezer.marvel_search_api.domain.actions.NavigationMainActions
import com.eliezer.marvel_search_api.databinding.FragmentMarvelSearchBinding
import com.eliezer.marvel_search_api.ui.fragments.marvel_search.viewmodel.MarvelSearchViewModel
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

@OptIn(DelicateCoroutinesApi::class)
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
                    setObserveSizeResult()
                    nameButtonPulse = id.toString()
                    searchListComics(textSearch)
                }
            }
            marvelSearchButtonGoCharacterList.apply {
                setOnClickListener {
                    val textSearch  = marvelSearchTextInputSearch.editText?.text.toString()
                    setObserveSizeResult()
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
                    setObserveAuthResult()
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
    private fun setObserveSizeResult() {
        viewModel.sizeResult.observe(owner,::getSizeResultList)
    }
    private fun setObserveAuthResult(){
        viewModel.authResult.observe(owner,::setAccount)
    }

    private fun setAccount(authResult: AuthResult) {
        setNotObserveAuthResult()
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
    fun disableButtons() {
        GlobalScope.launch {
            withContext(Dispatchers.Main) {
                binding.marvelSearchButtonGoComicsList.isEnabled = false
                binding.marvelSearchButtonGoCharacterList.isEnabled = false
                binding.marvelSearchImageButtonGoFavorite.isEnabled = false
                binding.marvelSearchImageButtonGoogleSignIn.isEnabled = false
                binding.marvelSearchImageButtonGoogleSignIn.setImageResource(R.drawable.ic_google_sign_in_disable)
            }
        }
    }
    fun enableButtons() {
        GlobalScope.launch {
            withContext(Dispatchers.Main) {
                binding.marvelSearchButtonGoComicsList.isEnabled = true
                binding.marvelSearchButtonGoCharacterList.isEnabled = true
                binding.marvelSearchImageButtonGoFavorite.isEnabled = true
                binding.marvelSearchImageButtonGoogleSignIn.isEnabled = true
                binding.marvelSearchImageButtonGoogleSignIn.setImageResource(R.drawable.ic_google_sign_in)
            }
        }
    }

    private fun getSizeResultList(size: Int){
        setNotObserveSizeResult()
        if(size>0) {
            moveFragment()
        }
        else if(size==0)
            showError(R.string.marvel_search_button_go_character_list__text)
        enableButtons()
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

    private fun setNotObserveSizeResult() {
        viewModel.sizeResult.removeObservers(owner)
        viewModel.resetSizeResult()
    }
    private fun setNotObserveAuthResult() {
        viewModel.authResult.removeObservers(owner)
        viewModel.resetAuthResult()
    }

    fun resetLists() {
        viewModel.resetLists()
    }
}

