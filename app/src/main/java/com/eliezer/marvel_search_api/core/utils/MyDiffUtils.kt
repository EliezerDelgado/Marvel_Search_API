package com.eliezer.marvel_search_api.core.utils

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.eliezer.marvel_search_api.core.domain.DiffUtilCallback


class MyDiffUtils<T>() {
     private var newItems = ArrayList<T>()
     private var oldItems= ArrayList<T>()
    fun start(oldItems:  ArrayList<T>)
    {
        this.oldItems = oldItems
    }
    fun finish(newItems : ArrayList<T>,adapter : RecyclerView.Adapter<*>)
    {
        this.newItems = newItems
        val diffCallback =  DiffUtilCallback(oldList = oldItems, newList =  newItems)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        diffResult.dispatchUpdatesTo(adapter)
    }
}
