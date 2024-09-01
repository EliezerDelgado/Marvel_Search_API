package com.eliezer.marvel_characters.ui.fragments.comic_list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.eliezer.marvel_characters.core.base.BaseFragment
import com.eliezer.marvel_characters.data.const.FAVORITE_ID
import com.eliezer.marvel_characters.data.const.SEARCH_ID
import com.eliezer.marvel_characters.data.mappers.mainActivity
import com.eliezer.marvel_characters.data.repository.comics.mock.GetComicsRepository
import com.eliezer.marvel_characters.databinding.FragmentComicsListBinding
import com.eliezer.marvel_characters.ui.fragments.comic_list.functionImp.ComicsListFunctionImplement
import com.eliezer.marvel_characters.ui.fragments.comic_list.viewmodel.ComicsListViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ComicsListFragment :
    BaseFragment<FragmentComicsListBinding>(
        FragmentComicsListBinding::inflate
    )
{

    @Inject
    lateinit var getComicsRepository: GetComicsRepository
    private var funImpl : ComicsListFunctionImplement? = null
    private val comicsListViewModel: ComicsListViewModel by viewModels()
    private var mode : String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        funImpl = ComicsListFunctionImplement(
            binding,
            mainActivity(requireActivity()).navigationMainActions!!,
            comicsListViewModel,
            getComicsRepository,
            this
        )
        mode = funImpl?.getMode(requireArguments())
        funImpl?.setAdapter()
        if(mode == SEARCH_ID) {
            funImpl?.getComicsArg(requireArguments())
            funImpl?.getListSearchComicsRepository()
        }else if (mode == FAVORITE_ID)
            funImpl?.getListFavoriteComicsRepository(FAVORITE_ID)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        funImpl = null
    }
}