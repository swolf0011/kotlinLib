package com.swolf.ly.kotlin.nycommonlib.factory.rxjava

import android.annotation.SuppressLint
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

class KRxJavaUtil<T> {

    @SuppressLint("CheckResult")
    fun hander(observableOnSubscribe: ObservableOnSubscribe<T>, onNext: Consumer<T>) {
        Observable.create(observableOnSubscribe)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            //指定上游发送事件线程
            .subscribeOn(Schedulers.computation())
            //指定下游接收事件线程
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(onNext)
    }

    @SuppressLint("CheckResult")
    fun hander(observableOnSubscribe: ObservableOnSubscribe<T>, onNext: Consumer<T>, onError: Consumer<in Throwable>) {
        Observable.create(observableOnSubscribe)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            //指定上游发送事件线程
            .subscribeOn(Schedulers.computation())
            //指定下游接收事件线程
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(onNext, onError)
    }

    fun hander(observableOnSubscribe: ObservableOnSubscribe<T>, observer: Observer<T>) {
        Observable.create(observableOnSubscribe)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            //指定上游发送事件线程
            .subscribeOn(Schedulers.computation())
            //指定下游接收事件线程
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }
}
