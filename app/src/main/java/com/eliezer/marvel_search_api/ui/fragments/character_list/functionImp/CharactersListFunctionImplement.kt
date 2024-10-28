package com.eliezer.marvel_search_api.ui.fragments.character_list.functionImp

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.annotation.StringRes
import androidx.lifecycle.LifecycleOwner
import com.eliezer.marvel_search_api.R
import com.eliezer.marvel_search_api.data.repository.characters.mock.SetCharactersRepository
import com.eliezer.marvel_search_api.databinding.FragmentCharactersListBinding
import com.eliezer.marvel_search_api.domain.actions.NavigationMainActions
import com.eliezer.marvel_search_api.domain.alert_dialogs.errorDialog
import com.eliezer.marvel_search_api.domain.alert_dialogs.warningDialog
import com.eliezer.marvel_search_api.domain.function.FunctionLoadingManager
import com.eliezer.marvel_search_api.domain.listener.MyOnScrolledListener
import com.eliezer.marvel_search_api.domain.local_property.LocalAccount
import com.eliezer.marvel_search_api.models.dataclass.Character
import com.eliezer.marvel_search_api.models.dataclass.Characters
import com.eliezer.marvel_search_api.models.dataclass.Comics
import com.eliezer.marvel_search_api.ui.fragments.character_list.CharactersListFragmentArgs
import com.eliezer.marvel_search_api.ui.fragments.character_list.adapter.CharactersListAdapter
import com.eliezer.marvel_search_api.ui.fragments.character_list.functionImp.function.CharacterListFunctionManagerRepository
import com.eliezer.marvel_search_api.ui.fragments.character_list.viewmodel.CharactersListViewModel

class CharactersListFunctionImplement(
    binding: FragmentCharactersListBinding,
    viewModel: CharactersListViewModel,
    private val navigationMainActions: NavigationMainActions,
    private val characterListFunctionManagerRepository: CharacterListFunctionManagerRepository,
    private val owner : LifecycleOwner,
    private val context: Context
) : CharactersListAdapter.CharacterHolderListener{

    private var searchCharacter : String? = null
    private var myOnScrolledListener : MyOnScrolledListener?  = MyOnScrolledListener { getListCharacters()}
    private val functionManagerViewModel = FunctionManagerViewModel(viewModel)
    private val functionManagerRecyclerAdapter = FunctionManagerRecyclerAdapter(this)
    private val functionManagerBinding = FunctionManagerBinding(binding)
    private val functionLoadingManager = FunctionLoadingManager(context)

    fun getListSearchCharactersRepository()
    {
        searchCharacter?.also {
            val characters = characterListFunctionManagerRepository.getListRepository(it)
            functionManagerRecyclerAdapter.setCharactersList(characters)
            getIdCharactersModeSearch(owner)
        }
    }
    fun disableMyOnScrolledListener()
    {
        myOnScrolledListener = null
    }
    fun setAdapter() {
        functionManagerBinding.setAdapter(functionManagerRecyclerAdapter.adapter!!)
    }
    fun setMyOnScrolledListener()
    {
        myOnScrolledListener!!.also { functionManagerBinding.recyclerViewCharactersAddScrollListener(it)}
    }

    override fun onCharacterItemClickListener(character: Character) {
        searchCharacter?.also {
            navigationMainActions.doActionCharacterListFragmentToCharacterProfileFragment(character = character)
        } ?:  navigationMainActions.doActionFavoritesFragmentToCharacterProfileFragment(character = character)
    }

    override fun onImageButtonFavoriteListener(character: Character) {
        LocalAccount.userAccount.value?.run {
            if (character.favorite) {
                characterListFunctionManagerRepository.insertFavoriteCharacterFireStore(character.id)
                characterListFunctionManagerRepository.insertFavoriteCharacterInDatabase(character)
                functionManagerRecyclerAdapter.adapter!!.setFavoriteCharacter(character)
            }
            else {
                characterListFunctionManagerRepository.deleteFavoriteCharacterFireStore(character.id)
                characterListFunctionManagerRepository.deleteFavoriteCharacterInDatabase(character)
                functionManagerRecyclerAdapter.adapter!!.setNoFavoriteCharacter(character)
            }
            true
        } ?:  also{
            showWarning(R.string.warning_user_should_log_in)
        }
    }


    fun getMode(arguments: Bundle) = CharactersListFragmentArgs.fromBundle(arguments).argMode

    fun getCharactersArg(arguments: Bundle) {
        searchCharacter = CharactersListFragmentArgs.fromBundle(arguments).argSearchCharacter
    }

    private fun getListCharacters() {
        myOnScrolledListener?.also { functionManagerBinding.recyclerViewCharactersRemoveScrollListener(it)}
        val characters = searchCharacter?.let { characterListFunctionManagerRepository.getListRepository(searchCharacter!!)}
        characters.also {
            if (it == null || it.total > it.listCharacters.size)
                searchListCharacters()
            else if (functionManagerRecyclerAdapter.adapter!!.isListEmpty())
                setListCharacters(it)
        }
    }
    private fun searchListCharacters() {
        functionLoadingManager.showLoadingDialog()
        functionManagerViewModel.setListCharactersObservesVM(owner, ::setListCharacters)
        searchCharacter?.also { functionManagerViewModel.searchCharacterList(it)}
    }

    private fun setListCharacters(characters: Characters?) {
        val position = myOnScrolledListener?.position
        characters?.also {
            characterListFunctionManagerRepository.setListTmpCharacters(it)
            if (it.listCharacters.isNotEmpty())
                functionManagerRecyclerAdapter.adapter?.addCharacters(it.listCharacters)
        }
        myOnScrolledListener?.also { functionManagerBinding.resetRecyclerView(it)}
        functionManagerViewModel.setListCharactersNoObservesVM(owner)
        position?.also { functionManagerBinding.recyclerViewCharactersScrollToPosition(it)}
        if(functionLoadingManager.isShowing())
            functionLoadingManager.stopLoading()
    }

    private fun setFavoriteListCharacters(characters: Characters?) {
        functionManagerViewModel.setListCharactersNoObservesVM(owner)
        setCharacterFavorite(characters)
        characters?.apply {
            if (listCharacters.isNotEmpty())
                functionManagerRecyclerAdapter.adapter?.setCharacters(listCharacters)
            else
                functionManagerRecyclerAdapter.adapter?.clearCharacters()
        }
        functionManagerViewModel.setListCharactersNoObservesVM(owner)
    }

    private fun setCharacterFavorite(characters: Characters?) {
        characters?.listCharacters?.forEach {
            it.favorite = true
        }
    }

    private fun getIdCharactersModeSearch(owner: LifecycleOwner) {
        functionManagerViewModel.setListCharactersObservesVM(owner,::setIdsCharacters)
        functionManagerViewModel.getFavoriteCharactersList()
    }

    private fun setIdsCharacters(characters: Characters) {
        val ids = ArrayList<Int>()
        characters.listCharacters.forEach {
            ids.add(it.id)
        }
        functionManagerRecyclerAdapter.adapter?.setIdsFavoriteCharacters(ids)
    }

    fun getListCharactersModeFavorite()
    {
        functionManagerViewModel.setListCharactersObservesVM(owner,::setFavoriteListCharacters)
        functionManagerViewModel.getFavoriteCharactersList()
    }

    fun errorListener() {
        internalErrorListener()
        errorsForUserListener()
    }

    private fun errorsForUserListener() {
        //TODO("Not yet implemented")
    }

    private fun internalErrorListener() =
        functionManagerViewModel.apply {
            setObservesCharactersViewModelError(owner, ::createErrorLog)
        }

    private fun createErrorLog(throwable: Throwable) =
        Log.e("***",throwable.message,throwable)


    fun stopErrorListener() =
        functionManagerViewModel.setNoObservesCharactersViewModelError(owner)


    private fun showError(@StringRes idError: Int) {
        errorDialog(context,context.resources.getString(idError)).show()
    }
    private fun showWarning(@StringRes idError: Int) {
        warningDialog(context,context.resources.getString(idError)).show()
    }

}

private class FunctionManagerBinding(
    private val binding: FragmentCharactersListBinding,
)
{
    fun setAdapter(adapter: CharactersListAdapter) {
        binding.charactersListRecyclerView.setHasFixedSize(true)
        binding.charactersListRecyclerView.adapter = adapter
    }
    fun recyclerViewCharactersAddScrollListener(myOnScrolledListener: MyOnScrolledListener) =
        binding.charactersListRecyclerView.addOnScrollListener(myOnScrolledListener)

    fun recyclerViewCharactersRemoveScrollListener(myOnScrolledListener: MyOnScrolledListener) =
        binding.charactersListRecyclerView.removeOnScrollListener(myOnScrolledListener)

    fun recyclerViewCharactersScrollToPosition(position : Int)=
        binding.charactersListRecyclerView.scrollToPosition(position)

    fun resetRecyclerView(myOnScrolledListener: MyOnScrolledListener) =
        binding.charactersListRecyclerView.addOnScrollListener(myOnScrolledListener)

}

private class FunctionManagerRecyclerAdapter(
    listener: CharactersListAdapter.CharacterHolderListener
)
{
    var adapter: CharactersListAdapter? = CharactersListAdapter(arrayListOf(),listener)
        private set

    fun setCharactersList(characters: Characters?) =
        adapter?.addCharacters(characters?.listCharacters ?: emptyList())
}
private class FunctionManagerViewModel(
    private val viewModel: CharactersListViewModel
)
{
    fun setListCharactersObservesVM(owner: LifecycleOwner, observe : ((Characters)->(Unit))) {
        viewModel.charactersViewModel.characters.observe(owner,observe)
    }
    fun setListCharactersNoObservesVM(owner: LifecycleOwner) {
        viewModel.charactersViewModel.characters.removeObservers(owner)
        viewModel.charactersViewModel.resetCharacters()
    }

    fun getFavoriteCharactersList() =
        viewModel.charactersViewModel.getFavoriteCharactersList()

    fun searchCharacterList(name : String)=
        viewModel.charactersViewModel.searchCharactersList(name)

    fun setObservesCharactersViewModelError(owner: LifecycleOwner,observe: ((Throwable)->(Unit))) =
        viewModel.charactersViewModel.error.observe(owner,observe)


    fun setNoObservesCharactersViewModelError(owner: LifecycleOwner) =
        viewModel.charactersViewModel.error.removeObservers(owner)
}