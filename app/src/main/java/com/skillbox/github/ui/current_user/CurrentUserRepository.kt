package com.skillbox.github.ui.current_user

import com.skillbox.github.models.UserProfile
import com.skillbox.github.network.Networking
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CurrentUserRepository {
    suspend fun getUser(): UserProfile {
        return withContext(Dispatchers.IO) { Networking.githubApi.getUserInfo() }
    }

    suspend fun getFollowingUser(): List<UserProfile> {
        return withContext(Dispatchers.IO) { Networking.githubApi.getFollowingUsers() }
    }
}
