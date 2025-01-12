package org.example.tmdb

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.example.tmdb.common.ErrorScreen
import org.example.tmdb.common.ProgressScreen
import org.example.tmdb.viewmodel.FeedViewModel
import org.example.tmdb.viewmodel.MovieListUiState
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App(viewModel: FeedViewModel = koinViewModel<FeedViewModel>()) {
    MaterialTheme {
        val state by viewModel.stateFlow.collectAsStateWithLifecycle()
        when(val currentState = state) {
            is MovieListUiState.Loading -> ProgressScreen()
            is MovieListUiState.Error -> ErrorScreen(currentState.message) { viewModel.refresh() }
            is MovieListUiState.Success -> FeedScreen(currentState.result)
        }
    }
}