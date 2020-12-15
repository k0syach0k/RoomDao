package com.skillbox.github.ui.repository_list

import com.skillbox.github.models.Repository
import com.skillbox.github.network.Networking
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RepositoryListRepository {
    suspend fun getRepositoryList(): List<Repository> {
        return withContext(Dispatchers.IO) {
            Networking.githubApi.getUserRepositories()
        }
    }
}
