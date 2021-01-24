package com.skillbox.contentProvider.ui.detail

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract
import com.skillbox.contentProvider.data.FullContact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DetailRepository(private val context: Context) {

    suspend fun getContactData(id: Long): FullContact = withContext(Dispatchers.IO) {
        FullContact(
            id,
            getNameContact(id, ContactsContract.CommonDataKinds.StructuredName.PREFIX),
            getNameContact(id, ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME),
            getNameContact(id, ContactsContract.CommonDataKinds.StructuredName.MIDDLE_NAME),
            getNameContact(id, ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME),
            getNameContact(id, ContactsContract.CommonDataKinds.StructuredName.SUFFIX),
            getContactData(
                id,
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.NUMBER
            ),
            getContactData(
                id,
                ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                ContactsContract.CommonDataKinds.Email.CONTACT_ID,
                ContactsContract.CommonDataKinds.Email.ADDRESS
            )
        )
    }

    private fun getNameContact(id: Long, uri: String): String {
        return context.contentResolver.query(
            ContactsContract.Data.CONTENT_URI,
            null,
            "${ContactsContract.Data.MIMETYPE} = ? AND ${ContactsContract.Data.CONTACT_ID} = ?",
            arrayOf(
                ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE,
                id.toString()
            ),
            null
        )?.use {
            getNameFromCursor(it, uri)
        }.orEmpty()
    }

    private fun getNameFromCursor(cursor: Cursor, uri: String): String {
        if (cursor.moveToFirst().not()) return ""
        val nameIndex = cursor.getColumnIndex(uri)
        return cursor.getString(nameIndex).orEmpty()
    }

    private fun getContactData(
        id: Long,
        contentUri: Uri,
        selectString: String,
        dataString: String
    ): List<String> {
        return context.contentResolver.query(
            contentUri,
            null,
            "$selectString = ?",
            arrayOf(id.toString()),
            null
        )?.use {
            getDataFromCursor(it, dataString)
        }.orEmpty()
    }

    private fun getDataFromCursor(cursor: Cursor, dataString: String): List<String> {
        if (cursor.moveToFirst().not()) return emptyList()
        val list = mutableListOf<String>()
        val numberIndex = cursor.getColumnIndex(dataString)
        do {
            list.add(cursor.getString(numberIndex).orEmpty())
        } while (cursor.moveToNext())
        return list
    }

    fun deleteContactById(id: Long): Int {
        return context.contentResolver.delete(
            Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, id.toString()),
            null,
            null
        )
    }
}
