package com.swolf.ly.kotlin.nycommonlib.photo.popupWindow

import android.Manifest
import android.app.Activity
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.DocumentsContract
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File
import java.util.ArrayList

object KPickPhotoUtil {

    /**
     * 权限标示
     */
    private val PERMISSION_WRITE_EXTERNAL_STORAGE = 1000

    internal fun uriToPath6(activity: Activity, uri: Uri): String? {
        var path: String? = null
        if (DocumentsContract.isDocumentUri(activity, uri)) {
            // 如果是document类型的Uri，则通过document id处理
            val docId = DocumentsContract.getDocumentId(uri)
            if ("com.android.providers.media.documents" == uri.authority) {
                val id = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1] // 解析出数字格式的id
                val selection = MediaStore.Images.Media._ID + "=" + id
                path = getImagePath6(activity, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection)
            } else if ("com.android.providers.downloads.documents" == uri.authority) {
                val contentUri = ContentUris.withAppendedId(
                    Uri.parse("content://downloads/public_downloads"),
                    java.lang.Long.valueOf(docId)
                )
                path = getImagePath6(activity, contentUri, null)
            }
        } else if ("content".equals(uri.scheme!!, ignoreCase = true)) {
            // 如果是content类型的Uri，则使用普通方式处理
            path = getImagePath6(activity, uri, null)
        } else if ("file".equals(uri.scheme!!, ignoreCase = true)) {
            // 如果是file类型的Uri，直接获取图片路径即可
            path = uri.path
        }
        return path
    }


    //获取照片路径
    internal fun getImagePath6(activity: Activity, uri: Uri, selection: String?): String? {
        var path: String? = null
        //通过Uri和 selection获取真实图片路径
        val cursor = activity.contentResolver.query(uri, null, selection, null, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
            }
            cursor.close()
        }
        return path
    }

    /**
     * 通过URI，获取文件路径
     */
    internal fun getFilePathFromUri(context: Context, uri: Uri): String {
        var path = ""
        var cursor = context.contentResolver.query(uri, null, null, null, null)
        val index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        if (cursor != null) {
            cursor.moveToFirst()
            path = cursor.getString(index)
            cursor.close()
            cursor = null
        }
        return path
    }


    //////////////
    internal fun checkSelfPermissionGallery6(activity: Activity): Boolean {
        var bool = true
        //权限检查
        if (ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            //没有权限，请求权限
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), PERMISSION_WRITE_EXTERNAL_STORAGE
            )
            bool = false
        }
        return bool
    }


    internal fun checkSelfPermissionTakePhoto6(activity: Activity): Boolean {
        var bool = true
        val list = ArrayList<String>()
        if (ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            list.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            list.add(Manifest.permission.CAMERA)
        }
        if (list.size > 0) {
            val strs = arrayOfNulls<String>(list.size)
            for (i in strs.indices) {
                strs[i] = list[i]
            }
            //没有权限，请求权限
            ActivityCompat.requestPermissions(activity, strs, PERMISSION_WRITE_EXTERNAL_STORAGE)
            bool = false
        }
        return bool
    }

    internal fun getImageContentUri(context: Context, imageFile: File): Uri? {
        val filePath = imageFile.absolutePath
        val cursor = context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            arrayOf(MediaStore.Images.Media._ID),
            MediaStore.Images.Media.DATA + "=? ",
            arrayOf(filePath), null
        )
        if (cursor != null && cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID))
            val baseUri = Uri.parse("content://media/external/images/media")
            return Uri.withAppendedPath(baseUri, "" + id)
        } else {
            if (imageFile.exists()) {
                val values = ContentValues()
                values.put(MediaStore.Images.Media.DATA, filePath)
                return context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            } else {
                return null
            }
        }
    }
}
