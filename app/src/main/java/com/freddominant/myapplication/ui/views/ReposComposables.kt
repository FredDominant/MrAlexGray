package com.freddominant.myapplication.ui.views

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.freddominant.myapplication.data.entities.GitHubRepo
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun ReposView(gitHubRepos: List<GitHubRepo>, isFetchingMore: Boolean, handleFetchMore: () -> Unit) {
    Column(
        modifier = Modifier.padding(8.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ReposList(gitHubRepos = gitHubRepos, handleFetchMore = handleFetchMore)
        AnimatedVisibility(
            visible = isFetchingMore,
            enter = fadeIn() + expandHorizontally(),
            exit = fadeOut() + shrinkHorizontally()
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Loading More...",
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    softWrap = true,
                    style = MaterialTheme.typography.body1.copy(
                        fontWeight = FontWeight.Normal,
                    )
                )
            }
        }
    }
}

@Composable
fun Content(state: GitHubReposViewState, handleFetchMore: () -> Unit) {
    when {
        state.isLoading -> LoadingView()
        state.hasError -> ErrorView()
        state.repos.isEmpty() -> NoReposView()
        else -> ReposView(
            gitHubRepos = state.repos,
            isFetchingMore = state.isFetchingMore,
            handleFetchMore = handleFetchMore
        )
    }
}

@Composable
fun ReposList(gitHubRepos: List<GitHubRepo>, handleFetchMore: () -> Unit) {
    val listState = rememberLazyListState()
    LazyColumn(state = listState) {
        items(items = gitHubRepos) { gitHubRepo ->
            RepoCell(gitHubRepo)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
    InfiniteScrollHandler(listState = listState) { handleFetchMore() }
}

@Composable
fun NoReposView() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "There are currently no repos to display",
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            softWrap = true,
            style = MaterialTheme.typography.body1.copy(
                fontWeight = FontWeight.Normal,
            )
        )
    }
}

@Composable
fun ErrorView() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "An unknown error has occurred",
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            softWrap = true,
            style = MaterialTheme.typography.body1.copy(
                fontWeight = FontWeight.Normal,
            )
        )
    }
}

@Composable
fun LoadingView() {
    Column(
        modifier = Modifier.fillMaxSize()
            .semantics { contentDescription = "Loading indicator" },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(color = Color.Black, strokeWidth = 5.dp)
    }
}

@Composable
fun RepoCell(gitHubRepo: GitHubRepo) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 4.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = 5.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Image(
                modifier = Modifier
                    .background(color = Color.LightGray)
                    .fillMaxWidth()
                    .width(400.dp)
                    .height(250.dp),
                painter = painterResource(id = gitHubRepo.dummyImageResource),
                contentDescription = "${gitHubRepo.name} image",
                contentScale = ContentScale.FillBounds
            )
            Spacer(modifier = Modifier.height(3.dp))
            if (gitHubRepo.name.isNotEmpty()) {
                RepoDetailsItem(
                    modifier = Modifier.padding(horizontal = 4.dp),
                    title = "Repo Name",
                    description = gitHubRepo.name,
                    isClickableLink = true,
                    url = gitHubRepo.repoUrl
                )
            }
            if (gitHubRepo.fullName.isNotEmpty()) {
                Spacer(modifier = Modifier.height(3.dp))
                RepoDetailsItem(
                    modifier = Modifier.padding(horizontal = 4.dp),
                    title = "Repo Full Name",
                    description = gitHubRepo.fullName,
                    isClickableLink = true,
                    url = gitHubRepo.repoUrl
                )
                Spacer(modifier = Modifier.height(3.dp))
            }
            if (gitHubRepo.licence.isNotEmpty()) {
                Spacer(modifier = Modifier.height(3.dp))
                RepoDetailsItem(
                    modifier = Modifier.padding(horizontal = 4.dp),
                    title = "License",
                    description = gitHubRepo.licence,
                    isClickableLink = true,
                    url = gitHubRepo.licenceUrl
                )
            }
            if (gitHubRepo.visibility.isNotEmpty()) {
                Spacer(modifier = Modifier.height(3.dp))
                RepoDetailsItem(
                    modifier = Modifier.padding(horizontal = 4.dp),
                    title = "Visibility",
                    description = gitHubRepo.visibility
                )
            }
            if (gitHubRepo.language.isNotEmpty()) {
                Spacer(modifier = Modifier.height(3.dp))
                RepoDetailsItem(
                    modifier = Modifier.padding(horizontal = 4.dp),
                    title = "Language",
                    description = gitHubRepo.language
                )
            }
            if (gitHubRepo.defaultBranchName.isNotEmpty()) {
                Spacer(modifier = Modifier.height(3.dp))
                RepoDetailsItem(
                    modifier = Modifier.padding(horizontal = 4.dp),
                    title = "Default Branch Name",
                    description = gitHubRepo.defaultBranchName
                )
            }
            Spacer(modifier = Modifier.height(3.dp))
            RepoDetailsItem(
                modifier = Modifier.padding(horizontal = 4.dp),
                title = "Can be forked",
                description = "${gitHubRepo.canBeForked}",
            )
            Spacer(modifier = Modifier.height(3.dp))
            RepoDetailsItem(
                modifier = Modifier.padding(horizontal = 4.dp),
                title = "Watchers",
                description = "${gitHubRepo.watchersCount}",
            )
            Spacer(modifier = Modifier.height(3.dp))
            RepoDetailsItem(
                modifier = Modifier.padding(horizontal = 4.dp),
                title = "Open Issues",
                description = "${gitHubRepo.openIssuesCount}",
            )
        }
    }
}

@Composable
fun RepoDetailsItem(
    modifier: Modifier, 
    title: String,
    description: String,
    isClickableLink: Boolean = false,
    url: String = ""
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Bottom
    ) {
        Text(
            text = "$title: ",
            style = MaterialTheme.typography.h6.copy(
                fontWeight = FontWeight.SemiBold,
            )
        )
        Spacer(modifier = Modifier.width(4.dp))
        if (isClickableLink) {
            val annotatedString = buildAnnotatedString {
                append(description)
                addStyle(
                    style = SpanStyle(
                        color = Color.Magenta,
                        textDecoration = TextDecoration.Underline,
                        fontSize = MaterialTheme.typography.h6.fontSize
                    ),
                    start = 0,
                    end = url.length
                )
            }
            val uriHandler = LocalUriHandler.current

            ClickableText(text = annotatedString, onClick = {
                uriHandler.openUri(url)
            })
        } else {
            Text(
                text = description,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                softWrap = true,
                style = MaterialTheme.typography.h6.copy(
                    fontWeight = FontWeight.Normal,
                )
            )
        }
    }
}

@OptIn(InternalCoroutinesApi::class)
@Composable
fun InfiniteScrollHandler(
    listState: LazyListState,
    buffer: Int = 3,
    onLoadMore: () -> Unit
) {
    val loadMore = remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val totalItemsNumber = layoutInfo.totalItemsCount
            val lastVisibleItemIndex = (layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0) + 1
            lastVisibleItemIndex > (totalItemsNumber - buffer)
        }
    }

    LaunchedEffect(loadMore) {
        snapshotFlow { loadMore.value }
            .distinctUntilChanged()
            .collect { onLoadMore() }
    }
}
