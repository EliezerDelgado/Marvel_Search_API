package com.eliezer.marvel_characters.ui.fragments.character_profile.adapter

import android.view.LayoutInflater
import com.eliezer.marvel_characters.core.base.BaseAdapter
import com.eliezer.marvel_characters.databinding.ItemComicSummaryBinding
import com.eliezer.marvel_characters.models.dataclass.Comic

class CharacterProfileComicsListAdapter(items : List<Comic>) : BaseAdapter<Comic, ItemComicSummaryViewHolder>(
    items = items) {
    fun setComics(listComicSummary : List<Comic>) =
        setListItems(listComicSummary)

    override fun setViewHolder(inflater: LayoutInflater): ItemComicSummaryViewHolder {
        val binding = ItemComicSummaryBinding.inflate(inflater)
        return ItemComicSummaryViewHolder(binding)
    }
}