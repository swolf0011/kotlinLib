package com.swolf.ly.kotlin.nycommonlib.photo.popupWindow

import android.Manifest
import android.app.Activity
import com.swolf.ly.kotlin.nycommonlib.util.KPermissionUtil

object KPickPhotoPermissionUtil {

    private val my_permissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.READ_CONTACTS
    )

    /**
     * 检测权限并申请
     */
    fun checkSelfPermission2requestPermissions(activity: Activity, PERMISSION_requestCode: Int): Boolean {
        return KPermissionUtil.checkSelfPermission2requestPermissions(activity, my_permissions, PERMISSION_requestCode)
    }


}