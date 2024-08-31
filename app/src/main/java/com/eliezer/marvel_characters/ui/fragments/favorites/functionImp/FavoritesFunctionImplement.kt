package com.eliezer.marvel_characters.ui.fragments.favorites.functionImp

import android.annotation.SuppressLint
import android.content.Context
import com.eliezer.marvel_characters.R
import com.eliezer.marvel_characters.databinding.FragmentFavoritesBinding
import com.eliezer.marvel_characters.ui.fragments.character_list.CharactersListFragment
import com.eliezer.marvel_characters.ui.fragments.comic_list.ComicsListFragment
import com.eliezer.marvel_characters.domain.listener.MyOnTabSelectedListened
import com.eliezer.marvel_characters.ui.fragments.favorites.adapter.FavoritesPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class FavoritesFunctionImplement(private val binding: FragmentFavoritesBinding,private val pagerAdapter: FavoritesPagerAdapter,private val context: Context) {
    private fun setFragments() {
        pagerAdapter.addFragment(CharactersListFragment::class.java)
        pagerAdapter.addFragment(ComicsListFragment::class.java)
    }

    private fun setContentView() {
        binding.favoritesViewpager2.setAdapter(pagerAdapter)
    }

    @SuppressLint("NewApi")
    private fun createTabLayout() {
        binding.favoritesTabLayout.setTabIndicatorFullWidth(true)
        tabLayoutMediator()
        setTabLayout()
    }

    private fun setTabLayout() {
        binding.favoritesTabLayout.addOnTabSelectedListener(
            MyOnTabSelectedListened(
                selectedColor = context.resources.getColor(R.color.white,context.theme),
                unselectedColor = context.resources.getColor(R.color.white,context.theme),
                reselectedColor = context.resources.getColor(R.color.white,context.theme)
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
            when (position) {
                0 -> {
                    //tab.setIcon(R.drawable.ic_action_camera)
                    tab.view.setBackgroundColor(
                        context.resources.getColor(
                            R.color.white,
                            context.theme
                        )
                    )
                    tab.view.y -= 4f
                    tab.select()
                }

                1 -> {
                    //tab.setIcon(R.drawable.ic_action_car)
                    tab.view.setBackgroundColor(
                        context.resources.getColor(
                            R.color.white,
                            context.theme
                        )
                    )
                    tab.view.y -= 4f
                }
            }
        }.attach()
    }

}