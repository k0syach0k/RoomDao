package com.skillbox.github.ui.current_user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skillbox.github.models.UserProfile
import com.skillbox.github.utils.SingleLiveEvent
import kotlinx.coroutines.*

class CurrentUserViewModel : ViewModel() {
    private val userRepository = CurrentUserRepository()

    private val errorHandler = CoroutineExceptionHandler { _, throwable ->
        exceptionLiveData.postValue(throwable)
    }

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO + errorHandler)

    private val userLiveData = MutableLiveData<UserProfile>()
    private val isLoadingLiveData = MutableLiveData<Boolean>()
    private val exceptionLiveData = SingleLiveEvent<Throwable>()
    private val followingUsersLiveData = MutableLiveData<List<UserProfile>>()

    val user: LiveData<UserProfile>
        get() = userLiveData

    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData

    val exception: LiveData<Throwable>
        get() = exceptionLiveData

    val followingUsers: LiveData<List<UserProfile>>
        get() = followingUsersLiveData

    @ExperimentalCoroutinesApi
    fun getData() {
        if (userLiveData.value == null) {
            scope.launch {
                isLoadingLiveData.postValue(true)

                val deferredUser = scope.async {
                    userRepository.getUser()
                }
                val deferredFollowingUser = scope.async {
                    userRepository.getFollowingUser()
                }

                userLiveData.postValue(deferredUser.await())
                followingUsersLiveData.postValue(deferredFollowingUser.await())

                isLoadingLiveData.postValue(false)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}
