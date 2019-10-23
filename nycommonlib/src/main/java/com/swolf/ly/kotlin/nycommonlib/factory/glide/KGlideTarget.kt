package com.swolf.ly.kotlin.nycommonlib.factory.glide

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import java.io.File

object KGlideTarget {

    /**
     * 最简单加载网络图片的用法
     */
    fun load(context: Context, imgUrl: String, target: SimpleTarget<Bitmap>) {
        Glide.with(context).asBitmap().load(imgUrl).into(target)
    }

    /**
     * 资源文件中加载
     */
    fun load(context: Context, resourceId: Int, target: SimpleTarget<Bitmap>) {
        Glide.with(context).asBitmap().load(resourceId).into(target)
    }

    /**
     * 文件中加载
     */
    fun load(context: Context, file: File, target: SimpleTarget<Bitmap>) {
        Glide.with(context).asBitmap().load(file).into(target)
    }

    /**
     * URI中加载图片
     */
    fun load(context: Context, uri: Uri, target: SimpleTarget<Bitmap>) {
        Glide.with(context).asBitmap().load(uri).into(target)
    }

}
