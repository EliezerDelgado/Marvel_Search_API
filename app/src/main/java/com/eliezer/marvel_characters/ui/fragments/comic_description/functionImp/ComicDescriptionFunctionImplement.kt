package com.eliezer.marvel_characters.ui.fragments.comic_description.functionImp

import android.os.Bundle
import android.view.View
import androidx.lifecycle.LifecycleOwner
import com.eliezer.marvel_characters.BR
import com.eliezer.marvel_characters.core.utils.loadImageFromWebOperations
import com.eliezer.marvel_characters.data.repository.characters.mock.GetCharactersRepository
import com.eliezer.marvel_characters.databinding.FragmentComicDescriptionBinding
import com.eliezer.marvel_characters.models.dataclass.Character
import com.eliezer.marvel_characters.models.dataclass.Characters
import com.eliezer.marvel_characters.models.dataclass.Comic
import com.eliezer.marvel_characters.ui.fragments.comic_description.ComicDescriptionFragmentArgs
import com.eliezer.marvel_characters.ui.fragments.comic_description.adapter.ComicDescriptionCharacterListAdapter
import com.eliezer.marvel_characters.ui.fragments.comic_description.viewmodel.ComicDescriptionViewModel

class ComicDescriptionFunctionImplement (
    private val binding: FragmentComicDescriptionBinding,
    private val viewModel: ComicDescriptionViewModel,
    private val getCharactersRepository : GetCharactersRepository,
    private val owner : LifecycleOwner
) {
    private var comic: Comic? = null

    private var adapter: ComicDescriptionCharacterListAdapter? = null
    fun setBindingVariable()
    {
        binding.setVariable(BR.item,comic)
        val t = Thread {
            comic?.urlPicture.also { binding.setVariable(BR.img, loadImageFromWebOperations(it)) }
        }
        t.start()
    }
    private fun setObservesVM() {
        viewModel.listCharacter.observe(owner,::setListCharacters)
    }

    fun getListCharacters() {
        val characters = getCharactersRepository.getListRepository(comic?.id.toString())
        if(characters==null)
        {
            searchListCharacters()
        }
        else
        {
            setListCharacters(characters)
        }
    }

    private fun searchListCharacters() {

        comic?.id?.also {
            setObservesVM()
            viewModel.searchCharactersList(it)
        }
    }

    private fun setListCharacters(characters: Characters?) {
        characters?.apply {
            if (listCharacters.isNotEmpty())
                adapter?.setCharacters(listCharacters)
        }
        resetRecyclerView()
        setNotObservesVM()
    }

    private fun resetRecyclerView() {
        binding.comicDescriptionRecyclerViewComics.apply {
            visibility = View.GONE
            visibility = View.VISIBLE
        }
    }


    private fun setNotObservesVM() {
        viewModel.listCharacter.removeObservers(owner)
        viewModel.resetCharacters()
    }

    fun setAdapter() {
        adapter = ComicDescriptionCharacterListAdapter(arrayListOf())
        binding.comicDescriptionRecyclerViewComics.setHasFixedSize(true)
        binding.comicDescriptionRecyclerViewComics.adapter = adapter
    }

    fun getIntentExtras(argument : Bundle) {
        comic = ComicDescriptionFragmentArgs.fromBundle(argument).argComic
    }
}