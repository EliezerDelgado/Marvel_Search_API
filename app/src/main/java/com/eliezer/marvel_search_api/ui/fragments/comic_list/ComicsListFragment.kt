package com.eliezer.marvel_search_api.ui.fragments.comic_list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.eliezer.marvel_search_api.core.base.BaseFragment
import com.eliezer.marvel_search_api.data.const.FAVORITE_ID
import com.eliezer.marvel_search_api.data.const.SEARCH_ID
import com.eliezer.marvel_search_api.data.mappers.mainActivity
import com.eliezer.marvel_search_api.databinding.FragmentComicsListBinding
import com.eliezer.marvel_search_api.ui.fragments.comic_list.functionImp.function.ComicListFunctionManagerRepository
import com.eliezer.marvel_search_api.ui.fragments.comic_list.functionImp.ComicsListFunctionImplement
import com.eliezer.marvel_search_api.ui.fragments.comic_list.viewmodel.ComicsListViewModel
import com.eliezer.marvel_search_api.ui.fragments.favorites.interfaces.FavoriteToolbarButtonsClickAction
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ComicsListFragment :
    BaseFragment<FragmentComicsListBinding>(
        FragmentComicsListBinding::inflate
    ), FavoriteToolbarButtonsClickAction
{

    @Inject
    lateinit var comicListFunctionManagerRepository: ComicListFunctionManagerRepository
    private var funImpl : ComicsListFunctionImplement? = null
    private val comicsListViewModel: ComicsListViewModel by viewModels()
    private var mode : String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        implementFunctions()
    }

    private fun implementFunctions() {
        funImpl = ComicsListFunctionImplement(
            binding = binding,
            viewModel = comicsListViewModel,
            navigationMainActions = mainActivity(requireActivity()).navigationMainActions!!,
            comicListFunctionManagerRepository = comicListFunctionManagerRepository,
            owner = this,
            context = requireContext()
        )
        funImpl?.errorListener()
        mode = funImpl?.getMode(requireArguments())
        funImpl?.setAdapter()
        checkMode()
    }

    private fun checkMode() {
        if(mode == SEARCH_ID)
            functionsSearchMode()
        else if(mode == FAVORITE_ID)
            functionsFavoriteMode()
    }

    private fun functionsFavoriteMode() {
        funImpl?.disableMyOnScrolledListener()
        funImpl?.getListComicsModeFavorite()
    }

    private fun functionsSearchMode() {
        funImpl?.setMyOnScrolledListener()
        funImpl?.getComicsArg(requireArguments())
        funImpl?.getListSearchComicsRepository()
    }

    override fun doReload() {
        funImpl?.getListComicsModeFavorite()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        funImpl?.stopErrorListener()
        funImpl = null
    }
}