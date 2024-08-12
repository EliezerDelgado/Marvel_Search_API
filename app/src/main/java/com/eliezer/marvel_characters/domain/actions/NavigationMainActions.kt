package com.eliezer.marvel_characters.domain.actions

import androidx.fragment.app.FragmentContainerView
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.eliezer.marvel_characters.R
import com.eliezer.marvel_characters.models.dataclass.Character
import com.eliezer.marvel_characters.ui.fragments.character_list.CharacterListFragmentDirections

class NavigationMainActions (val navHostFragment: FragmentContainerView){
    fun doActionMarvelSearchsFragmentToCharacterListFragment() =

        navHostFragment.findNavController().navigate(R.id.action_marvelSearchFragment_to_characterListFragment)

    fun doActionMarvelSearchsFragmentToComicListFragment() =
        navHostFragment.findNavController().navigate(R.id.action_marvelSearchFragment_to_comicListFragment)

    fun navigateUp()
    {
        NavHostFragment.findNavController(navHostFragment.getFragment()).navigateUp()
    }
    fun actionCharacterListFragmentToCharacterProfileFragment(character: Character)
    {
        val action = CharacterListFragmentDirections.actionCharacterListFragmentToCharacterProfileFragment(character)
        navHostFragment.findNavController().navigate(action)
    }
}