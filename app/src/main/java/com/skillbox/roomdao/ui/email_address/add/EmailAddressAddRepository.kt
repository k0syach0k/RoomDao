package com.skillbox.roomdao.ui.email_address.add

import com.skillbox.roomdao.db.DataBase
import com.skillbox.roomdao.model.email_address.EmailAddress
import com.skillbox.roomdao.model.user.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EmailAddressAddRepository {

    suspend fun saveEmailAddress(emailAddress: EmailAddress) = withContext(Dispatchers.IO) {
        DataBase.instance.emailAddressDao().saveEmailAddress(emailAddress)
    }

    suspend fun deleteEmailAddress(emailAddress: EmailAddress) = withContext(Dispatchers.IO) {
        DataBase.instance.emailAddressDao().deleteEmailAddress(emailAddress)
    }
}