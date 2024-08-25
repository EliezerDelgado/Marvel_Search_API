package com.eliezer.marvel_characters.ui.fragments.comic_list

import android.app.Activity
import android.os.Bundle
import android.view.View
import com.eliezer.marvel_characters.core.base.BaseFragment
import com.eliezer.marvel_characters.core.base.BaseViewModel
import com.eliezer.marvel_characters.data.mappers.mainActivity
import com.eliezer.marvel_characters.data.repository.comics.mock.GetComicsRepository
import com.eliezer.marvel_characters.databinding.FragmentComicsListBinding
import com.eliezer.marvel_characters.ui.activity.MainActivity
import com.eliezer.marvel_characters.ui.fragments.comic_list.functionImp.ComicsListFunctionImplement
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ComicsListFragment :
    BaseFragment<FragmentComicsListBinding>(
        FragmentComicsListBinding::inflate
    )
{
    private var funImpl : ComicsListFunctionImplement? = null

    @Inject
    lateinit var getCharactersRepository: GetComicsRepository
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        funImpl = ComicsListFunctionImplement(binding,mainActivity(requireActivity()).navigationMainActions!!,getCharactersRepository)

        funImpl?.getIntentExtras(requireArguments())
        funImpl?.setAdapter()
        funImpl?.getListComicsRepository()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        funImpl = null
    }
}