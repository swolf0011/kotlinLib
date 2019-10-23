package com.swolf.ly.kotlin.nycommonlib.factory.room

import androidx.room.*


@Dao
interface IMessageDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg entities: Message)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entities: List<Message>)

    @Delete
    fun delete(vararg entities: Message)

    @Delete
    fun delete(entities: List<Message>)

    @Update
    fun update(vararg entitys: Message)

    @Update
    fun update(entity: List<Message>)


    @Query("SELECT * FROM Message")
    fun queryAll(): List<Message>

    @Query("SELECT * FROM Message WHERE id IN (:ids)")
    fun queryByIds(ids: LongArray): List<Message>

    @Query("SELECT * FROM Message WHERE id = (:id) LIMIT 1")
    fun queryById(id: Long): Message

    @Query("SELECT * FROM Message LIMIT :pageSize OFFSET (:pageNum-1)*:pageSize")
    fun query(pageSize: Int, pageNum: Int): List<Message>

    @Query("SELECT * FROM Message LIMIT 1")
    fun queryTop(): Message
}
