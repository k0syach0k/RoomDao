package com.skillbox.contentProvider.ui.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.skillbox.contentProvider.data.PartialContact
import com.skillbox.contentProvider.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class ListViewModel(application: Application) : AndroidViewModel(application) {
    private val contactRepository = ListRepository(application.applicationContext)

    private val contactsMutableLiveData = MutableLiveData<List<PartialContact>>()
    private val exceptionMutableLiveData = SingleLiveEvent<Throwable>()
    private val isLoadingLiveData = SingleLiveEvent<Boolean>()

    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData

    val contactsLiveData: LiveData<List<PartialContact>>
        get() = contactsMutableLiveData

    val exceptionLiveData: LiveData<Throwable>
        get() = exceptionMutableLiveData

    fun loadContactList() {
        viewModelScope.launch {
            isLoadingLiveData.postValue(true)
            try {
                contactsMutableLiveData.postValue(contactRepository.getAllContact())
            } catch (t: Throwable) {
                exceptionMutableLiveData.postValue(t)
                contactsMutableLiveData.postValue(emptyList())
            }
            isLoadingLiveData.postValue(false)
        }
    }
}
