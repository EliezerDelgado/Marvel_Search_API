package com.eliezer.marvel_search_api.ui.fragments.character_list.adapter

import android.view.LayoutInflater
import com.eliezer.marvel_search_api.core.base.BaseAdapter
import com.eliezer.marvel_search_api.databinding.ItemCharacterBinding
import com.eliezer.marvel_search_api.models.dataclass.Character

class CharactersListAdapter(items : ArrayList<Character>,private val listener : CharacterHolderListener?) : BaseAdapter<Character, ItemCharacterViewHolder>(
    items = items) {
    interface CharacterHolderListener
    {
        fun onCharacterItemClickListener(character: Character)
        fun onImageButtonFavoriteListener(character : Character)
    }
    fun addCharacters(characters : List<Character>) {
        addListItems(characters)
    }
    fun setCharacters(characters : List<Character>) =
        setListItems(characters)

    fun clearCharacters() {
        clearItems()
    }

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

        listener?.also {
            holder.itemCharacterImageButtonFavoriteListener{
                character.favorite = !character.favorite
                it.onImageButtonFavoriteListener(character)
            }
        }
    }
    fun setIdsFavoriteCharacters(ids:ArrayList<Int>) {
        for (character in items)
        {
            if(ids.contains(character.id)) {
                character.favorite = true
                update(items.indexOf(character),items.indexOf(character)+1)
            }
        }
    }
    fun setFavoriteCharacter(character: Character) {
        val index = items.indexOf(character)
        items[index].favorite = true
        update(index,index+1)
    }
    fun setNoFavoriteCharacter(character: Character) {
        val index = items.indexOf(character)
        items[index].favorite = false
        update(index,index+1)
    }
}