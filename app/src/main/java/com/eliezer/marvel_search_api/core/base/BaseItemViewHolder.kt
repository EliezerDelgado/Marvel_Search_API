package com.eliezer.marvel_search_api.core.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.eliezer.marvel_search_api.BR

abstract class BaseItemViewHolder<T>(protected open val binding: ViewDataBinding) : RecyclerView.ViewHolder(
    binding.root
) {
    private var _item : T? = null
    fun bind(item: T) {
        binding.apply {
            setVariable(BR.item, item)
            _item = item
            executePendingBindings()
        }
        onBindMethodCalled(item)
        itemView.setOnClickListener{
            onItemCLickListener()
        }
        itemView.setOnLongClickListener {
            onItemLongClickListener()
            true
        }
    }
    open fun onItemCLickListener(){
    }
    open fun onItemLongClickListener(){
    }

    open fun onBindMethodCalled(item: T) {}
}