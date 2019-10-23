package com.swolf.ly.kotlin.nycommonlib.factory.glide

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator
import com.bumptech.glide.module.AppGlideModule

@GlideModule
class KAppGlideModule : AppGlideModule() {
    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {}


    override fun applyOptions(context: Context, builder: GlideBuilder) {
        //设置memory和Bitmap池的大小
        val calculator = MemorySizeCalculator.Builder(context).build()
        val defaultMemoryCacheSize = calculator.memoryCacheSize
        val defaultBitmapPoolSize = calculator.bitmapPoolSize
        val customBitmapPoolSize = (1.2 * defaultBitmapPoolSize).toInt()
        val customMemoryCacheSize = (1.2 * defaultMemoryCacheSize).toInt()
        val cacheSize100MegaBytes = 1024 * 1024 * 100 // 100 MB

        builder.setDefaultRequestOptions(KGlideBuilder.getRequestOptions())
        builder.setBitmapPool(LruBitmapPool(customBitmapPoolSize.toLong()))
        builder.setMemoryCache(LruResourceCache(customMemoryCacheSize.toLong()))
        builder.setDiskCache(InternalCacheDiskCacheFactory(context, cacheSize100MegaBytes.toLong()))
    }

}