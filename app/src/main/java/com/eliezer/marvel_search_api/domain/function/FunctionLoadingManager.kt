package com.eliezer.marvel_search_api.domain.function

import android.content.Context
import com.eliezer.marvel_search_api.domain.alert_dialogs.loadingDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FunctionLoadingManager(context: Context) {
    private var  operationsComplete  = 0
    private var pendingCancel = false
    private val loadingDialog =  loadingDialog(context)
    fun sumOperationsComplete(valor : Int){
        operationsComplete += valor
    }
    fun  showLoadingDialog() =
        CoroutineScope(Dispatchers.Main).launch {
            if(!pendingCancel)
                loadingDialog.show()
            pendingCancel = false
        }.start()

    fun stopLoading(condition: Int) {
        if(operationsComplete == condition) {
            loadingDialog.cancel()
            operationsComplete = 0
        }
    }
    fun stopLoading() {
        if(loadingDialog.isShowing)
            loadingDialog.cancel()
        else
            pendingCancel = true
    }

    fun isShowing() =
            loadingDialog.isShowing
}