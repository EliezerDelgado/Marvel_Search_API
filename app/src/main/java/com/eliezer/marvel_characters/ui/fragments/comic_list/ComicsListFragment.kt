package com.eliezer.marvel_characters.ui.fragments.comic_list

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.eliezer.marvel_characters.core.base.BaseFragment
import com.eliezer.marvel_characters.core.base.BaseViewModel
import com.eliezer.marvel_characters.data.mappers.mainActivity
import com.eliezer.marvel_characters.data.repository.comics.mock.GetComicsRepository
import com.eliezer.marvel_characters.databinding.FragmentComicsListBinding
import com.eliezer.marvel_characters.ui.activity.MainActivity
import com.eliezer.marvel_characters.ui.fragments.character_list.viewmodel.CharactersListViewModel
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
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        funImpl = ComicsListFunctionImplement(
            binding,
            mainActivity(requireActivity()).navigationMainActions!!,
            comicsListViewModel,
            getComicsRepository,
            this
        )
        funImpl?.getIntentExtras(requireArguments())
        funImpl?.setAdapter()
        funImpl?.getListComicsRepository()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        funImpl = null
    }
}