package com.eliezer.marvel_search_api.ui.fragments.character_profile.functionImp

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import com.eliezer.marvel_search_api.BR
import com.eliezer.marvel_search_api.core.utils.loadImageFromWebOperations
import com.eliezer.marvel_search_api.data.repository.comics.mock.GetComicsRepository
import com.eliezer.marvel_search_api.databinding.FragmentCharacterProfileBinding
import com.eliezer.marvel_search_api.domain.function.FunctionToolbarSearch
import com.eliezer.marvel_search_api.domain.listener.MyOnScrolledListener
import com.eliezer.marvel_search_api.models.dataclass.Character
import com.eliezer.marvel_search_api.models.dataclass.Comics
import com.eliezer.marvel_search_api.ui.fragments.character_profile.CharacterProfileFragmentArgs
import com.eliezer.marvel_search_api.ui.fragments.character_profile.adapter.CharacterProfileComicsListAdapter
import com.eliezer.marvel_search_api.ui.fragments.character_profile.viewmodel.CharacterProfileViewModel

class CharacterProfileFunctionImplement(
    binding: FragmentCharacterProfileBinding,
    viewModel: CharacterProfileViewModel,
    getComicsRepository : GetComicsRepository,
    private val owner : LifecycleOwner
) : CharacterProfileComicsListAdapter.CharacterProfileComicHolderListener{
    private val myOnScrolledListener = MyOnScrolledListener { getListComics() }
    private val functionRepository = FunctionRepository(getComicsRepository)
    private val functionManagerBinding =FunctionManagerBinding(binding)
    private val functionManagerRecyclerAdapter = FunctionManagerRecyclerAdapter(this)
    private val functionManagerViewModel = FunctionManagerViewModel(viewModel)
    private val functionToolbarSearch = FunctionToolbarSearch(
        functionManagerBinding.getArrayTextView(),
        binding.characterProfileScrollView,
        functionManagerRecyclerAdapter.adapter,
        binding.characterProfileRecyclerViewComics
    )

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
            functionManagerViewModel.setObservesVM(owner,::adapterComics)
            functionRepository.searchComics(functionManagerViewModel.viewModel)
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
        setAdapterComics(comics)
        functionManagerViewModel.setNotObservesVM(owner)
        functionManagerBinding.setScrollPosition(myOnScrolledListener.position)
        functionManagerBinding.resetRecyclerView()
        functionManagerBinding.recyclerViewComicsAddScrollListener(myOnScrolledListener)
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
}
private class FunctionManagerViewModel(
    val viewModel: CharacterProfileViewModel)
{
    fun setObservesVM(owner: LifecycleOwner,observeComics: ((Comics)->(Unit))) {
        viewModel.listComic.observe(owner,observeComics)
    }
    fun setNotObservesVM(owner: LifecycleOwner) {
        viewModel.listComic.removeObservers(owner)
        viewModel.resetComics()
    }

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
    fun setComics(comics: Comics)
    {
        adapter.setComics(comics.listComics)
    }
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
        val t = Thread {
            character.urlPicture.also {
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
        binding.characterProfileRecyclerViewComics.removeOnScrollListener(myOnScrolledListener)
    }
    fun recyclerViewComicsAddScrollListener(myOnScrolledListener: MyOnScrolledListener)
    {
        binding.characterProfileRecyclerViewComics.addOnScrollListener(myOnScrolledListener)
    }
    fun comicsTitleSetVisible()
    {
        binding.characterProfileTextViewComicsTitle.visibility = View.VISIBLE
    }

    fun resetRecyclerView() {
        binding.characterProfileRecyclerViewComics.requestLayout()
    }

    fun setAdapter(adapter: CharacterProfileComicsListAdapter) {
        binding.characterProfileRecyclerViewComics.setHasFixedSize(true)
        binding.characterProfileRecyclerViewComics.adapter = adapter
    }
    fun setScrollPosition(position: Int) {
        binding.characterProfileRecyclerViewComics.scrollToPosition(position)
    }
}
private class FunctionRepository(
    private val getComicsRepository : GetComicsRepository
)
{
    var character: Character? = null

    fun getIntentExtras(argument: Bundle) {
        character = CharacterProfileFragmentArgs.fromBundle(argument).argCharacter
    }
    fun getListComics(): Comics?
    {
        return getComicsRepository.getListRepository(character?.id.toString())
    }
    fun searchComics(viewModel: CharacterProfileViewModel) {
        character?.id?.also {
            viewModel.searchComicsList(it)
        }
    }
}
