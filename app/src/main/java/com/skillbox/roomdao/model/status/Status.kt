package com.skillbox.roomdao.model.status

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = StatusContract.TABLE_NAME)
@TypeConverters(StatusConverter::class)
data class Status(
    @PrimaryKey
    @ColumnInfo(name = StatusContract.Columns.STATUS)
    val status: StatusEnum
)
