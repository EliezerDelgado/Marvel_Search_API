package com.eliezer.marvel_characters.ui.fragments.comic_list

import android.os.Bundle
import android.view.View
import com.eliezer.marvel_characters.core.base.BaseFragment
import com.eliezer.marvel_characters.databinding.FragmentComicsListBinding

class ComicsListFragment :
    BaseFragment<FragmentComicsListBinding>(
        FragmentComicsListBinding::inflate
    )
{
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}