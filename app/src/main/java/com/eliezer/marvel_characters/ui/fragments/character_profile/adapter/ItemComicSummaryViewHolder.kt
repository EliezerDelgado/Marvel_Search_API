package com.eliezer.marvel_characters.ui.fragments.character_profile.adapter

import com.eliezer.marvel_characters.BR
import com.eliezer.marvel_characters.core.base.BaseItemViewHolder
import com.eliezer.marvel_characters.core.utils.loadImageFromWebOperations
import com.eliezer.marvel_characters.databinding.ItemCharacterBinding
import com.eliezer.marvel_characters.databinding.ItemComicSummaryBinding
import com.eliezer.marvel_characters.models.dataclass.Character
import com.eliezer.marvel_characters.models.responses.character.ComicSummary

class ItemComicSummaryViewHolder(binding: ItemComicSummaryBinding) : BaseItemViewHolder<ComicSummary>(binding = binding ) {
    override fun onBindMethodCalled(item: ComicSummary) {
        val t = Thread {
            binding.setVariable(BR.img, loadImageFromWebOperations(item.resourceURI))
        }
        t.start()
    }
}