package com.skillbox.roomdao.ui.user.add

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.skillbox.roomdao.R
import com.skillbox.roomdao.model.user.User
import com.skillbox.roomdao.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class UserAddViewModel(application: Application) : AndroidViewModel(application) {
    private var context = application.applicationContext
    private val userRepository = UserAddRepository()

    private val toastMutableLiveData = SingleLiveEvent<String>()
    private val exceptionMutableLiveData = SingleLiveEvent<Throwable>()
    private val isLoadingLiveData = SingleLiveEvent<Boolean>()
    private val userSuccessfullyDeleted = SingleLiveEvent<Boolean>()

    val toastLiveData: LiveData<String>
        get() = toastMutableLiveData

    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData

    val exceptionLiveData: LiveData<Throwable>
        get() = exceptionMutableLiveData

    val userDeleted: LiveData<Boolean>
        get() = userSuccessfullyDeleted

    fun saveUser(user: User){
        viewModelScope.launch {
            isLoadingLiveData.postValue(true)
            try {
                userRepository.saveUser(user)
                toastMutableLiveData.postValue(context.getString(R.string.toastUserSaved, user.lastName, user.firstName))
            } catch (t: Throwable) {
                exceptionMutableLiveData.postValue(t)
            }
            isLoadingLiveData.postValue(false)
        }
    }

    fun deleteUser(user: User){
        viewModelScope.launch {
            isLoadingLiveData.postValue(true)
            try {
                userRepository.deleteUser(user)
                toastMutableLiveData.postValue(context.getString(R.string.toastUserDeleted, user.lastName, user.firstName))
                userSuccessfullyDeleted.postValue(true)
            } catch (t: Throwable) {
                exceptionMutableLiveData.postValue(t)
            }
            isLoadingLiveData.postValue(false)
        }
    }
}