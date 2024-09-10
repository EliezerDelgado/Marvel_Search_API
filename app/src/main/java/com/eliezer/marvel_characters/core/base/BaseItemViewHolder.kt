package com.eliezer.marvel_characters.core.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.eliezer.marvel_characters.BR

abstract class BaseItemViewHolder<T>(protected open val binding: ViewDataBinding) : RecyclerView.ViewHolder(
    binding.root
) {
    fun bind(item: T) {
        binding.apply {
            setVariable(BR.item, item)
            executePendingBindings()
        }
        onBindMethodCalled(item)
        itemView.setOnClickListener{
            onItemCLickListener()
        }
    }

    open fun onItemCLickListener(){
    }

    open fun onBindMethodCalled(item: T) {}
}