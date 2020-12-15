package com.skillbox.github.ui.repository_detail

import com.skillbox.github.network.Networking
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RepositoryDetailRepository {
    suspend fun getStarState(owner: String, repo: String): Boolean {
        var result = false
        when (withContext(Dispatchers.IO) { Networking.githubApi.getStarState(owner, repo).code() }) {
            404 -> result = false
            204 -> result = true
            else -> Error("Error receiving star state")
        }
        return result
    }

    suspend fun starRepository(owner: String, repo: String): Boolean {
        return responseProcessing(
            withContext(Dispatchers.IO) { Networking.githubApi.starRepository(owner, repo).code() }
        )
    }

    suspend fun unStarRepository(owner: String, repo: String): Boolean {
        return responseProcessing(
            withContext(Dispatchers.IO) { Networking.githubApi.unStarRepository(owner, repo).code() }
        )
    }

    private fun responseProcessing(
        responseCode: Int
    ): Boolean {
        return when (responseCode) {
            204 -> true
            else -> false
        }
    }
}
