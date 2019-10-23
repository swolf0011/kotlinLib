package com.swolf.ly.kotlin.nycommonlib.photo

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import java.io.ByteArrayOutputStream
import java.io.IOException

object KPhotoCompressUtil {

    /**
     * 调整大小图片，放大或缩小图片
     */
    fun resizeImage(bitmap: Bitmap, newWidth: Int, newHeight: Int): Bitmap {
        val width = bitmap.width
        val height = bitmap.height
        val scaleWidth = newWidth.toFloat() / width
        val scaleHeight = newHeight.toFloat() / height
        val matrix = Matrix()
        matrix.postScale(scaleWidth, scaleHeight)
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true)
    }



    /**
     * 压缩图片2文件
     *
     * @param bitmap
     * @param cf       枚举：
     * @param new_size 新大小，单位是KB。不能<=0
     * @return
     */
    fun bitmapToBytes(bitmap: Bitmap?, cf: Bitmap.CompressFormat, new_size: Int): ByteArray? {
        var new_size = new_size
        if (bitmap == null) {
            return null
        }
        if (new_size <= 0) {
            new_size = 100
        }
        var quality = 80 // 100:not compress
        val baos = ByteArrayOutputStream()
        bitmap.compress(cf, quality, baos)
        while (baos.toByteArray().size / 1024 > new_size && quality > 10) { // >100KB
            baos.reset()
            quality -= 10
            if (quality > 0) {
                bitmap.compress(cf, quality, baos)
            }
        }
        val bs = baos.toByteArray()
        if (baos != null) {
            try {
                baos.flush()
                baos.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
        return bs
    }

    fun bitmapToBitmap(bitmap: Bitmap, cf: Bitmap.CompressFormat, new_size: Int): Bitmap {
        val bs = bitmapToBytes(bitmap, cf, new_size)
        return BitmapFactory.decodeByteArray(bs, 0, bs?.size?:-1)
    }

    /**
     * 压缩指定宽高图片到指定大小到文件
     *
     * @param filePath
     * @param cf
     * @param n_w
     * @param n_h
     * @return
     */
    fun imageFilePathToBytes(filePath: String, cf: Bitmap.CompressFormat, n_w: Int, n_h: Int): ByteArray {
        var bs: ByteArray? = null
        val bitmap = imageFilePathToBitmap(filePath, cf, n_w, n_h)
        val quality = 100 // 100:not compress
        val baos = ByteArrayOutputStream()
        bitmap.compress(cf, quality, baos)
        bs = baos.toByteArray()
        if (baos != null) {
            try {
                baos.flush()
                baos.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
        bitmap.recycle()
        return bs

    }

    fun imageFilePathToBitmap(filePath: String, cf: Bitmap.CompressFormat, n_w: Int, n_h: Int): Bitmap {
        val newOpts = BitmapFactory.Options()
        newOpts.inJustDecodeBounds = true// begin set is true;
        var bitmap = BitmapFactory.decodeFile(filePath, newOpts)
        newOpts.inJustDecodeBounds = false// set is true;

        val w = newOpts.outWidth
        val h = newOpts.outHeight

        var inSampleSize = 1// 1:not compress
        if (w > h && w > n_w) {
            inSampleSize = newOpts.outWidth / n_w
        } else if (w < h && h > n_h) {
            inSampleSize = newOpts.outHeight / n_h
        }
        if (inSampleSize <= 0) {
            inSampleSize = 1
        }
        newOpts.inSampleSize = inSampleSize
        newOpts.inPreferredConfig = Bitmap.Config.ARGB_8888// 该模式是默认的,可不设
        // newOpts.inPurgeable = true;// 同时设置才会有效
        // newOpts.inInputShareable = true;// 。当系统内存不够时候图片自动被回收
        bitmap = BitmapFactory.decodeFile(filePath, newOpts)
        return bitmap
    }


}
