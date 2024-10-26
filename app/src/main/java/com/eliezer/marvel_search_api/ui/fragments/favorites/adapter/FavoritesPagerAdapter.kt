package com.eliezer.marvel_search_api.ui.fragments.favorites.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.eliezer.marvel_search_api.core.base.BasePagerAdapter
import com.eliezer.marvel_search_api.data.const.FAVORITE_ID
import com.eliezer.marvel_search_api.data.const.LIST_MODE
import com.eliezer.marvel_search_api.ui.fragments.character_list.CharactersListFragment
import com.eliezer.marvel_search_api.ui.fragments.comic_list.ComicsListFragment

class FavoritesPagerAdapter(fragment: Fragment) : BasePagerAdapter(fragment) {
    fun createComicsListFragment()
    {
        val args = modeFavorite()
        addFragment(ComicsListFragment::class.java,args)
    }
    fun createCharactersListFragment()
    {
        val args = modeFavorite()
        addFragment(CharactersListFragment::class.java,args)
    }
    private fun modeFavorite(): Bundle{
        val args = Bundle()
        args.putString(LIST_MODE, FAVORITE_ID)
        return args
    }
}