package com.eliezer.marvel_characters.ui.fragments.character_list.adapter

import android.view.LayoutInflater
import com.eliezer.marvel_characters.core.base.BaseAdapter
import com.eliezer.marvel_characters.databinding.ItemCharacterBinding
import com.eliezer.marvel_characters.models.dataclass.Character

class CharactersListAdapter(items : List<Character>) : BaseAdapter<Character, ItemCharacterViewHolder>(
    items = items) {
    fun setCharacters(characters : List<Character>) =
        setListItems(characters)

    override fun setViewHolder(inflater: LayoutInflater): ItemCharacterViewHolder {
        val binding = ItemCharacterBinding.inflate(inflater)
        return ItemCharacterViewHolder(binding)
    }
}