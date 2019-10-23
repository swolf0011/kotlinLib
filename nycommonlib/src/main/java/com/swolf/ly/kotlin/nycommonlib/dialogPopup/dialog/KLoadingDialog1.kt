package com.swolf.ly.kotlin.nycommonlib.dialogPopup.dialog

import android.app.Activity
import android.app.Dialog
import android.graphics.drawable.AnimationDrawable
import android.util.DisplayMetrics
import android.widget.ImageView
import android.widget.RelativeLayout
import com.swolf.ly.kotlin.nycommonlib.R

object KLoadingDialog1 {
    private var dialog: Dialog? = null

    @JvmOverloads
    fun show(activity: Activity, isCancelable: Boolean = false) {
        val wrap_content = RelativeLayout.LayoutParams.WRAP_CONTENT
        val match_parent = RelativeLayout.LayoutParams.MATCH_PARENT
        val pl0 = RelativeLayout.LayoutParams(match_parent, match_parent)
        val rl = RelativeLayout(activity)
        rl.layoutParams = pl0

        val iv = ImageView(activity)
        val pl = RelativeLayout.LayoutParams(wrap_content * 4, wrap_content * 4)
        pl.addRule(RelativeLayout.CENTER_IN_PARENT)

        rl.addView(iv)

        iv.setImageResource(R.drawable.ny_loading_dialog_anim)

        val animationDrawable = iv.drawable as AnimationDrawable
        animationDrawable.start()

        dialog = Dialog(activity, R.style.ny_loading_dialog)
        dialog!!.setCancelable(isCancelable)
        dialog!!.setCanceledOnTouchOutside(false)
        dialog!!.setContentView(rl)

        val metric = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(metric)

        val lp = dialog!!.window!!.attributes
        lp.width = (metric.widthPixels * 0.2).toInt()//这改变dialog大小。0.2倍大小
        lp.height = (metric.heightPixels * 0.2).toInt()//这改变dialog大小。0.2倍大小
        dialog!!.window!!.attributes = lp
        dialog!!.show()
    }

    fun dismiss() {
        if (null != dialog && dialog!!.isShowing) {
            dialog!!.dismiss()
            dialog = null
        }
    }
}
