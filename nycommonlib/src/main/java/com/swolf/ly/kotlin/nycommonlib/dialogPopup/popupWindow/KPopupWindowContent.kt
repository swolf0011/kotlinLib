package com.swolf.ly.kotlin.nycommonlib.dialogPopup.popupWindow

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import com.swolf.ly.kotlin.nycommonlib.R

class KPopupWindowContent {


    fun show(
        context: Context, clickParentView: View, contentView: View, title: String,
        callback: IHandlerCallback?, listener: PopupWindow.OnDismissListener
    ) {
        val pop = KPopupWindowUtil()
        val view = LayoutInflater.from(context).inflate(R.layout.ny_popupwindow_content, null)
        val tv_cancel = view.findViewById(R.id.tv_cancel) as TextView
        val tv_title = view.findViewById(R.id.tv_title) as TextView
        val tv_confirm = view.findViewById(R.id.tv_confirm) as TextView
        val ll_content = view.findViewById(R.id.ll_content) as LinearLayout
        tv_title.text = title
        ll_content.removeAllViews()
        ll_content.addView(contentView)

        tv_cancel.setOnClickListener {
            pop.dismiss()
            callback?.cancel()
        }
        tv_confirm.setOnClickListener {
            pop.dismiss()

            callback?.confirm()
        }

        pop.showScreenBottom(
            clickParentView,
            view,
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT,
            0,
            0,
            listener
        )
    }


    interface IHandlerCallback {
        fun cancel()

        fun confirm()
    }

}
