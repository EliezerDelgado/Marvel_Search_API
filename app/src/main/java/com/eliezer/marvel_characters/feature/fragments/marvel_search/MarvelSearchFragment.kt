package com.eliezer.marvel_characters.feature.fragments.marvel_search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.eliezer.marvel_characters.core.base.BaseFragment
import com.eliezer.marvel_characters.core.base.BaseViewModel
import com.eliezer.marvel_characters.databinding.FragmentMarvelSearchBinding
import com.eliezer.marvel_characters.feature.activity.MainActivity
import com.eliezer.marvel_characters.feature.fragments.marvel_search.viewmodel.MarvelSearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MarvelSearchFragment : BaseFragment<FragmentMarvelSearchBinding>(
    FragmentMarvelSearchBinding::inflate
) {
    private val searchViewModel: MarvelSearchViewModel by viewModels()
    private var controller : MarvelSearchController? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controller = MarvelSearchController(binding,searchViewModel,(activity as MainActivity).navigationMainActions!!,viewLifecycleOwner)
        controller?.setObservesVM()
        controller?.buttonListener()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        controller = null
    }
    override fun addViewModel(): BaseViewModel {
        return searchViewModel
    }
}