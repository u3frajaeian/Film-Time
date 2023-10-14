package io.filmtime.feature.movie.list

import io.filmtime.data.model.VideoThumbnail

data class MovieListUiState(
  val isLoading: Boolean,
  val items: List<VideoThumbnail> = emptyList(),
)
