package com.eliezer.marvel_characters.ui.fragments.character_list

import android.content.Intent.getIntent
import android.os.Bundle
import android.view.View
import androidx.navigation.NavHost
import androidx.navigation.fragment.NavHostFragment
import com.eliezer.marvel_characters.R
import com.eliezer.marvel_characters.core.base.BaseFragment
import com.eliezer.marvel_characters.data.repository.characters.mock.GetCharactersRepository
import com.eliezer.marvel_characters.databinding.FragmentCharacterListBinding
import com.eliezer.marvel_characters.ui.activity.MainActivity
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
        funImpl = CharactersListFunctionImplement(binding,(activity as MainActivity).navigationMainActions!! ,getCharactersRepository)
        funImpl?.setAdapter()
        funImpl?.getListCharactersRepository()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        funImpl = null
    }
}