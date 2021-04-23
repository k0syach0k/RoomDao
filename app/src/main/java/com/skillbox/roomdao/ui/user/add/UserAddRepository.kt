package com.skillbox.roomdao.ui.user.add

import com.skillbox.roomdao.db.DataBase
import com.skillbox.roomdao.model.user.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserAddRepository() {

    suspend fun saveUser(user: User)= withContext(Dispatchers.IO){
        DataBase.instance.userDao().saveUser(user)
    }

    suspend fun deleteUser(user: User)= withContext(Dispatchers.IO){
        DataBase.instance.userDao().deleteUser(user)
    }
}