package com.swolf.ly.kotlin.nycommonlib.factory.room

import android.content.Context


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

    fun queryAll(): List<Message> {
        var list: List<Message>? = dao.queryAll()
        if (list == null) {
            list = ArrayList()
        }
        return list
    }

    fun query(pageSize: Int, pageNum: Int): List<Message> {
        var list: List<Message>? = dao.query(pageSize, pageNum)
        if (list == null) {
            list = ArrayList()
        }
        return list
    }

    fun queryTop(): Message {
        return dao.queryTop()
    }
}
