package com.eliezer.marvel_characters.ui.fragments.character_list.adapter

import com.eliezer.marvel_characters.BR
import com.eliezer.marvel_characters.core.base.BaseItemViewHolder
import com.eliezer.marvel_characters.core.utils.loadImageFromWebOperations
import com.eliezer.marvel_characters.databinding.ItemCharacterBinding
import com.eliezer.marvel_characters.models.dataclass.Character

class ItemCharacterViewHolder(binding: ItemCharacterBinding) : BaseItemViewHolder<Character>(binding = binding ) {
    override fun onBindMethodCalled(item: Character) {
        val t = Thread {
            binding.setVariable(BR.img, loadImageFromWebOperations(item.urlPicture))
        }
        t.start()
    }
}