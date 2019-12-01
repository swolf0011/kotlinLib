package com.swolf.ly.kotlin.nycommonlib.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * 封装一个kotlin下的通用adapter
 */

class KRecyclerViewAdapters<T:KRecyclerViewAdapterEntity> : RecyclerView.Adapter<KRecyclerViewViewHolderHelper>() {

    //数据
    private var mDatalist: ArrayList<T>? = null
    //布局id
    private var mLayoutIds: IntArray?=null
    //绑定事件的lambda放发
    private var addBindView: ((itemView: View, itemData: T) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KRecyclerViewViewHolderHelper {
        val layout = mLayoutIds?.get(viewType)
        val view = LayoutInflater.from(parent.context).inflate(layout!!, parent, false)
        return KRecyclerViewViewHolderHelper(view)
    }
    override fun getItemViewType(position: Int): Int {
        return mDatalist?.get(position)?.typeIndex!!
    }

    override fun getItemCount(): Int {
        return mDatalist?.size ?: -1 //左侧为null时返回-1
    }

    override fun onBindViewHolder(p0: KRecyclerViewViewHolderHelper, p1: Int) {
        addBindView?.invoke(p0.itemView, mDatalist?.get(p1)!!)
    }

    /**
     * 建造者，用来完成adapter的数据组合
     */
    class Builder<B:KRecyclerViewAdapterEntity> {
        private var adapter: KRecyclerViewAdapters<B> = KRecyclerViewAdapters()
        /**
         * 设置数据
         */
        fun setData(lists: ArrayList<B>): Builder<B> {
            adapter.mDatalist = lists
            return this
        }
        /**
         * 设置布局id
         */
        fun setLayoutId(mLayoutIds: IntArray): Builder<B> {
            adapter.mLayoutIds = mLayoutIds
            return this
        }
        /**
         * 绑定View和数据
         */
        fun addBindView(itemBind: ((itemView: View, itemData: B) -> Unit)): Builder<B> {
            adapter.addBindView = itemBind
            return this
        }

        fun create(): KRecyclerViewAdapters<B> {
            return adapter
        }
    }

//    val  adapter = KotlinDataAdapter.Builder<DeviceModel>()
//        .setData(deviceList)
//        .setLayoutId(R.layout.item_device)
//        .addBindView { itemView, itemData ->
//            itemView.tv_device_name.text = if (itemData.platform.isEmpty()) "未命名" else
//        }
//        .create()

}
