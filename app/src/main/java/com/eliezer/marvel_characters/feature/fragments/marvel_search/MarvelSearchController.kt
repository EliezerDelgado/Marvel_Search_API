package com.eliezer.marvel_characters.feature.fragments.marvel_search

import androidx.annotation.StringRes
import androidx.lifecycle.LifecycleOwner
import com.eliezer.marvel_characters.R
import com.eliezer.marvel_characters.io.actions.NavigationMainActions
import com.eliezer.marvel_characters.databinding.FragmentMarvelSearchBinding
import com.eliezer.marvel_characters.feature.fragments.marvel_search.viewmodel.MarvelSearchViewModel

class MarvelSearchController(
    private val binding: FragmentMarvelSearchBinding,
    private val marvelSearchViewModel: MarvelSearchViewModel,
    private val navigationMainActions: NavigationMainActions,
    private val owner : LifecycleOwner
) {
    fun buttonListener(){
        binding.marvelSearchButtonGoComicsList.setOnClickListener { goComicsListFragment() }
        binding.marvelSearchButtonGoCharacterList.setOnClickListener { binding.marvelSearchTextInputSearch.editText?.text.toString().also { searchListCharacters(it) } }
    }
    private fun goCharacterListFragment() = navigationMainActions.doActionMarvelSearchsFragmentToCharacterListFragment()
    private fun goComicsListFragment() = navigationMainActions.doActionMarvelSearchsFragmentToComicListFragment()
    fun setObservesVM() {
        marvelSearchViewModel.sizecharacterList.observe(owner,::getSizeCharacterList)
    }
    private fun searchListCharacters(name:String)
    {
        marvelSearchViewModel.searchCharacterList(name)
    }
    private fun getSizeCharacterList(size: Int){
        if(size>0)
            goCharacterListFragment()
        else
            showError(R.string.marvel_search_button_go_character_list__text)
    }

    private fun showError(@StringRes idError: Int) {
        //todo e un utils
    }
}