package com.eliezer.marvel_characters.ui.fragments.character_profile

import android.os.Bundle
import android.text.Editable
import android.view.View
import androidx.fragment.app.viewModels
import com.eliezer.marvel_characters.R
import com.eliezer.marvel_characters.core.base.BaseFragment
import com.eliezer.marvel_characters.data.mappers.mainActivity
import com.eliezer.marvel_characters.data.repository.comics.mock.GetComicsRepository
import com.eliezer.marvel_characters.databinding.FragmentCharacterProfileBinding
import com.eliezer.marvel_characters.domain.listener.MyMenuProvider
import com.eliezer.marvel_characters.domain.listener.MyTextChangedListener
import com.eliezer.marvel_characters.ui.fragments.character_profile.functionImp.CharacterProfileFunctionImplement
import com.eliezer.marvel_characters.ui.fragments.character_profile.viewmodel.CharacterProfileViewModel
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
        funImpl = CharacterProfileFunctionImplement(binding,comicsListViewModel,getComicsRepository,this)
        mainAddMenuProvider()
        mainActivity(requireActivity()).setToolbarView(true)
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
        mainActivity(requireActivity()).setSubToolbarView(false)
    }

    private fun openMenuSearch() {
        mainActivity(requireActivity()).setSubToolbarView(true)
        mainActivity(requireActivity()).getSubToolBarEditText()?.addTextChangedListener(MyTextChangedListener(
        null,
            null,
            afterTextChanged = getTextAfterTextChange()))
    }

    private fun getTextAfterTextChange(): ((p0: Editable?) -> Unit) ={ edit->
        funImpl?.searchWord(edit.toString())
    }

    override fun onDestroyView() {
        mainActivity(requireActivity()).getToolBar()?.removeMenuProvider(myToolbarMenuProvider)
        mainActivity(requireActivity()).getSubToolBar()?.removeMenuProvider(mySubToolbarMenuProvider)
        super.onDestroyView()
        funImpl = null
    }
}