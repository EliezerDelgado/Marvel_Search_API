package com.eliezer.marvel_search_api.ui.fragments.marvel_search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.eliezer.marvel_search_api.core.base.BaseFragment
import com.eliezer.marvel_search_api.core.base.BaseViewModel
import com.eliezer.marvel_search_api.data.mappers.mainActivity
import com.eliezer.marvel_search_api.databinding.FragmentMarvelSearchBinding
import com.eliezer.marvel_search_api.ui.fragments.marvel_search.functionImp.MarvelSearchFunctionImplement
import com.eliezer.marvel_search_api.ui.fragments.marvel_search.viewmodel.MarvelSearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MarvelSearchFragment : BaseFragment<FragmentMarvelSearchBinding>(
    FragmentMarvelSearchBinding::inflate
) {
    private val searchViewModel: MarvelSearchViewModel by viewModels()
    private var funImpl : MarvelSearchFunctionImplement? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity(requireActivity()).setToolbarView(false)
        funImpl = MarvelSearchFunctionImplement(binding,searchViewModel, mainActivity(requireActivity()).navigationMainActions!!,viewLifecycleOwner)
        funImpl?.resetLists()
        funImpl?.buttonListener()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        funImpl = null
    }
    override fun addViewModel(): BaseViewModel {
        return searchViewModel
    }
}