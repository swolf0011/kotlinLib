package com.swolf.ly.kotlin.nycommonlib.factory.retrofit

import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.Retrofit
import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitManager private constructor() {
    companion object {
        private lateinit var mRetrofitManager: RetrofitManager
        private lateinit var mRetrofit: Retrofit
        private lateinit var mBaseUrl: String
        fun setInitBaseUrl(baseUrl: String){
            mBaseUrl = baseUrl
        }

        fun getInstance(): RetrofitManager {
            if(mRetrofitManager == null){
                mRetrofitManager = RetrofitManager()
                //初始化一个OkHttpClient
                val builder = OkHttpClient.Builder()
                    .connectTimeout(30000, TimeUnit.MILLISECONDS)
                    .readTimeout(30000, TimeUnit.MILLISECONDS)
                    .writeTimeout(30000, TimeUnit.MILLISECONDS)
//                  builder.addInterceptor(new LoggingInterceptor());
                val okHttpClient = builder.build()
                mRetrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .baseUrl(mBaseUrl)
                    .build()
            }
            return mRetrofitManager
        }
    }

    //返回一个泛型
    fun <T> getServer(server: Class<T>): T {
        return mRetrofit.create(server)
    }
}