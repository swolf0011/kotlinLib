package com.swolf.ly.kotlin.nycommonlib.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import butterknife.ButterKnife

abstract class KBaseFragment:Fragment(), KIBaseViewFun, KIView {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        var myView = inflater.inflate(setLayout(), container, false)
        ButterKnife.bind(myView)
        getIntentInData()
        initView()
        initValue()
        getDate()

        return myView
    }

}