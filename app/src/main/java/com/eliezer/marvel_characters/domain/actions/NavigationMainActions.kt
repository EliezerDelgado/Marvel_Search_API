package com.eliezer.marvel_characters.domain.actions

import androidx.fragment.app.FragmentContainerView
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.eliezer.marvel_characters.R
import com.eliezer.marvel_characters.models.dataclass.Character
import com.eliezer.marvel_characters.models.dataclass.Comic
import com.eliezer.marvel_characters.ui.fragments.character_list.CharactersListFragmentDirections
import com.eliezer.marvel_characters.ui.fragments.comic_list.ComicsListFragmentDirections
import com.eliezer.marvel_characters.ui.fragments.marvel_search.MarvelSearchFragmentDirections

class NavigationMainActions (private val navHostFragment: FragmentContainerView){
    fun doActionMarvelSearchFragmentToCharacterListFragment(search : String) {
        val action = MarvelSearchFragmentDirections.actionMarvelSearchFragmentToCharacterListFragment(search)
        navHostFragment.findNavController().navigate(action)
    }

    fun doActionMarvelSearchFragmentToComicListFragment(search : String) {
        val action = MarvelSearchFragmentDirections.actionMarvelSearchFragmentToComicListFragment(search)
        navHostFragment.findNavController().navigate(action)
    }

    fun navigateUp()
    {
        NavHostFragment.findNavController(navHostFragment.getFragment()).navigateUp()
    }
    fun actionCharacterListFragmentToCharacterProfileFragment(character: Character)
    {
        val action = CharactersListFragmentDirections.actionCharacterListFragmentToCharacterProfileFragment(character)
        navHostFragment.findNavController().navigate(action)
    }

    fun actionComicsListFragmentToComicDescriptionFragment(comic: Comic)
    {
        val action = ComicsListFragmentDirections.actionComicsListFragmentToComicDescriptionFragment(comic)
        navHostFragment.findNavController().navigate(action)
    }
}