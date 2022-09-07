package com.freddominant.myapplication.ui.views

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.freddominant.myapplication.data.entities.GitHubRepo
import com.freddominant.myapplication.ui.theme.MrAlexGrayTheme
import com.freddominant.myapplication.ui.theme.Shapes
import com.freddominant.myapplication.ui.viewmodel.MrAlexGrayViewModel
import com.freddominant.myapplication.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MrAlexGrayViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MrAlexGrayTheme {
                val state = viewModel.viewState.collectAsState().value
                Log.e("State >>", "$state")
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 4.dp),
                    color = MaterialTheme.colors.background
                ) {
                    when {
                        state.isLoading -> LoadingView()
                        state.hasError -> ErrorView()
                        state.repos.isEmpty() -> NoReposView()
                        else -> ReposView(
                            gitHubRepos = state.repos,
                            isFetchingMore = state.isFetchingMore,
                            handleFetchMore = { viewModel.loadMoreRepositories() }
                        )
                    }
                }
            }
        }
    }
}
