package com.eliezer.marvel_characters.ui.fragments.character_profile.adapter

import android.view.LayoutInflater
import com.eliezer.marvel_characters.core.base.BaseAdapter
import com.eliezer.marvel_characters.databinding.ItemComicHorizontalBinding
import com.eliezer.marvel_characters.models.dataclass.Comic

class CharacterProfileComicsListAdapter(items : ArrayList<Comic>) : BaseAdapter<Comic, ItemComicHorizontalViewHolder>(
    items = items) {

    fun setComics(listComic : List<Comic>) =
        setListItems(listComic)

    override fun setViewHolder(inflater: LayoutInflater): ItemComicHorizontalViewHolder {
        val binding = ItemComicHorizontalBinding.inflate(inflater)
        return ItemComicHorizontalViewHolder(binding)
    }
}