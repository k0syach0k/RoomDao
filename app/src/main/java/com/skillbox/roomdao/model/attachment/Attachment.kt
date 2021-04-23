package com.skillbox.roomdao.model.attachment

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.skillbox.roomdao.model.email.Email
import com.skillbox.roomdao.model.email.EmailContract

@Entity(
    tableName = AttachmentContract.TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = Email::class,
            parentColumns = [EmailContract.Columns.ID],
            childColumns = [AttachmentContract.Columns.ID]
        )
    ]
)
data class Attachment(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = AttachmentContract.Columns.ID)
    val id: Int,
    @ColumnInfo(name = AttachmentContract.Columns.EMAIL_ID)
    val emailId: Int,
    @ColumnInfo(name = AttachmentContract.Columns.URL)
    val url: String
)
