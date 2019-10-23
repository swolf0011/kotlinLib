package com.swolf.ly.kotlin.nycommonlib.photo

import android.app.Activity
import android.graphics.*
import android.graphics.drawable.Drawable
import android.media.ThumbnailUtils
import android.view.View
import android.widget.ImageView
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.drawToBitmap
import com.swolf.ly.kotlin.nycommonlib.encryptDecrypt.KBase64Util
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.io.InputStream

object KPhotoTransformationUtil {
    /**
     * 创建缩略图 40X40
     */
    fun createThumbnail(bitmap: Bitmap): Bitmap {
        return ThumbnailUtils.extractThumbnail(bitmap, 40, 40)
    }

    /**
     * 创建缩略图
     */
    fun createThumbnail(bitmap: Bitmap, width: Int, height: Int): Bitmap {
        return ThumbnailUtils.extractThumbnail(bitmap, width, height)
    }

    /**
     * drawable转Bitmap
     */
    fun drawableToBitmap(drawable: Drawable): Bitmap {
        val bitmap = drawable.toBitmap()
        return bitmap
    }

    /**
     * 文件2Bitmap
     *
     * @param filePath
     * @return
     */
    fun fileToBitmap(filePath: String): Bitmap? {
        return if (File(filePath).exists()) {
            BitmapFactory.decodeFile(filePath)
        } else null
    }


    /**
     * bitmap转 byte[]
     */
    fun bitmapToBytes(bitmap: Bitmap): ByteArray {
        var bytes: ByteArray? = null
        var outStream: ByteArrayOutputStream? = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream)
        bytes = outStream!!.toByteArray()
        try {
            outStream.close()
            outStream = null
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return bytes
    }

    /**
     * Bitmap转base64,图片的base64编码是不包含图片头的，如（data:image/jpg;base64,）
     */
    fun bitmapToBase64(bitmap: Bitmap): String {
        val bs = bitmapToBytes(bitmap)
        return KBase64Util.encode(bs)
    }

    /**
     * byte[]转bitmap
     */
    fun bytesToBitmap(bs: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(bs, 0, bs.size)
    }

    /**
     * InputStream转bitmap
     */
    fun inputStreamToBitmap(`is`: InputStream): Bitmap {
        return BitmapFactory.decodeStream(`is`)
    }


    /**
     * 获取ImageView中的Bitmap
     */
    fun getBitmapByImageView(v: ImageView): Bitmap {
        val bitmap = Bitmap.createBitmap(v.drawToBitmap())
        return bitmap
    }

    /**
     * 将View转成Bitmap
     */
    fun viewToBitmap(v: View): Bitmap {
        var bitmap = Bitmap.createBitmap(v.width, v.height, Bitmap.Config.RGB_565)
        val canvas = Canvas(bitmap)
        v.draw(canvas)
        val bs = KPhotoCompressUtil.bitmapToBytes(bitmap, Bitmap.CompressFormat.WEBP, 200)
        if (bs != null && bs.size > 0) {
            bitmap = bytesToBitmap(bs)
        }
        return bitmap
    }

    fun captureScreen(activity: Activity): Bitmap {
        val decorView = activity.window.decorView
        //获取屏幕整张图片
        return decorView.drawToBitmap()
    }

    /**
     * 当前屏幕的截图
     */
    fun currentWindowToBitmap(activity: Activity): Bitmap {
        val path = ""
        //1.构建Bitmap
        val windowManager = activity.windowManager
        val display = windowManager.defaultDisplay
        val point = Point()
        display.getSize(point)
        val w = point.x
        val h = point.y
        var bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.RGB_565)
        //2.获取屏幕
        val decorview = activity.window.decorView
        bitmap = decorview.drawToBitmap()
        return bitmap
    }

}
