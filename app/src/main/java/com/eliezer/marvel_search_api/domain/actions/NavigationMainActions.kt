package com.eliezer.marvel_search_api.domain.actions

import androidx.fragment.app.FragmentContainerView
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.eliezer.marvel_search_api.R
import com.eliezer.marvel_search_api.data.const.SEARCH_ID
import com.eliezer.marvel_search_api.models.dataclass.Character
import com.eliezer.marvel_search_api.models.dataclass.Comic
import com.eliezer.marvel_search_api.ui.fragments.character_list.CharactersListFragmentDirections
import com.eliezer.marvel_search_api.ui.fragments.comic_list.ComicsListFragmentDirections
import com.eliezer.marvel_search_api.ui.fragments.favorites.FavoritesFragmentDirections
import com.eliezer.marvel_search_api.ui.fragments.marvel_search.MarvelSearchFragmentDirections

class NavigationMainActions (private val navHostFragment: FragmentContainerView){
    fun doActionMarvelSearchFragmentToCharacterListFragment(search : String) {
        val action = MarvelSearchFragmentDirections.actionMarvelSearchFragmentToCharacterListFragment(
            argMode = SEARCH_ID,
            argSearchCharacter = search
        )
        navHostFragment.findNavController().navigate(action)
    }

    fun doActionMarvelSearchFragmentToComicListFragment(search : String) {
        val action = MarvelSearchFragmentDirections.actionMarvelSearchFragmentToComicListFragment(
            argMode = SEARCH_ID,
            argSearchComic = search
        )
        navHostFragment.findNavController().navigate(action)
    }

    fun navigateUp()
    {
        NavHostFragment.findNavController(navHostFragment.getFragment()).navigateUp()
    }
    fun doActionCharacterListFragmentToCharacterProfileFragment(character: Character)
    {
        val action = CharactersListFragmentDirections.actionCharacterListFragmentToCharacterProfileFragment(character)
        navHostFragment.findNavController().navigate(action)
    }

    fun doActionComicsListFragmentToComicDescriptionFragment(comic: Comic)
    {
        val action = ComicsListFragmentDirections.actionComicsListFragmentToComicDescriptionFragment(comic)
        navHostFragment.findNavController().navigate(action)
    }
    fun doActionMarvelSearchFragmentToFavoritesFragment()
    {
        navHostFragment.findNavController().navigate(R.id.action_marvelSearchFragment_to_favoritesFragment)
    }
    fun doActionFavoritesFragmentToCharacterProfileFragment(character: Character)
    {

        val action = FavoritesFragmentDirections.actionFavoritesFragmentToCharacterProfileFragment(character)
        navHostFragment.findNavController().navigate(action)
    }

    fun doActionFavoritesFragmentToComicDescriptionFragment(comic: Comic)
    {

        val action = FavoritesFragmentDirections.actionFavoritesFragmentToComicDescriptionFragment(comic)
        navHostFragment.findNavController().navigate(action)
    }
}