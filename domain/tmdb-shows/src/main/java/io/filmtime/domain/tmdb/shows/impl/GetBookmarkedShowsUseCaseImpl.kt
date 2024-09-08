package io.filmtime.domain.tmdb.shows.impl

import io.filmtime.data.model.VideoThumbnail
import io.filmtime.data.tmdb.shows.TmdbShowsRepository
import io.filmtime.domain.tmdb.shows.GetBookmarkedShowsUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class GetBookmarkedShowsUseCaseImpl @Inject constructor(
  private val showsRepository: TmdbShowsRepository,
) : GetBookmarkedShowsUseCase {
  override suspend fun invoke(): Flow<List<VideoThumbnail>> =
    showsRepository.getBookmarkedShows()
}
