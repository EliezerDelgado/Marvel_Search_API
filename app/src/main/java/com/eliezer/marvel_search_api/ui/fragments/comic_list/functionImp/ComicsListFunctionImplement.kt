package com.eliezer.marvel_search_api.ui.fragments.comic_list.functionImp

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.annotation.StringRes
import androidx.lifecycle.LifecycleOwner
import com.eliezer.marvel_search_api.R
import com.eliezer.marvel_search_api.data.expand.isInternetConnected
import com.eliezer.marvel_search_api.databinding.FragmentComicsListBinding
import com.eliezer.marvel_search_api.domain.actions.NavigationMainActions
import com.eliezer.marvel_search_api.domain.alert_dialogs.errorDialog
import com.eliezer.marvel_search_api.domain.alert_dialogs.warningDialog
import com.eliezer.marvel_search_api.domain.function.FunctionLoadingManager
import com.eliezer.marvel_search_api.domain.function.FunctionManagerComicRepository
import com.eliezer.marvel_search_api.domain.listener.MyOnScrolledListener
import com.eliezer.marvel_search_api.domain.local_property.LocalAccount
import com.eliezer.marvel_search_api.models.dataclass.Comic
import com.eliezer.marvel_search_api.models.dataclass.Comics
import com.eliezer.marvel_search_api.ui.fragments.character_list.CharactersListFragmentArgs
import com.eliezer.marvel_search_api.ui.fragments.comic_list.ComicsListFragmentArgs
import com.eliezer.marvel_search_api.ui.fragments.comic_list.adapter.ComicsListAdapter
import com.eliezer.marvel_search_api.ui.fragments.comic_list.viewmodel.ComicsListViewModel


class ComicsListFunctionImplement (
    binding: FragmentComicsListBinding,
    viewModel: ComicsListViewModel,
    private val navigationMainActions: NavigationMainActions,
    private val functionManagerComicRepository: FunctionManagerComicRepository,
    private val owner : LifecycleOwner,
    private val context: Context
) : ComicsListAdapter.ComicHolderListener {

    private var searchComic: String? = null
    private var myOnScrolledListener : MyOnScrolledListener? = MyOnScrolledListener { getListComics() }
    private val functionManagerViewModel = FunctionManagerViewModel(viewModel)
    private val functionManagerRecyclerAdapter = FunctionManagerRecyclerAdapter(this)
    private val functionManagerBinding = FunctionManagerBinding(binding)
    private val functionLoadingManager = FunctionLoadingManager(context)

    fun getListSearchComicsRepository() {
        searchComic?.also {
            val comics = functionManagerComicRepository.getListRepository(it)
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
        searchComic?.also {
            navigationMainActions.doActionComicsListFragmentToComicDescriptionFragment(comic = comic)
        } ?:  navigationMainActions.doActionFavoritesFragmentToComicDescriptionFragment(comic = comic)
    }

    override fun onImageButtonFavoriteListener(comic: Comic) {
        if(context.isInternetConnected) {
            LocalAccount.userAccount.value?.run {
                if (comic.favorite)
                    insertFavoriteComic(comic)
                else
                    deleteFavoriteComic(comic)
                true
            } ?: also {
                showWarning(R.string.warning_user_should_log_in)
            }
        }
        else
            showError(R.string.error_no_internet)
    }

    private fun deleteFavoriteComic(comic: Comic) {
        functionManagerComicRepository.deleteFavoriteComicFireStore(comic.id)
        functionManagerComicRepository.deleteFavoriteComicInDatabase(comic)
        functionManagerRecyclerAdapter.adapter!!.setNoFavoriteComic(comic)
    }

    private fun insertFavoriteComic(comic: Comic) {
        functionManagerComicRepository.insertFavoriteComicFireStore(comic.id)
        functionManagerComicRepository.insertFavoriteComicInDatabase(comic)
        functionManagerRecyclerAdapter.adapter!!.setFavoriteComic(comic)
    }


    fun getMode(arguments: Bundle) = CharactersListFragmentArgs.fromBundle(arguments).argMode
    fun getComicsArg(arguments: Bundle) {
        searchComic = ComicsListFragmentArgs.fromBundle(arguments).argSearchComic
    }


    private fun getListComics() {
        myOnScrolledListener?.also { functionManagerBinding.recyclerViewComicsRemoveScrollListener(it)}
        val comics = searchComic?.let { functionManagerComicRepository.getListRepository(it)}
        comics.also {
            if (it == null || it.total > it.listComics.size)
                searchListComics()
            else if (functionManagerRecyclerAdapter.adapter!!.isListEmpty())
                setListComics(it)
        }
    }

    private fun searchListComics() {
        functionLoadingManager.showLoadingDialog()
        functionManagerViewModel.setListComicsObservesVM(owner, ::setListComics)
        searchComic?.also {
            functionManagerViewModel.searchComicList(it)
        }
    }

    private fun setListComics(comics: Comics?) {
        val position = myOnScrolledListener?.positionBefore
        comics?.also {
            functionManagerComicRepository.setListTmpCharacters(it)
            if (it.listComics.isNotEmpty())
                functionManagerRecyclerAdapter.adapter?.addComics(it.listComics)

        }
        myOnScrolledListener?.also { functionManagerBinding.resetRecyclerView(it)}
        functionManagerViewModel.setListComicsNoObservesVM(owner)
        position?.also { functionManagerBinding.recyclerViewComicsScrollToPosition(it)}
        if(functionLoadingManager.isShowing())
            functionLoadingManager.stopLoading()
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


    fun errorListener() {
        internalErrorListener()
    }

    private fun internalErrorListener() =
        functionManagerViewModel.apply {
            setObservesCharactersViewModelError(owner, ::createErrorLog)
        }


    private fun createErrorLog(throwable: Throwable) =
        Log.e("***",throwable.message,throwable)


    fun stopErrorListener() =
        functionManagerViewModel.setNoObservesCharactersViewModelError(owner)


    private fun showError(@StringRes idError: Int) {
        errorDialog(context,context.resources.getString(idError)).show()
    }
    private fun showWarning(@StringRes idError: Int) {
        warningDialog(context,context.resources.getString(idError)).show()
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
    private val viewModel: ComicsListViewModel
)
{
    fun setListComicsObservesVM(owner: LifecycleOwner, observe : ((Comics)->(Unit))) {
        viewModel.comicsViewModel.comics.observe(owner,observe)
    }
    fun setListComicsNoObservesVM(owner: LifecycleOwner) {
        viewModel.comicsViewModel.comics.removeObservers(owner)
        viewModel.comicsViewModel.resetComics()
    }
    fun getFavoriteComicsList()
    {
        viewModel.comicsViewModel.getFavoriteComicsList()
    }

    fun searchComicList(title : String)
    {
        viewModel.comicsViewModel.searchComicsList(title)
    }

    fun setObservesCharactersViewModelError(owner: LifecycleOwner,observe: ((Throwable)->(Unit))) =
        viewModel.comicsViewModel.error.observe(owner,observe)


    fun setNoObservesCharactersViewModelError(owner: LifecycleOwner) =
        viewModel.comicsViewModel.error.removeObservers(owner)
}