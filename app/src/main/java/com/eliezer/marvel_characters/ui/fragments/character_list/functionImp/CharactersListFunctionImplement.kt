package com.eliezer.marvel_characters.ui.fragments.character_list.functionImp

import com.eliezer.marvel_characters.data.repository.mock.GetCharactersRepository
import com.eliezer.marvel_characters.databinding.FragmentCharacterListBinding
import com.eliezer.marvel_characters.ui.fragments.character_list.adapter.CharacterListAdapter
import com.eliezer.marvel_characters.models.dataclass.Character

class CharactersListFunctionImplement(
    private val binding: FragmentCharacterListBinding,
    private val getCharactersRepository : GetCharactersRepository
) {

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
        val i = getCharactersRepository.getListRepository()
        setCharacterList(i)
    }

    private fun setCharacterList(characters: List<Character>?) =
        adapter?.setCharacters(characters ?: emptyList())
}