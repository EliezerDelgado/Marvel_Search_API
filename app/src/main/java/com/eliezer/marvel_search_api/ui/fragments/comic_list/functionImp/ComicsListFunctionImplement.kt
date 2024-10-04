package com.eliezer.marvel_search_api.ui.fragments.comic_list.functionImp

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import com.eliezer.marvel_search_api.data.repository.comics.mock.GetComicsRepository
import com.eliezer.marvel_search_api.databinding.FragmentComicsListBinding
import com.eliezer.marvel_search_api.domain.actions.NavigationMainActions
import com.eliezer.marvel_search_api.domain.listener.MyOnScrolledListener
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
) : ComicsListAdapter.ComicHolderListener{
    private var title : String? = null
    private var listIdsFavorite = ArrayList<Int>()
    private val myOnScrolledListener = MyOnScrolledListener { getListComics()}
    private val functionManagerViewModel = FunctionManagerViewModel(viewModel)
    private val functionManagerRecyclerAdapter = FunctionManagerRecyclerAdapter(this)
    private val functionManagerBinding = FunctionManagerBinding(binding)



    fun getListSearchComicsRepository()
    {
        title?.also {
            val comics = comicListFunctionManagerRepository.getComicsRepository.getListRepository(it)
            functionManagerRecyclerAdapter.setComicsList(comics)
        }
    }
    fun getListFavoriteComicsRepository(favoriteId : String)
    {
        val comics = comicListFunctionManagerRepository.getComicsRepository.getListRepository(favoriteId)
        functionManagerRecyclerAdapter.setComicsList(comics)
    }
    fun setAdapter() {
        functionManagerBinding.setAdapter(functionManagerRecyclerAdapter.adapter!!)
        functionManagerBinding.recyclerViewComicsAddScrollListener(myOnScrolledListener)
    }


    override fun onComicItemClickListener(comic: Comic) {
        navigationMainActions.doActionComicsListFragmentToComicDescriptionFragment(comic =comic)
    }

    override fun onImageButtonFavoriteListener(comic:Comic) =
         if(comic.favorite)
             comicListFunctionManagerRepository.insertComic.insertFavoriteComic(comic.id)
        else
             comicListFunctionManagerRepository.deleteComic.deleteFavoriteComic(comic.id)


    fun getMode(arguments: Bundle) = CharactersListFragmentArgs.fromBundle(arguments).argMode
    fun getComicsArg(arguments: Bundle) {
        title = ComicsListFragmentArgs.fromBundle(arguments).argSearchComic
    }


    private fun getListComics() {
        functionManagerBinding.recyclerViewComicsRemoveScrollListener(myOnScrolledListener)
        val comics = comicListFunctionManagerRepository.getComicsRepository.getListRepository(title!!)
        if(comics==null || comics.total > comics.listComics.size)
            searchListComics()
        else if (functionManagerRecyclerAdapter.adapter!!.isListEmpty())
            setListComics(comics)

    }
    private fun searchListComics() {
        functionManagerViewModel.setListComicsObservesVM(owner,::setListComics)
        functionManagerViewModel.searchComicList(title!!)
    }

    private fun setListComics(comics: Comics?) {
        val position = myOnScrolledListener.position
        comics?.apply {
            if (listComics.isNotEmpty())
                functionManagerRecyclerAdapter.adapter?.setComics(listComics)

        }
        functionManagerBinding.resetRecyclerView(myOnScrolledListener)
        functionManagerViewModel.setListComicsNoObservesVM(owner)
        functionManagerBinding.recyclerViewComicsScrollToPosition(position)
    }

    private fun getIdComicsModeSearch(owner: LifecycleOwner) =  functionManagerViewModel.setIdComicsObservesVM(owner,::setListIdFavorite)

    private fun setListIdFavorite(ids: ArrayList<Int>) {
        listIdsFavorite = ids
        functionManagerRecyclerAdapter.adapter?.setFavoriteComics(ids)
        getIdComicsModeSearch(owner)
    }

    fun getIdComicsModeFavorite() =  functionManagerViewModel.setIdComicsObservesVM(owner,::getListComicsByIds)
    private fun getListComicsByIds(ids: ArrayList<Int>) {
        functionManagerViewModel.setListComicsObservesVM(owner,::setListComics)
        functionManagerViewModel.getFavoriteComicsList(ids)
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
        adapter?.setComics(comics?.listComics ?: emptyList())
}
private class FunctionManagerViewModel(
    private val viewModel: ComicsListViewModel)
{
    fun setIdComicsObservesVM(owner: LifecycleOwner, observeComics: ((ArrayList<Int>)->(Unit))) {
        viewModel.favoriteIdComics.observe(owner,observeComics)
    }
    fun setIdComicsNotObservesVM(owner: LifecycleOwner) {
        viewModel.favoriteIdComics.removeObservers(owner)
        viewModel.resetFavoriteIdComics()
    }

    fun setListComicsObservesVM(owner: LifecycleOwner, observe : ((Comics)->(Unit))) {
        viewModel.listComic.observe(owner,observe)
    }
    fun setListComicsNoObservesVM(owner: LifecycleOwner) {
        viewModel.listComic.removeObservers(owner)
        viewModel.resetComics()
    }
    fun getFavoriteComicsList(ids : ArrayList<Int>)
    {
        viewModel.getFavoriteComicsList(ids)
    }

    fun searchComicList(title : String)
    {
        viewModel.searchComicsList(title)
    }
}