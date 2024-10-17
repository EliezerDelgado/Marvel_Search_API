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
        funImpl = ComicsListFunctionImplement(
            binding,
            comicsListViewModel,
            mainActivity(requireActivity()).navigationMainActions!!,
            comicListFunctionManagerRepository,
            this
        )
        mode = funImpl?.getMode(requireArguments())
        funImpl?.setAdapter()
        if(mode == SEARCH_ID) {
            funImpl?.setMyOnScrolledListener()
            funImpl?.getComicsArg(requireArguments())
            funImpl?.getListSearchComicsRepository()
        }else if (mode == FAVORITE_ID) {
            funImpl?.disableMyOnScrolledListener()
            funImpl?.getListComicsModeFavorite()
        }
    }

    override fun doReload() {
        funImpl?.getListComicsModeFavorite()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        funImpl = null
    }
}