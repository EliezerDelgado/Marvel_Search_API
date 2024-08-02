package com.eliezer.marvel_characters.core.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.eliezer.marvel_characters.core.domain.DiffUtilCallback

abstract class BaseAdapter<T, S : BaseItemViewHolder<T>>(protected var items: List<T>) : RecyclerView.Adapter<S>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): S {
        val inflater = LayoutInflater.from(parent.context)
        return setViewHolder(inflater)
    }
    abstract fun setViewHolder(inflater: LayoutInflater) : S
    override fun getItemCount(): Int {
       return items.size
    }
    protected fun setListItems(items: List<T>)
    {
        val diffCallback = DiffUtilCallback(this.items,items)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.items = items
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onBindViewHolder(holder: S, position: Int) {
        items[position].also {
            holder.bind(it)
        }
        addMoreBindViewHolderFunction(holder)
    }

    open fun addMoreBindViewHolderFunction(holder: S) {}
}