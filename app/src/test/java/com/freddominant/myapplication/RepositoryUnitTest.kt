package com.freddominant.myapplication

import com.freddominant.myapplication.data.api.GitHubService
import com.freddominant.myapplication.data.dto.GitHubRepoDTO
import com.freddominant.myapplication.data.dto.RepoLicense
import com.freddominant.myapplication.data.dto.RepoOwner
import com.freddominant.myapplication.data.entities.GitHubRepo
import com.freddominant.myapplication.data.entities.RepositoryImpl
import com.freddominant.myapplication.data.mapper.GitHubRepoDTOtoGitHubRepoMapper
import com.squareup.moshi.Json
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.*

@OptIn(ExperimentalCoroutinesApi::class)
class RepositoryUnitTest {
    @Test
    fun `RepositoryImpl should fetch Repos successfully`() {
        val gitHubService: GitHubService = mock()
        val mapper: GitHubRepoDTOtoGitHubRepoMapper = mock()
        runTest {
            whenever(gitHubService.getRepos(2, 0)).thenReturn(dummyGitHubRepoDTOs)
            val repositoryImpl = RepositoryImpl(gitHubService, mapper)
            Assert.assertTrue(repositoryImpl.fetchRepos(2, 0).size == dummyGitHubRepoDTOs.size)
            verify(gitHubService, atLeastOnce()).getRepos(2, 0)
            verify(mapper, atLeastOnce()).map(gitHubService.getRepos(2, 0).first())
        }
    }

    @Test
    fun `RepositoryImpl should call mapper`() {
        val gitHubService: GitHubService = mock()
        val mapper: GitHubRepoDTOtoGitHubRepoMapper = mock()
        runTest {
            whenever(gitHubService.getRepos(2, 0)).thenReturn(dummyGitHubRepoDTOs)
            val repositoryImpl = RepositoryImpl(gitHubService, mapper)
            Assert.assertTrue(repositoryImpl.fetchRepos(2, 0).size == dummyGitHubRepoDTOs.size)
            verify(mapper, atLeastOnce()).map(gitHubService.getRepos(2, 0).first())
        }
    }

    companion object {
        val dummyRepo = GitHubRepoDTO(
            id = "randomIDOne",
            name = "repoName",
            private = false,
            visibility = "private",
            forks = null,
            language = "kotlin",
            watchers = 9,
            description = null,
            owner = RepoOwner(
                userName = "owner",
                profileImage = "",
                profileUrl = ""
            ),
            license = null,
            openIssues = 11,
            url = "randomURL",
            defaultBranch = null,
            allowForking = true,
            fullName = null,
            createdAt = "2012-10-06T16:37:39Z",
            lastUpdateAt = null
        )

        val dummyGitHubRepoDTOs = listOf(
            dummyRepo,
            dummyRepo.copy(id = "iD_2", language = "Java", watchers = 12, private = false)
        )

        val dummyGitHubRepo = dummyGitHubRepoDTOs.map { GitHubRepoDTOtoGitHubRepoMapper().map(it) }

        val extraDummyGitHubRepo = listOf(
            dummyRepo.copy(
                id = "extraDummyOne",
                fullName = "Dummy One Repo",
                language = "CoffeeScript"
            ),
            dummyRepo.copy(
                id = "extraDummyTwo",
                fullName = "Dummy Two Repo",
                language = "ActionScript"
            )
        ).map { GitHubRepoDTOtoGitHubRepoMapper().map(it) }
    }
}