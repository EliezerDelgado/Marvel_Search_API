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
        functionManagerViewModel.setObservesUserAccount(owner, ::updateLocalDatabase)
    }

    private fun updateLocalDatabase(userAccount : UserAccount?) {
        LocalAccount.userAccount.value?.also {
            clearDatabase()
        }
    }

    private fun clearDatabase() {
        functionManagerViewModel.setObservesIsCharactersDatabaseClear(owner,::updateCharacterDatabase)
        functionManagerViewModel.clearComicsList()
        functionManagerViewModel.clearCharactersList()
    }
    private fun updateComicDatabase(isClearDatabase : Boolean) {
        if(isClearDatabase)
        {
            functionManagerViewModel.setNotObservesIsComicsDatabaseClear(owner)
            getIdComicsModeFavorite()
            getIdCharactersModeFavorite()
        }
    }
    private fun updateCharacterDatabase(isClearDatabase : Boolean) {
        if(isClearDatabase )
        {
            functionManagerViewModel.setNotObservesIsCharactersDatabaseClear(owner)
            getIdComicsModeFavorite()
            getIdCharactersModeFavorite()
        }
    }
    //Comics
    private fun getIdComicsModeFavorite() {
        functionManagerViewModel.setObservesIdComics(owner,::getListComicsByIds)
        functionManagerViewModel.getFavoriteIdsComicsList()

    }
    private fun getListComicsByIds(ids: ArrayList<Int>) {
        functionManagerViewModel.setObservesListComics(owner,::setFavoriteListComicsInDatabase)
        functionManagerViewModel.getFavoriteComicsList(ids)
        functionManagerViewModel.setNotObservesIdComics(owner)
    }

    private fun setFavoriteListComicsInDatabase(comics: Comics) {
       // TODO INSERT mainActivityFunctionManagerRepository.insertDatabaseComics(comics.listComics)
        functionManagerViewModel.setNotObservesListComics(owner)
        if(loadingDialog.isShowing)  loadingDialog.cancel()
    }
    //Characters
    private fun getIdCharactersModeFavorite() {
        functionManagerViewModel.setObservesIdCharacters(owner,::getListCharactersByIds)
        functionManagerViewModel.getFavoriteIdsCharactersList()

    }
    private fun getListCharactersByIds(ids: ArrayList<Int>) {
        functionManagerViewModel.setObservesListCharacters(owner,::setFavoriteListCharactersInDatabase)
        functionManagerViewModel.getFavoriteCharactersList(ids)
        functionManagerViewModel.setNotObservesIdCharacters(owner)
    }

    private fun setFavoriteListCharactersInDatabase(characters: Characters) {
        //mainActivityFunctionManagerRepository.insertDatabaseCharacters(characters.listCharacters)
        functionManagerViewModel.setNotObservesListCharacters(owner)
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
    fun setObservesUserAccount(owner: LifecycleOwner, observe: (UserAccount?) -> Unit) {
        LocalAccount.userAccount.observe(owner,observe)
    }
    fun setObservesIsComicsDatabaseClear(owner: LifecycleOwner, observe: (Boolean) -> Unit) {
        viewModel.comicsDatabaseViewModel.isClear.observe(owner,observe)
    }
    fun setObservesIsCharactersDatabaseClear(owner: LifecycleOwner, observe: (Boolean) -> Unit) {
        viewModel.charactersDatabaseViewModel.isClear.observe(owner,observe)
    }
    //Comic
    fun setObservesIdComics(owner: LifecycleOwner, observeComics: ((ArrayList<Int>)->(Unit))) {
        viewModel.favoriteIdComicsViewModel.favoriteIdComics.observe(owner,observeComics)
    }
    fun setNotObservesIdComics(owner: LifecycleOwner) {
        viewModel.favoriteIdComicsViewModel.favoriteIdComics.removeObservers(owner)
        viewModel.favoriteIdComicsViewModel.resetFavoriteIdComics()
    }
    fun getFavoriteIdsComicsList()
    {
        viewModel.favoriteIdComicsViewModel.getFavoriteIdComicsList()
    }

    fun getFavoriteComicsList(ids : ArrayList<Int>)
    {
        viewModel.comicsViewModel.getFavoriteComicsList(ids)
    }
    fun clearComicsList()
    {
        viewModel.comicsDatabaseViewModel.clearFavoritesComicsList()
    }

    fun setObservesListComics(owner: LifecycleOwner, observe : ((Comics)->(Unit))) {
        viewModel.comicsViewModel.comics.observe(owner,observe)
    }
    fun setNotObservesListComics(owner: LifecycleOwner) {
        viewModel.comicsViewModel.comics.removeObservers(owner)
        viewModel.comicsViewModel.resetComics()
    }
    // Character
    fun setObservesIdCharacters(owner: LifecycleOwner, observeCharacters: ((ArrayList<Int>)->(Unit))) {
        viewModel.favoriteIdCharactersViewModel.favoriteIdCharacters.observe(owner,observeCharacters)
    }
    fun setNotObservesIdCharacters(owner: LifecycleOwner) {
        viewModel.favoriteIdCharactersViewModel.favoriteIdCharacters.removeObservers(owner)
        viewModel.favoriteIdCharactersViewModel.resetFavoriteIdCharacters()
    }
    fun getFavoriteIdsCharactersList()
    {
        viewModel.favoriteIdCharactersViewModel.getFavoriteIdCharactersList()
    }

    fun getFavoriteCharactersList(ids : ArrayList<Int>)
    {
        viewModel.charactersViewModel.getFavoriteCharactersList(ids)
    }

    fun clearCharactersList()
    {
        viewModel.charactersDatabaseViewModel.clearFavoritesCharactersList()
    }
    fun setObservesListCharacters(owner: LifecycleOwner, observe : ((Characters)->(Unit))) {
        viewModel.charactersViewModel.characters.observe(owner,observe)
    }
    fun setNotObservesListCharacters(owner: LifecycleOwner) {
        viewModel.charactersViewModel.characters.removeObservers(owner)
        viewModel.charactersViewModel.resetCharacters()
    }
    fun setNotObservesIsCharactersDatabaseClear(owner: LifecycleOwner) {
        viewModel.charactersDatabaseViewModel.isClear.removeObservers(owner)
        viewModel.charactersDatabaseViewModel.resetIsClear()
    }
    fun setNotObservesIsComicsDatabaseClear(owner: LifecycleOwner) {
        viewModel.comicsDatabaseViewModel.isClear.removeObservers(owner)
        viewModel.comicsDatabaseViewModel.resetIsClear()
    }
}