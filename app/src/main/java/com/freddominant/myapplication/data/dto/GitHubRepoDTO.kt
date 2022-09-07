package com.freddominant.myapplication.data.dto

import com.squareup.moshi.Json

data class GitHubRepoDTO(
    val id: String,
    val name: String?,
    val private: Boolean,
    val visibility: String?,
    val forks: String?,
    val language: String?,
    val watchers: Int,
    val description: String?,
    val owner: RepoOwner,
    val license: RepoLicense?,
    @Json(name = "open_issues_count")
    val openIssues: Int,
    @Json(name = "html_url")
    val url: String,
    @Json(name = "default_branch")
    val defaultBranch: String?,
    @Json(name = "allow_forking")
    val allowForking: Boolean,
    @Json(name = "full_name")
    val fullName: String?,
)

data class RepoOwner(
    @Json(name = "login")
    val userName: String,
    @Json(name = "avatar_url")
    val profileImage: String,
    @Json(name = "html_url")
    val profileUrl: String
)

data class RepoLicense(
    val key: String?,
    val name: String?,
    val url: String?
)