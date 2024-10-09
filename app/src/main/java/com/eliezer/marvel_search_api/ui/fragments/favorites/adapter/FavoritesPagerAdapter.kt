package com.eliezer.marvel_search_api.ui.fragments.favorites.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.eliezer.marvel_search_api.core.base.BasePagerAdapter
import com.eliezer.marvel_search_api.data.const.FAVORITE_ID
import com.eliezer.marvel_search_api.ui.fragments.character_list.CharactersListFragment
import com.eliezer.marvel_search_api.ui.fragments.comic_list.ComicsListFragment

class FavoritesPagerAdapter(fragment: Fragment) : BasePagerAdapter(fragment) {
    fun createComicsListFragment()
    {
        val args = Bundle()
        args.putString("argMode", FAVORITE_ID)
        addFragment(ComicsListFragment::class.java,args)
    }
    fun createCharactersListFragment()
    {
        val args = Bundle()
        args.putString("argMode", FAVORITE_ID)
        addFragment(CharactersListFragment::class.java,args)
    }
}