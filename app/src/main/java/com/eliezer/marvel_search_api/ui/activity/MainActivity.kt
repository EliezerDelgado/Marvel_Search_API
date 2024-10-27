package com.eliezer.marvel_search_api.ui.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.eliezer.marvel_search_api.databinding.ActivityMainBinding
import com.eliezer.marvel_search_api.domain.actions.NavigationMainActions
import com.eliezer.marvel_search_api.ui.activity.funtionImp.MainActivityFunctionImplement
import com.eliezer.marvel_search_api.ui.activity.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _navigationMainActions: NavigationMainActions? = null
    val navigationMainActions get() = _navigationMainActions

    private var binding : ActivityMainBinding? = null
    private val viewModel: MainActivityViewModel by viewModels()
    private var funImpl : MainActivityFunctionImplement? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding!!.mainCoordinatorLayout) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        _navigationMainActions = NavigationMainActions(binding!!.mainNavHostFragment)
        implementFunctions()
    }

    private fun implementFunctions() {
        funImpl = binding?.let {
            MainActivityFunctionImplement(
                binding =  it,
                viewModel = viewModel,
                owner = this,
                context = this
            )
        }
        funImpl?.errorListener()
        funImpl?.setMainToolbar()
        listeningChangesInAuthResult()
        getLocalUser()
    }


    private fun listeningChangesInAuthResult() {
       funImpl?.listeningChangesInUserAccount()
    }

    private fun getLocalUser() = funImpl?.getLocalUser()

    fun setToolbarView(visibility : Boolean) =     funImpl?.setToolbarView(visibility)
    fun getToolBar() = binding?.mainToolbar
    fun getSubToolBar() = binding?.mainSubToolbar

    fun getSubToolBarEditText() = binding?.mainSubToolbarEditText

    fun setSubToolbarView(visibility: Boolean) = funImpl?.setSubToolbarView(visibility)

    override fun onDestroy() {
        super.onDestroy()
        _navigationMainActions = null
        funImpl?.stopErrorListener()
        funImpl = null
        binding = null
    }
}