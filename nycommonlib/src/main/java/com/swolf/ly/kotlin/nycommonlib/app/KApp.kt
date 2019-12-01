package com.swolf.ly.kotlin.nycommonlib.app

import android.R
import android.app.Application
import androidx.multidex.MultiDex
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.squareup.leakcanary.LeakCanary

class KApp:Application() {

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        initLogger()
        initLeakCanary()
    }

    fun initLeakCanary(){
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
    }
    fun initLogger(){
        Logger.addLogAdapter(
            AndroidLogAdapter(
                PrettyFormatStrategy.newBuilder().
                    showThreadInfo(false).
                    methodCount(0).
                    tag("ly").
                    build()))
    }
}