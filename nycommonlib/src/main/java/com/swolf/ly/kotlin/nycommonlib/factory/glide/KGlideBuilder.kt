package com.swolf.ly.kotlin.nycommonlib.factory.glide

import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.swolf.ly.kotlin.nycommonlib.R

object KGlideBuilder {
    var placeholderImgRes = R.mipmap.ic_launcher
    var errorImgRes = R.mipmap.ic_launcher
    var fallbackImgRes = R.mipmap.ic_launcher
    private var requestOptions: RequestOptions? = null

    fun init2App(placeholderImgResDrawable: Int, errorImgResDrawable: Int, fallbackImgResDrawable: Int) {
        placeholderImgRes = placeholderImgResDrawable
        errorImgRes = errorImgResDrawable
        fallbackImgRes = fallbackImgResDrawable
    }


    fun getRequestOptions(): RequestOptions{
        if (requestOptions == null) {
            requestOptions = RequestOptions()
                .placeholder(KGlideBuilder.placeholderImgRes)//设置占位图
                .error(KGlideBuilder.errorImgRes)
                .override(400, 400)//指定图片的缩放类型为fitCenter （等比例缩放图片，宽或者是高等于ImageView的宽或者是高。）
                .fitCenter()//适配居中
                .centerCrop()//指定图片的缩放类型为centerCrop （等比例缩放图片，直到图片的狂高都大于等于ImageView的宽度，然后截取中间的显示。）
                .circleCrop()//指定图片的缩放类型为centerCrop （圆形）
                .skipMemoryCache(true)//跳过内存缓存
                .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存所有版本的图像
                .diskCacheStrategy(DiskCacheStrategy.NONE)//跳过磁盘缓存
                .diskCacheStrategy(DiskCacheStrategy.DATA)//只缓存原来分辨率的图片
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)//只缓存最终的图片
                .format(DecodeFormat.PREFER_RGB_565)
                .dontTransform()//淡入淡出效果
        }
        return requestOptions!!
    }

    fun getRequestOptions(rp: RequestOptions) {
        requestOptions = rp
    }
}
