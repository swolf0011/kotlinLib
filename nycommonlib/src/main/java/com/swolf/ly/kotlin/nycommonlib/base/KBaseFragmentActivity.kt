package com.swolf.ly.kotlin.nycommonlib.base

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import butterknife.ButterKnife

abstract class KBaseFragmentActivity : FragmentActivity(), KIBaseViewFun, KIView {
    var mActivity: KBaseFragmentActivity? = null
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