package com.swolf.ly.kotlin.nycommonlib.util

import android.content.Context
import android.widget.Toast

class KToastUtil private constructor() {
    companion object {
        private object KSubHolder {
            val util = KToastUtil()
        }

        val instance: KToastUtil
            get() = KSubHolder.util
    }

    fun show(context: Context, resId: Int) {
        Toast.makeText(context, resId, Toast.LENGTH_SHORT).show()
    }

    fun show(context: Context, text: CharSequence) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}
