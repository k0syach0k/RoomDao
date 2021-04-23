package com.skillbox.roomdao.model.email

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.skillbox.roomdao.model.email_address.EmailAddress
import com.skillbox.roomdao.model.email_address.EmailAddressContract
import com.skillbox.roomdao.model.folder.Folder

@Entity(
    tableName = EmailContract.TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = EmailAddress::class,
            parentColumns = [EmailAddressContract.Columns.ID],
            childColumns = [EmailContract.Columns.ADDRESS_FROM, EmailContract.Columns.ADDRESS_TO]
        ),
        ForeignKey(
            entity = Email::class,
            parentColumns = [EmailContract.Columns.ID],
            childColumns = [EmailContract.Columns.REPLY_TO]
        )
    ]
)
data class Email(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = EmailContract.Columns.ID)
    val id: Int,
    @ColumnInfo(name = EmailContract.Columns.ADDRESS_FROM)
    val addressFromId: Int,
    @ColumnInfo(name = EmailContract.Columns.ADDRESS_TO)
    val addressToId: Int,
    @ColumnInfo(name = EmailContract.Columns.FOLDER)
    val folder: Folder,
    @ColumnInfo(name = EmailContract.Columns.REPLY_TO)
    val replyToId: Int?,
    @ColumnInfo(name = EmailContract.Columns.SUBJECT)
    val subject: String,
    @ColumnInfo(name = EmailContract.Columns.MESSAGE)
    val message: String
)