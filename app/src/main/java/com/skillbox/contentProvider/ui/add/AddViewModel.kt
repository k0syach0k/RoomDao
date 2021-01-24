package com.skillbox.contentProvider.ui.add

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.skillbox.contentProvider.R
import com.skillbox.contentProvider.data.FullContact
import com.skillbox.contentProvider.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class AddViewModel(application: Application) : AndroidViewModel(application) {
    private val context = application.applicationContext
    private val contactRepository = AddRepository(context)

    private val exceptionMutableLiveData = SingleLiveEvent<Throwable>()
    private val isLoadingLiveData = SingleLiveEvent<Boolean>()
    private val toastMutableLiveData = SingleLiveEvent<String>()
    private val contactSuccessfullyAddedMutableLiveData = SingleLiveEvent<Boolean>()

    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData

    val exceptionLiveData: LiveData<Throwable>
        get() = exceptionMutableLiveData

    val toastLiveData: LiveData<String>
        get() = toastMutableLiveData

    val contactSuccessfullyAdded: LiveData<Boolean>
        get() = contactSuccessfullyAddedMutableLiveData

    fun saveContact(contact: FullContact) {
        viewModelScope.launch {
            isLoadingLiveData.postValue(true)
            try {
                contactRepository.saveContact(contact)
                toastMutableLiveData.postValue(context.getString(R.string.contactSuccessfulAdded))
                contactSuccessfullyAddedMutableLiveData.postValue(true)
            } catch (t: Throwable) {
                exceptionMutableLiveData.postValue(t)
            }
            isLoadingLiveData.postValue(false)
        }
    }
}
