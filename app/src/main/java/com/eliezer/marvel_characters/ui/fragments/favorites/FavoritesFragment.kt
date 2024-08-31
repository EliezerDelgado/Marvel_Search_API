package com.eliezer.marvel_characters.ui.fragments.favorites
import android.os.Bundle
import android.view.View
import com.eliezer.marvel_characters.core.base.BaseFragment
import com.eliezer.marvel_characters.databinding.FragmentFavoritesBinding
import com.eliezer.marvel_characters.ui.fragments.favorites.adapter.FavoritesPagerAdapter

class FavoritesFragment :  BaseFragment<FragmentFavoritesBinding>(
    FragmentFavoritesBinding::inflate
)  {
    private var pagerAdapter : FavoritesPagerAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFragments()
        setContentView()
        createTabLayout()
    }
}