package com.skillbox.roomdao.model.email

object EmailContract {
    const val TABLE_NAME = "email"

    object Columns {
        const val ID = "id"
        const val ADDRESS_FROM = "email_from_id"
        const val ADDRESS_TO = "email_to_id"
        const val FOLDER = "folder"
        const val REPLY_TO = "reply_to"
        const val SUBJECT = "subject"
        const val MESSAGE = "message"
    }
}