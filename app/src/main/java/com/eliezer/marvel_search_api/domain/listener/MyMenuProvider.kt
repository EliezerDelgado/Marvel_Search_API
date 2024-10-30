package com.eliezer.marvel_search_api.domain.listener

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.annotation.MenuRes
import androidx.core.view.MenuProvider

class MyMenuProvider(
    @MenuRes private val menuId : Int,
    private val onMenuItemSelected : (menuItem : MenuItem) -> Boolean
) : MenuProvider {
    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(menuId,menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return  onMenuItemSelected.invoke(menuItem)
    }
}