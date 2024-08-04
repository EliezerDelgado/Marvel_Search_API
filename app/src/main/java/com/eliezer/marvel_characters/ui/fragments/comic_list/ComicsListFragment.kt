package com.eliezer.marvel_characters.ui.fragments.comic_list

import android.os.Bundle
import android.view.View
import com.eliezer.marvel_characters.core.base.BaseFragment
import com.eliezer.marvel_characters.databinding.FragmentComicsListBinding
import com.eliezer.marvel_characters.ui.fragments.character_list.functionImp.CharactersListFunctionImplement
import com.eliezer.marvel_characters.ui.fragments.comic_list.functionImp.ComicsListFunctionImplement

class ComicsListFragment :
    BaseFragment<FragmentComicsListBinding>(
        FragmentComicsListBinding::inflate
    )
{
    private var funImpl : ComicsListFunctionImplement? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        funImpl = ComicsListFunctionImplement(binding)
        funImpl?.setAdapter()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        funImpl = null
    }
}