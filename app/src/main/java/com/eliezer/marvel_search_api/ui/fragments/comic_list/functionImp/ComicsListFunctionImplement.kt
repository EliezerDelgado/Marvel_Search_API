package com.eliezer.marvel_search_api.ui.fragments.comic_list.functionImp

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import com.eliezer.marvel_search_api.databinding.FragmentComicsListBinding
import com.eliezer.marvel_search_api.domain.actions.NavigationMainActions
import com.eliezer.marvel_search_api.domain.listener.MyOnScrolledListener
import com.eliezer.marvel_search_api.domain.local_property.LocalAccount
import com.eliezer.marvel_search_api.models.dataclass.Comic
import com.eliezer.marvel_search_api.models.dataclass.Comics
import com.eliezer.marvel_search_api.ui.fragments.character_list.CharactersListFragmentArgs
import com.eliezer.marvel_search_api.ui.fragments.character_list.functionImp.function.ComicListFunctionManagerRepository
import com.eliezer.marvel_search_api.ui.fragments.comic_list.ComicsListFragmentArgs
import com.eliezer.marvel_search_api.ui.fragments.comic_list.adapter.ComicsListAdapter
import com.eliezer.marvel_search_api.ui.fragments.comic_list.viewmodel.ComicsListViewModel


class ComicsListFunctionImplement (
    binding: FragmentComicsListBinding,
    viewModel: ComicsListViewModel,
    private val navigationMainActions: NavigationMainActions,
    private val comicListFunctionManagerRepository: ComicListFunctionManagerRepository,
    private val owner : LifecycleOwner
) : ComicsListAdapter.ComicHolderListener {

    private var title: String? = null
    private var myOnScrolledListener : MyOnScrolledListener? = MyOnScrolledListener { getListComics() }
    private val functionManagerViewModel = FunctionManagerViewModel(viewModel)
    private val functionManagerRecyclerAdapter = FunctionManagerRecyclerAdapter(this)
    private val functionManagerBinding = FunctionManagerBinding(binding)


    fun getListSearchComicsRepository() {
        title?.also {
            val comics = comicListFunctionManagerRepository.getListRepository(it)
            functionManagerRecyclerAdapter.setComicsList(comics)
            getIdComicsModeSearch(owner)
        }
    }
    fun disableMyOnScrolledListener()
    {
        myOnScrolledListener = null
    }
    fun setAdapter() {
        functionManagerBinding.setAdapter(functionManagerRecyclerAdapter.adapter!!)
    }
    fun setMyOnScrolledListener()
    {
        myOnScrolledListener!!.also { functionManagerBinding.recyclerViewComicsAddScrollListener(it)}
    }


    override fun onComicItemClickListener(comic: Comic) {
        title?.also {
            navigationMainActions.doActionComicsListFragmentToComicDescriptionFragment(comic = comic)
        } ?:  navigationMainActions.doActionFavoritesFragmentToComicDescriptionFragment(comic = comic)
    }

    override fun onImageButtonFavoriteListener(comic: Comic) {
        LocalAccount.authResult.value?.run {
                if (comic.favorite) {
                    comicListFunctionManagerRepository.insertFavoriteComicFireStore(comic.id)
                    comicListFunctionManagerRepository.insertFavoriteComicInDatabase(comic)
                    functionManagerRecyclerAdapter.adapter!!.setFavoriteComic(comic)
                }
                else {
                    comicListFunctionManagerRepository.deleteFavoriteComicFireStore(comic.id)
                    comicListFunctionManagerRepository.deleteFavoriteComicInDatabase(comic)
                    functionManagerRecyclerAdapter.adapter!!.setNoFavoriteComic(comic)
                }
                true
            } ?: {

        }
    }


    fun getMode(arguments: Bundle) = CharactersListFragmentArgs.fromBundle(arguments).argMode
    fun getComicsArg(arguments: Bundle) {
        title = ComicsListFragmentArgs.fromBundle(arguments).argSearchComic
    }


    private fun getListComics() {
        myOnScrolledListener?.also { functionManagerBinding.recyclerViewComicsRemoveScrollListener(it)}
        val comics = title?.let { comicListFunctionManagerRepository.getListRepository(it)}
        if (comics == null || comics.total > comics.listComics.size)
            searchListComics()
        else if (functionManagerRecyclerAdapter.adapter!!.isListEmpty())
            setListComics(comics)
    }

    private fun searchListComics() {
        functionManagerViewModel.setListComicsObservesVM(owner, ::setListComics)
        title?.also { functionManagerViewModel.searchComicList(it)}
    }

    private fun setListComics(comics: Comics?) {
        val position = myOnScrolledListener?.position
        comics?.apply {
            if (listComics.isNotEmpty())
                functionManagerRecyclerAdapter.adapter?.addComics(listComics)

        }
        myOnScrolledListener?.also { functionManagerBinding.resetRecyclerView(it)}
        functionManagerViewModel.setListComicsNoObservesVM(owner)
        position?.also { functionManagerBinding.recyclerViewComicsScrollToPosition(it)}
    }
    private fun setFavoriteListComics(comics: Comics?) {
        functionManagerViewModel.setListComicsNoObservesVM(owner)
        setComicFavorite(comics)
        comics?.apply {
            if (listComics.isNotEmpty())
                functionManagerRecyclerAdapter.adapter?.setComics(listComics)
            else
                functionManagerRecyclerAdapter.adapter?.clearComics()

        }
        functionManagerViewModel.setListComicsNoObservesVM(owner)
    }

    private fun setComicFavorite(comics: Comics?) {
        comics?.listComics?.forEach {
            it.favorite = true
        }
    }

    private fun getIdComicsModeSearch(owner: LifecycleOwner) {
        functionManagerViewModel.setListComicsObservesVM(owner,::setIdsComics)
        functionManagerViewModel.getFavoriteComicsList()
    }

    private fun setIdsComics(comics: Comics) {
        val ids = ArrayList<Int>()
        comics.listComics.forEach {
            ids.add(it.id)
        }
        functionManagerRecyclerAdapter.adapter?.setIdsFavoriteComics(ids)
    }


    fun getListComicsModeFavorite() {
        functionManagerViewModel.setListComicsObservesVM(owner,::setFavoriteListComics)
        functionManagerViewModel.getFavoriteComicsList()
    }
}

private class FunctionManagerBinding(
    private val binding: FragmentComicsListBinding,
)
{
    fun setAdapter(adapter: ComicsListAdapter) {
        binding.comicsListRecyclerView.setHasFixedSize(true)
        binding.comicsListRecyclerView.adapter = adapter
    }

    fun recyclerViewComicsAddScrollListener(myOnScrolledListener: MyOnScrolledListener) {
        binding.comicsListRecyclerView.addOnScrollListener(myOnScrolledListener)
    }
    fun recyclerViewComicsRemoveScrollListener(myOnScrolledListener: MyOnScrolledListener) {
        binding.comicsListRecyclerView.removeOnScrollListener(myOnScrolledListener)
    }
    fun recyclerViewComicsScrollToPosition(position : Int)
    {
        binding.comicsListRecyclerView.scrollToPosition(position)
    }
    fun resetRecyclerView(myOnScrolledListener: MyOnScrolledListener) {
        binding.comicsListRecyclerView.addOnScrollListener(myOnScrolledListener)
    }
}
private class FunctionManagerRecyclerAdapter(
    listener: ComicsListAdapter.ComicHolderListener
)
{
    var adapter: ComicsListAdapter? = ComicsListAdapter(arrayListOf(),listener)
        private set


    fun setComicsList(comics: Comics?) =
        adapter?.addComics(comics?.listComics ?: emptyList())
}
private class FunctionManagerViewModel(
    private val viewModel: ComicsListViewModel)
{
    fun setListComicsObservesVM(owner: LifecycleOwner, observe : ((Comics)->(Unit))) {
        viewModel.listComic.observe(owner,observe)
    }
    fun setListComicsNoObservesVM(owner: LifecycleOwner) {
        viewModel.listComic.removeObservers(owner)
        viewModel.resetComics()
    }
    fun getFavoriteComicsList()
    {
        viewModel.getFavoriteComicsList()
    }

    fun searchComicList(title : String)
    {
        viewModel.searchComicsList(title)
    }
}