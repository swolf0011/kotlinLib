package com.swolf.ly.kotlin.nycommonlib.factory.room.impl

import android.app.Application
import androidx.lifecycle.LiveData
import com.swolf.ly.kotlin.nycommonlib.factory.room.MyRoomDatabase

class MessageRepository constructor(app: Application) {

    var mIMessageDao: IMessageDao? = null
    var mLiveData:LiveData<List<Message>>? = null
    init {
        mIMessageDao = MyRoomDatabase.getInstance(app).getMessageDao()
        mLiveData = mIMessageDao!!.queryAll()
    }

}