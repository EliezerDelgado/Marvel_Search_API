package com.eliezer.marvel_search_api.domain.listener

import androidx.annotation.ColorInt
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener

class MyOnTabSelectedListened (@ColorInt private val selectedColor :  Int, @ColorInt private val unselectedColor :  Int, @ColorInt private val reselectedColor :  Int): OnTabSelectedListener {
    override fun onTabSelected(tab: TabLayout.Tab) {
        val icon = tab.icon
        if (icon != null) {
            icon.colorFilter = BlendModeColorFilterCompat
                .createBlendModeColorFilterCompat(selectedColor,
                    BlendModeCompat.SRC_ATOP
                )
            tab.setIcon(icon)
        }
    }

    override fun onTabUnselected(tab: TabLayout.Tab) {
        val icon = tab.icon
        if (icon != null) {
            icon.colorFilter = BlendModeColorFilterCompat
                .createBlendModeColorFilterCompat(unselectedColor,
                    BlendModeCompat.SRC_ATOP
                )
            tab.setIcon(icon)
        }
    }

    override fun onTabReselected(tab: TabLayout.Tab) {
        val icon = tab.icon
        if (icon != null) {
            icon.colorFilter = BlendModeColorFilterCompat
                .createBlendModeColorFilterCompat(reselectedColor,
                    BlendModeCompat.SRC_ATOP
                )
            tab.setIcon(icon)
        }
    }
}