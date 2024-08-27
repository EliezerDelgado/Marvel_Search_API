package com.eliezer.marvel_characters.ui.fragments.character_list.adapter

import android.view.LayoutInflater
import com.eliezer.marvel_characters.core.base.BaseAdapter
import com.eliezer.marvel_characters.databinding.ItemCharacterBinding
import com.eliezer.marvel_characters.models.dataclass.Character

class CharactersListAdapter(items : ArrayList<Character>,private val listener : CharacterHolderListener?) : BaseAdapter<Character, ItemCharacterViewHolder>(
    items = items) {
    interface CharacterHolderListener
    {
        fun onCharacterItemClickListener(character: Character)
    }
    fun setCharacters(characters : List<Character>) =
        setListItems(characters)

    override fun setViewHolder(inflater: LayoutInflater): ItemCharacterViewHolder {
        val binding = ItemCharacterBinding.inflate(inflater)
        return ItemCharacterViewHolder(binding)
    }

    override fun addMoreBindViewHolderFunction(holder: ItemCharacterViewHolder,item: Character) {
        super.addMoreBindViewHolderFunction(holder,item)
        onClickListener(holder,item)
    }

    private fun onClickListener(holder: ItemCharacterViewHolder, character: Character)
    {
        holder.itemView.setOnClickListener{listener?.onCharacterItemClickListener(character)}
    }
}