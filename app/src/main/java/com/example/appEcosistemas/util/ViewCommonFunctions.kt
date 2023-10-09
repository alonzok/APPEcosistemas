package com.example.appEcosistemas.util

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import com.example.appEcosistemas.R

object ViewCommonFunctions {

    fun showAlertDialogInformative(context: Context, title: String, message: String) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton(R.string.accept) { dialog, _ -> dialog.dismiss() }
            .create().show()
    }

    fun showAlertDialogDesicion(
        context: Context, titleResource: Int, messageResource: Int,
        confirmMessage: Int, cancelMessage: Int,
        positive: DialogInterface.OnClickListener,
        negative: DialogInterface.OnClickListener
    ) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(titleResource)
            .setMessage(messageResource)
            .setPositiveButton(confirmMessage, positive)
            .setNegativeButton(cancelMessage, negative)
            .create().show()
    }

    fun showAlertDialogAccept(
        context: Context, titleResource: Int, messageResource: Int,
        confirmMessage: Int,
        positive: DialogInterface.OnClickListener
    ) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(titleResource)
            .setCancelable(false)
            .setMessage(messageResource)
            .setPositiveButton(confirmMessage, positive)
            .create().show()
    }

    fun getCommonCancelDialogListener(): DialogInterface.OnClickListener {
        return DialogInterface.OnClickListener { dialog, _ -> dialog.dismiss() }
    }
}