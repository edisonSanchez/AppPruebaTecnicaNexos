package com.edisonsanchez.apppruebatecnicanexos.data

import android.app.Service
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import com.edisonsanchez.apppruebatecnicanexos.R

fun hideKeyBoard(context: Context, view: View) {
    val inputMethodManager = context.getSystemService(Service.INPUT_METHOD_SERVICE)
            as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun showSimpleAlertDialog(context: Context, message: String) {
    val alertDialog = AlertDialog.Builder(context)
        .setMessage(message)
        .setPositiveButton(context.getString(R.string.text_button_ok)) { dialog, _ -> dialog.dismiss() }
        .create()
    alertDialog.show()
}