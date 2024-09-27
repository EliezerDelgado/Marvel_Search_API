package com.eliezer.marvel_search_api.ui.activity

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.eliezer.marvel_search_api.R
import com.eliezer.marvel_search_api.data.firebase.configuration.GoogleDataStoreConfiguration
import com.eliezer.marvel_search_api.data.firebase.services.MyFirebaseAnalytics
import com.eliezer.marvel_search_api.data.firebase.services.MyGoogleDataStoreSelects
import com.eliezer.marvel_search_api.databinding.ActivityMainBinding
import com.eliezer.marvel_search_api.domain.actions.NavigationMainActions
import com.google.android.material.appbar.AppBarLayout
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import java.io.InputStream

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _navigationMainActions: NavigationMainActions? = null
    val navigationMainActions get() = _navigationMainActions
    private lateinit var firebaseAnalytics: MyFirebaseAnalytics
    private var binding : ActivityMainBinding? = null

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
       _navigationMainActions = NavigationMainActions(binding!!.mainNavHostFragment)
        binding?.mainToolbar?.bringToFront()
        binding?.mainSubToolbar?.bringToFront()
        setGoogleDataStore()
    }

    private fun setGoogleDataStore() {
        Thread{
            GoogleDataStoreConfiguration.setFiresStore()
            /*
            MyGoogleDataStoreInserts().insertCharacter("1","1")
            MyGoogleDataStoreInserts().insertCharacter("1","2")
            MyGoogleDataStoreInserts().insertCharacter("1","3")
            MyGoogleDataStoreInserts().insertCharacter("2","4")
            */
            MyGoogleDataStoreSelects().getCharacterId("1")
        }.start()
    }

    fun setToolbarView(visibility : Boolean)
    {
        binding?.mainToolbar?.visibility = if(visibility) View.VISIBLE else View.GONE
        getCoordinatorLayoutParams().behavior = if(visibility) AppBarLayout.ScrollingViewBehavior() else null
        binding?.mainSubToolbar?.visibility = View.GONE
        if(!visibility)
            binding?.mainCoordinatorLayout?.requestLayout()
    }
    fun getToolBar() = binding?.mainToolbar
    fun getSubToolBar() = binding?.mainSubToolbar

    fun getSubToolBarEditText() = binding?.mainSubToolbarEditText

    fun setSubToolbarView(visibility: Boolean)
    {
        binding?.mainSubToolbar?.visibility = if(visibility) View.VISIBLE else View.GONE
    }
    private fun getCoordinatorLayoutParams() =  binding?.mainConstraintLayout?.layoutParams as CoordinatorLayout.LayoutParams

    override fun onDestroy() {
        super.onDestroy()
        _navigationMainActions = null
        binding = null
    }
}