package com.eliezer.marvel_characters.feature.fragments.marvel_search

import com.eliezer.marvel_characters.io.actions.NavigationMainActions
import com.eliezer.marvel_characters.databinding.FragmentMarvelSearchBinding

class MarvelSearchController(private val binding: FragmentMarvelSearchBinding, private val navigationMainActions: NavigationMainActions) {
    fun buttonListener(){
        binding.marvelSearchButtonGoComicsList.setOnClickListener { goComicsListFragment() }
        binding.marvelSearchButtonGoCharacterList.setOnClickListener { goCharacterListFragment() }
    }
    private fun goCharacterListFragment() = navigationMainActions.doActionMarvelSearchsFragmentToCharacterListFragment()
    private fun goComicsListFragment() = navigationMainActions.doActionMarvelSearchsFragmentToComicListFragment()
}