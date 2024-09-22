package com.eliezer.marvel_search_api.ui.activity

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.eliezer.marvel_search_api.data.firebase.services.MyFirebaseAnalytics
import com.eliezer.marvel_search_api.databinding.ActivityMainBinding
import com.eliezer.marvel_search_api.domain.actions.NavigationMainActions
import com.google.android.material.appbar.AppBarLayout
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _navigationMainActions: NavigationMainActions? = null
    val navigationMainActions get() = _navigationMainActions
    private lateinit var firebaseAnalytics: MyFirebaseAnalytics
    private lateinit var auth: FirebaseAuth
    private var binding : ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        // Obtain the FirebaseAnalytics instance
        firebaseAnalytics = MyFirebaseAnalytics(Firebase.analytics)
        auth = Firebase.auth
        setContentView(binding!!.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding!!.mainCoordinatorLayout) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
       _navigationMainActions = NavigationMainActions(binding!!.mainNavHostFragment)
        binding?.mainToolbar?.bringToFront()
        binding?.mainSubToolbar?.bringToFront()
    }
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
           // reload()
        }
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