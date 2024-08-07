package com.eliezer.marvel_characters.ui.fragments.character_list

import android.os.Bundle
import android.view.View
import com.eliezer.marvel_characters.core.base.BaseFragment
import com.eliezer.marvel_characters.data.repository.characters.mock.GetCharactersRepository
import com.eliezer.marvel_characters.databinding.FragmentCharacterListBinding
import com.eliezer.marvel_characters.ui.fragments.character_list.functionImp.CharactersListFunctionImplement
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CharacterListFragment :
    BaseFragment<FragmentCharacterListBinding>(
        FragmentCharacterListBinding::inflate
    )
{
    @Inject
    lateinit var getCharactersRepository: GetCharactersRepository
    private var funImpl : CharactersListFunctionImplement? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        funImpl = CharactersListFunctionImplement(binding,getCharactersRepository)
        funImpl?.setAdapter()
        funImpl?.getListCharactersRepository()
    }



    override fun onDestroyView() {
        super.onDestroyView()
        funImpl = null
    }
}