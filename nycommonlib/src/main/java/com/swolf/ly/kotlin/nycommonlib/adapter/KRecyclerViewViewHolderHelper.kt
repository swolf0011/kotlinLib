package com.swolf.ly.kotlin.nycommonlib.adapter

import android.util.SparseArray
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class KRecyclerViewViewHolderHelper(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val mViews = SparseArray<View>()
    /**
     * 根据id获取控件
     * @param viewId 控件id
     * @return
     */
    fun <T : View> getView(viewId: Int): T {
        var view: View = mViews.get(viewId)
        if (null == view) {
            view = itemView.findViewById(viewId)
            mViews.put(viewId, view)
        }
        return view as T
    }
}