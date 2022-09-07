package com.freddominant.myapplication.data.api

import com.freddominant.myapplication.data.dto.GitHubRepoDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubService {
    @GET("/users/mralexgray/repos")
    suspend fun getRepos(
        @Query("per_page") itemsPerPage: Int,
        @Query("page") page: Int
    ): List<GitHubRepoDTO>
}