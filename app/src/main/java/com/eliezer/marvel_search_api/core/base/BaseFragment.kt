package com.eliezer.marvel_search_api.core.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

open class BaseFragment<VB : ViewBinding>(private val bindingInflater: (layoutInflater: LayoutInflater) -> VB) : Fragment() {

    private var _binding: VB? = null
    val binding get() = _binding!!

    private var _viewModel : BaseViewModel? = null
    val viewModel get() = _viewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = this.bindingInflater.invoke(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModels()
    }

    private fun showError(throwable: Throwable) {
        Log.e("***",throwable.message.toString(),throwable)
    }

    private fun observeViewModels()
     {
         _viewModel?.error.also {
             it?.observe(viewLifecycleOwner, this::showError)
         }
         _viewModel?.loading?.observe(viewLifecycleOwner,this::loading)
     }

    private fun loading(loading: Boolean?) {
        if(loading == true)
            startLoading()
        else
            stopLoading()
    }
    private fun setViewModel()
    {
        _viewModel = addViewModel()
    }
    protected  open fun addViewModel() :BaseViewModel?=null

    protected open fun stopLoading() {}

    protected open fun startLoading() {}

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _viewModel = null
    }
}