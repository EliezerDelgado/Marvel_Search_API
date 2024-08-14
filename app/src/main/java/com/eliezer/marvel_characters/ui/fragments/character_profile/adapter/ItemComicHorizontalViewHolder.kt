package com.eliezer.marvel_characters.ui.fragments.character_profile.adapter

import com.eliezer.marvel_characters.BR
import com.eliezer.marvel_characters.core.base.BaseItemViewHolder
import com.eliezer.marvel_characters.core.utils.loadImageFromWebOperations
import com.eliezer.marvel_characters.databinding.ItemComicHorizontalBinding
import com.eliezer.marvel_characters.models.dataclass.Comic

class ItemComicHorizontalViewHolder(binding: ItemComicHorizontalBinding) : BaseItemViewHolder<Comic>(binding = binding ) {
    override fun onBindMethodCalled(item: Comic) {
        val t = Thread {
            binding.setVariable(BR.img, loadImageFromWebOperations(item.urlPicture))
        }
        t.start()
    }
}