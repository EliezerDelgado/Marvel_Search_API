package com.eliezer.marvel_characters.feature.fragments.marvel_search

import android.os.Bundle
import android.view.View
import com.eliezer.marvel_characters.core.base.BaseFragment
import com.eliezer.marvel_characters.databinding.FragmentMarvelSearchBinding
import com.eliezer.marvel_characters.feature.activity.MainActivity

class MarvelSearchFragment : BaseFragment<MainActivity, FragmentMarvelSearchBinding>(
    FragmentMarvelSearchBinding::inflate
) {
    private var controller : MarvelSearchController? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controller = MarvelSearchController(binding!!,baseActivity!!.navigationMainActions!!)
        controller?.buttonListener()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        controller = null
    }
}