package com.swolf.ly.kotlin.nycommonlib.factory.gsona
data class KArea(var code: Long, var name: String, var subs: List<KArea> = ArrayList<KArea>())