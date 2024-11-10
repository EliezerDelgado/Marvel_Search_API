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
import com.eliezer.marvel_search_api.databinding.FragmentMarvelSearchBinding
import com.eliezer.marvel_search_api.domain.actions.NavigationMainActions
import com.eliezer.marvel_search_api.domain.alert_dialogs.errorDialog
import com.eliezer.marvel_search_api.domain.alert_dialogs.userDialog
import com.eliezer.marvel_search_api.domain.alert_dialogs.warningDialog
import com.eliezer.marvel_search_api.domain.function.FunctionLoadingManager
import com.eliezer.marvel_search_api.domain.local_property.LocalAccount
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
    private val functionLoadingManager = FunctionLoadingManager(context)

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
        functionLoadingManager.showLoadingDialog()
    }

    private fun searchListComics(title: String) {
        disableSearchButtons()
        disableGoogleButtons()
        functionLoadingManager.showLoadingDialog()
        functionManagerViewModel.searchComicsList(title)
    }

    fun disableSearchButtons() =
        CoroutineScope(Dispatchers.Main).launch {
                functionManagerBinding.setEnableMarvelSearchButtonGoComicsList(false)
                functionManagerBinding.setEnableMarvelSearchButtonGoCharacterList(false)
        }.start()

    fun enableSearchButtons() =
        CoroutineScope(Dispatchers.Main).launch {
                functionManagerBinding.setEnableMarvelSearchButtonGoComicsList(true)
                functionManagerBinding.setEnableMarvelSearchButtonGoCharacterList(true)
        }.start()


    fun disableGoogleButtons() =
        CoroutineScope(Dispatchers.Main).launch {
                functionManagerBinding.setEnableMarvelSearchImageButtonGoogleSignIn(false)
                functionManagerBinding.setDrawableMarvelSearchImageButtonGoogleSignIn(R.drawable.img_google_sign_in_disable)
        }.start()

    fun enableGoogleButtons() =
        CoroutineScope(Dispatchers.Main).launch {
                functionManagerBinding.setEnableMarvelSearchImageButtonGoogleSignIn(true)
                functionManagerBinding.setDrawableMarvelSearchImageButtonGoogleSignIn(R.drawable.img_google_sign_in)
        }.start()

    fun disableFavoriteButtons() =
        CoroutineScope(Dispatchers.Main).launch {
            functionManagerBinding.setEnableMarvelSearchImageButtonGoFavorite(false)
        }.start()

    fun enableFavoriteButtons() =
        CoroutineScope(Dispatchers.Main).launch {
            functionManagerBinding.setEnableMarvelSearchImageButtonGoFavorite(true)
        }.start()

    fun checkFavorite() =  CoroutineScope(Dispatchers.Main).launch {
        LocalAccount.userAccount.value?.also {
            functionManagerBinding.setEnableMarvelSearchImageButtonGoFavorite(true)
        }
    }



    private fun getSizeResultList(size: Int){
        functionLoadingManager.stopLoading()
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
        CoroutineScope(Dispatchers.IO).launch {
            if (comics.total > 0) {
                comics.setImageComics()
                marvelSearchFunctionRepositoryManager.insertTmpComics(comics)
            }
            getSizeResultList(comics.total)
        }
    }

    private fun getSearchCharacters(characters: Characters)
    {
        functionManagerViewModel.setNoObservesSearchCharacters(owner)
        CoroutineScope(Dispatchers.IO).launch {
            if(characters.total>0) {
                characters.setImageCharacters()
                marvelSearchFunctionRepositoryManager.insertTmpCharacters(characters)
            }
            getSizeResultList(characters.total)
        }.start()
    }

    private fun moveFragment() {
        CoroutineScope(Dispatchers.Main).launch {
            functionManagerBinding.apply {
                when (nameButtonPulse) {
                    idMarvelSearchButtonGoComicsList() -> goComicsListFragment()
                    idMarvelSearchButtonGoCharacterList() -> goCharacterListFragment()
                    idMarvelSearchImageButtonGoFavorite() -> goFavoriteFragment()
                }
                resetNameButtonPulse()
            }
        }
    }

    private fun goFavoriteFragment() = navigationMainActions.doActionMarvelSearchFragmentToFavoritesFragment()

    private fun showError(@StringRes idError: Int) =
        CoroutineScope(Dispatchers.Main).launch {
            errorDialog(context, context.resources.getString(idError)).show()
        }.start()

    private fun showWarning(@StringRes idError: Int) =
        CoroutineScope(Dispatchers.Main).launch {
            warningDialog(context, context.resources.getString(idError)).show()
        }.start()


    private fun setNotObserveUserAccount() =
        functionManagerViewModel.setNoObservesGoogleAuthResult(owner)

    fun resetAll() {
        functionManagerViewModel.apply {
            resetCharacters()
            resetComics()
        }
    }

    fun errorListener() {
        internalErrorListener()
        errorsForUserListener()
    }

    private fun errorsForUserListener() {
        functionManagerViewModel.apply {
            setObservesComicsViewModelUserErrorMessage(
                owner,
                ::showErrorToUser
            )
            setObservesCharactersViewModelUserErrorMessage(
                owner,
                ::showErrorToUser
            )
            setObservesGoogleAuthResultViewModelUserErrorMessage(
                owner,
                ::showErrorToUser
            )
        }
    }

    private fun internalErrorListener() {
        functionManagerViewModel.apply {
            setObservesComicsViewModelError(owner, ::createErrorLog)
            setObservesCharactersViewModelError(owner, ::createErrorLog)
            setObservesGoogleAuthResultViewModelError(
                owner,
                ::createErrorLog
            )
        }
    }

    fun stopLoading()=
        functionLoadingManager.stopLoading()

    fun stopErrorListener() {
        functionManagerViewModel.setNoObservesError(owner)
        functionManagerViewModel.setNoObservesUserErrorMessage(owner)
    }

    private fun showErrorToUser(@StringRes idString: Int) {
        functionLoadingManager.stopLoading()
        showError(idString)
    }

    private fun createErrorLog(throwable: Throwable) {
        if(throwable is NoCredentialException)
            functionManagerViewModel.signInNewGoogleAccount(context)
        else
        {
            if(throwable is HttpException){
                errorEmptySearch()
            }
            Log.e("***",throwable.message,throwable)
        }
    }

    private fun errorEmptySearch() {
        functionLoadingManager.stopLoading()
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
        viewModel.comicsViewModel.resetComics()
    }
    fun setObservesSearchCharacters(owner: LifecycleOwner,observe : (Characters)->(Unit))
    {
        viewModel.charactersViewModel.characters.observe(owner, observe)
    }
    fun setNoObservesSearchCharacters(owner: LifecycleOwner)
    {
        viewModel.charactersViewModel.characters.removeObservers(owner)
        viewModel.charactersViewModel.resetCharacters()
    }

    fun setObservesCharactersViewModelError(owner: LifecycleOwner,observe: ((Throwable)->(Unit))) =
        viewModel.charactersViewModel.error.observe(owner,observe)

    fun setObservesComicsViewModelError(owner: LifecycleOwner,observe: ((Throwable)->(Unit))) =
        viewModel.comicsViewModel.error.observe(owner,observe)


    fun setObservesGoogleAuthResultViewModelError(owner: LifecycleOwner,observe: ((Throwable)->(Unit))) =
        viewModel.googleAuthResultViewModel.error.observe(owner,observe)

    fun setNoObservesError(owner: LifecycleOwner) {
        viewModel.charactersViewModel.error.removeObservers(owner)
        viewModel.googleAuthResultViewModel.error.removeObservers(owner)
        viewModel.comicsViewModel.error.removeObservers(owner)
    }

    fun setObservesCharactersViewModelUserErrorMessage(owner: LifecycleOwner,observe: ((Int)->(Unit))) =
        viewModel.charactersViewModel.userErrorMessage.observe(owner,observe)

    fun setObservesComicsViewModelUserErrorMessage(owner: LifecycleOwner,observe: ((Int)->(Unit))) =
        viewModel.comicsViewModel.userErrorMessage.observe(owner,observe)

    fun setObservesGoogleAuthResultViewModelUserErrorMessage(owner: LifecycleOwner,observe: ((Int)->(Unit))) =
        viewModel.googleAuthResultViewModel.userErrorMessage.observe(owner,observe)

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


