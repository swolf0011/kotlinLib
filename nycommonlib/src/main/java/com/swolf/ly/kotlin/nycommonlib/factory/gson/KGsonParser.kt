package com.swolf.ly.kotlin.nycommonlib.factory.gson

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.text.DateFormat
import java.util.*

/**
NYParent p0 = KGsonParser.jsonStr2Object(str, new TypeToken<NYParent>(){});
NYSub<NYParent> p1 = KGsonParser.jsonStr2Object(str, new TypeToken<NYSub<NYParent>>(){});
String pStr0 = KGsonParser.object2JsonStr(p0);
 */
object KGsonParser {
    /**
     *
     * @param json              String时间格式默认为:yyyy-MM-dd HH:mm:ss
     * @param typeToken              new TypeToken<VersionResponse>()
     * @param dateFormatString  时间格式
     * @return
    </VersionResponse> */
    fun <T> jsonStr2Object(json: String, typeToken: TypeToken<T>, dateFormatString: String?="yyyy-MM-dd HH:mm:ss"): T? {
        var dateFormatString = dateFormatString
        try {
            if (dateFormatString == null || dateFormatString.length == 0) {
                dateFormatString = "yyyy-MM-dd HH:mm:ss"
            }
            val gsonBuilder = GsonBuilder()
            val gson =
                gsonBuilder.registerTypeAdapter(Date::class.java, KGsonDateHandler(dateFormatString)).setDateFormat(
                    DateFormat.LONG
                ).create()
            return gson.fromJson(json, typeToken.getType())
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

    /**
     *
     * @param obj obj实体的字段最好使用包装类型，这样如果字段为空就不会转换出来
     * @param dateFormatString 时间格式
     * @return
     */
    @JvmOverloads
    fun object2JsonStr(obj: Any, dateFormatString: String? = "yyyy-MM-dd HH:mm:ss"): String? {
        var dateFormatString = dateFormatString
        try {
            if (dateFormatString == null || dateFormatString.length == 0) {
                dateFormatString = "yyyy-MM-dd HH:mm:ss"
            }
            val gson =
                GsonBuilder().registerTypeAdapter(Date::class.java, KGsonDateHandler(dateFormatString)).setDateFormat(
                    DateFormat.LONG
                ).create()
            return gson.toJson(obj)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }
}

