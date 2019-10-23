package com.swolf.ly.kotlin.nycommonlib.factory.retrofit

import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.Retrofit
import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitManager private constructor() {
    companion object {
        class Builder {
            var mBaseUrl: String = ""
            fun setBaseUrl(baseUrl: String): Builder {
                mBaseUrl = baseUrl
                return this
            }
            fun build(): Retrofit {
                return RetrofitManager().initRetrofit(mBaseUrl)
            }
        }
    }
    fun initRetrofit(baseUrl: String): Retrofit {
        //初始化一个OkHttpClient
        val builder = OkHttpClient.Builder()
            .connectTimeout(30000, TimeUnit.MILLISECONDS)
            .readTimeout(30000, TimeUnit.MILLISECONDS)
            .writeTimeout(30000, TimeUnit.MILLISECONDS)
//            builder.addInterceptor(new LoggingInterceptor());
        val okHttpClient = builder.build()
        val mRetrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .build()
        return mRetrofit
    }




}