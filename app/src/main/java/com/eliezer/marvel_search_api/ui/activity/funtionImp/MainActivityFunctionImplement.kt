package com.eliezer.marvel_search_api.ui.activity.funtionImp

import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.credentials.Credential
import androidx.lifecycle.LifecycleOwner
import com.eliezer.marvel_search_api.databinding.ActivityMainBinding
import com.eliezer.marvel_search_api.domain.local_property.LocalAccount
import com.eliezer.marvel_search_api.models.dataclass.Characters
import com.eliezer.marvel_search_api.models.dataclass.Comics
import com.eliezer.marvel_search_api.models.dataclass.MyUserCredential
import com.eliezer.marvel_search_api.ui.activity.viewmodel.MainActivityViewModel
import com.eliezer.marvel_search_api.ui.fragments.character_list.functionImp.function.MainActivityFunctionManagerRepository
import com.google.android.material.appbar.AppBarLayout
import com.google.firebase.auth.AuthResult

class MainActivityFunctionImplement(
    binding: ActivityMainBinding,
    viewModel : MainActivityViewModel,
    private val mainActivityFunctionManagerRepository: MainActivityFunctionManagerRepository,
    private val owner: LifecycleOwner
) {
    private val functionManagerBinding = FunctionManagerBinding(
            binding
        )
    private val functionManagerViewModel = FunctionManagerViewModel(
        viewModel
    )

    fun setMainToolbar() = functionManagerBinding.setMainToolbar()

    fun getLocalUserCredential()
    {
        functionManagerViewModel.setMyUserCredentialObservesVM(owner,::getMyUserCredential)
        functionManagerViewModel.getMyUserCredential()
    }

    private fun getMyUserCredential(myUserCredential: MyUserCredential?) {
        functionManagerViewModel.setMyUserCredentialNotObservesVM(owner)
        myUserCredential?.also {
            sendCredential(it)
        }
    }
    private fun sendCredential(myUserCredential: MyUserCredential)
    {
        functionManagerViewModel.setGetUserWithCredentialObserveVM(owner, ::getAuthResult)
        val credential = Credential.createFrom(
            type = myUserCredential.type,
            data = myUserCredential.data
        )
        functionManagerViewModel.getGoogleSigInAuthResult(credential)
    }
    private fun getAuthResult(authResult: AuthResult)
    {
        functionManagerViewModel.setGetUserWithCredentialNotObserveVM(owner)
        LocalAccount.authResult.postValue(authResult)
    }
    fun setToolbarView(visibility: Boolean) = functionManagerBinding.setToolbarView(visibility)

    fun setSubToolbarView(visibility: Boolean) = functionManagerBinding.setSubToolbarView(visibility)
    fun listeningChangesInAuthResult() {
            functionManagerViewModel.setAuthResultObservesVM(owner, ::updateLocalDatabase)
    }

    private fun updateLocalDatabase(authResult: AuthResult?) {
        LocalAccount.authResult.value?.also {
            mainActivityFunctionManagerRepository.clearDatabaseComic()
            mainActivityFunctionManagerRepository.clearDatabaseCharacter()
            updateDatabaseWithFavoriteComics()
        }
    }

    private fun updateDatabaseWithFavoriteComics() {
        getIdComicsModeFavorite()
        getIdCharactersModeFavorite()
    }
    //Comics
    private fun getIdComicsModeFavorite() {
        functionManagerViewModel.setIdComicsObservesVM(owner,::getListComicsByIds)
        functionManagerViewModel.getFavoriteIdsComicsList()

    }
    private fun getListComicsByIds(ids: ArrayList<Int>) {
        functionManagerViewModel.setListComicsObservesVM(owner,::setFavoriteListComicsInDatabase)
        functionManagerViewModel.getFavoriteComicsList(ids)
        functionManagerViewModel.setIdComicsNotObservesVM(owner)
    }

    private fun setFavoriteListComicsInDatabase(comics: Comics) {
        mainActivityFunctionManagerRepository.insertDatabaseComics(comics.listComics)
        functionManagerViewModel.setListComicsNoObservesVM(owner)
    }
    //Characters
    private fun getIdCharactersModeFavorite() {
        functionManagerViewModel.setIdCharactersObservesVM(owner,::getListCharactersByIds)
        functionManagerViewModel.getFavoriteIdsCharactersList()

    }
    private fun getListCharactersByIds(ids: ArrayList<Int>) {
        functionManagerViewModel.setListCharactersObservesVM(owner,::setFavoriteListCharactersInDatabase)
        functionManagerViewModel.getFavoriteCharactersList(ids)
        functionManagerViewModel.setIdCharactersNotObservesVM(owner)
    }

    private fun setFavoriteListCharactersInDatabase(characters: Characters) {
        mainActivityFunctionManagerRepository.insertDatabaseCharacters(characters.listCharacters)
        functionManagerViewModel.setListCharactersNoObservesVM(owner)
    }
}

private class FunctionManagerBinding(
    private val binding: ActivityMainBinding,
)
{
    fun setMainToolbar()
    {
        binding.mainToolbar.bringToFront()
        binding.mainSubToolbar.bringToFront()
    }
    fun setToolbarView(visibility : Boolean)
    {
        binding.mainToolbar.visibility = if(visibility) View.VISIBLE else View.GONE
        getCoordinatorLayoutParams().behavior = if(visibility) AppBarLayout.ScrollingViewBehavior() else null
        binding.mainSubToolbar.visibility = View.GONE
        if(!visibility)
            binding.mainCoordinatorLayout.requestLayout()
    }

    fun setSubToolbarView(visibility: Boolean)
    {
        binding.mainSubToolbar.visibility = if(visibility) View.VISIBLE else View.GONE
    }
    private fun getCoordinatorLayoutParams() =  binding.mainConstraintLayout.layoutParams as CoordinatorLayout.LayoutParams
}
private class FunctionManagerViewModel(
    private val viewModel : MainActivityViewModel
)
{
    fun setMyUserCredentialObservesVM(owner: LifecycleOwner,observe : ((MyUserCredential?)-> (Unit)))
    {
        viewModel.myUserCredentialLiveData.observe(owner,observe)
    }
    fun setMyUserCredentialNotObservesVM(owner: LifecycleOwner) {
        viewModel.myUserCredentialLiveData.removeObservers(owner)
    }

    fun setGetUserWithCredentialObserveVM(owner: LifecycleOwner, observe: (AuthResult) -> Unit) {
        viewModel.googleAuthResult.observe(owner,observe)
    }
    fun setGetUserWithCredentialNotObserveVM(owner: LifecycleOwner) {
        viewModel.googleAuthResult.removeObservers(owner)
    }
    fun getMyUserCredential()
    {
        viewModel.getLocalUserCredential()
    }
    fun getGoogleSigInAuthResult(credential: Credential)
    {
        viewModel.signInGoogle(credential)
    }

    fun setAuthResultObservesVM(owner: LifecycleOwner, observe: (AuthResult?) -> Unit) {
        LocalAccount.authResult.observe(owner,observe)
    }
    //Comic
    fun setIdComicsObservesVM(owner: LifecycleOwner, observeComics: ((ArrayList<Int>)->(Unit))) {
        viewModel.favoriteIdComics.observe(owner,observeComics)
    }
    fun setIdComicsNotObservesVM(owner: LifecycleOwner) {
        viewModel.favoriteIdComics.removeObservers(owner)
        viewModel.resetFavoriteIdComics()
    }
    fun getFavoriteIdsComicsList()
    {
        viewModel.getFavoriteIdComicsList()
    }

    fun getFavoriteComicsList(ids : ArrayList<Int>)
    {
        viewModel.getFavoriteComicsList(ids)
    }

    fun setListComicsObservesVM(owner: LifecycleOwner, observe : ((Comics)->(Unit))) {
        viewModel.listComic.observe(owner,observe)
    }
    fun setListComicsNoObservesVM(owner: LifecycleOwner) {
        viewModel.listComic.removeObservers(owner)
        viewModel.resetComics()
    }
    // Character
    fun setIdCharactersObservesVM(owner: LifecycleOwner, observeCharacters: ((ArrayList<Int>)->(Unit))) {
        viewModel.favoriteIdCharacters.observe(owner,observeCharacters)
    }
    fun setIdCharactersNotObservesVM(owner: LifecycleOwner) {
        viewModel.favoriteIdCharacters.removeObservers(owner)
        viewModel.resetFavoriteIdCharacters()
    }
    fun getFavoriteIdsCharactersList()
    {
        viewModel.getFavoriteIdCharactersList()
    }

    fun getFavoriteCharactersList(ids : ArrayList<Int>)
    {
        viewModel.getFavoriteCharactersList(ids)
    }

    fun setListCharactersObservesVM(owner: LifecycleOwner, observe : ((Characters)->(Unit))) {
        viewModel.listCharacter.observe(owner,observe)
    }
    fun setListCharactersNoObservesVM(owner: LifecycleOwner) {
        viewModel.listCharacter.removeObservers(owner)
        viewModel.resetCharacters()
    }
}