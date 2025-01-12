package org.example.tmdb.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.example.tmdb.domain.model.FeedWrapper
import org.example.tmdb.domain.repository.BaseFeedRepository
import org.example.tmdb.utils.Async

sealed class MovieListUiState {
    object Loading : MovieListUiState()
    data class Error(val message: String) : MovieListUiState()
    data class Success(val result: List<FeedWrapper>) : MovieListUiState()
}

class FeedViewModel(
    private val repository: BaseFeedRepository,
) : ViewModel() {

    private val _stateFlow = MutableStateFlow<MovieListUiState>(MovieListUiState.Loading)
    val stateFlow: StateFlow<MovieListUiState>
        get() = _stateFlow

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            repository.getResult().collect { uiState ->
                when (uiState) {
                    is Async.Loading -> _stateFlow.tryEmit(MovieListUiState.Loading)
                    is Async.Error -> _stateFlow.tryEmit(MovieListUiState.Error(uiState.message))
                    is Async.Success -> _stateFlow.tryEmit(MovieListUiState.Success(uiState.data))
                }
            }
        }
    }
}