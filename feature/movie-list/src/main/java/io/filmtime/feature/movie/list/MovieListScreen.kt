package io.filmtime.feature.movie.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun MovieListScreen(
  viewModel: MovieListViewModel,
  onBackPressed: () -> Unit,
) {
  val state by viewModel.state.collectAsStateWithLifecycle()
}
