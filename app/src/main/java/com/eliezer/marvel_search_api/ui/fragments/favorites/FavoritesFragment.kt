package com.eliezer.marvel_search_api.ui.fragments.favorites
import android.os.Bundle
import android.view.View
import com.eliezer.marvel_search_api.core.base.BaseFragment
import com.eliezer.marvel_search_api.data.mappers.mainActivity
import com.eliezer.marvel_search_api.databinding.FragmentFavoritesBinding
import com.eliezer.marvel_search_api.domain.listener.MyMenuProvider
import com.eliezer.marvel_search_api.domain.listener.MyOnTabSelectedListened
import com.eliezer.marvel_search_api.domain.local_property.ThemeColors
import com.eliezer.marvel_search_api.ui.fragments.favorites.adapter.FavoritesPagerAdapter
import com.eliezer.marvel_search_api.ui.fragments.favorites.functionImp.FavoritesFunctionImplement
import com.eliezer.marvel_search_api.ui.fragments.favorites.interfaces.FavoriteToolbarButtonsClickAction


class FavoritesFragment :  BaseFragment<FragmentFavoritesBinding>(
    FragmentFavoritesBinding::inflate
)  {
    private var pagerAdapter : FavoritesPagerAdapter? = null
    private var funImpl : FavoritesFunctionImplement? = null

    private val myToolbarMenuProvider = MyMenuProvider(com.eliezer.marvel_search_api.R.menu.favorite_toolbar_menu){ item->
        when(item.itemId)
        {
            com.eliezer.marvel_search_api.R.id.favorite_toolbar_menu_reload -> reloadFragment()
        }
        true
    }

    private fun reloadFragment() {
        val position = binding.favoritesViewpager2.currentItem
        pagerAdapter!!.getFragmentInstance<FavoriteToolbarButtonsClickAction>(position)!!.doReload()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pagerAdapter = FavoritesPagerAdapter(this)
        funImpl = FavoritesFunctionImplement(binding,pagerAdapter!!)
        mainAddMenuProvider()
        mainActivity(requireActivity()).setToolbarView(true)
        funImpl?.setFragments()
        funImpl?.setContentView()
        funImpl?.createTabLayout(
            MyOnTabSelectedListened(
                selectedColor = ThemeColors.getColorPrimary(requireContext()),
                unselectedColor =  ThemeColors.getColorSecondary(requireContext()),
                reselectedColor =  ThemeColors.getColorTertiary(requireContext()))
            )
    }

    private fun mainAddMenuProvider()
    {
        mainActivity(requireActivity()).getToolBar()?.addMenuProvider(myToolbarMenuProvider)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        mainActivity(requireActivity()).getToolBar()?.removeMenuProvider(myToolbarMenuProvider)
        mainActivity(requireActivity()).setToolbarView(false)
        funImpl = null
        pagerAdapter=null
    }
}