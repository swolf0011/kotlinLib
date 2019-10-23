package com.swolf.ly.kotlin.nycommonlib.dialogPopup.popupWindow

import android.graphics.drawable.BitmapDrawable
import android.view.Gravity
import android.view.View
import android.widget.PopupWindow
import com.swolf.ly.kotlin.nycommonlib.R


class KPopupWindowUtil {
    internal var popupWindow: PopupWindow? = null

    /**
     * 获取PopupWindow
     */
    private fun initPopupWindow(
        popupView: View,
        width: Int,
        height: Int,
        animationStyle: Int,
        listener: PopupWindow.OnDismissListener?
    ) {
        popupWindow = PopupWindow(popupView, width, height, true)
        popupWindow!!.isTouchable = true
        popupWindow!!.isOutsideTouchable = true
        popupWindow!!.setOnDismissListener(listener)
        popupWindow!!.setBackgroundDrawable(BitmapDrawable())
        popupWindow!!.animationStyle = animationStyle
    }

    /**
     * 显示View下方的PopupWindow
     */
    fun showViewBottom(
        clickParentView: View,
        view: View,
        width: Int,
        height: Int,
        xoff: Int,
        yoff: Int,
        listener: PopupWindow.OnDismissListener
    ) {
        initPopupWindow(view, width, height, -1, listener)
        val location = IntArray(2)
        clickParentView.getLocationOnScreen(location)
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        val popupWidth = view.measuredWidth
        val popupHeight = view.measuredHeight

        popupWindow!!.showAsDropDown(
            clickParentView,
            location[0] + (clickParentView.width - popupWidth) / 2 + xoff,
            yoff
        )
    }

    /**
     * 显示View上方的PopupWindow
     */
    fun showViewTop(
        clickParentView: View,
        view: View,
        width: Int,
        height: Int,
        xoff: Int,
        yoff: Int,
        listener: PopupWindow.OnDismissListener
    ) {
        initPopupWindow(view, width, height, -1, listener)
        val location = IntArray(2)
        clickParentView.getLocationOnScreen(location)

        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        val popupWidth = view.measuredWidth
        val popupHeight = view.measuredHeight
        popupWindow!!.showAtLocation(
            clickParentView, Gravity.NO_GRAVITY, location[0] + (clickParentView.width - popupWidth) / 2 + xoff,
            location[1] - popupHeight + yoff
        )
    }

    /**
     * 显示屏幕底部的PopupWindow
     */
    fun showScreenBottom(
        clickParentView: View,
        view: View,
        width: Int,
        height: Int,
        xoff: Int,
        yoff: Int,
        listener: PopupWindow.OnDismissListener?
    ) {
        initPopupWindow(view, width, height, R.style.ny_screen_bottombar_popupwindow_anim, listener)
        popupWindow!!.showAtLocation(clickParentView, Gravity.BOTTOM, xoff, yoff)
    }

    /**
     * 显示屏幕中间的PopupWindow
     */
    fun showScreenCenter(
        clickParentView: View,
        view: View,
        width: Int,
        height: Int,
        xoff: Int,
        yoff: Int,
        listener: PopupWindow.OnDismissListener
    ) {
        initPopupWindow(view, width, height, R.style.ny_screen_bottombar_popupwindow_anim, listener)
        popupWindow!!.showAtLocation(clickParentView, Gravity.CENTER, xoff, yoff)
    }

    /**
     * 解散dismissPopupWindow
     */
    fun dismiss() {
        if (popupWindow != null) {
            popupWindow!!.dismiss()
            popupWindow = null
        }
    }

}
