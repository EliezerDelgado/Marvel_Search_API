package com.eliezer.marvel_search_api.ui.fragments.character_profile

import android.os.Bundle
import android.text.Editable
import android.view.View
import androidx.fragment.app.viewModels
import com.eliezer.marvel_search_api.R
import com.eliezer.marvel_search_api.core.base.BaseFragment
import com.eliezer.marvel_search_api.data.mappers.mainActivity
import com.eliezer.marvel_search_api.data.repository.comics.mock.GetComicsRepository
import com.eliezer.marvel_search_api.data.repository.comics.mock.SetComicsRepository
import com.eliezer.marvel_search_api.databinding.FragmentCharacterProfileBinding
import com.eliezer.marvel_search_api.domain.listener.MyMenuProvider
import com.eliezer.marvel_search_api.domain.listener.MyTextChangedListener
import com.eliezer.marvel_search_api.ui.fragments.character_profile.functionImp.CharacterProfileFunctionImplement
import com.eliezer.marvel_search_api.ui.fragments.character_profile.viewmodel.CharacterProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CharacterProfileFragment :
    BaseFragment<FragmentCharacterProfileBinding>(
        FragmentCharacterProfileBinding::inflate
    ) {
    private  var funImpl : CharacterProfileFunctionImplement? = null
    private val comicsListViewModel: CharacterProfileViewModel by viewModels()
    @Inject
    lateinit var getComicsRepository: GetComicsRepository

    @Inject
    lateinit var setComicsRepository: SetComicsRepository

    private val myToolbarMenuProvider = MyMenuProvider(R.menu.main_toolbar_menu){ item->
        when(item.itemId)
        {
            R.id.main_toolbar_menu_search -> openMenuSearch()
        }
        true
    }
    private val mySubToolbarMenuProvider = MyMenuProvider(R.menu.main_sub_toolbar_menu){ item->
        when(item.itemId)
        {
            R.id.main_sub_toolbar_menu_search -> closeMenuSearch()
            R.id.main_sub_toolbar_menu_back -> funImpl?.searchWordBack()
            R.id.main_sub_toolbar_menu_forward -> funImpl?.searchWordForward()
        }
        true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity(requireActivity()).setToolbarView(true)
        mainActivity(requireActivity()).setToolbarView(true)
        mainAddMenuProvider()
        implementFunctions()
    }

    private fun implementFunctions() {
        funImpl = CharacterProfileFunctionImplement(
            binding  = binding,
            viewModel = comicsListViewModel,
            getComicsRepository = getComicsRepository,
            setComicsRepository = setComicsRepository,
            owner = this,
            context = requireContext()
        )
        funImpl?.errorListener()
        funImpl?.getIntentExtras(requireArguments())
        funImpl?.setBindingVariable()
        funImpl?.setAdapter()
        funImpl?.getListComics()
    }

    private fun mainAddMenuProvider()
    {
        mainActivity(requireActivity()).getToolBar()?.addMenuProvider(myToolbarMenuProvider)
        mainActivity(requireActivity()).getSubToolBar()?.addMenuProvider(mySubToolbarMenuProvider)
    }

    private fun closeMenuSearch() {
        mainActivity(requireActivity()).getSubToolBarEditText()?.setText("")
        mainActivity(requireActivity()).setSubToolbarView(false)
        funImpl?.returnNormalColor()
    }

    private fun openMenuSearch() {
        mainActivity(requireActivity()).setSubToolbarView(true)
        mainActivity(requireActivity()).getSubToolBarEditText()?.addTextChangedListener(MyTextChangedListener(
        null,
            null,
            afterTextChanged = getTextAfterTextChange()))
    }

    private fun getTextAfterTextChange(): ((p0: Editable?) -> Unit) ={ edit->
        funImpl?.searchText(edit.toString())
    }

    override fun onDestroyView() {
        mainActivity(requireActivity()).getToolBar()?.removeMenuProvider(myToolbarMenuProvider)
        mainActivity(requireActivity()).getSubToolBar()?.removeMenuProvider(mySubToolbarMenuProvider)
        mainActivity(requireActivity()).setToolbarView(false)
        super.onDestroyView()
        funImpl?.stopLoading()
        funImpl?.stopErrorListener()
        funImpl = null
    }
}