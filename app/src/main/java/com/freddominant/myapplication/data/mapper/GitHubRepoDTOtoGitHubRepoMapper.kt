package com.freddominant.myapplication.data.mapper

import com.freddominant.myapplication.data.dto.GitHubRepoDTO
import com.freddominant.myapplication.data.entities.GitHubRepo
import com.freddominant.myapplication.utils.Utils
import javax.inject.Inject

class GitHubRepoDTOtoGitHubRepoMapper @Inject constructor(): Mapper<GitHubRepoDTO, GitHubRepo> {
    override fun map(input: GitHubRepoDTO): GitHubRepo {
        return GitHubRepo(
            id = input.id,
            name = input.name.orEmpty(),
            fullName = input.fullName.orEmpty(),
            description = input.description.orEmpty(),
            visibility = input.visibility.orEmpty(),
            language = input.language.orEmpty(),
            ownerName = input.owner.userName,
            ownerProfileUrl = input.owner.profileUrl,
            ownerProfileImageUrl = input.owner.profileImage,
            openIssuesCount = input.openIssues,
            canBeForked = input.allowForking,
            defaultBranchName = input.defaultBranch.orEmpty(),
            repoUrl = input.url,
            watchersCount = input.watchers,
            licence = input.license?.name.orEmpty(),
            licenceUrl = input.license?.url.orEmpty(),
            dummyImageResource = Utils.getRandomImageResource()
        )
    }
}