package com.skillbox.roomdao.model.email_address

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.skillbox.roomdao.model.user.User
import com.skillbox.roomdao.model.user.UserContract
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(
    tableName = EmailAddressContract.TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = [UserContract.Columns.ID],
            childColumns = [EmailAddressContract.Columns.USER_ID]
        )
    ]
)
data class EmailAddress(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = EmailAddressContract.Columns.ID)
    val id: Int,
    @ColumnInfo(name = EmailAddressContract.Columns.USER_ID)
    val userId: Int,
    @ColumnInfo(name = EmailAddressContract.Columns.EMAIL_ADDRESS)
    val emailAddress: String
): Parcelable
