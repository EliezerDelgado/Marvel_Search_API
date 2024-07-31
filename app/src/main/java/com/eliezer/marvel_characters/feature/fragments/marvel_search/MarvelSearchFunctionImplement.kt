package com.eliezer.marvel_characters.feature.fragments.marvel_search

import androidx.annotation.StringRes
import androidx.lifecycle.LifecycleOwner
import com.eliezer.marvel_characters.R
import com.eliezer.marvel_characters.io.actions.NavigationMainActions
import com.eliezer.marvel_characters.databinding.FragmentMarvelSearchBinding
import com.eliezer.marvel_characters.feature.fragments.marvel_search.viewmodel.MarvelSearchViewModel

class MarvelSearchFunctionImplement(
    private val binding: FragmentMarvelSearchBinding,
    private val marvelSearchViewModel: MarvelSearchViewModel,
    private val navigationMainActions: NavigationMainActions,
    private val owner : LifecycleOwner
) {
    fun buttonListener(){
        binding.marvelSearchButtonGoComicsList.setOnClickListener { goComicsListFragment() }
        binding.marvelSearchButtonGoCharacterList.setOnClickListener {
            setObservesVM()
            binding.marvelSearchTextInputSearch.editText?.text.toString().also {
                searchListCharacters(it)
            }
        }
    }
    private fun goCharacterListFragment()=
        navigationMainActions.doActionMarvelSearchsFragmentToCharacterListFragment()
    private fun goComicsListFragment() = navigationMainActions.doActionMarvelSearchsFragmentToComicListFragment()
    private fun setObservesVM() {
        marvelSearchViewModel.sizeCharacterList.observe(owner,::getSizeCharacterList)
    }
    private fun searchListCharacters(name:String)
    {
        disableButtons()
        marvelSearchViewModel.searchCharacterList(name)
    }

    private fun disableButtons() {
        binding.marvelSearchButtonGoComicsList.isEnabled = false
        binding.marvelSearchButtonGoCharacterList.isEnabled = false
    }
    private fun enableButtons() {
        binding.marvelSearchButtonGoComicsList.isEnabled = true
        binding.marvelSearchButtonGoCharacterList.isEnabled = true
    }

    private fun getSizeCharacterList(size: Int){
        if(size>0)
            goCharacterListFragment()
        else if(size==0)
            showError(R.string.marvel_search_button_go_character_list__text)
        enableButtons()
        setNotObservesVM()
    }

    private fun showError(@StringRes idError: Int) {
        //todo e un utils
    }

    private fun setNotObservesVM() {
        marvelSearchViewModel.resetSizeCharacterList()
        marvelSearchViewModel.sizeCharacterList.removeObservers(owner)
    }
}