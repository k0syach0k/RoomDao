package com.skillbox.roomdao.model.email_address

import androidx.room.*

@Dao
interface EmailAddressDao {

    @Query("SELECT * FROM ${EmailAddressContract.TABLE_NAME} WHERE ${EmailAddressContract.Columns.USER_ID} = :userId")
    suspend fun getAllEmailAddressByUserId(userId: Int): List<EmailAddress>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveEmailAddress(emailAddress: EmailAddress)

    @Delete
    suspend fun deleteEmailAddress(emailAddress: EmailAddress)
}