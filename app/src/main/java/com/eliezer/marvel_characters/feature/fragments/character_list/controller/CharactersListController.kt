package com.eliezer.marvel_characters.feature.fragments.character_list.controller

import androidx.lifecycle.LifecycleOwner
import com.eliezer.marvel_characters.data.repository.GetCharactersRepository
import com.eliezer.marvel_characters.databinding.FragmentCharacterListBinding
import com.eliezer.marvel_characters.feature.fragments.character_list.adapter.CharacterListAdapter
import com.eliezer.marvel_characters.models.dataclass.Character
import javax.inject.Inject

class CharactersListController(
    private val binding: FragmentCharacterListBinding
) {

    @Inject private lateinit var getCharactersRepository: GetCharactersRepository
    private var adapter: CharacterListAdapter? = null
    fun navigateBack() {
        //fragment.baseActivity?.navigationMainActions?.navigateUp()
    }

    fun setAdapter() {
        adapter = CharacterListAdapter(arrayListOf())
        binding.characterListRecyclerView.setHasFixedSize(true)
        binding.characterListRecyclerView.adapter = adapter
    }

    fun getListCharacterRepository()
    {
        setCharacterList(getCharactersRepository.getListRepository())
    }

    private fun setCharacterList(characters: List<Character>?) =
        adapter?.setCharacters(characters ?: emptyList())
}