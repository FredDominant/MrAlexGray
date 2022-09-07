package com.freddominant.myapplication

import com.freddominant.myapplication.data.dto.GitHubRepoDTO
import com.freddominant.myapplication.data.mapper.GitHubRepoDTOtoGitHubRepoMapper
import org.junit.Assert
import org.junit.Test

class GitHubRepoDTOtoGitHubRepoMapperUnitTest {

    @Test
    fun `GitHubRepoDTOtoGitHubRepoMapper should map to GitHubRepoDTO correctly`() {
        val mapper = GitHubRepoDTOtoGitHubRepoMapper()
        val gitHubRepoDTO: GitHubRepoDTO = RepositoryUnitTest.dummyRepo
        val gitHubRepo = mapper.map(gitHubRepoDTO)
        Assert.assertTrue(gitHubRepo.id == gitHubRepoDTO.id)
        Assert.assertTrue(gitHubRepo.watchersCount == gitHubRepoDTO.watchers)
        Assert.assertTrue(gitHubRepo.openIssuesCount == gitHubRepoDTO.openIssues)
        Assert.assertTrue(gitHubRepo.repoUrl == gitHubRepoDTO.url)
        Assert.assertTrue(null == gitHubRepoDTO.fullName)
    }

    @Test
    fun `GitHubRepoDTOtoGitHubRepoMapper should replace null values with empty string`() {
        val mapper = GitHubRepoDTOtoGitHubRepoMapper()
        val gitHubRepoDTO: GitHubRepoDTO = RepositoryUnitTest.dummyRepo
        val gitHubRepo = mapper.map(gitHubRepoDTO)
        Assert.assertTrue(gitHubRepo.licence.isEmpty() && gitHubRepoDTO.license == null)
        Assert.assertTrue(gitHubRepo.defaultBranchName.isEmpty() && gitHubRepoDTO.defaultBranch == null)
        Assert.assertTrue(gitHubRepo.description.isEmpty() && gitHubRepoDTO.description == null)

    }

}