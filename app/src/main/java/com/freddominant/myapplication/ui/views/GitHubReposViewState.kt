package com.freddominant.myapplication.ui.views

import com.freddominant.myapplication.data.entities.GitHubRepo

data class GitHubReposViewState(
    val isLoading: Boolean,
    val hasError: Boolean,
    val errorMessage: String? = null,
    val isFetchingMore: Boolean,
    val repos: List<GitHubRepo>
)
