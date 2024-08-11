package io.filmtime.domain.tmdb.shows.impl

import androidx.paging.PagingData
import io.filmtime.data.model.VideoThumbnail
import io.filmtime.data.tmdb.shows.TmdbShowsRepository
import io.filmtime.domain.tmdb.shows.GetShowsByGenreUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetShowsByGenreUseCaseImpl @Inject constructor(
  private val tmdbShowRepository: TmdbShowsRepository,
) : GetShowsByGenreUseCase {

  override fun invoke(genreId: Long): Flow<PagingData<VideoThumbnail>> =
    tmdbShowRepository.showsByGenre(genreId)
}
