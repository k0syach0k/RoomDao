package com.skillbox.github.network

import com.skillbox.github.models.Repository
import com.skillbox.github.models.UserProfile
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface GithubApi {

    @GET("/user")
    suspend fun getUserInfo(): UserProfile

    @GET("/user/following")
    suspend fun getFollowingUsers(): List<UserProfile>

    @GET("/repositories")
    suspend fun getUserRepositories(): List<Repository>

    @GET("/user/starred/{owner}/{repo}")
    suspend fun getStarState(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Response<Unit>

    @PUT("/user/starred/{owner}/{repo}")
    suspend fun starRepository(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Response<Unit>

    @DELETE("/user/starred/{owner}/{repo}")
    suspend fun unStarRepository(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Response<Unit>
}
