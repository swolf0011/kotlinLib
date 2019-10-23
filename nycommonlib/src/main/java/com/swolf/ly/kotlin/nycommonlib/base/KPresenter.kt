package com.swolf.ly.kotlin.nycommonlib.base

abstract class KPresenter {
    var mView:KIView?=null
    constructor(view:KIView){
        this.mView = view
    }
    abstract fun presenter(command :Int,map:Map<String, Object>)
}