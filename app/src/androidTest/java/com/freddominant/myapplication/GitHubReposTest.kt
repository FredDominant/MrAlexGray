package com.freddominant.myapplication

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.freddominant.myapplication.data.dto.GitHubRepoDTO
import com.freddominant.myapplication.data.dto.RepoOwner
import com.freddominant.myapplication.data.mapper.GitHubRepoDTOtoGitHubRepoMapper
import com.freddominant.myapplication.ui.theme.MrAlexGrayTheme
import com.freddominant.myapplication.ui.views.*
import org.junit.Rule
import org.junit.Test

class GitHubReposTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun LoadingViewComposableShouldBeDisplayedProperly() {
        composeTestRule.setContent {
            MrAlexGrayTheme {
                val loadingState = GitHubReposViewState(
                    isLoading = true,
                    hasError = false,
                    repos = emptyList(),
                    isFetchingMore = false
                )
                Content(state = loadingState) { }
            }
        }
        composeTestRule.onNode(hasContentDescription("Loading indicator"))
            .assertIsDisplayed()
            .assertHasNoClickAction()
            .assertExists()
    }

    @Test
    fun ErrorViewComposableShouldBeDisplayedProperly() {
        composeTestRule.setContent {
            MrAlexGrayTheme {
                val errorState = GitHubReposViewState(
                    isLoading = false,
                    hasError = true,
                    repos = emptyList(),
                    isFetchingMore = false
                )
                Content(state = errorState) { }
            }
        }
        composeTestRule.onNode(hasTextExactly("An unknown error has occurred"))
            .assertIsDisplayed()
            .assertHasNoClickAction()
            .assertExists()
    }

    @Test
    fun NoReposComposableShouldBeDisplayedProperly() {
        composeTestRule.setContent {
            MrAlexGrayTheme {
                val noReposState = GitHubReposViewState(
                    isLoading = false,
                    hasError = false,
                    repos = emptyList(),
                    isFetchingMore = false
                )
                Content(state = noReposState) { }
            }
        }
        composeTestRule.onNode(hasTextExactly("There are currently no repos to display"))
            .assertIsDisplayed()
            .assertHasNoClickAction()
            .assertExists()
    }

    @Test
    fun RepoCellComposableShouldBeDisplayedProperly() {
        composeTestRule.setContent {
            MrAlexGrayTheme {
                RepoCell(dummyGitHubRepo.first())
            }
        }
        composeTestRule.onNode(hasTextExactly(dummyGitHubRepo.first().name))
            .assertIsDisplayed()
            .assertExists()
    }

    companion object {
        private val dummyRepo = GitHubRepoDTO(
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
            lastUpdateAt = "2012-10-06T16:37:39Z"
        )

        private val dummyGitHubRepoDTOs = listOf(
            dummyRepo,
            dummyRepo.copy(id = "iD_2", language = "Java", watchers = 12, private = false)
        )
        val dummyGitHubRepo = dummyGitHubRepoDTOs.map { GitHubRepoDTOtoGitHubRepoMapper().map(it) }
    }
}