package com.eliezer.marvel_characters.feature.comic_list

import android.os.Bundle
import android.view.View
import com.eliezer.marvel_characters.core.base.BaseFragment
import com.eliezer.marvel_characters.databinding.FragmentComicsListBinding
import com.eliezer.marvel_characters.feature.MainActivity

class ComicsListFragment :
    BaseFragment<MainActivity, FragmentComicsListBinding>(
        FragmentComicsListBinding::inflate
    )
{
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}