package com.freddominant.myapplication.data.entities

import androidx.annotation.DrawableRes

data class GitHubRepo(
    val id: String,
    val name: String,
    val fullName: String,
    val description: String,
    val visibility: String,
    val language: String,
    val ownerName: String,
    val ownerProfileUrl: String,
    val ownerProfileImageUrl: String,
    val openIssuesCount: Int,
    val canBeForked: Boolean,
    val defaultBranchName: String,
    val repoUrl: String,
    val watchersCount: Int,
    val licence: String,
    val licenceUrl: String,
    @DrawableRes val dummyImageResource: Int
)
