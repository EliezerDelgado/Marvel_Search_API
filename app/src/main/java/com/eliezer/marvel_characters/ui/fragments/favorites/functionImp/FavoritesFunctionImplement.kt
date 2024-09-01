package com.eliezer.marvel_characters.ui.fragments.favorites.functionImp

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.core.view.size
import com.eliezer.marvel_characters.R
import com.eliezer.marvel_characters.databinding.FragmentFavoritesBinding
import com.eliezer.marvel_characters.ui.fragments.character_list.CharactersListFragment
import com.eliezer.marvel_characters.ui.fragments.comic_list.ComicsListFragment
import com.eliezer.marvel_characters.domain.listener.MyOnTabSelectedListened
import com.eliezer.marvel_characters.ui.fragments.favorites.adapter.FavoritesPagerAdapter
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
                selectedColor = context.resources.getColor(R.color.white,context.theme),
                unselectedColor = context.resources.getColor(R.color.black,context.theme),
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
 //       createViewsBackground()
        val colorArray =ArrayList<Int>()
        colorArray.add(0,context.resources.getColor(R.color.dark_crimson,context.theme))

        colorArray.add(1,context.resources.getColor(R.color.dark_green,context.theme))
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
        createViewsBackground(colorArray)
    }

    private fun createViewsBackground(@ColorInt colorArray: ArrayList<Int>) {
        colorArray.forEach {
            val view = View(context)
            val  params = LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.MATCH_PARENT,
                0.5f
            )
            view.layoutParams = params
            view.setBackgroundColor(it)
            binding.favoritesLinearLayoutTabBackground.addView(view)
        }
    }

}