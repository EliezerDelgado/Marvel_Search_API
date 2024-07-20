package com.eliezer.marvel_characters.feature.fragments.comic_list

import android.os.Bundle
import android.view.View
import com.eliezer.marvel_characters.core.base.BaseFragment
import com.eliezer.marvel_characters.databinding.FragmentComicsListBinding
import com.eliezer.marvel_characters.feature.activity.MainActivity

class ComicsListFragment :
    BaseFragment<MainActivity, FragmentComicsListBinding>(
        FragmentComicsListBinding::inflate
    )
{
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}