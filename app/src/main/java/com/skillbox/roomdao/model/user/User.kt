package com.skillbox.roomdao.model.user

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = UserContract.TABLE_NAME)
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = UserContract.Columns.ID)
    val id: Int,
    @ColumnInfo(name = UserContract.Columns.FIRST_NAME)
    val firstName: String,
    @ColumnInfo(name = UserContract.Columns.LAST_NAME)
    val lastName: String,
    @ColumnInfo(name = UserContract.Columns.PASSWORD_HASH)
    val passwordHash: String
): Parcelable