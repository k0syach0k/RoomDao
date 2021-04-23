package com.skillbox.roomdao.db

import android.content.Context
import androidx.room.Room
import com.skillbox.roomdao.model.EmailDataBase

object DataBase {
    lateinit var instance: EmailDataBase
        private set

    fun init(context: Context){
        instance = Room
            .databaseBuilder(context, EmailDataBase::class.java, EmailDataBase.DB_NAME)
            .build()
    }
}