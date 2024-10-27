package com.eliezer.marvel_search_api.ui.activity.funtionImp

import android.content.Context
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.LifecycleOwner
import com.eliezer.marvel_search_api.databinding.ActivityMainBinding
import com.eliezer.marvel_search_api.domain.alert_dialogs.loadingDialog
import com.eliezer.marvel_search_api.domain.function.FunctionLoadingManager
import com.eliezer.marvel_search_api.domain.local_property.LocalAccount
import com.eliezer.marvel_search_api.models.dataclass.Character
import com.eliezer.marvel_search_api.models.dataclass.Characters
import com.eliezer.marvel_search_api.models.dataclass.Comic
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
    private val functionLoadingManager = FunctionLoadingManager(context)

    fun setMainToolbar() = functionManagerBinding.setMainToolbar()
    fun setToolbarView(visibility: Boolean) = functionManagerBinding.setToolbarView(visibility)

    fun setSubToolbarView(visibility: Boolean) = functionManagerBinding.setSubToolbarView(visibility)
    fun listeningChangesInUserAccount() {
        functionLoadingManager.showLoadingDialog()
        functionManagerViewModel.setObservesUserAccount(owner, ::updateLocalDatabase)
    }

    private fun updateLocalDatabase(userAccount : UserAccount?) {
        LocalAccount.userAccount.value?.also {
            clearDatabase()
        }
    }

    private fun clearDatabase() {
        functionManagerViewModel.setObservesIsCharactersDatabaseClear(owner,::updateCharacterDatabase)
        functionManagerViewModel.setObservesIsComicsDatabaseClear(owner,::updateComicDatabase)
        functionManagerViewModel.clearComicsList()
        functionManagerViewModel.clearCharactersList()
    }
    private fun updateComicDatabase(isClearDatabase : Boolean) {
        if(isClearDatabase)
        {
            functionManagerViewModel.setNoObservesIsComicsDatabaseClear(owner)
            getIdComicsModeFavorite()
            getIdCharactersModeFavorite()
        }
    }
    private fun updateCharacterDatabase(isClearDatabase : Boolean) {
        if(isClearDatabase )
        {
            functionManagerViewModel.setNoObservesIsCharactersDatabaseClear(owner)
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
        functionManagerViewModel.setNoObservesIdComics(owner)
    }

    private fun setFavoriteListComicsInDatabase(comics: Comics) {
        functionManagerViewModel.setNoObservesListComics(owner)
        functionManagerViewModel.setObservesInsertListComicsInDatabase(owner,::insertedComicsCompleted)
        functionManagerViewModel.insertComicsListInDatabase(comics.listComics)
    }

    private fun insertedComicsCompleted(longs: List<Long>) {
        functionManagerViewModel.setNoObservesInsertListComicsInDatabase(owner)
        functionLoadingManager.sumOperationsComplete(1)
        functionLoadingManager.stopLoading(2)
    }


    //Characters
    private fun getIdCharactersModeFavorite() {
        functionManagerViewModel.setObservesIdCharacters(owner,::getListCharactersByIds)
        functionManagerViewModel.getFavoriteIdsCharactersList()

    }
    private fun getListCharactersByIds(ids: ArrayList<Int>) {
        functionManagerViewModel.setObservesListCharacters(owner,::setFavoriteListCharactersInDatabase)
        functionManagerViewModel.getFavoriteCharactersList(ids)
        functionManagerViewModel.setNoObservesIdCharacters(owner)
    }

    private fun setFavoriteListCharactersInDatabase(characters: Characters) {
        functionManagerViewModel.setNoObservesListCharacters(owner)
        functionManagerViewModel.setObservesInsertListCharactersInDatabase(owner,::insertedCharactersCompleted)
        functionManagerViewModel.insertCharactersListInDatabase(characters.listCharacters)
    }

    private fun insertedCharactersCompleted(longs: List<Long>) {
        functionManagerViewModel.setNoObservesInsertListCharactersInDatabase(owner)
        functionLoadingManager.sumOperationsComplete(1)
        functionLoadingManager.stopLoading(2)
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
    fun setNoObservesIdComics(owner: LifecycleOwner) {
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

    fun setObservesListComics(owner: LifecycleOwner, observe : ((Comics)->(Unit))) =
        viewModel.comicsViewModel.comics.observe(owner,observe)

    fun setNoObservesListComics(owner: LifecycleOwner) {
        viewModel.comicsViewModel.comics.removeObservers(owner)
        viewModel.comicsViewModel.resetComics()
    }

    fun setObservesInsertListComicsInDatabase(owner: LifecycleOwner, observe : ((List<Long>)->(Unit))) {
        viewModel.comicsDatabaseViewModel.isInserted.observe(owner,observe)
    }

    fun setNoObservesInsertListComicsInDatabase(owner: LifecycleOwner) {
        viewModel.comicsDatabaseViewModel.isInserted.removeObservers(owner)
        viewModel.comicsDatabaseViewModel.resetIsInserted()
    }

    fun setNoObservesIsComicsDatabaseClear(owner: LifecycleOwner) {
        viewModel.comicsDatabaseViewModel.isClear.removeObservers(owner)
        viewModel.comicsDatabaseViewModel.resetIsClear()
    }
    fun insertComicsListInDatabase(comics : ArrayList<Comic>) = viewModel.comicsDatabaseViewModel.insertFavoritesComicsList(comics)

    // Character
    fun setObservesIdCharacters(owner: LifecycleOwner, observeCharacters: ((ArrayList<Int>)->(Unit))) {
        viewModel.favoriteIdCharactersViewModel.favoriteIdCharacters.observe(owner,observeCharacters)
    }
    fun setNoObservesIdCharacters(owner: LifecycleOwner) {
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
    fun setNoObservesListCharacters(owner: LifecycleOwner) {
        viewModel.charactersViewModel.characters.removeObservers(owner)
        viewModel.charactersViewModel.resetCharacters()
    }
    fun setNoObservesIsCharactersDatabaseClear(owner: LifecycleOwner) {
        viewModel.charactersDatabaseViewModel.isClear.removeObservers(owner)
        viewModel.charactersDatabaseViewModel.resetIsClear()
    }

    fun setObservesInsertListCharactersInDatabase(owner: LifecycleOwner, observe : ((List<Long>)->(Unit))) {
        viewModel.charactersDatabaseViewModel.isInserted.observe(owner,observe)
    }

    fun setNoObservesInsertListCharactersInDatabase(owner: LifecycleOwner) {
        viewModel.charactersDatabaseViewModel.isInserted.removeObservers(owner)
        viewModel.charactersDatabaseViewModel.resetIsInserted()
    }
    fun insertCharactersListInDatabase(characters: ArrayList<Character>) =  viewModel.charactersDatabaseViewModel.insertFavoritesCharactersList(characters)
}