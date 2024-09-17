package com.eliezer.marvel_search_api.ui.fragments.comic_list.adapter

import android.view.LayoutInflater
import com.eliezer.marvel_search_api.core.base.BaseAdapter
import com.eliezer.marvel_search_api.databinding.ItemComicBinding
import com.eliezer.marvel_search_api.models.dataclass.Comic

class ComicsListAdapter (items : ArrayList<Comic>,private val listener : ComicHolderListener?) : BaseAdapter<Comic, ItemComicViewHolder>(
    items = items) {
    interface ComicHolderListener
    {
        fun onComicItemClickListener(comic: Comic)
    }
    fun setComics(comics : List<Comic>) =
        setListItems(comics)

    override fun setViewHolder(inflater: LayoutInflater): ItemComicViewHolder {
        val binding = ItemComicBinding.inflate(inflater)
        return ItemComicViewHolder(binding)
    }

    override fun addMoreBindViewHolderFunction(holder: ItemComicViewHolder, item: Comic) {
        super.addMoreBindViewHolderFunction(holder,item)
        onClickListener(holder,item)
    }

    private fun onClickListener(holder: ItemComicViewHolder, comic: Comic)
    {
        holder.itemView.setOnClickListener{listener?.onComicItemClickListener(
            comic)}
    }
}