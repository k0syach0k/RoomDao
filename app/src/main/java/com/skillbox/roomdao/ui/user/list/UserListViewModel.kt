package com.skillbox.roomdao.ui.user.list

import androidx.lifecycle.*
import com.skillbox.roomdao.model.user.User
import com.skillbox.roomdao.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class UserListViewModel() : ViewModel() {
    private val userRepository = UserListRepository()

    private val userMutableLiveData = MutableLiveData<List<User>>()
    private val exceptionMutableLiveData = SingleLiveEvent<Throwable>()
    private val isLoadingLiveData = SingleLiveEvent<Boolean>()

    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData

    val userLiveData: LiveData<List<User>>
        get() = userMutableLiveData

    val exceptionLiveData: LiveData<Throwable>
        get() = exceptionMutableLiveData

    fun getAllUsers(){
        viewModelScope.launch {
            isLoadingLiveData.postValue(true)
            try {
                userMutableLiveData.postValue(userRepository.getAllUsers())
            } catch (t: Throwable) {
                exceptionMutableLiveData.postValue(t)
                userMutableLiveData.postValue(emptyList())
            }
            isLoadingLiveData.postValue(false)
        }
    }
}