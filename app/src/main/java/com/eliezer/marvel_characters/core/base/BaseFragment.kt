package com.eliezer.marvel_characters.core.base

import android.os.Bundle
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
        setViewModel()
        observeViewModels()
    }

    private fun showError(throwable: Throwable?) {
        //Todo con una ventana que sale mostrando el error con letras rojas ej: no se ha encontrado el servidor
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