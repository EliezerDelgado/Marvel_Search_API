package com.eliezer.marvel_characters.feature.fragments.character_list.controller

import androidx.lifecycle.LifecycleOwner
import com.eliezer.marvel_characters.databinding.FragmentCharacterListBinding
import com.eliezer.marvel_characters.feature.fragments.character_list.adapter.CharacterListAdapter
import com.eliezer.marvel_characters.feature.fragments.character_list.viewmodel.CharacterViewModel
import com.eliezer.marvel_characters.models.dataclass.Character

class CharactersListController(
    private val binding: FragmentCharacterListBinding,
    private val characterViewModel: CharacterViewModel,
    private val owner : LifecycleOwner
) {

    private var adapter : CharacterListAdapter? = null
    fun navigateBack()
    {
        //fragment.baseActivity?.navigationMainActions?.navigateUp()
    }
    fun setAdapter()
    {
        adapter = CharacterListAdapter( arrayListOf())
        binding.characterListRecyclerView.setHasFixedSize(true)
        binding.characterListRecyclerView.adapter = adapter
    }

    fun setObservesVM() {
        characterViewModel.characterList.observe(owner,::setCharacterList)
    }

    private fun setCharacterList(characters: List<Character>?) {
        adapter?.setCharacters(characters ?: emptyList())
    }
}