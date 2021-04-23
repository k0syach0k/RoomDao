package com.skillbox.roomdao.ui.email_address.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skillbox.roomdao.model.email_address.EmailAddress
import com.skillbox.roomdao.model.user.User
import com.skillbox.roomdao.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class EmailAddressListViewModel() : ViewModel() {
    private val emailRepository = EmailAddressListRepository()

    private val emailMutableLiveData = MutableLiveData<List<EmailAddress>>()
    private val exceptionMutableLiveData = SingleLiveEvent<Throwable>()
    private val isLoadingLiveData = SingleLiveEvent<Boolean>()

    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData

    val emailLiveData: LiveData<List<EmailAddress>>
        get() = emailMutableLiveData

    val exceptionLiveData: LiveData<Throwable>
        get() = exceptionMutableLiveData

    fun getAllEmailAddressForUser(user: User){
        viewModelScope.launch {
            isLoadingLiveData.postValue(true)
            try {
                emailMutableLiveData.postValue(emailRepository.getAllEmailAddressForUser(user))
            } catch (t: Throwable) {
                exceptionMutableLiveData.postValue(t)
                emailMutableLiveData.postValue(emptyList())
            }
            isLoadingLiveData.postValue(false)
        }
    }
}