package com.eliezer.marvel_search_api.domain.local_property

import android.content.Context
import android.util.TypedValue
import com.eliezer.marvel_search_api.R

object ThemeColors {
    private val typedValue = TypedValue()
    fun getColorPrimary(context:Context) : Int
    {
        context.theme.resolveAttribute(android.R.attr.colorPrimary, typedValue, true)
        return typedValue.data
    }
    fun getColorSecondary(context:Context) : Int
    {
        context.theme.resolveAttribute(android.R.attr.colorSecondary, typedValue, true)
        return typedValue.data
    }
    fun getColorTertiary(context:Context) : Int
    {
        context.theme.resolveAttribute(R.attr.colorTertiary, typedValue, true)
        return typedValue.data
    }
}