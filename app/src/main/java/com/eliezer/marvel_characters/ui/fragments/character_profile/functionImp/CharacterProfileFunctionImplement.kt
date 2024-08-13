package com.eliezer.marvel_characters.ui.fragments.character_profile.functionImp

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import com.eliezer.marvel_characters.BR
import com.eliezer.marvel_characters.core.utils.loadImageFromWebOperations
import com.eliezer.marvel_characters.databinding.FragmentCharacterProfileBinding
import com.eliezer.marvel_characters.models.dataclass.Character
import com.eliezer.marvel_characters.models.dataclass.Comic
import com.eliezer.marvel_characters.ui.fragments.character_profile.CharacterProfileFragmentArgs
import com.eliezer.marvel_characters.ui.fragments.character_profile.adapter.CharacterProfileComicsListAdapter
import com.eliezer.marvel_characters.ui.fragments.character_profile.viewmodel.CharacterProfileViewModel

class CharacterProfileFunctionImplement(
    private val binding: FragmentCharacterProfileBinding,
    private val viewModel: CharacterProfileViewModel,
    private val owner : LifecycleOwner
) {
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
    private fun setObservesVM() {
        viewModel.listComic.observe(owner,::getListComics)
    }

    fun searchListComics() {
        character?.id?.also {
            setObservesVM()
            viewModel.searchComicsList(it)
        }
    }

    private fun getListComics(comics: List<Comic>?) {
        comics?.also {
            if (it.isNotEmpty())
                adapter?.setComics(it)
        }
        setNotObservesVM()
    }


    private fun setNotObservesVM() {
        viewModel.listComic.removeObservers(owner)
        viewModel.resetComics()
    }

    fun setAdapter() {
        adapter = CharacterProfileComicsListAdapter(arrayListOf())
        binding.characterProfileRecyclerViewComics.setHasFixedSize(true)
        binding.characterProfileRecyclerViewComics.adapter = adapter
    }

    fun getIntentExtras(argument : Bundle) {
        character = CharacterProfileFragmentArgs.fromBundle(argument).argCharacter
    }
}