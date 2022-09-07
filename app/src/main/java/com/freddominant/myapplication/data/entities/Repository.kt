package com.freddominant.myapplication.data.entities

interface Repository {
    suspend fun fetchRepos(itemsPerPage: Int, page: Int): List<GitHubRepo>
}