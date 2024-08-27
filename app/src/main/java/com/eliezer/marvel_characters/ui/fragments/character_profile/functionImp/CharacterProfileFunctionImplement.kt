package com.eliezer.marvel_characters.ui.fragments.character_profile.functionImp

import android.os.Bundle
import android.view.View
import androidx.lifecycle.LifecycleOwner
import com.eliezer.marvel_characters.BR
import com.eliezer.marvel_characters.core.utils.loadImageFromWebOperations
import com.eliezer.marvel_characters.data.repository.comics.mock.GetComicsRepository
import com.eliezer.marvel_characters.databinding.FragmentCharacterProfileBinding
import com.eliezer.marvel_characters.models.dataclass.Character
import com.eliezer.marvel_characters.models.dataclass.Comics
import com.eliezer.marvel_characters.ui.fragments.character_profile.CharacterProfileFragmentArgs
import com.eliezer.marvel_characters.ui.fragments.character_profile.adapter.CharacterProfileComicsListAdapter
import com.eliezer.marvel_characters.ui.fragments.character_profile.viewmodel.CharacterProfileViewModel


class CharacterProfileFunctionImplement(
    private val binding: FragmentCharacterProfileBinding,
    private val viewModel: CharacterProfileViewModel,
    private val getComicsRepository : GetComicsRepository,
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
        viewModel.listComic.observe(owner,::setListComics)
    }

    fun getListComics() {
        val comics = getComicsRepository.getListRepository(character?.id.toString())
        if(comics==null)
            searchComics()
        else
            setListComics(comics)
    }

    private fun searchComics() {
        character?.id?.also {
            setObservesVM()
            viewModel.searchComicsList(it)
        }
    }

    private fun setListComics(comics: Comics?) {
        comics?.also {
            if (it.listComics.isNotEmpty())
                adapter?.setComics(it.listComics)
        }
        resetRecyclerView()
        setNotObservesVM()
    }

    private fun resetRecyclerView() {
        binding.characterProfileRecyclerViewComics.apply {
            visibility = View.GONE
            visibility = View.VISIBLE
        }
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