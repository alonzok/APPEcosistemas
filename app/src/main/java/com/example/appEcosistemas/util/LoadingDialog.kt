package com.example.appEcosistemas.util

import android.app.Activity
import android.app.AlertDialog
import android.util.Log
import android.widget.TextView
import com.example.appEcosistemas.R
import java.lang.Exception

class LoadingDialog(private val activity: Activity) {
    private lateinit var isDialog: AlertDialog

    fun startLoading(message: String) {
        /** Set View */
        val inflater = activity.layoutInflater
        val dialogView = inflater.inflate(R.layout.loading_item, null)
        val mensaje = dialogView.findViewById<TextView>(R.id.tv_espere)
        mensaje.text = message

        /** Set Dialog */
        val builder = AlertDialog.Builder(activity)
        builder.setView(dialogView)
        builder.setCancelable(false)
        isDialog = builder.create()


        try {
            isDialog.show()
        } catch (e: Exception) {
            Log.e("ERROR", e.toString())
        }
    }

    fun isLoading(): Boolean {
        return if (::isDialog.isInitialized) {
            isDialog.isShowing
        } else {
            false
        }
    }

    fun isDismiss() {
        try {
            if (::isDialog.isInitialized) {
                isDialog.dismiss()
            }
        } catch (e: Exception) {
            Log.e("ERROR", e.toString())
        }
    }
}