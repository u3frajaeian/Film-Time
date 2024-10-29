package io.filmtime.tv.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.filmtime.data.model.VideoType
import io.filmtime.tv.R
import io.filmtime.tv.ui.similar.SimilarViewModel

@Composable
fun SimilarSection(
  type: VideoType = VideoType.Movie,
  tmdbId: Int,
  onMovieClick: (Int) -> Unit = {},
) {
  val viewModel: SimilarViewModel = hiltViewModel()
  val uiState by viewModel.state.collectAsStateWithLifecycle()

  LaunchedEffect(Unit) {
    viewModel.loadSimilar(videoId = tmdbId, videoType = type)
  }
  MoviesRow(
    thumbnails = uiState.videoItems,
    title = stringResource(R.string.similar),
    onClick = onMovieClick,
  )
}
