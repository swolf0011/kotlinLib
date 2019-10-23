package com.swolf.ly.kotlin.nycommonlib.base

interface KIBaseViewFun {
    /**
     * 设备布局
     */
    fun setLayout():Int
    /**
     * 初始化Intent数据
     */
    fun getIntentInData()
    /**
     * 初始化View
     */
    fun initView()
    /**
     * 初始化Value
     */
    fun initValue()
    /**
     * 获取数据
     */
    fun getDate()
}