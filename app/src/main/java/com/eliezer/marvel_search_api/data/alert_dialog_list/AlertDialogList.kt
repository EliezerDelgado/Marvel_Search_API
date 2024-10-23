@file:OptIn(InternalCoroutinesApi::class)

package com.eliezer.marvel_search_api.data.alert_dialog_list

import androidx.appcompat.app.AlertDialog
import kotlinx.coroutines.InternalCoroutinesApi

class AlertDialogShow {
    @Synchronized
    fun showAlertDialog(alertDialog: AlertDialog) {
        alertDialog.show()
    }
}