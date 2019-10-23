package com.swolf.ly.kotlin.nycommonlib.photo

import android.content.Context
import android.graphics.Bitmap
import android.media.ExifInterface
import android.provider.MediaStore
import java.io.IOException
import java.util.ArrayList

object KPhotoUtil {


    /**
     * 获取SDCard所有图片
     */
    fun getSDCardPhotoList(context: Context): List<NYPhoto> {
        val photoList = ArrayList<NYPhoto>()
        val cr = context.contentResolver
        if (cr != null) {
            val projection = arrayOf(
                MediaStore.Images.Thumbnails._ID,
                MediaStore.Images.Thumbnails.DATA,
                MediaStore.Images.Thumbnails.HEIGHT,
                MediaStore.Images.Thumbnails.WIDTH
            )
            val cursor = cr.query(
                MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, projection, null, null,
                MediaStore.Images.Thumbnails.DEFAULT_SORT_ORDER
            )

            if (null != cursor && cursor.moveToFirst()) {
                do {
                    val p = NYPhoto()
                    p.id = cursor.getInt(0)
                    p.path = cursor.getString(1)
                    p.height = cursor.getInt(2)
                    p.width = cursor.getInt(3)
                    p.bitmap = MediaStore.Images.Thumbnails.getThumbnail(
                        cr,
                        p.id.toLong(),
                        MediaStore.Images.Thumbnails.MICRO_KIND,
                        null
                    )
                    photoList.add(p)
                } while (cursor.moveToNext())
            }
        }
        return photoList
    }

    /**
     * 读取照片exif信息中的旋转角度
     *
     * @param path 照片路径
     * @return角度
     */
    fun readPictureDegree(path: String): Int {
        var degree = 0
        try {
            val exifInterface = ExifInterface(path)
            val orientation =
                exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> degree = 90
                ExifInterface.ORIENTATION_ROTATE_180 -> degree = 180
                ExifInterface.ORIENTATION_ROTATE_270 -> degree = 270
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return degree
    }

    /**
     * 图片实体
     * Created by LiuYi-15973602714 on 2017-01-01
     */
    class NYPhoto {
        var id: Int = 0
        var url: String? = null
        var path: String? = null
        var height: Int = 0
        var width: Int = 0
        var bitmap: Bitmap? = null
    }
}
