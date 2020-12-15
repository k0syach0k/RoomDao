package com.skillbox.github.ui.repository_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skillbox.github.models.Repository
import com.skillbox.github.utils.SingleLiveEvent
import kotlinx.coroutines.*

class RepositoryListViewModel : ViewModel() {
    private val repository = RepositoryListRepository()

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    private val listRepositoryLiveData = MutableLiveData<List<Repository>>()
    private val isLoadingLiveData = MutableLiveData<Boolean>()
    private val exceptionLiveData = SingleLiveEvent<Throwable>()

    val listRepository: LiveData<List<Repository>>
        get() = listRepositoryLiveData

    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData

    val exception: LiveData<Throwable>
        get() = exceptionLiveData

    fun getRepositoryList() {
        if (listRepository.value == null) {
            scope.launch {
                isLoadingLiveData.postValue(true)
                try {
                    val repositoryList = repository.getRepositoryList()
                    listRepositoryLiveData.postValue(repositoryList)
                } catch (t: Throwable) {
                    exceptionLiveData.postValue(t)
                } finally {
                    isLoadingLiveData.postValue(false)
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}
