package com.swolf.ly.kotlin.nycommonlib.factory.retrofit

import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST



interface IRequest {

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("android")
    fun onSend(@Header("appkey") appkey: String, @Body msgs: String): Observable<String>
}