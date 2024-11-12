package com.eliezer.marvel_search_api.ui.fragments.character_profile.functionImp

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import com.eliezer.marvel_search_api.BR
import com.eliezer.marvel_search_api.core.utils.loadImageFromWebOperations
import com.eliezer.marvel_search_api.data.repository.comics.mock.GetComicsRepository
import com.eliezer.marvel_search_api.data.repository.comics.mock.SetComicsRepository
import com.eliezer.marvel_search_api.databinding.FragmentCharacterProfileBinding
import com.eliezer.marvel_search_api.domain.function.FunctionLoadingManager
import com.eliezer.marvel_search_api.domain.function.FunctionToolbarSearch
import com.eliezer.marvel_search_api.domain.listener.MyOnScrolledListener
import com.eliezer.marvel_search_api.models.dataclass.Character
import com.eliezer.marvel_search_api.models.dataclass.Comics
import com.eliezer.marvel_search_api.ui.fragments.character_profile.CharacterProfileFragmentArgs
import com.eliezer.marvel_search_api.ui.fragments.character_profile.adapter.CharacterProfileComicsListAdapter
import com.eliezer.marvel_search_api.ui.fragments.character_profile.viewmodel.CharacterProfileViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CharacterProfileFunctionImplement(
    binding: FragmentCharacterProfileBinding,
    viewModel: CharacterProfileViewModel,
    getComicsRepository : GetComicsRepository,
    setComicsRepository: SetComicsRepository,
    private val owner : LifecycleOwner,
    context: Context
) : CharacterProfileComicsListAdapter.CharacterProfileComicHolderListener{
    private val myOnScrolledListener = MyOnScrolledListener {
        getListComics()
    }
    private val functionRepository = FunctionRepository(getComicsRepository,setComicsRepository)
    private val functionManagerBinding = FunctionManagerBinding(binding)
    private val functionManagerRecyclerAdapter = FunctionManagerRecyclerAdapter(this)
    private val functionManagerViewModel = FunctionManagerViewModel(viewModel)
    private val functionToolbarSearch = FunctionToolbarSearch(
        functionManagerBinding.getArrayTextView(),
        binding.characterProfileScrollView,
        functionManagerRecyclerAdapter.adapter,
        binding.characterProfileRecyclerViewComics
    )
    private val functionLoadingManager = FunctionLoadingManager(context)

    override fun onScroll(position: Int) {
        functionManagerBinding.setScrollPosition(position)
    }

    fun setBindingVariable() {
        functionManagerBinding.setBindingVariable(functionRepository.character!!)
    }

    fun getIntentExtras(argument: Bundle) {
        functionRepository.getIntentExtras(argument)
    }

    fun getListComics() {
        functionManagerBinding.recyclerViewComicsRemoveScrollListener(myOnScrolledListener)
        if(!getComicsRepository())
        {
            functionManagerViewModel.setObservesListComic(owner,::adapterComics)
            functionLoadingManager.showLoadingDialog()
            functionRepository.character?.also { (functionManagerViewModel.searchComics(it))}
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
    private fun adapterComics(comics: Comics?)
    {
        functionManagerViewModel.setNotObservesLitComic(owner)
        CoroutineScope(Dispatchers.IO).launch {
            comics?.also {
                it.setImageComics()
                functionRepository.setListTmpComics(it)
                setAdapterComics(it)
                functionManagerBinding.setScrollPosition(myOnScrolledListener.positionBefore)
                functionManagerBinding.resetRecyclerView()
            }
            functionManagerBinding.recyclerViewComicsAddScrollListener(myOnScrolledListener)
            functionLoadingManager.stopLoading()
        }.start()
    }
    private fun getComicsRepository() : Boolean
    {
        val comics =functionRepository.getListComics()
        return comics?.run {
            if (total > listComics.size) {
                if(functionManagerRecyclerAdapter.adapterIsEmpty())
                    setAdapterComics(comics)
                true
            }
            else
                false
        }   ?: false
    }
    private fun setAdapterComics(comics: Comics?)
    {
        comics?.also {
            if (it.listComics.isNotEmpty()) {
                functionManagerRecyclerAdapter.setComics(it)
                functionManagerBinding.comicsTitleSetVisible()
            }
        }
    }
    fun errorListener() {
        internalErrorListener()
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
    private val viewModel: CharacterProfileViewModel)
{
    fun setObservesListComic(owner: LifecycleOwner, observeComics: ((Comics)->(Unit))) {
        viewModel.comicsViewModel.comics.observe(owner,observeComics)
    }
    fun setNotObservesLitComic(owner: LifecycleOwner) {
        viewModel.comicsViewModel.comics.removeObservers(owner)
        viewModel.comicsViewModel.resetComics()
    }

    fun searchComics(character: Character) =
        viewModel.comicsViewModel.searchComicsList(character.id)

    fun setObservesCharactersViewModelError(owner: LifecycleOwner,observe: ((Throwable)->(Unit))) =
        viewModel.comicsViewModel.error.observe(owner,observe)


    fun setNoObservesCharactersViewModelError(owner: LifecycleOwner) =
        viewModel.comicsViewModel.error.removeObservers(owner)

}
private class FunctionManagerRecyclerAdapter(listener:
    CharacterProfileComicsListAdapter.CharacterProfileComicHolderListener)
{
    var adapter: CharacterProfileComicsListAdapter = CharacterProfileComicsListAdapter(
        arrayListOf(),
        listener
    )
        private set
    fun adapterIsEmpty()=  adapter.isListEmpty()
    fun setComics(comics: Comics)=
        adapter.setComics(comics.listComics)
}
private class FunctionManagerBinding(
    private val binding: FragmentCharacterProfileBinding
)
{
    fun getArrayTextView() = arrayListOf<TextView>(
        binding.characterProfileTextViewName,
        binding.characterProfileTextViewDescription,
        binding.characterProfileTextViewComicsTitle
    )
    fun setBindingVariable(character:Character) {
        binding.setVariable(BR.item, character)
        CoroutineScope(Dispatchers.IO).launch {
            character.urlPicture.also {
                binding.setVariable(
                    BR.img,
                    loadImageFromWebOperations(it)
                )
            }
        }.start()
    }
    fun recyclerViewComicsRemoveScrollListener(myOnScrolledListener: MyOnScrolledListener)
    {
        binding.characterProfileRecyclerViewComics.removeOnScrollListener(myOnScrolledListener)
    }
    fun recyclerViewComicsAddScrollListener(myOnScrolledListener: MyOnScrolledListener)
    {
        binding.characterProfileRecyclerViewComics.addOnScrollListener(myOnScrolledListener)
    }
    fun comicsTitleSetVisible() =
        CoroutineScope(Dispatchers.Main).launch {
            binding.characterProfileTextViewComicsTitle.visibility = View.VISIBLE
        }.start()

    fun resetRecyclerView()  =
        CoroutineScope(Dispatchers.Main).launch {
            binding.characterProfileRecyclerViewComics.requestLayout()
        }.start()

    fun setAdapter(adapter: CharacterProfileComicsListAdapter) {
        binding.characterProfileRecyclerViewComics.setHasFixedSize(true)
        binding.characterProfileRecyclerViewComics.adapter = adapter
    }
    fun setScrollPosition(position: Int)  =
        CoroutineScope(Dispatchers.Main).launch {
            try {
                binding.characterProfileRecyclerViewComics.scrollToPosition(position)
            }
            catch (_ : Exception){}
        }.start()
}
private class FunctionRepository(
    private val getComicsRepository : GetComicsRepository,
    private val  setComicsRepository: SetComicsRepository
)
{
    var character: Character? = null

    fun getIntentExtras(argument: Bundle) {
        character = CharacterProfileFragmentArgs.fromBundle(argument).argCharacter
    }
    fun getListComics(): Comics?=
        getComicsRepository.getListRepository(character?.id.toString())

    fun setListTmpComics(comics: Comics)=
        setComicsRepository.setListTmpComics(comics.search,comics)
}
