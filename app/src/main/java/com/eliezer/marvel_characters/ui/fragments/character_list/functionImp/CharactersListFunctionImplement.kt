package com.eliezer.marvel_characters.ui.fragments.character_list.functionImp

import com.eliezer.marvel_characters.data.repository.characters.mock.GetCharactersRepository
import com.eliezer.marvel_characters.databinding.FragmentCharacterListBinding
import com.eliezer.marvel_characters.ui.fragments.character_list.adapter.CharactersListAdapter
import com.eliezer.marvel_characters.models.dataclass.Character

class CharactersListFunctionImplement(
    private val binding: FragmentCharacterListBinding,
    private val getCharactersRepository : GetCharactersRepository
) {

    private var adapter: CharactersListAdapter? = null
    fun navigateBack() {
        //fragment.baseActivity?.navigationMainActions?.navigateUp()
    }

    fun setAdapter() {
        adapter = CharactersListAdapter(arrayListOf())
        binding.charactersListRecyclerView.setHasFixedSize(true)
        binding.charactersListRecyclerView.adapter = adapter
    }

    fun getListCharactersRepository()
    {
        val characters = getCharactersRepository.getListRepository()
        setCharacterList(characters)
    }

    private fun setCharacterList(characters: List<Character>?) =
        adapter?.setCharacters(characters ?: emptyList())
}