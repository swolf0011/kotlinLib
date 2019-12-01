package com.swolf.ly.kotlin.nycommonlib.factory.room.impl

import android.content.Context
import androidx.lifecycle.LiveData
import com.swolf.ly.kotlin.nycommonlib.factory.room.MyRoomDatabase


class MessegeDaoUtil(context: Context) {

    private val dao: IMessageDao

    init {
        var myRoomDatabase = MyRoomDatabase.getInstance(context)
        dao = myRoomDatabase.getMessageDao()
    }

    fun insert(m: Message) {
        dao.insert(m)
    }

    fun delete(m: Message) {
        dao.delete(m)
    }

    fun delete(ms: List<Message>) {
        dao.delete(ms)
    }

    fun update(m: Message) {
        dao.update(m)
    }

    fun queryAll(): LiveData<List<Message>> {
        var list: LiveData<List<Message>> = dao.queryAll()
        return list
    }

    fun query(pageSize: Int, pageNum: Int): LiveData<List<Message>>{
        var list: LiveData<List<Message>> = dao.query(pageSize, pageNum)
        return list
    }

    fun queryTop(): Message {
        return dao.queryTop()
    }
}
