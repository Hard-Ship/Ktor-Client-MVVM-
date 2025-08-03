package com.app.ktorclientmvvm.util

import android.widget.Toast
import com.app.ktorclientmvvm.MyApplication

fun showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(MyApplication.getInstance(), message, duration).show()
}