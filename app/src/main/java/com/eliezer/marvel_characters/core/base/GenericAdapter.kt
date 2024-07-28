package com.eliezer.marvel_characters.core.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.eliezer.marvel_characters.core.domain.DiffUtilCallback

open class GenericAdapter<T,S: ItemGenericViewHolder<T>>(protected var items: List<T>, protected val binding: (layoutInflater: LayoutInflater) -> ViewDataBinding) : RecyclerView.Adapter<S>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): S {
        val holder = binding.invoke(LayoutInflater.from(parent.context)).let {
            ItemGenericViewHolder<T>(it) as S
        }
        return holder
    }
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
    }
}