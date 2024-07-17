package com.eliezer.marvel_characters.io.navigation

import androidx.fragment.app.FragmentContainerView
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.eliezer.marvel_characters.R

class NavigationMainActions (val navHostFragment: FragmentContainerView){
    fun actionMarvelSearchsFragmentToCharacterListFragment() =

        navHostFragment.findNavController().navigate(R.id.action_marvelSearchFragment_to_characterListFragment)

    fun actionMarvelSearchsFragmentToComicListFragment() =
        navHostFragment.findNavController().navigate(R.id.action_marvelSearchFragment_to_comicListFragment)

    fun navigateUp()
    {
        NavHostFragment.findNavController(navHostFragment.getFragment()).navigateUp()
    }
}