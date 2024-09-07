package io.filmtime.domain.tmdb.shows.impl

import io.filmtime.data.model.GeneralError
import io.filmtime.data.model.MovieVideo
import io.filmtime.data.model.Result
import io.filmtime.data.tmdb.shows.TmdbShowsRepository
import io.filmtime.domain.tmdb.shows.GetShowVideosUseCase
import javax.inject.Inject

internal class GetShowVideosUseCaseImpl @Inject constructor(
  private val repository: TmdbShowsRepository,
) : GetShowVideosUseCase {
  override suspend fun invoke(movieId: Int): Result<List<MovieVideo>, GeneralError> =
    repository.getVideos(movieId)
}
