package com.skillbox.contentProvider.ui.add

import android.content.ContentValues
import android.content.Context
import android.provider.ContactsContract
import com.skillbox.contentProvider.R
import com.skillbox.contentProvider.data.FullContact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.regex.Pattern

class AddRepository(private val context: Context) {
    private val phonePattern = Pattern.compile("^\\+?[0-9]{3}-?[0-9]{6,12}\$")

    suspend fun saveContact(contact: FullContact) = withContext(Dispatchers.IO){
        if (contact.firstName.isBlank()) throw Error(context.getString(R.string.incorrectName))
        if (contact.lastName.isBlank()) throw Error(context.getString(R.string.incorrectLastName))
        if (phonePattern.matcher(contact.phoneNumbers[0]).matches().not())
            throw Error(context.getString(R.string.incorrectPhoneNumber))

        val contactId = saveRawContact()
        if (contactId < 0) throw Error(context.getString(R.string.errorSaveContact))

        saveName(contactId, contact)

        saveContactData(
            contactId,
            contact.phoneNumbers[0],
            ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE,
            ContactsContract.CommonDataKinds.Phone.NUMBER
        )

        if (contact.email[0].isNotBlank())
            saveContactData(
                contactId,
                contact.email[0],
                ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE,
                ContactsContract.CommonDataKinds.Email.ADDRESS
            )
    }

    private fun saveRawContact(): Long {
        val uri = context.contentResolver.insert(ContactsContract.RawContacts.CONTENT_URI, ContentValues())
        return uri?.lastPathSegment?.toLongOrNull() ?: throw Error(context.getString(R.string.errorSaveContact))
    }

    private fun saveName(id: Long, contact: FullContact){
        val contentValues = ContentValues().apply {
            put(ContactsContract.Data.RAW_CONTACT_ID, id)
            put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
            put(ContactsContract.CommonDataKinds.StructuredName.PREFIX, contact.prefixName)
            put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, contact.firstName)
            put(ContactsContract.CommonDataKinds.StructuredName.MIDDLE_NAME, contact.middleName)
            put(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME, contact.lastName)
            put(ContactsContract.CommonDataKinds.StructuredName.SUFFIX, contact.suffixName)
        }
        context.contentResolver.insert(ContactsContract.Data.CONTENT_URI, contentValues)
    }

    private fun saveContactData(id: Long, data: String, uriType: String, uriData: String) {
        val contentValues = ContentValues().apply {
            put(ContactsContract.Data.RAW_CONTACT_ID, id)
            put(ContactsContract.Data.MIMETYPE, uriType)
            put(uriData, data)
        }
        context.contentResolver.insert(ContactsContract.Data.CONTENT_URI, contentValues)
    }
}
