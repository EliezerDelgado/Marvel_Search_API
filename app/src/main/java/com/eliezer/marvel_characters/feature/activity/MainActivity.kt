package com.eliezer.marvel_characters.feature.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.eliezer.marvel_characters.io.actions.NavigationMainActions
import com.eliezer.marvel_characters.R
import com.eliezer.marvel_characters.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _navigationMainActions: NavigationMainActions? = null
    val navigationMainActions get() = _navigationMainActions
    private var binding : ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        _navigationMainActions = NavigationMainActions(binding!!.navHostFragment)
    }

    override fun onDestroy() {
        super.onDestroy()
        _navigationMainActions = null
        binding = null
    }
}