package com.eliezer.marvel_characters.core.base

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<T : Activity,S : ViewBinding>(private val bindingInflater: (layoutInflater: LayoutInflater) -> S) : Fragment() {
    private var _baseActivity: T? = null
    val baseActivity get() = _baseActivity

    private var _binding: S? = null
    val binding get() = _binding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _baseActivity = activity as T
        _binding = this.bindingInflater.invoke(inflater)
        return binding?.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _baseActivity = null
        _binding = null
    }
}