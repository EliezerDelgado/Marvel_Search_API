package com.eliezer.marvel_characters.ui.fragments.comic_description.functionImp

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import com.eliezer.marvel_characters.BR
import com.eliezer.marvel_characters.core.utils.loadImageFromWebOperations
import com.eliezer.marvel_characters.data.repository.characters.mock.GetCharactersRepository
import com.eliezer.marvel_characters.databinding.FragmentComicDescriptionBinding
import com.eliezer.marvel_characters.domain.function.FunctionToolbarSearch
import com.eliezer.marvel_characters.domain.listener.MyOnScrolledListener
import com.eliezer.marvel_characters.models.dataclass.Character
import com.eliezer.marvel_characters.models.dataclass.Characters
import com.eliezer.marvel_characters.models.dataclass.Comic
import com.eliezer.marvel_characters.ui.fragments.character_profile.viewmodel.CharacterProfileViewModel
import com.eliezer.marvel_characters.ui.fragments.comic_description.ComicDescriptionFragmentArgs
import com.eliezer.marvel_characters.ui.fragments.comic_description.adapter.ComicDescriptionCharacterListAdapter
import com.eliezer.marvel_characters.ui.fragments.comic_description.viewmodel.ComicDescriptionViewModel

class ComicDescriptionFunctionImplement (
    binding: FragmentComicDescriptionBinding,
    viewModel: ComicDescriptionViewModel,
    private val getCharactersRepository : GetCharactersRepository,
    private val owner : LifecycleOwner
) :ComicDescriptionCharacterListAdapter.ComicDescriptionComicHolderListener{
    private val myOnScrolledListener = MyOnScrolledListener { getListComics() }
    private val functionRepository = FunctionRepository(getCharactersRepository)
    private val functionManagerBinding = FunctionManagerBinding(binding)
    private val functionManagerRecyclerAdapter = FunctionManagerRecyclerAdapter(this)
    private val functionManagerViewModel = FunctionManagerViewModel(viewModel)
    private val functionToolbarSearch = FunctionToolbarSearch(
        functionManagerBinding.getArrayTextView(),
        binding.characterProfileScrollView,
        functionManagerRecyclerAdapter.adapter,
        binding.comicDescriptionRecyclerViewCharacters
    )

    override fun onScroll(position: Int) {
        functionManagerBinding.setScrollPosition(position)
    }
    fun setBindingVariable() {
        functionManagerBinding.setBindingVariable(functionRepository.comic!!)
    }

    fun getIntentExtras(argument: Bundle) {
        functionRepository.getIntentExtras(argument)
    }

    fun getListCharacters() {
        functionManagerBinding.recyclerViewComicsRemoveScrollListener(myOnScrolledListener)
        if(!getCharactersRepository())
        {
            functionManagerViewModel.setObservesVM(owner,::adapterCharacters)
            functionRepository.searchCharacters(functionManagerViewModel.viewModel)
        }
    }
    fun setAdapter() {
        functionManagerBinding.setAdapter(functionManagerRecyclerAdapter.adapter)
        functionManagerBinding.recyclerViewComicsAddScrollListener(myOnScrolledListener)
    }
    fun searchWordBack() {
        functionToolbarSearch.searchWordBack()
    }

    fun searchWordForward() {
        functionToolbarSearch.searchWordForward()
    }

    fun searchText(text: String) {
        functionToolbarSearch.searchText(text)
    }
    fun returnNormalColor() {
        functionToolbarSearch.returnNormalColor()
    }
    private fun adapterCharacters(characters: Characters?)
    {
        setAdapterCharacters(characters)
        functionManagerViewModel.setNotObservesVM(owner)
        functionManagerBinding.setScrollPosition(myOnScrolledListener.position)
        functionManagerBinding.resetRecyclerView()
        functionManagerBinding.recyclerViewComicsAddScrollListener(myOnScrolledListener)
    }
    private fun getCharactersRepository() : Boolean
    {
        val characters =functionRepository.getListCharacters()
        return characters?.run {
            if (total > listCharacters.size) {
                if(functionManagerRecyclerAdapter.adapterIsEmpty())
                    setAdapterCharacters(characters)
                true
            }
            else
                false
        }   ?: false
    }
    private fun setAdapterCharacters(characters: Characters?)
    {
        characters?.also {
            if (it.listCharacters.isNotEmpty()) {
                functionManagerRecyclerAdapter.setCharacters(it)
                functionManagerBinding.comicsTitleSetVisible()
            }
        }
    }
}

private class FunctionManagerViewModel(
    val viewModel: ComicDescriptionViewModel
)
{
    fun setObservesVM(owner: LifecycleOwner,observeCharacters: ((Characters)->(Unit))) {
        viewModel.listCharacter.observe(owner,observeCharacters)
    }
    fun setNotObservesVM(owner: LifecycleOwner) {
        viewModel.listCharacter.removeObservers(owner)
        viewModel.resetCharacters()
    }

}
private class FunctionManagerRecyclerAdapter(listener:
                                             ComicDescriptionCharacterListAdapter.ComicDescriptionComicHolderListener)
{
    var adapter: ComicDescriptionCharacterListAdapter = ComicDescriptionCharacterListAdapter(
        arrayListOf(),
        listener
    )
        private set
    fun adapterIsEmpty()=  adapter.isListEmpty()
    fun setCharacters(characters: Characters)
    {
        adapter.setCharacters(characters.listCharacters)
    }
}
private class FunctionManagerBinding(
    private val binding: FragmentComicDescriptionBinding
)
{
    fun getArrayTextView() = arrayListOf<TextView>(
        binding.comicDescriptionTextViewName,
        binding.comicDescriptionTextViewDescription,
        binding.comicDescriptionTextViewComicsTitle
    )
    fun setBindingVariable(comic: Comic) {
        binding.setVariable(BR.item, comic)
        val t = Thread {
            comic.urlPicture.also {
                binding.setVariable(
                    BR.img,
                    loadImageFromWebOperations(it)
                )
            }
        }
        t.start()
    }
    fun recyclerViewComicsRemoveScrollListener(myOnScrolledListener: MyOnScrolledListener)
    {
        binding.comicDescriptionRecyclerViewCharacters.removeOnScrollListener(myOnScrolledListener)
    }
    fun recyclerViewComicsAddScrollListener(myOnScrolledListener: MyOnScrolledListener)
    {
        binding.comicDescriptionRecyclerViewCharacters.addOnScrollListener(myOnScrolledListener)
    }
    fun comicsTitleSetVisible()
    {
        binding.comicDescriptionTextViewComicsTitle.visibility = View.VISIBLE
    }

    fun resetRecyclerView() {
        binding.comicDescriptionRecyclerViewCharacters.requestLayout()
    }

    fun setAdapter(adapter: ComicDescriptionCharacterListAdapter) {
        binding.comicDescriptionRecyclerViewCharacters.setHasFixedSize(true)
        binding.comicDescriptionRecyclerViewCharacters.adapter = adapter
    }
    fun setScrollPosition(position: Int) {
        binding.comicDescriptionRecyclerViewCharacters.scrollToPosition(position)
    }
}
private class FunctionRepository(
    private val getCharactersRepository: GetCharactersRepository
)
{
    var comic: Comic? = null

    fun getIntentExtras(argument: Bundle) {
        comic = ComicDescriptionFragmentArgs.fromBundle(argument).argComic
    }
    fun getListCharacters(): Characters?
    {
        return getCharactersRepository.getListRepository(comic?.id.toString())
    }
    fun searchCharacters(viewModel: ComicDescriptionViewModel) {
        comic?.id?.also {
            viewModel.searchCharactersList(it)
        }
    }
}


