package com.swolf.ly.kotlin.nycommonlib.factory.glide

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import java.io.File

object KGlideDef {

    //    /**
    //     * 最简单加载网络图片的用法
    //     */
    //    public static void loadDemo(Context context, String imgUrl, ImageView imageView) {
    //        Glide.with(context).load(imgUrl)
    //                .thumbnail(0.1f)
    //                .placeholder(NYGlideBuilder.placeholderImgRes)//设置占位图
    //                .error(NYGlideBuilder.errorImgRes)//设置错误图片
    //                .fitCenter()//适配居中
    //                .dontAnimate()//直接显示图片而没有任何淡入淡出效果
    //                .dontTransform()//淡入淡出效果
    //                .into(imageView);
    //    }


    /**
     * 最简单加载网络图片的用法
     */
    fun load(context: Context, imgUrl: String, imageView: ImageView) {
        Glide.with(context).load(imgUrl)
            .thumbnail(0.1f)
            .apply(KGlideBuilder.getRequestOptions())
            .into(imageView)
    }

    /**
     * 资源文件中加载
     */
    fun load(context: Context, resourceId: Int, imageView: ImageView) {
        Glide.with(context).load(resourceId)
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
        Glide.with(context).load(file)
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
        Glide.with(context).load(uri)
            .thumbnail(0.1f)
            .placeholder(KGlideBuilder.placeholderImgRes)//设置占位图
            .error(KGlideBuilder.errorImgRes)//设置错误图片
            .fitCenter()//适配居中
            .dontTransform()//淡入淡出效果
            .into(imageView)
    }


    /**
     * 清除内存缓存:必须在UI线程中调用
     */
    fun clearMemory(context: Context) {
        Glide.get(context).clearMemory()
    }

    /**
     * 清除磁盘缓存:必须在后台线程中调用，建议同时clearMemory()
     */
    fun clearDiskCache(context: Context) {
        Glide.get(context).clearDiskCache()
    }


}
