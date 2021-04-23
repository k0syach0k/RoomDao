package com.skillbox.roomdao.ui.email_address.add

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skillbox.roomdao.R
import com.skillbox.roomdao.model.email_address.EmailAddress
import com.skillbox.roomdao.model.user.User
import com.skillbox.roomdao.ui.user.add.UserAddRepository
import com.skillbox.roomdao.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class EmailAddressAddViewModel(application: Application) : AndroidViewModel(application) {
    private var context = application.applicationContext
    private val emailAddressAddRepository = EmailAddressAddRepository()

    private val toastMutableLiveData = SingleLiveEvent<String>()
    private val exceptionMutableLiveData = SingleLiveEvent<Throwable>()
    private val isLoadingLiveData = SingleLiveEvent<Boolean>()
    private val emailAddressSuccessfullyDeleted = SingleLiveEvent<Boolean>()

    val toastLiveData: LiveData<String>
        get() = toastMutableLiveData

    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData

    val exceptionLiveData: LiveData<Throwable>
        get() = exceptionMutableLiveData

    val emailAddressDeleted: LiveData<Boolean>
        get() = emailAddressSuccessfullyDeleted

    fun saveEmailAddress(emailAddress: EmailAddress){
        viewModelScope.launch {
            isLoadingLiveData.postValue(true)
            try {
                emailAddressAddRepository.saveEmailAddress(emailAddress)
                toastMutableLiveData.postValue(context.getString(R.string.toastEmailAddressSaved, emailAddress.emailAddress))
            } catch (t: Throwable) {
                exceptionMutableLiveData.postValue(t)
            }
            isLoadingLiveData.postValue(false)
        }
    }

    fun deleteEmailAddress(emailAddress: EmailAddress){
        viewModelScope.launch {
            isLoadingLiveData.postValue(true)
            try {
                emailAddressAddRepository.deleteEmailAddress(emailAddress)
                toastMutableLiveData.postValue(context.getString(R.string.toastEmailAddressDeleted, emailAddress.emailAddress))
                emailAddressSuccessfullyDeleted.postValue(true)
            } catch (t: Throwable) {
                exceptionMutableLiveData.postValue(t)
            }
            isLoadingLiveData.postValue(false)
        }
    }
}