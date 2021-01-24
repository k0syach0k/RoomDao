package com.skillbox.contentProvider.ui.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.skillbox.contentProvider.R
import com.skillbox.contentProvider.data.FullContact
import com.skillbox.contentProvider.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class DetailViewModel(application: Application) : AndroidViewModel(application) {
    private val context = application.applicationContext
    private val contactRepository = DetailRepository(context)

    private val contactsMutableLiveData = MutableLiveData<FullContact>()
    private val exceptionMutableLiveData = SingleLiveEvent<Throwable>()
    private val isLoadingLiveData = SingleLiveEvent<Boolean>()
    private val toastMutableLiveData = SingleLiveEvent<String>()
    private val contactSuccessfullyDeletedMutableLiveData = SingleLiveEvent<Boolean>()

    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData

    val contactsLiveData: LiveData<FullContact>
        get() = contactsMutableLiveData

    val exceptionLiveData: LiveData<Throwable>
        get() = exceptionMutableLiveData

    val toastLiveData: LiveData<String>
        get() = toastMutableLiveData

    val contactSuccessfullyDeleted: LiveData<Boolean>
        get() = contactSuccessfullyDeletedMutableLiveData

    fun loadContactData(id: Long) {
        viewModelScope.launch {
            isLoadingLiveData.postValue(true)
            try {
                contactsMutableLiveData.postValue(contactRepository.getContactData(id))
            } catch (t: Throwable) {
                exceptionMutableLiveData.postValue(t)
            }
            isLoadingLiveData.postValue(false)
        }
    }

    fun deleteContactById(id: Long) {
        viewModelScope.launch {
            isLoadingLiveData.postValue(true)
            try {
                contactRepository.deleteContactById(id)
                toastMutableLiveData.postValue(context.getString(R.string.contactSuccessfulDeleted))
                contactSuccessfullyDeletedMutableLiveData.postValue(true)
            } catch (t: Throwable) {
                exceptionMutableLiveData.postValue(t)
            }
            isLoadingLiveData.postValue(false)
        }
    }
}
