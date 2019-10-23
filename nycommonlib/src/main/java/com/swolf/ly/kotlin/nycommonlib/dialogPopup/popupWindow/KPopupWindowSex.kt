package com.swolf.ly.kotlin.nycommonlib.dialogPopup.popupWindow

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import com.swolf.ly.kotlin.nycommonlib.R

class KPopupWindowSex {


    fun show(
        context: Context, clickParentView: View,
        callback: IHandlerCallback?, listener: PopupWindow.OnDismissListener
    ) {
        val pop = KPopupWindowUtil()
        val view = LayoutInflater.from(context).inflate(R.layout.ny_popupwindow_sex, null)
        val tv_female = view.findViewById(R.id.tv_female) as TextView
        val tv_male = view.findViewById(R.id.tv_male) as TextView

        tv_female.setOnClickListener {
            pop.dismiss()
            callback?.female("女")
        }
        tv_male.setOnClickListener {
            pop.dismiss()
            callback?.male("男")
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
        fun male(`val`: String)

        fun female(female: String)
    }

}
