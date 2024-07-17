package com.eliezer.marvel_characters.core.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.eliezer.marvel_characters.BR

open class ItemGenericViewHolder<T>(protected val binding: ViewDataBinding) : RecyclerView.ViewHolder(
    binding.root
) {
    fun bind(item: T) {
        binding.apply {
            setVariable(BR.item, item)
            executePendingBindings()
        }
        onBindMethodCalled(item)
    }
    protected open fun onBindMethodCalled(item: T)
    {

    }
}