package com.swolf.ly.kotlin.nycommonlib.util

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.ArrayList


/**
 * Android6.0及以上申请权限
 * Created by LiuYi
 */
object KPermissionUtil{
    //    public final int PERMISSION_requestCode = 1000;
    /**
     * 判断系统api版本大于等于23才走权限检查
     */
    val isMNC: Boolean
        get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M


    /**
     * 检测权限
     * @param activity
     * @param permissions
     * @return
     */
    fun checkSelfPermission(activity: Activity, permissions: Array<String>): Array<String?>? {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return null
        }
        val list = ArrayList<String>()
        for (str in permissions) {
            if (ContextCompat.checkSelfPermission(activity, str) != PackageManager.PERMISSION_GRANTED) {
                list.add(str)
            }
        }
        if (list.size > 0) {
            val strs = arrayOfNulls<String>(list.size)
            for (i in strs.indices) {
                strs[i] = list[i]
            }
            return strs
        }
        return null
    }

    /**
     * 检测权限并申请
     * @param activity
     * @param permissions
     * @return
     */
    fun checkSelfPermission2requestPermissions(activity: Activity, permissions: Array<String>, PERMISSION_requestCode: Int): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true
        }
        var bool = true
        val list = ArrayList<String>()
        for (str in permissions) {
            if (ContextCompat.checkSelfPermission(activity, str) != PackageManager.PERMISSION_GRANTED) {
                list.add(str)
            }
        }
        if (list.size > 0) {
            val strs = arrayOfNulls<String>(list.size)
            for (i in strs.indices) {
                strs[i] = list[i]
            }
            //没有权限，请求权限
            ActivityCompat.requestPermissions(activity, strs, PERMISSION_requestCode)
            bool = false
        }
        return bool
    }

}