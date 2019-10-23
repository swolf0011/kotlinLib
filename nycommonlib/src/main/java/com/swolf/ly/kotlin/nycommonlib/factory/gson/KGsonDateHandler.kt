package com.swolf.ly.kotlin.nycommonlib.factory.gson

import com.google.gson.*
import java.lang.reflect.Type
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class KGsonDateHandler(dateFormatString: String) : JsonDeserializer<Date>, JsonSerializer<Date> {
    private var dateFormatString = "yyyy-MM-dd HH:mm:ss"

    init {
        this.dateFormatString = dateFormatString


    }

    /**
     * 时间反序列化
     */
    override fun deserialize(arg0: JsonElement, arg1: Type, arg2: JsonDeserializationContext): Date? {
        try {
            var str = arg0.toString()
            // 去除两边的引号
            str = str.substring(1, str.length - 1)
            return SimpleDateFormat(dateFormatString).parse(str)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return null
    }

    /**
     * 时间序列化
     */
    override fun serialize(arg0: Date, arg1: Type, arg2: JsonSerializationContext): JsonElement {
        return JsonPrimitive(SimpleDateFormat(dateFormatString).format(arg0))
    }
}
