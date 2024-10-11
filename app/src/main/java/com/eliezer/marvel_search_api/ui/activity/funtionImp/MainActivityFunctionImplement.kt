package com.eliezer.marvel_search_api.ui.activity.funtionImp

import androidx.lifecycle.LifecycleOwner
import com.eliezer.marvel_search_api.databinding.ActivityMainBinding
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

    fun getLocalUser()
    {
        functionManagerViewModel.setMyUserCredentialObservesVM(owner,::getMyUserCredential)
    }

    private fun getMyUserCredential(myUserCredential: MyUserCredential) {
        functionManagerViewModel.setMyUserCredentialNotObservesVM(owner)
        functionManagerViewModel.setGetUserWithCredentialObserveVM(owner,::getAuthResult)
    }
    private fun getAuthResult(authResult: AuthResult)
    {

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
    fun setMyUserCredentialObservesVM(owner: LifecycleOwner,observe : ((MyUserCredential)-> (Unit)))
    {
        viewModel.myUserCredentialLiveData.observe(owner,observe)
    }
    fun setMyUserCredentialNotObservesVM(owner: LifecycleOwner)
    {
        viewModel.myUserCredentialLiveData.removeObservers(owner)
    }

    fun setGetUserWithCredentialObserveVM(owner: LifecycleOwner, kFunction1: (AuthResult) -> Unit) {
        TODO("Not yet implemented")
    }
}