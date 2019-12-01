package com.swolf.ly.kotlin.nycommonlib.factory.room

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.Room
import com.swolf.ly.kotlin.nycommonlib.factory.room.impl.IMessageDao
import com.swolf.ly.kotlin.nycommonlib.factory.room.impl.Message


@Database(entities = [Message::class], version = 1, exportSchema = false)
abstract class MyRoomDatabase: RoomDatabase() {
    companion object {
        private val DB_NAME = "AspectDatabase.db"
        @Volatile
        private var instance: MyRoomDatabase? = null

        @Synchronized
        fun getInstance(context: Context): MyRoomDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context,
                    MyRoomDatabase::class.java,
                    DB_NAME
                )
                    .allowMainThreadQueries()
                    .build()
            }
            return instance!!
        }
    }

    abstract fun getMessageDao(): IMessageDao

}
