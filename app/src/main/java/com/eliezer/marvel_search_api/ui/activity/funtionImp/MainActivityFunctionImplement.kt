package com.eliezer.marvel_search_api.ui.activity.funtionImp

import androidx.credentials.Credential
import androidx.lifecycle.LifecycleOwner
import com.eliezer.marvel_search_api.databinding.ActivityMainBinding
import com.eliezer.marvel_search_api.domain.local_property.LocalAccount
import com.eliezer.marvel_search_api.models.dataclass.MyUserCredential
import com.eliezer.marvel_search_api.ui.activity.viewmodel.MainActivityViewModel
import com.google.firebase.auth.AuthResult

class MainActivityFunctionImplement(
    binding: ActivityMainBinding,
    viewModel : MainActivityViewModel,
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
}