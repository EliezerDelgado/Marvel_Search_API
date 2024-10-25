package com.eliezer.marvel_search_api.ui.fragments.favorites.functionImp

import com.eliezer.marvel_search_api.R
import com.eliezer.marvel_search_api.databinding.FragmentFavoritesBinding
import com.eliezer.marvel_search_api.domain.listener.MyOnTabSelectedListened
import com.eliezer.marvel_search_api.ui.fragments.favorites.adapter.FavoritesPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class FavoritesFunctionImplement(binding: FragmentFavoritesBinding,pagerAdapter: FavoritesPagerAdapter) {
    private val functionPagerAdapter= FunctionPagerAdapter(binding,pagerAdapter)

    fun setFragments() = functionPagerAdapter.setFragments()
    fun setContentView() = functionPagerAdapter.setContentView()
    fun createTabLayout(myOnTabSelectedListened :MyOnTabSelectedListened) = functionPagerAdapter.createTabLayout(myOnTabSelectedListened)

}

private  class FunctionPagerAdapter(
    private val binding: FragmentFavoritesBinding,
    private val pagerAdapter: FavoritesPagerAdapter
)
{

    fun setFragments() {
        pagerAdapter.createCharactersListFragment()
        pagerAdapter.createComicsListFragment()
    }

    fun setContentView() {
        binding.favoritesViewpager2.setAdapter(pagerAdapter)
    }

    fun createTabLayout(myOnTabSelectedListened :MyOnTabSelectedListened) {
        tabLayoutMediator()
        setTabLayout(myOnTabSelectedListened)
        binding.favoritesTabLayout.requestLayout()
    }

    private fun setTabLayout(myOnTabSelectedListened :MyOnTabSelectedListened) {
        binding.favoritesTabLayout.addOnTabSelectedListener(
            myOnTabSelectedListened
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
            when (position) {
                0 -> {
                    tab.setIcon(R.drawable.img_spiderman)
                    tab.select()
                }

                1 -> {
                    tab.setIcon(R.drawable.img_comic)
                }
            }
        }.attach()
    }
}