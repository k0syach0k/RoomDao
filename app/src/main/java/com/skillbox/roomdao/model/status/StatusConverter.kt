package com.skillbox.roomdao.model.status

import androidx.room.TypeConverter

class StatusConverter {

    @TypeConverter
    fun convertStatusToString(status: StatusEnum): String = status.name

    @TypeConverter
    fun convertStringToStatus(string: String): StatusEnum = StatusEnum.valueOf(string)
}