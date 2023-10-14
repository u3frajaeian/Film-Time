package io.filmtime.feature.movie.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.filmtime.data.model.Result
import io.filmtime.domain.tmdb.movies.GetTrendingMoviesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
  private val getTrendingMovies: GetTrendingMoviesUseCase,
) : ViewModel() {
  private val _state = MutableStateFlow(MovieListUiState(isLoading = false))
  val state = _state.asStateFlow()

  init {
    viewModelScope.launch {
      loadTrendingMovies()
    }
  }

  private suspend fun loadTrendingMovies() {
    getTrendingMovies()
      .onStart {
        _state.update { state -> state.copy(isLoading = true) }
      }
      .onCompletion { _state.update { state -> state.copy(isLoading = false) } }
      .onEach { result ->
        when (result) {
          is Result.Success -> {
            _state.update { state ->
              state.copy(items = state.items + result.data)
            }
          }

          is Result.Failure -> {
            // TODO: Handle error
          }
        }
      }
      .collect()
  }
}
