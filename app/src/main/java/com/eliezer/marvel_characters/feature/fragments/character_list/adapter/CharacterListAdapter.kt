package com.eliezer.marvel_characters.feature.fragments.character_list.adapter

import android.content.Context
import android.view.LayoutInflater
import androidx.databinding.ViewDataBinding
import com.eliezer.marvel_characters.core.base.GenericAdapter
import com.eliezer.marvel_characters.databinding.ItemCharacterBinding
import com.eliezer.marvel_characters.models.dataclass.Character

class CharacterListAdapter(items : List<Character>) : GenericAdapter<Character, ItemCharacterViewHolder>(
    items = items) {
    fun setCharacters(characters : List<Character>) =
        setListItems(characters)

    override fun setViewHolder(inflater: LayoutInflater): ItemCharacterViewHolder {
        val binding = ItemCharacterBinding.inflate(inflater)
        return ItemCharacterViewHolder(binding)
    }
}