package io.filmtime.domain.tmdb.shows

import io.filmtime.data.model.VideoThumbnail
import kotlinx.coroutines.flow.Flow

interface GetBookmarkedShowsUseCase {

  suspend operator fun invoke(): Flow<List<VideoThumbnail>>
}
