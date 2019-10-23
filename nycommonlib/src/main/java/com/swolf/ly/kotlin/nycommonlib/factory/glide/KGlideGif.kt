package com.swolf.ly.kotlin.nycommonlib.factory.glide

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import java.io.File

object KGlideGif {


    /**
     * 最简单加载网络图片的用法
     */
    fun load(context: Context, imgUrl: String, imageView: ImageView) {
        Glide.with(context).asGif().load(imgUrl)
            .thumbnail(0.1f)
            .placeholder(KGlideBuilder.placeholderImgRes)//设置占位图
            .error(KGlideBuilder.errorImgRes)//设置错误图片
            .fitCenter()//适配居中
            .dontTransform()//淡入淡出效果
            .into(imageView)
    }

    /**
     * 资源文件中加载
     */
    fun load(context: Context, resourceId: Int, imageView: ImageView) {
        Glide.with(context).asGif().load(resourceId)
            .thumbnail(0.1f)
            .placeholder(KGlideBuilder.placeholderImgRes)//设置占位图
            .error(KGlideBuilder.errorImgRes)//设置错误图片
            .fitCenter()//适配居中
            .dontTransform()//淡入淡出效果
            .into(imageView)
    }

    /**
     * 文件中加载
     */
    fun load(context: Context, file: File, imageView: ImageView) {
        Glide.with(context).asGif().load(file)
            .thumbnail(0.1f)
            .placeholder(KGlideBuilder.placeholderImgRes)//设置占位图
            .error(KGlideBuilder.errorImgRes)//设置错误图片
            .fitCenter()//适配居中
            .dontTransform()//淡入淡出效果
            .into(imageView)
    }

    /**
     * URI中加载图片
     */
    fun load(context: Context, uri: Uri, imageView: ImageView) {
        Glide.with(context).asGif().load(uri)
            .thumbnail(0.1f)
            .placeholder(KGlideBuilder.placeholderImgRes)//设置占位图
            .error(KGlideBuilder.errorImgRes)//设置错误图片
            .fitCenter()//适配居中
            .dontTransform()//淡入淡出效果
            .into(imageView)
    }

}
