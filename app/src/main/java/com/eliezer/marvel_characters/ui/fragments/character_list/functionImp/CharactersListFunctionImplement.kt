package com.eliezer.marvel_characters.ui.fragments.character_list.functionImp

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import com.eliezer.marvel_characters.data.repository.characters.mock.GetCharactersRepository
import com.eliezer.marvel_characters.databinding.FragmentCharactersListBinding
import com.eliezer.marvel_characters.domain.actions.NavigationMainActions
import com.eliezer.marvel_characters.domain.listener.MyOnScrolled
import com.eliezer.marvel_characters.models.dataclass.Character
import com.eliezer.marvel_characters.models.dataclass.Characters
import com.eliezer.marvel_characters.ui.fragments.character_list.CharactersListFragmentArgs
import com.eliezer.marvel_characters.ui.fragments.character_list.adapter.CharactersListAdapter
import com.eliezer.marvel_characters.ui.fragments.character_list.viewmodel.CharactersListViewModel

class CharactersListFunctionImplement(
    private val binding: FragmentCharactersListBinding,
    private val navigationMainActions: NavigationMainActions,
    private val  viewModel: CharactersListViewModel,
    private val getCharactersRepository : GetCharactersRepository,
    private val owner : LifecycleOwner
) : CharactersListAdapter.CharacterHolderListener{

    private var adapter: CharactersListAdapter? = null
    private var name : String? = null
    private val myOnScrolled = MyOnScrolled { getListCharacters()}

    fun setAdapter() {
        adapter = CharactersListAdapter(arrayListOf(),this)
        binding.charactersListRecyclerView.setHasFixedSize(true)
        binding.charactersListRecyclerView.adapter = adapter
        binding.charactersListRecyclerView.addOnScrollListener(myOnScrolled)
    }

    fun getListCharactersRepository()
    {
        name?.also {
            val characters = getCharactersRepository.getListRepository(it)
            setCharacterList(characters)
        }
    }

    private fun setCharacterList(characters: Characters?) =
        adapter?.setCharacters(characters?.listCharacters ?: emptyList())

    override fun onCharacterItemClickListener(character: Character) {
        navigationMainActions.actionCharacterListFragmentToCharacterProfileFragment(character =character)
    }

    fun getIntentExtras(arguments: Bundle) {
        name = CharactersListFragmentArgs.fromBundle(arguments).argSearchCharacter
    }

    private fun setObservesVM() {
        viewModel.listCharacter.observe(owner,::setListCharacters)
    }

    private fun getListCharacters() {
        binding.charactersListRecyclerView.removeOnScrollListener(myOnScrolled)
        val characters = getCharactersRepository.getListRepository(name!!)
        if(characters==null || characters.total > characters.listCharacters.size)
        {
            searchListCharacters()
        }
        else if (adapter!!.isListEmpty())
        {
            setListCharacters(characters)
        }
    }

    private fun searchListCharacters() {
            setObservesVM()
            viewModel.searchCharactersList(name!!)
    }

    private fun setListCharacters(characters: Characters?) {
        val position = myOnScrolled.position
        characters?.apply {
            if (listCharacters.isNotEmpty())
                adapter?.setCharacters(listCharacters)
        }
        binding.charactersListRecyclerView.scrollToPosition(position)
        resetRecyclerView()
        setNotObservesVM()
    }

    private fun setNotObservesVM() {
        viewModel.listCharacter.removeObservers(owner)
        viewModel.resetCharacters()
    }
    private fun resetRecyclerView() {
        binding.charactersListRecyclerView.addOnScrollListener(myOnScrolled)
    }
}