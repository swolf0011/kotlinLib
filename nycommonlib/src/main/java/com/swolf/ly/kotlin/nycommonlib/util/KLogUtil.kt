package com.swolf.ly.kotlin.nycommonlib.util

import android.util.Log

object KLogUtil {
    var tag = "KLogUtil"

    fun i(msg: String) {
        Log.i(tag, msg)
    }

    fun d(msg: String) {
        Log.d(tag, msg)
    }

    fun e(msg: String) {
        Log.e(tag, msg)
    }

    fun v(msg: String) {
        Log.v(tag, msg)
    }
}
