package com.eliezer.marvel_characters.domain.actions

import androidx.fragment.app.FragmentContainerView
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.eliezer.marvel_characters.R

class NavigationMainActions (val navHostFragment: FragmentContainerView){
    fun doActionMarvelSearchsFragmentToCharacterListFragment() =

        navHostFragment.findNavController().navigate(R.id.action_marvelSearchFragment_to_characterListFragment)

    fun doActionMarvelSearchsFragmentToComicListFragment() =
        navHostFragment.findNavController().navigate(R.id.action_marvelSearchFragment_to_comicListFragment)

    fun navigateUp()
    {
        NavHostFragment.findNavController(navHostFragment.getFragment()).navigateUp()
    }
}