package com.eliezer.marvel_characters.ui.fragments.comic_description.adapter

import android.view.LayoutInflater
import com.eliezer.marvel_characters.core.base.BaseAdapter
import com.eliezer.marvel_characters.databinding.ItemCharacterHorizontalBinding
import com.eliezer.marvel_characters.models.dataclass.Character

class ComicDescriptionCharacterListAdapter(items : ArrayList<Character>) : BaseAdapter<Character, ItemCharacterHorizontalViewHolder>(
    items = items) {
    fun setCharacters(listCharacters : List<Character>) =
        setListItems(listCharacters)

    override fun setViewHolder(inflater: LayoutInflater): ItemCharacterHorizontalViewHolder {
        val binding = ItemCharacterHorizontalBinding.inflate(inflater)
        return ItemCharacterHorizontalViewHolder(binding)
    }
}