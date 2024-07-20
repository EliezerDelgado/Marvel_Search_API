package com.eliezer.marvel_characters.feature.fragments.character_list.controller

import com.eliezer.marvel_characters.databinding.FragmentCharacterListBinding
import com.eliezer.marvel_characters.feature.fragments.character_list.adapter.CharacterListAdapter
import com.eliezer.marvel_characters.models.responses.Character

class CharactersListController(val binding: FragmentCharacterListBinding) {
    private var adapter : CharacterListAdapter? = null
    private var items : ArrayList<Character>? = null
    fun navigateBack()
    {
        //fragment.baseActivity?.navigationMainActions?.navigateUp()
    }
    fun setAdapter()
    {
        adapter = CharacterListAdapter(items!!)
        binding?.characterListRecyclerView?.setHasFixedSize(true)
        binding?.characterListRecyclerView?.adapter = adapter
    }
}