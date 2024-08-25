package com.eliezer.marvel_characters.ui.fragments.character_list.functionImp

import android.os.Bundle
import com.eliezer.marvel_characters.data.repository.characters.mock.GetCharactersRepository
import com.eliezer.marvel_characters.databinding.FragmentCharactersListBinding
import com.eliezer.marvel_characters.domain.actions.NavigationMainActions
import com.eliezer.marvel_characters.models.dataclass.Character
import com.eliezer.marvel_characters.models.dataclass.Characters
import com.eliezer.marvel_characters.ui.fragments.character_list.CharactersListFragmentArgs
import com.eliezer.marvel_characters.ui.fragments.character_list.adapter.CharactersListAdapter

class CharactersListFunctionImplement(
    private val binding: FragmentCharactersListBinding,
    private val navigationMainActions: NavigationMainActions,
    private val getCharactersRepository : GetCharactersRepository
) : CharactersListAdapter.CharacterHolderListener{

    private var adapter: CharactersListAdapter? = null
    private var name : String? = null
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
        val characters = getCharactersRepository.getListRepository(name!!)
        setCharacterList(characters)
    }

    private fun setCharacterList(characters: Characters?) =
        adapter?.setCharacters(characters?.listCharacters ?: emptyList())

    override fun onCharacterItemClickListener(character: Character) {
        navigationMainActions.actionCharacterListFragmentToCharacterProfileFragment(character =character)
    }

    fun getIntentExtras(arguments: Bundle) {
        name = CharactersListFragmentArgs.fromBundle(arguments).argSearchCharacter
    }
}