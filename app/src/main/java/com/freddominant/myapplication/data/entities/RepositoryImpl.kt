package com.freddominant.myapplication.data.entities

import com.freddominant.myapplication.data.api.GitHubService
import com.freddominant.myapplication.data.mapper.GitHubRepoDTOtoGitHubRepoMapper
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val gitHubService: GitHubService,
    private val mapper: GitHubRepoDTOtoGitHubRepoMapper
) : Repository {
    override suspend fun fetchRepos(itemsPerPage: Int, page: Int): List<GitHubRepo> {
        return gitHubService
            .getRepos(itemsPerPage = itemsPerPage, page = page)
            .map { mapper.map(it) }
    }
}