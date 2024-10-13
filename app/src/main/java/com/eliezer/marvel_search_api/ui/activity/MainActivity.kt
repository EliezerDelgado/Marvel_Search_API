package com.eliezer.marvel_search_api.ui.activity

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.viewModels
import com.eliezer.marvel_search_api.data.firebase.services.MyFireStoreInserts
import com.eliezer.marvel_search_api.data.firebase.services.MyFirebaseAnalytics
import com.eliezer.marvel_search_api.data.firebase.services.MyFireStoreSelects
import com.eliezer.marvel_search_api.databinding.ActivityMainBinding
import com.eliezer.marvel_search_api.domain.actions.NavigationMainActions
import com.eliezer.marvel_search_api.ui.activity.funtionImp.MainActivityFunctionImplement
import com.eliezer.marvel_search_api.ui.activity.viewmodel.MainActivityViewModel
import com.eliezer.marvel_search_api.ui.fragments.comic_list.functionImp.ComicsListFunctionImplement
import com.eliezer.marvel_search_api.ui.fragments.marvel_search.viewmodel.MarvelSearchViewModel
import com.google.android.material.appbar.AppBarLayout
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _navigationMainActions: NavigationMainActions? = null
    val navigationMainActions get() = _navigationMainActions

    private lateinit var firebaseAnalytics: MyFirebaseAnalytics
    private var binding : ActivityMainBinding? = null
    private val viewModel: MainActivityViewModel by viewModels()
    private var funImpl : MainActivityFunctionImplement? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        // Obtain the FirebaseAnalytics instance
        firebaseAnalytics = MyFirebaseAnalytics(Firebase.analytics)
        setContentView(binding!!.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding!!.mainCoordinatorLayout) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        funImpl = binding?.let { MainActivityFunctionImplement(it,viewModel,this)}
        _navigationMainActions = NavigationMainActions(binding!!.mainNavHostFragment)
        funImpl?.setMainToolbar()
        getLocalUser()
        listeningChangesInAuthResult()
    }

    private fun listeningChangesInAuthResult() {
        funImpl?.listeningChangesInAuthResult()
    }

    private fun getLocalUser() =     funImpl?.getLocalUserCredential()

    fun setToolbarView(visibility : Boolean) =     funImpl?.setToolbarView(visibility)
    fun getToolBar() = binding?.mainToolbar
    fun getSubToolBar() = binding?.mainSubToolbar

    fun getSubToolBarEditText() = binding?.mainSubToolbarEditText

    fun setSubToolbarView(visibility: Boolean) = funImpl?.setSubToolbarView(visibility)

    override fun onDestroy() {
        super.onDestroy()
        _navigationMainActions = null
        funImpl = null
        binding = null
    }
}