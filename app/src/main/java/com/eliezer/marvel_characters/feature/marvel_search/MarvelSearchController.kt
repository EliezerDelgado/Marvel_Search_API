package com.eliezer.marvel_characters.feature.marvel_search

import com.eliezer.marvel_characters.io.navigation.NavigationMainActions
import com.eliezer.marvel_characters.databinding.FragmentMarvelSearchBinding

class MarvelSearchController(val binding: FragmentMarvelSearchBinding,val navigationMainActions: NavigationMainActions) {
    fun buttonListener(){
        binding.marvelSearchButtonGoComicsList.setOnClickListener { goComicsListFragment() }
        binding.marvelSearchButtonGoCharacterList.setOnClickListener { goCharacterListFragment() }
    }
    private fun goCharacterListFragment() = navigationMainActions.actionMarvelSearchsFragmentToCharacterListFragment()
    private fun goComicsListFragment() = navigationMainActions.actionMarvelSearchsFragmentToComicListFragment()
}