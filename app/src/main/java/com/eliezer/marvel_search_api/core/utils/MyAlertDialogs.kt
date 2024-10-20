package com.eliezer.marvel_search_api.core.utils

import android.app.Activity
import androidx.appcompat.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.library.baseAdapters.BR
import com.eliezer.marvel_search_api.R
import com.eliezer.marvel_search_api.databinding.AlertDialogMessageBinding
import com.eliezer.marvel_search_api.databinding.AlertDialogUserInformationBinding
import com.eliezer.marvel_search_api.models.dataclass.UserAccount
import com.google.android.material.dialog.MaterialAlertDialogBuilder


fun userDialog(
    context: Context,
    userAccount: UserAccount,
    signOutOnClick: ()-> Unit,
): AlertDialog {
    val inflater = LayoutInflater.from(context)
    val binding = AlertDialogUserInformationBinding.inflate(inflater)
    val alert = createSimpleDialogWithViewRounded(context,binding.root,null)
    setUserBindings(binding,alert,userAccount,signOutOnClick)
    return alert
}

fun errorDialog(
    context: Context,
    message : String
): AlertDialog = messageAlertDialog(context,message,R.drawable.img_error)

fun warningDialog(
    context: Context,
    message : String
): AlertDialog = messageAlertDialog(context,message,R.drawable.img_warning)

private fun  messageAlertDialog(
    context: Context,
    message : String,
    @DrawableRes drawable : Int): AlertDialog
{
    val inflater = LayoutInflater.from(context)
    val binding = AlertDialogMessageBinding.inflate(inflater)
    binding.alertDialogMessageTextViewMessage.text  = message
    binding.alertDialogMessageImageViewIco.setImageDrawable(AppCompatResources.getDrawable(context,drawable))
    val alert = createSimpleDialogWithViewRounded(context,binding.root,context.resources.getString(R.string.positive_button_error_name))
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

private fun createSimpleDialogWithViewRounded(
    context: Context,
    view: View,
    confirmButtonText: String?
): AlertDialog {
    val builder = MaterialAlertDialogBuilder(context, R.style.EliezerMarvel_RoundedCornersDialog)
        .setView(view)
        .setCancelable(false)
    confirmButtonText?.also {
        builder.setPositiveButton(confirmButtonText) { dialog, _ ->
            dialog.dismiss()
        }
    }
    return builder.create()
}