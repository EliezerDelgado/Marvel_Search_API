package com.eliezer.marvel_search_api.core.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.eliezer.marvel_search_api.core.domain.DiffUtilCallback
import com.eliezer.marvel_search_api.models.SearchEncounter

abstract class BaseAdapter<T, S : BaseItemViewHolder<T>>(protected var items: ArrayList<T>) : RecyclerView.Adapter<S>() {

    fun isListEmpty() : Boolean = items.isEmpty()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): S {
        val inflater = LayoutInflater.from(parent.context)
        return setViewHolder(inflater)
    }
    fun getItemsContain(string : String) : ArrayList<SearchEncounter>
    {
        var numPosition = arrayListOf<SearchEncounter>()
        for (i in 0..<items.size)
        {
            val text = items[i].toString()
            if(text.contains(string))
            {
                numPosition = fillItemsContainNumPositions(string,items[i],i)
            }
        }
        return numPosition
    }

    private fun fillItemsContainNumPositions(string: String, any: Any?, numText : Int) : ArrayList<SearchEncounter>
    {
        val numPosition = arrayListOf<SearchEncounter>()
        val text = any.toString()
        val num = (text.length - text.replace(string,"").length)/string.length
        for (i in 0..<num)
        {
            numPosition.add(SearchEncounter(text.hashCode(),numText,text.length,i,null))
        }
        return numPosition
    }

    abstract fun setViewHolder(inflater: LayoutInflater) : S
    override fun getItemCount(): Int {
       return items.size
    }
    protected fun setListItems(items: List<T>)
    {
        val diffCallback = DiffUtilCallback(this.items,items)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.items.addAll(items)
        diffResult.dispatchUpdatesTo(this)
    }
    fun update()
    {
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: S, position: Int) {
        items[position].also {
            holder.bind(it)
            addMoreBindViewHolderFunction(holder, it )
        }
    }

    open fun addMoreBindViewHolderFunction(holder: S,item : T) {}
}