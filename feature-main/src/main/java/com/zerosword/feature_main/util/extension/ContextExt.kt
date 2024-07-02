package com.zerosword.feature_main.util.extension

import android.content.Context
import android.widget.Toast
import androidx.core.content.res.TypedArrayUtils.getText

fun Context.toast(message: CharSequence, lengthState: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(
        this,
        message,
        lengthState
    ).show()
}

fun Context.toast(message: String, lengthState: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(
        this,
        message,
        lengthState
    ).show()
}