package io.filmtime.domain.tmdb.movies.impl

import io.filmtime.data.model.VideoThumbnail
import io.filmtime.domain.tmdb.movies.GetBookmarkedMoviesUseCase
import io.fimltime.data.tmdb.movies.TmdbMovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class GetBookmarkedMoviesUseCaseImpl @Inject constructor(
  private val repository: TmdbMovieRepository,
) : GetBookmarkedMoviesUseCase {
  override suspend operator fun invoke(): Flow<List<VideoThumbnail>> =
    repository.getBookmarkedMovies()
}
