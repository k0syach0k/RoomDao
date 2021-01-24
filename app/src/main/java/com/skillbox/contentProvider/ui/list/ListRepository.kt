package com.skillbox.contentProvider.ui.list

import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import com.skillbox.contentProvider.data.PartialContact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ListRepository(private val context: Context) {

    suspend fun getAllContact(): List<PartialContact> = withContext(Dispatchers.IO) {
        context.contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            null,
            null,
            null
        )?.use {
            getContactsFromCursor(it)
        }.orEmpty()
    }

    private fun getContactsFromCursor(cursor: Cursor): List<PartialContact> {
        if (cursor.moveToFirst().not()) return emptyList()

        val listContacts = mutableListOf<PartialContact>()

        val idIndex = cursor.getColumnIndex(ContactsContract.Contacts._ID)
        val idName = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)

        do {
            val idContact = cursor.getLong(idIndex)
            val nameContact = cursor.getString(idName).orEmpty()

            listContacts.add(
                PartialContact(
                    idContact,
                    nameContact
                )
            )
        } while (cursor.moveToNext())
        return listContacts
    }
}
