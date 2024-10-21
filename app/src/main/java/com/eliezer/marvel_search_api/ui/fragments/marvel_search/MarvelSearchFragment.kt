package com.eliezer.marvel_search_api.ui.fragments.marvel_search

import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.eliezer.marvel_search_api.core.base.BaseFragment
import com.eliezer.marvel_search_api.core.base.BaseViewModel
import com.eliezer.marvel_search_api.core.utils.errorDialog
import com.eliezer.marvel_search_api.core.utils.loadingDialog
import com.eliezer.marvel_search_api.core.utils.warningDialog
import com.eliezer.marvel_search_api.data.expand.isInternetConnected
import com.eliezer.marvel_search_api.data.expand.registerNetworkCallback
import com.eliezer.marvel_search_api.data.expand.unregisterNetworkCallback
import com.eliezer.marvel_search_api.domain.local_property.LocalAccount
import com.eliezer.marvel_search_api.data.mappers.mainActivity
import com.eliezer.marvel_search_api.databinding.FragmentMarvelSearchBinding
import com.eliezer.marvel_search_api.models.dataclass.UserAccount
import com.eliezer.marvel_search_api.ui.fragments.marvel_search.functionImp.MarvelSearchFunctionImplement
import com.eliezer.marvel_search_api.ui.fragments.marvel_search.viewmodel.MarvelSearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

@AndroidEntryPoint
class MarvelSearchFragment : BaseFragment<FragmentMarvelSearchBinding>(
    FragmentMarvelSearchBinding::inflate
) {
    //https://developer.android.com/identity/sign-in/credential-manager-siwg


    private val searchViewModel: MarvelSearchViewModel by viewModels()
    private var funImpl: MarvelSearchFunctionImplement? = null
    private val networkCallback = object : NetworkCallback() {
        override fun onAvailable(network: Network) {
            // Called when a network is available
            funImpl?.apply {
                enableSearchButtons()
                enableGoogleButtons()
            }
        }

        override fun onLost(network: Network) {
            // Called when a network is lost
            funImpl?.apply {
                disableSearchButtons()
                disableGoogleButtons()
                disableFavoriteButtons()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity(requireActivity()).setToolbarView(false)
        LocalAccount.userAccount.observe(this.viewLifecycleOwner, ::listenUserAccount)
        funImpl = MarvelSearchFunctionImplement(
            binding,
            searchViewModel,
            mainActivity(requireActivity()).navigationMainActions!!,
            viewLifecycleOwner
        )
        funImpl?.resetLists()
        funImpl?.buttonListener(requireContext())
        checkIsLogin()
        if (requireContext().isInternetConnected)
            funImpl?.enableSearchButtons()
        else
            funImpl?.disableSearchButtons()
        requireContext().registerNetworkCallback(networkCallback)
    }

    private fun listenUserAccount(userAccount: UserAccount?) {
        userAccount?.also{
            funImpl?.enableFavoriteButtons()
        } ?: funImpl?.disableFavoriteButtons()
    }


    private fun checkIsLogin() {
        LocalAccount.userAccount.value?.also{
            funImpl?.enableFavoriteButtons()
        } ?: funImpl?.disableFavoriteButtons()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        requireContext().unregisterNetworkCallback(networkCallback)
        funImpl = null
    }
    override fun addViewModel(): BaseViewModel {
        return searchViewModel
    }
}