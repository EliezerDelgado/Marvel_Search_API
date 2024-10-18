package com.eliezer.marvel_search_api.core.utils

import android.app.Activity
import androidx.appcompat.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.library.baseAdapters.BR
import com.eliezer.marvel_search_api.R
import com.eliezer.marvel_search_api.databinding.AlertDialogUserInformationBinding
import com.eliezer.marvel_search_api.models.dataclass.UserAccount
import com.google.android.material.dialog.MaterialAlertDialogBuilder


fun userDialog(
    context: Context,
    userAccount: UserAccount,
    signOutOnClick: ()-> Unit,
): AlertDialog {
    val inflater = LayoutInflater.from(context)
    val builder = MaterialAlertDialogBuilder(context, R.style.EliezerMarvel_RoundedCornersDialog)
    val binding = AlertDialogUserInformationBinding.inflate(inflater)
    val alert = createDialogWithView(builder,binding.root)
    setUserBindings(binding,alert,userAccount,signOutOnClick)
    return alert
}
private fun setUserBindings(
    binding: AlertDialogUserInformationBinding,
    alert: AlertDialog,
    userAccount: UserAccount,
    signOutOnClick: () -> Unit
) {
    binding.setVariable(BR.item,userAccount)
    binding.alertDialogUserInformationButtonSignOut.setOnClickListener {
        signOutOnClick.invoke()
        alert.cancel()
    }
    binding.alertDialogUserInformationButtonClose.setOnClickListener {
        alert.cancel()
    }
    val t = Thread {
        try {
            val url = userAccount.photoUrl.toString()
            binding.alertDialogUserInformationImageViewPhoto.setImageDrawable(loadImageFromWebOperations(url))
        }
        catch (e: Exception){
            e.toString()
        }
    }
    t.start()
}

private fun createDialogWithView(
    builder: MaterialAlertDialogBuilder,
    view: View
): AlertDialog {
    builder.setView(view)
        .setCancelable(false)
    return builder.create()
}
fun createSimpleInformativeDialogWithOnCLickListener(
    activity: Activity,
    title: String?,
    confirmButtonText: String?,
    listener: DialogInterface.OnClickListener,
): AlertDialog {
    val builder = AlertDialog.Builder(activity.baseContext)
    builder.setTitle(title)
        .setPositiveButton(confirmButtonText) { dialog: DialogInterface, which: Int ->
            dialog.dismiss()
            listener.onClick(dialog, which)
        }
        .setCancelable(false)
    return builder.create()
}