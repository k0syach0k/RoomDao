package com.skillbox.roomdao.ui.email_address.list

import com.skillbox.roomdao.db.DataBase
import com.skillbox.roomdao.model.email_address.EmailAddress
import com.skillbox.roomdao.model.user.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EmailAddressListRepository {

    suspend fun getAllEmailAddressForUser(user: User): List<EmailAddress> = withContext(Dispatchers.IO){
        return@withContext DataBase.instance.emailAddressDao().getAllEmailAddressByUserId(user.id)
    }
}