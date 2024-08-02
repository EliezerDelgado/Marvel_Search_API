package com.eliezer.marvel_characters.ui.fragments.marvel_search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.eliezer.marvel_characters.core.base.BaseFragment
import com.eliezer.marvel_characters.core.base.BaseViewModel
import com.eliezer.marvel_characters.databinding.FragmentMarvelSearchBinding
import com.eliezer.marvel_characters.ui.activity.MainActivity
import com.eliezer.marvel_characters.ui.fragments.marvel_search.viewmodel.MarvelSearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MarvelSearchFragment : BaseFragment<FragmentMarvelSearchBinding>(
    FragmentMarvelSearchBinding::inflate
) {
    private val searchViewModel: MarvelSearchViewModel by viewModels()
    private var funImpl : MarvelSearchFunctionImplement? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        funImpl = MarvelSearchFunctionImplement(binding,searchViewModel,(activity as MainActivity).navigationMainActions!!,viewLifecycleOwner)
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