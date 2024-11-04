package com.eliezer.marvel_search_api.domain.function

import android.content.Context
import com.eliezer.marvel_search_api.domain.alert_dialogs.loadingDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FunctionLoadingManager(context: Context) {
    private var  operationsComplete  = 0
    private val loadingDialog =  loadingDialog(context)
    fun sumOperationsComplete(valor : Int){
        operationsComplete += valor
    }
    fun  showLoadingDialog() =
        CoroutineScope(Dispatchers.Main).launch {
            loadingDialog.show()
        }.start()

    fun stopLoading(condition: Int) {
        if(operationsComplete == condition)
            loadingDialog.cancel()
    }
    fun stopLoading() =
            loadingDialog.cancel()

    fun isShowing() =loadingDialog.isShowing
}