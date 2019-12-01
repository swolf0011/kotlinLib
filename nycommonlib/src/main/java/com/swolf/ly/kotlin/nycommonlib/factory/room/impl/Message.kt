package com.swolf.ly.kotlin.nycommonlib.factory.room.impl

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Message")
data class Message(
    @PrimaryKey(autoGenerate = true)
    var id: Int?,
    @ColumnInfo(name = "schemeKey")
    var schemeKey: String,
    @ColumnInfo(name = "actorAgentOpenid")
    var actorAgentOpenid: String,
    @ColumnInfo(name = "actorAgentAccountName")
    var actorAgentAccountName: String,
    @ColumnInfo(name = "actorAgentAccountHomePage")
    var actorAgentAccountHomePage: String,
    @ColumnInfo(name = "actorAgentName")
    var actorAgentName: String

)



