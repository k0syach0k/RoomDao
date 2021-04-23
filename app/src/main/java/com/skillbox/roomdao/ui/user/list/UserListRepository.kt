package com.skillbox.roomdao.ui.user.list

import com.skillbox.roomdao.db.DataBase
import com.skillbox.roomdao.model.user.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserListRepository() {

    suspend fun getAllUsers(): List<User> = withContext(Dispatchers.IO){
        return@withContext DataBase.instance.userDao().getAllUsers()
    }

}