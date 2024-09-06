package io.filmtime.domain.tmdb.movies.impl

import io.filmtime.data.model.GeneralError
import io.filmtime.data.model.MovieVideo
import io.filmtime.data.model.Result
import io.filmtime.domain.tmdb.movies.GetMovieVideosUseCase
import io.fimltime.data.tmdb.movies.TmdbMovieRepository
import javax.inject.Inject

internal class GetMovieVideosUseCaseImpl @Inject constructor(
  private val repository: TmdbMovieRepository,
) : GetMovieVideosUseCase {
  override suspend fun invoke(movieId: Int): Result<List<MovieVideo>, GeneralError> =
    repository.getMovieVideos(movieId)
}
