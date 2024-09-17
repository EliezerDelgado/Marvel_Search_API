package com.eliezer.marvel_search_api.ui.fragments.favorites.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.eliezer.marvel_search_api.data.const.FAVORITE_ID


class FavoritesPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private val listFragmentClass = ArrayList<Class<out Fragment>>()
    fun addFragment(fragmentClass: Class<out Fragment>) {
        listFragmentClass.add(fragmentClass)
    }
    override fun createFragment(position: Int): Fragment {
        try {
            val fragmentInstance = listFragmentClass[position].getDeclaredConstructor().newInstance() as Fragment
            val args = Bundle()
            args.putString("argMode", FAVORITE_ID);
            fragmentInstance.arguments = args
            return fragmentInstance
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