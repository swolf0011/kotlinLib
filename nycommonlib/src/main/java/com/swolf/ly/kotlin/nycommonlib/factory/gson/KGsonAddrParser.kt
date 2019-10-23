package com.swolf.ly.kotlin.nycommonlib.factory.gson

import com.google.gson.reflect.TypeToken
import com.swolf.ly.kotlin.nycommonlib.factory.gsona.KArea

object KGsonAddrParser {
    private var areaMap: Map<String, Map<String, Array<String>>>? = null
    private var areaList: List<KArea>? = null
    fun parserMapArea(areaJson: String): Map<String, Map<String, Array<String>>>? {
        if (areaMap == null) {
            areaMap = KGsonParser.jsonStr2Object(areaJson, object : TypeToken<Map<String, Map<String, Array<String>>>>() {

                })
        }
        return areaMap
    }

    fun parserListArea(areaJson: String): List<KArea>? {
        if (areaList == null) {
            areaList = KGsonParser.jsonStr2Object(areaJson, object : TypeToken<List<KArea>>() {

            })
        }
        return areaList
    }
}
