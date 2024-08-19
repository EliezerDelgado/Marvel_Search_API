package com.eliezer.marvel_characters.ui.fragments.comic_description

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.eliezer.marvel_characters.core.base.BaseFragment
import com.eliezer.marvel_characters.databinding.FragmentComicDescriptionBinding
import com.eliezer.marvel_characters.ui.fragments.comic_description.functionImp.ComicDescriptionFunctionImplement
import com.eliezer.marvel_characters.ui.fragments.comic_description.viewmodel.ComicDescriptionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ComicDescriptionFragment :
    BaseFragment<FragmentComicDescriptionBinding>(
        FragmentComicDescriptionBinding::inflate
    ) {
    private  var funImpl : ComicDescriptionFunctionImplement? = null
    private val comicDescriptionViewModel: ComicDescriptionViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        funImpl = ComicDescriptionFunctionImplement(binding,comicDescriptionViewModel,this)
        funImpl?.getIntentExtras(requireArguments())
        funImpl?.setBindingVariable()
        funImpl?.setAdapter()
        funImpl?.searchListCharacters()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        funImpl = null
    }
}