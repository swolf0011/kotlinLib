package com.swolf.ly.kotlin.nycommonlib.dialogPopup.dialog

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.View
import android.view.Window

class KAlertDialogUtil {
    private var dialog: AlertDialog? = null

    /**
     * @param context
     * @param Title          标题：可以为null
     * @param view           视图
     * @param cancelable     返回键取消对话框
     * @param theme          对话框样式
     * @param cancel         取消：可以为null
     * @param cancelListener 取消监听：可以为null
     */
    fun show(
        context: Context,
        Title: String?,
        view: View,
        cancelable: Boolean,
        theme: Int,
        cancel: String?,
        cancelListener: DialogInterface.OnClickListener
    ) {
        var b: AlertDialog.Builder? = null
        if (theme > 0) {
            b = AlertDialog.Builder(context, theme)
        } else {
            b = AlertDialog.Builder(context)
        }
        b.setTitle(Title)
        b.setView(view)
        b.setCancelable(cancelable)
        if (cancel != null && cancel.length > 0) {
            b.setNegativeButton(cancel, cancelListener)
        }
        dialog = b.create()
        if (Title == null || "" == Title) {
            dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        }
        dialog!!.show()
    }

    /**
     * @param context
     * @param Title           标题：可以为null
     * @param message         消息
     * @param cancelable      返回键取消对话框
     * @param theme           对话框样式
     * @param cancel          取消：可以为null
     * @param cancelListener  取消监听：可以为null
     * @param confirm         确定：可以为null
     * @param confirmListener 确定监听：可以为null
     */
    fun show(
        context: Context,
        Title: String?,
        message: String,
        cancelable: Boolean,
        theme: Int,
        cancel: String?,
        cancelListener: DialogInterface.OnClickListener,
        confirm: String,
        confirmListener: DialogInterface.OnClickListener
    ) {
        var b: AlertDialog.Builder? = null
        if (theme > 0) {
            b = AlertDialog.Builder(context, theme)
        } else {
            b = AlertDialog.Builder(context)
        }
        b.setTitle(Title)
        b.setMessage(message)
        b.setCancelable(cancelable)
        if (cancel != null && cancel.length > 0) {
            b.setNegativeButton(cancel, cancelListener)
        }
        if (cancel != null && cancel.length > 0) {
            b.setPositiveButton(confirm, confirmListener)
        }
        dialog = b.create()
        if (Title == null || "" == Title) {
            dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        }
        dialog!!.show()
    }

    fun dismiss() {
        if (dialog != null && dialog!!.isShowing) {
            dialog!!.dismiss()
            dialog = null
        }
    }
}
