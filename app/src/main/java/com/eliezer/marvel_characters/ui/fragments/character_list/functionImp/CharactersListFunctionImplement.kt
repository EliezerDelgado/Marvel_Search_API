package com.eliezer.marvel_characters.ui.fragments.character_list.functionImp

import android.content.Intent.getIntent
import com.eliezer.marvel_characters.data.repository.characters.mock.GetCharactersRepository
import com.eliezer.marvel_characters.databinding.FragmentCharacterListBinding
import com.eliezer.marvel_characters.domain.actions.NavigationMainActions
import com.eliezer.marvel_characters.models.dataclass.Character
import com.eliezer.marvel_characters.ui.fragments.character_list.adapter.CharactersListAdapter

class CharactersListFunctionImplement(
    private val binding: FragmentCharacterListBinding,
    private val navigationMainActions: NavigationMainActions,
    private val getCharactersRepository : GetCharactersRepository
) : CharactersListAdapter.CharacterHolderListener{

    private var adapter: CharactersListAdapter? = null
    fun navigateBack() {
        //fragment.baseActivity?.navigationMainActions?.navigateUp()
    }

    fun setAdapter() {
        adapter = CharactersListAdapter(arrayListOf(),this)
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

    override fun onCharacterItemClickListener(character: Character) {
        navigationMainActions.actionCharacterListFragmentToCharacterProfileFragment(character =character)
    }
}