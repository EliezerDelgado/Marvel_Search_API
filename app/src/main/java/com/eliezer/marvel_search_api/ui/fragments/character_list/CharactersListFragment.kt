package com.eliezer.marvel_search_api.ui.fragments.character_list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.eliezer.marvel_search_api.core.base.BaseFragment
import com.eliezer.marvel_search_api.data.const.FAVORITE_ID
import com.eliezer.marvel_search_api.data.const.SEARCH_ID
import com.eliezer.marvel_search_api.data.mappers.mainActivity
import com.eliezer.marvel_search_api.databinding.FragmentCharactersListBinding
import com.eliezer.marvel_search_api.domain.function.FunctionManagerCharacterRepository
import com.eliezer.marvel_search_api.ui.fragments.character_list.functionImp.CharactersListFunctionImplement
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
    lateinit var functionManagerCharacterRepository: FunctionManagerCharacterRepository
    private val characterListViewModel: CharactersListViewModel by viewModels()
    private var funImpl : CharactersListFunctionImplement? = null
    private var mode : String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        implementFunctions()
    }

    private fun implementFunctions() {
        funImpl = CharactersListFunctionImplement(
            binding = binding,
            viewModel = characterListViewModel,
            navigationMainActions = mainActivity(requireActivity()).navigationMainActions!!,
            functionManagerCharacterRepository = functionManagerCharacterRepository,
            owner = this,
            context = requireContext()
        )
        funImpl?.errorListener()
        mode = funImpl?.getMode(requireArguments())
        funImpl?.setAdapter()
        checkMode()
    }

    private fun checkMode() {
        if(mode == SEARCH_ID)
            functionsSearchMode()
        else if(mode == FAVORITE_ID)
            functionsFavoriteMode()
    }

    private fun functionsFavoriteMode() {
        funImpl?.disableMyOnScrolledListener()
        funImpl?.getListCharactersModeFavorite()
    }

    private fun functionsSearchMode() {
        mainActivity(requireActivity()).setToolbarView(false)
        funImpl?.setMyOnScrolledListener()
        funImpl?.getCharactersArg(requireArguments())
        funImpl?.getListSearchCharactersRepository()

    }

    override fun doReload() {
        funImpl?.getListCharactersModeFavorite()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        funImpl?.stopLoading()
        funImpl?.stopErrorListener()
        funImpl = null
    }

}