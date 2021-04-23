package com.skillbox.roomdao.model

import androidx.room.Database
import androidx.room.RoomDatabase
import com.skillbox.roomdao.model.email_address.EmailAddress
import com.skillbox.roomdao.model.email_address.EmailAddressDao
import com.skillbox.roomdao.model.user.User
import com.skillbox.roomdao.model.user.UserDao

@Database(entities = [User::class, EmailAddress::class], version = EmailDataBase.DB_VERSION)
abstract class EmailDataBase: RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun emailAddressDao(): EmailAddressDao

    companion object{
        const val DB_VERSION = 1
        const val DB_NAME = "email_database"
    }
}