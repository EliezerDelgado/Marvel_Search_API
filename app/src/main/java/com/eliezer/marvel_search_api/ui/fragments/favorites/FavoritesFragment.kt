package com.eliezer.marvel_search_api.ui.fragments.favorites
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.eliezer.marvel_search_api.core.base.BaseFragment
import com.eliezer.marvel_search_api.databinding.FragmentFavoritesBinding
import com.eliezer.marvel_search_api.ui.fragments.favorites.adapter.FavoritesPagerAdapter
import com.eliezer.marvel_search_api.ui.fragments.favorites.functionImp.FavoritesFunctionImplement

class FavoritesFragment :  BaseFragment<FragmentFavoritesBinding>(
    FragmentFavoritesBinding::inflate
)  {
    private var pagerAdapter : FavoritesPagerAdapter? = null
    private var funImpl : FavoritesFunctionImplement? = null
    private val favoriteViewModel: FavoriteViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pagerAdapter = FavoritesPagerAdapter(this)
        funImpl = FavoritesFunctionImplement(binding,pagerAdapter!!,favoriteViewModel,requireContext())
        funImpl?.setFragments()
        funImpl?.setContentView()
        funImpl?.createTabLayout()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        funImpl = null
        pagerAdapter=null
    }
}