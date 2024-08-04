package com.eliezer.marvel_characters.ui.fragments.comic_list.adapter

import android.view.LayoutInflater
import com.eliezer.marvel_characters.core.base.BaseAdapter
import com.eliezer.marvel_characters.databinding.ItemComicBinding
import com.eliezer.marvel_characters.models.dataclass.Comic

class ComicsListAdapter (items : List<Comic>) : BaseAdapter<Comic, ItemComicViewHolder>(
    items = items) {
    fun setComics(comics : List<Comic>) =
        setListItems(comics)

    override fun setViewHolder(inflater: LayoutInflater): ItemComicViewHolder {
        val binding = ItemComicBinding.inflate(inflater)
        return ItemComicViewHolder(binding)
    }
}