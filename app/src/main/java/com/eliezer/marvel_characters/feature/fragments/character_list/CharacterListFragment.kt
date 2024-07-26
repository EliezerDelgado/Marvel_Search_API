package com.eliezer.marvel_characters.feature.fragments.character_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.eliezer.marvel_characters.core.base.BaseFragment
import com.eliezer.marvel_characters.core.base.BaseViewModel
import com.eliezer.marvel_characters.databinding.FragmentCharacterListBinding
import com.eliezer.marvel_characters.feature.fragments.character_list.controller.CharactersListController

class CharacterListFragment :
    BaseFragment<FragmentCharacterListBinding>(
        FragmentCharacterListBinding::inflate
    )
{
    private var controller : CharactersListController? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controller = CharactersListController(binding,viewLifecycleOwner)
        controller?.setAdapter()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        controller = null
    }
}