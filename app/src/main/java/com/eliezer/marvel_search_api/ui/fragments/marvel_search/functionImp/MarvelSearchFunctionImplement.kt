package com.eliezer.marvel_search_api.ui.fragments.marvel_search.functionImp

import android.content.Context
import android.util.Log
import android.widget.Button
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatImageButton
import androidx.credentials.exceptions.NoCredentialException
import androidx.lifecycle.LifecycleOwner
import com.eliezer.marvel_search_api.R
import com.eliezer.marvel_search_api.domain.local_property.LocalAccount
import com.eliezer.marvel_search_api.domain.actions.NavigationMainActions
import com.eliezer.marvel_search_api.databinding.FragmentMarvelSearchBinding
import com.eliezer.marvel_search_api.domain.alert_dialogs.errorDialog
import com.eliezer.marvel_search_api.domain.alert_dialogs.loadingDialog
import com.eliezer.marvel_search_api.domain.alert_dialogs.userDialog
import com.eliezer.marvel_search_api.domain.alert_dialogs.warningDialog
import com.eliezer.marvel_search_api.models.dataclass.Characters
import com.eliezer.marvel_search_api.models.dataclass.Comics
import com.eliezer.marvel_search_api.models.dataclass.UserAccount
import com.eliezer.marvel_search_api.ui.fragments.marvel_search.functionImp.function.MarvelSearchFunctionRepositoryManager
import com.eliezer.marvel_search_api.ui.fragments.marvel_search.viewmodel.MarvelSearchViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

class MarvelSearchFunctionImplement(
    binding: FragmentMarvelSearchBinding,
    viewModel: MarvelSearchViewModel,
    private val marvelSearchFunctionRepositoryManager : MarvelSearchFunctionRepositoryManager,
    private val navigationMainActions: NavigationMainActions,
    private val owner : LifecycleOwner,
    private val context: Context
) {
    private val functionManagerBinding = FunctionManagerBinding(binding)
    private val functionManagerViewModel = FunctionManagerViewModel(viewModel)
    private val loadingAlertDialog = loadingDialog(context)

    fun buttonListener(context: Context){
        functionManagerBinding.apply {
            setOnClickListenerMarvelSearchButtonGoComicsList {
                val textSearch  = getMarvelSearchTextInputSearchText()
                setObserveSearchComics()
                searchListComics(textSearch)
            }
            setOnClickListenerMarvelSearchButtonGoCharacterList {
                val textSearch  = getMarvelSearchTextInputSearchText()
                setObserveSearchCharacters()
                searchListCharacters(textSearch)
            }
            setOnClickListenerMarvelSearchImageButtonGoFavorite {
                moveFragment()
            }
            setOnClickListenerMarvelSearchImageButtonGoogleSignIn {
                setObserveUserAccount()
                LocalAccount.userAccount.value?.let {
                    userDialog(context,it,::signOut).show()
                }?: googleSignIn()
            }
            setOnClickListenerMarvelSearchImageButtonAboutMe {
                navigationMainActions.actionMarvelSearchFragmentToAboutMeFragment()
            }
        }
    }

    private fun signOut() {
        LocalAccount.auth.signOut()
        LocalAccount.userAccount.value = null
    }

    private fun googleSignIn() {
            functionManagerViewModel.signInGoogleAccount(context)
    }

    private fun goCharacterListFragment()=
        navigationMainActions.doActionMarvelSearchFragmentToCharacterListFragment(functionManagerBinding.getMarvelSearchTextInputSearchText())

    private fun goComicsListFragment() =
        navigationMainActions.doActionMarvelSearchFragmentToComicListFragment(functionManagerBinding.getMarvelSearchTextInputSearchText())

    private fun setObserveSearchComics() =
        functionManagerViewModel.setObservesSearchComics(owner,::getSearchComics)

    private fun setObserveSearchCharacters() =
        functionManagerViewModel.setObservesSearchCharacters(owner,::getSearchCharacters)
    private fun setObserveUserAccount() =
        functionManagerViewModel.setObservesGoogleAuthResult(owner,::setAccount)


    private fun setAccount(userAccount: UserAccount) {
        setNotObserveUserAccount()
        LocalAccount.userAccount.postValue(userAccount)
    }

    private fun searchListCharacters(name:String)
    {
        disableSearchButtons()
        disableGoogleButtons()
        functionManagerViewModel.searchCharactersList(name)
        loadingAlertDialog.show()
    }

    private fun searchListComics(title: String) {
        disableSearchButtons()
        disableGoogleButtons()
        functionManagerViewModel.searchComicsList(title)
        loadingAlertDialog.show()
    }

    fun disableSearchButtons() =
        CoroutineScope(Dispatchers.Main).launch {
                functionManagerBinding.setEnableMarvelSearchButtonGoComicsList(false)
                functionManagerBinding.setEnableMarvelSearchButtonGoCharacterList(false)
        }

    fun enableSearchButtons() =
        CoroutineScope(Dispatchers.Main).launch {
                functionManagerBinding.setEnableMarvelSearchButtonGoComicsList(true)
                functionManagerBinding.setEnableMarvelSearchButtonGoCharacterList(true)
        }


    fun disableGoogleButtons() =
        CoroutineScope(Dispatchers.Main).launch {
                functionManagerBinding.setEnableMarvelSearchImageButtonGoogleSignIn(false)
                functionManagerBinding.setDrawableMarvelSearchImageButtonGoogleSignIn(R.drawable.img_google_sign_in_disable)
        }

    fun enableGoogleButtons() =
        CoroutineScope(Dispatchers.Main).launch {
                functionManagerBinding.setEnableMarvelSearchImageButtonGoogleSignIn(true)
                functionManagerBinding.setDrawableMarvelSearchImageButtonGoogleSignIn(R.drawable.img_google_sign_in)
        }

    fun disableFavoriteButtons() =
        CoroutineScope(Dispatchers.Main).launch {
            functionManagerBinding.setEnableMarvelSearchImageButtonGoFavorite(false)
        }

    fun enableFavoriteButtons() =
        CoroutineScope(Dispatchers.Main).launch {
            functionManagerBinding.setEnableMarvelSearchImageButtonGoFavorite(true)
        }

    fun checkFavorite() =  CoroutineScope(Dispatchers.Main).launch {
        LocalAccount.userAccount.value?.also {
            functionManagerBinding.setEnableMarvelSearchImageButtonGoFavorite(true)
        }
    }



    private fun getSizeResultList(size: Int){
        loadingAlertDialog.cancel()
        if(size>0) {
            moveFragment()
        }
        else if(size==0)
            showWarning(R.string.warning_size_list_search_is_0)
        enableSearchButtons()
        enableGoogleButtons()
    }
    private fun getSearchComics(comics: Comics)
    {
        functionManagerViewModel.setNoObservesSearchComics(owner)
        if(comics.total > 0)
            marvelSearchFunctionRepositoryManager.insertTmpComics(comics)
        getSizeResultList(comics.total)
    }
    private fun getSearchCharacters(characters: Characters)
    {
        marvelSearchFunctionRepositoryManager.insertTmpCharacters(characters)
        functionManagerViewModel.setNoObservesSearchCharacters(owner)
        getSizeResultList(characters.total)
    }

    private fun moveFragment() {
        when(functionManagerBinding.nameButtonPulse)
        {
            functionManagerBinding.idMarvelSearchButtonGoComicsList() -> goComicsListFragment()
            functionManagerBinding.idMarvelSearchButtonGoCharacterList() -> goCharacterListFragment()
            functionManagerBinding.idMarvelSearchImageButtonGoFavorite() -> goFavoriteFragment()
        }
        functionManagerBinding.resetNameButtonPulse()
    }

    private fun goFavoriteFragment() = navigationMainActions.doActionMarvelSearchFragmentToFavoritesFragment()

    private fun showError(@StringRes idError: Int) {
        errorDialog(context,context.resources.getString(idError)).show()
    }
    private fun showWarning(@StringRes idError: Int) {
        warningDialog(context,context.resources.getString(idError)).show()
    }

    private fun setNotObserveUserAccount() =
        functionManagerViewModel.setNoObservesGoogleAuthResult(owner)

    fun resetAll() {
        functionManagerViewModel.apply {
            resetCharacters()
            resetComics()
        }
    }
    fun showWarningNetworkLost() =  CoroutineScope(Dispatchers.Main).launch {
        showWarning(R.string.warning_not_network)
    }

    fun errorListener() {
        functionManagerViewModel.setObservesError(owner,::createErrorLog)
        functionManagerViewModel.setObservesUserErrorMessage(owner,::showErrorToUser)
    }
    fun stopErrorListener() {
        functionManagerViewModel.setNoObservesError(owner)
        functionManagerViewModel.setNoObservesUserErrorMessage(owner)
    }

    private fun showErrorToUser(@StringRes idString: Int) = showError(idString)


    private fun createErrorLog(throwable: Throwable) {
        if(throwable is NoCredentialException)
            functionManagerViewModel.signInNewGoogleAccount(context)
        else if(throwable is HttpException){
            errorEmptySearch(throwable)
        }
    }

    private fun errorEmptySearch(throwable:  Throwable) {
        Log.e("***",throwable.message,throwable)
        loadingAlertDialog.cancel()
        enableSearchButtons()
        enableGoogleButtons()
    }
}

private class FunctionManagerViewModel(
    val viewModel: MarvelSearchViewModel
)
{
    fun setObservesSearchComics(owner: LifecycleOwner,observe : (Comics)->(Unit))
    {
        viewModel.comicsViewModel.comics.observe(owner,observe)
    }

    fun setNoObservesSearchComics(owner: LifecycleOwner)
    {
        viewModel.comicsViewModel.comics.removeObservers(owner)
    }
    fun setObservesSearchCharacters(owner: LifecycleOwner,observe : (Characters)->(Unit))
    {
        viewModel.charactersViewModel.characters.observe(owner,observe)
    }
    fun setNoObservesSearchCharacters(owner: LifecycleOwner)
    {
        viewModel.charactersViewModel.characters.removeObservers(owner)
    }
    //TODO ERRORES DE TODOS VIEW MODEL OBSERVE
    fun setObservesError(owner: LifecycleOwner,observe: ((Throwable)->(Unit))) =
        viewModel.error.observe(owner,observe)

    fun setNoObservesError(owner: LifecycleOwner) {
        viewModel.charactersViewModel.error.removeObservers(owner)
        viewModel.googleAuthResultViewModel.error.removeObservers(owner)
        viewModel.comicsViewModel.error.removeObservers(owner)
    }

    fun setObservesUserErrorMessage(owner: LifecycleOwner,observe: ((Int)->(Unit))) =
        viewModel.userErrorMessage.observe(owner,observe)

    fun setNoObservesUserErrorMessage(owner: LifecycleOwner) {
        viewModel.charactersViewModel.userErrorMessage.removeObservers(owner)
        viewModel.googleAuthResultViewModel.userErrorMessage.removeObservers(owner)
        viewModel.comicsViewModel.userErrorMessage.removeObservers(owner)
    }

    fun setObservesGoogleAuthResult(owner: LifecycleOwner,observe: ((UserAccount)->(Unit))) =
        viewModel.googleAuthResultViewModel.googleAuthResult.observe(owner,observe)

    fun setNoObservesGoogleAuthResult(owner: LifecycleOwner) {
        viewModel.googleAuthResultViewModel.googleAuthResult.removeObservers(owner)
        viewModel.googleAuthResultViewModel.resetAuthResult()
    }

    fun signInGoogleAccount(context: Context) = viewModel.googleAuthResultViewModel.signInGoogleAccount(context)

    fun  searchCharactersList(name:String)  =  viewModel.charactersViewModel.searchCharactersList(name)

    fun  searchComicsList(title:String)  =  viewModel.comicsViewModel.searchComicsList(title)

    fun resetComics() = viewModel.comicsViewModel.resetComics()

    fun  resetCharacters() = viewModel.charactersViewModel.resetCharacters()

    fun signInNewGoogleAccount(context: Context) =
        viewModel.googleAuthResultViewModel.signInNewGoogleAccount(context)
}

private class FunctionManagerBinding(
    private val binding: FragmentMarvelSearchBinding,
)
{
    var nameButtonPulse : String? = null
        private set
    fun resetNameButtonPulse() {
        nameButtonPulse=null
    }
    fun getMarvelSearchTextInputSearchText() =binding.marvelSearchTextInputSearch.editText?.text.toString()

    fun setOnClickListenerMarvelSearchButtonGoComicsList(execute : ()-> Unit) =  buttonListening(binding.marvelSearchButtonGoComicsList,execute)

    fun setOnClickListenerMarvelSearchButtonGoCharacterList(execute : ()-> Unit) =  buttonListening(binding.marvelSearchButtonGoCharacterList,execute)

    fun setOnClickListenerMarvelSearchImageButtonGoFavorite(execute : ()-> Unit) =  buttonListening(binding.marvelSearchImageButtonGoFavorite,execute)

    fun setOnClickListenerMarvelSearchImageButtonGoogleSignIn(execute : ()-> Unit) =  buttonListening(binding.marvelSearchImageButtonGoogleSignIn,execute)

    fun setOnClickListenerMarvelSearchImageButtonAboutMe(execute : ()-> Unit) =  buttonListening(binding.marvelSearchImageButtonAboutMe,execute)

    fun setEnableMarvelSearchButtonGoComicsList(enable: Boolean) {
        binding.marvelSearchButtonGoComicsList.isEnabled = enable
    }
    fun setEnableMarvelSearchButtonGoCharacterList(enable: Boolean) {
        binding.marvelSearchButtonGoCharacterList.isEnabled = enable
    }
    fun setEnableMarvelSearchImageButtonGoogleSignIn(enable: Boolean) {
        binding.marvelSearchImageButtonGoogleSignIn.isEnabled = enable
    }

    fun setEnableMarvelSearchImageButtonGoFavorite(enable: Boolean) {
        binding.marvelSearchImageButtonGoFavorite.isEnabled = enable
    }

    fun idMarvelSearchButtonGoComicsList() =   binding.marvelSearchButtonGoComicsList.id.toString()

    fun idMarvelSearchButtonGoCharacterList() =   binding.marvelSearchButtonGoCharacterList.id.toString()

    fun idMarvelSearchImageButtonGoFavorite() =   binding.marvelSearchImageButtonGoFavorite.id.toString()

    fun setDrawableMarvelSearchImageButtonGoogleSignIn(@DrawableRes drawable: Int) =  binding.marvelSearchImageButtonGoogleSignIn.setImageResource(drawable)


    private fun buttonListening(button: Button,execute : ()-> Any?)
    {
        button.apply {
            setOnClickListener{
                nameButtonPulse = id.toString()
                execute.invoke()
            }
        }
    }
    private fun buttonListening(imageButton: AppCompatImageButton,execute : ()-> Any?)
    {
        imageButton.apply {
            setOnClickListener{
                nameButtonPulse = id.toString()
                execute.invoke()
            }
        }
    }
}


