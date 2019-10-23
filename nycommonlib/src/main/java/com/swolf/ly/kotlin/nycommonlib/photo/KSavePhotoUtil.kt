package com.swolf.ly.kotlin.nycommonlib.photo

import android.app.Activity
import android.graphics.Bitmap
import android.os.Environment
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object KSavePhotoUtil {


    /**
     * 保存当前屏幕的截图,返回图片路径
     */
    fun saveCurrentWindowIsBitmap(activity: Activity): String {
        var path = ""
        val bitmap = KPhotoTransformationUtil.currentWindowToBitmap(activity)
        val cameraDir = File(Environment.getRootDirectory().toString() + "/cameraDir/")
        if (!cameraDir.exists()) {
            cameraDir.mkdirs()
        }
        val fileName = "IMG_" + System.currentTimeMillis() + ".jpg"
        val imageFile = File(cameraDir, fileName)

        var fos: FileOutputStream? = null
        //3.保存Bitmap
        try {
            fos = FileOutputStream(imageFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos)
            fos.flush()
            path = imageFile.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (null != fos) {
                try {
                    fos.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }
        bitmap.recycle()
        return path
    }

    /**
     * 保存图片2文件
     *
     * @param bitmap
     * @param CompressFormat
     * @param saveFile
     * @return
     */
    fun saveImage2File(bitmap: Bitmap, CompressFormat: Bitmap.CompressFormat, saveFile: File): Boolean {
        val baos = ByteArrayOutputStream()
        bitmap.compress(CompressFormat, 100, baos)
        val b = saveImage2File(baos.toByteArray(), saveFile)
        if (baos != null) {
            try {
                baos.flush()
                baos.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
        return b
    }

    /**
     * 保存图片2文件
     *
     * @param bs
     * @param saveFile
     * @return
     */
    fun saveImage2File(bs: ByteArray, saveFile: File?): Boolean {
        var fos: FileOutputStream? = null
        if (saveFile == null) {
            return false
        }
        if (!saveFile.parentFile!!.exists()) {
            saveFile.parentFile!!.mkdirs()
        }
        try {
            if (!saveFile.exists()) {
                saveFile.createNewFile()
            }
            fos = FileOutputStream(saveFile)
            fos.write(bs)
            fos.flush()
            return true
        } catch (e1: Exception) {
            e1.printStackTrace()
            return false
        } finally {
            if (fos != null) {
                try {
                    fos.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }
    }
}