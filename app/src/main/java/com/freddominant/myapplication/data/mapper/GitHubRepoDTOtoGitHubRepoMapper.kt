package com.freddominant.myapplication.data.mapper

import android.util.Log
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.toUpperCase
import com.freddominant.myapplication.data.dto.GitHubRepoDTO
import com.freddominant.myapplication.data.entities.GitHubRepo
import com.freddominant.myapplication.utils.Utils
import com.freddominant.myapplication.utils.Utils.formatDate
import org.threeten.bp.DateTimeException
import org.threeten.bp.LocalDate
import org.threeten.bp.ZonedDateTime
import java.time.format.DateTimeParseException
import java.util.*
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
            dummyImageResource = Utils.getRandomImageResource(),
            createdDate = formatDate(input.createdAt),
            updatedDate = formatDate(input.lastUpdateAt)
        )
    }
}