package com.eliezer.marvel_search_api.ui.fragments.favorites.functionImp

import android.content.Context
import android.view.ViewGroup
import com.eliezer.marvel_search_api.R
import com.eliezer.marvel_search_api.databinding.FragmentFavoritesBinding
import com.eliezer.marvel_search_api.ui.fragments.character_list.CharactersListFragment
import com.eliezer.marvel_search_api.ui.fragments.comic_list.ComicsListFragment
import com.eliezer.marvel_search_api.domain.listener.MyOnTabSelectedListened
import com.eliezer.marvel_search_api.ui.fragments.favorites.adapter.FavoritesPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class FavoritesFunctionImplement(private val binding: FragmentFavoritesBinding,private val pagerAdapter: FavoritesPagerAdapter,private val context: Context) {
    fun setFragments() {
        pagerAdapter.addFragment(CharactersListFragment::class.java)
        pagerAdapter.addFragment(ComicsListFragment::class.java)
    }

    fun setContentView() {
        binding.favoritesViewpager2.setAdapter(pagerAdapter)
    }

    fun createTabLayout() {
        binding.favoritesTabLayout.setTabIndicatorFullWidth(true)
        tabLayoutMediator()
        setTabLayout()
    }

    private fun setTabLayout() {
        binding.favoritesTabLayout.addOnTabSelectedListener(
            MyOnTabSelectedListened(
                selectedColor = context.resources.getColor(R.color.attr_color_tertiary,context.theme),
                unselectedColor = context.resources.getColor(R.color.attr_color_secondary,context.theme),
                reselectedColor = context.resources.getColor(R.color.attr_color_tertiary,context.theme)
            )
        )
        binding.favoritesTabLayout.selectTab(
            binding.favoritesTabLayout.getTabAt(
                binding.favoritesViewpager2.currentItem
            )
        )
        binding.favoritesTabLayout.setSelectedTabIndicator(binding.favoritesTabLayout.tabSelectedIndicator)
    }

    private fun tabLayoutMediator() {
        TabLayoutMediator(
            binding.favoritesTabLayout, binding.favoritesViewpager2
        ) { tab: TabLayout.Tab, position: Int ->
            val arrayView = ArrayList<ViewGroup>()
            when (position) {
                0 -> {
                    tab.setIcon(R.drawable.ic_spiderman)
                    tab.select()
                }

                1 -> {
                    tab.setIcon(R.drawable.ic_comic)
                }
            }
        }.attach()
    }

}