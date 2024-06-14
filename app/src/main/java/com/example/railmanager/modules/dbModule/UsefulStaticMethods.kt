package com.example.railmanager.modules.dbModule

import android.app.AlertDialog
import android.content.Context

class UsefulStaticMethods {
    companion object {
        fun showSimpleAlertDialog(context: Context, message: String) {
            AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton("OK") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }

}