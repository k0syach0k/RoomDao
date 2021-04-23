package com.skillbox.roomdao.model.user

import androidx.room.*

@Dao
interface UserDao {

    @Query("SELECT * FROM ${UserContract.TABLE_NAME}")
    suspend fun getAllUsers(): List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUser(user: User)
    
    @Delete
    suspend fun deleteUser(user: User)
}