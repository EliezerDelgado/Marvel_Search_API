package com.eliezer.marvel_characters.ui.fragments.favorites.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class FavoritesPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private val listFragmentClass = ArrayList<Class<out Fragment>>()
    fun addFragment(fragmentClass: Class<out Fragment>) {
        listFragmentClass.add(fragmentClass)
    }
    override fun createFragment(position: Int): Fragment {
        try {
            return listFragmentClass[position].getDeclaredConstructor().newInstance() as Fragment
        } catch (e: IllegalAccessException) {
            throw RuntimeException(e)
        } catch (e: InstantiationException) {
            throw RuntimeException(e)
        }
    }

    override fun getItemCount(): Int {
        return listFragmentClass.size
    }
}