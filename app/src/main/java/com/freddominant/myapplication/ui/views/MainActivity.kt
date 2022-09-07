package com.freddominant.myapplication.ui.views

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.freddominant.myapplication.ui.theme.MrAlexGrayTheme
import com.freddominant.myapplication.ui.viewmodel.MrAlexGrayViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MrAlexGrayViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MrAlexGrayTheme {
                val state = viewModel.viewState.collectAsState().value
                Surface(
                    modifier = Modifier.fillMaxSize().padding(horizontal = 4.dp),
                    color = MaterialTheme.colors.background
                ) {
                    Content(state = state) { viewModel.loadMoreRepositories() }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.initialise()
    }
}
