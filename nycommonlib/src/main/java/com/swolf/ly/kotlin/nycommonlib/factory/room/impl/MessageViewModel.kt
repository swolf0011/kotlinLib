package com.swolf.ly.kotlin.nycommonlib.factory.room.impl

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class MessageViewModel(app: Application) : AndroidViewModel(app) {
    lateinit var mMessageRepository: MessageRepository
    lateinit var mLiveData: LiveData<List<Message>>

    init {
        mMessageRepository = MessageRepository(app)
        mLiveData = mMessageRepository.mLiveData!!
    }
}