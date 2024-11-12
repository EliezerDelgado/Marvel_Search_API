package com.eliezer.marvel_search_api.ui.fragments.comic_description.functionImp

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import com.eliezer.marvel_search_api.BR
import com.eliezer.marvel_search_api.core.utils.loadImageFromWebOperations
import com.eliezer.marvel_search_api.data.repository.characters.mock.GetCharactersRepository
import com.eliezer.marvel_search_api.data.repository.characters.mock.SetCharactersRepository
import com.eliezer.marvel_search_api.databinding.FragmentComicDescriptionBinding
import com.eliezer.marvel_search_api.domain.function.FunctionLoadingManager
import com.eliezer.marvel_search_api.domain.function.FunctionToolbarSearch
import com.eliezer.marvel_search_api.domain.listener.MyOnScrolledListener
import com.eliezer.marvel_search_api.models.dataclass.Characters
import com.eliezer.marvel_search_api.models.dataclass.Comic
import com.eliezer.marvel_search_api.ui.fragments.comic_description.ComicDescriptionFragmentArgs
import com.eliezer.marvel_search_api.ui.fragments.comic_description.adapter.ComicDescriptionCharacterListAdapter
import com.eliezer.marvel_search_api.ui.fragments.comic_description.viewmodel.ComicDescriptionViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ComicDescriptionFunctionImplement (
    binding: FragmentComicDescriptionBinding,
    viewModel: ComicDescriptionViewModel,
    getCharactersRepository : GetCharactersRepository,
    setCharactersRepository: SetCharactersRepository,
    private val owner : LifecycleOwner,
    private val context: Context
) :ComicDescriptionCharacterListAdapter.ComicDescriptionComicHolderListener{
    private val myOnScrolledListener = MyOnScrolledListener { getListCharacters() }
    private val functionRepository = FunctionRepository(getCharactersRepository,setCharactersRepository)
    private val functionManagerBinding = FunctionManagerBinding(binding)
    private val functionManagerRecyclerAdapter = FunctionManagerRecyclerAdapter(this)
    private val functionManagerViewModel = FunctionManagerViewModel(viewModel)
    private val functionToolbarSearch = FunctionToolbarSearch(
        functionManagerBinding.getArrayTextView(),
        binding.characterProfileScrollView,
        functionManagerRecyclerAdapter.adapter,
        binding.comicDescriptionRecyclerViewCharacters
    )
    private val functionLoadingManager = FunctionLoadingManager(context)

    override fun onScroll(position: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            functionManagerBinding.setScrollPosition(position)
        }.start()
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
            functionManagerViewModel.setObservesListCharacter(owner,::adapterCharacters)
            functionLoadingManager.showLoadingDialog()
            functionRepository.comic?.also { functionManagerViewModel.searchCharacters(it)}
        }
    }
    fun setAdapter() {
        functionManagerBinding.setAdapterBinding(functionManagerRecyclerAdapter.adapter)
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
        functionManagerViewModel.setNotObservesListCharacter(owner)
        CoroutineScope(Dispatchers.IO).launch {
            characters?.also {
                it.setImageCharacters()
                functionRepository.setListTmpCharacters(it)
                setAdapterCharacters(it)
                functionManagerBinding.setScrollPosition(myOnScrolledListener.positionBefore)
                functionManagerBinding.resetRecyclerView()
            }
            functionManagerBinding.recyclerViewComicsAddScrollListener(myOnScrolledListener)
            functionLoadingManager.stopLoading()
        }.start()
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
    fun errorListener() {
        internalErrorListener()
        errorsForUserListener()
    }

    private fun errorsForUserListener() {
        //TODO("Not yet implemented")
    }

    private fun internalErrorListener() =
        functionManagerViewModel.apply {
            setObservesCharactersViewModelError(owner, ::createErrorLog)
        }
    private fun createErrorLog(throwable: Throwable) =
        Log.e("***",throwable.message,throwable)

    fun stopLoading()=
        functionLoadingManager.stopLoading()

    fun stopErrorListener() =
        functionManagerViewModel.setNoObservesCharactersViewModelError(owner)
}

private class FunctionManagerViewModel(
    private val viewModel: ComicDescriptionViewModel
)
{
    fun setObservesListCharacter(owner: LifecycleOwner, observeCharacters: ((Characters)->(Unit))) =
        viewModel.charactersViewModel.characters.observe(owner,observeCharacters)

    fun setNotObservesListCharacter(owner: LifecycleOwner) {
        viewModel.charactersViewModel.characters.removeObservers(owner)
        viewModel.charactersViewModel.resetCharacters()
    }

    fun searchCharacters(comic: Comic) =
            viewModel.charactersViewModel.searchCharactersList(comic.id)

    fun setObservesCharactersViewModelError(owner: LifecycleOwner,observe: ((Throwable)->(Unit))) =
        viewModel.charactersViewModel.error.observe(owner,observe)


    fun setNoObservesCharactersViewModelError(owner: LifecycleOwner) =
        viewModel.charactersViewModel.error.removeObservers(owner)

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
    fun comicsTitleSetVisible()=
    CoroutineScope(Dispatchers.Main).launch {
        binding.comicDescriptionTextViewComicsTitle.visibility = View.VISIBLE
    }.start()

    fun resetRecyclerView() =
        CoroutineScope(Dispatchers.Main).launch {
            binding.comicDescriptionRecyclerViewCharacters.requestLayout()
         }.start()

    fun setAdapterBinding(adapter: ComicDescriptionCharacterListAdapter) {
        binding.comicDescriptionRecyclerViewCharacters.setHasFixedSize(true)
        binding.comicDescriptionRecyclerViewCharacters.adapter = adapter
    }
    fun setScrollPosition(position: Int) =
        CoroutineScope(Dispatchers.Main).launch {
            try {
                binding.comicDescriptionRecyclerViewCharacters.scrollToPosition(position)
            }
            catch (_ : Exception){}
        }.start()
}
private class FunctionRepository(
    private val getCharactersRepository: GetCharactersRepository,
    private val setCharactersRepository: SetCharactersRepository,
)
{
    var comic: Comic? = null

    fun getIntentExtras(argument: Bundle) {
        comic = ComicDescriptionFragmentArgs.fromBundle(argument).argComic
    }
    fun getListCharacters(): Characters?=
        getCharactersRepository.getListRepository(comic?.id.toString())

    fun  setListTmpCharacters(characters: Characters)  =
        setCharactersRepository.setListTmpCharacters(characters.search,characters)
}


