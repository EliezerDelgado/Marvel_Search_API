package com.eliezer.marvel_characters.ui.fragments.character_profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.eliezer.marvel_characters.core.base.BaseFragment
import com.eliezer.marvel_characters.databinding.FragmentCharacterProfileBinding
import com.eliezer.marvel_characters.ui.fragments.character_profile.functionImp.CharacterProfileFunctionImplement
import com.eliezer.marvel_characters.ui.fragments.character_profile.viewmodel.CharacterProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterProfileFragment :
    BaseFragment<FragmentCharacterProfileBinding>(
        FragmentCharacterProfileBinding::inflate
    ) {
        private  var funImpl : CharacterProfileFunctionImplement? = null

    private val characterProfileViewModel: CharacterProfileViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        funImpl = CharacterProfileFunctionImplement(binding,characterProfileViewModel,this)
        funImpl?.getIntentExtras(requireArguments())
        funImpl?.setBindingVariable()
        funImpl?.setAdapter()
        funImpl?.searchListComics()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        funImpl = null
    }
}