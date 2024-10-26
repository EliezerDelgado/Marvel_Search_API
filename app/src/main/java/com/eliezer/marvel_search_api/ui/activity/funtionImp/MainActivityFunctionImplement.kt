package com.eliezer.marvel_search_api.ui.activity.funtionImp

import android.content.Context
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.LifecycleOwner
import com.eliezer.marvel_search_api.databinding.ActivityMainBinding
import com.eliezer.marvel_search_api.domain.alert_dialogs.loadingDialog
import com.eliezer.marvel_search_api.domain.local_property.LocalAccount
import com.eliezer.marvel_search_api.models.dataclass.Characters
import com.eliezer.marvel_search_api.models.dataclass.Comics
import com.eliezer.marvel_search_api.models.dataclass.UserAccount
import com.eliezer.marvel_search_api.ui.activity.viewmodel.MainActivityViewModel
import com.google.android.material.appbar.AppBarLayout

class MainActivityFunctionImplement(
    binding: ActivityMainBinding,
    viewModel : MainActivityViewModel,
    private val owner: LifecycleOwner,
    context: Context
) {
    private val functionManagerBinding = FunctionManagerBinding(
            binding
        )
    private val functionManagerViewModel = FunctionManagerViewModel(
        viewModel
    )
    private val loadingDialog =        loadingDialog(context)

    fun setMainToolbar() = functionManagerBinding.setMainToolbar()
    fun setToolbarView(visibility: Boolean) = functionManagerBinding.setToolbarView(visibility)

    fun setSubToolbarView(visibility: Boolean) = functionManagerBinding.setSubToolbarView(visibility)
    fun listeningChangesInUserAccount() {
        loadingDialog.show()
        functionManagerViewModel.setUserAccountObservesVM(owner, ::updateLocalDatabase)
    }

    private fun updateLocalDatabase(userAccount : UserAccount?) {
        LocalAccount.userAccount.value?.also {
            clearDatabase()
        }
    }

    private fun clearDatabase() {
        functionManagerViewModel.setIsClearObservesVM(owner,::updateDatabase)
        functionManagerViewModel.clearComicsList()
        functionManagerViewModel.clearCharactersList()
    }
    private fun updateDatabase(numClearDatabase : Int) {
        if(numClearDatabase >=2)
        {
            functionManagerViewModel.setIsClearNotObservesVM(owner)
            getIdComicsModeFavorite()
            getIdCharactersModeFavorite()
        }
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
       // mainActivityFunctionManagerRepository.insertDatabaseComics(comics.listComics)
        functionManagerViewModel.setListComicsNoObservesVM(owner)
        if(loadingDialog.isShowing)  loadingDialog.cancel()
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
        //mainActivityFunctionManagerRepository.insertDatabaseCharacters(characters.listCharacters)
        functionManagerViewModel.setListCharactersNoObservesVM(owner)
        if(loadingDialog.isShowing)  loadingDialog.cancel()
    }

    fun getLocalUser() {
        val currentUser = LocalAccount.auth.currentUser
        LocalAccount.userAccount.postValue(
            currentUser?.run {
                UserAccount(displayName!!, email!!, photoUrl)
            }
        )
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
    fun setUserAccountObservesVM(owner: LifecycleOwner, observe: (UserAccount?) -> Unit) {
        LocalAccount.userAccount.observe(owner,observe)
    }
    fun setIsClearObservesVM(owner: LifecycleOwner, observe: (Int) -> Unit) {
        viewModel.isClear.observe(owner,observe)
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
    fun clearComicsList()
    {
        viewModel.clearFavoritesComicsList()
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

    fun clearCharactersList()
    {
        viewModel.clearFavoritesCharactersList()
    }
    fun setListCharactersObservesVM(owner: LifecycleOwner, observe : ((Characters)->(Unit))) {
        viewModel.listCharacter.observe(owner,observe)
    }
    fun setListCharactersNoObservesVM(owner: LifecycleOwner) {
        viewModel.listCharacter.removeObservers(owner)
        viewModel.resetCharacters()
    }
    fun setIsClearNotObservesVM(owner: LifecycleOwner) {
        viewModel.isClear.removeObservers(owner)
        viewModel.resetIsClear()
    }
}