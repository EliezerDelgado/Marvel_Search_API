package com.eliezer.marvel_characters.feature.fragments.character_list

import android.os.Bundle
import android.view.View
import com.eliezer.marvel_characters.core.base.BaseFragment
import com.eliezer.marvel_characters.databinding.FragmentCharacterListBinding
import com.eliezer.marvel_characters.feature.activity.MainActivity
import com.eliezer.marvel_characters.feature.fragments.character_list.controller.CharactersListController

class CharacterListFragment :
    BaseFragment<MainActivity, FragmentCharacterListBinding>(
        FragmentCharacterListBinding::inflate
    )
{
    private var controller : CharactersListController? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controller = CharactersListController(binding!!)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        controller = null
    }
}