package io.filmtime.domain.tmdb.movies

import androidx.paging.PagingData
import io.filmtime.data.model.VideoThumbnail
import kotlinx.coroutines.flow.Flow

interface GetMoviesByGenreUseCase {

  operator fun invoke(genreId: Long): Flow<PagingData<VideoThumbnail>>
}
