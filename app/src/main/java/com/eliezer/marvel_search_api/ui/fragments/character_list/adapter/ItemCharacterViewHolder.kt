package com.eliezer.marvel_search_api.ui.fragments.character_list.adapter

import com.eliezer.marvel_search_api.BR
import com.eliezer.marvel_search_api.core.base.BaseItemViewHolder
import com.eliezer.marvel_search_api.core.utils.loadImageFromWebOperations
import com.eliezer.marvel_search_api.databinding.ItemCharacterBinding
import com.eliezer.marvel_search_api.models.dataclass.Character

open class ItemCharacterViewHolder(override var binding: ItemCharacterBinding) : BaseItemViewHolder<Character>(binding = binding ) {
    override fun onBindMethodCalled(item: Character) {
        val t = Thread {
            binding.setVariable(BR.img, loadImageFromWebOperations(item.urlPicture))
        }
        t.start()
    }
    fun itemCharacterImageButtonFavoriteListener(listener: ()->Unit){
        binding.itemCharacterImageButtonFavorite.setOnClickListener {
            listener.invoke()
        }
    }
}