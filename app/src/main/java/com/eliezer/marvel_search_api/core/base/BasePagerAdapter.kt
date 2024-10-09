package com.eliezer.marvel_search_api.core.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

open class BasePagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    private val listFragmentClass = ArrayList<Class<out Fragment>>()
    private val listFragmentClassArgs = HashMap<Class<out Fragment>,Bundle?>()
    val listFragmentInstance = ArrayList<Fragment>()
    fun addFragment(fragmentClass: Class<out Fragment>,args : Bundle?) {
        listFragmentClass.add(fragmentClass)
        listFragmentClassArgs[fragmentClass] = args
    }
    override fun createFragment(position: Int): Fragment {
        try {
            val fragmentInstance = listFragmentClass[position].getDeclaredConstructor()?.newInstance() as Fragment
            fragmentInstance.arguments =  listFragmentClassArgs[listFragmentClass[position]]
            listFragmentInstance.add(fragmentInstance)
            return fragmentInstance
        } catch (e: IllegalAccessException) {
            throw RuntimeException(e)
        } catch (e: InstantiationException) {
            throw RuntimeException(e)
        }
    }
    inline fun<reified T> getFragmentInstance(position : Int) : T?{
        return if(listFragmentInstance[position] is T)
                listFragmentInstance[position] as T
        else
            null
    }
    override fun getItemCount(): Int {
        return listFragmentClass.size
    }
}