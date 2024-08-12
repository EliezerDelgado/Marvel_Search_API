package com.eliezer.marvel_characters.ui.fragments.character_profile.functionImp

import android.os.Bundle
import com.eliezer.marvel_characters.BR
import com.eliezer.marvel_characters.core.utils.loadImageFromWebOperations
import com.eliezer.marvel_characters.databinding.FragmentCharacterProfileBinding
import com.eliezer.marvel_characters.models.dataclass.Character
import com.eliezer.marvel_characters.ui.fragments.character_profile.CharacterProfileFragmentArgs
import com.eliezer.marvel_characters.ui.fragments.character_profile.adapter.CharacterProfileComicsListAdapter

class CharacterProfileFuctionImplement(
    private val binding: FragmentCharacterProfileBinding) {
    private var character: Character? = null

    private var adapter: CharacterProfileComicsListAdapter? = null
    fun setBindingVariable()
    {
        binding.setVariable(BR.item,character)
        val t = Thread {
            character?.urlPicture.also { binding.setVariable(BR.img, loadImageFromWebOperations(it)) }
        }
        t.start()
    }

    fun setAdapter() {
        adapter = CharacterProfileComicsListAdapter(arrayListOf())
        binding.characterProfileRecyclerViewComics.setHasFixedSize(true)
        binding.characterProfileRecyclerViewComics.adapter = adapter
        setComicSummaryList()
    }

    private fun setComicSummaryList() =
        character?.comics?.also {
            adapter?.setListComicSummary(it)
        }

    fun getIntentExtras(argument : Bundle) {
        character = CharacterProfileFragmentArgs.fromBundle(argument).argCharacter
    }
}