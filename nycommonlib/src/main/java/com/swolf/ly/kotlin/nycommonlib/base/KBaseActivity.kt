package com.swolf.ly.kotlin.nycommonlib.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import butterknife.ButterKnife

abstract class KBaseActivity : AppCompatActivity(), KIBaseViewFun, KIView {
    var mActivity: KBaseActivity? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(setLayout())
        mActivity = this
        ButterKnife.bind(this)
        getIntentInData()
        initView()
        initValue()
        getDate()
    }
}