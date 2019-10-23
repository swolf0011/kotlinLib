package com.swolf.ly.kotlin.nycommonlib.dialogPopup.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.View
import android.view.Window

class KDialogUtil {
    private var dialog: Dialog? = null

    /**
     * @param context
     * @param view
     * @param cancelable      返回键取消对话框
     * @param theme           对话框样式
     * @param dismissListener 解散事件
     */
    fun show(
        context: Context,
        view: View,
        cancelable: Boolean,
        theme: Int,
        dismissListener: DialogInterface.OnDismissListener
    ) {
        if (theme > 0) {
            dialog = Dialog(context, theme)
        } else {
            dialog = Dialog(context)
        }
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setContentView(view)
        dialog!!.setCancelable(cancelable)
        dialog!!.setOnDismissListener(dismissListener)
        dialog!!.show()
    }

    fun dismiss() {
        if (dialog != null && dialog!!.isShowing) {
            dialog!!.dismiss()
            dialog = null
        }
    }
}