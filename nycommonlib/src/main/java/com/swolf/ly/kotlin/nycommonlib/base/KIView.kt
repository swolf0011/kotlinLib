package com.swolf.ly.kotlin.nycommonlib.base

interface KIView {

    /**
     * 每个使用者定义command
     * @param command
     * @param map
     */
    fun viewHandler(command: Int, map: Map<String, Any>)
}