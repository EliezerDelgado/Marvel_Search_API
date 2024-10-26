package com.eliezer.marvel_search_api.ui.fragments.marvel_search

import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.eliezer.marvel_search_api.core.base.BaseFragment
import com.eliezer.marvel_search_api.core.base.BaseViewModel
import com.eliezer.marvel_search_api.data.expand.isInternetConnected
import com.eliezer.marvel_search_api.data.expand.registerNetworkCallback
import com.eliezer.marvel_search_api.data.expand.unregisterNetworkCallback
import com.eliezer.marvel_search_api.domain.local_property.LocalAccount
import com.eliezer.marvel_search_api.data.mappers.mainActivity
import com.eliezer.marvel_search_api.databinding.FragmentMarvelSearchBinding
import com.eliezer.marvel_search_api.models.dataclass.UserAccount
import com.eliezer.marvel_search_api.ui.fragments.marvel_search.functionImp.MarvelSearchFunctionImplement
import com.eliezer.marvel_search_api.ui.fragments.marvel_search.functionImp.function.MarvelSearchFunctionRepositoryManager
import com.eliezer.marvel_search_api.ui.fragments.marvel_search.viewmodel.MarvelSearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MarvelSearchFragment : BaseFragment<FragmentMarvelSearchBinding>(
    FragmentMarvelSearchBinding::inflate
) {


    private val searchViewModel: MarvelSearchViewModel by viewModels()
    private var funImpl: MarvelSearchFunctionImplement? = null

    @Inject
    lateinit var marvelSearchFunctionRepositoryManager: MarvelSearchFunctionRepositoryManager

    private val networkCallback = object : NetworkCallback() {
        override fun onAvailable(network: Network) {
            // Called when a network is available
            funImpl?.apply {
                enableSearchButtons()
                enableGoogleButtons()
                checkFavorite()
            }
        }

        override fun onLost(network: Network) {
            // Called when a network is lost
            funImpl?.apply {
                disableSearchButtons()
                disableGoogleButtons()
                disableFavoriteButtons()
                showWarningNetworkLost()
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity(requireActivity()).setToolbarView(false)
        LocalAccount.userAccount.observe(this.viewLifecycleOwner, ::listenUserAccount)
        funImpl = MarvelSearchFunctionImplement(
            binding =  binding,
            viewModel = searchViewModel,
            marvelSearchFunctionRepositoryManager = marvelSearchFunctionRepositoryManager,
            navigationMainActions = mainActivity(requireActivity()).navigationMainActions!!,
            owner = viewLifecycleOwner,
            context = requireContext()
        )
        funImpl?.resetAll()
        funImpl?.errorListener()
        funImpl?.buttonListener(requireContext())
        checkIsLogin()
        if (requireContext().isInternetConnected)
            funImpl?.enableSearchButtons()
        else
            funImpl?.disableSearchButtons()
        requireContext().registerNetworkCallback(networkCallback)
    }

    override fun addViewModel(): BaseViewModel = searchViewModel

    private fun listenUserAccount(userAccount: UserAccount?) =
        userAccount?.also{
            funImpl?.enableFavoriteButtons()
        } ?: funImpl?.disableFavoriteButtons()

    private fun checkIsLogin() =
        LocalAccount.userAccount.value?.also{
            funImpl?.enableFavoriteButtons()
        } ?: funImpl?.disableFavoriteButtons()

    override fun onDestroyView() {
        super.onDestroyView()
        requireContext().unregisterNetworkCallback(networkCallback)
        funImpl?.stopErrorListener()
        funImpl = null
    }
}