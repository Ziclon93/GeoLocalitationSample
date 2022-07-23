package com.example.freenowtest.util

import android.os.Handler
import android.os.Looper

fun (() -> Unit).withDelay(delay: Long) {
    Handler(Looper.getMainLooper()).postDelayed(this, delay)
}
