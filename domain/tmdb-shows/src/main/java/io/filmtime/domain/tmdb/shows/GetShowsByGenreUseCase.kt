package io.filmtime.domain.tmdb.shows

import androidx.paging.PagingData
import io.filmtime.data.model.VideoThumbnail
import kotlinx.coroutines.flow.Flow

interface GetShowsByGenreUseCase {

  operator fun invoke(genreId: Long): Flow<PagingData<VideoThumbnail>>
}
