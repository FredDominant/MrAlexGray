package com.freddominant.myapplication.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.freddominant.myapplication.CoroutineContextProvider
import com.freddominant.myapplication.ui.views.GitHubReposViewState
import com.freddominant.myapplication.data.entities.Repository
import com.freddominant.myapplication.data.entities.GitHubRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MrAlexGrayViewModel @Inject constructor(
    private val repository: Repository,
    private val contextProvider: CoroutineContextProvider
) : ViewModel() {

    private val _viewState = MutableStateFlow(
        GitHubReposViewState(
            isLoading = false,
            hasError = false,
            repos = emptyList(),
            isFetchingMore = false
        )
    )

    val viewState: StateFlow<GitHubReposViewState> = _viewState
    var currentGitHubRepos = emptyList<GitHubRepo>()
    private set

    var nextPageToFetch = 0
    private set

    fun initialise() {
        setLoadingState(isFetchingMore = false)
        fetchRepositories(page = nextPageToFetch)
    }

    private fun fetchRepositories(
        itemsPerPage: Int = ITEMS_PER_PAGE,
        page: Int,
        isPaginating: Boolean = false
    ) {
        viewModelScope.launch(contextProvider.io) {
            runCatching {
                repository.fetchRepos(itemsPerPage = itemsPerPage, page = page)
            }.onFailure { error ->
                _viewState.value = handleError(error)
            }.onSuccess { gitHubRepos ->
                _viewState.value = handleSuccess(gitHubRepos, isPaginating)
                nextPageToFetch++
            }
        }
    }

    private fun handleError(error: Throwable): GitHubReposViewState {
        return GitHubReposViewState(
            isLoading = false,
            hasError = true,
            repos = currentGitHubRepos,
            isFetchingMore = false,
            errorMessage = error.localizedMessage
        )
    }

    private fun handleSuccess(
        gitHubRepos: List<GitHubRepo>,
        isPaginating: Boolean = false)
    : GitHubReposViewState {
        val repos = if (isPaginating) currentGitHubRepos + gitHubRepos else gitHubRepos
        currentGitHubRepos = repos
        println(repos.first())
        return GitHubReposViewState(
            isLoading = false,
            hasError = false,
            repos = currentGitHubRepos,
            isFetchingMore = false
        )
    }

    private fun setLoadingState(isFetchingMore: Boolean) {
        _viewState.value = GitHubReposViewState(
            isLoading = !isFetchingMore,
            hasError = false,
            repos = currentGitHubRepos,
            isFetchingMore = isFetchingMore
        )
    }

    fun loadMoreRepositories() {
        setLoadingState(isFetchingMore = true)
        fetchRepositories(page = nextPageToFetch, isPaginating = true)
    }

    private companion object {
        const val ITEMS_PER_PAGE = 10
    }
}
