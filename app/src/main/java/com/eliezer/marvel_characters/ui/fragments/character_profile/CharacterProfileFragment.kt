package com.eliezer.marvel_characters.ui.fragments.character_profile

import android.os.Bundle
import android.view.View
import com.eliezer.marvel_characters.core.base.BaseFragment
import com.eliezer.marvel_characters.databinding.FragmentCharacterProfileBinding
import com.eliezer.marvel_characters.ui.fragments.character_profile.functionImp.CharacterProfileFuctionImplement

class CharacterProfileFragment :
    BaseFragment<FragmentCharacterProfileBinding>(
        FragmentCharacterProfileBinding::inflate
    ) {
        private  var funImpl : CharacterProfileFuctionImplement? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        funImpl = CharacterProfileFuctionImplement(binding)
        funImpl?.getIntentExtras(requireArguments())
        funImpl?.setBindingVariable()
        funImpl?.setAdapter()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        funImpl = null
    }
}