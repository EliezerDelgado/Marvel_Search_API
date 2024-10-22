package com.eliezer.marvel_search_api.domain.alert_dialogs

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.eliezer.marvel_search_api.R
import com.eliezer.marvel_search_api.core.utils.createSimpleDialogWithViewRounded
import com.eliezer.marvel_search_api.core.utils.loadImageFromWebOperations
import com.eliezer.marvel_search_api.core.utils.messageAlertDialog
import com.eliezer.marvel_search_api.databinding.AlertDialogLoadingBinding
import com.eliezer.marvel_search_api.databinding.AlertDialogUserInformationBinding
import com.eliezer.marvel_search_api.models.dataclass.UserAccount
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


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
): AlertDialog = messageAlertDialog(context,message, R.drawable.img_error)

fun warningDialog(
    context: Context,
    message : String
): AlertDialog = messageAlertDialog(context,message, R.drawable.img_warning)

fun loadingDialog(
    context: Context
): AlertDialog {
    val inflater = LayoutInflater.from(context)
    val binding = AlertDialogLoadingBinding.inflate(inflater)
    val alert = createSimpleDialogWithViewRounded(context,binding.root,null)
    return alert
}

private fun setUserBindings(
    binding: AlertDialogUserInformationBinding,
    alert: AlertDialog,
    userAccount: UserAccount,
    signOutOnClick: () -> Unit
) {
    binding.alertDialogUserInformationTextViewName.text = userAccount.name
    binding.alertDialogUserInformationTextViewEmail.text = userAccount.email
    binding.alertDialogUserInformationButtonSignOut.setOnClickListener {
        signOutOnClick.invoke()
        alert.cancel()
    }
    binding.alertDialogUserInformationButtonClose.setOnClickListener {
        alert.cancel()
    }
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val url = userAccount.photoUrl.toString()
            binding.alertDialogUserInformationImageViewPhoto.setImageDrawable(
                loadImageFromWebOperations(url)
            )
        }
        catch (e: Exception){
             //ToDO
        }
    }
}