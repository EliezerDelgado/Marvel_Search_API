package com.eliezer.marvel_characters.ui.fragments.character_profile.adapter

import android.view.LayoutInflater
import com.eliezer.marvel_characters.core.base.BaseAdapter
import com.eliezer.marvel_characters.databinding.ItemComicSummaryBinding
import com.eliezer.marvel_characters.models.responses.character.ComicSummary

class CharacterProfileComicsListAdapter(items : List<ComicSummary>) : BaseAdapter<ComicSummary, ItemComicSummaryViewHolder>(
    items = items) {
    fun setListComicSummary(listComicSummary : List<ComicSummary>) =
        setListItems(listComicSummary)

    override fun setViewHolder(inflater: LayoutInflater): ItemComicSummaryViewHolder {
        val binding = ItemComicSummaryBinding.inflate(inflater)
        return ItemComicSummaryViewHolder(binding)
    }
}