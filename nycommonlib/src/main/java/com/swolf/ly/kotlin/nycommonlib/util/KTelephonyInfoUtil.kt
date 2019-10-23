package com.swolf.ly.kotlin.nycommonlib.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Environment
import android.provider.Settings
import android.telephony.TelephonyManager
import android.text.TextUtils
import android.view.inputmethod.InputMethodManager
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader


/**
 * 手机信息工具
 *  <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
 *  <uses-permission android:name="android.permission.READ_PHONE_STATE" />
 */
object KTelephonyInfoUtil {
    /**
     * 获取手机号码
     */
    @SuppressLint("MissingPermission")
    fun getPhoneNumber(cxt: Context): String {
        var tm = cxt.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val number = tm.line1Number
        return number
    }

    /**
     * 手机型号
     */
    fun phoneModel(): String {
        return android.os.Build.MODEL
    }

    /**
     * 手机制造商
     */
    fun phoneManufacturer(): String {
        return android.os.Build.MANUFACTURER
    }

    /**
     * SDK版本号
     */
    fun phoneVersionSDK(): Int {
        return android.os.Build.VERSION.SDK_INT
    }

    /**
     * 系统版本号
     */
    fun phoneVersionRelease(): String {
        return android.os.Build.VERSION.RELEASE
    }

    /**
     * 获取设备的MIEI
     */
    fun getIMEI(cxt: Context): String {
        var tm = cxt.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        @SuppressLint("MissingPermission") var imei = tm.deviceId
        if (TextUtils.isEmpty(imei)) {
            //android.provider.Settings;
            imei = Settings.Secure.getString(cxt.contentResolver, Settings.Secure.ANDROID_ID)
        }
        return imei
    }

    /**
     * 获取设备的android_id
     */
    fun android_id(context: Context): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }

    /**
     * 获取手机卡IMSI
     */
    @SuppressLint("MissingPermission")
    fun getIMSI(cxt: Context): String {
        var tm = cxt.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val imsi = tm.subscriberId
        return imsi
    }

    /**
     * 获取IMSI的中国提供商名
     */
    fun getProvidersName(IMSI: String): String? {
        var ProvidersName: String? = null
        // IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。
        if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
            ProvidersName = "中国移动"
        } else if (IMSI.startsWith("46001")) {
            ProvidersName = "中国联通"
        } else if (IMSI.startsWith("46003")) {
            ProvidersName = "中国电信"
        }
        return ProvidersName
    }

    /**
     * 判断有无seen字段，有的话是android系统，无的话是ophone
     */
    fun getTelephonySystem(context: Context): String {
        var systemName = ""
        var c = context.contentResolver.query(Uri.parse("content://sms/"), null, null, null, null)
        if (c != null && c.moveToFirst()) {
            if (c.getColumnIndex("seen") < 0) {
                systemName = "android"
            } else {
                systemName = "ophone"
            }
        }
        if (c != null) {
            c.close()
            c = null
        }
        return systemName
    }

    /**
     * 进入系统网络设置界面
     */
    fun goToNetSetting(context: Context) {
        val sdkVersion = android.os.Build.VERSION.SDK_INT
        var intent: Intent? = null
        if (sdkVersion > 10) {
            intent = Intent(Settings.ACTION_SETTINGS)
        } else {
            intent = Intent()
            val comp = ComponentName("com.android.settings", "com.android.settings.Settings")
            intent.component = comp
            intent.action = "android.intent.action.VIEW"
        }
        context.startActivity(intent)
    }

    /**
     * 判断是否有网络连接
     *
     * @param context
     * @return
     */
    fun isNetworkConnected(context: Context?): Boolean {
        if (context != null) {
            val mConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val mNetworkInfo = mConnectivityManager.activeNetworkInfo
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable
            }
        }
        return false
    }

    /**
     * 判断WIFI网络是否可用
     *
     * @param context
     * @return
     */
    fun isWifiConnected(context: Context?): Boolean {
        if (context != null) {
            val mConnectivityManager = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val mWiFiNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
            if (mWiFiNetworkInfo != null) {
                return mWiFiNetworkInfo.isAvailable
            }
        }
        return false
    }

    /**
     * 判断MOBILE网络是否可用
     *
     * @param context
     * @return
     */
    fun isMobileConnected(context: Context?): Boolean {
        if (context != null) {
            val mConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val mMobileNetworkInfo = mConnectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isAvailable
            }
        }
        return false
    }

    /**
     * 连接上一个没有外网连接的WiFi
     * 或者有线就会出现极端的情况
     *
     * @return
     */
    fun ping(urlStr: String): Boolean {
        var result: Boolean = false
        var reader: BufferedReader? = null
        var input: InputStream? = null
        try {
            val ip = urlStr// ping 的地址，可以换成任何一种可靠的外网
            val p = Runtime.getRuntime().exec("ping -c 3 -w 100 $ip")// ping网址3次
            // 读取ping的内容，可以不加
            input = p.inputStream
            reader = BufferedReader(InputStreamReader(input))
            val stringBuffer = StringBuffer()
            var content = ""
            do{
                content = reader.readLine()
                stringBuffer.append(content)
            }while(content!=null)
            // ping的状态
            val status = p.waitFor()
            if (status == 0) {
                result = true
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } finally {
            if(reader!=null){
                reader.close()
                reader = null
            }
            if(input!=null){
                input.close()
                input = null
            }
        }
        return result
    }

    /**
     * 判断GPS定位服务是否开启
     */
    fun hasLocationGPS(context: Context): Boolean {
        val manager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    /**
     * 判断基站定位是否开启
     */
    fun hasLocationNetWork(context: Context): Boolean {
        val manager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    /**
     * 检查内存卡可读
     */
    fun checkMemoryCard(context: Context) {
        if (Environment.MEDIA_MOUNTED != Environment.getExternalStorageState()) {
            val intent = Intent(Settings.ACTION_SETTINGS)
            context.startActivity(intent)
        }
    }

    /**
     * 打开网络设置对话框
     */
    fun openWirelessSet(context: Context) {
        val intent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
        context.startActivity(intent)
    }

    /**
     * 关闭键盘
     */
    fun closeInput(activity: Activity) {
        val inputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (inputMethodManager != null && activity.currentFocus != null) {
            inputMethodManager.hideSoftInputFromWindow(
                activity.currentFocus!!.windowToken, InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }

}


