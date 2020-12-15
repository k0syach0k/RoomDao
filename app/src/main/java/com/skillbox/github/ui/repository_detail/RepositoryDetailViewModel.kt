package com.skillbox.github.ui.repository_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skillbox.github.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class RepositoryDetailViewModel : ViewModel() {
    private val repository = RepositoryDetailRepository()

    private val repositoryStarLiveData = MutableLiveData<Boolean>()
    private val isLoadingLiveData = MutableLiveData<Boolean>()
    private val exceptionLiveData = SingleLiveEvent<Throwable>()

    val repositoryStar: LiveData<Boolean>
        get() = repositoryStarLiveData

    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData

    val exception: LiveData<Throwable>
        get() = exceptionLiveData

    fun getStarState(owner: String, repo: String) {
        if (repositoryStar.value == null) {
            isLoadingLiveData.postValue(true)
            viewModelScope.launch {
                try {
                    val starState = repository.getStarState(owner, repo)
                    repositoryStarLiveData.postValue(starState)
                } catch (t: Throwable) {
                    exceptionLiveData.postValue(t)
                } finally {
                    isLoadingLiveData.postValue(false)
                }
            }
        }
    }

    fun changeStarState(owner: String, repo: String) {
        if (repositoryStar.value != null) {
            isLoadingLiveData.postValue(true)
            viewModelScope.launch {
                try {
                    var starChangeState = false

                    when (repositoryStar.value) {
                        true -> starChangeState = repository.unStarRepository(owner, repo)
                        false -> starChangeState = repository.starRepository(owner, repo)
                    }

                    if (starChangeState) repositoryStarLiveData.postValue(repositoryStar.value!!.not())
                } catch (t: Throwable) {
                    exceptionLiveData.postValue(t)
                } finally {
                    isLoadingLiveData.postValue(false)
                }
            }
        }
    }
}
