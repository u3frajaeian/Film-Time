package io.filmtime.feature.video.thumbnail.grid.genre

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import dagger.hilt.android.lifecycle.HiltViewModel
import io.filmtime.data.model.VideoThumbnail
import io.filmtime.data.model.VideoType
import io.filmtime.domain.tmdb.movies.GetMoviesByGenreUseCase
import io.filmtime.domain.tmdb.shows.GetShowsByGenreUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class VideoGridGenreViewModel @Inject constructor(
  savedStateHandle: SavedStateHandle,
  private val moviesByGenreUseCase: GetMoviesByGenreUseCase,
  private val showsByGenreUseCase: GetShowsByGenreUseCase,
) : ViewModel() {

  private val genreId: Long =
    savedStateHandle["genre_id"] ?: throw IllegalArgumentException("genre_id must be provided")

  private val videoType: VideoType =
    savedStateHandle["video_type"] ?: throw IllegalArgumentException("video_type must be provided")

  val pagedList: Flow<PagingData<VideoThumbnail>> =
    videosByGenreUseCase(genreId, videoType)
      .map { pagingData ->
        val items = mutableListOf<VideoThumbnail>()
        pagingData.filter { videoThumbnail ->
          items.contains(videoThumbnail)
            .not()
            .also { shouldAdd ->
              if (shouldAdd) {
                items.add(videoThumbnail)
              }
            }
        }
      }
      .cachedIn(viewModelScope)

  private fun videosByGenreUseCase(genreId: Long, videoType: VideoType): Flow<PagingData<VideoThumbnail>> =
    when (videoType) {
      VideoType.Movie -> moviesByGenreUseCase(genreId)
      VideoType.Show -> showsByGenreUseCase(genreId)
    }
}
