package com.eliezer.marvel_search_api.ui.fragments.character_list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.eliezer.marvel_search_api.core.base.BaseFragment
import com.eliezer.marvel_search_api.data.const.FAVORITE_ID
import com.eliezer.marvel_search_api.data.const.SEARCH_ID
import com.eliezer.marvel_search_api.data.mappers.mainActivity
import com.eliezer.marvel_search_api.databinding.FragmentCharactersListBinding
import com.eliezer.marvel_search_api.ui.fragments.character_list.functionImp.CharactersListFunctionImplement
import com.eliezer.marvel_search_api.ui.fragments.character_list.functionImp.function.CharacterListFunctionManagerRepository
import com.eliezer.marvel_search_api.ui.fragments.character_list.viewmodel.CharactersListViewModel
import com.eliezer.marvel_search_api.ui.fragments.favorites.interfaces.FavoriteToolbarButtonsClickAction
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CharactersListFragment :
    BaseFragment<FragmentCharactersListBinding>(
        FragmentCharactersListBinding::inflate
    ), FavoriteToolbarButtonsClickAction
{
    @Inject
    lateinit var characterListFunctionManagerRepository: CharacterListFunctionManagerRepository


    private val characterListViewModel: CharactersListViewModel by viewModels()
    private var funImpl : CharactersListFunctionImplement? = null
    private var mode : String? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity(requireActivity()).setToolbarView(true)
        funImpl = CharactersListFunctionImplement(
            binding,
            mainActivity(requireActivity()).navigationMainActions!!,
            characterListViewModel,
            characterListFunctionManagerRepository,
            this
        )
        mode = funImpl?.getMode(requireArguments())
        funImpl?.setAdapter()
        if(mode == SEARCH_ID) {
            funImpl?.getCharactersArg(requireArguments())
            funImpl?.getListSearchCharactersRepository()
        }
        else if(mode == FAVORITE_ID)
            funImpl?.getListFavoriteCharactersRepository(FAVORITE_ID)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        funImpl = null
    }

    override fun doReload() {
        //funImpl?.getListFavoriteCharactersRepository(FAVORITE_ID)
    }
}