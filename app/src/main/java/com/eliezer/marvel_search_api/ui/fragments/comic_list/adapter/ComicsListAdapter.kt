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
        fun onImageButtonFavoriteListener(comic : Comic)
    }
    fun addComics(comics : List<Comic>) {
        addListItems(comics)
    }
    fun setComics(comics : List<Comic>) {
        setListItems(comics)
    }
    fun clearComics() {
        clearItems()
    }

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
        listener?.also {
            holder.itemComicImageButtonFavoriteListener{
                comic.favorite = !comic.favorite
                it.onImageButtonFavoriteListener(comic)
            }
        }
    }

    fun setIdsFavoriteComics(ids:ArrayList<Int>) {
        for (comic in items)
        {
            if(ids.contains(comic.id)) {
                comic.favorite = true
                update(items.indexOf(comic),items.indexOf(comic)+1)
            }
        }
    }
    fun setFavoriteComic(comic: Comic) {
        val index = items.indexOf(comic)
        items[index].favorite = true
        update(index,index+1)
    }
    fun setNoFavoriteComic(comic: Comic) {
        val index = items.indexOf(comic)
        items[index].favorite = false
        update(index,index+1)
    }

}