package io.filmtime.domain.tmdb.movies.impl

import androidx.paging.PagingData
import io.filmtime.data.model.VideoThumbnail
import io.filmtime.domain.tmdb.movies.GetMoviesByGenreUseCase
import io.fimltime.data.tmdb.movies.TmdbMovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMoviesByGenreUseCaseImpl @Inject constructor(
  private val tmdbMovieRepository: TmdbMovieRepository,
) : GetMoviesByGenreUseCase {

  override fun invoke(genreId: Long): Flow<PagingData<VideoThumbnail>> =
    tmdbMovieRepository.moviesByGenre(genreId)
}
