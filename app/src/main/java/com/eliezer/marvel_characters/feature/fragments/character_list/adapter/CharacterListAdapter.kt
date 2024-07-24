package com.eliezer.marvel_characters.feature.fragments.character_list.adapter

import com.eliezer.marvel_characters.core.base.GenericAdapter
import com.eliezer.marvel_characters.databinding.ItemCharacterBinding
import com.eliezer.marvel_characters.models.dataclass.Character

class CharacterListAdapter(items : List<Character>) : GenericAdapter<Character, ItemCharacterViewHolder>(
    items = items,ItemCharacterBinding::inflate) {
    fun setCharacters(characters : List<Character>)
    {
        setListItems(characters)
    }
}