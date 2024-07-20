package com.eliezer.marvel_characters.feature.fragments.character_list.adapter

import com.eliezer.marvel_characters.core.base.ItemGenericViewHolder
import com.eliezer.marvel_characters.databinding.ItemCharacterBinding
import com.eliezer.marvel_characters.models.responses.Character

class ItemCharacterViewHolder(binding: ItemCharacterBinding) : ItemGenericViewHolder<Character>(binding = binding ) {

    override fun onBindMethodCalled(item: Character)
    {
        binding as ItemCharacterBinding
        binding.itemCharacterTextViewName
    }
}