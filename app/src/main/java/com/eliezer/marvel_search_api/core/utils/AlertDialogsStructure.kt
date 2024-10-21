package com.eliezer.marvel_search_api.core.utils

import androidx.appcompat.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.library.baseAdapters.BR
import com.eliezer.marvel_search_api.R
import com.eliezer.marvel_search_api.databinding.AlertDialogLoadingBinding
import com.eliezer.marvel_search_api.databinding.AlertDialogMessageBinding
import com.eliezer.marvel_search_api.databinding.AlertDialogUserInformationBinding
import com.eliezer.marvel_search_api.models.dataclass.UserAccount
import com.google.android.material.dialog.MaterialAlertDialogBuilder




fun  messageAlertDialog(
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


fun createSimpleDialogWithViewRounded(
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